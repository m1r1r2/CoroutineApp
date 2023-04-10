package   com.example.coroutineapp.ui.tasks.onetask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutineapp.data.api.ApiHelper
import com.example.coroutineapp.data.local.DatabaseHelper
import com.example.coroutineapp.ui.base.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LongRunningTaskViewModel(
    private val apiHelper: ApiHelper, private val dbHelper: DatabaseHelper
) : ViewModel() {

    private val uiState = MutableLiveData<UIState<String>>()

    fun startLongRunningTask() {
        viewModelScope.launch {
            uiState.postValue(UIState.Loading)
            try {
                // do a long running task
                doLongRunningTask()
                uiState.postValue(UIState.Success("Task Completed"))
            } catch (e: Exception) {
                uiState.postValue(UIState.Error("Something Went Wrong"))
            }
        }
    }

    fun getUiState(): LiveData<UIState<String>> {
        return uiState
    }

    private suspend fun doLongRunningTask() {
        withContext(Dispatchers.Default) {
            // your code for doing a long running task
            // Added delay to simulate
            delay(5000)
        }
    }

}