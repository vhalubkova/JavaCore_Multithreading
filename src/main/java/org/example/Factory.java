package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Factory implements Runnable {
    List<RobotPart> store = new ArrayList<>();

    private boolean isNight = false;
    private HashMap<Faction, Integer> counter = new HashMap<>();

    public void setNight(boolean isNight) {
        this.isNight = isNight;
        counter = new HashMap<>();
    }

    public void work() {

        Random random = new Random();
        int number = random.nextInt(1, 10);
        System.out.println("Factory generate parts of robot: " + number + " parts");
        for (int i = 0; i < number; i++) {
            RobotPart part = createRobotPart();
            store.add(part);
            System.out.println(part.getPartName());
        }
    }

    private RobotPart createRobotPart() {
        RobotPart part = null;
        Random random = new Random();
        int type = random.nextInt(4);
        switch (type) {
            case 0:
                part = new Head();
                break;
            case 1:
                part = new Torso();
                break;
            case 2:
                part = new Hand();
                break;
            case 3:
                part = new Feet();
                break;
        }
        return part;
    }

    public synchronized RobotPart giveRobotPart(Faction faction) {
        boolean is = isNight;
        if (!isNight) {
            return null;
        }
        if (store.isEmpty()) {
            return null;
        }
        if (!counter.containsKey(faction)) {
            counter.put(faction, 1);
        } else {
            if (counter.get(faction) > 5) {
                return null;
            }
            counter.put(faction, counter.get(faction) + 1);
        }

        RobotPart part = store.getLast();
        store.remove(part);

        return part;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            if (!isNight && store.isEmpty()) {
                work();
            } else {
                try {
                    Thread.sleep(5L);
                } catch (InterruptedException e) {
                    break;
                }



            }
        }
    }
}
