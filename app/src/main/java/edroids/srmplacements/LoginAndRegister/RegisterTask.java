package edroids.srmplacements.LoginAndRegister;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import edroids.srmplacements.MainUi.MainActivity;
import edroids.srmplacements.R;

/**
 * Created by Chinmay on 05-Apr-15.
 */
public class RegisterTask extends AsyncTask<String, Void, String> {
    private ProgressDialog Dialog;
    Context context;
    JSONObject reply;
    String deviceid;
    String regid;

    RegisterTask(Context context, JSONObject reply, ProgressDialog dialog)
    {
        this.context=context;
        this.reply=reply;
        try{
            this.regid=reply.get("Register_No").toString();
        }
        catch(Exception e){
            Log.w("error",e);
        }
        Dialog=dialog;
    }
    @Override
    protected void onPreExecute()
    {
        Dialog.setCancelable(false);
        Dialog.setMessage("Registering with SRM server");
        Dialog.show();
    }
    @Override
    protected String doInBackground(String args[])
    {
        String reply="false";
        try{
            GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
            if (gcm == null)
                gcm = GoogleCloudMessaging.getInstance(context);
            deviceid = gcm.register(context.getString(R.string.projectId));

            Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("DeviceId", deviceid);
            paramsMap.put("RegId",regid);
            StringBuilder postBody = new StringBuilder();
            Iterator<Map.Entry<String, String>> iterator = paramsMap.entrySet().iterator();
            while (iterator.hasNext())
            {
                Map.Entry<String, String> param = iterator.next();
                postBody.append(param.getKey()).append('=').append(param.getValue());
                if (iterator.hasNext())
                {
                    postBody.append('&');
                }
            }
            String body = postBody.toString();
            byte[] bytes = body.getBytes();

            //String link=context.getString(R.string.baseUrl)+context.getString(R.string.registrationPage);
            String link="http://ckapoor.esy.es/nitin/adddev.php";
            URL serverUrl = new URL(link);
            HttpURLConnection httpCon = null;
            httpCon = (HttpURLConnection) serverUrl.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setUseCaches(false);
            httpCon.setFixedLengthStreamingMode(bytes.length);
            httpCon.setRequestMethod("POST");
            httpCon.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
            OutputStream out = httpCon.getOutputStream();
            out.write(bytes);
            out.close();

            int status = httpCon.getResponseCode();
            if (status == 200)
                reply = "true";

            if (httpCon != null)
                httpCon.disconnect();
        }
        catch (Exception e){
            Log.w("error", e.toString());
        }
        return reply;
    }
    @Override
    protected void onPostExecute(String result)
    {
        Log.w("RegisterTaskReply",result);
        Dialog.dismiss();
        if(result.equals("false"))
            Toast.makeText(context, "Some error cccured. Please try again later", Toast.LENGTH_SHORT).show();
        else{

            try {
                SharedPreferences prefs = context.getSharedPreferences("CoreData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("RegistrationNumber", regid);
                editor.putString("DeviceId", deviceid);
                editor.commit();

                prefs = context.getSharedPreferences("StudentData", Context.MODE_PRIVATE);
                editor = prefs.edit();
                editor.putString("StudentName", reply.getString("Student_Name"));
                editor.putString("RegistrationNumber", reply.getString("Register_No"));
                editor.putString("CGPA", reply.getString("CGPA_All_Sem"));
                editor.commit();
            }
            catch(Exception e){
                Log.w("error",e);
            }

            Toast.makeText(context,"Registration Successful",Toast.LENGTH_SHORT).show();
            Intent i=new Intent(context, MainActivity.class);
            context.startActivity(i);
            ((Activity)context).overridePendingTransition(R.anim.activity_fade_in,R.anim.activity_fade_out);
            ((Activity)context).finish();
        }
    }
}