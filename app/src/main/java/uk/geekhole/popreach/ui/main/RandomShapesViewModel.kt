package uk.geekhole.popreach.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.subjects.BehaviorSubject
import uk.geekhole.popreach.models.Shape
import uk.geekhole.popreach.models.ShapeAction
import uk.geekhole.popreach.models.ShapeStat
import uk.geekhole.popreach.models.Shapes
import java.util.*

class RandomShapesViewModel(private val maxX: Int, private val maxY: Int, private val shapeSize: Int) : ViewModel() {

    class RandomShapesViewModelFactory(private val maxX: Int, private val maxY: Int, private val shapeSize: Int) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = RandomShapesViewModel(maxX, maxY, shapeSize) as T
    }

    private val shapesData = mutableListOf<Shape>()
    private val transformActions = mutableListOf<ShapeAction>()

    // Regenerate every time so that we have the most up-to-date stats and we aren't relying on having to manually update two different lists.
    private val statsData: List<ShapeStat>
        get() {
            val dataset = mutableListOf<ShapeStat>()
            dataset.add(ShapeStat(Shapes.SQUARE, shapesData.count { it.type == Shapes.SQUARE }))
            dataset.add(ShapeStat(Shapes.CIRCLE, shapesData.count { it.type == Shapes.CIRCLE }))
            dataset.add(ShapeStat(Shapes.TRIANGLE, shapesData.count { it.type == Shapes.TRIANGLE }))
            return dataset
        }

    val shapesObservable: BehaviorSubject<List<Shape>> = BehaviorSubject.create()
    val statsObservable: BehaviorSubject<List<ShapeStat>> = BehaviorSubject.create()

    fun addSquare() {
        addShape(Shapes.SQUARE)
    }

    fun addCircle() {
        addShape(Shapes.CIRCLE)
    }

    fun addTriangle() {
        addShape(Shapes.TRIANGLE)
    }

    fun undoLast() {
        if (transformActions.isEmpty()) return

        val transformation = transformActions.removeLast()
        when (transformation.action) {
            ShapeAction.ActionType.ADD -> shapesData.removeLast()
            ShapeAction.ActionType.TRANSFORM -> undoTransformation(transformation)
            ShapeAction.ActionType.DELETE -> shapesData.addAll(transformation.shapes)
        }

        notifyDataChanged()
    }

    fun resolveClick(x: Int, y: Int) {
        val changeShapes = getShapesForCoordinates(x, y)

        if (changeShapes.isNullOrEmpty()) return

        // Since we aren't checking bounds when adding random shapes we could have more than one shape if they are overlapping.
        changeShapes.forEach {
            when (it.type) {
                Shapes.SQUARE -> it.type = Shapes.CIRCLE
                Shapes.CIRCLE -> it.type = Shapes.TRIANGLE
                Shapes.TRIANGLE -> it.type = Shapes.SQUARE
            }
        }

        transformActions.add(ShapeAction(ShapeAction.ActionType.TRANSFORM, changeShapes))

        notifyDataChanged()
    }

    fun resolveLongClick(x: Int, y: Int) {
        val deleteShapes = getShapesForCoordinates(x, y)

        if (deleteShapes.isNullOrEmpty()) return

        // Since we aren't checking bounds when adding random shapes we could have more than one shape if they are overlapping.
        shapesData.removeAll(deleteShapes)
        // Save the the shape so that we deleted so that we know which shape to re-add when undo is pressed
        transformActions.add(ShapeAction(ShapeAction.ActionType.DELETE, deleteShapes))


        notifyDataChanged()
    }

    fun deleteAll(type: Shapes) {
        val shapesToRemove = shapesData.filter { it.type == type }
        shapesData.removeAll(shapesToRemove)
        transformActions.add(ShapeAction(ShapeAction.ActionType.DELETE, shapesToRemove))

        notifyDataChanged()
    }

    private fun addShape(type: Shapes) {
        val x = (0..maxX - shapeSize).random()
        val y = (0..maxY - shapeSize).random()

        val shape = Shape(UUID.randomUUID(), type, x, y, shapeSize)
        shapesData.add(shape)

        transformActions.add(ShapeAction(ShapeAction.ActionType.ADD, listOf(shape)))

        notifyDataChanged()
    }

    private fun undoTransformation(action: ShapeAction) {
        if (action.action != ShapeAction.ActionType.TRANSFORM) return // Shouldn't happen if we're doing everything right!

        val shapes = shapesData.filter { action.shapes.contains(it) }

        shapes.forEach { shape ->
            when (shape.type) {
                Shapes.SQUARE -> shape.type = Shapes.TRIANGLE
                Shapes.CIRCLE -> shape.type = Shapes.SQUARE
                Shapes.TRIANGLE -> shape.type = Shapes.CIRCLE
            }
        }
    }

    private fun getShapesForCoordinates(x: Int, y: Int): List<Shape> {
        return shapesData.filter {
            // Filter the bounds of the tap to see if we need to do anything
            it.startX <= x && it.endX >= x
                    && it.startY <= y && it.endY >= y
        }
    }

    private fun notifyDataChanged() {
        shapesObservable.onNext(shapesData)
        statsObservable.onNext(statsData)
    }
}