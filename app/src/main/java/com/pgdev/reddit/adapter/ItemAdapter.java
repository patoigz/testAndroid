package com.pgdev.reddit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.pgdev.reddit.R;
import com.pgdev.reddit.model.Item;

import java.util.ArrayList;

/**
 * Created by patoi on 08-Feb-18.
 */

public class ItemAdapter extends ArrayAdapter<Item> {

    private ArrayList<Item> items;
    private Context context;

    private static class ViewHolder {
        ImageView imageViewThumbnail;
    }

    public ItemAdapter(Context context, ArrayList<Item> items) {
        super(context, R.layout.fragment_item_detail, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Item item = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.fragment_item_detail, parent, false);
            viewHolder.imageViewThumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        
        viewHolder.imageViewThumbnail.setImageResource(R.mipmap.ic_launcher);

        return convertView;
    }
}
