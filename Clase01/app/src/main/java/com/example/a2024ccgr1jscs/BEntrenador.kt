package com.example.a2024ccgr1jscs

class BEntrenador (
    var id: Int,
    var nombre: String,
    var description: String
){
    override fun toString(): String {
        return "$nombre ${description}"
    }
}