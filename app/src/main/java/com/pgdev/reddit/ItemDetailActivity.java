package com.pgdev.reddit;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.pgdev.reddit.model.Item;

/**
 * Created by patoi on 08-Feb-18.
 */

public class ItemDetailActivity extends AppCompatActivity {
    private static final String TAG = ItemDetailActivity.class.getSimpleName();
    private ItemDetailFragment fragmentItemDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Item item = (Item) getIntent().getSerializableExtra(getString(R.string.key_item));
        if (savedInstanceState == null) {
            fragmentItemDetail = ItemDetailFragment.newInstance(item, getString(R.string.key_item));
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.flDetailContainer, fragmentItemDetail);
            fragmentTransaction.commit();
        }
    }
}
