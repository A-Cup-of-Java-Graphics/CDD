# CDD

Official code of the Cat Drug Dealer game written by ElectraFossil, Car352Tearful, and CoolBudy1.

# Render Engine

The render engine was built using LWJGL 3.3, which contains libraries such as OpenGL, and OpenCV. 

The render engine is designed for a 2D game, but uses 3D vectorial positions to simulate depth.

There are 2 sections to the Render Engine. 
The first is the actual scene, this is rendered first.
The second section is the GUI and other non-physical interactables, they also have a depth value to keep track of display order, but the code automatically clears the GL_DEPTH_BUFFER_BIT at the beginning of the second render pass, which means that the depth values of the GUI and the actual scene objects may have similar values, but will never truly interact with eachother.

# Collision Engine

The collision engine takes an input from a color coded map and uses OpenCV to create the desired colliders. (Written by CoolBudy1, Implemented by Car352Tearful)

# Physics Engine

# Sound Engine
