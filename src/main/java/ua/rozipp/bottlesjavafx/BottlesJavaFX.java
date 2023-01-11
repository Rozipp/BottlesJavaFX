package ua.rozipp.bottlesjavafx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class BottlesJavaFX extends Application {

    public static Random random = new Random();

    private final VBox vBoxMain = new VBox();
    private HBox hBoxWinTopNodes = new HBox();
    public List<Circle> topNodes = new LinkedList<>();
    private HBox hBoxWinBottles = new HBox();
    public List<WinBottle> winBottles = new LinkedList<>();

    private Step lastStep;
    boolean finished = false;

    @Override
    public void start(Stage stage) {
        stage.setWidth(400);
        stage.setHeight(300);

        Button buttonStart = new Button();
        buttonStart.setText("Старт");
        buttonStart.setOnAction(actionEvent -> buttonStartClick(stage));

        Button buttonNext = new Button();
        buttonNext.setText("Вперед");
        buttonNext.setOnAction(actionEvent -> buttonNextClick());

        Button buttonPrev = new Button();
        buttonPrev.setText("Назад");
        buttonPrev.setOnAction(actionEvent -> buttonPrevClick());

        HBox hBoxButtons = new HBox();
        hBoxButtons.setSpacing(30);
        hBoxButtons.getChildren().addAll(buttonStart, buttonNext, buttonPrev);

        vBoxMain.getChildren().addAll(hBoxButtons);

        stage.setScene(new Scene(vBoxMain));
        stage.show();
    }

    private void buttonStartClick(Stage stage) {
        finished = true;
//        if (root != null) root.clear();
        vBoxMain.getChildren().removeAll(hBoxWinTopNodes, hBoxWinBottles);

        lastStep = new Step(newTable());;
//        lastStep.calcAllNextStep();

        int height = lastStep.getTable().height;
        int countBottles = lastStep.getTable().countBottles;

        stage.setWidth(countBottles * 70);
        stage.setHeight(60 * height + 120);

        hBoxWinBottles = new HBox();
        hBoxWinBottles.setPadding(new Insets(10, 10, 10, 10));
        hBoxWinBottles.setSpacing(30);
        for (int i = 0; i < countBottles; i++) {
            WinBottle winBottle = new WinBottle(height);
            winBottles.add(winBottle);
            int finalI = i;
            winBottle.vBox.setOnMouseClicked(mouseEvent -> {
                if (!finished) {
                    onMouseClicked(finalI);
                }
            });
            hBoxWinBottles.getChildren().add(winBottle.vBox);
        }

        hBoxWinTopNodes = new HBox();
        hBoxWinTopNodes.setPadding(new Insets(10, 10, 10, 10));
        hBoxWinTopNodes.setSpacing(30);
        for (int i = 0; i < countBottles; i++) {
            Circle circle = new Circle();
            circle.setRadius(20);
            circle.setFill(CC.NULL.getColor());
            topNodes.add(circle);
            hBoxWinTopNodes.getChildren().add(circle);
        }

        vBoxMain.getChildren().addAll(hBoxWinTopNodes, hBoxWinBottles);
        finished = false;
        update(lastStep.getTable());
    }

    private void buttonNextClick() {
        if (!finished) {
            lastStep = lastStep.go();
            update(lastStep.getTable());
//                if (lastStep.table.isCompleted()) {
//                    System.out.println("-----------Ураааа--------------");
//                    System.out.println();
//                    finished = true;
//                }
        }
    }

    private void buttonPrevClick() {
        if (!finished) {
            lastStep = lastStep.back();
            update(lastStep.getTable());
        }
    }

    private int circleInTop = -1;

    private void onMouseClicked(int clickBottleNumber) {
        if (circleInTop == -1) {
            if (lastStep.getTable().isTake(clickBottleNumber)) {
                upToTop(clickBottleNumber);
                circleInTop = clickBottleNumber;
            }
        } else {
            if (isPut(circleInTop, clickBottleNumber)) {
                move(circleInTop, clickBottleNumber);
                circleInTop = -1;
            } else {
                if (lastStep.getTable().isTake(clickBottleNumber)) {
                    upToTopBack(circleInTop);
                    upToTop(clickBottleNumber);
                    circleInTop = clickBottleNumber;
                }
            }
        }
        update(lastStep.getTable());
    }

    public boolean isPut(int from, int to) {
        CC fromTopNode = CC.getCC((Color) topNodes.get(from).getFill());
        Bottle toBottle = lastStep.getTable().getBottle(to);
        if (toBottle.isFull()) return false;
        return toBottle.isEntry() || from == to || toBottle.isEqualsColor(fromTopNode);
    }

    public void upToTop(int from) {
        topNodes.get(from).setFill(lastStep.getTable().getBottle(from).getLastColor().getColor());
        int n = lastStep.getTable().getBottle(from).getOccupancyRate();
        winBottles.get(from).setColor(n - 1, CC.NULL.getColor());
    }

    public void upToTopBack(int from) {
        int n = lastStep.getTable().getBottle(from).getOccupancyRate();
        winBottles.get(from).setColor(n - 1, (Color) topNodes.get(from).getFill());
        topNodes.get(from).setFill(CC.NULL.getColor());
    }

    public void move(int from, int to) {
        Table table = new Table(lastStep.getTable());
        table.move(from, to);
        lastStep = Step.steps.get(table.calcState());
        update(lastStep.getTable());
    }

    public void update(Table table) {
        for (int i = 0; i < winBottles.size(); i++) {
            winBottles.get(i).setBottle(table.getBottle(i));
        }
    }

    private Table newTable() {
        Table table = new Table(4, 4);
        table.initRandom(BottlesJavaFX.random);
//        table.setBottle(0, new Integer[]{1, 9, 11, 6});
//        table.setBottle(1, new Integer[]{5, 8, 10, 3});
//        table.setBottle(2, new Integer[]{1, 10, 10, 4});
//        table.setBottle(3, new Integer[]{5, 5, 9, 3}); //? 5
//        table.setBottle(4, new Integer[]{6, 7, 2, 12});
//        table.setBottle(5, new Integer[]{5, 11, 7, 11});
//        table.setBottle(6, new Integer[]{9, 8, 3, 12});
//        table.setBottle(7, new Integer[]{6, 6, 9, 1}); //? 6
//        table.setBottle(8, new Integer[]{10, 12, 7, 2});
//        table.setBottle(9, new Integer[]{2, 4, 8, 1}); //? 2
//        table.setBottle(10, new Integer[]{3, 7, 12, 11});
//        table.setBottle(11, new Integer[]{8, 4, 2, 4});

        return table;
    }

    public static void main(String[] args) {
        Application.launch();
    }

}