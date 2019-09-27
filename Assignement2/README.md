# TDT4250-A2
Second assignment in TDT4250 Advanced Software Design

The goal of assignment 2 is to get experience with OSGi and how bundles and components contribute to modularity and extensibility. 

Setup
------
The root project and folder was created using the BndTools' Bnd OSGi Workspace wizard, and each bundle with the Bnd OSGi Project wizard. Which bundle template to use depends on the specific bundle, there are api, component and servlet bundles in this project.

To run the project, you should open the launch.bndrun file in the servlet bundle, make sure it resolves and use Run OSGi. 
To test it you can do a convertion using http://localhost:8080/unit/{conversion}?q={number} in a browser (e.g. localhost:8080/unit/kmtomiles?q=5).

Gogo shell commands
------
* `list` - Lists all active conversion components
* `convert {conversion name} {number to convert}` - converts the given number (e.g. `convert kmtomiles 5`)
* `add {conversion name} {conversion equation}` - Adds a new conversion component (e.g. `add kgtopounds "x*2.205"`)
* `remove {conversion name}` - Removes manually added conversion component (e.g. `remove kgtopounds`)

Structure
------
It's modular, being split into bundles with minimal dependencies, and flexible, since new bundles can contribute additional dictionaries as components.

The bundles the project consists of:
* tdt4250.unit.api 
* tdt4250.unit.util 
* tdt4250.unit.servlet
* tdt4250.unit.rest
* tdt4250.unit.gogo 
* tdt4250.unit.kmtomiles
* tdt4250.unit.poundstokg


