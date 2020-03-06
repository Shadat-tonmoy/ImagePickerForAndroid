package os.shadattonmoy.imagepickerforandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import os.shadattonmoy.imagepickerforandroid.constants.Tags;
import os.shadattonmoy.imagepickerforandroid.ui.actvities.ImagePickerActivity;

public class ImagePickerForAndroid
{
    private int toolbarColor = -1;
    private Context context;
    public void openImagePicker()
    {
        Intent intent = new Intent(context, ImagePickerActivity.class);
        intent.putExtra(Tags.TOOLBAR_COLOR,toolbarColor);
        context.startActivity(intent);
    }

    private ImagePickerForAndroid(Builder builder)
    {
        this.toolbarColor = builder.toolbarColor;
        this.context = builder.context;
    }

    public static class Builder
    {
        private int toolbarColor;
        private Context context;

        public Builder(Context context)
        {
            this.context = context;
        }

        public Builder toolbarColor(int toolbarColor)
        {
            this.toolbarColor = toolbarColor;
            return this;
        }

        public ImagePickerForAndroid build()
        {
            return new ImagePickerForAndroid(this);
        }
    }
}
