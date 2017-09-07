package com.kpi.lubchenko.lab1;

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

        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(createButtonPanel());
        panel.add(createEditBallPanel());

        content.add(panel, BorderLayout.SOUTH);
    }

    private JButton createBallButton(Color color, Integer priority) {
        JButton addBlueBall = new JButton("Add Blue Ball");
        addBlueBall.addActionListener(e -> {

            Ball b = new Ball(canvas, color);
            canvas.add(b);

            BallThread thread = new BallThread(b);
            thread.setPriority(priority);
            thread.start();
            System.out.println("Thread name = " + thread.getName());
        });
        return addBlueBall;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray);

        JButton addRedButton = createBallButton(Color.RED, Thread.MAX_PRIORITY);
        buttonPanel.add(addRedButton);

        JButton addBlueButton = createBallButton(Color.BLUE, Thread.MIN_PRIORITY);
        buttonPanel.add(addBlueButton);

        JButton buttonStop = new JButton("Stop");
        buttonStop.addActionListener(e -> System.exit(0));
        buttonPanel.add(buttonStop);

        return buttonPanel;
    }

    private JPanel createEditBallPanel() {
        JPanel editPanel = new JPanel();

        JLabel numberLabel = new JLabel("number:");
        editPanel.add(numberLabel);
        JTextField numberTextField = new JTextField();
        numberTextField.setPreferredSize(new Dimension(40, 20));
        editPanel.add(numberTextField);

        JLabel sizeLable = new JLabel("size:");
        editPanel.add(sizeLable);
        JTextField sizeTextField = new JTextField();
        sizeTextField.setPreferredSize(new Dimension(40, 20));
        editPanel.add(sizeTextField);

        JLabel speedLable = new JLabel("speed:");
        editPanel.add(speedLable);
        JTextField speedTextField = new JTextField();
        speedTextField.setPreferredSize(new Dimension(40, 20));
        editPanel.add(speedTextField);

        JButton apply = createApplyButton(numberTextField, speedTextField, sizeTextField);
        editPanel.add(apply);

        return editPanel;
    }

    JButton createApplyButton(JTextField numberField , JTextField speedField , JTextField sizeField) {
        JButton applyButton = new JButton("Apply");
        applyButton.addActionListener(e -> {
            try {
                int number = Integer.parseInt(numberField.getText());
                int speed = Integer.parseInt(speedField.getText());
                int size = Integer.parseInt(sizeField.getText());

                Ball ball = canvas.getBalls().get(number);
                ball.setSpeed(speed);
                ball.setSize(size);

            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(null, "cant be parsed");
            } catch (IndexOutOfBoundsException exception) {
                JOptionPane.showMessageDialog(null, "Ball with this number not found");
            }
        });
        return applyButton;
    }
}
