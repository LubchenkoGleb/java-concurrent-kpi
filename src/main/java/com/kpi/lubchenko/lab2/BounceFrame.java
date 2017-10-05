package com.kpi.lubchenko.lab2;

import javax.swing.*;
import java.awt.*;

public class BounceFrame extends JFrame {
    private BallCanvas canvas;
    private static final int WIDTH = 450;
    private static final int HEIGHT = 350;

    public BounceFrame() {
        System.out.println("In Frame Thread name = " + Thread.currentThread().getName());

        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Bounce program");
        this.canvas = new BallCanvas();

        Container content = this.getContentPane();
        content.add(this.canvas, BorderLayout.CENTER);

        content.add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JButton createBallsButton() {
        JButton addBall = new JButton("Add 100x blue balls");
        addBall.addActionListener(e -> {

            for (int i = 0; i < 100; i++) {
                Ball b = new Ball(canvas, Color.BLUE);
                canvas.add(b);

                BallThread thread = new BallThread(b);
                thread.setPriority(Thread.MIN_PRIORITY);
                thread.start();
                System.out.println("Thread name = " + thread.getName());
            }

            Ball b = new Ball(canvas, Color.RED);
            canvas.add(b);

            BallThread thread = new BallThread(b);
            thread.setPriority(Thread.MAX_PRIORITY);
            thread.start();
            System.out.println("Thread name = " + thread.getName());
        });
        return addBall;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray);

        JButton addBlueButton = createBallsButton();
        buttonPanel.add(addBlueButton);

        JButton buttonStop = new JButton("Stop");
        buttonStop.addActionListener(e -> System.exit(0));
        buttonPanel.add(buttonStop);

        return buttonPanel;
    }
}
