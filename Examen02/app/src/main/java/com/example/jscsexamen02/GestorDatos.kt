package com.example.jscsexamen02

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.jscsexamen02.Departamento
import com.example.jscsexamen02.Empleado
import java.io.File

class GestorDatos {
    private val gson = Gson()
    private val archivoDepartamentos = "java/com/example/jscsexamen02/database/departamentos.json"
    private val archivoEmpleados = "java/com/example/jscsexamen02/database/empleados.json"

    fun cargarDepartamentos(): List<Departamento> {
        val archivo = File(archivoDepartamentos)
        return if (archivo.exists()) {
            gson.fromJson(archivo.readText(), object : TypeToken<List<Departamento>>() {}.type)
        } else {
            emptyList()
        }
    }

    fun guardarDepartamentos(departamentos: List<Departamento>) {
        File(archivoDepartamentos).writeText(gson.toJson(departamentos))
    }

    fun cargarEmpleados(): List<Empleado> {
        val archivo = File(archivoEmpleados)
        return if (archivo.exists()) {
            gson.fromJson(archivo.readText(), object : TypeToken<List<Empleado>>() {}.type)
        } else {
            emptyList()
        }
    }

    fun guardarEmpleados(empleados: List<Empleado>) {
        File(archivoEmpleados).writeText(gson.toJson(empleados))
    }
}