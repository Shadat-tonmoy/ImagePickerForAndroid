package os.shadattonmoy.imagepickerforandroid.tasks.uiUpdateTasks;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import os.shadattonmoy.imagepickerforandroid.constants.Constants;
import os.shadattonmoy.imagepickerforandroid.model.ImageFolder;
import os.shadattonmoy.imagepickerforandroid.ui.actvities.ImagePickerActivity;
import os.shadattonmoy.imagepickerforandroid.ui.adapters.ImagePickerListAdapter;
import os.shadattonmoy.imagepickerforandroid.ui.fragments.ImagePickerGridFragment;
import os.shadattonmoy.imagepickerforandroid.ui.screenView.ImagePickerListScreenView;

public class ImagePickerListUIUpdateTask
{

    private Activity activity;
    private ImagePickerListScreenView screenView;


    public ImagePickerListUIUpdateTask(Activity activity) {
        this.activity = activity;
    }

    public void bindView(ImagePickerListScreenView screenView)
    {
        this.screenView = screenView;
    }

    public void bindImageFolders(List<ImageFolder> imageFolders)
    {
        if(imageFolders==null || imageFolders.size()<=0)
        {
            hideLoadingMessage();
            showNothingFoundMessage();
        }
        else screenView.getImagePickerListAdapter().bindImageFolders(imageFolders);
    }

    public void registerFolderItemClickListener(ImagePickerListAdapter.Listener listener)
    {
        screenView.getImagePickerListAdapter().setListener(listener);
    }

    public void loadAllImageListForFolder(Bundle argument)
    {
        ((AppCompatActivity)activity).getSupportFragmentManager()
                .beginTransaction()
                .replace(((ImagePickerActivity)activity).getScreenView().getFragmentContainerID(),
                        ImagePickerGridFragment.newInstance(argument), Constants.IMAGE_PICKER_LIST_FRAGMENT)
                .addToBackStack(Constants.IMAGE_PICKER_LIST_FRAGMENT)
                .commitAllowingStateLoss();
    }

    public void bindPDFFiles(List<String> pdfFilePaths)
    {
        if(pdfFilePaths==null || pdfFilePaths.size()<=0)
        {
            hideLoadingMessage();
            showNothingFoundMessage();
        }
        else screenView.getImagePickerListAdapter().bindPDFFiles(pdfFilePaths);

    }

    public void showLoadingMessage()
    {
        screenView.getLoadingMessageView().setVisibility(View.VISIBLE);
    }

    public void hideLoadingMessage()
    {
        screenView.getLoadingMessageView().setVisibility(View.GONE);
    }

    public void showNothingFoundMessage()
    {
        screenView.getNoFileFoundView().setVisibility(View.VISIBLE);
    }

    public void hideNothingFoundMessage()
    {
        screenView.getNoFileFoundView().setVisibility(View.GONE);
    }

    public void performFilter(String newText)
    {
        screenView.getImagePickerListAdapter().getFileFilteringTask().getFilter().filter(newText);
    }

    /*public void showSortingOptionDialog(SortingOptionBottomSheet.Listener listener, Bundle args) {
        SortingOptionBottomSheet sortingOptionBottomSheet = new SortingOptionBottomSheet();
        sortingOptionBottomSheet.setArguments(args);
        sortingOptionBottomSheet.registerListener(listener);
        sortingOptionBottomSheet.show(((AppCompatActivity)activity).getSupportFragmentManager(),Tags.SORTING_OPTIONS);
    }*/

    /*public void sortNote(SortingType sortingType, boolean ascending)
    {
        screenView.getFilePickerListAdapter().getFileSortingTask().sortFiles(sortingType,ascending);

    }*/

    /*public void toggleSortTypeIcon(boolean isAscending, MenuItem menuItem)
    {
        if(isAscending)
            menuItem.setIcon(activity.getResources().getDrawable(R.drawable.sort_arrow_down_white));
        else menuItem.setIcon(activity.getResources().getDrawable(R.drawable.sort_arrow_up_white));
    }*/


}
