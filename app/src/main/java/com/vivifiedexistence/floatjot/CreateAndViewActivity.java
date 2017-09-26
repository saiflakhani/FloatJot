package com.vivifiedexistence.floatjot;

import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateAndViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Button saveBtn = (Button)findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText title = (EditText)findViewById(R.id.noteTitle);
                EditText message = (EditText)findViewById(R.id.noteBody);
                FloatNoteAttributes attributes = new FloatNoteAttributes();
                attributes.title = title.getText().toString();
                attributes.message = message.getText().toString();
                attributes.lat = String.valueOf(AppGlobalData.currentLocation.getLatitude());
                attributes.lon = String.valueOf(AppGlobalData.currentLocation.getLongitude());
                attributes.vx = String.valueOf(AppGlobalData.currentVec.x);
                attributes.vy = String.valueOf(AppGlobalData.currentVec.y);
                attributes.vz = String.valueOf(AppGlobalData.currentVec.z);
                //attributes.title = attributes.title.replaceAll(" ","%20");
                //attributes.message = attributes.message.replaceAll(" ","%20");
                AppGlobalData.notesList.add(attributes);
                HttpHandler handler = new HttpHandler();
                //handler.makeServiceCall("http://192.168.43.27/floatjot/addNote.php?user_id=100&title=" + attributes.title
                      //  + "&message=" + attributes.message + "&lat=" + attributes.lat + "&lng=" + attributes.lon + "&vx=" +
                        //attributes.vx + "&vy=" + attributes.vy + "&vz=" + attributes.vz);

                sendData(attributes);
                finish();
            }
        });
    }

    public void sendData(FloatNoteAttributes attr){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://172.20.10.9/floatjot/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<FloatNoteAttributes> call = service.addNote(100,attr.title,attr.message,attr.lat,attr.lon,attr.vx,attr.vy,attr.vz);
        call.enqueue(new Callback<FloatNoteAttributes>() {
            @Override
            public void onResponse(Call<FloatNoteAttributes> call, Response<FloatNoteAttributes> response) {
//                Toast.makeText(CreateAndViewActivity.this,"YAYY",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<FloatNoteAttributes> call, Throwable t) {
//                Toast.makeText(CreateAndViewActivity.this,"Check Internet",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
