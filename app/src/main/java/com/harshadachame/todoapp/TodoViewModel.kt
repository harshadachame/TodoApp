package com.harshadachame.todoapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.harshadachame.todoapp.model.Todo
import kotlinx.coroutines.launch

class TodoViewModel(private val apiService: TodoRepository) : ViewModel() {

    private val _todos = MutableLiveData<List<Todo>>()
    val todos: LiveData<List<Todo>> get() = _todos

    init {
        viewModelScope.launch {

            val response = apiService.getTodos()

            if (response.isSuccessful) {
                val jsonElement = response.body()

                if (jsonElement is JsonArray) {
                    _todos.value = Gson().fromJson(jsonElement, object : TypeToken<List<Todo>>() {}.type)
                } else if (jsonElement is JsonObject) {
                    val jsonArray = jsonElement.getAsJsonArray("todos")
                    _todos.value = Gson().fromJson(jsonArray, object : TypeToken<List<Todo>>() {}.type)
                }
            } else {

                Log.e("TodoViewModel", "Error fetching todos: ${response.errorBody()}")
            }
        }
    }
}