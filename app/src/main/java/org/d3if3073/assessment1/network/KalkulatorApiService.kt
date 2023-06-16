package org.d3if3073.assessment1.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.d3if3073.assessment1.model.OperatorApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


private const val BASE_URL =
    "https://raw.githubusercontent.com/akuyudha/Assessment1/kalkulatorAPI/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface KalkulatorApiService {
    @GET("kalkulator.json")
    suspend fun getHasilOperasi(): List<OperatorApi>

}

object KalkulatorApi {
    val service: KalkulatorApiService by lazy {
        retrofit.create(KalkulatorApiService::class.java)
    }
}

enum class ApiStatus { LOADING, SUCCESS, FAILED }