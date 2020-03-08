package com.shadattonmoy.imagepickerforandroid.ui.screen;

public interface ImagePickerActivityScreen {

    interface Listener
    {
        void onBackButtonClicked();

        void onSpinnerItemSelected(int position);

    }
}
