package com.example.rmendoza.birderjourney_gce;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.BirdArrayItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by rodrigoshariff on 4/2/2016.
 */
public class ImageAndTextArrayAdapter extends ArrayAdapter<BirdArrayItem> {

    private final Activity context;

    public ImageAndTextArrayAdapter(Activity context, int resourceId, List<BirdArrayItem> items) {
        super(context, resourceId, items);
        this.context= context;

    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        BirdArrayItem rowItemBird = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_search_bird, null);
            holder = new ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.list_item_search_bird_name);
            holder.imageView = (ImageView) convertView.findViewById(R.id.list_item_search_bird_image);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.txtTitle.setText(rowItemBird.getFullName());
        String ImageURL = "";
        if (rowItemBird.getImageID().equals("TBD"))
        {
            ImageURL = "http://vignette2.wikia.nocookie.net/legendmarielu/images/b/b4/No_image_available.jpg/revision/latest?cb=20130511180903";
        }
        else
        {
            ImageURL = "https://rodrigoshariff.smugmug.com/Bird/Birder-Journey/" + rowItemBird.getImageID() + "/0/Th/" + rowItemBird.getImageFileName();
        }

        Picasso.with(context).load(ImageURL).into(holder.imageView);

        return convertView;
    }
}