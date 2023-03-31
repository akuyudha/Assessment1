package org.d3if3073.assessment1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import org.d3if3073.assessment1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btHitung.setOnClickListener { hitungBilangan() }

        }

    private fun hitungBilangan() {
        //deklarasi variabel dan inisialisasi widget
        val bil1 = binding.bilanganPertama.text.toString()
        if (TextUtils.isEmpty(bil1)) {
            Toast.makeText(this, "Bilangan pertama tidak boleh kosong!", Toast.LENGTH_LONG).show()
            return
        }
        val bil1toInt = bil1.toInt()
        val bil1toFloat = bil1.toFloat()
        val bil2 = binding.bilanganKedua.text.toString()
        if (TextUtils.isEmpty(bil2)) {
            Toast.makeText(this, "Bilangan kedua tidak boleh kosong!", Toast.LENGTH_LONG).show()
            return
        }
        val bil2ToInt = bil2.toInt()
        val bil2Tofloat = bil2.toFloat()
        val operator = binding.spinner.selectedItem.toString()
        if (TextUtils.isEmpty(operator)) {
            Toast.makeText(this, "Pilih operator aritmatika!", Toast.LENGTH_LONG).show()
            return
        }



        if(operator.equals("+ (Penjumlahan)", ignoreCase = true)){
            var hitungJumlah = bil1toInt + bil2ToInt
            binding.hasil.text = "Hasil :  $hitungJumlah"
        }else if(operator.equals("- (Pengurangan)", ignoreCase = true)){
            var hitungKurang = bil1toInt - bil2ToInt
            binding.hasil.text = "Hasil : $hitungKurang"
        }else if(operator.equals("x (Perkalian)", ignoreCase = true)){
            var hitungKali = bil1toInt * bil2ToInt
            binding.hasil.text = "Hasil : $hitungKali"
        }else if(operator.equals("/ (Pembagian)", ignoreCase = true)){
            var hitungBagi = bil1toFloat / bil2Tofloat
            binding.hasil.text = "Hasil : $hitungBagi"
        }
    }
}