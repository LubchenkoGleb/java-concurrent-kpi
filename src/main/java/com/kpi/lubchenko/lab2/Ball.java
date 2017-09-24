package com.kpi.lubchenko.lab2;

import lombok.Data;

import java.awt.*;
import java.awt.geom.Ellipse2D;

@Data
class Ball {
    private int speed = 10;
    private boolean isInPocket;
    private Color color;
    private BallCanvas canvas;
    private int size = 20;
    private int x = 0;
    private int y = 0;
    private int dx = 2;
    private int dy = 2;


    public Ball(BallCanvas c, Color color) {
        this.color = color;
        this.canvas = c;

        x = 0;
        y = 0;
    }

    public void draw(Graphics2D g2, Integer number) {
        Ellipse2D.Double ball = new Ellipse2D.Double(x, y, size, size);
        g2.setPaint(color);
        g2.fill(ball);
        g2.drawString(number.toString(), x, y);
        checkBallInThePocket(x, y);
    }

    public void move() {
        x += dx;
        y += dy;
        if (x < 0) {
            x = 0;
            dx = -dx;
        }
        if (x + size >= this.canvas.getWidth()) {
            x = this.canvas.getWidth() - size;
            dx = -dx;
        }
        if (y < 0) {
            y = 0;
            dy = -dy;
        }
        if (y + size >= this.canvas.getHeight()) {
            y = this.canvas.getHeight() - size;
            dy = -dy;
        }
        this.canvas.repaint();
    }

    private void checkBallInThePocket(int x, int y) {
        boolean leftX = x + size < canvas.pocketRadius;
        boolean rightX = x >  canvas.getWidth() - canvas.pocketRadius;

        boolean topY = y + size < canvas.pocketRadius;
        boolean downY = y > canvas.getHeight() - canvas.pocketRadius;

        if((leftX && topY) || (rightX && topY) || (rightX && downY) || (leftX && downY)) {
            isInPocket = true;
        }
    }
}