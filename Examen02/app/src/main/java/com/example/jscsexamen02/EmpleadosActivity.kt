package com.example.jscsexamen02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton

class EmpleadosActivity : AppCompatActivity() {
    private lateinit var gestorDatos: GestorDatos
    private lateinit var listViewEmpleados: ListView
    private lateinit var empleados: MutableList<Empleado>
    private lateinit var departamentos: List<Departamento>
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empleados)

        Log.d("EmpleadosActivity", "Iniciando EmpleadosActivity")

        gestorDatos = GestorDatos(this)
        listViewEmpleados = findViewById(R.id.listViewEmpleados)
        val fabAddEmployee = findViewById<FloatingActionButton>(R.id.fab_add_employee)

        registerForContextMenu(listViewEmpleados)

        mostrarEmpleados()

        fabAddEmployee.setOnClickListener {
            showCreateDialog()
        }
    }

    private fun mostrarEmpleados() {
        Log.d("EmpleadosActivity", "Iniciando carga de empleados")
        empleados = gestorDatos.cargarEmpleados().toMutableList()
        departamentos = gestorDatos.cargarDepartamentos()
        Log.d("EmpleadosActivity", "Empleados cargados: ${empleados.size}")

        val empleadosConDepartamento = empleados.map { empleado ->
            val departamentoNombre = departamentos.find { it.id == empleado.departamentoId }?.nombre ?: "Desconocido"
            "${empleado.nombre} - $departamentoNombre"
        }

        if (empleadosConDepartamento.isEmpty()) {
            Log.w("EmpleadosActivity", "No se encontraron empleados para mostrar")
            Toast.makeText(this, "No hay empleados para mostrar", Toast.LENGTH_SHORT).show()
        }

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, empleadosConDepartamento)
        listViewEmpleados.adapter = adapter
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.employee_context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val selectedEmployee = empleados[info.position]

        return when (item.itemId) {
            R.id.action_edit_employee -> {
                showEditDialog(selectedEmployee)
                true
            }
            R.id.action_delete_employee -> {
                showDeleteDialog(selectedEmployee)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun showCreateDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_employee, null)
        val editTextName = dialogView.findViewById<EditText>(R.id.editTextEmployeeName)
        val spinnerDepartment = dialogView.findViewById<Spinner>(R.id.spinnerDepartment)

        val departmentAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, departamentos)
        departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDepartment.adapter = departmentAdapter

        AlertDialog.Builder(this)
            .setTitle("Agregar Empleado")
            .setView(dialogView)
            .setPositiveButton("Agregar") { _, _ ->
                val name = editTextName.text.toString()
                val selectedDepartment = spinnerDepartment.selectedItem as Departamento
                if (name.isNotBlank()) {
                    val newEmployee = Empleado(
                        id = (empleados.maxOfOrNull { it.id } ?: 0) + 1,
                        nombre = name,
                        departamentoId = selectedDepartment.id
                    )
                    empleados.add(newEmployee)
                    gestorDatos.guardarEmpleados(empleados)
                    actualizarLista()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun showEditDialog(employee: Empleado) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_employee, null)
        val editTextName = dialogView.findViewById<EditText>(R.id.editTextEmployeeName)
        val spinnerDepartment = dialogView.findViewById<Spinner>(R.id.spinnerDepartment)

        editTextName.setText(employee.nombre)

        val departmentAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, departamentos)
        departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDepartment.adapter = departmentAdapter

        val departmentIndex = departamentos.indexOfFirst { it.id == employee.departamentoId }
        if (departmentIndex != -1) {
            spinnerDepartment.setSelection(departmentIndex)
        }

        AlertDialog.Builder(this)
            .setTitle("Editar Empleado")
            .setView(dialogView)
            .setPositiveButton("Guardar") { _, _ ->
                val nuevoNombre = editTextName.text.toString()
                val nuevoDepartamento = spinnerDepartment.selectedItem as Departamento
                if (nuevoNombre.isNotBlank()) {
                    val index = empleados.indexOfFirst { it.id == employee.id }
                    if (index != -1) {
                        empleados[index].nombre = nuevoNombre
                        empleados[index].departamentoId = nuevoDepartamento.id
                        gestorDatos.guardarEmpleados(empleados)
                        actualizarLista()
                    }
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun showDeleteDialog(employee: Empleado) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar Empleado")
            .setMessage("¿Estás seguro de que deseas eliminar este empleado?")
            .setPositiveButton("Eliminar") { _, _ ->
                empleados.remove(employee)
                gestorDatos.guardarEmpleados(empleados)
                actualizarLista()
                Toast.makeText(this, "Empleado eliminado", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun actualizarLista() {
        Log.d("EmpleadosActivity", "Actualizando lista de empleados")
        empleados = gestorDatos.cargarEmpleados().toMutableList()
        Log.d("EmpleadosActivity", "Empleados actualizados: ${empleados.size}")

        val empleadosConDepartamento = empleados.map { empleado ->
            val departamentoNombre = departamentos.find { it.id == empleado.departamentoId }?.nombre ?: "Desconocido"
            "${empleado.nombre} - $departamentoNombre"
        }

        adapter.clear()
        adapter.addAll(empleadosConDepartamento)
        adapter.notifyDataSetChanged()
    }
}