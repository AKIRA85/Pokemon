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
    protected Map<String, Integer> action = new HashMap<String, Integer>();

    Data(){
        distribution.put(0, new int[] {1, 2, 3});
        distribution.put(1, new int[] {1, 2, 3});
        distribution.put(2, new int[] {1, 2, 3});
        distribution.put(3, new int[] {4, 5, 6});
        distribution.put(4, new int[] {4, 5, 6});
        distribution.put(5, new int[] {4, 5, 6});
        distribution.put(6, new int[] {7, 8, 9});
        distribution.put(7, new int[] {7, 8, 9});
        distribution.put(8, new int[] {7, 8, 9});
        distribution.put(9, new int[] {10, 11, 12});
        distribution.put(10, new int[] {10, 11, 12});
        distribution.put(11, new int[] {10, 11, 12});
        distribution.put(12, new int[] {13, 14, 15});
        distribution.put(13, new int[] {13, 14, 15});
        distribution.put(14, new int[] {13, 14, 15});

        action.put("1_1", 101); action.put("1_2", 1); action.put("1_3", 2);
        action.put("2_1", 106); action.put("2_2", 3); action.put("2_3", 4);
        action.put("3_1", 5); action.put("3_2", 6); action.put("3_3", 7);
        action.put("4_1", 103); action.put("4_2", 8); action.put("4_3", 9);
        action.put("5_1", 10); action.put("5_2", 11); action.put("5_3", 12);
        action.put("6_1", 13); action.put("6_2", 19); action.put("6_3", 20);
        action.put("7_1", 102); action.put("7_2", 22); action.put("7_3", 21);
        action.put("8_1", 14); action.put("8_2", 23); action.put("8_3", 24);
        action.put("9_1", 15); action.put("9_2", 26); action.put("9_3", 25);
        action.put("10_1", 104); action.put("10_2", 27); action.put("10_3", 28);
        action.put("11_1", 16); action.put("11_2", 30); action.put("11_3", 29);
        action.put("12_1", 17); action.put("12_2", 0); action.put("12_3", 0);
        action.put("13_1", 105); action.put("13_2", 0); action.put("13_3", 0);
        action.put("14_1", 107); action.put("14_2", 0); action.put("14_3", 0);
        action.put("15_1", 18); action.put("15_2", 0); action.put("15_3", 0);
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
