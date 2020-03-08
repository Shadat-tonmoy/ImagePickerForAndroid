package os.shadattonmoy.imagepickerforandroid.controller;

import android.app.Activity;
import android.os.Bundle;

import java.util.List;

import os.shadattonmoy.imagepickerforandroid.constants.Constants;
import os.shadattonmoy.imagepickerforandroid.constants.ImagePickerType;
import os.shadattonmoy.imagepickerforandroid.constants.SortingType;
import os.shadattonmoy.imagepickerforandroid.constants.Tags;
import os.shadattonmoy.imagepickerforandroid.model.ImageFolder;
import os.shadattonmoy.imagepickerforandroid.tasks.ImageFileFetchingTask;
import os.shadattonmoy.imagepickerforandroid.tasks.uiUpdateTasks.ImagePickerListUIUpdateTask;
import os.shadattonmoy.imagepickerforandroid.ui.adapters.ImagePickerListAdapter;
import os.shadattonmoy.imagepickerforandroid.ui.screen.ImagePickerListScreen;
import os.shadattonmoy.imagepickerforandroid.ui.screenView.ImagePickerListScreenView;

public class ImagePickerListController implements ImagePickerListScreen.Listener, ImagePickerListAdapter.Listener
{
    Activity activity;
    private ImagePickerListUIUpdateTask uiUpdateTask;
    private ImagePickerListScreenView screenView;
    private ImagePickerType imagePickerType = ImagePickerType.FOLDER_LIST_FOR_IMAGE;
    private Bundle arguments;
    private boolean titleAsc = true, sizeAsc = true, lastModifiedAsc = true,isAscending = false, forAppending = false, isBatchModeEnabled = false;
    private SortingType sortingType  = SortingType.FILE_LAST_MODIFIED_TIME;

    public ImagePickerListController(Activity activity)
    {
        this.activity=activity;
        this.uiUpdateTask = new ImagePickerListUIUpdateTask(activity);
    }

    public void bindView(ImagePickerListScreenView screenView)
    {
        this.screenView = screenView;
        this.uiUpdateTask.bindView(screenView);
    }

    public void onCreate()
    {
        fetchListElement();
    }

    private void fetchListElement()
    {
        if(imagePickerType == ImagePickerType.FOLDER_LIST_FOR_IMAGE)
        {
            fetchImageFolders();
        }
        else if(imagePickerType == ImagePickerType.FILE_LIST_FOR_PDF)
        {
            fetchPDFFiles();
        }
    }

    public void onStart()
    {
        screenView.registerListener(this);
    }

    private void fetchImageFolders()
    {
        ImageFileFetchingTask imageFileFetchingTask = new ImageFileFetchingTask(activity, ImagePickerType.FOLDER_LIST_FOR_IMAGE);
        List<ImageFolder> allImageFolders = imageFileFetchingTask.getAllImageFolder();
        uiUpdateTask.bindImageFolders(allImageFolders);
        uiUpdateTask.registerFolderItemClickListener(this);
    }

    public void bindArguments(Bundle arguments)
    {
        this.arguments = arguments;
        isBatchModeEnabled = arguments.getBoolean(Tags.BATCH_MODE_ENABLED,false);
    }

    private void fetchPDFFiles()
    {

    }

    public void onStop()
    {
        screenView.unregisterListener(this);
    }

    @Override
    public void onFilePickerListItemClicked(String path, ImagePickerType imagePickerType)
    {

        switch (imagePickerType)
        {
            case FILE_LIST_FOR_PDF:
                gotoBatchImageProcessorForPDF(path);
                break;
            case FOLDER_LIST_FOR_IMAGE:
                loadAllImageForFolder(path);
                break;
        }

    }

    private void loadAllImageForFolder(String folderPath)
    {
        Bundle args = new Bundle();
        args.putString(Constants.FOLDER_PATH_TAG,folderPath);
        args.putBoolean(Constants.FOR_ADDING_MORE_IMAGE,forAppending);
        args.putBoolean(Tags.BATCH_MODE_ENABLED,isBatchModeEnabled);
        uiUpdateTask.loadAllImageListForFolder(args);

    }

    private void gotoBatchImageProcessorForPDF(String pdfFilePath)
    {

    }



    /*public void onOptionMenuClicked(MenuItem menu)
    {
        int menuId = menu.getItemId();
        switch (menuId)
        {
            case R.id.sort_menu:
                uiUpdateTask.showSortingOptionDialog(this,getSortingSelectionBundle());
                break;
            case R.id.sort_type_menu:
                toggleAscendingDescending(menu);
                break;
        }
    }*/

    /*private Bundle getSortingSelectionBundle()
    {
        Bundle bundle = new Bundle();
        bundle.putBoolean(Tags.TITLE_ASC,titleAsc);
        bundle.putBoolean(Tags.SIZE_ASC,sizeAsc);
        bundle.putBoolean(Tags.TIME_ASC,lastModifiedAsc);
        return bundle;
    }*/

    /*@Override
    public void onFileTitleOptionSelected()
    {
        sortingType = SortingType.FILE_TITLE;
        uiUpdateTask.sortNote(SortingType.FILE_TITLE,isAscending);

    }

    @Override
    public void onFileLastModifiedOptionSelected()
    {
        sortingType = SortingType.FILE_LAST_MODIFIED_TIME;
        uiUpdateTask.sortNote(SortingType.FILE_LAST_MODIFIED_TIME,isAscending);

    }

    @Override
    public void onFileSizeOptionSelected()
    {
        sortingType = SortingType.FILE_SIZE;
        uiUpdateTask.sortNote(SortingType.FILE_SIZE,isAscending);
    }

    private void toggleAscendingDescending(MenuItem menu)
    {
        isAscending = !isAscending;
        uiUpdateTask.toggleSortTypeIcon(isAscending,menu);
        switch (sortingType)
        {
            case FILE_SIZE:
                uiUpdateTask.sortNote(SortingType.FILE_SIZE,isAscending);
                break;
            case FILE_LAST_MODIFIED_TIME:
                uiUpdateTask.sortNote(SortingType.FILE_LAST_MODIFIED_TIME,isAscending);
                break;
            case FILE_TITLE:
                uiUpdateTask.sortNote(SortingType.FILE_TITLE,isAscending);
                break;
        }
    }*/
}
