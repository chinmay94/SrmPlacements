package edroids.srmplacements.PushNotificationStuff;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

/**
 * Created by Chinmay on 05-Apr-15.
 */
public class ReplyTask extends AsyncTask<String, Void, String>
{
    private ProgressDialog Dialog;
    Context context1;
    String notifid;
    String comment;
    String regno;
    String replyStr;

    ReplyTask(Context context,ProgressDialog dialog,String regno, String notifid,String replyString,String comment)
    {
        context1=context;
        Dialog=dialog;
        this.regno=regno;
        replyStr=replyString;
        this.notifid=notifid;
        this.comment=comment;
    }

    @Override
    protected void onPreExecute()
    {
        Dialog.setCancelable(false);
        Dialog.setMessage("Replying");
        Dialog.show();
    }
    @Override
    protected String doInBackground(String... sturl)
    {
        String line=null;
        try
        {
            /*String repUrl=prefs.getString("repurl","error");*/
            //String link="http://ckapoor.esy.es/"+repUrl+"?RegId="+regno+"&Reply="+replyStr;
            //String link="";
            String link="http://ckapoor.esy.es/nitin/reply.php?RegId="+regno+"&NotifId="+notifid+"&Reply="+replyStr+"&Comment="+comment;
            Log.w("check", link);
            HttpClient client=new DefaultHttpClient();
            HttpGet request=new HttpGet();
            request.setURI(new URI(link));
            HttpResponse response=client.execute(request);
            HttpEntity entity=response.getEntity();
            BufferedReader br=new BufferedReader(new InputStreamReader(entity.getContent()));
            line=br.readLine();
            br.close();
        }
        catch(Exception e)
        {
            line="Problem occured";
            System.out.println(e);
        }
        return line;
    }
    @Override
    protected void onPostExecute(String result)
    {
        Dialog.dismiss();
        if(result.equalsIgnoreCase("success")){
            Toast.makeText(context1, "Successfully replied", Toast.LENGTH_SHORT).show();
            final SharedPreferences prefs = context1.getSharedPreferences("NotifDataReply", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(notifid, replyStr);
            editor.commit();

            ((Activity)context1).finish();
        }
        else{
            Toast.makeText(context1, "Failed. Please try again later.", Toast.LENGTH_SHORT).show();
        }
    }
}
