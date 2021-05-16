package uk.geekhole.popreach.ui.stats

import androidx.recyclerview.widget.RecyclerView
import uk.geekhole.popreach.databinding.RowStatBinding
import uk.geekhole.popreach.models.ShapeStat

class StatViewHolder(private val binding: RowStatBinding, private val onDeleteShapesClickListener: OnDeleteShapesClickListener) : RecyclerView.ViewHolder(binding.root) {

    fun bind(stat: ShapeStat) {
        binding.stat = stat
        binding.executePendingBindings()

        binding.delete.setOnClickListener { onDeleteShapesClickListener.onDeleteClicked(stat) }
    }
}