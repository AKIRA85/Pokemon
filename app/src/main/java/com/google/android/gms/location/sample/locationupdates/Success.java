package com.google.android.gms.location.sample.locationupdates;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Success extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        ImageView imageView = (ImageView) findViewById(R.id.pokemon_image);
        TextView textView = (TextView) findViewById(R.id.message);
        Intent intent = getIntent();
        String pokemonName = intent.getStringExtra("pokemon_name");
        Log.i("image", "success : "+pokemonName);
        imageView.setImageDrawable(Data.findImageByName(pokemonName, this));
        textView.setText("Congratulation!! You caught "+pokemonName);
    }

    public void okButtonHandler(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
