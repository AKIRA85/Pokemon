package com.google.android.gms.location.sample.locationupdates;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.google.android.gms.location.sample.locationupdates.PokemonContract.*;
/**
 * Created by owner on 5/1/16.
 */
public class PokemonDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Pokemon";

    private static final String POKEMON_TABLE_CREATE =
            "CREATE TABLE " + Pokemon.TABLE_NAME + " (" +
                    Pokemon.RANK + " INT, " +
                    Pokemon.LEVEL1 + " TEXT, " +
                    Pokemon.LEVEL2 + " TEXT, " +
                    Pokemon.LEVEL3 + " TEXT, " +
                    Pokemon.TYPE_ID + " INT" +
                    " );";

    private static final String POKEMON_TABLE_INSERT =
            "INSERT INTO " + Pokemon.TABLE_NAME +
                    " ("+ Pokemon.RANK +", "+ Pokemon.LEVEL1 +", "+ Pokemon.LEVEL2 +", "+
                    Pokemon.LEVEL3 +", "+Pokemon.TYPE_ID+")" +
                    " VALUES " +
                    "( '1',     'Charmander',   'Charmeleon',   'Charizard',    '1' ), "+
                    "( '2',     'Cyndaquil',    'Quilava',      'Typhlosion',   '1' ), "+
                    "( '3',     'Magby',        'Magmar',       'Magmortar',    '1' ), "+
                    "( '4',     'Bulbasaur',    'Ivysaur',      'Venusaur',     '2' ), "+
                    "( '5',     'Bellsprout',   'Weepinbell',   'Victreebell',  '2' ), "+
                    "( '6',     'Oddish',       'Gloom',        'Vileplum',     '2' ), "+
                    "( '7',     'Squirtle',     'Wartortle',    'Blastoise',    '3' ), "+
                    "( '8',     'Horsea',       'Seadra',       'Kingdra',      '3' ), "+
                    "( '9',     'Poliwag',      'Poliwhirl',    'Poliwarth',    '3' ), "+
                    "( '10',    'Rhyhorn',      'Rhydon',       'Rhyperior',    '4' ), "+
                    "( '11',    'Larvitar',     'Pupitar',      'Tyranitar',    '4' ), "+
                    "( '12',    'Geodude',      'Graveler',     'Golem',        '4' ), "+
                    "( '13',    'Pichu',        'Pikachu',      'Raichu',       '5' ), "+
                    "( '14',    'Elekid',       'Electrabuzz',  'Electivire',   '5' ), "+
                    "( '15',    'Magnemite',    'Magneton',     'Magnezone',    '5' ) "+
                    ";";

    private static final String TYPE_TABLE_CREATE =
            "CREATE TABLE " + Type.TABLE_NAME + " (" +
                    Type.CODE + " INT, " +
                    Type.NAME + " TEXT" +
                    " );";

    private static final String TYPE_TABLE_INSERT =
            "INSERT INTO " + Type.TABLE_NAME +
                    " (" + Type.CODE + ", " + Type.NAME + ")" + " VALUES " +
                    "( '1', 'Fire' ), " +
                    "( '2', 'Grass' ), " +
                    "( '3', 'Water' ), " +
                    "( '4', 'Rock' ), " +
                    "( '5', 'Electric' )" +
                    ";";

    private static final String CAPTURE_TABLE_CREATE =
            "CREATE TABLE " + CaptureList.TABLE_NAME + " (" +
                    CaptureList.RANK + " INT, " +
                    CaptureList.LEVEL + " INT" +
                    " );";


    public PokemonDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(POKEMON_TABLE_CREATE);
        db.execSQL(TYPE_TABLE_CREATE);
        db.execSQL(POKEMON_TABLE_INSERT);
        db.execSQL(TYPE_TABLE_INSERT);
        db.execSQL(CAPTURE_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}