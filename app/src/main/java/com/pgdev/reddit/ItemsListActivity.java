package com.pgdev.reddit;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.FrameLayout;

/**
 * Created by patoi on 08-Feb-18.
 */

public class ItemsListActivity extends FragmentActivity {
    private boolean isTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        determinePaneLayout();
    }

    private void determinePaneLayout() {
        FrameLayout fragmentItemDetail = (FrameLayout) findViewById(R.id.flDetailContainer);
        if (fragmentItemDetail != null) {
            isTwoPane = true;
            ItemsListFragment fragmentItemsList = (ItemsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentItemsList);
            fragmentItemsList.setActivateOnItemClick(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.items, menu);
        return true;
    }
}
