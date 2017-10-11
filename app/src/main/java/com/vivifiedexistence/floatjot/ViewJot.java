package com.vivifiedexistence.floatjot;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ViewJot extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_jot);
        TextView title = (TextView)findViewById(R.id.tVnoteTitle001);
        title.setText(AppGlobalData.currentFloatJot.title);
        Log.d("TAG",AppGlobalData.currentFloatJot.title);
        TextView noteMessage = (TextView)findViewById(R.id.tVnoteMessage001);
        noteMessage.setText(AppGlobalData.currentFloatJot.message);

        TextView user = (TextView) findViewById(R.id.textView3);

        int id = AppGlobalData.currentFloatJot.user_id;

        String array[] = {"sabari360NoScope","SaifIsAWESOME!"};
        user.setText(array[AppGlobalData.currentFloatJot.user_id - 100]);
    }
}
