package org.example;

import java.util.HashMap;
import java.util.OptionalInt;

public class Wednesday implements Runnable, Faction {
    boolean isNight = false;
    HashMap<String, Integer> robots = new HashMap<>();
    Factory factory;

    public void setNight(boolean isNight) {
        this.isNight = isNight;
    }

    public Wednesday(Factory factory) {
        this.factory = factory;
    }

    public void takeRobotPart(Factory factory) {
        RobotPart newPart = factory.giveRobotPart(this);
        if(newPart == null) {
            return;
        }
        String name = newPart.getPartName();
        System.out.println("Wednesday taking robot part " + name);
        if (robots.containsKey(name)) {
            robots.put(name, robots.get(name) + 1);
        } else {
            robots.put(name, 1);
        }
    }

    public int createRobots() {
        int robotsNumber = 0;
        OptionalInt intNumber = robots.values().stream().mapToInt(Integer::intValue).min();
        if(intNumber.isPresent()) {
            robotsNumber = intNumber.getAsInt();
        }
        System.out.println("Wednesday created robots: " + robotsNumber);
        return robotsNumber;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            if (isNight) {
                takeRobotPart(factory);
            } else {
                try {
                    Thread.sleep(3L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
