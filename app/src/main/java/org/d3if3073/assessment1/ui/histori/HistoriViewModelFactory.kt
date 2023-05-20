package org.d3if3073.assessment1.ui.histori

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if3073.assessment1.db.KalkulatorDao

class HistoriViewModelFactory(
    private val db: KalkulatorDao
): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
       if (modelClass.isAssignableFrom(HistoriViewModel::class.java)) {
           return HistoriViewModel(db) as T
       }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}