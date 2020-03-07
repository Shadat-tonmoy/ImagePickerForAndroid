package os.shadattonmoy.imagepickerforandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.List;

import os.shadattonmoy.imagepickerforandroid.constants.Tags;
import os.shadattonmoy.imagepickerforandroid.ui.actvities.ImagePickerActivity;

import static os.shadattonmoy.imagepickerforandroid.constants.Constants.INVALID;

public class ImagePickerForAndroid implements Parcelable
{

    public interface ImageSelectionListener extends Serializable
    {
        void onImageSelected(List<String> selectedImageList);
    }

    private static final String TAG = "ImagePickerForAndroid";
    private int toolbarColor = INVALID;
    private int statusBarColor = INVALID;
    private int navigationIcon = INVALID;
    private boolean isBatchModeEnabled = false;
    private ImageSelectionListener listener;
    private Context context;


    protected ImagePickerForAndroid(Parcel in) {
        toolbarColor = in.readInt();
        statusBarColor = in.readInt();
        navigationIcon = in.readInt();
        isBatchModeEnabled = in.readByte() != 0;
    }

    public static final Creator<ImagePickerForAndroid> CREATOR = new Creator<ImagePickerForAndroid>() {
        @Override
        public ImagePickerForAndroid createFromParcel(Parcel in) {
            return new ImagePickerForAndroid(in);
        }

        @Override
        public ImagePickerForAndroid[] newArray(int size) {
            return new ImagePickerForAndroid[size];
        }
    };

    public void openImagePicker()
    {
        Intent intent = new Intent(context, ImagePickerActivity.class);
        intent.putExtra(Tags.TOOLBAR_COLOR,toolbarColor);
        intent.putExtra(Tags.STATUS_BAR_COLOR,statusBarColor);
        intent.putExtra(Tags.NAVIGATION_ICON,navigationIcon);
        intent.putExtra(Tags.BATCH_MODE_ENABLED,isBatchModeEnabled);
        intent.putExtra(Tags.IMAGE_PICKER_FOR_ANDROID,this);
        context.startActivity(intent);
    }

    private ImagePickerForAndroid(Builder builder)
    {
        this.toolbarColor = builder.toolbarColor;
        this.statusBarColor = builder.statusBarColor;
        this.navigationIcon = builder.navigationIcon;
        this.isBatchModeEnabled = builder.isBatchModeEnabled;
        this.listener = builder.imageSelectionListener;
        this.context = builder.context;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(toolbarColor);
        parcel.writeInt(statusBarColor);
        parcel.writeInt(navigationIcon);
        parcel.writeByte((byte) (isBatchModeEnabled ? 1 : 0));
    }


    public static class Builder
    {
        private int toolbarColor,statusBarColor,navigationIcon;
        private boolean isBatchModeEnabled;
        private ImageSelectionListener imageSelectionListener;
        private Context context;

        public Builder(Context context)
        {
            this.context = context;
            toolbarColor = INVALID;
            statusBarColor = INVALID;
            navigationIcon = INVALID;
            isBatchModeEnabled = false;
        }

        public Builder toolbarColor(int toolbarColor)
        {
            this.toolbarColor = toolbarColor;
            return this;
        }

        public Builder statusBarColor(int statusBarColor)
        {
            this.statusBarColor = statusBarColor;
            return this;
        }

        public Builder navigationIcon(int navigationIcon)
        {
            this.navigationIcon = navigationIcon;
            return this;
        }

        public Builder enableBatchMode()
        {
            this.isBatchModeEnabled = true;
            return this;
        }

        public Builder setImageSelectionListener(ImageSelectionListener listener)
        {
            this.imageSelectionListener = listener;
            return this;
        }

        public ImagePickerForAndroid build()
        {
            return new ImagePickerForAndroid(this);
        }
    }


    public void onImageListSelected(List<String> selectedImageList)
    {
        Log.e(TAG, "onImageListSelected: listener "+listener);
        if(listener!=null)
            listener.onImageSelected(selectedImageList);
    }
}
