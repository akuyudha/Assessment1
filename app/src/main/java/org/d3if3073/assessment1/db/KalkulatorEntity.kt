package org.d3if3073.assessment1.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kalkulator")
data class KalkulatorEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var tanggal: Long = System.currentTimeMillis(),
    var bil1: Float,
    var bil2: Float,
    var operator: String
)
