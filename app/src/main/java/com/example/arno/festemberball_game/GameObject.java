package com.example.arno.festemberball_game;
//not going to be instantiated anywhere;
//all game objects lie here;

import android.graphics.Rect;

public abstract class GameObject {
    protected int x;
    protected int y;
    protected int dx;
    protected int dy;
    protected double radius;

    public void setX(int x){
        this.x = x;

    }

    public void setY(int y){
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }




}

