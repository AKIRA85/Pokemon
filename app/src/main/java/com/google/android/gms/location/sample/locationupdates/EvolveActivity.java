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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.sample.locationupdates.PokemonContract.*;

import java.util.Date;

public class EvolveActivity extends AppCompatActivity {

    Intent mIntent;
    String mPokemonName;
    int mPokemonRank;
    int mLevel;
    SQLiteDatabase mDB;
    EditText mEditText;
    String mAnswer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        mEditText = (EditText) findViewById(R.id.answer);
        mIntent = getIntent();
        mPokemonName = mIntent.getStringExtra("pokemon_name");
        Log.i("image", "pokeball : "+mPokemonName);
        mDB = (new PokemonDatabase(this)).getWritableDatabase();
        mPokemonRank = mIntent.getIntExtra("pokemon_rank", 0);
        mLevel = mIntent.getIntExtra("pokemon_level", 0);
        int nextlevel = mLevel+1;
        Data data = new Data();
        int action = data.action.get(mPokemonRank+"_"+nextlevel);
        String[] queryColumns = {QuestionList.QUESTION,
                                QuestionList.ANSWER,
                                QuestionList.IMAGE,
                                QuestionList.SCORE};
        Cursor cursor = mDB.query(QuestionList.TABLE_NAME,
                                queryColumns,
                                QuestionList.QNO+"=?",
                                new String[]{""+action},
                                null,
                                null,
                                null);
        if(cursor.getCount()>0){
            View view = getLayoutInflater().inflate(R.layout.question, new LinearLayout(this));
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.task_window);
            linearLayout.addView(view, 0);
            cursor.moveToFirst();
            mAnswer = cursor.getString(1);
            String question = cursor.getString(0);
            TextView textView = (TextView) findViewById(R.id.question);
            textView.setText(question);
            if(cursor.getInt(2)!=0){
                Log.i("question", "image present");
                ImageView imageView = (ImageView)findViewById(R.id.q_image);
                imageView.setImageDrawable(Data.findImageByName("i"+action, this));
                imageView.setVisibility(View.VISIBLE);
            }else{
                Log.i("question", "no image present");
            }
        }
    }

    public void submitButtonHandler(View view){
        String input = mEditText.getText().toString();
        input = input.replaceAll(" ", "");
        if(input.equalsIgnoreCase(mAnswer)){

            String pokemonNewName = Data.actionEvolve(mDB, mPokemonRank, mLevel);

            if(pokemonNewName!=null){
                Intent intent = new Intent(this, Success.class);
                intent.putExtra("from", "evolve");
                intent.putExtra("pokemon_rank", mPokemonRank);
                intent.putExtra("message", "Congratulation!! Your "+mPokemonName+" evolved into ");
                intent.putExtra("pokemon_name", pokemonNewName);
                startActivity(intent);
            }else {
                Toast.makeText(this, "Something went Wrong! Sorry, your Pokemon did not evolve. :(",
                        Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Sorry, wrong answer",
                    Toast.LENGTH_SHORT).show();
        }

    }

    public void skipButtonHandler(View view){
        Intent intent = new Intent(this, CaptureListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    public void yesButtonHandler(View view){
        String column = "";
        if(mLevel==1){
            column = PokemonContract.Pokemon.LEVEL2;
        }else if(mLevel == 2){
            column = PokemonContract.Pokemon.LEVEL3;
        }
        //Query name of evolved form
        Cursor cursor = mDB.query(Pokemon.TABLE_NAME,
                                new String[]{column},
                                PokemonContract.Pokemon.RANK+"=?",
                                new String[]{""+mPokemonRank},
                                null,
                                null,
                                null);
        cursor.moveToFirst();
        String pokemonNewName = cursor.getString(0);
        ContentValues contentValues = new ContentValues();
        contentValues.put(PokemonContract.CaptureList.LEVEL, mLevel+1);
        contentValues.put(PokemonContract.CaptureList.NAME, pokemonNewName);

        //Update CaptureList
        int row = mDB.update(PokemonContract.CaptureList.TABLE_NAME,
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
        }
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
