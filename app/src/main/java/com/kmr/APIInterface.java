package com.kmr;

import com.kmr.model.BaseResponse;
import com.kmr.model.Laporan;
import com.kmr.model.OrderDao;
import com.kmr.model.PlaceDao;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

interface APIInterface {

    @GET("account/login?")
    Call<BaseResponse<Boolean>> login(
            @Query("email") String email, @Query("password") String password);

    @GET("account/register?")
    Call<BaseResponse<Boolean>> register(
            @Query("email") String email, @Query("password") String password);

    @GET("laporan-bulanan?")
    Call<BaseResponse<Laporan>> laporanBulanan(
            @Query("bulanAkhir") String bulanAkhir,
            @Query("bulanAwal") String bulanAwal,
            @Query("tahunAkhir") String tahunAkhir,
            @Query("tahunAwal") String tahunAwal);

    @GET("laporan-mingguan??")
    Call<BaseResponse<Laporan>> laporanMingguan(
            @Query("mingguAkhir") String mingguAkhir,
            @Query("mingguAwal") String mingguAwal,
            @Query("tahunAkhir") String tahunAkhir,
            @Query("tahunAwal") String tahunAwal);

    @GET("order/by-email?")
    Call<BaseResponse<List<OrderDao>>> getOrderByEmail(@Query("email") String email);

    @GET("order/pesan?")
    Call<BaseResponse<Boolean>> orderPesan(
            @Query("email") String email,
            @Query("durasiSewa") int durasiSewa,
            @Query("placeId") String placeId,
            @Query("placeName") String placeName,
            @Query("tanggalAwalSewa") String tanggalAwalSewa,
            @Query("totalBayar") double totalBayar);

    @GET("place/by-place-type?")
    Call<BaseResponse<List<PlaceDao>>> getTempat(@Query("placeType") String placeType);

    @FormUrlEncoded
    @POST("/place?")
    Call<Boolean> createTempat(
            @Field("address") String address,
            @Field("address2") String address2,
            @Field("durasi") String durasi,
            @Field("image") String image,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude,
            @Field("name") String name,
            @Field("placeType") String placeType,
            @Field("ukuran") String ukuran,
            @Field("price") String price);

//    @FormUrlEncoded
//    @POST("/api/users?")
//    Call<UserList> doCreateUserWithField(@Field("name") String name, @Field("job") String job);
}