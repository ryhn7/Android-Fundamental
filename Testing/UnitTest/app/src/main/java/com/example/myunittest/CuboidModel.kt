package com.example.myunittest

class CuboidModel {
    private var width = 0.0
    private var height = 0.0
    private var length = 0.0

    fun getVolume(): Double = width * height * length

    fun getSurfaceArea(): Double {
        val wl = width * length
        val wh = width * height
        val lh = length * height
        return 2 * (wl + wh + lh)
    }

    fun getCircumference(): Double = 4 * (width + height + length)

    fun save(width: Double, height: Double, length: Double) {
        this.width = width
        this.height = height
        this.length = length
    }
}
