package com.google.android.gms.location.sample.locationupdates;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.location.sample.locationupdates.PokemonContract.*;

import java.util.Date;

public class Pokeball extends ActionBarActivity {

    Intent mIntent;
    String mPokemonName;
    int mPokemonRank;
    Data mData = new Data();
    SQLiteDatabase mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        mIntent = getIntent();
        int action=0;
        if(mIntent.hasExtra("pokemon")){
            mPokemonName = mIntent.getStringExtra("pokemon");
            mPokemonRank = mIntent.getIntExtra("rank", 0);
            action = mData.action.get(mPokemonRank+"_1");
            Log.i("question", "for "+mPokemonRank+"_1");
        }
        if(mIntent.hasExtra("q_no")){
            action = mIntent.getIntExtra("q_no", 0);
        }
        mDB = (new PokemonDatabase(this)).getWritableDatabase();
        String[] queryColumn = {QuestionList.QUESTION,
                                QuestionList.IMAGE,
                                QuestionList.ANSWER,
                                QuestionList.SCORE};

        Cursor cursor = mDB.query(QuestionList.TABLE_NAME,
                queryColumn,
                QuestionList.QNO+"=?",
                new String[] {action+""},
                null,
                null,
                null);
        if(cursor.getCount()!=1){
            Log.i("question", "number of questions not equal to 1");
        }else{
            Log.i("question", cursor.getString(0)+" qno "+action);
            cursor.moveToFirst();
            int score = cursor.getInt(3);
            int layoutID=0;
            if (score==8){
                switch (action){
                    case 101:layoutID = R.layout.canteen;
                        break;
                    case 102:layoutID = R.layout.cc;
                        break;
                    case 103:layoutID = R.layout.ground;
                        break;
                    case 104:layoutID = R.layout.library;
                        break;
                    case 105:layoutID = R.layout.science_block;
                        break;
                    case 106:layoutID = R.layout.workshop;
                        break;
                    case 107:layoutID = R.layout.analog_lab;
                        break;
                }
                View view = getLayoutInflater().inflate(layoutID, new LinearLayout(this));
                ScrollView scrollView = (ScrollView) findViewById(R.id.task_window);
                scrollView.addView(view);
            }
        }

    }

    public void yesButtonHandler(View view){

        int rank = mIntent.getIntExtra("rank", 0);
        int typeID = mIntent.getIntExtra("type_ID", 0);
        Date date = new Date();
        String strDate = date.toString();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CaptureList.RANK, rank);
        contentValues.put(CaptureList.LEVEL, 1);
        contentValues.put(CaptureList.NAME, mPokemonName);
        contentValues.put(CaptureList.TYPE_ID, typeID);
        contentValues.put(CaptureList.TIME, strDate);
        long row = mDB.insert(CaptureList.TABLE_NAME, null, contentValues );
        Log.i("insert", "success "+row);
        Intent intent = new Intent(this, Success.class);
        intent.putExtra("from", "pokeball");
        intent.putExtra("message", "Congratulation!! You caught ");
        intent.putExtra("pokemon_name", mPokemonName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void noButtonHandler(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
