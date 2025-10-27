package com.example.project.data.api

import com.example.project.data.models.ApiCharacter
import com.example.project.data.models.ApiCharacterList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int
    ): ApiCharacterList

    @GET("character/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int
    ) : ApiCharacter

    @GET("character")
    suspend fun searchMethod(
        @Query("name") name: String,
        @Query("page") page: Int
    ): ApiCharacterList
}