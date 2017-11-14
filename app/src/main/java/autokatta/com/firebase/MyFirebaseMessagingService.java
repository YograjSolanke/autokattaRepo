package autokatta.com.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Set;

import autokatta.com.AutokattaMainActivity;
import autokatta.com.R;

/**
 * Created by Deepak on 24-April-17.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        //Calling method to generate notification

        if (NotificationListener.isAppIsInBackground(getApplicationContext())) {
            Log.e("Background", "Back");
            Log.i("From-", "->" + remoteMessage.getFrom());
            Log.i("Data", "->" + remoteMessage.getData());
            showNotificationMessages(remoteMessage.getData().entrySet());
        } else {
            Log.i("From-", "->" + remoteMessage.getFrom());
            Log.i("Data", "->" + remoteMessage.getData());
            sendNotification(remoteMessage.getData().entrySet());
        }
    }

    /*
    If Application is in background...
     */
    private void showNotificationMessages(Set<Map.Entry<String, String>> data) {
        //PendingIntent callIntent = null;
        PendingIntent pendingIntent = null;
        //PendingIntent likeIntent = null;
        String body = "";
        String name = "";
        //String like = "";
        Bundle bundle = new Bundle();
        if (data != null) {
            for (Map.Entry<String, String> entry : data) {
                //Intent like = new Intent(this, AutokattaMainActivity.class);
                ///Intent call = new Intent(Intent.ACTION_DIAL);
                String key = entry.getKey();
                String value = entry.getValue();

                Log.d("Autokatta-Back", "key, " + key + " value " + value);

                if (key.equals("name")) {
                    name = value;
                    bundle.putString("name", name);
                }

                if (key.equals("body")) {
                    body = value;
                    bundle.putString("body", body);

                }

                if (key.equals("contact")) {
                    bundle.putString("contact", value);
                }

                if (key.equals("Like")) {
                    bundle.putString("Like", value);
                }
                //like.putExtra("key", entry.getKey());
                //like.putExtra("value", entry.getValue());


                Intent intent = new Intent(this, AutokattaMainActivity.class);
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pendingIntent = PendingIntent.getActivity(this, 0, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                /*if (key.equals("contact")) {
                    call.setData(Uri.parse("tel:" + value));
                    callIntent = PendingIntent.getActivity(this, 0, call,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                }
                if (key.equals("Like")) {
                    like.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    likeIntent = PendingIntent.getActivity(this, 0, like,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                }*/
            }
        } else {
            Intent intent = new Intent(this, AutokattaMainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        }

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo48x48)
                .setContentTitle("Autokatta")
                .setContentText(name + " " + body)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        //.setContentIntent(likeIntent)
        //.setContentIntent(callIntent)
        //.addAction(R.mipmap.new_like_white, "Like", likeIntent)
        //.addAction(R.mipmap.new_call_white, "Call", callIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }

    /*
    When application is in foreground...
     */
    private void sendNotification(Set<Map.Entry<String, String>> data) {
        //PendingIntent callIntent = null;
        //PendingIntent likeIntent = null;
        PendingIntent pendingIntent = null;
        String body = "";
        String name = "";
        Bundle bundle = new Bundle();
        if (data != null) {
            for (Map.Entry<String, String> entry : data) {
                //Intent like = new Intent(this, AutokattaMainActivity.class);
                //Intent call = new Intent(Intent.ACTION_DIAL);
                String key = entry.getKey();
                String value = entry.getValue();
                Log.d("Autokatta-fore", "key, " + key + " value " + value);

                if (key.equals("name")) {
                    name = value;
                    bundle.putString("name", name);
                }

                if (key.equals("body")) {
                    body = value;
                    bundle.putString("body", body);
                }

                if (key.equals("contact")) {
                    bundle.putString("contact", value);
                }

                if (key.equals("Like")) {
                    bundle.putString("Like", value);
                }

                Intent intent = new Intent(this, AutokattaMainActivity.class);
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pendingIntent = PendingIntent.getActivity(this, 0, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                //like.putExtra("key", entry.getKey());
                //like.putExtra("value", entry.getValue());

                /*if (key.equals("contact")) {
                    call.setData(Uri.parse("tel:" + value));
                    callIntent = PendingIntent.getActivity(this, 0, call,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                }
                if (key.equals("Like")) {
                    like.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    likeIntent = PendingIntent.getActivity(this, 0, like,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                }*/
                Log.i("KEYYYYYYYYYYY", "->" + entry.getKey());
            }
        }

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo48x48)
                .setContentTitle("Autokatta")
                .setContentText(name + " " + body)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        //.setContentIntent(callIntent)
        //.setContentIntent(likeIntent)
        //.addAction(R.mipmap.new_like_white, "Like", likeIntent)
        //.addAction(R.mipmap.new_call_white, "Call", callIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}
