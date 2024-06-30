package models

data class Departamento(
    val id: Int,
    var nombre: String,
    var empleados: MutableList<Empleado> = mutableListOf()
)