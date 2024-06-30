package data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import models.Departamento
import models.Empleado
import java.io.File

class GestorDatos {
    private val gson = Gson()
    private val archivoDepartamentos = "src/main/resources/departamentos.json"
    private val archivoEmpleados = "src/main/resources/empleados.json"

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