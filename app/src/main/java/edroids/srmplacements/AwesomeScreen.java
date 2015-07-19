package edroids.srmplacements;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import org.json.JSONObject;

import edroids.srmplacements.LoginAndRegister.LoginActivity;
import edroids.srmplacements.LoginAndRegister.LoginTask;
import edroids.srmplacements.MainUi.MainActivity;

/**
 * Created by Chinmay on 04-May-15.
 */
public class AwesomeScreen extends Activity{

    RelativeLayout main;
    RelativeLayout logobox;
    RelativeLayout loginbox;
    boolean gotoMain;
    private static int SPLASH_TIME_OUT = 1500;

    Button send;
    ProgressDialog Dialog;
    EditText regid;
    EditText pw;
    AsyncTask<String, Void, JSONObject> task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.awesome_screen);

        main=(RelativeLayout)findViewById(R.id.main);
        logobox=(RelativeLayout)findViewById(R.id.logobox);
        loginbox=(RelativeLayout)findViewById(R.id.loginbox);

        Dialog=new ProgressDialog(AwesomeScreen.this);
        regid=(EditText)findViewById(R.id.regid);
        pw=(EditText)findViewById(R.id.pw);
        send=(Button)findViewById(R.id.login);

        final Animation logoboxanim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        logobox.startAnimation(logoboxanim);

        gotoMain=false;
        SharedPreferences sharedpreferences=getSharedPreferences("CoreData", Context.MODE_PRIVATE);
        if(sharedpreferences.contains("RegistrationNumber") && sharedpreferences.contains("DeviceId"))
            gotoMain=true;

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task = new LoginTask(AwesomeScreen.this,regid.getText().toString(),pw.getText().toString(),Dialog).execute();
            }
        });

        new Handler().postDelayed(new Runnable() {
            public void run() {
                if(gotoMain){
                    Intent i=new Intent(AwesomeScreen.this, MainActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.activity_fade_in,R.anim.activity_fade_out);
                    finish();
                }
                else{
                    /*Intent i=new Intent(AwesomeScreen.this, LoginActivity.class);
                    startActivity(i);
                    finish();*/

                    /*TransitionDrawable transition = (TransitionDrawable) main.getBackground();
                    transition.startTransition(800);*/

                    Animation secondanim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.move_up);
                    logobox.startAnimation(secondanim);

                    loginbox.setVisibility(View.VISIBLE);
                    Animation loginboxanim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in2);
                    loginbox.startAnimation(loginboxanim);
                }
            }
        }, SPLASH_TIME_OUT);


    }
}
