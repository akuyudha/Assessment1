package org.d3if3073.assessment1.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface KalkulatorDao {

    @Insert
    fun insert(kalkulator: KalkulatorEntity)

    @Query("SELECT * FROM kalkulator ORDER BY id DESC")
    fun getLastKalkulator(): LiveData<List<KalkulatorEntity>>

    @Query("DELETE FROM kalkulator")
    fun clearData()
}