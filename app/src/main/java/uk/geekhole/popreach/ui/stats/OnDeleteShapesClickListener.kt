package uk.geekhole.popreach.ui.stats

import uk.geekhole.popreach.models.ShapeStat

interface OnDeleteShapesClickListener {
    fun onDeleteClicked(stat: ShapeStat)
}