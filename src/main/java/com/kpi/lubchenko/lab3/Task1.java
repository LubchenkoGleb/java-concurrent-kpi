package com.kpi.lubchenko.lab3;

public class Task1 {

    public static void main(String[] args) throws InterruptedException {
        String text = "We have the java learning course!";
        String[] words = text.split(" ");
        for (String word : words) {
            System.out.println(word);
            Thread.sleep(1000);
        }
    }
}
