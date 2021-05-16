# Brief
We’d like you to build a simple Android app that allows users to create shapes on a canvas. 
## Your app should perform the following
The required task is to build an Android application consisting of two screens:
### Editor screen
- Display 3 buttons (square, circle, and triangle). Each time you tap on a button: an object of that shape will be created, and displayed in a random position on the screen.
- The user should be able to tap each shape which will cause it to become another shape: tapping a square will make it a circle, tapping a circle will make it a triangle, tapping a triangle will make it a square.
- Display an “Undo” button. Each time you press undo it will cancel the last action performed; which will be either the creation or the transformation of a shape object.
- Display a “Stats” button that will open the second screen of the app.
### Stats screen
- Show a list that will display the count of all the objects placed on the canvas, grouped by shape.
## Suggested improvements
- Delete all: in the stats screen, implement a functionality to delete a row. It will cause all the shapes of that specific kind to be removed from the canvas.
- Remove shape: in the canvas, if you long tap on a shape you can delete it. You should also be able to undo the deletion of an object.

*Don’t focus too much on the user interface, using default Android views and/or basic custom views is fine.*
