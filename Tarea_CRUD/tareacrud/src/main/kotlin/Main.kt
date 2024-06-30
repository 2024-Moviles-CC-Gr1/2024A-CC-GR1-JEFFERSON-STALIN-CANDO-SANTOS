import crud.GestorCRUD

fun main() {
    val gestor = GestorCRUD()

    while (true) {
        println("\n--- CRUD de Departamentos y Empleados ---")
        println("1. Agregar Departamento")
        println("2. Agregar Empleado")
        println("3. Editar Departamento")
        println("4. Editar Empleado")
        println("5. Eliminar Departamento")
        println("6. Eliminar Empleado")
        println("7. Listar Departamentos")
        println("8. Listar Empleados")
        println("0. Salir")

        print("Seleccione una opción: ")
        when (readLine()) {
            "1" -> {
                print("Nombre del nuevo departamento: ")
                val nombre = readLine() ?: ""
                gestor.agregarDepartamento(nombre)
            }
            "2" -> {
                print("Nombre del nuevo empleado: ")
                val nombre = readLine() ?: ""
                gestor.listarDepartamentos()
                print("Ingrese ID del departamento: ")
                val departamentoId = readLine()?.toIntOrNull() ?: 0
                gestor.agregarEmpleado(nombre, departamentoId)
            }
            "3" -> {
                gestor.listarDepartamentos()
                print("ID del departamento a editar: ")
                val id = readLine()?.toIntOrNull() ?: 0
                print("Nuevo nombre del departamento: ")
                val nuevoNombre = readLine() ?: ""
                gestor.editarDepartamento(id, nuevoNombre)
            }
            "4" -> {
                gestor.listarEmpleados()
                print("ID del empleado a editar: ")
                val id = readLine()?.toIntOrNull() ?: 0
                print("Nuevo nombre del empleado: ")
                val nuevoNombre = readLine() ?: ""
                print("Nuevo ID del departamento: ")
                val nuevoDepartamentoId = readLine()?.toIntOrNull() ?: 0
                gestor.editarEmpleado(id, nuevoNombre, nuevoDepartamentoId)
            }
            "5" -> {
                gestor.listarEmpleados()
                print("ID del departamento a eliminar: ")
                val id = readLine()?.toIntOrNull() ?: 0
                gestor.eliminarDepartamento(id)
            }
            "6" -> {
                gestor.listarEmpleados()
                print("ID del empleado a eliminar: ")
                val id = readLine()?.toIntOrNull() ?: 0
                gestor.eliminarEmpleado(id)
            }
            "7" -> gestor.listarDepartamentos()
            "8" -> gestor.listarEmpleados()
            "0" -> {
                println("Gracias por usar el sistema. ¡Hasta luego!")
                return
            }
            else -> println("Opción no válida. Por favor, intente de nuevo.")
        }
    }
}