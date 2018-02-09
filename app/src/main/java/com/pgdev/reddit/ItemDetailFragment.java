package com.pgdev.reddit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pgdev.reddit.model.Item;
import com.pgdev.reddit.util.TimeAgo;
import com.squareup.picasso.Picasso;

/**
 * Created by patoi on 08-Feb-18.
 */

public class ItemDetailFragment extends Fragment {
    private static final String TAG = ItemDetailFragment.class.getSimpleName();
    private Item item;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        item = (Item) getArguments().getSerializable(getActivity().getString(R.string.key_item));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_detail, container, false);
        TextView textViewAuthor = (TextView) view.findViewById(R.id.text_author);
        TextView textViewTitle = (TextView) view.findViewById(R.id.text_title);
        TextView textViewDate = (TextView) view.findViewById(R.id.text_date);
        TextView textViewComments = (TextView) view.findViewById(R.id.text_num_comments);
        ImageView imageViewThumbnail = (ImageView) view.findViewById(R.id.thumbnail);

        FloatingActionButton button = (FloatingActionButton) view.findViewById(R.id.dismiss);
        button.setVisibility(View.GONE);

        textViewAuthor.setText(item.getAuthor());
        textViewTitle.setText(item.getTitle());
        textViewDate.setText(TimeAgo.fromNow(item.getCreated_utc()));
        textViewComments.setText(String.valueOf(item.getNum_comments()) + " comments");
        Picasso.with(getActivity()).load(item.getThumbnail()).into(imageViewThumbnail);

        imageViewThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = item.getThumbnail();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        return view;
    }

    public static ItemDetailFragment newInstance(Item item, String key) {
        ItemDetailFragment itemDetailFragment = new ItemDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(key, item);
        itemDetailFragment.setArguments(args);
        return itemDetailFragment;
    }
}
