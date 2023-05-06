package com.cotato.homecook.utils;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class DummyDataGenerator {
    public String generateSellerName() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 5; i++) {
            char letter = (char) (random.nextInt(26) + 'a');
            sb.append(letter);
        }
        sb.append("판매자");

        return sb.toString();
    }

    public String generateShopName() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 5; i++) {
            char letter = (char) (random.nextInt(26) + 'a');
            sb.append(letter);
        }
        sb.append("상점");

        return sb.toString();
    }

    public String generateRandomString(String postfix) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 5; i++) {
            char letter = (char) (random.nextInt(26) + 'a');
            sb.append(letter);
        }
        sb.append(postfix);

        return sb.toString();
    }


    public String generateCategory() {
        String[] foodTypes = {"한식", "중식", "양식", "일식", "기타"};
        int randomIndex = ThreadLocalRandom.current().nextInt(foodTypes.length);
        return foodTypes[randomIndex];
    }

    public double generateCoordinate(String parameter) {
        if (parameter.equals("latitude")) {
            double random = ThreadLocalRandom.current().nextDouble(37.594555, 37.609908);
            return BigDecimal.valueOf(random)
                    .setScale(6, RoundingMode.HALF_UP)
                    .doubleValue();
        } else if (parameter.equals("longitude")) {
            double random = ThreadLocalRandom.current().nextDouble(126.915507, 126.937341);
            return BigDecimal.valueOf(random)
                    .setScale(6, RoundingMode.HALF_UP)
                    .doubleValue();
        } else {
            throw new IllegalArgumentException("Invalid parameter");
        }
    }

    public int generateRandomNumber(int x, int y) {
        Random random = new Random();
        int randomNumber = random.nextInt((y - x) + 1) + x;
        return randomNumber;
    }
    public long generateRandomPrice() {
        return ThreadLocalRandom.current().nextLong(10000L, 30001L);
    }
}
