package com.example.pruebatecnicawhiz.home.data.service

import com.example.pruebatecnicawhiz.home.constants.AppConstants
import com.example.pruebatecnicawhiz.home.data.model.EvolutionChain
import com.example.pruebatecnicawhiz.home.data.model.PokemonAbility
import com.example.pruebatecnicawhiz.home.data.model.PokemonList
import com.example.pruebatecnicawhiz.home.data.model.PokemonSpecies
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface WebService {

    @GET("pokemon")
    suspend fun getPokemonList(@Query("limit") limit: String): PokemonList

    @GET("pokemon-species/{name}")
    suspend fun getPokemonByName(@Path("name") name: String): PokemonSpecies

    @GET
    suspend fun getEvolutionChainWithDynamicUrl(@Url url: String): EvolutionChain

    @GET("pokemon/{name}")
    suspend fun getPokemonAbilities(@Path("name") name: String): PokemonAbility

}


object RetrofitClient {

    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val webservice by lazy {
        Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(okHttpClient)
            .build().create(WebService::class.java)
    }

}