package com.shadattonmoy.imagepickerforandroid.ui.actvities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;


import com.shadattonmoy.imagepickerforandroid.ImagePickerForAndroid;
import com.shadattonmoy.imagepickerforandroid.constants.ImagePickerType;
import com.shadattonmoy.imagepickerforandroid.constants.Tags;
import com.shadattonmoy.imagepickerforandroid.controller.ImagePickerActivityController;
import com.shadattonmoy.imagepickerforandroid.ui.screenView.ImagePickerActivityScreenView;

public class ImagePickerActivity extends AppCompatActivity
{
    private static final String TAG = "ImagePickerActivity";

    private ImagePickerActivityController controller;
    private Activity activity;
    private ImagePickerActivityScreenView screenView;
    private static ImagePickerForAndroid imagePickerForAndroid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init()
    {
        activity = this;
        ImagePickerType imagePickerType = getFilePickerType();
        screenView = new ImagePickerActivityScreenView(activity.getLayoutInflater(),null);
        controller = new ImagePickerActivityController(activity);
        controller.bindView(screenView);
        setActionBar();
        setContentView(screenView.getRootView());
        initSelectionBundle();
        controller.onCreate(getIntent(),imagePickerType);
        controller.setupToolbar(getIntent());
        controller.setImagePickerForAndroid(imagePickerForAndroid);


    }

    private void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.hide();
        }
        else
        {
            setSupportActionBar(screenView.getToolbar());
        }
    }

    private void initSelectionBundle()
    {
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            controller.bindSelectionBundle(bundle);
        }
        else
        {
            controller.bindSelectionBundle(new Bundle());
        }
    }

    private ImagePickerType getFilePickerType()
    {
        Intent intent = getIntent();
        ImagePickerType imagePickerType = ImagePickerType.FOLDER_LIST_FOR_IMAGE;
        if(intent!=null && intent.getExtras()!=null && intent.hasExtra(Tags.IMAGE_PICKER_TYPE))
        {
            imagePickerType = (ImagePickerType) intent.getExtras().get(Tags.IMAGE_PICKER_TYPE);
        }
        return imagePickerType;
    }

    @Override
    protected void onStart() {
        super.onStart();
        controller.onStart();
    }

    @Override
    protected void onStop() {
        controller.onStop();
        super.onStop();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        controller.initToolbarAction();

    }

    public ImagePickerActivityScreenView getScreenView() {
        return screenView;
    }

    public ImagePickerActivityController getController() {
        return controller;
    }

    public static void setImagePickerForAndroid(ImagePickerForAndroid imagePickerForAndroid)
    {
        ImagePickerActivity.imagePickerForAndroid = imagePickerForAndroid;
    }
}
