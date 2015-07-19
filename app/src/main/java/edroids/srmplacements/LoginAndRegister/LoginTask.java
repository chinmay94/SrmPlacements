package edroids.srmplacements.LoginAndRegister;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import edroids.srmplacements.R;

/**
 * Created by Chinmay on 05-Apr-15.
 */
public class LoginTask extends AsyncTask<String,Void,JSONObject>
{
    private ProgressDialog Dialog;
    Context context;
    String regid;
    String pw;

    public LoginTask(Context context, String regid, String pw, ProgressDialog dialog)
    {
        this.context=context;
        this.regid=regid;
        this.pw=pw;
        Dialog=dialog;
    }
    @Override
    protected void onPreExecute()
    {
        Dialog.setCancelable(false);
        Dialog.setMessage("Logging in");
        Dialog.show();
    }
    @Override
    protected JSONObject doInBackground(String[] args)
    {
        Log.w("hello","started");
        JSONObject reply=null;
        try{
            Map<String, String> paramsMap = new HashMap<>();
            paramsMap.put("RegId",regid);
            paramsMap.put("PW",pw);
            StringBuilder postBody = new StringBuilder();
            Iterator<Map.Entry<String, String>> iterator = paramsMap.entrySet().iterator();
            while (iterator.hasNext())
            {
                Map.Entry<String, String> param = iterator.next();
                postBody.append(param.getKey()).append('=').append(param.getValue());
                if (iterator.hasNext())
                    postBody.append('&');
            }
            String body = postBody.toString();
            byte[] bytes = body.getBytes();

            //String loginurl=context.getString(R.string.baseUrl)+context.getString(R.string.loginPage);
            String loginurl="http://ckapoor.esy.es/nitin/login.php";
            URL serverUrl = new URL(loginurl);

            HttpURLConnection httpCon = null;
            httpCon = (HttpURLConnection) serverUrl.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setUseCaches(false);
            httpCon.setFixedLengthStreamingMode(bytes.length);
            httpCon.setRequestMethod("POST");
            httpCon.setRequestProperty("Connection","Close");
            httpCon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            OutputStream out = httpCon.getOutputStream();
            out.write(bytes);
            out.close();

            String replytemp="";
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
                String line="";
                while((line=reader.readLine())!=null)
                    replytemp+=line;
                Log.w("LoginTaskReply",replytemp);
                reply=new JSONObject(replytemp);
            }
            catch (Exception e)
            {
                Log.w("error",e.toString());
                reply=null;
            }
        }
        catch (Exception e)
        {
            Log.w("error do in back", e.toString());
        }
        return reply;
    }
    @Override
    protected void onPostExecute(JSONObject reply)
    {
        Dialog.dismiss();
        try {
            if (reply.getString("result").equals("false")) {
                Toast.makeText(context, "Wrong Registration Number or Password", Toast.LENGTH_SHORT).show();
            } else {
                System.out.println(reply.toString());
                final AsyncTask<String, Void, String> task = new RegisterTask(context, reply, Dialog).execute();
            }
        }
        catch (Exception e){
            Log.w("error post execute", e.toString());
        }
    }
}
