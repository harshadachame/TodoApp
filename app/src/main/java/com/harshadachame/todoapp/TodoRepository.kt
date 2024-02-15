package com.harshadachame.todoapp

import com.google.gson.JsonElement
import com.harshadachame.todoapp.api.RetrofitInstance
import retrofit2.Response
class TodoRepository {
    suspend fun getTodos(): Response<JsonElement> {
        return RetrofitInstance.todoApi.getTodos()
    }
}