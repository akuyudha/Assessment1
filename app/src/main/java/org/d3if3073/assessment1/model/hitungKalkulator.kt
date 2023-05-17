package org.d3if3073.assessment1.model

fun Hitung.hitungKalkulator(): Hasil {
    val bil1 = bil1
    val bil2 = bil2
    val operatorPerhitungan =
        if (operator.equals("+ (Penjumlahan)", ignoreCase = true)) {
            bil1 + bil2
        } else if (operator.equals("- (Pengurangan)", ignoreCase = true)) {
            bil1 - bil2
        } else if (operator.equals("x (Perkalian)", ignoreCase = true)) {
            bil1 * bil2
        } else if (operator.equals("/ (Pembagian)", ignoreCase = true)) {
            bil1 / bil2
        } else {
            0f
        }
    return Hasil(operatorPerhitungan)
}