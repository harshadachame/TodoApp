package com.harshadachame.todoapp.model

import java.io.Serializable

data class Todo(

    val id: Int,
    val todo: String,
    val completed: Boolean,
    val userId: Int
):Serializable
