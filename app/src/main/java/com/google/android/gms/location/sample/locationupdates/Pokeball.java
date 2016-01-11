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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokeball);
        mIntent = getIntent();
        String pokemonName = mIntent.getStringExtra("pokemon");
        Log.i("image", "pokeball : "+pokemonName);
        TextView tv = (TextView) findViewById(R.id.question);
        tv.setText("Do you want to catch "+pokemonName+"?");
    }

    public void yesButtonHandler(View view){
        SQLiteDatabase db = (new PokemonDatabase(this)).getWritableDatabase();
        int rank = mIntent.getIntExtra("rank", 0);
        ContentValues contentValues = new ContentValues();
        contentValues.put(CaptureList.RANK, rank);
        contentValues.put(CaptureList.LEVEL, 1);
        db.insert(CaptureList.TABLE_NAME, null, contentValues );
        Intent intent = new Intent(this, Success.class);
        intent.putExtra("pokemon_name", mIntent.getStringExtra("pokemon"));
        startActivity(intent);
    }

    public void noButtonHandler(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
