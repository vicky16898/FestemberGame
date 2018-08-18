package com.example.arno.festemberball_game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class BackGround {
    private GamePanel gamePanel;
    private int x = 0;
    private int y = 0;
    private int dx = 0;
    private Bitmap bm;

    public BackGround(Bitmap bm, GamePanel gamePanel) {

        this.bm = bm;
        this.gamePanel = gamePanel;
        dx = GamePanel.moveSpeed;
    }


    public void draw(Canvas canvas) {

        canvas.drawBitmap(bm, x, y, null);
        if (y < 0) {
            canvas.drawBitmap(bm, x, y + gamePanel.getHeight(), null);
        }


    }

    public void update() {
        Log.d("from back_ground class", "true");
        y = y + dx;
        if (y < (-gamePanel.getHeight())) {
            y = 0;
        }


    }


}
