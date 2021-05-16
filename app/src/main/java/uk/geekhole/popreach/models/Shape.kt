package uk.geekhole.popreach.models

import java.util.*

data class Shape(var id: UUID, var type: Shapes, val startX: Int, val startY: Int, val size: Int) {
    val endX = startX + size
    val endY = startY + size
}