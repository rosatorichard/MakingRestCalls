package com.batchmates.android.makingrestcalls;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class OkHTTPMyIntentService extends IntentService {
    private static final String PLACETOGET = "http://www.mocky.io/v2/5963a049100000aa138f1347";
    private String result;

    public OkHTTPMyIntentService() {
        super("OkHTTPMyIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(PLACETOGET).build();


        try {
            result = client.newCall(request).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent2 = new Intent("SERVE");
        intent2.putExtra("EXTRA", result);
        sendBroadcast(intent2);
    }

}