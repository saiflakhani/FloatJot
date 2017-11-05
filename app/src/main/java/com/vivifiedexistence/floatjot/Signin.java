package com.vivifiedexistence.floatjot;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signin extends AppCompatActivity {

    FirebaseAuth mAuth;

    ImageView sback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        sback = (ImageView)findViewById(R.id.sinb);
        mAuth = FirebaseAuth.getInstance();
        sback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

        final EditText eTemail = (EditText)findViewById(R.id.usrEmail);
        final EditText eTpassword = (EditText)findViewById(R.id.pswrd);
        TextView sin = (TextView) findViewById(R.id.sin);

        sin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = eTemail.getText().toString();
                String password = eTpassword.getText().toString();
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(Signin.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("FIREBASE", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Toast.makeText(Signin.this, "Failed. Check Email integrity",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            //Intent i = new Intent(Signin.this,MapsActivity.class);
                            //startActivity(i);
                        }
                    }
                });
            }
        });

    }
}
