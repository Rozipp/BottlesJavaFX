package ua.rozipp.bottlesjavafx;

import java.math.BigInteger;
import java.util.*;

public class Step {

    public static int numberGlobal = 0;
    public static HashMap<BigInteger, Step> steps = new HashMap<>();

    public final int from;
    public final int to;
    private final Step parent;
    private final List<Step> children = new LinkedList<>();
    private final Table table;
    private final BigInteger state;
    private int now = -1;
    private boolean end = false;
    private boolean completed = false;
    private int number;

    public Step(Table table) {
        this.parent = null;
        this.from = -1;
        this.to = -1;
        this.number = numberGlobal++;
        this.table = table;
        state = table.calcState();
        steps.put(state, this);
    }

    public Step(Step parent, int from, int to) {
        this.parent = parent;
        this.from = from;
        this.to = to;
        this.number = numberGlobal++;
        this.table = new Table(parent.getTable());
        table.move(from, to);
        state = table.calcState();
        steps.put(state, this);
        System.out.println(number);
    }

    @Override
    public String toString() {
        return from + " - " + to;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Step step)) return false;
        return step.from == this.from && step.to == this.to;
    }

    public Step getParent() {
        return parent;
    }

    public Table getTable() {
        return table;
    }

    public void calcAllNextStep() {
        for (int from = 0; from < table.countBottles; from++) {
            for (int to = 0; to < table.countBottles; to++) {
                if (from == to) continue;
                if (this.getParent() != null)
                    if (this.getParent().from == this.to &&
                            this.getParent().to == this.from) continue;

                if (table.isMoving(this, from, to)) {
                    children.add(new Step(this, from, to));
                }
            }
        }

        if (children.isEmpty()) end = true;
    }

    public BigInteger getState() {
        return state;
    }

    public Step go() {
        System.out.println("number=" + number + "  " +  children.size() + " end = " + end + "  now = " + now);
        if (end) return parent;
        if (now >= children.size()+1) return parent;
        return children.get(++now);

    }

    public Step back() {
        System.out.println("number=" + number + "  " +  children.size() + " end = " + end + "  now = " + now);
        parent.now--;
        return parent;
    }

    public void clear() {
        table.clear();
    }
}
