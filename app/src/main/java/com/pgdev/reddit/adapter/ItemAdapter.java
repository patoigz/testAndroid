package com.pgdev.reddit.adapter;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pgdev.reddit.ItemsListFragment;
import com.pgdev.reddit.R;
import com.pgdev.reddit.model.Item;
import com.pgdev.reddit.util.TimeAgo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by patoi on 08-Feb-18.
 */

public class ItemAdapter extends ArrayAdapter<Item> {

    private ArrayList<Item> items;
    private Context context;

    private static class ViewHolder {
        TextView textViewAuthor;
        TextView textViewTitle;
        TextView textViewDate;
        TextView textViewNumComments;
        ImageView imageViewThumbnail;
        FloatingActionButton dismiss;
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
            viewHolder.textViewAuthor = (TextView) convertView.findViewById(R.id.text_author);
            viewHolder.textViewTitle = (TextView) convertView.findViewById(R.id.text_title);
            viewHolder.textViewDate = (TextView) convertView.findViewById(R.id.text_date);
            viewHolder.textViewNumComments = (TextView) convertView.findViewById(R.id.text_num_comments);
            viewHolder.imageViewThumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);
            viewHolder.dismiss = (FloatingActionButton) convertView.findViewById(R.id.dismiss);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textViewAuthor.setText(item.getAuthor());
        viewHolder.textViewTitle.setText(item.getTitle());
        viewHolder.textViewDate.setText(TimeAgo.fromNow(item.getCreated_utc()));
        viewHolder.textViewNumComments.setText(String.valueOf(item.getNum_comments()) + " comments");
        Picasso.with(context).load(item.getThumbnail()).into(viewHolder.imageViewThumbnail);

        if (item.isRead()) {
            convertView.setBackgroundResource(android.R.color.transparent);
        } else {
            convertView.setBackgroundResource(R.color.colorUnread);
        }

        final View tmpView = convertView;
        viewHolder.dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_out_right);
                animation.setDuration(500);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        ((ItemsListFragment.OnItemSelectedListener) context).dismissItem(item);
                    }
                });

                tmpView.startAnimation(animation);
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ItemsListFragment.OnItemSelectedListener) context).onItemSelected(item);

                item.setRead(true);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
