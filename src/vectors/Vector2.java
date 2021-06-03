
package vectors;

public class Vector2{
 
 public float x;
 public float y;

 public Vector2(float x, float y){
 	this.x = x;
 	this.y = y;
 }
 //this is used to get the distance between two point in 2D space
 public static float distance(Vector2 a,Vector2 b){ return (float)Math.sqrt(Math.pow(a.x - b.x,2) + Math.pow(a.y - a.y,2));}
 //this is used to multiply two vector2 
 public static Vector2 multiply(Vector2 a,Vector2 b){return new Vector2(a.x * b.x,a.y * b.y);}
 //this is used to sum two vector2
 public static Vector2 sum(Vector2 a,Vector2 b){return new Vector2(a.x + b.x,a.y + b.y);}
 //this is used to devide two vector2
 public static Vector2 devide(Vector2 a,Vector2 b){return new Vector2(a.x / b.x,a.y / b.y);}
}