package com.batchmates.android.makingrestcalls;

import com.batchmates.android.makingrestcalls.Model.weather.Weatherdata;

import java.util.Observable;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Android on 7/11/2017.
 */

public class RetroFitHelper {

    private static final String BASE_URL = "http://samples.openweathermap.org/";
    private static final String QUERY_ZIP="30339";
    private static final String APP_ID="b1b15e88fa797225412429c1c50c122a1";

    public static Retrofit Create()
    {

        //logging
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();



        Retrofit retro= new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retro;
    }



    public static Call<Weatherdata> callWeatherData()
    {
        Retrofit retrofit=Create();
        weatherService weatherService =retrofit.create(RetroFitHelper.weatherService.class);
        return weatherService.getWeatherdata(QUERY_ZIP,APP_ID);
    }



    public interface weatherService{

        @GET("data/2.5/forecast")
        Call<Weatherdata> getWeatherdata(@Query("zip")String zip,@Query("appid")String appid);

        @GET("data/2.5/forecast")
        rx.Observable<Weatherdata> getWeatherDataObservable(@Query("zip")String zip,@Query("appid")String appid);

    }

    public static rx.Observable<Weatherdata> getWeatherDataObservable()
    {
        Retrofit retrofit=Create();
        weatherService service=retrofit.create(weatherService.class);
        return service.getWeatherDataObservable(QUERY_ZIP,APP_ID);
    }
}
