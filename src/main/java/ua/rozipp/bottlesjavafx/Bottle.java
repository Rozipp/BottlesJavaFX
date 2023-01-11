package ua.rozipp.bottlesjavafx;

import java.util.ArrayList;

public class Bottle {

    private final int height;
    private final ArrayList<CC> stack = new ArrayList<>();
    private int occupancyRate = 0;
    private boolean self = false;

    public Bottle(int height) {
        this.height = height;

        for (int i = 0; i < height; i++) {
            stack.add(CC.NULL);
        }
    }

    public Bottle(Bottle bottle) {
        this.height = bottle.height;

        for (int i = 0; i < height; i++) {
            stack.add(bottle.getColor(i));
        }
        this.occupancyRate = bottle.occupancyRate;
    }

    private CC getColor(int i) {
        if (i < 0 || i >= stack.size()) return CC.NULL;
        return stack.get(i);
    }

    private void setColor(int i, CC cc) {
        stack.set(i, cc);
    }

    public CC getLastColor() {
        return getColor(occupancyRate - 1);
    }

    public CC take() {
        occupancyRate--;
        CC cc = getColor(occupancyRate);
        setColor(occupancyRate, CC.NULL);
        return cc;
    }

    public void put(CC cc) {
        setColor(occupancyRate, cc);
        occupancyRate++;
    }

    public boolean isFull() {
        return occupancyRate >= height;
    }

    public boolean isCompleted() {
        if (stack.isEmpty()) return false;
        if (!isFull()) return false;
        CC cc = stack.get(0);
        for (CC node : stack) {
            if (node != cc) return false;
        }
        return true;
    }

    public boolean isSameColor() {
        if (self) return true;
        if (isEntry()) return false;
        CC cc = stack.get(0);
        for (CC node : stack) {
            if (node.isNULL()) break;
            if (node != cc) return false;
        }
        self = true;
        return true;
    }

    public boolean isEqualsColor(CC color) {
        return getLastColor() == color;
    }

    public boolean isEntry() {
        return occupancyRate == 0;
    }

    public int getFreeNodeCount() {
        return height - occupancyRate;
    }

    public int getOccupancyRate() {
        return occupancyRate;
    }

    public int getCountColor(CC cc) {
        int count = 0;
        for (int i = getOccupancyRate(); i > 0; i--) {
            if (stack.get(i - 1).equals(cc))
                count++;
            else break;
        }
//        System.out.println("count = " + count);
        return count;
    }

    public int getHeight() {
        return height;
    }

    public CC get(int i) {
        return stack.get(i);
    }

    public ArrayList<CC> getStack() {
        return stack;
    }
}
