package com.harshadachame.todoapp.api

import com.google.gson.JsonElement
import retrofit2.Response
import retrofit2.http.GET

interface TodoApi {
    @GET("todos")
    suspend fun getTodos(): Response<JsonElement>
}