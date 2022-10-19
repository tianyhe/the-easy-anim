# The Easy Animator

---

## Introduction

This program helps to create simple but effective 2D animations from shapes. The program takes an
input file (examples are provided in the 'resources' folder) and creates an animation from it.

The program offers a number of options to display the animation:

| Options | Description                                                                                                                         |
|:--------|:------------------------------------------------------------------------------------------------------------------------------------|
| Text    | The animation is described in text format.                                                                                          |
| SVG     | The animation is presented in the form of an SVG file.                                                                              |
| Visual  | The animation is displayed in a window using Java Swing as the GUI.                                                                 |
| Edit    | The animation is displayed in a window using Java Swing as the GUI, and the user will have the ability to manipulate the animation. |

---

## How to use the program

---

The program comes with a JAR file that can be run from the command line.

### Steps to run the program

1. Using the command line prompt, navigate to the directory where the JAR file is located.
2. Type the following command:
   ```
   java -jar the-easy-animator.jar -in "file-path-of-animation-file" -out "file-path-of-output-file" -view "view-type" -speed "integer-ticks-per-second"
   ```

#### Characteristics of a valid arguments are:

* Each pair of arguments (-in "input-file", -out "output-file", etc.) may appear in any order
* The `-in` argument and `-view` are mandatory
* If the `-out` argument is not provided, the default is System.out
* If the `-speed` argument is not provided, the default is 1 tick per second
* The `-view` argument must be followed by a valid view type - `text`, `svg`, `visual`, or `edit`

#### Example of valid command-line argument:

* use smalldemo.txt for the animation file, and create a text view with its output going to
  System.out, and a speed of 2 ticks per second.

```
java -jar the-easy-animator.jar -in smalldemo.txt -view text -speed 20
```

* use buildings.txt for the animation file, and create an SVG view with its output going to the file
  out.svg, with a speed of 1 tick per second.

```
java -jar the-easy-animator.jar -view svg -out out.svg -in buildings.txt
```

* use smalldemo.txt for the animation file, and create a text view with its output going to
  System.out.

```
java -jar the-easy-animator.jar -in smalldemo.txt -view text
```

* use smalldemo.txt for the animation file, and create a visual view to show the animation at a
  speed of 50 ticks per second.

```
java -jar the-easy-animator.jar -in smalldemo.txt -speed 50 -view visual
```

* use buildings.txt for the animation file, and create an interactive view to show the animation at
  a speed of 40 tick per second.

```   
java -jar the-easy-animator.jar -in buildings.txt -view edit -speed 40
```

---

## Design

The application was built using the classic Model-View-Controller architecture.

---

### The Model

---

![Class Diagram of the Model](https://raw.githubusercontent.com/tianyhe/picgo/main/img/202208152342245.png)

The animation is described in terms of what should happen when according to a timeline when the animation plays.

To represents the animation, the model uses a list of `AnimatedShape` objects to store the shapes
that should be animated. In order to keep track of the animator and have the ability to rewind the
animation, the `AnimatedShape` contains a list of `Keyframe` objects and a list of `Motion` objects.

The `Keyframe` objects represent the state of the shape at a specific time, mostly at the time when
there is a change in the shape's state. It is responsible for referencing the shape's state at a
specific time of the animation.

The `Motion` objects represent the change in the state of the shape over time and are used to create
different possible animation effects to the shape, such as moving, changing color, and scaling.

The `Motion` objects can be constructed using two keyframes, the first keyframe that records the
state of the shape before the changes happen, and the second keyframe that records the state of the
shape after the changes.

The `Motion` objects can also be constructed using a list of data that represents the state of the
shape before the changes happen, and a list of data that represents the state of the shape after the
changes.

In order to keep track of the changes, both the `Keyframe` and `Motion` objects have a `tick` field
that represents the time at which the change happens/ends.

The model plays the animation by iterating through the list of `Motion` objects in
the `AnimatedShape`
and apply the changes of state to each specific tick. Since each kind of animation can be thought of
as
changing some attribute of the shape with time while keeping some other attribute of the shape
constant.

The changes between each tick are calculated using linear interpolation and stored as a Map where
the key is the tick, and the value is the new state of the shape in the `Motion` objects of
the `AnimatedShape`.

![Class Diagrams for the Shape](https://raw.githubusercontent.com/tianyhe/picgo/main/img/202208152343749.png)

All types of shapes that are used in the animation are represented by the `IShape` interface and extends an abstract class `AbstractShape`.
The `AbstractShape` class contains fields and methods that are common to all shapes.


---

### The View

---

![Class Diagram for the View](https://raw.githubusercontent.com/tianyhe/picgo/main/img/202208152344207.png)

The view is responsible for displaying the animation. The program offers a number of options to
display the animation:

#### Declarative animation - `TextView`

In this view, the animation is described in terms of text format. This view can show or store a
textual description of the animation and works with a variety of output sources. Users can save the
description to a file or print the description to the console.

#### SVG animation - `SvgView`

In this view, the animation will be produced with a textual description that is compliant with the
popular SVG file format. The SVG file format is an XML-based format that can be used to describe
images and animations.

Users can store the output to a .svg file and render the animation in a web browser.

#### Visual animation - `VisualView`

In this view, the animation is displayed in a window using features of the Java Swing library. It
simply
plays the animation by updating the state of the shapes at each tick and repaints the panel. This
view offers little interaction with the user besides the ability to quit the program with a "quit"
button.

#### Interactive animation - `InteractiveView`

The interactive view is built on top of the visual view with the addition of a control panel that
allows the user to manipulate the animation.

The control panel has a slider to rewind the animation to any specific tick.
The control panel has buttons for playing, pausing, resetting, enabling and disabling looping for the
animation.
The control panel also offers a text field to enter and set the speed of the animation without
interrupting the playing of the animation.

---

### The Controller

---

![Class Diagram for the relationship between the Model, the View, and the Controller](https://raw.githubusercontent.com/tianyhe/picgo/main/img/202208152345705.png)

The controller is responsible for mediating the interaction between the model and the view.

The controller implements the `ActionListener` interface and is responsible for capturing Action Events
from the view.

When an action is performed by the user through the view, the controller is able to respond to the
action by passing the action to the model or making specific changes to the animation.

#### Available actions that delegate to the controller

* Start - starts the animation
* Pause - pauses the animation
* Reset - resets the animation to the first tick
* Enable Loop - enables looping for the animation
* Disable Loop - disables looping for the animation
* Set Speed - sets the speed of the animation
* Rewind - rewinds the animation to a selected tick
* Save - saves the animation to a file
* Load - loads the animation from a file

The controller is also responsible for updating the state of the animation using a timer to alert
the model to apply motions and the view to repaint the panel.

---

### Demo of the Program

---

![Demo of the Program](https://raw.githubusercontent.com/tianyhe/picgo/main/img/202208152351478.jpg)