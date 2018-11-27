package capital.novum.concordia.service

import capital.novum.concordia.model.*
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
                    .baseUrl(Constant.COINMARKET_URL)
                    .build()
            return retrofit.create(CoinMarketService::class.java)
        }
    }

}