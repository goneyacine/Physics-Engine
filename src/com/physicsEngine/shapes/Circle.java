
package com.physicsEngine.shapes;

public class Circle extends Shape{
	public float radius;
    
    public Circle(float radius){
    	this.radius = radius;

    	height = radius * 2;
    	width = radius * 2;

    	name = "circle";
    }

}