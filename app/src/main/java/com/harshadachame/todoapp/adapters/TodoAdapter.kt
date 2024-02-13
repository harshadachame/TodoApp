package com.harshadachame.todoapp.adapters

import android.widget.Filterable
import android.widget.TextView
import com.harshadachame.todoapp.R
import com.harshadachame.todoapp.model.Todo
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter


class TodoAdapter(context: Context, private var todos: List<Todo>) : ArrayAdapter<Todo>(context,0,todos),
    Filterable {

    private var filteredTodos : List<Todo> = todos
    private var filterType: FilterType = FilterType.ALL

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val todo = getItem(position)

        var listItemView = convertView
        if(listItemView ==  null){
            listItemView = LayoutInflater.from(context).inflate(R.layout.list_item_todo,parent, false)
        }

        val titleTv :TextView = listItemView!!.findViewById(R.id.titleText)
        val statusTv :TextView = listItemView.findViewById(R.id.statusText)

        titleTv.text = todo?.todo
        statusTv.text = if(todo?.completed== true) "Completed" else "Not Completed"

        return listItemView
    }

    fun setFilter(filterType: FilterType){
        this.filterType = filterType
        filteredTodos = when (filterType){
            FilterType.COMPLETED -> todos.filter{it.completed}
            FilterType.NOT_COMPLETED -> todos.filter{!it.completed}
            else -> todos
        }
    }

    fun updateList(newList: List<Todo>){
        todos = newList
        notifyDataSetChanged()
    }

}
