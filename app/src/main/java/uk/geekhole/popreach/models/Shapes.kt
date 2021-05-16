package uk.geekhole.popreach.models

import androidx.annotation.DrawableRes
import uk.geekhole.popreach.R

enum class Shapes(@DrawableRes val resourceId: Int) {
    SQUARE(R.drawable.icn_square), CIRCLE(R.drawable.icn_circle), TRIANGLE(R.drawable.icn_triangle)
}