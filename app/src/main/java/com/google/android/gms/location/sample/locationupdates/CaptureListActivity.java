package com.google.android.gms.location.sample.locationupdates;


import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.location.sample.locationupdates.PokemonContract.*;

public class CaptureListActivity extends ListActivity{

    // This is the Adapter being used to display the list's data
    SimpleCursorAdapter mAdapter;
    Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mActivity = this;

        String[] queryColumns = {CaptureList._ID,
                CaptureList.NAME,
                CaptureList.RANK,
                CaptureList.LEVEL,
                CaptureList.TYPE_ID};
        String[] fromColumns = {CaptureList.NAME,
                                CaptureList.NAME,
                                CaptureList.RANK,
                                CaptureList.LEVEL,
                                CaptureList.TYPE_ID};
        int[] toViews = {R.id.pokemon_image,
                        R.id.pokemon_name,
                        R.id.pokemon_rank,
                        R.id.pokemon_level,
                        R.id.pokemon_type};

        SimpleCursorAdapter.ViewBinder viewBinder = new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                int id = view.getId();

                if(id == R.id.pokemon_image){
                    String pokemonName = cursor.getString(columnIndex);
                    Drawable drawable = Data.findImageByName(pokemonName, mActivity);
                    ImageView imageView = (ImageView) view;
                    imageView.setImageDrawable(drawable);
                    return true;
                }else if (id == R.id.pokemon_rank){
                    TextView textView = (TextView)view;
                    int rank = cursor.getInt(columnIndex);
                    textView.setText("Rank : "+rank);
                    return true;
                }else if (id == R.id.pokemon_level){
                    TextView textView = (TextView)view;
                    int level = cursor.getInt(columnIndex);
                    textView.setText("Level : "+level);
                    return true;
                }else if (id == R.id.pokemon_type){
                    TextView textView = (TextView)view;
                    int typeID = cursor.getInt(columnIndex);
                    String type="Unknown";
                    switch (typeID){
                        case 1:type="Fire";
                            break;
                        case 2:type="Grass";
                            break;
                        case 3:type="Water";
                            break;
                        case 4:type="Rock";
                            break;
                        case 5:type="Electric";
                            break;
                    }
                    textView.setText("Type : "+type);
                    return true;
                }else {
                    return false;
                }
            }
        };

        SQLiteDatabase db = (new PokemonDatabase(this)).getReadableDatabase();
        Cursor cursor = db.query(CaptureList.TABLE_NAME,
                queryColumns,
                null,
                null,
                null,
                null,
                null);

        // Create an empty adapter we will use to display the loaded data.
        // We pass null for the cursor, then update it in onLoadFinished()
        mAdapter = new SimpleCursorAdapter(this,
                R.layout.simple_list_item, cursor,
                fromColumns, toViews, 0);
        mAdapter.setViewBinder(viewBinder);
        setListAdapter(mAdapter);
    }


}
