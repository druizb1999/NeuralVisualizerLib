package com.configuration;

import java.security.SecureRandom;

//Randomizer with secure random for the random kernel position
public class Randomizer {

    public double randomBetween(double high){
    	SecureRandom random = new SecureRandom();
        return Math.floor(random.nextDouble()*(high*2+1)) - high;
    }
}
