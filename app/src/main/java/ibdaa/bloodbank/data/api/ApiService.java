package ibdaa.bloodbank.data.api;

import ibdaa.bloodbank.data.model.addAndRemovefavorites.AddAndRemovefavorites;
import ibdaa.bloodbank.data.model.createDonationRequest.Requests;
import ibdaa.bloodbank.data.model.donations.Donations;
import ibdaa.bloodbank.data.model.favorites.Favorites;
import ibdaa.bloodbank.data.model.forgetPassword.ForgetPassword;
import ibdaa.bloodbank.data.model.generalResponse.GeneralResponse;
import ibdaa.bloodbank.data.model.login.Login;
import ibdaa.bloodbank.data.model.posts.Posts;
import ibdaa.bloodbank.data.model.settings.Settings;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @POST("login")
    @FormUrlEncoded
    Call<Login> login(@Field("phone") String phone,
                      @Field("password") String password);

    @POST("signup")
    @FormUrlEncoded
    Call<Login> signUp(@Field("name") String name,
                       @Field("email") String email,
                       @Field("birth_date") String birthDate,
                       @Field("city_id") int cityId,
                       @Field("phone") String phone,
                       @Field("donation_last_date") String lastDonation,
                       @Field("password") String password,
                       @Field("password_confirmation") String confirmPassword,
                       @Field("blood_type_key") int bloodTypeId);

   /* @POST("profile")
    @FormUrlEncoded
    Call<Profile> getProfile(@Field("api_token") String api_token);*/

    @GET("posts")
    Call<Posts> getAllPosts(@Query("api_token") String apiToken,
                            @Query("page") int page);

    @GET("posts")
    Call<Posts> getFilteredPosts(@Query("api_token") String apiToken,
                                 @Query("page") int page,
                                 @Query("keyword") String keyword,
                                 @Query("category_id") int categoryId);


    @GET("cities")
    Call<GeneralResponse> getCity(@Query("governorate_id") int governorate_id);

    @GET("governorates")
    Call<GeneralResponse> getGvernorates();

    @GET("blood-types")
    Call<GeneralResponse> getBlood_types();

    @POST("profile")
    @FormUrlEncoded
    Call<Login> getprofile(@Field("api_token") String apiToken);

    @GET("donation-requests")
    Call<Donations> donationRequests(@Query("api_token") String apiToken,
                                     @Query("page") int page);

    @GET("donation-requests")
    Call<Donations> donationRequests(@Query("api_token") String apiToken,
                                     @Query("page") int page,
                                     @Query("blood_type_id") int bloodTypeId,
                                     @Query("governorate_id") int governorateId);

    @GET("donation-requests")
    Call<Donations> displayDonation(@Query("api_token") String apiToken,
                                    @Query("donation_id") int donationId);

    @GET("post")
    Call<Posts> displayPostDetails(@Query("api_token") String apiToken,
                                       @Query("post_id") int post_id,
                                       @Query("page") int page);

    @GET("my-favourites")
    Call<Favorites> getAllFavourites(@Query("api_token") String apiToken,
                                     @Query("page") int page);

    @POST("post-toggle-favourite")
    @FormUrlEncoded
    Call<AddAndRemovefavorites> addAndRemoveFavourite(@Field("post_id") int postId,
                                                      @Field("api_token") String apiToken);

    @POST("donation-request")
    @FormUrlEncoded
    Call<Requests> createDonationRequest(@Field("api_token") String apiToken,
                                         @Field("patient_name") String patientName,
                                         @Field("patient_age") int patientAge,
                                         @Field("blood_type_id") int bloodTypeId,
                                         @Field("bags_num") int bagsNum,
                                         @Field("hospital_name") String hospitalName,
                                         @Field("hospital_address") String hospitalAddress,
                                         @Field("city_id") int cityId,
                                         @Field("phone") String phone,
                                         @Field("notes") String notes,
                                         @Field("latitude") double latitude,
                                         @Field("longitude") double longitude);

    @GET("categories")
    Call<GeneralResponse> getCategories();


    @GET("settings")
    Call<Settings> getAppSettings(@Query("api_token") String api_token);

    @POST("reset-password")
    @FormUrlEncoded
    Call<ForgetPassword> onForgetPassword(@Field("phone") String phone);

    @POST("new-password")
    @FormUrlEncoded
    Call<ForgetPassword> newPassword(@Field("password") String password,
                                     @Field("password_confirmation") String passwordConfirmation,
                                     @Field("pin_code") String pin_code,
                                     @Field("phone") String phone);
}