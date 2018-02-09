package com.pgdev.reddit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pgdev.reddit.model.Item;
import com.pgdev.reddit.util.Constants;

/**
 * Created by patoi on 08-Feb-18.
 */

public class ItemDetailFragment extends Fragment {
    private static final String TAG = ItemDetailFragment.class.getSimpleName();
    private Item item;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        item = (Item) getArguments().getSerializable(Constants.ITEM);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_detail, container, false);
        return view;
    }

    public static ItemDetailFragment newInstance(Item item) {
        ItemDetailFragment itemDetailFragment = new ItemDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.ITEM, item);
        itemDetailFragment.setArguments(args);
        return itemDetailFragment;
    }
}
