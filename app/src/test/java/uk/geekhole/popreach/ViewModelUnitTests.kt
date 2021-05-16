package uk.geekhole.popreach

import org.junit.Test
import uk.geekhole.popreach.models.Shapes
import uk.geekhole.popreach.ui.main.RandomShapesViewModel

class ViewModelUnitTests {
    @Test
    fun shapeAddition() {
        val model = RandomShapesViewModel(2000, 2000, 60)
        model.addSquare()
        model.addCircle()
        model.addTriangle()

        val observer = model.shapesObservable.test()
        val values = observer.values().last()

        assert(values != null)
        assert(values[0].type == Shapes.SQUARE) // Wouldn't ever use  in production code.
        assert(values[1].type == Shapes.CIRCLE)
        assert(values[2].type == Shapes.TRIANGLE)
    }

    @Test
    fun shapeTransformation() {
        val model = RandomShapesViewModel(2000, 2000, 60)
        model.addSquare()
        model.addCircle()
        model.addTriangle()

        val observer = model.shapesObservable.test()
        var values = observer.values().last()

        model.resolveClick(values[0].startX, values[0].startY)
        model.resolveClick(values[1].startX, values[1].startY)
        model.resolveClick(values[2].startX, values[2].startY)

        values = model.shapesObservable.value

        assert(values[0].type == Shapes.CIRCLE)
        assert(values[1].type == Shapes.TRIANGLE)
        assert(values[2].type == Shapes.SQUARE)

    }

    @Test
    fun undoLast() {
        val model = RandomShapesViewModel(2000, 2000, 60)
        model.addSquare()
        model.addCircle()
        model.addTriangle()

        model.undoLast() // Should remove the triangle

        val observer = model.shapesObservable.test()

        var values = observer.values().last()

        assert(values.count() == 2)
        assert(values[0].type == Shapes.SQUARE)
        assert(values[1].type == Shapes.CIRCLE)

        model.resolveClick(values[1].startX, values[1].startY) // Should change the circle to a triangle

        values = observer.values().last()

        assert(values.count() == 2)
        assert(values[0].type == Shapes.SQUARE)
        assert(values[1].type == Shapes.TRIANGLE)


    }

    @Test
    fun deleteAllAndUndo() {
        val model = RandomShapesViewModel(2000, 2000, 60)
        model.addSquare()
        model.addCircle()
        model.addTriangle()
        model.addCircle()
        model.addTriangle()
        model.addSquare()
        model.addTriangle()

        model.deleteAll(Shapes.TRIANGLE)

        val observer = model.shapesObservable.test()

        observer.assertValue {
            it.count() == 4 &&
                    it.count { s -> s.type == Shapes.SQUARE } == 2 &&
                    it.count { s -> s.type == Shapes.SQUARE } == 2
        }

        model.deleteAll(Shapes.CIRCLE)

        assert(observer.valueCount() == 2)
        var currentValues = observer.values()[1]
        assert(currentValues.count() == 2
                && currentValues.count { s -> s.type == Shapes.SQUARE } == 2)


        model.undoLast()

        assert(observer.valueCount() == 3)
        currentValues = observer.values()[2]
        assert(currentValues.count() == 4 &&
                currentValues.count { s -> s.type == Shapes.SQUARE } == 2 &&
                currentValues.count { s -> s.type == Shapes.SQUARE } == 2)
    }
}