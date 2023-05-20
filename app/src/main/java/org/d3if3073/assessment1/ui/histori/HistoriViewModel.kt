package org.d3if3073.assessment1.ui.histori

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if3073.assessment1.db.KalkulatorDao

class HistoriViewModel(private val db: KalkulatorDao): ViewModel() {
    val data = db.getLastKalkulator()
    fun clearAllData() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            db.clearData()
        }
    }
}