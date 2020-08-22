package com.shadattonmoy.imagepickerforandroid.ui.screenView;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.shadattonmoy.imagepickerforandroid.R;
import com.shadattonmoy.imagepickerforandroid.constants.Constants;
import com.shadattonmoy.imagepickerforandroid.constants.ImagePickerType;
import com.shadattonmoy.imagepickerforandroid.ui.screen.ImagePickerActivityScreen;

public class ImagePickerActivityScreenView extends BaseScreenView<ImagePickerActivityScreen.Listener> implements ImagePickerActivityScreen
{


    private Toolbar toolbar;
    private FrameLayout fragmentContainer;
    private Spinner fileOptionSpinner;
    private ImagePickerType imagePickerType;

    public ImagePickerActivityScreenView(LayoutInflater layoutInflater, @Nullable ViewGroup parent)
    {
        setRootView(layoutInflater.inflate(R.layout.image_picker_layout, parent, false));
        inflateUIElements();
        initUserInteractions();
    }


    @Override
    public void initUserInteractions()
    {
        fileOptionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                for(Listener listener:getListeners())
                {
                    listener.onSpinnerItemSelected(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

    }

    @Override
    public void inflateUIElements()
    {
        toolbar = findViewById(R.id.filepicker_toolbar);
        fragmentContainer = findViewById(R.id.fragment_container);
        toolbar.setTitle(Constants.EMPTY_TEXT);
        toolbar.setNavigationIcon(getContext().getResources().getDrawable(R.drawable.back_white));
        fileOptionSpinner = findViewById(R.id.toolbar_spinner);

    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void initToolbarAction()
    {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(ImagePickerActivityScreen.Listener listener:getListeners())
                    listener.onBackButtonClicked();
            }
        });
    }

    public FrameLayout getFragmentContainer() {
        return fragmentContainer;
    }

    public int getFragmentContainerID() {
        return R.id.fragment_container;
    }

    public Spinner getFileOptionSpinner() {
        return fileOptionSpinner;
    }

    public void setFilePickerType(ImagePickerType imagePickerType) {
        this.imagePickerType = imagePickerType;
    }
}
