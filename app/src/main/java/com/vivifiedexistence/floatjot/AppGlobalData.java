package com.vivifiedexistence.floatjot;

import android.location.Location;

import java.util.ArrayList;

import util.Vec;

/**
 * Created by saif on 2017-09-23.
 */

public class AppGlobalData {
    public static ArrayList<FloatNoteAttributes> notesList = new ArrayList<FloatNoteAttributes>();
    public  static Location currentLocation;
    public static Vec currentVec;
    public static FloatNoteAttributes currentFloatJot;
    public static ArrayList<FloatNoteAttributes> nearbyJots = new ArrayList<>();
    public static ArrayList<String> profanityList = new ArrayList<>();
}
