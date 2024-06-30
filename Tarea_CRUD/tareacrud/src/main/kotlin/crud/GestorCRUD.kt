package crud

import data.GestorDatos
import models.Departamento
import models.Empleado

class GestorCRUD {
    private val gestor = GestorDatos()
    private var departamentos = gestor.cargarDepartamentos().toMutableList()
    private var empleados = gestor.cargarEmpleados().toMutableList()

    fun agregarDepartamento(nombre: String) {
        val id = departamentos.maxByOrNull { it.id }?.id?.plus(1) ?: 1
        departamentos.add(Departamento(id, nombre))
        gestor.guardarDepartamentos(departamentos)
        println("Departamento agregado con éxito.")
    }

    fun agregarEmpleado(nombre: String, departamentoId: Int) {
        val id = empleados.maxByOrNull { it.id }?.id?.plus(1) ?: 1
        val empleado = Empleado(id, nombre, departamentoId)
        empleados.add(empleado)
        gestor.guardarEmpleados(empleados)

        val departamento = departamentos.find { it.id == departamentoId }
        departamento?.empleados?.add(empleado)
        gestor.guardarDepartamentos(departamentos)

        println("Empleado agregado con éxito.")
    }

    fun editarDepartamento(id: Int, nuevoNombre: String) {
        val departamento = departamentos.find { it.id == id }
        if (departamento != null) {
            departamento.nombre = nuevoNombre
            gestor.guardarDepartamentos(departamentos)
            println("Departamento editado con éxito.")
        } else {
            println("Departamento no encontrado.")
        }
    }

    fun editarEmpleado(id: Int, nuevoNombre: String, nuevoDepartamentoId: Int) {
        val empleado = empleados.find { it.id == id }
        if (empleado != null) {
            departamentos.find { it.id == empleado.departamentoId }?.empleados?.removeIf { it.id == id }

            empleado.nombre = nuevoNombre
            empleado.departamentoId = nuevoDepartamentoId

            departamentos.find { it.id == nuevoDepartamentoId }?.empleados?.add(empleado)

            gestor.guardarEmpleados(empleados)
            gestor.guardarDepartamentos(departamentos)
            println("Empleado editado con éxito.")
        } else {
            println("Empleado no encontrado.")
        }
    }

    fun eliminarDepartamento(id: Int) {
        val departamento = departamentos.find { it.id == id }
        if (departamento != null) {
            departamentos.remove(departamento)
            empleados.removeAll { it.departamentoId == id }
            gestor.guardarDepartamentos(departamentos)
            gestor.guardarEmpleados(empleados)
            println("Departamento y sus empleados eliminados con éxito.")
        } else {
            println("Departamento no encontrado.")
        }
    }

    fun eliminarEmpleado(id: Int) {
        val empleado = empleados.find { it.id == id }
        if (empleado != null) {
            empleados.remove(empleado)
            departamentos.find { it.id == empleado.departamentoId }?.empleados?.removeIf { it.id == id }
            gestor.guardarEmpleados(empleados)
            gestor.guardarDepartamentos(departamentos)
            println("Empleado eliminado con éxito.")
        } else {
            println("Empleado no encontrado.")
        }
    }

    fun listarDepartamentos() {
        departamentos.forEach { dept ->
            println("ID: ${dept.id}, Nombre: ${dept.nombre}")
            println("Empleados:")
            dept.empleados.forEach { emp ->
                println("  - ID: ${emp.id}, Nombre: ${emp.nombre}")
            }
            println()
        }
    }

    fun listarEmpleados() {
        empleados.forEach { emp ->
            val departamento = departamentos.find { it.id == emp.departamentoId }
            println("ID: ${emp.id}, Nombre: ${emp.nombre}, Departamento: ${departamento?.nombre ?: "No asignado"}")
        }
    }
}