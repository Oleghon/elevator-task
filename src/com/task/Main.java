package com.task;

import java.util.*;

public class Main {
    private static Random random = new Random();

    public static void main(String[] args) {

        int buildingFloors = generate(5, 20);
        Building building = new Building(buildingFloors, initMap(buildingFloors));

        building.show();
        building.getToFloorByElevator();
        System.out.println("--------------------------------");
        building.show();

    }

    private static Map<Integer, List<Passenger>> initMap(int maxStage) {
        Map<Integer, List<Passenger>> stages = new LinkedHashMap<>();
        for (int i = 1; i <= maxStage; i++) {
            if (i == 1) {
                stages.put(i, initList(2, maxStage));
            } else if (i == maxStage) {
                stages.put(i, initList(1, maxStage - 1));
            } else {
                stages.put(i, initList(1, maxStage));
            }
        }
        return stages;
    }

    private static List<Passenger> initList(int minStage, int maxStage) {
        List<Passenger> passengers = new ArrayList<>();
        int maxPassenger = generate(0, 10);
        for (int i = 0; i < maxPassenger; i++) {
            passengers.add(new Passenger(generate(minStage, maxStage)));
        }
        return passengers;
    }

    private static int generate(int min, int max) {
        return random.nextInt(min, max + 1);
    }
}