package edu.utep.cs.cs4381.pricewatcher;

import java.util.Random;

public class PriceFinder {

    public PriceFinder(){
    }
    public static double getPrice(String url){
        Random rand = new Random();
        double min = 100.00;
        double max = 250.00;
        double difference = max - min;
        double randomPrice = min + (difference * rand.nextDouble());
        return randomPrice;
    }
}
