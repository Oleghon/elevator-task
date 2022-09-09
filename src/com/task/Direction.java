package com.task;

public enum Direction {
    UP, DOWN;

    public int moveByDirection(int stage) {
        if (this.equals(Direction.UP)) {
            return stage + 1;
        } else {
            return stage - 1;
        }
    }
}
