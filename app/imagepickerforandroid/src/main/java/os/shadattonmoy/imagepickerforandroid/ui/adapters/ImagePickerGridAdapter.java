package os.shadattonmoy.imagepickerforandroid.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import os.shadattonmoy.imagepickerforandroid.R;
import os.shadattonmoy.imagepickerforandroid.constants.Constants;


public class ImagePickerGridAdapter extends BaseAdapter
{

    public class ViewHolder {
        ImageView imageView;
        TextView selectionCountView;
    }


    private Context context;
    private List<String> imagePaths, selectedImages;
    private Map<Integer, Boolean> selectedPositions;
    private Map<Integer, Integer> selectionIds;
    private boolean selectMultiple = false;
    private int selectionCount = 1;

    public ImagePickerGridAdapter(Context context) {
        this.context = context;
        this.imagePaths = new ArrayList<>();
        this.selectedImages = new ArrayList<>();
        this.selectedPositions = new HashMap<>();
        this.selectionIds= new HashMap<>();
    }

    public int getCount() {
        if(imagePaths!=null)
            return imagePaths.size();
        else return 0;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent)
    {

        View cell = convertView;
        ViewHolder viewHolder;
        if(cell==null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            cell = inflater.inflate(R.layout.file_picker_single_cell,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.imageView=  cell.findViewById(R.id.image_view);
            /*viewHolder.foreGroundView =  cell.findViewById(R.id.image_selection_foreground);*/
            viewHolder.selectionCountView =  cell.findViewById(R.id.selection_count_view);
            cell.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Glide.with(context).load(imagePaths.get(position))
                .into(viewHolder.imageView);
        if(selectedPositions!=null && selectedPositions.get(position)!=null && selectedPositions.get(position))
        {
            if(selectionIds!=null && selectionIds.get(position)!=null)
            {

                viewHolder.selectionCountView.setText(selectionIds.get(position)+ Constants.EMPTY_TEXT);
                viewHolder.selectionCountView.setBackgroundResource(R.drawable.circle_background_blue);
            }
        }
        else
        {
            viewHolder.selectionCountView.setBackgroundResource(R.drawable.circle_background_trans);
            viewHolder.selectionCountView.setText(Constants.EMPTY_TEXT);

        }
        return cell;
    }

    public void bindImages(List<String> imagePaths)
    {
        this.imagePaths = imagePaths;
        notifyDataSetChanged();
    }

    public void selectItemAtPosition(int position)
    {
        boolean currentSelection = true;
        if(selectedPositions.get(position)!=null)
        {
            currentSelection = selectedPositions.get(position);
            currentSelection = !currentSelection;
        }
        selectedPositions.put(position,currentSelection);
        String pathAtPosition = imagePaths.get(position);
        if(currentSelection)
        {
            if(!selectedImages.contains(pathAtPosition))
            {
                selectedImages.add(pathAtPosition);
            }
            incrementSelectionCount(position);
        }
        else
        {
            if(selectedImages.contains(pathAtPosition))
            {
                selectedImages.remove(pathAtPosition);
            }
            decrementSelectionCount(position);
        }
        notifyDataSetChanged();
    }

    private void incrementSelectionCount(int position)
    {
        selectionIds.put(position,selectionCount++);
    }

    private void decrementSelectionCount(int position)
    {
        if(selectionIds!=null && selectionIds.get(position)!=null)
        {
            int selectionCountAtPosition = selectionIds.get(position);
            for(Map.Entry<Integer, Integer> entry : selectionIds.entrySet())
            {
                int key = entry.getKey();
                int value = entry.getValue();
                if(value > selectionCountAtPosition)
                {
                    selectionIds.put(key,value-1);
                }
            }
            selectionCount--;
        }
    }

    public List<String> getSelectedImages() {
        return selectedImages;
    }

    public int getTotalSelectedImages()
    {
        if(selectedImages==null)
            return 0;
        else return selectedImages.size();
    }

    public void selectAll()
    {
        selectedImages = new ArrayList<>();
        selectionIds = new HashMap<>();
        for(int i=0;i<imagePaths.size();i++)
        {
            selectedImages.add(imagePaths.get(i));
            selectedPositions.put(i,true);
            selectionIds.put(i,i+1);
            selectionCount = i+1;
        }
        selectionCount++;
        notifyDataSetChanged();

    }

    public void selectNone()
    {
        selectedImages = new ArrayList<>();
        selectedPositions = new HashMap<>();
        selectionIds = new HashMap<>();
        resetSelectionCount();
        notifyDataSetChanged();
    }

    public void selectFromBundle(Bundle bundle)
    {
        ArrayList<String> selectedImages = (ArrayList<String>) bundle.getSerializable(Constants.SELECTED_IMAGES);
        HashMap<Integer, Boolean> selectedPositions = (HashMap<Integer, Boolean>) bundle.getSerializable(Constants.SELECTED_IMAGES_POSITIONS);
        HashMap<Integer, Integer> selectionIds = (HashMap<Integer, Integer>) bundle.getSerializable(Constants.SELECTED_IMAGES_IDS);
        this.selectedImages = selectedImages == null ? new ArrayList<String>() : selectedImages;
        this.selectedPositions = selectedPositions == null ? new HashMap<Integer, Boolean>() : selectedPositions;
        this.selectionIds = selectionIds == null ? new HashMap<Integer, Integer>() : selectionIds;
        if(selectedImages!=null)
            this.selectionCount = selectedImages.size()+1;
        /*resetSelectionCount();*/
        notifyDataSetChanged();
    }

    public void setSelectMultiple(boolean selectMultiple)
    {
        this.selectMultiple = selectMultiple;
        if(!selectMultiple)
            selectNone();
        resetSelectionCount();
    }

    public List<String> getImagePaths() {
        return imagePaths;
    }

    public void resetSelectionCount()
    {
        selectionCount = 1;
    }
}
