package com.example.jscsexamen02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton


class DepartamentosActivity : AppCompatActivity() {
    private lateinit var gestorDatos: GestorDatos
    private lateinit var listViewDepartamentos: ListView
    private lateinit var departamentos: MutableList<Departamento>
    private lateinit var adapter: ArrayAdapter<Departamento>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_departamentos)

        gestorDatos = GestorDatos(this)
        listViewDepartamentos = findViewById(R.id.listViewDepartamentos)
        val fabAddDepartment = findViewById<FloatingActionButton>(R.id.fab_add_department)

        // Register the context menu for the ListView
        registerForContextMenu(listViewDepartamentos)

        mostrarDepartamentos()

        fabAddDepartment.setOnClickListener {
            showCreateDialog()
        }
    }


    private fun mostrarDepartamentos() {
        Log.d("DepartamentosActivity", "Iniciando carga de departamentos")
        departamentos = gestorDatos.cargarDepartamentos().toMutableList()
        Log.d("DepartamentosActivity", "Departamentos cargados: ${departamentos.size}")

        if (departamentos.isEmpty()) {
            Log.w("DepartamentosActivity", "No se encontraron departamentos para mostrar")
            Toast.makeText(this, "No hay departamentos para mostrar", Toast.LENGTH_SHORT).show()
        }

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, departamentos)
        listViewDepartamentos.adapter = adapter

        // Verificación adicional
        if (listViewDepartamentos.adapter == null || listViewDepartamentos.adapter.isEmpty) {
            Log.w("DepartamentosActivity", "El adaptador está vacío o no se ha asignado correctamente")
        }
    }


    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.department_context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val selectedDepartment = departamentos[info.position]

        return when (item.itemId) {
            R.id.action_create -> {
                showCreateDialog()
                true
            }
            R.id.action_edit -> {
                showEditDialog(selectedDepartment)
                true
            }
            R.id.action_delete -> {
                showDeleteDialog(selectedDepartment)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun showCreateDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_department, null)
        val editTextName = dialogView.findViewById<EditText>(R.id.editTextName)

        AlertDialog.Builder(this)
            .setTitle("Agregar Departamento")
            .setView(dialogView)
            .setPositiveButton("Agregar") { _, _ ->
                val name = editTextName.text.toString()
                if (name.isNotBlank()) {
                    val newDepartment = Departamento(
                        id = (departamentos.maxOfOrNull { it.id } ?: 0) + 1,
                        nombre = name
                    )
                    departamentos.add(newDepartment)
                    gestorDatos.guardarDepartamentos(departamentos)
                    actualizarLista() // Llamar a actualizarLista después de agregar
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
    private fun showEditDialog(department: Departamento) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_department, null)
        val editTextName = dialogView.findViewById<EditText>(R.id.editTextName)
        editTextName.setText(department.nombre)

        val builder = AlertDialog.Builder(this)
            .setTitle("Actualizar Departamento")
            .setView(dialogView)
            .setPositiveButton("Guardar") { _, _ ->
                val nuevoNombre = editTextName.text.toString()
                val index = departamentos.indexOfFirst { it.id == department.id }
                if (index != -1) {
                    departamentos[index].nombre = nuevoNombre
                    gestorDatos.guardarDepartamentos(departamentos)
                    actualizarLista() // Llamar a actualizarLista después de actualizar
                }
            }
            .setNegativeButton("Cancelar", null)

        builder.create().show()
    }
    private fun actualizarLista() {
        Log.d("DepartamentosActivity", "Actualizando lista de departamentos")
        departamentos = gestorDatos.cargarDepartamentos().toMutableList()
        Log.d("DepartamentosActivity", "Departamentos actualizados: ${departamentos.size}")
        adapter.clear()
        adapter.addAll(departamentos)
        adapter.notifyDataSetChanged()
    }



    private fun showDeleteDialog(department: Departamento) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar Departamento")
            .setMessage("¿Estás seguro de que deseas eliminar este departamento y todos sus empleados?")
            .setPositiveButton("Eliminar") { _, _ ->
                departamentos.remove(department)
                gestorDatos.guardarDepartamentos(departamentos)
                eliminarEmpleadosPorDepartamento(department.id)
                actualizarLista() // Cambiado de mostrarDepartamentos() a actualizarLista()
                Toast.makeText(this, "Departamento eliminado", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun eliminarEmpleadosPorDepartamento(departmentId: Int) {
        val empleados = gestorDatos.cargarEmpleados().toMutableList()
        val cantidadAntes = empleados.size
        empleados.removeAll { it.departamentoId == departmentId }
        val cantidadDespues = empleados.size
        gestorDatos.guardarEmpleados(empleados)
        Log.d("DepartamentosActivity", "Empleados eliminados: ${cantidadAntes - cantidadDespues}")
    }
}