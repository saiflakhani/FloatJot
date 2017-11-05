package com.vivifiedexistence.floatjot;

import android.content.Context;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.vivifiedexistence.floatjot.AppGlobalData.notesList;

public class CreateAndViewActivity extends AppCompatActivity {
    Context c;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference notes = db.getReference("notes");
    DatabaseReference meow = db.getReference("meow");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        c= this;
        //meow.setValue("meow");
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
                BlockProfanity.checkProfanity(c,attributes.message);
                BlockProfanity.checkProfanity(c,attributes.title);
                //attributes.title = attributes.title.replaceAll(" ","%20");
                //attributes.message = attributes.message.replaceAll(" ","%20");
                if(notesList==null){
                    notesList = new ArrayList<FloatNoteAttributes>();
                    notesList.add(attributes);
                    //notes.child("floatnotes").setValue(AppGlobalData.notesList);
                    //notes.child(attributes.title).setValue(attributes);
                }

                else{
                    notesList.add(attributes);
                }
                    Log.d("TAG","Notes List is not Null");
                notes.child("floatnotes").setValue(notesList);
                HttpHandler handler = new HttpHandler();
                handler.makeServiceCall("http://192.168.31.143/floatjot/addNote.php?user_id=100&title=" + attributes.title
                        + "&message=" + attributes.message + "&lat=" + attributes.lat + "&lng=" + attributes.lon + "&vx=" +
                       attributes.vx + "&vy=" + attributes.vy + "&vz=" + attributes.vz);

                sendData(attributes);
                finish();
            }
        });

    }

    public void sendData(FloatNoteAttributes attr){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.43.129/floatjot/")
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
