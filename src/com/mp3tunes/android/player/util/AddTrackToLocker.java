package com.mp3tunes.android.player.util;

import java.io.IOException;

import com.binaryelysium.mp3tunes.api.HttpClientCaller;
import com.binaryelysium.mp3tunes.api.InvalidSessionException;
import com.binaryelysium.mp3tunes.api.LockerException;
import com.binaryelysium.mp3tunes.api.RemoteMethod;
import com.binaryelysium.mp3tunes.api.Track;
import com.mp3tunes.android.player.LocalId;
import com.mp3tunes.android.player.Music;
import com.mp3tunes.android.player.R;
import com.mp3tunes.android.player.activity.Player;
import com.mp3tunes.android.player.content.Queries.MakeQueryException;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.util.Log;

public class AddTrackToLocker extends AsyncTask<Void, Void, Boolean>
{
    private Track   mTrack;
    private Context mContext;
    
    private static final int NOTIFY_ID = 10911252; // mp3 + 1 in ascii

    public AddTrackToLocker(Track track, Context context)
    {
        mTrack   = track;
        mContext = context;
    }

    @Override
    protected Boolean doInBackground(Void... params)
    {
        if (!LocalId.class.isInstance(mTrack.getId())) return true;
        String path = mTrack.getPlayUrl(0);
        Log.w("Mp3Tunes", "Trying to upload: " + path + " to locker");
        
        try {
            RemoteMethod method = new RemoteMethod.Builder(RemoteMethod.METHODS.LOCKER_PUT)
                .addFileKey("")
                .create();
            if (HttpClientCaller.getInstance().put(method, path)) {
                Music.getDb(mContext).refreshSearch(mTrack.getTitle());
                sendNotification(mTrack, true);
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidSessionException e) {
            e.printStackTrace();
        } catch (SQLiteException e) {
            e.printStackTrace();
        } catch (LockerException e) {
            e.printStackTrace();
        } catch (MakeQueryException e) {
            e.printStackTrace();
        }
        sendNotification(mTrack, false);
        return false;
    }
    
    private void sendNotification(Track t, boolean status)
    {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nm = (NotificationManager)mContext.getSystemService(ns);
        
        int icon = R.drawable.logo_statusbar;
        long when = System.currentTimeMillis();
        CharSequence tickerText;
        
        if (status)
            tickerText = t.getTitle() + " added to locker";
        else
            tickerText = "Failed to add " + t.getTitle() + " to locker";
        
        Notification notification = new Notification(icon, tickerText, when);
        Intent        intent        = new Intent(mContext, Player.class);
        PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, intent, 0);
        notification.setLatestEventInfo(mContext, "Mp3Tunes", tickerText, contentIntent);
        
        nm.notify(NOTIFY_ID, notification);
        nm.cancel(NOTIFY_ID);
    }
}