package com.batchmates.android.makingrestcalls;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Android on 7/10/2017.
 */

public interface GitHubService {

    @GET("users/{user}/repos")
    Call<List<GithubRepo>>callProfile(@Path("user")String user);
}
