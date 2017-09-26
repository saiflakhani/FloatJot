package com.vivifiedexistence.floatjot;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by saif on 2017-09-23.
 */

public interface APIService {

    @FormUrlEncoded
    @POST("addNote.php")
    Call<FloatNoteAttributes> addNote(@Field("user_id") int user_id, @Field("title") String title, @Field("message") String message, @Field("lat")String lat ,@Field("lng")String lng,@Field("vx")String vx,@Field("vy")String vy,@Field("vz")String vz);
}
