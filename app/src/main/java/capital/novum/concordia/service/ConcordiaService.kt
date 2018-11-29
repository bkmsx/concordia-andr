package capital.novum.concordia.service

import capital.novum.concordia.model.*
import capital.novum.concordia.util.UrlConstant
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ConcordiaService {
    @GET(UrlConstant.LIST_CITIZENSHIP)
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
            @Header("token") token: String
    ) : Observable<ProjectList>

    @GET("project/detail")
    fun getProjectDetail(
            @Header("token") token: String,
        @Query("project_id") projectId: Int
    ) : Observable<ProjectDetail>

    @GET("user-wallets")
    fun getUserWallet(
            @Header("token") token: String
    ) : Observable<UserWallets>

    @POST("wallet-add")
    @FormUrlEncoded
    fun addWalletAddress(
            @Header("token") token: String,
            @Field("method_id") methodId : Int,
            @Field("wallet_address") walletAddress : String
    ) : Observable<Result>

    @POST("project/participate")
    @FormUrlEncoded
    fun participate(
            @Header("token") token: String,
            @Field("project_id") projectId: Int,
            @Field("payment_method") paymentMethod: String,
            @Field("amount_tokens") amountToken: String,
            @Field("payment_method_id") paymentMethodId: Int,
            @Field("payment_amount") paymentAmount: String,
            @Field("wallet_address") walletAddress: String,
            @Field("discount") discount: String,
            @Field("referral_code") referralCode: String,
            @Field("payment_amount_eth") paymentAmountETH: String
    ) : Observable<Result>

    @POST("register")
    @FormUrlEncoded
    fun register(
            @Field("first_name") firstName: String,
            @Field("last_name") lastName: String,
            @Field("date_of_birth") dob: String,
            @Field("email") email: String,
            @Field("password") password: String,
            @Field("device_security_enable") securityEnable: String,
            @Field("type_of_security") securityType: String,
            @Field("referral_code") referralCode: String,
            @Field("device_id") deviceId: String,
            @Field("validation") validation: Int,
            @Field("platform") platform: String
    ) : Observable<Result>

    @POST("forgot-password")
    @FormUrlEncoded
    fun retrievePassword(
            @Field("email") firstName: String
    ) : Observable<Result>

    @POST(UrlConstant.SEND_OTP)
    fun sendOTP(
            @Body params: HashMap<String, String>
    ) : Observable<Result>

    @POST("otp/verify")
    fun verifyOTP(
            @Body params: HashMap<String, String>
    ) : Observable<Result>

    @POST("change-password")
    fun changePassword(
            @Header("token") token: String,
            @Body params: HashMap<String, String>
    ) : Observable<Result>

    @POST(UrlConstant.CHANGE_CONFIGURATION)
    fun changePersonalCofiguration(
            @Header("token") token: String,
            @Body params: HashMap<String, String>
    ) : Observable<Result>

    @POST(UrlConstant.DELETE_PARTICIPATE)
    fun deleteParticipate(
            @Header("token") token: String,
            @Body params: HashMap<String, String>
    ) : Observable<Result>

    @GET(UrlConstant.LIST_PARTICIPATE)
    fun listParticipate(
            @Header("token") token: String
    ) : Observable<ParticipateListResult>

    @GET(UrlConstant.PARTICIPATE_DETAIL)
    fun participateDetail(
            @Header("token") token: String,
            @QueryMap params: HashMap<String, String>
    ) : Observable<ParticipateDetailResult>

    @POST(UrlConstant.DELETE_WALLET)
    fun deleteWallet(
            @Header("token") token: String,
            @Body params: HashMap<String, String>
    ) : Observable<Result>

    @POST(UrlConstant.UPDATE_WALLET)
    fun updateWallet(
            @Header("token") token: String,
            @Body params: HashMap<String, String>
    ) : Observable<Result>

    @Multipart
    @POST(UrlConstant.UPDATE_PASSPORT)
    fun updatePassport(
            @Header("token") token: String,
            @PartMap params: HashMap<String, RequestBody>,
            @Part passport: MultipartBody.Part,
            @Part selfie: MultipartBody.Part?
    ) : Observable<Result>

    companion object {
        fun create(baseUrl: String) : ConcordiaService {
            val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(baseUrl)
                    .build()
            return retrofit.create(ConcordiaService::class.java)
        }
    }
}

interface CoinMarketService {
    @GET("quotes/latest")
    fun getCryptoCurrencyQuotes(
            @Header("X-CMC_PRO_API_KEY") token: String = "1046459a-6062-41e8-8f48-f8253ed4f81d",
            @Query("symbol") symbol: String = "ETH",
            @Query("convert") convert: String
    ) : Observable<CoinMarketResult>

    companion object {
        fun create() : CoinMarketService {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(UrlConstant.COINMARKET_URL)
                    .build()
            return retrofit.create(CoinMarketService::class.java)
        }
    }

}