package com.kpi.lubchenko.lab1;

import lombok.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

@Data
public class BallCanvas extends JPanel {

    private ArrayList<Ball> balls;
    private List<Ellipse2D.Double> pockets;
    public int pocketRadius = 40;

    public BallCanvas() {
        balls = new ArrayList<>();
    }

    public void add(Ball b) {
        this.balls.add(b);
    }

    public List<Ellipse2D.Double> initPockets() {
        ArrayList<Ellipse2D.Double> pockets = new ArrayList<>();

        Ellipse2D.Double leftDownCorner = new Ellipse2D.Double(-40, -40, 80, 80);
        pockets.add(leftDownCorner);

        Ellipse2D.Double rightDownCorner = new Ellipse2D.Double(this.getWidth() - 40, -40, 80, 80);
        pockets.add(rightDownCorner);

        Ellipse2D.Double leftTopCorner = new Ellipse2D.Double(-40, this.getHeight() - 40, 80, 80);
        pockets.add(leftTopCorner);

        Ellipse2D.Double rightTopCorner = new Ellipse2D.Double(this.getWidth() - 40, this.getHeight() - 40, 80, 80);
        pockets.add(rightTopCorner);

        return pockets;
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        pockets = initPockets();
        pockets.forEach(g2::fill);

        for (int i = 0; i < balls.size(); i++) {
            Ball b = balls.get(i);
            b.draw(g2, i);
            if(b.isInPocket()) {
                balls.remove(i);
            }
        }
    }



}