package uk.geekhole.popreach.ui.stats

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import uk.geekhole.popreach.R
import uk.geekhole.popreach.databinding.FragmentStatsBinding
import uk.geekhole.popreach.models.ShapeStat
import uk.geekhole.popreach.ui.global.BaseFragment
import uk.geekhole.popreach.ui.main.RandomShapesViewModel

class StatsFragment : BaseFragment(R.layout.fragment_stats), OnDeleteShapesClickListener {

    companion object {
        fun newInstance() = StatsFragment()
    }

    private val binding by viewBinding(FragmentStatsBinding::bind)
    private val viewModel: RandomShapesViewModel by activityViewModels()

    private var adapter: StatRecyclerAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        disposables.add(viewModel.statsObservable.observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                createOrUpdateAdapter(it)
            })
    }

    private fun createOrUpdateAdapter(data: List<ShapeStat>) {
        if (adapter == null) {
            adapter = StatRecyclerAdapter(data, this)
            binding.statsList.adapter = adapter
        } else {
            adapter?.updateData(data)
            if (binding.statsList.adapter != adapter) binding.statsList.adapter = adapter
        }
    }

    override fun onDeleteClicked(stat: ShapeStat) {
        viewModel.deleteAll(stat.type)
    }
}