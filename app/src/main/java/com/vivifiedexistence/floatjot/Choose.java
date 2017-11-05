package com.vivifiedexistence.floatjot;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Choose extends AppCompatActivity {
    TextView sin;
    LinearLayout circle;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_sign);

        mAuth = FirebaseAuth.getInstance();
        mListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null){
                    Intent i = new Intent(Choose.this,MapsActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    Log.d("TAG", "onAuthStateChanged:signed_out");
                }
            }
        };

        circle = (LinearLayout)findViewById(R.id.circle);
        sin = (TextView)findViewById(R.id.sin);

        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(Choose.this,Signup.class);
                startActivity(it);

            }
        });
        sin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Choose.this,Signin.class);
                startActivity(it);
            }
        });

        TextView skip = (TextView)findViewById(R.id.tVskip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Choose.this,MapsActivity.class);
                startActivity(i);
            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mListener != null) {
            mAuth.removeAuthStateListener(mListener);
        }
    }
}
