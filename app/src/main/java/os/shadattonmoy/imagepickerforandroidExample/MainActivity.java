package os.shadattonmoy.imagepickerforandroidExample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import os.shadattonmoy.imagepickerforandroid.ImagePickerForAndroid;
import os.shadattonmoy.imagepickerforandroid.helpers.AppPermissionHelper;
import os.shadattonmoy.imagepickerforandroidExample.R;

public class MainActivity extends AppCompatActivity
{

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.open_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImagePicker();
            }
        });





    }

    private void openImagePicker()
    {
        if(AppPermissionHelper.hasWriteExternalStoragePermission(this))
        {
            ImagePickerForAndroid imagePickerForAndroid = new ImagePickerForAndroid.Builder(this)
                    .toolbarColor(this.getResources().getColor(R.color.colorRed))
                    .statusBarColor(this.getResources().getColor(R.color.colorRed))
                    .navigationIcon(R.drawable.back_white_cupertino)
                    .enableBatchMode()
                    .build();
            imagePickerForAndroid.openImagePicker();



        }
    }
}
