package os.shadattonmoy.imagepickerforandroidExample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import os.shadattonmoy.imagepickerforandroid.ImagePickerForAndroid;
import os.shadattonmoy.imagepickerforandroid.helpers.AppPermissionHelper;

public class MainActivity extends AppCompatActivity implements ImagePickerForAndroid.BatchImageSelectionListener, ImagePickerForAndroid.SingleImageSelectionListener
{

    private static final String TAG = "MainActivity";
    private Button singleImagePickerButton, batchImagePickerButton;
    private LinearLayout singleModeOutput, batchModeOutput;
    private ImageView singleModeOutputImageView;
    private TextView outputTextView;
    private RecyclerView batchOutputRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        singleImagePickerButton = findViewById(R.id.open_button);
        batchImagePickerButton = findViewById(R.id.open_batch_button);
        singleModeOutput = findViewById(R.id.single_mode_output);
        outputTextView = findViewById(R.id.textView);
        singleModeOutputImageView = findViewById(R.id.single_mode_output_image);
        batchModeOutput = findViewById(R.id.batch_mode_output);
        batchOutputRecyclerView = findViewById(R.id.batch_mode_output_recycler_view);
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
        batchModeOutput.setVisibility(View.VISIBLE);
        singleModeOutput.setVisibility(View.GONE);
        outputTextView.setVisibility(View.GONE);
        BatchOutputGridAdapter batchOutputGridAdapter = new BatchOutputGridAdapter(this);
        batchOutputRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        batchOutputGridAdapter.bindImagePaths(selectedImageList);
        batchOutputRecyclerView.setAdapter(batchOutputGridAdapter);
        for (String selectedImage : selectedImageList)
        {
            Log.e(TAG, "onImageListSelected: " + selectedImage);
        }
    }

    @Override
    public void onSingleImageSelected(String selectedImage)
    {
        batchModeOutput.setVisibility(View.GONE);
        singleModeOutput.setVisibility(View.VISIBLE);
        outputTextView.setVisibility(View.GONE);
        Glide.with(this)
                .load(selectedImage)
                .into(singleModeOutputImageView);
        Log.e(TAG, "onSingleImageSelected: "+selectedImage);


    }
}
