import java.util.*
import kotlin.collections.ArrayList

fun main(){
    println("Hola Mundo")
    //Variables inmutables (No se reasigna "=")
    val inmutable: String = "Adrian";
    //inmutable="Vecente"
    var mutable: String="Vicente"
    mutable= "Adrian" //OK

    //Duck Typing
    val ejemploVariable = "Jefferson Cando"
    val edadEjemplo: Int =12
    ejemploVariable.trim()
    //ejemploVariable=edadEjemplo//error
    val nombreProfesor: String = "Adrian Eguez"
    val sueldo: Double = 1.2
    val estadoCivil:Char='C'
    val mayorEdad: Boolean=true
    //Clases en Java
    val fechaNacimiento: Date= Date()
    println(fechaNacimiento)

    //When (Switch)
    val estadoCivilWhen="C"
    when (estadoCivilWhen){
        ("C")->{
            println("Casado")
        }
        "S"->{
            println("Soltero")
        }
        else->{
            println("No sabemos")
        }
    }
    val esSoltero=(estadoCivilWhen=="S")
    val coqueteo = if (esSoltero) "Si" else "No"
    println(esSoltero)
    println(coqueteo)


    //resvisar la clase de 09/05/2024
    //
    //
    //Arreglos
    //estaticos
    val arregloEstatico: Array<Int> = arrayOf<Int>(1,2,3)
    //dinamico
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(
        1,2,3,4,5,6,7,8,9,10
    )
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)

    //FOR EACH = > Unit
    //Iterar arreglo
    val respuestaForEach: Unit = arregloDinamico.forEach{valorActual: Int -> //
            println("Valor actual: ${valorActual}");
        }
    //"it" significa el elemento iterado
    println("iteracion de elementos con ir")
    arregloDinamico.forEach { println("Valor Actual (it): ${it}") }
    //Operador MAP -> MUTA(Modifica cambia) el arreglo
    //1) Enviamos el nuevo valor de la iteracion
    //2) Nos devuelve un NUEVO ARREGLO con valores de las iteraciones

    val respuestaMap: List<Double> = arregloDinamico
        .map{ valorActual: Int->
            return@map valorActual.toDouble()+100.00
        }

    println(respuestaMap)
    val respuestaMapDos =arregloDinamico.map{it+15}
    println(respuestaMapDos)

    //Filter ->Filtrar el arreglo

    val respuestaFilter: List<Int> = arregloDinamico
        .filter { valorActual:Int ->
            val mayoresACinco: Boolean = valorActual > 5
            return@filter mayoresACinco
        }

    val respuestaFilterDos=arregloDinamico.filter { it<= 5 }
    println(respuestaFilter)
    println(respuestaFilterDos)

    //OR AND operadores

    val respuestaAny: Boolean = arregloDinamico
        .any{ valorActual:Int ->
            return@any (valorActual>5)
        }

    println(respuestaAny)

    val respuestaAnyDos=arregloDinamico.all { it>5 }
    println(respuestaAnyDos)

    //Reduce

    val respuestaReduce: Int = arregloDinamico
        .reduce{acumulado:Int , valorActual: Int->
            return@reduce(acumulado+valorActual)
        }

    println(respuestaReduce)
    // return@reduce (acumulado + (itemCarrito.cantidad*itemCarrito.precio)
}