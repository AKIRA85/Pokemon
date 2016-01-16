package com.google.android.gms.location.sample.locationupdates;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.sample.locationupdates.PokemonContract.*;

public class DetailActivity extends AppCompatActivity {

    String mPokemonName;
    int mPokemonRank;
    int mPokemonLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mPokemonRank = getIntent().getIntExtra("pokemon_rank", 0);
        SQLiteDatabase db = (new PokemonDatabase(this)).getReadableDatabase();
        String[] columns = {CaptureList.RANK,
                        CaptureList.NAME,
                        CaptureList.TYPE_ID,
                        CaptureList.LEVEL};
        Cursor cursor = db.query(CaptureList.TABLE_NAME,
                columns,
                CaptureList.RANK+"=?",
                new String[]{""+mPokemonRank},
                null,
                null,
                null);
        Log.i("index", cursor.getColumnName(1));
        cursor.moveToFirst();
        mPokemonName = cursor.getString(1);
        mPokemonLevel = cursor.getInt(3);

        TextView textView = (TextView)findViewById(R.id.pokemon_name);
        textView.setText(mPokemonName);
        ImageView imageView = (ImageView)findViewById(R.id.pokemon_image);
        imageView.setImageDrawable(Data.findImageByName(mPokemonName, this));
        textView = (TextView)findViewById(R.id.pokemon_type);
        textView.setText(""+Data.getTypeById(cursor.getInt(2)));
        textView = (TextView)findViewById(R.id.pokemon_rank);
        textView.setText(""+mPokemonRank);
        textView = (TextView)findViewById(R.id.pokemon_level);
        textView.setText(""+mPokemonLevel);
        if(mPokemonLevel>=3){
            Button button = (Button)findViewById(R.id.evolve_button);
            button.setVisibility(View.INVISIBLE);
        }
    }

    public void onEvolveButtonHandler(View view){
        Intent intent = new Intent(this, EvolveActivity.class);
        intent.putExtra("pokemon_rank", mPokemonRank);
        intent.putExtra("pokemon_name", mPokemonName);
        intent.putExtra("pokemon_level", mPokemonLevel);
        startActivity(intent);
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event)  {
//        if (Integer.parseInt(android.os.Build.VERSION.SDK) < 5
//                && keyCode == KeyEvent.KEYCODE_BACK
//                && event.getRepeatCount() == 0) {
//            onBackPressed();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    public void onBackPressed(){
//        Intent intent = new Intent(this, CaptureListActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }
}
