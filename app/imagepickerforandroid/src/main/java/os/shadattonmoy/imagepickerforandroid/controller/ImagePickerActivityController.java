package os.shadattonmoy.imagepickerforandroid.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import os.shadattonmoy.imagepickerforandroid.ImagePickerForAndroid;
import os.shadattonmoy.imagepickerforandroid.constants.Constants;
import os.shadattonmoy.imagepickerforandroid.constants.ImagePickerType;
import os.shadattonmoy.imagepickerforandroid.constants.Tags;
import os.shadattonmoy.imagepickerforandroid.tasks.uiUpdateTasks.ImagePickerUIUpdateTask;
import os.shadattonmoy.imagepickerforandroid.ui.fragments.ImagePickerGridFragment;
import os.shadattonmoy.imagepickerforandroid.ui.fragments.ImagePickerListFragment;
import os.shadattonmoy.imagepickerforandroid.ui.screen.ImagePickerActivityScreen;
import os.shadattonmoy.imagepickerforandroid.ui.screenView.ImagePickerActivityScreenView;

public class ImagePickerActivityController implements ImagePickerActivityScreen.Listener
{

    private static final String TAG = "ImagePickerActivityCont";
    private Activity activity;
    private ImagePickerActivityScreenView screenView;
    private ImagePickerUIUpdateTask uiUpdateTask;
    private boolean firstStart = true, isBatchModeEnabled = false;
    private ImagePickerType imagePickerType = ImagePickerType.FOLDER_LIST_FOR_IMAGE;
    private Bundle arguments;
    private ImagePickerForAndroid imagePickerForAndroid;

    public ImagePickerActivityController(Activity activity)
    {
        this.activity = activity;
        this.uiUpdateTask = new ImagePickerUIUpdateTask(activity);
    }

    public void bindView(ImagePickerActivityScreenView screenView)
    {
        this.screenView = screenView;
        uiUpdateTask.bindView(this.screenView);
    }

    public void onCreate(Intent extras, ImagePickerType imagePickerType)
    {
        this.imagePickerType = imagePickerType;
        this.isBatchModeEnabled = extras.getBooleanExtra(Tags.BATCH_MODE_ENABLED,false);
        arguments.putSerializable(Constants.IMAGE_PICKER_TYPE, imagePickerType);
        arguments.putBoolean(Tags.BATCH_MODE_ENABLED, isBatchModeEnabled);
        uiUpdateTask.setToolbarSpinner();
        openImagePickerGridFragment();
    }

    public void setupToolbar(Intent toolbarProperties)
    {
        uiUpdateTask.setupToolbar(toolbarProperties);

    }

    public void onStart()
    {
        screenView.registerListener(this);

    }

    public void onStop()
    {
        screenView.unregisterListener(this);
    }

    @Override
    public void onBackButtonClicked()
    {
        uiUpdateTask.onBackButtonClicked();

    }

    public void onImageListSelected(List<String> selectedImageList)
    {
        imagePickerForAndroid.onImageListSelected(selectedImageList);
        activity.finish();
    }

    public void onSingleImageSelected(String selectedImage)
    {
        imagePickerForAndroid.onSingleImageSelected(selectedImage);
        activity.finish();
    }

    @Override
    public void onSpinnerItemSelected(int position)
    {

        if(!firstStart)
        {
            loadFragmentForSpinner(position);
        }
        if(firstStart)
        {
            firstStart = false;
        }
    }

    private void loadFragmentForSpinner(int position)
    {
        switch (position)
        {
            case 0:
                openImagePickerGridFragment();
                break;
            case 1:
                openImagePickerListFragment();
                break;
        }
    }

    private void openImagePickerListFragment()
    {
        Bundle args = new Bundle();
        args.putSerializable(Constants.IMAGE_PICKER_TYPE,imagePickerType);
        if(arguments!=null)
            args.putBoolean(Constants.FOR_ADDING_MORE_IMAGE,arguments.getBoolean(Constants.FOR_ADDING_MORE_IMAGE,false));
        uiUpdateTask
                .replaceFragment(ImagePickerListFragment.newInstance(args), Constants.IMAGE_PICKER_LIST_FRAGMENT);
    }

    private void openImagePickerGridFragment()
    {
        uiUpdateTask
                .replaceFragment(ImagePickerGridFragment.newInstance(arguments), Constants.IMAGE_PICKER_LIST_FRAGMENT);
    }

    public void bindSelectionBundle(Bundle bundle)
    {
        this.arguments = bundle;
    }

    public void initToolbarAction()
    {
        screenView.initToolbarAction();
    }

    public void setImagePickerForAndroid(ImagePickerForAndroid imagePickerForAndroid) {
        this.imagePickerForAndroid = imagePickerForAndroid;
    }
}
