package edroids.srmplacements.History;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import edroids.srmplacements.Reply.AwesomeReplyListPopulator;

/**
 * Created by Chinmay on 05-May-15.
 */
public class awesomeHistory extends AsyncTask<String, Void, String>
{
    private ProgressDialog Dialog;
    Context context;
    String RegNo;
    RecyclerView recList;
    TextView msg;

    GoogleCloudMessaging gcm;

    public awesomeHistory(Context context,ProgressDialog dialog,String RegNo,RecyclerView recList,TextView msg)
    {
        this.context=context;
        Dialog=dialog;
        this.RegNo=RegNo;
        this.recList=recList;
        this.msg=msg;
    }

    @Override
    protected void onPreExecute()
    {
        Dialog.setCancelable(false);
        Dialog.setMessage("Getting All Notifications");
        Dialog.show();
    }
    @Override
    protected String doInBackground(String... sturl)
    {
        String line=null;
        try
        {
            String link="http://ckapoor.esy.es/nitin/history.php?RegId="+RegNo;
            Log.w("link", link);
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
        Log.w("reply",result);
        JSONObject obj=new JSONObject();
        try{
            obj=new JSONObject(result);
            AwesomeHistoryListPopulator h=new AwesomeHistoryListPopulator(context,obj,recList);
            h.createarrays();
        }
        catch (JSONException e){
            recList.setVisibility(View.GONE);
            msg.setVisibility(View.VISIBLE);
        }
    }
}
