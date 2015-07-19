package edroids.srmplacements.Reply;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edroids.srmplacements.PushNotificationStuff.MessageView;

/**
 * Created by Chinmay on 05-May-15.
 */
public class AwesomeReplyListPopulator {

    Context context;
    JSONObject obj;
    RecyclerView recList;
    JSONArray elements;

    public AwesomeReplyListPopulator(Context context, JSONObject obj, RecyclerView recList)
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
            editor.putString(current.getString("id"), current.getString("reply")=="-"?"false":"true");
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

                        Intent x=new Intent(context,MessageView.class);
                        Bundle b=new Bundle();
                        b.putString("message",elements.getJSONObject(position).toString());
                        x.putExtras(b);
                        context.startActivity(x);

                        //Toast.makeText(context,"REPLY PAGE",Toast.LENGTH_SHORT).show();
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

        /*recList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView t=(TextView)v.findViewById(R.id.company);
                Toast.makeText(context,""+t.getText(),Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    /*public boolean populate() throws JSONException
    {
        final SharedPreferences prefs = context.getSharedPreferences("NotifDataReply", Context.MODE_PRIVATE);

        final JSONArray elements=obj.getJSONArray("notifs");
        ArrayList<String> toshow=new ArrayList<>();
        for(int i=0;i<elements.length();i++){
            JSONObject current=elements.getJSONObject(i);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(current.getString("id"), current.getString("reply"));
            editor.commit();

            toshow.add(current.getString("content"));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,android.R.layout.simple_list_item_1,toshow);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                try {
                    if(prefs.getString(elements.getJSONObject(position).getString("id"),null).equalsIgnoreCase("-")){
                        Intent x=new Intent(context,MessageView.class);
                        Bundle b=new Bundle();
                        b.putString("message",elements.getJSONObject(position).toString());
                        x.putExtras(b);
                        context.startActivity(x);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return true;
    }*/
}
