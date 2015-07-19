package edroids.srmplacements.History;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edroids.srmplacements.PushNotificationStuff.MessageView;
import edroids.srmplacements.R;

/**
 * Created by Chinmay on 05-May-15.
 */
public class AwesomeHistoryListPopulator{

    Context context;
    JSONObject obj;
    RecyclerView recList;
    JSONArray elements;

    public AwesomeHistoryListPopulator(Context context,JSONObject obj,RecyclerView recList)
    {
        this.context=context;
        this.obj=obj;
        this.recList=recList;
    }

    public void createarrays() throws  JSONException
    {
        final SharedPreferences prefs = context.getSharedPreferences("NotifDataReply", Context.MODE_PRIVATE);
        elements=obj.getJSONArray("notifs");

        ArrayList<ContactInfo> result=new ArrayList<>();

        for(int i=0;i<elements.length();i++){
            JSONObject current=elements.getJSONObject(i);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(current.getString("id"), current.getString("reply").equalsIgnoreCase("-")?"false":"true");
            editor.commit();

            ContactInfo ci=new ContactInfo();
            ci.id=current.getString("id");
            ci.content=current.getString("content");
            ci.date=current.getString("date");
            ci.time=current.getString("time");
            ci.reply=current.getString("reply");
            ci.comment=current.getString("comment");
            ci.company=current.getString("cn");
            result.add(ci);
        }

        ContactAdapter ca=new ContactAdapter(result,context);

        recList.setAdapter(ca);

        recList.addOnItemTouchListener(new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position)
            {
                final SharedPreferences prefs = context.getSharedPreferences("NotifDataReply", Context.MODE_PRIVATE);
                try {
                    String id=elements.getJSONObject(position).getString("id");
                    if(prefs.getString(id,"false").equalsIgnoreCase("false")){

                        ImageView im=(ImageView)view.findViewById(R.id.statusimg);
                        im.setImageDrawable(context.getResources().getDrawable(R.drawable.tick));

                        Intent x=new Intent(context,MessageView.class);
                        Bundle b=new Bundle();
                        b.putString("message",elements.getJSONObject(position).toString());
                        x.putExtras(b);
                        context.startActivity(x);
                    }
                    else{
                        Toast.makeText(context,"DETAILS PAGE",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.w("test", "" + position);
            }
        }));
    }
}
