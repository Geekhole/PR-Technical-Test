package uk.geekhole.popreach.ui.stats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uk.geekhole.popreach.databinding.RowStatBinding
import uk.geekhole.popreach.models.ShapeStat

class StatRecyclerAdapter(private var data: List<ShapeStat>, private val onDeleteShapesClickListener: OnDeleteShapesClickListener) : RecyclerView.Adapter<StatViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatViewHolder {
        return StatViewHolder(RowStatBinding.inflate(LayoutInflater.from(parent.context), parent, false), onDeleteShapesClickListener)
    }

    override fun onBindViewHolder(holder: StatViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.count()

    fun updateData(data: List<ShapeStat>) {
        this.data = data
        notifyDataSetChanged() // Bad in production apps. I'd usually use Diff util or AsyncListDiffer.
    }
}