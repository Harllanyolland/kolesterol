package com.example.sergio.kolestrol_1;

import com.example.sergio.kolestrol_1.Model.ServerRequest;
import com.example.sergio.kolestrol_1.Model.ServerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Sergio on 6/8/2017.
 */

public interface RequestInterface {

    @POST("user/loginUser")
    Call<ServerResponse> operation(@Body ServerRequest request);

}
