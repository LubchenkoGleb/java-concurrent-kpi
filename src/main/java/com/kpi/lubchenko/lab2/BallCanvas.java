package com.kpi.lubchenko.lab2;

import lombok.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

@Data
public class BallCanvas extends JPanel {

    private ArrayList<Ball> balls;
    private List<Ellipse2D.Double> pockets;//массив с лзами
    public int pocketRadius = 10;//массив размера лузы

    public BallCanvas() {
        balls = new ArrayList<>();
    }

    public void add(Ball b) {
        this.balls.add(b);
    }

    public List<Ellipse2D.Double> initPockets() {//иниц лузы создала лузы и дабавила в массив с лузами(ниже)
        ArrayList<Ellipse2D.Double> pockets = new ArrayList<>();

        Ellipse2D.Double leftDownCorner = new Ellipse2D.Double(
                -pocketRadius, -pocketRadius, pocketRadius * 2, pocketRadius * 2);
        pockets.add(leftDownCorner);

        Ellipse2D.Double rightDownCorner = new Ellipse2D.Double(
                this.getWidth() - pocketRadius, -pocketRadius, pocketRadius * 2, pocketRadius * 2);
        pockets.add(rightDownCorner);

        Ellipse2D.Double leftTopCorner = new Ellipse2D.Double(
                -pocketRadius, this.getHeight() - pocketRadius, pocketRadius * 2, pocketRadius * 2);
        pockets.add(leftTopCorner);

        Ellipse2D.Double rightTopCorner = new Ellipse2D.Double(
                this.getWidth() - pocketRadius, this.getHeight() - pocketRadius, pocketRadius * 2, pocketRadius * 2);
        pockets.add(rightTopCorner);

        return pockets;
    }


    @Override//метод переопределяется из базового класса и автомаически вызывается для перерисовки компонентов
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

//        pockets = initPockets();
//        pockets.forEach(g2::fill);//прохожу по всем лузам и зарисову их

        for (int i = 0; i < balls.size(); i++) {//прохожу по всем шарикам
            Ball b = balls.get(i);
            b.draw(g2, i);
            if (b.isInPocket()) {//если шарик в лузе удаляешь из массива с шариками
                balls.remove(i);
            }
        }
    }


}