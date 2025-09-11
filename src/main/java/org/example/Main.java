package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Factory factory = new Factory();
        World world = new World(factory);
        Wednesday wednesday = new Wednesday(factory);

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.execute(factory);
        executorService.execute(world);
        executorService.execute(wednesday);

        for (int i = 0; i < 100; i++) {
            System.out.println("Start Day " + (i + 1));
            factory.setNight(false);
            world.setNight(false);
            wednesday.setNight(false);

            try {
                //System.out.println("sleep");
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Start Night");
            factory.setNight(true);
            world.setNight(true);
            wednesday.setNight(true);

            try {
                //System.out.println("sleep");
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("");
        }

        executorService.shutdownNow();


        int worldArmy = world.createRobots();
        int wednesdayArmy = wednesday.createRobots();

        //battle
        if (worldArmy > wednesdayArmy) {
            System.out.println("World win");
        }
        if (wednesdayArmy > worldArmy) {
            System.out.println("Wednesday win");
        }
        if (wednesdayArmy == worldArmy) {
            System.out.println("dead heat");
        }

    }
}


