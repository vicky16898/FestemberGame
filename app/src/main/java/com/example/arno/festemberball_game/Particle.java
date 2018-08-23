package com.example.arno.festemberball_game;

import android.util.Log;

import java.util.Random;

//this is where a particle is created;
public class Particle {
    public int xdistFromOrigin = 0;
    public int ydistFromOrigin = 0;
    private double direction;
    private double directionCosine;
    private double directionSine;
    public int color;
    public int x;
    public int y;
    private int initX;
    private int initY;

    public Particle(int x, int y) {
        init(x, y);
        this.direction = 2 * Math.PI * new Random().nextInt(NO_OF_DIRECTION) / NO_OF_DIRECTION;
        if (direction > 2.96706 || direction < 0.174533) {

            direction = (2.792527) * (new Random().nextDouble());
        }


        this.directionCosine = Math.cos(direction);
        this.directionSine = Math.sin(direction);
        this.color = new Random().nextInt(3);

    }

    public void init(int x, int y) {
        xdistFromOrigin = 0;
        ydistFromOrigin = 0;
        this.initX = this.x = x;
        this.initY = this.y = y;
    }

    public synchronized void move() {

        xdistFromOrigin += 2;
        ydistFromOrigin += 10;


        x = (int) (initX + xdistFromOrigin * directionCosine);
        y = (int) (initY + ydistFromOrigin * directionSine);

    }

    private final static int NO_OF_DIRECTION = 500;

}
