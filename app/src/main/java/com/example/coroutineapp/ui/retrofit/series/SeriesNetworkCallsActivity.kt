package com.example.coroutineapp.ui.retrofit.series

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coroutineapp.R
import com.example.coroutineapp.data.api.ApiHelperImpl
import com.example.coroutineapp.data.api.RetrofitBuilder
import com.example.coroutineapp.data.local.DatabaseBuilder
import com.example.coroutineapp.data.local.DatabaseHelperImpl
import com.example.coroutineapp.data.model.ApiUser
import com.example.coroutineapp.ui.base.ApiUserAdapter
import com.example.coroutineapp.ui.base.UIState
import com.example.coroutineapp.ui.base.ViewModelFactory
import kotlinx.android.synthetic.main.activity_recycler_view.*

class SeriesNetworkCallsActivity :AppCompatActivity() {
    private lateinit var viewModel: SeriesNetworkCallsViewModel
    private lateinit var adapter: ApiUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        setupUI()
        setupViewModel()
        setupObserver()
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter =
            ApiUserAdapter(
                arrayListOf()
            )
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun setupObserver() {
        viewModel.getUiState().observe(this) {
            when (it) {
                is UIState.Success -> {
                    progressBar.visibility = View.GONE
                    renderList(it.data)
                    recyclerView.visibility = View.VISIBLE
                }
                is UIState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                is UIState.Error -> {
                    //Handle Error
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun renderList(users: List<ApiUser>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(
                ApiHelperImpl(RetrofitBuilder.apiService),
                DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext))
            )
        )[SeriesNetworkCallsViewModel::class.java]
    }
}