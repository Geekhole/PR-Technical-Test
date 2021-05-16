package uk.geekhole.popreach.models

data class ShapeAction(val action: ActionType, val shapes: List<Shape>) {
    enum class ActionType {
        ADD, DELETE, TRANSFORM
    }
}