package com.google.android.gms.location.sample.locationupdates;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by owner on 2/1/16.
 */
public class Data {

    protected Map<Integer, int[]> distribution = new HashMap<Integer, int[]>();

    Data(){
        distribution.put(0, new int[] {1, 2, 3});
        distribution.put(1, new int[] {4, 5, 6});
        distribution.put(2, new int[] {7, 8, 9});
        distribution.put(3, new int[] {10, 11, 12});
        distribution.put(4, new int[] {13, 14, 15});
        distribution.put(5, new int[] {1, 4, 7});
        distribution.put(6, new int[] {2, 5, 8});
        distribution.put(7, new int[] {3, 6, 9});
        distribution.put(8, new int[] {1, 10, 13});
        distribution.put(9, new int[] {2, 11, 14});
        distribution.put(10, new int[] {3, 12, 15});
        distribution.put(11, new int[] {4, 7, 10});
        distribution.put(12, new int[] {5, 8, 11});
        distribution.put(13, new int[] {6, 9, 12});
        distribution.put(14, new int[] {4, 8, 12});
        distribution.put(15, new int[] {7, 11, 15});
    }

    public static Drawable findImageByName(String name, Activity activity){
        name = name.toLowerCase();
        Log.i("image", name);
        Resources resources = activity.getResources();
        int resourceId =  resources.getIdentifier(name.toLowerCase(), "drawable",
                activity.getApplicationContext().getPackageName());
        return resources.getDrawable(resourceId);
    }

    public static String getTypeById(int id){
        switch (id){
            case 1:return "Fire";
            case 2:return "Grass";
            case 3:return "Water";
            case 4:return "Rock";
            case 5:return "Electric";
            default:return "Unknown";
        }
    }
}
