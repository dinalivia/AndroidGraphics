package com.bennyplo.graphics2d;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Shader;
import android.view.View;

/**
 * Created by benlo on 09/05/2018.
 */

public class MyView extends View {
    private Paint redPaint, redFillPaint, bluePaint, blackPaint, gradientPaint;
    private Path mylines;
    private LinearGradient linear;
    private Point[] points, points2;

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
        mylines.lineTo(150,400);
        mylines.lineTo(180,340);
        mylines.lineTo(240,420);
//        mylines.lineTo(500,100); // 300, 200
        mylines.lineTo(300,200); // 300, 200

        mylines.close();


        // ---- Linear Gradient ---- //

        linear = new LinearGradient(50,300,300,200, Color.BLUE, Color.RED, Shader.TileMode.MIRROR);
        gradientPaint = new Paint();
        gradientPaint.setStyle(Paint.Style.FILL);
        gradientPaint.setShader(linear);

        // ---- Affine transformations ---- //

        points = new Point[5];
        points[0] = new Point(50,300);
        points[1] = new Point(150,400);
        points[2] = new Point(180,340);
        points[3] = new Point(240,420);
        points[4] = new Point(300,200);

        // Assignment polygon
        points2 = new Point[4];
        points2[0] = new Point(500,300);
        points2[1] = new Point(500,400);
        points2[2] = new Point(600,400);
        points2[3] = new Point(600,300);

    }

    protected Point FindCentre(Point[] points) {
        Point centre = new Point(0,0);
        for (int i = 0; i < points.length; i ++) {
            centre.x += points[i].x;
            centre.y += points[i].y;
        }
        centre.x = centre.x / (points.length + 1);
        centre.y = centre.y / (points.length + 1);
        return centre;
    }

    protected void UpdatePath(Point[] newpoints) {
        mylines.reset();
        mylines.moveTo(newpoints[0].x, newpoints[0].y);
        for (int i = 1; i < newpoints.length; i++)
            mylines.lineTo(newpoints[i].x, newpoints[i].y);
        mylines.close();
    }

    protected  Point[] AffineTransformation (Point[] vertices, double[][] matrix) {
        Point[] result = new Point[vertices.length];
        for (int i = 0; i < vertices.length; i++) {
            int t = (int) (matrix[0][0] * vertices[i].x + matrix[0][1] * vertices[i].y + matrix[0][2]);
            int u = (int) (matrix[1][0] * vertices[i].x + matrix[1][1] * vertices[i].y + matrix[1][2]);
            result[i] = new Point(t, u);
        }
        return result;
    }

    protected Point[] translate (Point[] input, int px, int py) {
        double[][] matrix = new double[3][3];
        matrix[0][0] = 1; matrix[0][1] = 0; matrix[0][2] = px;
        matrix[1][0] = 0; matrix[1][1] = 1; matrix[1][2] = py;
        matrix[2][0] = 0; matrix[2][1] = 0; matrix[2][2] = 1;
        return AffineTransformation(input, matrix);
    }

    protected Point[] rotate (Point[] input, double theta) {
        double[][] matrix = new double[3][3];
        matrix[0][0] = Math.cos(theta); matrix[0][1] = - Math.sin(theta); matrix[0][2] = 0;
        matrix[1][0] = Math.sin(theta); matrix[1][1] = Math.cos(theta); matrix[1][2] = 0;
        matrix[2][0] = 0; matrix[2][1] = 0; matrix[2][2] = 1;
        return AffineTransformation(input, matrix);
    }

    protected Point[] scale (Point[] input, double a, double b) {
        double[][] matrix = new double[3][3];
        matrix[0][0] = a; matrix[0][1] = 0; matrix[0][2] = 0;
        matrix[1][0] = 0; matrix[1][1] = b; matrix[1][2] = 0;
        matrix[2][0] = 0; matrix[2][1] = 0; matrix[2][2] = 1;
        return AffineTransformation(input, matrix);
    }

    protected Point[] shear (Point[] input, double c, double d) {
        double[][] matrix = new double[3][3];
        matrix[0][0] = 1; matrix[0][1] = c; matrix[0][2] = 0;
        matrix[1][0] = d; matrix[1][1] = 1; matrix[1][2] = 0;
        matrix[2][0] = 0; matrix[2][1] = 0; matrix[2][2] = 1;
        return AffineTransformation(input, matrix);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Add your drawing code here
//        canvas.drawRect(10,30,200,200,redPaint);
//        canvas.drawPath(mylines, redFillPaint);
//        canvas.drawPath(mylines, blackPaint);

        // Original draw with gradientPaint
        //canvas.drawPath(mylines, blackPaint);

        /*
        // Translation
        Point[] newpoints = translate(points,20,40);
        UpdatePath(newpoints);
        canvas.drawPath(mylines,blackPaint);

        // Rotation
        Point centre = FindCentre(points);
        newpoints = translate(points, -centre.x, -centre.y);
        newpoints = rotate(newpoints, (180 * Math.PI) / 180);
        newpoints = translate(newpoints, centre.x, centre.y);
        UpdatePath(newpoints);
        canvas.drawPath(mylines, bluePaint);


        // Scaling
        Point centre1 = FindCentre(points);
        Point[] newpoints = scale(points, 1.5, 1.5);
        Point centre2 = FindCentre(newpoints);
        newpoints = translate(newpoints, centre1.x - centre2.x, centre1.y - centre2.y);
        UpdatePath(newpoints);
        canvas.drawPath(mylines, blackPaint);

        // Shear
        Point centre1 = FindCentre(points);
        Point[] newpoints = shear(points, 0.5, 0.5);
        Point centre2 = FindCentre(newpoints);
        newpoints = translate(newpoints, centre1.x - centre2.x, centre1.y - centre2.y);
        UpdatePath(newpoints);
        canvas.drawPath(mylines, blackPaint);
        */

        // ---- ASSIGNMENT (Affine transformation)---- //

        // -- Part 1 -- //

        // Shear 2 in x
        Point centre1 = FindCentre(points);
        Point[] newpoints = shear(points, 2, 0);
        Point centre2 = FindCentre(newpoints);
        newpoints = translate(newpoints, centre1.x - centre2.x, centre1.y - centre2.y);

        // Scale 0.5 in x and 3 in y
        centre1 = FindCentre(newpoints);
        newpoints = scale(newpoints, 0.5, 3);
        centre2 = FindCentre(newpoints);
        newpoints = translate(newpoints, centre1.x - centre2.x, centre1.y - centre2.y);

        // Rotate by 45 degrees
        Point centre = FindCentre(newpoints);
        newpoints = translate(newpoints, -centre.x, -centre.y);
        newpoints = rotate(newpoints, (45 * Math.PI) / 180);
        newpoints = translate(newpoints, centre.x, centre.y);

        // Translate by 550 in x
        newpoints = translate(newpoints,20,40);

        UpdatePath(newpoints);
        canvas.drawPath(mylines, gradientPaint);
        

        // -- Part 2 -- //
        /*
        Point centre1 = FindCentre(points2);
        Point[] newpoints = scale(points2, 0.5, 0.5);
        Point centre2 = FindCentre(newpoints);
        ///newpoints = translate(newpoints, centre1.x - centre2.x, centre1.y - centre2.y);
        UpdatePath(newpoints);
        canvas.drawPath(mylines, blackPaint);

        // 2. Calculate the centre of the polygon
        Point centre = FindCentre(points2);

        //3. Before rotating the polygon, minus the centre for each point to obtain the updated coordinates.
        Point[] newpoints2 = translate(points2, -centre.x, -centre.y);

        //4. Rotate your polygon by 45 degrees (Note: you need to convert to radian angle to perform the rotation).
        newpoints2 = rotate(newpoints2, (45 * Math.PI) / 180);

        //5. Add the centre back to each point.
        newpoints2 = translate(newpoints2, centre.x, centre.y);


        //newpoints2 = scale(newpoints2, 0.4, 0.4);



        //6. draw canvas
        UpdatePath(newpoints2);
        canvas.drawPath(mylines, gradientPaint);
        */

    }
}
