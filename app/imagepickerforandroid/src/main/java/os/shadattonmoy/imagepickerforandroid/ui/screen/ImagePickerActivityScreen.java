package os.shadattonmoy.imagepickerforandroid.ui.screen;

public interface ImagePickerActivityScreen {

    interface Listener
    {
        void onBackButtonClicked();

        void onSelectAllButtonClicked();

        void onDoneButtonClicked();

        void onSpinnerItemSelected(int position);
    }
}
