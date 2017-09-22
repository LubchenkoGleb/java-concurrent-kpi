package com.kpi.lubchenko.lab2;

import javax.swing.*;

public class Bounce {
    public static void main(String[] args) {
        BounceFrame frame = new BounceFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//настроила чтоб когда нажмаешь на крестик  закрывалось
        frame.setVisible(true);
        System.out.println("Thread name = " + Thread.currentThread().getName());// вывела имя текущего потока
    }
}