package uk.geekhole.popreach.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.activityViewModels
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import uk.geekhole.popreach.R
import uk.geekhole.popreach.databinding.FragmentRandomShapesBinding
import uk.geekhole.popreach.ui.global.BaseFragment
import uk.geekhole.popreach.ui.stats.StatsFragment


class RandomShapesFragment : BaseFragment(R.layout.fragment_random_shapes), GestureDetector.OnGestureListener {

    companion object {
        fun newInstance() = RandomShapesFragment()
    }

    private val binding by viewBinding(FragmentRandomShapesBinding::bind)
    private val viewModel: RandomShapesViewModel by activityViewModels {
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        RandomShapesViewModel.RandomShapesViewModelFactory(displayMetrics.widthPixels, displayMetrics.heightPixels, resources.getDimension(R.dimen.width_height_shape).toInt())
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.createSquare.setOnClickListener(onSquareClicked)
        binding.createCircle.setOnClickListener(onCircleClicked)
        binding.createTriangle.setOnClickListener(onTriangleClicked)
        binding.undo.setOnClickListener(onUndoClicked)
        binding.stats.setOnClickListener(onStatsClicked)
        binding.shapeView.gestureDetector = GestureDetectorCompat(activity, this)

        disposables.add(viewModel.shapesObservable.observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                binding.shapeView.setShapeData(it)
            })
    }

    private val onSquareClicked = View.OnClickListener {
        viewModel.addSquare()
    }

    private val onCircleClicked = View.OnClickListener {
        viewModel.addCircle()
    }

    private val onTriangleClicked = View.OnClickListener {
        viewModel.addTriangle()
    }

    private val onUndoClicked = View.OnClickListener {
        viewModel.undoLast()
    }

    private val onStatsClicked = View.OnClickListener {
        fragmentManager.showFragment(StatsFragment.newInstance(), false)
    }

    override fun onDown(event: MotionEvent?): Boolean {
        // Not required
        return true
    }

    override fun onShowPress(p0: MotionEvent?) {
        // Not required
    }

    override fun onSingleTapUp(event: MotionEvent): Boolean {
        viewModel.resolveClick(event.x.toInt(), event.y.toInt())
        return false
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        // Not required
        return false
    }

    override fun onLongPress(event: MotionEvent) {
        viewModel.resolveLongClick(event.x.toInt(), event.y.toInt())
    }

    override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        // Not required
        return false
    }
}