package com.task;

import java.util.List;
import java.util.Map;

public class Building {
    private final int maxFloors;
    private Map<Integer, List<Passenger>> floors;
    private Elevator elevator = new Elevator();

    public Building(int maxFloors, Map<Integer, List<Passenger>> floors) {
        this.maxFloors = maxFloors;
        this.floors = floors;
    }

    public void getToFloorByElevator() {
        elevator.move(chooseDirection(elevator.getCurrentFloor()), floors);
    }

    private Direction chooseDirection(int floor) {
        if (floor > 1 && floor < maxFloors) {
            List<Passenger> passengers = floors.get(floor);
            long countUp = passengers.stream().filter(passenger -> passenger.floor() > floor).count();
            long countDown = passengers.stream().filter(passenger -> passenger.floor() < floor).count();
            return countUp > countDown ? Direction.UP : Direction.DOWN;
        } else if (floor == maxFloors) {
            return Direction.DOWN;
        } else {
            return Direction.UP;
        }
    }

    public void show() {
        floors.forEach((k, v) -> System.out.println("Floor #" + k + ", passengers: " + v.size()));
    }
}
