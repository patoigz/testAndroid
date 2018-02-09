package com.pgdev.reddit;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.pgdev.reddit.adapter.ItemAdapter;
import com.pgdev.reddit.model.Item;
import com.pgdev.reddit.util.Constants;
import com.pgdev.reddit.util.HttpHandler;

import java.util.ArrayList;

/**
 * Created by patoi on 08-Feb-18.
 */

public class ItemsListFragment extends Fragment {
    private static final String TAG = ItemsListFragment.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView listView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new getTopFromReddit().execute();
    }

    private void populateAdapter() {
        ArrayList<Item> entryItems = new ArrayList<>();
        entryItems.add(new Item("title", "author", 2, "thumbnail", 1));
        ArrayAdapter<Item> adapterItems = new ItemAdapter(getActivity(), entryItems);

        listView.setAdapter(adapterItems);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_items_list, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        listView = (ListView) view.findViewById(R.id.list_view);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });

        populateAdapter();
        return view;
    }

    private void refreshContent() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                populateAdapter();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

    public void setActivateOnItemClick(boolean activateOnItemClick) {
        listView.setChoiceMode(activateOnItemClick ? ListView.CHOICE_MODE_SINGLE : ListView.CHOICE_MODE_NONE);
    }


    private class getTopFromReddit extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... arg0) {
            HttpHandler httpHandler = new HttpHandler();
            String jsonStr = httpHandler.makeServiceCall(Constants.URL);
            try {
                if (jsonStr != null) {
                    Log.i(TAG, jsonStr.toString());

                    return true;
                }
            } catch (Exception ex) {
                Log.e(TAG, ex.getMessage());
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
        }
    }
}
