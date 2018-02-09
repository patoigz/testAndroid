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
import com.pgdev.reddit.util.HttpHandler;
import com.pgdev.reddit.util.Util;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by patoi on 08-Feb-18.
 */

public class ItemsListFragment extends Fragment {
    private static final String TAG = ItemsListFragment.class.getSimpleName();

    public interface OnItemSelectedListener {
        void onItemSelected(Item item);

        void dismissItem(Item item);
    }

    private ProgressDialog pDialog;
    private ListView listView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ArrayAdapter<Item> adapterItems;
    private ArrayList<Item> items = null;
    private int MIN = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void populateAdapter() {
        if (items == null) return;

        int value = Math.min((MIN + getResources().getInteger(R.integer.entries)), items.size());
        adapterItems = new ItemAdapter(getActivity(), new ArrayList<>(items.subList(MIN, value)));
        MIN += getResources().getInteger(R.integer.entries);
        if (MIN >= items.size()) {
            MIN = 0;
        }
        listView.setAdapter(adapterItems);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_items_list, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        listView = (ListView) view.findViewById(R.id.list_view);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });

        if (items == null) {
            new getTopFromReddit().execute();
        }
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
            String jsonStr = httpHandler.makeServiceCall(getString(R.string.url));
            try {
                if (jsonStr != null) {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    Log.i(TAG, jsonStr.substring(0, 100));

                    items = Util.parseJson(getActivity(), jsonObj);
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
            if (result) {
                populateAdapter();
            }
        }
    }

    public void removeItem(Item item) {
        adapterItems.remove(item);
        adapterItems.notifyDataSetChanged();
    }

    public void removeAll() {
        adapterItems = new ItemAdapter(getActivity(), new ArrayList<Item>());
        listView.setAdapter(adapterItems);
        adapterItems.notifyDataSetChanged();
    }
}
