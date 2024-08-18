package com.example.jscsexamen02

class Departamento (
    val id: Int,
    var nombre: String,
    var empleados: MutableList<Empleado> = mutableListOf()
        ){

    override fun toString(): String {
        return "$nombre"
    }
}