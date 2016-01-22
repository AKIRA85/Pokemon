package com.google.android.gms.location.sample.locationupdates;

import android.app.Activity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.sample.locationupdates.PokemonContract.*;

public class GodActivity extends AppCompatActivity {

    SQLiteDatabase mDB;
    Activity mActivity=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_god);

        ListView listView = (ListView)findViewById(R.id.list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDB = (new PokemonDatabase(this)).getWritableDatabase();
        String[] columns = {Pokemon._ID,
                            Pokemon.RANK,
                            Pokemon.LEVEL1,
                            Pokemon.TYPE_ID};
        String[] fromColumns = {Pokemon.RANK,
                                Pokemon.LEVEL1,
                                Pokemon.TYPE_ID};
        int[] toViews = {R.id.pokemon_rank,
                        R.id.pokemon_name,
                        R.id.pokemon_type_id};

        Cursor cursor = mDB.query(Pokemon.TABLE_NAME,
                                columns,
                                null,
                                null,
                                null,
                                null,
                                null);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                                                            R.layout.god_list_item,cursor,
                                                            fromColumns,
                                                            toViews,
                                                            CursorAdapter.FLAG_AUTO_REQUERY);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                TextView rankTV = (TextView) v.findViewById(R.id.pokemon_rank);
                final String rank =  rankTV.getText().toString();
                TextView nameTV = (TextView) v.findViewById(R.id.pokemon_name);
                final String name =  nameTV.getText().toString();
                TextView typeTV = (TextView) v.findViewById(R.id.pokemon_type_id);
                final String typeID =  typeTV.getText().toString();
                String[] columns = {CaptureList.LEVEL,
                        CaptureList.TIME };

                final Cursor cursor = mDB.query(CaptureList.TABLE_NAME,
                        columns,
                        CaptureList.RANK+"=?",
                        new String[]{rank},
                        null,
                        null,
                        null);
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);

                if(cursor.getCount()==1){
                    cursor.moveToFirst();
                    builder.setMessage("Select a action.");
                    builder.setPositiveButton("Evolve", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String newName = Data.actionEvolve(mDB, Integer.parseInt(rank), cursor.getInt(0));
                            if(newName!=null){
                                Toast.makeText(mActivity, "Pokemon Evolved",
                                        Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(mActivity, "Pokemon was not Evolved",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.setNegativeButton("Release", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            boolean success = Data.actionRelease(mDB, rank);
                            if(success){
                                Toast.makeText(mActivity, "Pokemon Released",
                                        Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(mActivity, "Pokemon was not Released",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Do nothing
                        }
                    });
                }else{
                    builder.setMessage("Do you want to capture this pokemon");
                    builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            boolean success = Data.actionCapture(mDB, Integer.parseInt(rank), name,
                                                Integer.parseInt(typeID));
                            if(success){
                                Toast.makeText(mActivity, "Pokemon Captured",
                                        Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(mActivity, "Pokemon was not Captured",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Do nothing
                        }
                    });
                }
                builder.show();
            }
        });
    }

}
