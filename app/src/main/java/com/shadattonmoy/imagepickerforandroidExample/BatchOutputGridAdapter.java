package com.shadattonmoy.imagepickerforandroidExample;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class BatchOutputGridAdapter extends RecyclerView.Adapter<BatchOutputGridAdapter.OutputViewHolder>
{

    private List<String> imagePaths;
    private Activity activity;

    public BatchOutputGridAdapter(Activity activity) {
        this.activity = activity;
    }

    class OutputViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;

        public OutputViewHolder(@NonNull View itemView)
        {
            super(itemView);
            imageView = itemView.findViewById(R.id.output_image);
        }
    }

    @NonNull
    @Override
    public OutputViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = activity.getLayoutInflater().inflate(R.layout.batch_output_single_cell,parent,false);
        return new OutputViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OutputViewHolder holder, int position)
    {
        String imagePath = imagePaths.get(position);
        Glide.with(activity)
                .load(imagePath)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount()
    {
        if(imagePaths!=null)
            return imagePaths.size();
        return 0;
    }

    public void bindImagePaths(List<String> imagePaths) {
        this.imagePaths = imagePaths;
        notifyDataSetChanged();
    }
}
