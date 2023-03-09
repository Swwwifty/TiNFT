package online.tinft.common

private const val LAMPORT_RATE = 1_000_000_000
fun Double.format2Digits() = (this * 100).toInt() / 100.0

fun Double.format2DigitsLamport() = (this / LAMPORT_RATE * 100).toInt() / 100.0
