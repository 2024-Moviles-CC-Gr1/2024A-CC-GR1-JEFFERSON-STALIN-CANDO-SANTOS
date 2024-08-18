package com.example.jscsexamen02

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.FileNotFoundException
import java.io.IOException

class GestorDatos(private val context: Context) {
    private val gson = Gson()

    fun cargarDepartamentos(): List<Departamento> {
        return try {
            val json = context.openFileInput("departamentos.json").bufferedReader().use { it.readText() }
            Log.d("GestorDatos", "JSON de departamentos leído: $json")
            if (json.isBlank()) {
                Log.w("GestorDatos", "El archivo de departamentos está vacío")
                emptyList()
            } else {
                gson.fromJson<List<Departamento>>(json, object : TypeToken<List<Departamento>>() {}.type)
                    .also { Log.d("GestorDatos", "Departamentos cargados: ${it.size}") }
            }
        } catch (e: FileNotFoundException) {
            Log.w("GestorDatos", "Archivo de departamentos no encontrado. Creando nuevo.")
            emptyList()
        } catch (e: Exception) {
            Log.e("GestorDatos", "Error al cargar departamentos", e)
            emptyList()
        }
    }

    fun cargarEmpleados(): List<Empleado> {
        return try {
            val json = context.openFileInput("empleados.json").bufferedReader().use { it.readText() }
            Log.d("GestorDatos", "JSON de empleados leído: $json")
            if (json.isBlank()) {
                Log.w("GestorDatos", "El archivo de empleados está vacío")
                emptyList()
            } else {
                gson.fromJson<List<Empleado>>(json, object : TypeToken<List<Empleado>>() {}.type)
                    .also { Log.d("GestorDatos", "Empleados cargados: ${it.size}") }
            }
        } catch (e: FileNotFoundException) {
            Log.w("GestorDatos", "Archivo de empleados no encontrado. Creando nuevo.")
            emptyList()
        } catch (e: Exception) {
            Log.e("GestorDatos", "Error al cargar empleados", e)
            emptyList()
        }
    }

    fun guardarDepartamentos(departamentos: List<Departamento>) {
        try {
            val json = gson.toJson(departamentos)
            Log.d("GestorDatos", "Guardando departamentos: $json")
            context.openFileOutput("departamentos.json", Context.MODE_PRIVATE).use {
                it.write(json.toByteArray())
            }
            Log.d("GestorDatos", "Departamentos guardados exitosamente")
        } catch (e: IOException) {
            Log.e("GestorDatos", "Error al guardar departamentos", e)
        }
    }

    fun guardarEmpleados(empleados: List<Empleado>) {
        try {
            val json = gson.toJson(empleados)
            Log.d("GestorDatos", "Guardando empleados: $json")
            context.openFileOutput("empleados.json", Context.MODE_PRIVATE).use {
                it.write(json.toByteArray())
            }
            Log.d("GestorDatos", "Empleados guardados exitosamente")
        } catch (e: IOException) {
            Log.e("GestorDatos", "Error al guardar empleados", e)
        }
    }
}