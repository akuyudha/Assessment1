package org.d3if3073.assessment1.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.d3if3073.assessment1.R
import org.d3if3073.assessment1.databinding.ItemDescoperatorBinding
import org.d3if3073.assessment1.model.OperatorApi

class KalkulatorAdapter :
    ListAdapter<OperatorApi, KalkulatorAdapter.KalkulatorViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KalkulatorViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDescoperatorBinding.inflate(inflater, parent, false)
        return KalkulatorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KalkulatorViewHolder, position: Int) {
        val operator = getItem(position)
        holder.bind(operator)
    }

    class KalkulatorViewHolder(private val binding: ItemDescoperatorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(operator: OperatorApi) {
            binding.jenisOperator.text = operator.jenisOperator
            binding.deskripsiOperator.text = operator.informasi

            // Load image using Glide library
            Glide.with(binding.root)
                .load(operator.gambar)
                .error(R.drawable.broken_image)
                .into(binding.imageOperator)
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<OperatorApi>() {
        override fun areItemsTheSame(oldItem: OperatorApi, newItem: OperatorApi): Boolean {
            return oldItem.jenisOperator == newItem.jenisOperator
        }

        override fun areContentsTheSame(oldItem: OperatorApi, newItem: OperatorApi): Boolean {
            return oldItem == newItem
        }
    }
}
