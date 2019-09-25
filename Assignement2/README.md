# TDT4250-A2
Second assignment in TDT4250 Advanced Software Design

About
------
The goal of assignment 2 is to get experience with OSGi and how bundles and components contribute to modularity and extensibility. 

Setup
------
The root project and folder was created using the BndTools' Bnd OSGi Workspace wizard, and each bundle with the Bnd OSGi Project wizard. Which bundle template to use depends on the specific bundle, there are api, component and servlet bundles in this project.

To run the project, you should open the launch.bndrun file in the servlet bundle, make sure it resolves and use Run OSGi. 
To test it you can do a conversion using: http://localhost:8080/unit/kmtomiles?q=54 in a browser.

Shell commands
------


Structure
------
The project consists of the tdt4250.unit.api, tdt4250.unit.util, tdt4250.unit.kmtomiles, tdt4250.unit.poundstokg, tdt4250.unit.servlet and tdt4250.dict3.rest and tdt4250.unit.gogo bundles. 
It's modular, being split into bundles with minimal dependencies, and flexible, since new bundles can contribute additional dictionaries as components.
