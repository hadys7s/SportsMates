package com.example.sportsmates.networking

import com.example.sportsmates.news.data.endpoint.NewsEndpoint
import com.example.sportsmates.utils.Constants
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): Resource<T> =
            Resource(status = Status.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): Resource<T> =
            Resource(status = Status.ERROR, data = data, message = message)

        fun <T> loading(data: T?): Resource<T> =
            Resource(status = Status.LOADING, data = data, message = null)
    }


}

class ApiHelper(private val apiService: NewsEndpoint) {
    suspend fun getNews(
        mainSport: String,
        optionalSport1: String = mainSport,
        optionalSport2: String = mainSport
    ) =
        apiService.getRecommendedNews("$mainSport OR $optionalSport1 OR $optionalSport2")

    suspend fun getTrendingNews() =
        apiService.getTrendingNews()
}


object RetrofitBuilder {

    private const val BASE_URL = "https://newsapi.org/v2/"

    fun apiClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                var request: Request = chain.request()
                val url: HttpUrl = request.url
                    .newBuilder()
                    .addQueryParameter(Constants.API_KEY_NAME, Constants.API_KEY_VALUE)
                    .build()
                request = request.newBuilder().url(url).build()
                chain.proceed(request)
            })
            .addInterceptor(logging).build()


    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(apiClient())
        .build()
    val apiService: NewsEndpoint = retrofit.create(NewsEndpoint::class.java)

}


