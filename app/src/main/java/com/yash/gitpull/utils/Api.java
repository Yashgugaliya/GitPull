package com.yash.gitpull.utils;

import com.yash.gitpull.model.ClosedPullRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    String BASE_URL = "https://api.github.com/";

    @GET("repos/Yashgugaliya/Moviezy/pulls?state=closed")
    Call<ArrayList<ClosedPullRequest>> getClosedRequest();
}
