package com.google.android.gms.location.sample.locationupdates;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.google.android.gms.location.sample.locationupdates.PokemonContract.*;
/**
 * Created by owner on 5/1/16.
 */
public class PokemonDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "Pokemon";

    private static final String POKEMON_TABLE_CREATE =
            "CREATE TABLE " + Pokemon.TABLE_NAME + " (" +
                    Pokemon._ID + " INT, " +
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
                    "( '5',     'Bellsprout',   'Weepinbell',   'Victreebel',  '2' ), "+
                    "( '6',     'Oddish',       'Gloom',        'Vileplum',     '2' ), "+
                    "( '7',     'Squirtle',     'Wartortle',    'Blastoise',    '3' ), "+
                    "( '8',     'Horsea',       'Seadra',       'Kingdra',      '3' ), "+
                    "( '9',     'Poliwag',      'Poliwhirl',    'Poliwrath',    '3' ), "+
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
                    CaptureList._ID + " INT, " +
                    CaptureList.RANK + " INT, " +
                    CaptureList.LEVEL + " INT," +
                    CaptureList.NAME + " TEXT," +
                    CaptureList.TYPE_ID + " INT," +
                    CaptureList.TIME + " TEXT" +
                    " );";

    private static final String QUESTION_TABLE_CREATE =
            "CREATE TABLE " + QuestionList.TABLE_NAME + " (" +
                    QuestionList._ID + " INT, " +
                    QuestionList.QNO + " INT, " +
                    QuestionList.QUESTION + " TEXT, " +
                    QuestionList.ANSWER + " TEXT, " +
                    QuestionList.IMAGE + " INT, " +
                    QuestionList.SCORE + " INT" +
                    " );";

    private static final String QUESTION_TABLE_INSERT =
            "INSERT INTO " + QuestionList.TABLE_NAME +
                    " (" + QuestionList.QNO + ", " +
                    QuestionList.QUESTION +", " +
                    QuestionList.ANSWER +", " +
                    QuestionList.IMAGE +", " +
                    QuestionList.SCORE +")" + " VALUES " +
                    "( '101', " +
                    " 'canteen', " +
                    " '0', " +
                    " '0', " +
                    " '8' " +
                    "), "
                    +"( '102', " +
                    " 'cc', " +
                    " '0', " +
                    " '0', " +
                    " '8' " +
                    "), "
                    +"( '103', " +
                    " 'ground', " +
                    " '0', " +
                    " '0', " +
                    " '8' " +
                    "), "+
                    "( '104', " +
                    " 'library', " +
                    " '0', " +
                    " '0', " +
                    " '8' " +
                    "), "
                    +"( '105', " +
                    " 'science_block', " +
                    " '0', " +
                    " '0', " +
                    " '8' " +
                    "), "
                    +"( '106', " +
                    " 'workshop', " +
                    " '0', " +
                    " '0', " +
                    " '8' " +
                    "), "
                    +"( '107', " +
                    " 'analog_lab', " +
                    " '0', " +
                    " '0', " +
                    " '8' " +
                    "), "+
                    "( '0', " +
                        " 'Do you want to use Pokeball?', " +
                        " '0', " +
                        " '0', " +
                        " '4' " +
                        "), "+
                    "( '1', " +
                        " 'What is the value of a capacitor labeled 102?', " +
                        " '1000pf', " +
                        " '0', " +
                        " '4' " +
                        "), "+
                    "( '2', " +
                        " 'What circuit has the following frequency response?', " +
                        " 'lowpassfilter', " +
                        " '1', " +
                        " '4' " +
                        "), "+
                    "( '3', " +
                        " 'A capacitor stores energy within a dielectric between the conducting plates in the form of:', " +
                        " 'electricfield', " +
                        " '0', " +
                        " '4' " +
                        "), "+
                    "( '4', " +
                        " 'What is this circuit?', " +
                        " 'bandstopfilter', " +
                        " '1', " +
                        " '4' " +
                        "), "+
                    "( '5', " +
                        " 'The reflected resistance is ___ as seen by the source in the given circuit.', " +
                        " '24000', " +
                        " '1', " +
                        " '4' " +
                        "), "+
                    "( '6', " +
                        " 'A two-pole high-pass active filter would have a roll-off rate of ___ dB/decade', " +
                        " '-40', " +
                        " '0', " +
                        " '4' " +
                        "), "+
                    "( '7', " +
                        " 'The bandwidth is ______ kHz in the given circuit.(Note : Upto two decimal places)', " +
                        " '3.54', " +
                        " '1', " +
                        " '4' " +
                        "), "+
                    "( '8', " +
                        " '_____ current law states that the current flowing into a point of a circuit is equal to the sum of the voltages in the output branches', " +
                        " 'kirchhoff', " +
                        " '0', " +
                        " '4' " +
                        "), "+
                    "( '9', " +
                        " 'When a full band of frequencies is allowed to pass through a filter circuit to the output, the resonant circuit is called a:', " +
                        " 'bandpassfilter', " +
                        " '0', " +
                        " '4' " +
                        "), "+
                    "( '10', " +
                        " 'A group of force lines going from the north pole to the south pole of a magnet is called the magnetic ___.', " +
                        " 'flux', " +
                        " '0', " +
                        " '4' " +
                        "), "+
                    "( '11', " +
                        " 'What is the voltage across the capacitor?(mV)', " +
                        " '111', " +
                        " '1', " +
                        " '4' " +
                        "), "+
                    "( '12', " +
                        " 'The phase angle in the given circuit equals ___ degrees.', " +
                        " '36.87', " +
                        " '1', " +
                        " '4' " +
                        "), "+
                    "( '13', " +
                        " '______ law states that the current induced in a circuit due to a change in the magnetic field is so directed as to oppose the change in flux or to exert a mechanical force opposing the motion.', " +
                        " 'Lenzs', " +
                        " '0', " +
                        " '4' " +
                        "), "+
                    "( '14', " +
                        " 'Mutual induction is dependent on change in', " +
                        " 'current', " +
                        " '0', " +
                        " '4' " +
                        "), "+
                    "( '15', " +
                        " 'What has the voltage across the resistor decayed to by the end of the pulse in the given circuit?(Note : represent the answer in Volts upto two decimal places.)', " +
                        " '0.75', " +
                        " '1', " +
                        " '4' " +
                        "), "+
                    "( '16', " +
                        " 'If a 169.7 V half-wave peak has an average voltage of 54 V, what is the average of two full-wave peaks?', " +
                        " '108', " +
                        " '0', " +
                        " '4' " +
                        "), "+
                    "( '17', " +
                        " 'The peak inverse voltage (PIV) for the diode in the give circuit equals ___.', " +
                        " '3', " +
                        " '0', " +
                        " '4' " +
                        "), "+
                    "( '18', " +
                        " 'How many bits are used in the 8085(processor) data bus?', " +
                        " '8', " +
                        " '0', " +
                        " '4' " +
                        "), "+
                    "( '19', " +
                        " 'The circuits in the 8085 that provide the arithmetic and logic functions are called the __________', " +
                        " 'ALU', " +
                        " '0', " +
                        " '4' " +
                        "), "+
                    "( '20', " +
                        " '(Repeated)The circuits in the 8085 that provide the arithmetic and logic functions are called the __________', " +
                        " 'ALU', " +
                        " '0', " +
                        " '4' " +
                        "), "+
                    "( '21', " +
                        " 'Avalanche breakdown is shown by which diode', " +
                        " 'Zener', " +
                        " '0', " +
                        " '4' " +
                        "), "+
                    "( '22', " +
                        " 'Op-Amp here is working as a _______', " +
                        " 'comparator', " +
                        " '1', " +
                        " '4' " +
                        "), "+
                    "( '23', " +
                        " '(Change)This device is called _______.', " +
                        " 'oscilloscope', " +
                        " '0', " +
                        " '4' " +
                        "), "+
                    "( '24', " +
                        " '________ transformer', " +
                        " 'stepup', " +
                        " '1', " +
                        " '4' " +
                        "), "+
                    "( '25', " +
                        " 'This was invented by ', " +
                        " 'nicolatesla', " +
                        " '1', " +
                        " '4' " +
                        "), "+
                    "( '26', " +
                        " '_______ was considered the invention of the twentieth\n" +
                    "century that changed electronic circuits forever. It is a semiconductor\n" +
                    "device used to amplify and switch electronic signals and electrical power.', " +
                        " 'transistor', " +
                        " '0', " +
                        " '4' " +
                        "), "+
                    "( '27', " +
                        " 'invented the technology used for radio guidance system by Allied Forces Torpedoes.\n"+
                        " It was called ________.', " +
                        " 'frequencyhoppingspreadspectrum', " +
                        " '1', " +
                        " '4' " +
                        "), "+
                    "( '28', " +
                        " 'What Tony sees is not reality it is ________', " +
                        " 'augmentedreality', " +
                        " '1', " +
                        " '4' " +
                        "), "+
                    "( '29', " +
                    " '_______ blocks direct current but not alternating current', " +
                    " 'capacitor', " +
                    " '0', " +
                    " '4' " +
                    "), "+
                    "( '30', " +
                    " '_______ measures g-force and can be easily found in any smart phone', " +
                    " 'accelerometer', " +
                    " '0', " +
                    " '4' " +
                    ");";

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
        db.execSQL(QUESTION_TABLE_CREATE);
        db.execSQL(QUESTION_TABLE_INSERT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion != oldVersion){
            onCreate(db);
        }
    }
}