package com.vivifiedexistence.floatjot;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by saif on 2017-09-16.
 */

public class HttpHandler {
    public String makeServiceCall(String url)
    {
        String response="NULL";
        try {
            URL callURL = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)callURL.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            response = bufferedReader.readLine();
        }catch(Exception e)
        {
            Log.e("HTTP",e.getMessage()+"error");
        }
        Log.d("RESPONSE",response);
        return response;
    }
}
