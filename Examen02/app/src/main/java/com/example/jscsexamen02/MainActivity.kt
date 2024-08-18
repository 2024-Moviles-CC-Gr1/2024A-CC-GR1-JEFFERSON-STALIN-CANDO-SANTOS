package com.example.jscsexamen02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnVerDepartamento = findViewById<Button>(R.id.btn_ver_Departamentos)
        btnVerDepartamento.setOnClickListener {
            irActividad(DepartamentosActivity::class.java)
        }

        val btnVerEmpleados = findViewById<Button>(R.id.btn_ver_Empleados)
        btnVerEmpleados.setOnClickListener {
            irActividad(EmpleadosActivity::class.java)
        }
    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}



