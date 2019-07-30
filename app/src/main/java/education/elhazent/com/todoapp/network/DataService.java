package education.elhazent.com.todoapp.network;

import java.util.List;

import education.elhazent.com.todoapp.model.ResponseTodo;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface DataService {

    @GET("posts")
    Call<List<ResponseTodo>> getAlldata();

    @FormUrlEncoded
    @POST("posts")
    Call<ResponseTodo> postData(@Field("title") String title,
                                @Field("body") String body,
                                @Field("userId") String userId);

    @FormUrlEncoded
    @PUT("posts/1")
    Call<ResponseTodo> editData(
            //@Path("id") String id,
            @Field("title") String title,
            @Field("body") String body,
            @Field("userId") String userId);

    @DELETE("posts/1")
    Call<ResponseTodo> deleteData();

}
