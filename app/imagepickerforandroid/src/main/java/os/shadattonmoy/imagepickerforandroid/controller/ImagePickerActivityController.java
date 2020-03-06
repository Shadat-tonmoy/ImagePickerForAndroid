package os.shadattonmoy.imagepickerforandroid.controller;

import android.app.Activity;
import android.os.Bundle;

import os.shadattonmoy.imagepickerforandroid.constants.Constants;
import os.shadattonmoy.imagepickerforandroid.constants.ImagePickerType;
import os.shadattonmoy.imagepickerforandroid.tasks.uiUpdateTasks.ImagePickerUIUpdateTask;
import os.shadattonmoy.imagepickerforandroid.ui.fragments.ImagePickerGridFragment;
import os.shadattonmoy.imagepickerforandroid.ui.fragments.ImagePickerListFragment;
import os.shadattonmoy.imagepickerforandroid.ui.screen.ImagePickerActivityScreen;
import os.shadattonmoy.imagepickerforandroid.ui.screenView.ImagePickerActivityScreenView;

public class ImagePickerActivityController implements ImagePickerActivityScreen.Listener
{


    Activity activity;
    private ImagePickerActivityScreenView screenView;
    private ImagePickerUIUpdateTask uiUpdateTask;
    private boolean firstStart = true;
    private ImagePickerType imagePickerType = ImagePickerType.FOLDER_LIST_FOR_IMAGE;
    private Bundle arguments;

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

    public void onCreate(ImagePickerType imagePickerType)
    {
        this.imagePickerType = imagePickerType;
        arguments.putSerializable(Constants.IMAGE_PICKER_TYPE, imagePickerType);
        uiUpdateTask.setToolbarSpinner();
        uiUpdateTask.replaceFragment(ImagePickerGridFragment.newInstance(arguments), Constants.IMAGE_PICKER_LIST_FRAGMENT);
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

    @Override
    public void onSelectAllButtonClicked() {

    }

    @Override
    public void onDoneButtonClicked() {

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
                uiUpdateTask
                        .replaceFragment(ImagePickerGridFragment.newInstance(arguments), Constants.IMAGE_PICKER_LIST_FRAGMENT);
                break;
            case 1:
                Bundle args = new Bundle();
                args.putSerializable(Constants.IMAGE_PICKER_TYPE,imagePickerType);
                if(arguments!=null)
                    args.putBoolean(Constants.FOR_ADDING_MORE_IMAGE,arguments.getBoolean(Constants.FOR_ADDING_MORE_IMAGE,false));
                uiUpdateTask
                        .replaceFragment(ImagePickerListFragment.newInstance(args), Constants.IMAGE_PICKER_LIST_FRAGMENT);
                break;
        }
    }

    public void bindSelectionBundle(Bundle bundle)
    {
        this.arguments = bundle;
    }

    public void initToolbarAction()
    {
        screenView.initToolbarAction();

    }
}
