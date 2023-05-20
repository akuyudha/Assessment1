package org.d3if3073.assessment1.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if3073.assessment1.db.KalkulatorDao
import org.d3if3073.assessment1.db.KalkulatorEntity
import org.d3if3073.assessment1.model.Hasil
import org.d3if3073.assessment1.model.Hitung
import org.d3if3073.assessment1.model.hitungKalkulator

class MainViewModel(private val db: KalkulatorDao): ViewModel() {

    private val hasil = MutableLiveData<Hasil?>()

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
}