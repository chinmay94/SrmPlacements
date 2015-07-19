package edroids.srmplacements.LoginAndRegister;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import edroids.srmplacements.R;

/**
 * Created by Chinmay on 05-Apr-15.
 */
public class LoginActivity extends Activity
{
    Button send;
    ProgressDialog Dialog;
    EditText regid;
    EditText pw;

    AsyncTask<String, Void, JSONObject> task;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Dialog=new ProgressDialog(LoginActivity.this);
        regid=(EditText)findViewById(R.id.regid);
        pw=(EditText)findViewById(R.id.pw);
        send=(Button)findViewById(R.id.login);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task = new LoginTask(LoginActivity.this,regid.getText().toString(),pw.getText().toString(),Dialog).execute();
            }
        });
    }
}
