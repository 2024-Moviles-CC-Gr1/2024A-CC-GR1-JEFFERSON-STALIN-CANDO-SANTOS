package com.example.jscsexamen02

class Empleado(
    val id: Int,
    var nombre: String,
    var departamentoId: Int
) {
    override fun toString(): String {
        return "$nombre ${departamentoId}"
    }
}