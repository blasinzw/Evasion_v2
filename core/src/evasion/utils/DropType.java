package evasion.utils;

import java.util.Random;

/**
 * Created by Zander on 3/30/2015.
 */
public enum DropType {
    POWER_UP,
    MONEY;
    private static final Random RANDOM = new Random();

    //all add up to a hundred
    private static final int PROBABILITY_OF_POWER_UP = 50;
    private static final int PROBABILITY_OF_MONEY = 50;


    public static DropType getRandomType() {
        int prob = RANDOM.nextInt(100);

        if (prob < PROBABILITY_OF_POWER_UP) {
            return MONEY; //POWER_UP; TESTING
        }else if (prob < PROBABILITY_OF_POWER_UP + PROBABILITY_OF_MONEY) {
            return MONEY;
        }else {
            return MONEY;
        }
    }
}
