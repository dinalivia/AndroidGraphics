package com.bennyplo.graphics2d;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.view.View;

/**
 * Created by benlo on 09/05/2018.
 */

public class MyView extends View {
    private Paint redPaint, redFillPaint, bluePaint, blackPaint, gradientPaint;
    private Path mylines;
    private LinearGradient linear;

    public MyView(Context context) {
        super(context, null);
        //Add your initialisation code here

        // ---- Paints ---- //

        redPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        redPaint.setStyle(Paint.Style.STROKE);//stroke only no fill
        redPaint.setColor(0xffff0000);//color red
        redPaint.setStrokeWidth(5);//set the line stroke width to 5

        redFillPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        redFillPaint.setStyle(Paint.Style.FILL);//fill
        redFillPaint.setARGB(255,255,0,0);
        redFillPaint.setStrokeWidth(5);//set the line stroke width to 5

        bluePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        bluePaint.setStyle(Paint.Style.STROKE);//stroke only no fill
        bluePaint.setColor(Color.BLUE);//color blue
        bluePaint.setStrokeWidth(5);//set the line stroke width to 5

        blackPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        blackPaint.setStyle(Paint.Style.STROKE);//stroke only no fill
        blackPaint.setColor(Color.BLACK);//color black
        blackPaint.setStrokeWidth(5);//set the line stroke width to 5

        // ---- Paths ---- //

        mylines = new Path();
        //mylines.moveTo(100,100); // 50, 300
        mylines.moveTo(50,300); // 50, 300
        mylines.lineTo(200,300);
        mylines.lineTo(300,150);
        mylines.lineTo(400,300);
        //mylines.lineTo(500,100); // 300, 200
        mylines.lineTo(300,200); // 300, 200


        mylines.close();


        // ---- Linear Gradient ---- //

        linear = new LinearGradient(100,100,1000,1000, Color.BLUE, Color.RED, Shader.TileMode.MIRROR);
        gradientPaint = new Paint();
        gradientPaint.setStyle(Paint.Style.FILL);
        gradientPaint.setShader(linear);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Add your drawing code here
//        canvas.drawRect(10,30,200,200,redPaint);
//        canvas.drawPath(mylines, redFillPaint);
        canvas.drawPath(mylines, blackPaint);
        canvas.drawPath(mylines, gradientPaint);
    }
}
