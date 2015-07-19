package edroids.srmplacements;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;

import edroids.srmplacements.LoginAndRegister.LoginActivity;
import edroids.srmplacements.MainUi.MainActivity;


public class SplashActivity extends Activity
{

    SharedPreferences sharedpreferences;
    Intent i;
    private static int SPLASH_TIME_OUT = 1500;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        sharedpreferences=getSharedPreferences("CoreData", Context.MODE_PRIVATE);
        if(sharedpreferences.contains("RegistrationNumber") && sharedpreferences.contains("DeviceId"))
            i=new Intent(SplashActivity.this, MainActivity.class);
        else
            i=new Intent(SplashActivity.this, LoginActivity.class);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}