package com.shadattonmoy.imagepickerforandroid.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shadattonmoy.imagepickerforandroid.controller.ImagePickerGridController;
import com.shadattonmoy.imagepickerforandroid.ui.screenView.ImagePickerGridScreenView;

public class ImagePickerGridFragment extends Fragment
{

    private ImagePickerGridScreenView imagePickerGridScreenView;
    private ImagePickerGridController imagePickerGridController;
    private Activity activity;


    public static ImagePickerGridFragment newInstance(Bundle args)
    {
        ImagePickerGridFragment fragment = new ImagePickerGridFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void init(ViewGroup container)
    {
        activity = requireActivity();
        imagePickerGridScreenView = new ImagePickerGridScreenView(activity.getLayoutInflater(),null);
        imagePickerGridController = new ImagePickerGridController(activity);
        imagePickerGridController.bindView(imagePickerGridScreenView);
        imagePickerGridController.bindArguments(getArguments());
    }
    
    
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        init(container);
        return imagePickerGridScreenView.getRootView();
    }

    @Override
    public void onStart() {
        super.onStart();
        imagePickerGridController.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
        imagePickerGridController.onStop();
    }
}
