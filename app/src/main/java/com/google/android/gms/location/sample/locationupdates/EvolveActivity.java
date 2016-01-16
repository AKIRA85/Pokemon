package com.google.android.gms.location.sample.locationupdates;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class EvolveActivity extends AppCompatActivity {

    Intent mIntent;
    String mPokemonName;
    int mPokemonRank;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        mIntent = getIntent();
        mPokemonName = mIntent.getStringExtra("pokemon_name");
        Log.i("image", "pokeball : "+mPokemonName);
        TextView tv = (TextView) findViewById(R.id.question);
        tv.setText("Do you want to evolve "+mPokemonName+"?");
    }

    public void yesButtonHandler(View view){

        SQLiteDatabase db = (new PokemonDatabase(this)).getWritableDatabase();
        mPokemonRank = mIntent.getIntExtra("pokemon_rank", 0);
        int level = mIntent.getIntExtra("pokemon_level", 0);
        String column = "";
        if(level==1){
            column = PokemonContract.Pokemon.LEVEL2;
        }else if(level == 2){
            column = PokemonContract.Pokemon.LEVEL3;
        }

        //Query name of evolved form
        Cursor cursor = db.query(PokemonContract.Pokemon.TABLE_NAME,
                                new String[]{column},
                                PokemonContract.Pokemon.RANK+"=?",
                                new String[]{""+mPokemonRank},
                                null,
                                null,
                                null);
        cursor.moveToFirst();
        String pokemonNewName = cursor.getString(0);
        ContentValues contentValues = new ContentValues();
        contentValues.put(PokemonContract.CaptureList.LEVEL, level+1);
        contentValues.put(PokemonContract.CaptureList.NAME, pokemonNewName);

        //Update CaptureList
        int row = db.update(PokemonContract.CaptureList.TABLE_NAME,
                contentValues,
                PokemonContract.Pokemon.RANK+"=?",
                new String[]{""+mPokemonRank});


        if(row==1){
            Intent intent = new Intent(this, Success.class);
            intent.putExtra("from", "evolve");
            intent.putExtra("pokemon_rank", mPokemonRank);
            intent.putExtra("message", "Congratulation!! Your "+mPokemonName+" evolved into ");
            intent.putExtra("pokemon_name", pokemonNewName);
            startActivity(intent);
        }else {
            Toast.makeText(this, "Something went Wrong! Sorry, your Pokemon did not evolve. :(",
                    Toast.LENGTH_SHORT).show();
            noButtonHandler(findViewById(R.id.no_button));
        }
    }

    public void noButtonHandler(View view){
        Intent intent = new Intent(this, CaptureListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) < 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onBackPressed(){
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
}
