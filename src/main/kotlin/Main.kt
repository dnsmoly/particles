package com.smoly

fun main() {
    val params = ParticleParams(60, 1, 1000, 1000, 1000)
    val system = ParticleSystem(params)
    repeat(100) {
        system.start()
        system.display()
    }
}