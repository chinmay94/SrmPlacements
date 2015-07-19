package edroids.srmplacements.PushNotificationStuff;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import edroids.srmplacements.R;

/**
 * Created by Chinmay on 05-Apr-15.
 */
public class NotificationIntentService extends IntentService
{
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    public NotificationIntentService()
    {
        super("GcmIntentService");
    }
    public static final String TAG = "NotificationIntentService";

    @Override
    protected void onHandleIntent(Intent intent)
    {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty())
        {
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType))
            {
                sendNotification("Send error: " + extras.toString());
            }
            else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType))
            {
                sendNotification("Deleted messages on server: "+ extras.toString());
            }
            else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType))
            {
                //sendNotification("Message:"+extras.getString("m")+" "+ extras.get(Config.MESSAGE_KEY));
                sendNotification(extras.getString("m"));
            }
        }
        MessageBroadcastReceiver.completeWakefulIntent(intent);
    }

    private void sendNotification(String msg)
    {
        Log.w("hello",msg);
        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent i=new Intent(this,MessageView.class);
        Bundle b=new Bundle();
        b.putString("message",msg);
        i.putExtras(b);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
        //PendingIntent contentIntent = PendingIntent.getActivity(this, 0, i, 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.srmlogo).setContentTitle("Placement Department").setStyle(new NotificationCompat.BigTextStyle().bigText(msg)).setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mBuilder.setAutoCancel(true);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}