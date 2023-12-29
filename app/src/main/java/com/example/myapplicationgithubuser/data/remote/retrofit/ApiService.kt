import com.example.myapplicationgithubuser.data.remote.response.DetailUserResponse
import com.example.myapplicationgithubuser.data.remote.response.ItemsItem
import com.example.myapplicationgithubuser.data.remote.response.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUser(
        @Query("q") login: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>
}