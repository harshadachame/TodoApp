package com.harshadachame.todoapp

import com.google.gson.JsonElement
import com.harshadachame.todoapp.api.RetrofitInstance
import com.harshadachame.todoapp.model.Todo
import retrofit2.Response

class TodoRepository {

    suspend fun getTodos(): Response<JsonElement> {
        return RetrofitInstance.todoApi.getTodos()
    }
}