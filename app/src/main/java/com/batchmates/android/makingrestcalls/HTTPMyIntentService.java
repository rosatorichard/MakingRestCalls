package com.batchmates.android.makingrestcalls;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class HTTPMyIntentService extends IntentService {

    private static final String TAG = "Service HTTP";
    private String webSite ="https://api.github.com/users/rosatorichard";
    private HttpURLConnection urlConnection = null;

    public HTTPMyIntentService() {
        super("HTTPMyIntentService");
    }



    @Override
    protected void onHandleIntent(Intent intent) {

        try {
            URL url= new URL(webSite);

            urlConnection=(HttpURLConnection)url.openConnection();

            InputStream in=new BufferedInputStream(urlConnection.getInputStream());

            Scanner scan=new Scanner(in);

            while(scan.hasNext())
            {
                Log.d(TAG, "onHandleIntent: "+scan.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (urlConnection!=null)
            {
                urlConnection.disconnect();
            }
        }

    }

}
