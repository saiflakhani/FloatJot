package com.vivifiedexistence.floatjot;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import commands.Command;
import gl.Color;
import gl.GL1Renderer;
import gl.GLFactory;
import gl.scenegraph.MeshComponent;
import system.ArActivity;
import system.DefaultARSetup;
import util.Vec;
import worldData.Obj;
import worldData.World;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Context c= this;
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Button b = (Button)findViewById(R.id.button);
        Button map = (Button)findViewById(R.id.startMap);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,MapsActivity.class);
                startActivity(i);
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArActivity.startWithSetup(MainActivity.this, new DefaultARSetup() {
                    @Override
                    public void addObjectsTo(GL1Renderer renderer, World world, GLFactory objectFactory) {
                        //world.add(objectFactory.newHexGroupTest(null));
                        final World myWorld = world;

                        MeshComponent cube = objectFactory.newCube(Color.blue());
                        getGuiSetup().addButtonToTopView(new Command() {
                            @Override
                            public boolean execute() {
                                Vec vector = myWorld.getMyCamera().getPositionOnGroundWhereTheCameraIsLookingAt();
                                Color c = Color.getRandomRGBColor();
                                c.alpha = 0.7f;
                                MeshComponent arrow = GLFactory.getInstance().newDiamond(c);
                                arrow.setPosition(vector);
                                myWorld.add(arrow);
                                arrow.setOnClickCommand(new Command() {
                                    @Override
                                    public boolean execute() {
                                        Intent i = new Intent(MainActivity.this,CreateAndViewActivity.class);
                                        startActivity(i);
                                        return false;
                                    }
                                });
                                return false;
                            }
                        },"Add A JotNote");
                        cube.setPosition(new Vec(-10,-20,10));
                        world.add(cube);
                        getGuiSetup().setTopViewCentered();
                        cube.setOnClickCommand(new Command() {
                            @Override
                            public boolean execute() {
                                Intent i = new Intent(MainActivity.this,CreateAndViewActivity.class);
                                startActivity(i);
                                return false;
                            }
                        });
                        /*GeoObj o = new GeoObj();
                        o.setComp(objectFactory.newCube());
                        // place 20 meters north of the user:
                        o.setVirtualPosition(new Vec(0, 20, 0));
                        o.setComp(new SimpleTooFarAwayComp(30, getCamera(), getActivity()));
                        world.add(o);*/
                    }
                });
            }
        });
    }


    public void createDialog(){
        final EditText input1 = new EditText(this);
        input1.setInputType(InputType.TYPE_CLASS_NUMBER);
        TableLayout.LayoutParams params = new TableLayout.LayoutParams();
        params.setMargins(5, 5, 5, 5);
        input1.setLayoutParams(params);
        final Context c = this;
        new AlertDialog.Builder(this)
                .setTitle("Add a Note")
                .setMessage("Leave it here")
                .setView(input1)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(c,"Saved Successfully",Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }


}
