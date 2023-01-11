package ua.rozipp.bottlesjavafx;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.LinkedList;
import java.util.List;

public class WinBottle {

    private final int height;
    public final VBox vBox = new VBox();
    private final List<Circle> circles = new LinkedList<>();

    public WinBottle(int height) {
        this.height = height;
        vBox.setSpacing(3);
        vBox.setPrefSize(20, 20 * height);

        for (int i = height - 1; i >= 0; i--) {
            Circle circle = new Circle();
            circle.setRadius(20);
            circle.setFill(CC.NULL.getColor());
            circles.add(circle);
        }
        for (int i = height - 1; i >= 0; i--) {
            vBox.getChildren().add(circles.get(i));
        }
    }

    public void setBottle(Bottle bottle) {
        for (int i = 0; i < bottle.getHeight(); i++)
            setColor(i, bottle.get(i).getColor());
    }

    public void setColor(int i, Color color){
        circles.get(i).setFill(color);
    }

}