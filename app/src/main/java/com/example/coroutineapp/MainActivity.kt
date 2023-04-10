package com.example.coroutineapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.coroutineapp.ui.errorhandling.exceptionhandler.ExceptionHandlerActivity
import com.example.coroutineapp.ui.errorhandling.supervisor.IgnoreErrorAndContinueActivity
import com.example.coroutineapp.ui.errorhandling.trycatch.TryCatchActivity
import com.example.coroutineapp.ui.retrofit.parallel.ParallelNetworkCallsActivity
import com.example.coroutineapp.ui.retrofit.series.SeriesNetworkCallsActivity
import com.example.coroutineapp.ui.retrofit.single.SingleNetworkCallActivity
import com.example.coroutineapp.ui.room.RoomDBActivity
import com.example.coroutineapp.ui.tasks.onetask.LongRunningTaskActivity
import com.example.coroutineapp.ui.tasks.twotasks.TwoLongRunningTasksActivity
import com.example.coroutineapp.ui.timeout.TimeoutActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startSingleNetworkCallActivity(view: View) {
        startActivity(Intent(this@MainActivity, SingleNetworkCallActivity::class.java))
    }

    fun startSeriesNetworkCallsActivity(view: View) {
        startActivity(Intent(this@MainActivity, SeriesNetworkCallsActivity::class.java))
    }

    fun startParallelNetworkCallsActivity(view: View) {
        startActivity(Intent(this@MainActivity, ParallelNetworkCallsActivity::class.java))
    }

    fun startRoomDatabaseActivity(view: View) {
        startActivity(Intent(this@MainActivity, RoomDBActivity::class.java))
    }

    fun startTimeoutActivity(view: View) {
        startActivity(Intent(this@MainActivity, TimeoutActivity::class.java))
    }

    fun startTryCatchActivity(view: View) {
        startActivity(Intent(this@MainActivity, TryCatchActivity::class.java))
    }

    fun startExceptionHandlerActivity(view: View) {
        startActivity(Intent(this@MainActivity, ExceptionHandlerActivity::class.java))
    }

    fun startIgnoreErrorAndContinueActivity(view: View) {
        startActivity(Intent(this@MainActivity, IgnoreErrorAndContinueActivity::class.java))
    }

    fun startLongRunningTaskActivity(view: View) {
        startActivity(Intent(this@MainActivity, LongRunningTaskActivity::class.java))
    }

    fun startTwoLongRunningTasksActivity(view: View) {
        startActivity(Intent(this@MainActivity, TwoLongRunningTasksActivity::class.java))
    }



}