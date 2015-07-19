package edroids.srmplacements.PushNotificationStuff;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import edroids.srmplacements.MainUi.MainActivity;
import edroids.srmplacements.R;

/**
 * Created by Chinmay on 05-Apr-15.
 */
public class MessageView extends Activity
{
    TextView nid;
    TextView ndate;
    TextView ncont;

    EditText reply;
    EditText comment;
    Button submit;
    ProgressDialog dialog;

    String notifid="";
    String notifdate="";
    String notifcontent="";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notif);
        Bundle b= getIntent().getExtras();
        String message=b.getString("message");
        try {
            JSONObject obj=new JSONObject(message);
            notifid=obj.getString("id");
            notifdate=obj.getString("date");
            notifcontent=obj.getString("content");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dialog=new ProgressDialog(MessageView.this);

        nid=(TextView)findViewById(R.id.nid);
        ndate=(TextView)findViewById(R.id.ndate);
        ncont=(TextView)findViewById(R.id.ncont);

        reply=(EditText)findViewById(R.id.replyText);
        comment=(EditText)findViewById(R.id.comment);
        submit=(Button)findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SharedPreferences prefs=MessageView.this.getSharedPreferences("CoreData", Context.MODE_PRIVATE);
                final String regno=prefs.getString("RegistrationNumber", null);
                final AsyncTask<String, Void, String> task = new ReplyTask(MessageView.this,dialog,regno,notifid,reply.getText().toString(),comment.getText().toString()).execute();
            }
        });
        nid.setText(notifid);
        ndate.setText(notifdate);
        ncont.setText(notifcontent);
    }
}
