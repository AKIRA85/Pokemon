package com.google.android.gms.location.sample.locationupdates;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Success extends AppCompatActivity {

    String mFromActivity;
    int mPokemonRank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        ImageView imageView = (ImageView) findViewById(R.id.pokemon_image);
        TextView textView = (TextView) findViewById(R.id.message);
        Intent intent = getIntent();
        mFromActivity = intent.getStringExtra("from");
        mPokemonRank = intent.getIntExtra("pokemon_rank", 0);
        String pokemonName = intent.getStringExtra("pokemon_name");
        String message = intent.getStringExtra("message");
        imageView.setImageDrawable(Data.findImageByName(pokemonName, this));
        textView.setText(message+pokemonName);
    }

    public void okButtonHandler(View view){
        Intent intent;
        if(mFromActivity.equalsIgnoreCase("pokeball")){
            intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }else{
            intent = new Intent(this, CaptureListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        }
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
