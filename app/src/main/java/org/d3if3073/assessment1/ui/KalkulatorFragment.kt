package org.d3if3073.assessment1.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.d3if3073.assessment1.MainActivity
import org.d3if3073.assessment1.databinding.DescoperatorFragmentBinding
import org.d3if3073.assessment1.db.KalkulatorDb
import org.d3if3073.assessment1.network.ApiStatus
import org.d3if3073.assessment1.network.KalkulatorApi
import org.d3if3073.assessment1.ui.hitung.HitungViewModelFactory
import retrofit2.HttpException

class KalkulatorFragment : Fragment() {
    private lateinit var binding: DescoperatorFragmentBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: KalkulatorAdapter
    private lateinit var emptyView: TextView

    private val viewModel: MainViewModel by lazy {
        val db = KalkulatorDb.getInstance(requireContext())
        val factory = HitungViewModelFactory(db.dao)
        ViewModelProvider(this, factory)[MainViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DescoperatorFragmentBinding.inflate(inflater, container, false)
        recyclerView = binding.recyclerView
        emptyView = binding.emptyView

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = KalkulatorAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewModel.getStatus().observe(viewLifecycleOwner) {
            updateProgress(it)
        }
        viewModel.scheduleUpdater(requireActivity().application)
        fetchData()
    }

    private fun fetchData() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = KalkulatorApi.service.getHasilOperasi()
                if (response.isNotEmpty()) {
                    adapter.submitList(response)
                    recyclerView.visibility = View.VISIBLE
                    emptyView.visibility = View.GONE
                } else {
                    recyclerView.visibility = View.GONE
                    emptyView.visibility = View.VISIBLE
                }
            } catch (e: HttpException) {
                // Handle HttpException here
            } catch (e: Throwable) {
                // Handle other exceptions here
            }
        }
    }

    private fun updateProgress(status: ApiStatus) {
        when (status) {
            ApiStatus.LOADING -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            ApiStatus.SUCCESS -> {
                binding.progressBar.visibility = View.GONE

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requestNotificationPermission()
                }
            }
            ApiStatus.FAILED -> {
                binding.progressBar.visibility = View.GONE
                binding.networkError.visibility = View.VISIBLE
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestNotificationPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                MainActivity.PERMISSION_REQUEST_CODE
            )
        }
    }
}