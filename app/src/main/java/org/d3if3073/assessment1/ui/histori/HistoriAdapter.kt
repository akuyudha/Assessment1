package org.d3if3073.assessment1.ui.histori

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.d3if3073.assessment1.R
import org.d3if3073.assessment1.databinding.ItemHistoriBinding
import org.d3if3073.assessment1.db.KalkulatorEntity
import org.d3if3073.assessment1.model.hitungKalkulator
import java.text.SimpleDateFormat
import java.util.*

class HistoriAdapter: ListAdapter<KalkulatorEntity, HistoriAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<KalkulatorEntity>() {
                override fun areContentsTheSame(
                    oldData: KalkulatorEntity,
                    newData: KalkulatorEntity
                ): Boolean {
                    return oldData.id == newData.id
                }

                override fun areItemsTheSame(
                    oldData: KalkulatorEntity,
                    newData: KalkulatorEntity
                ): Boolean {
                    return oldData == newData
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoriBinding.inflate(inflater,parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemHistoriBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: KalkulatorEntity) = with(binding) {
            val dateFormatter = SimpleDateFormat("dd MMMM yyyy",
                Locale("id", "ID")
            )

            val hasilHitung = item.hitungKalkulator()
            val operatorRes = when(item.operator) {
                "+ (Penjumlahan)" -> "+"
                "- (Pengurangan)" -> "-"
                "x (Perkalian)" -> "x"
                else -> "/"
            }
            operatorTextView.text = operatorRes

            val colorRes = when(item.operator) {
                "+ (Penjumlahan)" -> R.color.tambah
                "- (Pengurangan)" -> R.color.kurang
                "x (Perkalian)" -> R.color.kali
                else -> R.color.bagi
            }
            val circleBg = operatorTextView.background as GradientDrawable
            circleBg.setColor(ContextCompat.getColor(root.context, colorRes))
            tanggalTextView.text = dateFormatter.format(Date(item.tanggal))
            hasilTextView.text = root.context.getString(R.string.hasil_x, hasilHitung.hasil)
            dataTextView.text = root.context.getString(R.string.data_x, item.bil1, item.bil2, item.operator)
        }
    }
}