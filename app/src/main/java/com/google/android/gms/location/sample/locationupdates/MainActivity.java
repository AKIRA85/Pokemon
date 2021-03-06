package com.google.android.gms.location.sample.locationupdates;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.DateFormat;
import java.util.Date;
import java.util.Random;

import static java.lang.Math.round;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener,
        NavigationView.OnNavigationItemSelectedListener {

    protected static final String TAG = "location-updates-sample";

    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    // Keys for storing activity state in the Bundle.
    protected final static String REQUESTING_LOCATION_UPDATES_KEY = "requesting-location-updates-key";
    protected final static String LOCATION_KEY = "location-key";
    protected final static String LAST_UPDATED_TIME_STRING_KEY = "last-updated-time-string-key";
    protected final static String POKEMON_FOUND_STATUS_STRING_KEY = "pokemon-found-status-string-key";
    protected final static String POKEMON_RANK = "pokemon-rank";
    protected final static String POKEMON_NAME = "pokemon-name";
    protected final static String POKEMON_TYPE = "pokemon-type";
    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    /**
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
    protected LocationRequest mLocationRequest;

    /**
     * Represents a geographical location.
     */
    protected Location mCurrentLocation;
    protected Location mCornerLocation;

    // UI Widgets.
    protected Button mStartUpdatesButton;
    protected Button mStopUpdatesButton;
    protected Button mPokeballButton;
    protected Button mSearchPokemonButton;
    protected TextView mPokeStatusTextView;
    protected TextView mLastUpdateTimeTextView;
    protected TextView mLatitudeTextView;
    protected TextView mLongitudeTextView;
    protected ImageView mPokemonImageView;

    // Labels.
    protected String mLatitudeLabel;
    protected String mLongitudeLabel;
    protected String mLastUpdateTimeLabel;
    protected String mPokemonName;

    protected int mPokemonRank;
    protected int mPokemonTypeID;

    /**
     * Tracks the status of the location updates request. Value changes when the user presses the
     * Start Updates and Stop Updates buttons.
     */
    protected Boolean mRequestingLocationUpdates;
    protected Boolean mPokemonFound=false;
    boolean mGPSEnabled=false;

    /**
     * Time when the location was updated represented as a String.
     */
    protected String mLastUpdateTime;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPokemonFound=false;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Locate the UI widgets.
        mStartUpdatesButton = (Button) findViewById(R.id.start_updates_button);
        mStopUpdatesButton = (Button) findViewById(R.id.stop_updates_button);
        mLatitudeTextView = (TextView) findViewById(R.id.latitude_text);
        mLongitudeTextView = (TextView) findViewById(R.id.longitude_text);
        mLastUpdateTimeTextView = (TextView) findViewById(R.id.last_update_time_text);
        mPokeballButton = (Button) findViewById(R.id.use_pokeball_button);
        mSearchPokemonButton = (Button) findViewById(R.id.search_pokemon_button);
        mPokeStatusTextView = (TextView) findViewById(R.id.pokemon_status);
        mPokemonImageView = (ImageView) findViewById(R.id.pokemon_image);
        mPokeStatusTextView = (TextView) findViewById(R.id.pokemon_status);
        // Set labels.
        mLatitudeLabel = getResources().getString(R.string.latitude_label);
        mLongitudeLabel = getResources().getString(R.string.longitude_label);
        mLastUpdateTimeLabel = getResources().getString(R.string.last_update_time_label);

        mRequestingLocationUpdates = false;
        mLastUpdateTime = "";

        // Update values using data stored in the Bundle.
        mCornerLocation = new Location("developer");
        updateValuesFromBundle(savedInstanceState);
        updateUI();
        mCornerLocation.setLatitude(25.53);
        mCornerLocation.setLongitude(84.845);
        // Kick off the process of building a GoogleApiClient and requesting the LocationServices
        // API.
        buildGoogleApiClient();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.master_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_capture_list) {
            Intent intent = new Intent(this, CaptureListActivity.class);
            startActivity(intent);
        } else if (id == R.id.reset_button) {
            DialogMaker dialogMaker = new DialogMaker();
            dialogMaker.show(getSupportFragmentManager(), "reset_dialog");
        } else if (id == R.id.question_list) {
            Intent intent = new Intent(this, QuestionListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void isGPSEnable() {
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        mGPSEnabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!mGPSEnabled) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);
            alertDialogBuilder
                    .setMessage("GPS is disabled in your device. Enable it?")
                    .setCancelable(false)
                    .setPositiveButton("Enable GPS",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    startActivity(intent);
                                }
                            });
            AlertDialog alert = alertDialogBuilder.create();
            alert.show();

        }
    }

    /**
     * Updates fields based on data stored in the bundle.
     *
     * @param savedInstanceState The activity state saved in the Bundle.
     */
    private void updateValuesFromBundle(Bundle savedInstanceState) {
        Log.i(TAG, "Updating values from bundle");
        Log.i("bundle", "Updating values from bundle");
        if (savedInstanceState != null) {
            // Update the value of mRequestingLocationUpdates from the Bundle, and make sure that
            // the Start Updates and Stop Updates buttons are correctly enabled or disabled.
            if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean(
                        REQUESTING_LOCATION_UPDATES_KEY);
                setButtonsEnabledState();
            }

            // Update the value of mCurrentLocation from the Bundle and update the UI to show the
            // correct latitude and longitude.
            if (savedInstanceState.keySet().contains(LOCATION_KEY)) {
                // Since LOCATION_KEY was found in the Bundle, we can be sure that mCurrentLocation
                // is not null.
                mCurrentLocation = savedInstanceState.getParcelable(LOCATION_KEY);
                mCornerLocation = mCurrentLocation;
            }

            // Update the value of mLastUpdateTime from the Bundle and update the UI.
            if (savedInstanceState.keySet().contains(LAST_UPDATED_TIME_STRING_KEY)) {
                mLastUpdateTime = savedInstanceState.getString(LAST_UPDATED_TIME_STRING_KEY);
            }

            if (savedInstanceState.keySet().contains(POKEMON_FOUND_STATUS_STRING_KEY)) {
                mPokemonFound = savedInstanceState.getBoolean(POKEMON_FOUND_STATUS_STRING_KEY);
                Log.i("bundle", "found value present");
            }
            Log.i("bundle", "found "+mPokemonFound);
            if(mPokemonFound){
                mPokemonRank = savedInstanceState.getInt(POKEMON_RANK);
                mPokemonName = savedInstanceState.getString(POKEMON_NAME);
                mPokemonTypeID = savedInstanceState.getInt(POKEMON_TYPE);
            }
            updateUI();
        }
    }

    /**
     * Builds a GoogleApiClient. Uses the {@code #addApi} method to request the
     * LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        if (mGoogleApiClient==null){
            Log.i(TAG, "Building GoogleApiClient");
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            createLocationRequest();
        }
    }

    /**
     * Sets up the location request. Android has two location request settings:
     * {@code ACCESS_COARSE_LOCATION} and {@code ACCESS_FINE_LOCATION}. These settings control
     * the accuracy of the current location. This sample uses ACCESS_FINE_LOCATION, as defined in
     * the AndroidManifest.xml.
     * <p/>
     * When the ACCESS_FINE_LOCATION setting is specified, combined with a fast update
     * interval (5 seconds), the Fused Location Provider API returns location updates that are
     * accurate to within a few feet.
     * <p/>
     * These settings are appropriate for mapping applications that show real-time location
     * updates.
     */
    protected void createLocationRequest() {

        mLocationRequest = new LocationRequest();

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /**
     * Handles the Start Updates button and requests start of location updates. Does nothing if
     * updates have already been requested.
     */
    public void startUpdatesButtonHandler(View view) {
        if (!mRequestingLocationUpdates) {
            mRequestingLocationUpdates = true;
            setButtonsEnabledState();
            startLocationUpdates();
        }
    }

    /**
     * Handles the Stop Updates button, and requests removal of location updates. Does nothing if
     * updates were not previously requested.
     */
    public void stopUpdatesButtonHandler(View view) {
        if (mRequestingLocationUpdates) {
            mRequestingLocationUpdates = false;
            setButtonsEnabledState();
            stopLocationUpdates();
        }
    }

    /**
     * Requests location updates from the FusedLocationApi.
     */
    protected void startLocationUpdates() {
        // The final argument to {@code requestLocationUpdates()} is a LocationListener
        // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
        if(!mPokemonFound){
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
        }
    }

    /**
     * Ensures that only one button is enabled at any time. The Start Updates button is enabled
     * if the user is not requesting location updates. The Stop Updates button is enabled if the
     * user is requesting location updates.
     */
    private void setButtonsEnabledState() {
        if (mRequestingLocationUpdates) {
            mStartUpdatesButton.setEnabled(false);
            mStopUpdatesButton.setEnabled(true);
        } else {
            mStartUpdatesButton.setEnabled(true);
            mStopUpdatesButton.setEnabled(false);
        }
    }

    private void togglePokeballButtonsStates() {
        if (!mPokemonFound) {
            mPokeballButton.setEnabled(false);
            mSearchPokemonButton.setEnabled(false);
        } else {
            mPokeballButton.setEnabled(true);
            mSearchPokemonButton.setEnabled(true);
        }
    }

    /**
     * Updates the latitude, the longitude, and the last location time in the UI.
     */
    private void updateUI() {
        if(mCurrentLocation != null){
            mLatitudeTextView.setText(String.format("%s: %f", mLatitudeLabel,
                    mCurrentLocation.getLatitude()));
            mLongitudeTextView.setText(String.format("%s: %f", mLongitudeLabel,
                    mCurrentLocation.getLongitude()));
            mLastUpdateTimeTextView.setText(String.format("%s: %s", mLastUpdateTimeLabel,
                    mLastUpdateTime));
        }
        if(mPokemonFound){
            mPokemonImageView.setImageDrawable(Data.findImageByName(mPokemonName, this));
            mPokeStatusTextView.setText("You found " + mPokemonName + "!");
            togglePokeballButtonsStates();
        }
    }

    public void refreshButtonHandler(View view){
        stopUpdatesButtonHandler(view);
        startUpdatesButtonHandler(view);
    }

    /**
     * Removes location updates from the FusedLocationApi.
     */
    protected void stopLocationUpdates() {
        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.

        // The final argument to {@code requestLocationUpdates()} is a LocationListener
        // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        mGoogleApiClient.connect();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.google.android.gms.location.sample.locationupdates/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Within {@code onPause()}, we pause location updates, but leave the
        // connection to GoogleApiClient intact.  Here, we resume receiving
        // location updates if the user has requested them.
        isGPSEnable();
        if (mGoogleApiClient.isConnected() && !mPokemonFound && mGPSEnabled) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Stop location updates to save battery, but don't disconnect the GoogleApiClient object.
        if (mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
        }
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();

        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.google.android.gms.location.sample.locationupdates/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.disconnect();
    }

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i(TAG, "Connected to GoogleApiClient");

        // If the initial location was never previously requested, we use
        // FusedLocationApi.getLastLocation() to get it. If it was previously requested, we store
        // its value in the Bundle and check for it in onCreate(). We
        // do not request it again unless the user specifically requests location updates by pressing
        // the Start Updates button.
        //
        // Because we cache the value of the initial location in the Bundle, it means that if the
        // user launches the activity,
        // moves to a new location, and then changes the device orientation, the original location
        // is displayed as the activity is re-created.
        if (mCurrentLocation == null) {
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
            updateUI();
        }

        // If the user presses the Start Updates button before GoogleApiClient connects, we set
        // mRequestingLocationUpdates to true (see startUpdatesButtonHandler()). Here, we check
        // the value of mRequestingLocationUpdates and if it is true, we start location updates.
        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    /**
     * Callback that fires when the location changes.
     */
    @Override
    public void onLocationChanged(Location location) {
        Log.i("change", "yes");
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        updateUI();
        findPokemon();
        Toast.makeText(this, getResources().getString(R.string.location_updated_message),
                Toast.LENGTH_SHORT).show();
    }

    public void findPokemon() {

        double latdiff = mCurrentLocation.getLatitude() - mCornerLocation.getLatitude();
        double longdiff = mCurrentLocation.getLongitude() - mCornerLocation.getLongitude();
        double unit = 0.005;
        int x = (int) round(longdiff / unit);
        int y = (int) round(latdiff / unit);
        int block = x + 3 * y;
        block = ((block % 15) + 15) % 15;
        Log.i("coin", "latcenter : " + mCurrentLocation.getLatitude() +
                "longcenter" + mCurrentLocation.getLongitude());
        Log.i("coin", "x : " + x + " y : " + y + " block : " + block);

        Random random = new Random();
        Data data = new Data();
        SQLiteDatabase pokedb = (new PokemonDatabase(getApplicationContext())).getWritableDatabase();
        int coin = random.nextInt(1);
        int densityPokemon = data.distribution.get(block).length;
        boolean test = (coin == 0 && block > -1 && block < 16);
        //Log.i("coin", "value : "+coin+" block : "+block+" test : "+test);

        if (test && densityPokemon>0) {
            Log.i("coin", "inside");
            stopLocationUpdates();
            int number = random.nextInt(data.distribution.get(block).length);
            int[] poke_list = data.distribution.get(block);
            mPokemonRank = poke_list[number];
            Cursor cursor = pokedb.query(PokemonContract.CaptureList.TABLE_NAME,
                    new String[]{PokemonContract.CaptureList.RANK},
                    PokemonContract.CaptureList.RANK + "=?",
                    new String[]{"" + mPokemonRank},
                    null,
                    null,
                    null);
            if (cursor.getCount() == 0) {
                mPokemonFound = true;
                cursor = pokedb.query(PokemonContract.Pokemon.TABLE_NAME,
                        new String[]{PokemonContract.Pokemon.LEVEL1, PokemonContract.Pokemon.TYPE_ID},
                        PokemonContract.Pokemon.RANK + "=?",
                        new String[]{Integer.toString(poke_list[number])},
                        null,
                        null,
                        null);
                cursor.moveToFirst();
                mPokemonName = cursor.getString(0);
                mPokemonTypeID = cursor.getInt(1);
                cursor.close();
                Log.i("name", mPokemonName);

                updateUI();
                togglePokeballButtonsStates();
            }
        } else {
            mPokeStatusTextView.setText("No pokemon nearby");
        }
    }

    public void usePokeballButtonHandler(View view) {
        Intent intent = new Intent(this, Pokeball.class);
        intent.putExtra("pokemon", mPokemonName);
        intent.putExtra("rank", mPokemonRank);
        intent.putExtra("type_ID", mPokemonTypeID);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
//        Toast.makeText(this, "Pokemon Caught", Toast.LENGTH_SHORT).show();
//        startLocationUpdates();
//        togglePokeballButtonsStates();
    }

    public void searchPokemonButtonHandler(View view) {
        mPokemonImageView.setImageDrawable(Data.findImageByName("pokeball", this));
        mPokeStatusTextView.setText("Searching for Pokemon");
        mPokemonFound = false;
        startLocationUpdates();
        togglePokeballButtonsStates();
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }


    /**
     * Stores activity data in the Bundle.
     */
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.i("bundle", "saving values to bundle");
        savedInstanceState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY, mRequestingLocationUpdates);
        savedInstanceState.putParcelable(LOCATION_KEY, mCurrentLocation);
        savedInstanceState.putString(LAST_UPDATED_TIME_STRING_KEY, mLastUpdateTime);
        savedInstanceState.putBoolean(POKEMON_FOUND_STATUS_STRING_KEY, mPokemonFound);
        Log.i("bundle", "found "+mPokemonFound);
        if(mPokemonFound){
            savedInstanceState.putInt(POKEMON_RANK, mPokemonRank);
            savedInstanceState.putString(POKEMON_NAME, mPokemonName);
            savedInstanceState.putInt(POKEMON_TYPE, mPokemonTypeID);
        }
        super.onSaveInstanceState(savedInstanceState);
    }
}
