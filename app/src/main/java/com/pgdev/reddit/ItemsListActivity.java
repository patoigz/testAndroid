package com.pgdev.reddit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.pgdev.reddit.model.Item;

/**
 * Created by patoi on 08-Feb-18.
 */

public class ItemsListActivity extends AppCompatActivity implements ItemsListFragment.OnItemSelectedListener {
    private boolean isTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        determinePaneLayout();
    }

    private void determinePaneLayout() {
        FrameLayout fragmentItemDetail = (FrameLayout) findViewById(R.id.flDetailContainer);
        if (fragmentItemDetail != null) {
            isTwoPane = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_dismiss) {
            if (isTwoPane) {
                ItemsListFragment fragmentItemsList = (ItemsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentItemsList);
                fragmentItemsList.removeAll();

                removeDetailFragment();
            } else {
                ItemsListFragment fragmentItemsList = (ItemsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_items);
                fragmentItemsList.removeAll();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void removeDetailFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ItemDetailFragment ItemDetailFragment = (ItemDetailFragment) getSupportFragmentManager().findFragmentById(R.id.flDetailContainer);
        if (ItemDetailFragment != null) {
            fragmentTransaction.remove(ItemDetailFragment);
            fragmentTransaction.commit();
        }
    }


    @Override
    public void onItemSelected(Item item) {
        if (isTwoPane) {
            ItemDetailFragment fragmentItem = ItemDetailFragment.newInstance(item, getString(R.string.key_item));
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.flDetailContainer, fragmentItem);
            fragmentTransaction.commit();
        } else {
            Intent intent = new Intent(this, ItemDetailActivity.class);
            intent.putExtra(getString(R.string.key_item), item);
            startActivity(intent);
        }
    }

    @Override
    public void dismissItem(Item item) {
        if (isTwoPane) {
            ItemsListFragment fragmentItemsList = (ItemsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentItemsList);
            fragmentItemsList.removeItem(item);

            removeDetailFragment();
        } else {
            ItemsListFragment fragmentItemsList = (ItemsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_items);
            fragmentItemsList.removeItem(item);
        }
    }
}
