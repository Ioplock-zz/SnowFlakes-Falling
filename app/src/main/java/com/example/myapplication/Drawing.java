package com.example.myapplication;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import java.util.Random;

class Snowflake {
    public Snowflake() {
        Random r = new Random();
        this.x = r.nextInt(Drawing.viewW);
        do {
            this.velocity = r.nextFloat() * 4;
        }while (this.velocity < 0.4);
        radios = new Paint(Drawing.p);
        do {
            radios.setTextSize(r.nextInt(250));
        }while (radios.getTextSize() < 20);
        rorate = r.nextInt(359);
        radios.setTextAlign(Paint.Align.CENTER);
        radios.setColor(Color.WHITE);

        // 1) сгенерировать случайные координаты и скорость
        // в пределах от 0 до 1
    }

    float x, y, velocity, rorate;
    Paint radios;
    public void fall() {
        y += velocity;
        if(y > Drawing.viewH + this.radios.getTextSize()) this.y = - this.radios.getTextSize();
        // 2) предусмотреть перерождение снежинки
    }
}

public class Drawing extends View {
    Snowflake[] snowflakes;
    static Paint p = new Paint();
    Resources res = getResources();
    static public int viewH = 2000;
    static public int viewW = 2000;

    public Drawing(Context context) {
        super(context);
    }

    public Drawing(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        snowflakes = new Snowflake[100];
        // падение снежинок при касании
        for (int i = 0; i < snowflakes.length; i++) {
            snowflakes[i] = new Snowflake();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#CCD7EA"));
        for (int i = 0; i < snowflakes.length; i++) {
            canvas.drawText("*", snowflakes[i].x, snowflakes[i].y, snowflakes[i].radios);
        }
        // 3) нарисовать всё снежинки
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        viewH = h;
        viewW = w;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        for (int i = 0; i < snowflakes.length; i++) {
            snowflakes[i].fall();
        }
        invalidate();
        return true;
    }
}
