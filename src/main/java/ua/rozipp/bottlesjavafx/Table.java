package ua.rozipp.bottlesjavafx;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Table {

    public final int countColors;
    public final int height;
    public final int countBottles;

    private final List<Bottle> bottles = new LinkedList<>();

    public Table(int countColors, int height) {
        this.height = height;
        this.countColors = countColors;
        this.countBottles = countColors + 2;
        for (int i = 0; i < countBottles; i++) {
            bottles.add(new Bottle(height));
        }
    }

    public Table(Table table) {
        this.height = table.height;
        this.countColors = table.countColors;
        this.countBottles = countColors + 2;
        for (int i = 0; i < countBottles; i++) {
            bottles.add(new Bottle(table.getBottle(i)));
        }
    }

    public boolean isMoving(Step parent, int from, int to) {
        if (!isTake(from) || !isPut(from, to)) return false;

        for (Step s = parent.getParent(); s != null; s = s.getParent()) {
            if (parent.getState().equals(s.getState())) {
                return false;
            }
        }

        Bottle fromBottle = this.getBottle(from);
        Bottle toBottle = this.getBottle(to);

        CC cc = fromBottle.getLastColor();
        return toBottle.getFreeNodeCount() >= fromBottle.getCountColor(cc);
    }

    public boolean isTake(int from) {
        Bottle fromBottle = this.getBottle(from);
        if (fromBottle.isCompleted()) return false;
        return !fromBottle.isEntry();
    }

    public boolean isPut(int from, int to) {
        Bottle fromBottle = this.getBottle(from);
        Bottle toBottle = this.getBottle(to);
        if (toBottle.isFull()) return false;
        if (fromBottle.isSameColor() && (toBottle.isEntry() || toBottle.getFreeNodeCount() < fromBottle.getOccupancyRate())) return false;
        if (toBottle.isEntry()) return true;
        return toBottle.isEqualsColor(fromBottle.getLastColor());
    }

    public boolean isCompleted() {
        int countCompleted = 0;
        for (Bottle b : bottles) {
            if (b.isCompleted()) {
                countCompleted++;
            }
        }
        return countCompleted == countColors;
    }

    public void move(int from, int to) {
//        System.out.println("move: " + step + " -- " + step.getState());
        bottles.get(to).put(bottles.get(from).take());
    }

    public Bottle getBottle(int i) {
        return bottles.get(i);
    }

    public void setBottle(int j, Integer[] integers) {
        for (Integer integer : integers) {
            bottles.get(j).put(CC.getCC(integer));
        }
    }

    public void initRandom(Random random) {
        ArrayList<Integer> pool = new ArrayList<>();
        for (int i = 0; i < countColors; i++) {
            pool.add(height);
        }

        for (int j = 0; j < countColors; j++) {
            for (int i = 0; i < height; i++) {
                int c = random.nextInt(countColors);
                while (pool.get(c) == 0) {
                    c = random.nextInt(countColors);
                }
                pool.set(c, pool.get(c) - 1);
                bottles.get(j).put(CC.getCC(c + 1));
            }
        }
    }

    public BigInteger calcState() {
        BigInteger state = new BigInteger("0");
        for (Bottle bottle: bottles){
            for (CC cc : bottle.getStack()){
                state = state.multiply(BigInteger.valueOf(countColors)).add(BigInteger.valueOf(cc.getId()));
            }
        }
//        System.out.println(state);
        return state;
    }

    public void clear() {
    }
}