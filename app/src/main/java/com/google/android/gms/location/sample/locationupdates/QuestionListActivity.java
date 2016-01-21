package com.google.android.gms.location.sample.locationupdates;


import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.location.sample.locationupdates.PokemonContract.*;

public class QuestionListActivity extends ListActivity{

    // This is the Adapter being used to display the list's data
    SimpleCursorAdapter mAdapter;
    Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mActivity = this;

        String[] queryColumns = {QuestionList._ID,
                                QuestionList.QNO,
                                QuestionList.ANSWER,
                                QuestionList.SCORE};
        String[] fromColumns = {QuestionList.QNO,
                QuestionList.ANSWER,
                QuestionList.SCORE};

        int[] toViews = {R.id.q_no,
                R.id.q_answer,
                R.id.q_score};

        SQLiteDatabase db = (new PokemonDatabase(this)).getReadableDatabase();
        Cursor cursor = db.query(QuestionList.TABLE_NAME,
                queryColumns,
                null,
                null,
                null,
                null,
                null);

        // Create an empty adapter we will use to display the loaded data.
        // We pass null for the cursor, then update it in onLoadFinished()
        mAdapter = new SimpleCursorAdapter(this,
                R.layout.question_list_item, cursor,
                fromColumns, toViews, 0);
        setListAdapter(mAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String temp = ((TextView)v.findViewById(R.id.pokemon_rank)).getText().toString();
        int rank = Integer.parseInt(temp.substring(temp.lastIndexOf(" ")+1));
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("pokemon_rank", rank);
        startActivity(intent);
    }
}
