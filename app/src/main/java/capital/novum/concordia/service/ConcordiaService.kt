package capital.novum.concordia.service

import capital.novum.concordia.model.LoginResult
import capital.novum.concordia.model.Nationality
import capital.novum.concordia.model.ProjectList
import capital.novum.concordia.model.Result
import capital.novum.concordia.util.Constant
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ConcordiaService {
    @GET("citizenship/list")
    fun getNationalities(): Observable<Nationality>

    @POST("login/account")
    @FormUrlEncoded
    fun loginAccount(@Field("email") email : String,
                     @Field("password") password : String,
                     @Field("device_id") deviceId : String,
                     @Field("platform") platform : String
                     ) : Observable<LoginResult>

    @GET("project/list")
    fun getProjectList(
            @Header("toke") token: String
    ) : Observable<ProjectList>

    companion object {
        fun create() : ConcordiaService {
            val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(Constant.BASE_URL)
                    .build()
            return retrofit.create(ConcordiaService::class.java)
        }
    }
}