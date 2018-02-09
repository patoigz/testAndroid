package com.pgdev.reddit;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

import com.pgdev.reddit.model.Item;
import com.pgdev.reddit.util.Constants;

/**
 * Created by patoi on 08-Feb-18.
 */

public class ItemDetailActivity extends FragmentActivity {
    private static final String TAG = ItemDetailActivity.class.getSimpleName();
    private ItemDetailFragment fragmentItemDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Item item = (Item) getIntent().getSerializableExtra(Constants.ITEM);
        if (savedInstanceState == null) {
            fragmentItemDetail = ItemDetailFragment.newInstance(item);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.flDetailContainer, fragmentItemDetail);
            fragmentTransaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_detail, menu);
        return true;
    }

}
