package com.example.coroutineapp.ui.tasks.twotasks

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.coroutineapp.R
import com.example.coroutineapp.data.api.ApiHelperImpl
import com.example.coroutineapp.data.api.RetrofitBuilder
import com.example.coroutineapp.data.local.DatabaseBuilder
import com.example.coroutineapp.data.local.DatabaseHelperImpl
import com.example.coroutineapp.ui.base.UIState
import com.example.coroutineapp.ui.base.ViewModelFactory
import kotlinx.android.synthetic.main.activity_long_running_task.*
import kotlinx.android.synthetic.main.activity_recycler_view.*
import kotlinx.android.synthetic.main.activity_recycler_view.progressBar

class TwoLongRunningTasksActivity :AppCompatActivity() {

   private lateinit var viewModel: TwoLongRunningTasksViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_long_running_task)
        setupViewModel()
        setUpLongRunningTask()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(
                ApiHelperImpl(RetrofitBuilder.apiService),
                DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext))
            )
        )[TwoLongRunningTasksViewModel::class.java]
    }

    private fun setUpLongRunningTask() {
       viewModel.getUiState().observe(this){
           when(it){
               is UIState.Success->{
                   progressBar.visibility=View.GONE
                   textView.text=it.data
                    progressBar.visibility=View.VISIBLE
               }
               is UIState.Loading -> {
                   progressBar.visibility = View.VISIBLE
                   textView.visibility = View.GONE
               }
               is UIState.Error -> {
                   //Handle Error
                   progressBar.visibility = View.GONE
                   Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
               }
           }
       }
        viewModel.startLongRunningTask()
    }
}