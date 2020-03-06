package os.shadattonmoy.imagepickerforandroid;

import android.app.Activity;
import android.content.Intent;

import os.shadattonmoy.imagepickerforandroid.ui.actvities.ImagePickerActivity;

public class ImagePickerForAndroid
{
    public static void openImagePicker(Activity activity)
    {
        Intent intent = new Intent(activity, ImagePickerActivity.class);
        activity.startActivity(intent);
    }
}
