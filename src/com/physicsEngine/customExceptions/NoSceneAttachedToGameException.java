package com.physicsEngine.customExceptions;

//this exception is called whenever we create a game & leave the scenes list empty

public class NoSceneAttachedToGameException extends Exception{
 public NoSceneAttachedToGameException(){
     super("There Is No Scene Attached To The Game Scenes List");
 }
}
