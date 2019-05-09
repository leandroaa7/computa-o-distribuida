/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package time;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 *
 * @author mackleaps
 */
public class CalcTime {

    public static int calcTimeToMin(String time) {
        String timeSplit[] = time.split(":");
        int h = Integer.parseInt(timeSplit[0]);
        int minutes = Integer.parseInt(timeSplit[1].trim());
        h = h * 60;
        return h + minutes;
    }

    public static String calcMinToTime(int min) {
        Duration d = Duration.ofMinutes(min);
        LocalTime time = LocalTime.MIN.plus(d);
        String output = time.toString();
        return output;
    }

    public static float media(ArrayList<Integer> minutes) {
        int soma = 0;
        for (int i = 0; i < minutes.size(); i++) {
            soma += minutes.get(i);
        }
        float media = soma / minutes.size();
        return media;
    }

    public static int diff(int m1, int m2) {
        return m1 - m2;
    }

    public static void main(String[] args) {
        String s = "04:00";
        int minutes = calcTimeToMin(s);
        //System.out.println(20-40);

    }

}
