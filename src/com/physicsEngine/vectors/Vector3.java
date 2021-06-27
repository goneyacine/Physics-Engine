
package com.physicsEngine.vectors;

public class Vector3 {
	public float x;
	public float y;
	public float z;

	public Vector3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
//this is used to get the distance between two point in 2D space
	public static float distance(Vector3 a, Vector3 b) { return (float)Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2) + Math.pow(a.z - b.z, 2 ));}
//this is used to multiply two vector2
	public static Vector3 multiply(Vector3 a, Vector3 b) {return new Vector3(a.x * b.x, a.y * b.y, a.z * b.z);}
//this is used to sum two vector2
	public static Vector3 sum(Vector3 a, Vector3 b) {return new Vector3(a.x + b.x, a.y + b.y, a.z + b.z);}
//this is used to devide two vector2
	public static Vector3 devide(Vector3 a, Vector3 b) {return new Vector3(a.x / b.x, a.y / b.y, a.z / b.z);}
//this is used to suptract two vector2
	public static Vector3 subtract(Vector3 a, Vector3 b) {return new Vector3(a.x - b.x, a.y - b.y, a.z - b.z);}

}