package com.google.android.gms.location.sample.locationupdates;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.sample.locationupdates.PokemonContract.*;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        int rank = getIntent().getIntExtra("pokemon_rank", 0);
        SQLiteDatabase db = (new PokemonDatabase(this)).getReadableDatabase();
        String[] columns = {CaptureList.RANK,
                        CaptureList.NAME,
                        CaptureList.TYPE_ID,
                        CaptureList.LEVEL};
        Cursor cursor = db.query(CaptureList.TABLE_NAME,
                columns,
                CaptureList.RANK+"=?",
                new String[]{""+rank},
                null,
                null,
                null);
        Log.i("index", cursor.getColumnName(1));
        cursor.moveToFirst();
        String pokemonName = cursor.getString(1);
        int level = cursor.getInt(3);

        TextView textView = (TextView)findViewById(R.id.pokemon_name);
        textView.setText(pokemonName);
        ImageView imageView = (ImageView)findViewById(R.id.pokemon_image);
        imageView.setImageDrawable(Data.findImageByName(pokemonName, this));
        textView = (TextView)findViewById(R.id.pokemon_type);
        textView.setText(""+Data.getTypeById(cursor.getInt(2)));
        textView = (TextView)findViewById(R.id.pokemon_rank);
        textView.setText(""+cursor.getInt(0));
        textView = (TextView)findViewById(R.id.pokemon_level);
        textView.setText(""+level);
        if(level>=3){
            Button button = (Button)findViewById(R.id.evolve_button);
            button.setVisibility(View.INVISIBLE);
        }
    }
}
