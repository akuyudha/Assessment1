package org.d3if3073.assessment1.ui.hitung

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.d3if3073.assessment1.R
import org.d3if3073.assessment1.databinding.MainFragmentBinding
import org.d3if3073.assessment1.db.KalkulatorDb
import org.d3if3073.assessment1.model.Hasil
import org.d3if3073.assessment1.ui.MainViewModel

class MainFragment : Fragment() {

    private lateinit var binding: MainFragmentBinding

    private val viewModel: MainViewModel by lazy {
        val db = KalkulatorDb.getInstance(requireContext())
        val factory = HitungViewModelFactory(db.dao)
        ViewModelProvider(this, factory)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btHitung.setOnClickListener { hitungBilangan() }
        binding.btBagikan.setOnClickListener { bagikanData() }
        viewModel.getHasil().observe(requireActivity()) { showResult(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_history -> {
                findNavController().navigate(R.id.action_mainFragment_to_historiFragment)
                return true
            }
            R.id.menu_about -> {
                findNavController().navigate(
                    R.id.action_mainFragment_to_aboutFragment
                )
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun hitungBilangan() {
        //deklarasi variabel dan inisialisasi widget
        val bil1 = binding.bilanganPertama.text.toString()
        if (TextUtils.isEmpty(bil1)) {
            Toast.makeText(context, "Bilangan pertama tidak boleh kosong!", Toast.LENGTH_LONG)
                .show()
            return
        }
        val bil1toFloat = bil1.toFloat()
        val bil2 = binding.bilanganKedua.text.toString()
        if (TextUtils.isEmpty(bil2)) {
            Toast.makeText(context, "Bilangan kedua tidak boleh kosong!", Toast.LENGTH_LONG).show()
            return
        }
        val bil2Tofloat = bil2.toFloat()
        val operator = binding.spinner.selectedItem.toString()
        if (TextUtils.isEmpty(operator)) {
            Toast.makeText(context, "Pilih operator aritmatika!", Toast.LENGTH_LONG).show()
            return
        }
        viewModel.calculate(bil1toFloat, bil2Tofloat, operator)
    }

    private fun showResult(result: Hasil?) {
        if (result == null) return
        binding.hasil.text = "Hasil : " + result.hasil
        binding.btBagikan.visibility = View.VISIBLE
    }

    @SuppressLint("StringFormatMatches")
    private fun bagikanData() {
        val pesan = getString(
            R.string.templateBagikan,
            binding.bilanganPertama.text,
            binding.bilanganKedua.text,
            binding.spinner.selectedItem,
            binding.hasil.text
        )

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, pesan)
        if (shareIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(shareIntent)
        }
    }
}