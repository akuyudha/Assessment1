package org.d3if3073.assessment1.ui.hitung

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if3073.assessment1.db.KalkulatorDao
import org.d3if3073.assessment1.ui.MainViewModel

class HitungViewModelFactory(
    private val db: KalkulatorDao
): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(db) as T
        }
        throw IllegalArgumentException()
    }
}