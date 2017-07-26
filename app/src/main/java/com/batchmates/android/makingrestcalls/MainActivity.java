package com.batchmates.android.makingrestcalls;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.batchmates.android.makingrestcalls.Model.weather.Weatherdata;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String BASEURL = "https://api.github.com/users/rosatorichard";
    private static final String TAG = "MAIN ACTIVITY";
    private static final String BASE_URL_GITREPO = "https://api.github.com/";
    private TextView text;
    private MyReciever myReciever;
    private IntentFilter intentFilter;
    private Observable<Weatherdata> weatherdataObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text=(TextView)findViewById(R.id.textTochange);
    }

    public void buttonClicker(View view) {


        switch (view.getId())
        {
            case R.id.btnHTTP:
                Intent intent=new Intent(this, HTTPMyIntentService.class);
                startService(intent);
                break;

            case R.id.btnOkHTTP:
                final OkHttpClient client = new OkHttpClient();
                final Request request= new Request.Builder().url(BASEURL).build();


//                for later on execute
//                String result =client.newCall(request).execute().body().string();

//
//                Intent intent2 =new Intent(this,OkHTTPMyIntentService.class);
//                startService(intent2);



                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "run: You Are here");
                        try {
                            String result=client.newCall(request).execute().body().string();
                            Gson gson =new Gson();
                            HithubProfile hithubProfile=gson.fromJson(result,HithubProfile.class);
//                            String s =hithubProfile.getName().toString();
                            Log.d(TAG, "onResponse: "+ hithubProfile.getLogin());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();


//                client.newCall(request).enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        Log.d(TAG, "onFailure: "+ "FAILURE");
//                    }
//
//                    @Override
//                    public void onResponse(Call call, final Response response) throws IOException {
//
//                        Log.d(TAG, "onResponse: YATAA. Victory is ours "+ response.body().string());
//
//                        String s =response.body().string();
//
//                        Handler handler=new Handler(Looper.getMainLooper());
//
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                //text.setText(s);
//                            }
//                        });
//                    }
//                });
                break;

            case R.id.btnRETROFIT:

                Retrofit retro =new Retrofit.Builder().baseUrl(BASE_URL_GITREPO).addConverterFactory(GsonConverterFactory.create()).build();


                final GitHubService githubService= retro.create(GitHubService.class);
                retrofit2.Call<List<GithubRepo>>callToGetRepos = githubService.callProfile("rosatorichard");

                callToGetRepos.enqueue(new retrofit2.Callback<List<GithubRepo>>() {
                    @Override
                    public void onResponse(retrofit2.Call<List<GithubRepo>> call, retrofit2.Response<List<GithubRepo>> response) {
                        for (int i = 0; i <5 ; i++) {
                            Log.d(TAG, "onResponse: " +response.body().get(i).getName());

                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<List<GithubRepo>> call, Throwable t) {

                    }
                });
                break;

            case R.id.btnRETROClass:

                Log.d(TAG, "buttonClicker: Clicked");
                retrofit2.Call<Weatherdata> myCall=RetroFitHelper.callWeatherData();
                myCall.enqueue(new retrofit2.Callback<Weatherdata>() {
                    @Override
                    public void onResponse(retrofit2.Call<Weatherdata> call, retrofit2.Response<Weatherdata> response) {
                        Toast.makeText(MainActivity.this,"The Data "+response.body().getCity().getName(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(retrofit2.Call<Weatherdata> call, Throwable t) {

                        Log.d(TAG, "onFailure: We are failing");
                    }
                });
                break;

            case R.id.observable:
                Observable<Weatherdata> myObserve=RetroFitHelper.getWeatherDataObservable();
                myObserve.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Weatherdata>() {
                            @Override
                            public void onCompleted() {
                                Log.d(TAG, "onCompleted: Completed");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d(TAG, "onError: "+ e.toString());
                            }

                            @Override
                            public void onNext(Weatherdata weatherdata) {
                                Log.d(TAG, "onNext: "+weatherdata.getCity().getPopulation());
                                //recycler stuff can go here

                            }
                        });
                break;

        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        myReciever=new MyReciever();
        intentFilter= new IntentFilter();
        intentFilter.addAction("SERVE");

        registerReceiver(myReciever,intentFilter);
    }


    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(myReciever);
    }

    public class MyReciever extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent) {

            text.setText(intent.getStringExtra("EXTRA"));

        }
    }

}
