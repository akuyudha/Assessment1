package org.d3if3073.assessment1.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if3073.assessment1.db.KalkulatorDao
import org.d3if3073.assessment1.db.KalkulatorEntity
import org.d3if3073.assessment1.model.Hasil
import org.d3if3073.assessment1.model.OperatorApi
import org.d3if3073.assessment1.model.hitungKalkulator
import org.d3if3073.assessment1.network.ApiStatus
import org.d3if3073.assessment1.network.KalkulatorApi
import org.d3if3073.assessment1.network.UpdateWorker
import java.util.concurrent.TimeUnit

class MainViewModel(private val db: KalkulatorDao): ViewModel() {

    private val hasil = MutableLiveData<Hasil?>()
    private val data = MutableLiveData<List<OperatorApi>>()
    private val status = MutableLiveData<ApiStatus>()

    init {
        retrieveData()
    }

    private fun retrieveData() {
        viewModelScope.launch (Dispatchers.IO) {
            status.postValue(ApiStatus.LOADING)
            try {
                data.postValue(KalkulatorApi.service.getHasilOperasi())
                status.postValue(ApiStatus.SUCCESS)
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
                status.postValue(ApiStatus.FAILED)
            }
        }
    }

    fun calculate(bil1: Float, bil2: Float, operator: String) {
        val dataHitung = KalkulatorEntity(
            bil1 = bil1,
            bil2 = bil2,
            operator = operator
        )
        hasil.value = dataHitung.hitungKalkulator()

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.insert(dataHitung)
            }
        }
    }

    fun getHasil(): LiveData<Hasil?> = hasil

    fun getStatus(): LiveData<ApiStatus> = status

    fun scheduleUpdater(app: Application) {
        val request = OneTimeWorkRequestBuilder<UpdateWorker>()
            .setInitialDelay(1, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(app).enqueueUniqueWork(
            UpdateWorker.WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            request
        )
    }
}