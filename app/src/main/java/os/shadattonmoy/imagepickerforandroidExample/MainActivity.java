package os.shadattonmoy.imagepickerforandroidExample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import os.shadattonmoy.imagepickerforandroid.ImagePickerForAndroid;
import os.shadattonmoy.imagepickerforandroid.helpers.AppPermissionHelper;

public class MainActivity extends AppCompatActivity implements ImagePickerForAndroid.BatchImageSelectionListener, ImagePickerForAndroid.SingleImageSelectionListener
{

    private static final String TAG = "MainActivity";
    private Button singleImagePickerButton, batchImagePickerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        singleImagePickerButton = findViewById(R.id.open_button);
        batchImagePickerButton = findViewById(R.id.open_batch_button);
        singleImagePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImagePicker(false);
            }
        });

        batchImagePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImagePicker(true);
            }
        });
    }

    private void openImagePicker(boolean batchMode)
    {
        if(AppPermissionHelper.hasWriteExternalStoragePermission(this))
        {
            ImagePickerForAndroid imagePickerForAndroid = new ImagePickerForAndroid.Builder(this)
                    .toolbarColor(this.getResources().getColor(R.color.colorRed))
                    .statusBarColor(this.getResources().getColor(R.color.colorRed))
                    .navigationIcon(R.drawable.back_white_cupertino)
                    .batchMode(batchMode)
                    .batchImageSelectionListener(this)
                    .singleImageSelectionListener(this)
                    .build();
            imagePickerForAndroid.openImagePicker();
        }
    }

    @Override
    public void onBatchImageSelected(List<String> selectedImageList)
    {
        for (String selectedImage : selectedImageList)
        {
            Log.e(TAG, "onImageListSelected: " + selectedImage);
        }
    }

    @Override
    public void onSingleImageSelected(String selectedImage)
    {
        Log.e(TAG, "onSingleImageSelected: "+selectedImage);


    }
}
