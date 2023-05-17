package org.d3if3073.assessment1.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.d3if3073.assessment1.model.Hasil
import org.d3if3073.assessment1.model.Hitung
import org.d3if3073.assessment1.model.hitungKalkulator

class MainViewModel : ViewModel() {

    private val hasil = MutableLiveData<Hasil?>()

    fun calculate(bil1: Float, bil2: Float, operator: String) {
        val dataHitung = Hitung(
            bil1 = bil1,
            bil2 = bil2,
            operator = operator
        )
        hasil.value = dataHitung.hitungKalkulator()
    }

    fun getHasil(): LiveData<Hasil?> = hasil
}