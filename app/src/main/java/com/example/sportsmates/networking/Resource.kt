package com.example.sportsmates.networking

import com.example.sportsmates.chatbot.endpoint.NutroEndPoint
import com.example.sportsmates.chatbot.model.Nutro
import com.example.sportsmates.home.data.endpoints.NewsEndpoint
import com.example.sportsmates.utils.Constants
import com.example.sportsmates.utils.Constants.NEWS_BASE_URL
import com.example.sportsmates.utils.Constants.NUTRO_BASE_URL
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

sealed class Resource<out R> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val throwable: Throwable) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}

class NewsApiHelper(private val apiService: NewsEndpoint) {
    suspend fun getNews(
        mainSport: String?,
        optionalSport1: String? = mainSport,
        optionalSport2: String? = mainSport
    ) =
        apiService.getRecommendedNews("$mainSport OR $optionalSport1 OR $optionalSport2")

    suspend fun getTrendingNews() =
        apiService.getTrendingNews()
}

class NutroApiHelper(private val apiService: NutroEndPoint) {
    suspend fun getNutroInfo(food: String): Nutro {
        val query: HashMap<String, String> = HashMap()
        query["query"] = food
      return  apiService.getNutroInfo(query)
    }
}


object RetrofitBuilder {

    fun NewsapiClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                var request: Request = chain.request()
                val url: HttpUrl = request.url
                    .newBuilder()
                    .addQueryParameter(Constants.NEWS_API_KEY_NAME, Constants.NEWS_API_KEY_VALUE)
                    .build()
                request = request.newBuilder().url(url).build()
                chain.proceed(request)
            })
            .addInterceptor(logging).build()


    }

    fun NutroapiClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                var request: Request = chain.request()
                val url: HttpUrl = request.url
                    .newBuilder()
                    .addQueryParameter(Constants.NUTRO_API_KEY_NAME, Constants.NUTRO_API_KEY_VALUE)
                    .addQueryParameter(Constants.NUTRO_APP_ID_KEY_NAME, Constants.NUTRO_APP_ID_KEY_VALUE)
                    .build()
                request = request.newBuilder().url(url).build()
                chain.proceed(request)
            })
            .addInterceptor(logging).build()


    }

    private val retrofitNews: Retrofit = Retrofit.Builder()
        .baseUrl(NEWS_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(NewsapiClient())
        .build()
    private val retrofitNutro: Retrofit = Retrofit.Builder()
        .baseUrl(NUTRO_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(NutroapiClient())
        .build()
    val newsApiService: NewsEndpoint = retrofitNews.create(NewsEndpoint::class.java)
    val nutroApiService: NutroEndPoint = retrofitNutro.create(NutroEndPoint::class.java)

}


