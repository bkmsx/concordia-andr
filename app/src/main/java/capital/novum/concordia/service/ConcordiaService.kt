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

    @GET(UrlConstant.APP_PAYMENT_METHOD)
    fun getAppPaymentMethod(): Observable<PaymentMethodListResult>

    @POST(UrlConstant.LOGIN_ACCOUNT)
    fun loginAccount(@Body params: HashMap<String, String>) : Observable<LoginResult>

    @GET(UrlConstant.PROJECT_LIST)
    fun getProjectList(
            @Header("token") token: String
    ) : Observable<ProjectList>

    @GET(UrlConstant.PROJECT_DETAIL)
    fun getProjectDetail(
            @Header("token") token: String,
            @QueryMap params: HashMap<String, String>
    ) : Observable<ProjectDetail>

    @GET(UrlConstant.LIST_WALLET)
    fun getUserWallet(
            @Header("token") token: String
    ) : Observable<UserWallets>

    @POST(UrlConstant.ADD_WALLET)
    fun addWalletAddress(
            @Header("token") token: String,
            @Body params: HashMap<String, String>
    ) : Observable<Result>

    @POST(UrlConstant.PROJECT_PARTICIPATE)
    fun participate(
            @Header("token") token: String,
            @Body params: HashMap<String, String>
    ) : Observable<Result>

    @POST(UrlConstant.REGISTER)
    fun register(
            @Body params: HashMap<String, String>
    ) : Observable<Result>

    @POST(UrlConstant.FORGOT_PASSWORD)
    fun retrievePassword(
            @Body params: HashMap<String, String>
    ) : Observable<Result>

    @POST(UrlConstant.SEND_OTP)
    fun sendOTP(
            @Body params: HashMap<String, String>
    ) : Observable<Result>

    @POST(UrlConstant.VERIFY_OTP)
    fun verifyOTP(
            @Body params: HashMap<String, String>
    ) : Observable<Result>

    @POST(UrlConstant.CHANGE_PASSWORD)
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

    @POST(UrlConstant.UPDATE_REFERRAL_CODE)
    fun updateReferralCode(
            @Header("token") token: String,
            @Body params: HashMap<String, String>
    ) : Observable<Result>

    @GET(UrlConstant.LIST_REFERRAL_BONUS)
    fun listReferralBonus(
            @Header("token") token: String
    ) : Observable<ReferralListResult>

    @Multipart
    @POST(UrlConstant.UPDATE_PASSPORT)
    fun updatePassport(
            @Header("token") token: String,
            @PartMap params: HashMap<String, RequestBody>,
            @Part passport: MultipartBody.Part,
            @Part selfie: MultipartBody.Part?
    ) : Observable<Result>

    companion object {
        fun create(namePolicy: FieldNamingPolicy = FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES) : ConcordiaService {
            val gson = GsonBuilder().setFieldNamingPolicy(namePolicy).create()
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(UrlConstant.BASE_URL)
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