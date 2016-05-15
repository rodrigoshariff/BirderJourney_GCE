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
        TextView txtFullName;
        TextView txtFamily;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        BirdArrayItem rowItemBird = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_search_bird, null);
            holder = new ViewHolder();
            holder.txtFullName = (TextView) convertView.findViewById(R.id.list_item_search_bird_name);
            holder.txtFamily = (TextView) convertView.findViewById(R.id.list_item_search_bird_family);
            holder.imageView = (ImageView) convertView.findViewById(R.id.list_item_search_bird_image);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.txtFullName.setText(rowItemBird.getFullName());
        holder.txtFamily.setText(rowItemBird.getFamily());
        String ImageURL = "";
        if (rowItemBird.getImageID().equals("TBD"))
        {
            //ImageURL = "http://vignette2.wikia.nocookie.net/legendmarielu/images/b/b4/No_image_available.jpg/revision/latest?cb=20130511180903";
            holder.imageView.setImageResource(R.drawable.no_image_available);
        }
        else
        {
            ImageURL = "https://rodrigoshariff.smugmug.com/Bird/Birder-Journey/" + rowItemBird.getImageID() + "/0/Th/" + rowItemBird.getImageFileName();
            Picasso.with(context).load(ImageURL).into(holder.imageView);
        }



        return convertView;
    }
}