package com.task;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class Elevator {
    private final List<Passenger> passengers = new LinkedList<>();
    private final int maxPassengers = 5;
    private Direction direction = Direction.UP;
    private int currentFloor = 1;

    public void move(Direction direction, Map<Integer, List<Passenger>> passengerInBuilding) {
        this.direction = direction;
        int floor;
        floor = getBetterFloor(passengerInBuilding.get(currentFloor), currentFloor);
        System.out.println("Elevator on floor #" + currentFloor + ", and start getting to floor #" + floor);

        takePassengers(passengerInBuilding.get(currentFloor), floor);

        do {
            currentFloor = direction.moveByDirection(currentFloor);
            if (passengers.contains(new Passenger(currentFloor))) {
                letOutPassengers();
                System.out.println(maxPassengers - passengers.size() + " passengers went out on " + currentFloor + "th floor");
            }

            if (passengers.size() < maxPassengers) {
                floor = takePassengers(passengerInBuilding.get(currentFloor), floor);
                System.out.println("The elevator is getting to " + floor + "th floor");
            }
        } while (floor != currentFloor);

        System.out.println("Elevator got to floor #" + currentFloor + ".");
    }

    private void letOutPassengers() {
        passengers.removeIf(passenger -> passenger.floor() == currentFloor);
    }

    private int takePassengers(List<Passenger> passengersOnStage, int floor) {

        Predicate<Passenger> byTheWay;
        if (direction.equals(Direction.UP)) {
            byTheWay = p -> p.floor() > currentFloor;
        } else {
            byTheWay = p -> p.floor() < currentFloor;
        }

        List<Passenger> newPassengers = passengersOnStage.stream()
                .filter(byTheWay)
                .limit(maxPassengers - passengers.size())
                .toList();

        if (newPassengers.size() != 0) {
            System.out.println(passengersOnStage.size() + " passengers on " + currentFloor + "th floor.\n"
                    + newPassengers.size() + " passengers got into elevator");
            passengersOnStage.removeAll(newPassengers);
            passengers.addAll(newPassengers);

            return getBetterFloor(newPassengers, floor);
        }
        return floor;
    }

    private int getBetterFloor(List<Passenger> passengers, int floor) {
        if (direction == Direction.UP) {
            return getHighestFloor(passengers, floor);
        } else {
            return getLowestFloor(passengers, floor);
        }
    }

    private int getHighestFloor(List<Passenger> passengers, int floor) {
        return passengers.stream()
                .map(Passenger::floor)
                .filter(fl -> fl > floor)
                .max(Integer::compareTo)
                .orElse(floor);
    }

    private int getLowestFloor(List<Passenger> passengers, int floor) {
        return passengers.stream()
                .map(Passenger::floor)
                .filter(fl -> fl < floor)
                .min(Integer::compareTo)
                .orElse(floor);
    }

    public int getCurrentFloor() {
        return currentFloor;
    }
}
