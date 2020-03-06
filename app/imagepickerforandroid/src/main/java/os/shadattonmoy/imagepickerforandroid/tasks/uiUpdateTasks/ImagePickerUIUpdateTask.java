package os.shadattonmoy.imagepickerforandroid.tasks.uiUpdateTasks;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import os.shadattonmoy.imagepickerforandroid.R;
import os.shadattonmoy.imagepickerforandroid.ui.screenView.ImagePickerActivityScreenView;

public class ImagePickerUIUpdateTask
{
    private Activity activity;
    private ImagePickerActivityScreenView screenView;

    public ImagePickerUIUpdateTask(Activity activity)
    {
        this.activity = activity;
    }

    public void bindView(ImagePickerActivityScreenView screenView) {
        this.screenView= screenView;
    }

    public void setToolbarSpinner()
    {
        Spinner spinner = screenView.getFileOptionSpinner();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity,
                R.array.image_filepicker_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


    public void replaceFragment(Fragment fragment, String fragmentTag)
    {
        FragmentManager fragmentManager = ((AppCompatActivity)activity).getSupportFragmentManager();
        fragmentManager.popBackStackImmediate();

        fragmentManager.beginTransaction()
                .replace(screenView.getFragmentContainerID(),fragment,fragmentTag)
                .commitAllowingStateLoss();

    }

    public void onBackButtonClicked()
    {
        FragmentManager fragmentManager = ((AppCompatActivity)activity).getSupportFragmentManager();
        if(fragmentManager.getBackStackEntryCount()==0)
            activity.finish();
        else fragmentManager.popBackStack();
    }

}
