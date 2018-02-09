package com.pgdev.reddit.util;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.pgdev.reddit.R;
import com.pgdev.reddit.model.Item;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by patoi on 09-Feb-18.
 */

public class Util {
    private static final String TAG = Util.class.getSimpleName();
    private static final Gson GSON = new Gson();

    public static ArrayList<Item> parseJson(Context context, JSONObject jsonObj) {
        ArrayList<Item> items = new ArrayList<>();
        try {

            JSONObject data = jsonObj.getJSONObject(context.getString(R.string.key_data));
            JSONArray children = data.getJSONArray(context.getString(R.string.key_children));
            for (int i = 0, l = children.length(); i < l; i++) {
                data = children.getJSONObject(i);
                Item item = GSON.fromJson(data.get(context.getString(R.string.key_data)).toString(), Item.class);
                items.add(item);
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
        return items;
    }
}
