package com.harshadachame.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SearchView
import android.widget.Spinner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.harshadachame.todoapp.adapters.FilterType
import com.harshadachame.todoapp.adapters.TodoAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var  viewModel: TodoViewModel
    private lateinit var adapter: TodoAdapter
    var filterType = FilterType.ALL

    private  val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = TodoRepository()
        viewModel = ViewModelProvider(this, TodoViewModelFactory(repository)).get(TodoViewModel::class.java)

        val todoListView:ListView = findViewById(R.id.todoListView)
        adapter = TodoAdapter(this, emptyList())
        todoListView.adapter = adapter

        val search: SearchView = findViewById(R.id.search)
        val filter: Spinner = findViewById(R.id.spinner)

        viewModel.todos.observe(this, Observer {
            Log.e(TAG, "onCreate: ", )
            adapter.updateList(it)
        })

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                if (newText != null) {
                    filterTodos(newText, filter.selectedItemPosition)
                }
                return true
            }
        })

        filter.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                filterType = when (position){
                    1-> FilterType.COMPLETED
                    2-> FilterType.NOT_COMPLETED
                    else -> FilterType.ALL
                }
                adapter.setFilter(filterType)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }
    private fun filterTodos(query: String, filterPosition: Int) {
        val filteredList = when (filterPosition) {
            1 -> viewModel.todos.value?.filter { it.completed == true }
            2 -> viewModel.todos.value?.filter { it.completed == false }
            else -> viewModel.todos.value
        }
        val filteredAndSearchedList = filteredList?.filter {
            it.todo.contains(query, ignoreCase = true)
        }

        adapter.updateList(filteredAndSearchedList ?: emptyList())
    }
}

