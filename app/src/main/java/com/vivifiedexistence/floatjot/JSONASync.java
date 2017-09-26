package com.vivifiedexistence.floatjot;

import android.app.Activity;
import android.location.Location;
import android.os.AsyncTask;
import android.os.SystemClock;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import util.Log;

/**
 * Created by saif on 2017-09-23.
 */

public class JSONASync extends AsyncTask<Void,Void,Void>{
    MapsActivity act;
    public JSONASync(MapsActivity ac)
    {
        act = ac;
    }
    @Override
    protected void onPreExecute()
    {
        AppGlobalData.notesList.clear();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        HttpHandler handler = new HttpHandler();
        String JSON = handler.makeServiceCall("http://172.20.10.9/floatjot/getNotes.php?lastId=1");
        System.out.println("JSON RESPONSE : "+JSON);
        try {
            JSONArray myarray = new JSONArray(JSON);

            for(int i=0; i<myarray.length(); i++){
                JSONObject obj = myarray.getJSONObject(i);
                FloatNoteAttributes attr = new FloatNoteAttributes();

                attr.id = obj.getInt("note_id");
                attr.user_id = obj.getInt("user_id");
                attr.vx = obj.getString("vx");
                attr.vy = obj.getString("vy");
                attr.vz = obj.getString("vz");
                attr.title = obj.getString("title");
                attr.message = obj.getString("message");
                attr.lat = obj.getString("lat");
                attr.lon = obj.getString("lng");
                AppGlobalData.notesList.add(attr);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



        return null;
    }
    @Override
    protected void onPostExecute(Void result)
    {
        float min = 1000;
        boolean callDispSnack = false;
        for(int i=0;i<AppGlobalData.notesList.size();i++)
        {
            double latitude = Double.parseDouble(AppGlobalData.notesList.get(i).lat);
            double longitude = Double.parseDouble(AppGlobalData.notesList.get(i).lon);
            LatLng latlng = new LatLng(latitude,longitude);
            Location loctemp = new Location("");
            loctemp.setLatitude(latlng.latitude);
            loctemp.setLongitude(latlng.longitude);

            act.mMap.addMarker(new MarkerOptions().position(latlng).title("FloatJot here!"));
            float dist = act.deviceLocation.distanceTo(loctemp);
            if(dist < 100){
                AppGlobalData.nearbyJots.add(AppGlobalData.notesList.get(i));
                callDispSnack= true;
            }

        }
        Log.d("TAG","MIN DISTANCE"+min);

        if(callDispSnack)
        {
            act.dispSnackbar();
        }
    }
}
