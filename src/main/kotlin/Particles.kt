package com.smoly

import kotlin.random.Random

data class Particle(
    var x: Double,
    var y: Double,
    var speed: Int,
    var lifetime: Int,
)

class ParticleSystem(
    val params: ParticleParams
) {
    val particles: MutableList<Particle>
    val counts: MutableList<MutableList<Int>>

    init {
        particles = mutableListOf()
        repeat(params.particleCount) {
            particles.addLast(
                Particle(
                    0.0,
                    0.0,
                    Random.nextInt(0, params.maxSpeed),
                    Random.nextInt(0, params.maxSpeed)
                )
            )
        }
        counts = MutableList(params.height) {
            MutableList(params.width) { 0 }
        }
        if (params.debug) {
            println(particles)
            println(counts)
        }
    }

    fun start() {
        particles.forEach {
            reset(it)
        }
    }

    fun reset(p: Particle) {
        // todo if < 0 them 0 if > max = max
        val normRand = java.util.Random().nextGaussian()
        p.x = normRand * (params.width / 8.0) + params.width / 2.0
        if (params.debug) println("normRand: $normRand, x: ${p.x}")
        p.y = 0.0
        p.speed = Random.nextInt(0, params.maxSpeed)
        p.lifetime = Random.nextInt(0, params.maxLifetimeMS)
    }

    fun display() {
        for (i in 0 until params.height) {
            for (j in 0 until params.width) {
                counts[i][j] = 0
            }
        }
        particles.forEach {
            counts[it.y.toInt()][it.x.toInt()]++
        }
        counts.reverse()

        for (i in 0 until params.height) {
            for (j in 0 until params.width) {
                print(ascii(i, j))
            }
            println()
        }
    }

    private fun ascii(row: Int, col: Int): String {
        return when (counts[row][col]) {
            0 -> "."
            in 1..5 -> "▁"
            in 6..10 -> "▂"
            in 11..15 -> "▃"
            in 16..20 -> "▄"
            in 21..25 -> "▅"
            in 26..30 -> "▆"
            in 31..35 -> "▇"
            in 36..40 -> "█"
            else -> "█"
        }
    }
}

data class ParticleParams(
    val width: Int,
    val height: Int,
    val particleCount: Int,
    val maxSpeed: Int,
    val maxLifetimeMS: Int,
    val debug: Boolean = false
)