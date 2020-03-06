package os.shadattonmoy.imagepickerforandroid.tasks.uiUpdateTasks;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TabHost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import os.shadattonmoy.imagepickerforandroid.R;
import os.shadattonmoy.imagepickerforandroid.constants.Constants;
import os.shadattonmoy.imagepickerforandroid.constants.Tags;
import os.shadattonmoy.imagepickerforandroid.ui.screenView.ImagePickerActivityScreenView;

import static os.shadattonmoy.imagepickerforandroid.constants.Constants.INVALID;

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

    public void setupToolbar(Intent toolbarProperties)
    {
        int toolbarColor = toolbarProperties.getIntExtra(Tags.TOOLBAR_COLOR,INVALID);
        int navigationIcon = toolbarProperties.getIntExtra(Tags.NAVIGATION_ICON,INVALID);
        if(toolbarColor!=-1)
            screenView.getToolbar().setBackgroundColor(toolbarColor);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            int statusBarColor = toolbarProperties.getIntExtra(Tags.STATUS_BAR_COLOR, INVALID);
            if(statusBarColor!= INVALID)
            {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(statusBarColor);
            }
        }
        screenView.getToolbar().setNavigationIcon(activity.getResources().getDrawable(navigationIcon));
    }
}
