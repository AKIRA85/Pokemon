package com.google.android.gms.location.sample.locationupdates;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.location.sample.locationupdates.PokemonContract.*;

public class Pokeball extends ActionBarActivity {

    Intent mIntent;
    String mPokemonName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokeball);
        mIntent = getIntent();
        mPokemonName = mIntent.getStringExtra("pokemon");
        Log.i("image", "pokeball : "+mPokemonName);
        TextView tv = (TextView) findViewById(R.id.question);
        tv.setText("Do you want to catch "+mPokemonName+"?");
    }

    public void yesButtonHandler(View view){
        SQLiteDatabase db = (new PokemonDatabase(this)).getWritableDatabase();
        int rank = mIntent.getIntExtra("rank", 0);
        int typeID = mIntent.getIntExtra("type_ID", 0);
        ContentValues contentValues = new ContentValues();
        contentValues.put(CaptureList.RANK, rank);
        contentValues.put(CaptureList.LEVEL, 1);
        contentValues.put(CaptureList.NAME, mPokemonName);
        contentValues.put(CaptureList.TYPE_ID, typeID);
        db.insert(CaptureList.TABLE_NAME, null, contentValues );
        Intent intent = new Intent(this, Success.class);
        intent.putExtra("pokemon_name", mIntent.getStringExtra("pokemon"));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void noButtonHandler(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
