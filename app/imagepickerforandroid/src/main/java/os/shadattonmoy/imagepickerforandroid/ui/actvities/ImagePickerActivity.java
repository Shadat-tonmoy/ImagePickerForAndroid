package os.shadattonmoy.imagepickerforandroid.ui.actvities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import os.shadattonmoy.imagepickerforandroid.constants.Constants;
import os.shadattonmoy.imagepickerforandroid.constants.ImagePickerType;
import os.shadattonmoy.imagepickerforandroid.constants.Tags;
import os.shadattonmoy.imagepickerforandroid.controller.ImagePickerActivityController;
import os.shadattonmoy.imagepickerforandroid.ui.screenView.ImagePickerActivityScreenView;

public class ImagePickerActivity extends AppCompatActivity
{
    private static final String TAG = "ImagePickerActivity";

    private ImagePickerActivityController controller;
    private Activity activity;
    private ImagePickerActivityScreenView screenView;

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
        if(intent!=null && intent.getExtras()!=null && intent.hasExtra(Constants.IMAGE_PICKER_TYPE))
        {
            imagePickerType = (ImagePickerType) intent.getExtras().get(Constants.IMAGE_PICKER_TYPE);
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
}
