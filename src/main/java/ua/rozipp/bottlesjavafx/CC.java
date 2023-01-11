package ua.rozipp.bottlesjavafx;

import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

public enum CC {

    WHITE(0, Color.WHITE),        //0
    BLUE(1, Color.BLUE),         //1
    CYAN(2, Color.CYAN),         //2 бирюзовый
    MAGENTA(3, Color.MAGENTA),      //3 сиреневый
    INDIGO(4, Color.INDIGO),       //4 фиолетовый
    SKYBLUE(5, Color.SKYBLUE),      //5 голубой
    DEEPPINK(6, Color.DEEPPINK),     //6 розовый
    SADDLEBROWN(7, Color.SADDLEBROWN),  //7 коричневый
    CRIMSON(8, Color.CRIMSON),      //8 красный
    ORANGERED(9, Color.ORANGERED),    //9 светло-красный
    YELLOW(10, Color.YELLOW),       //10 желтый
    ORANGE(11, Color.ORANGE),       //11 оранжевый
    LIME(12, Color.LIME);         //12 Зеленый

    private final Color color;
    private final int id;

    CC(int id, Color color) {
        this.id = id;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public Color getColor() {
        return color;
    }

    public boolean isNULL() {
        return this == NULL;
    }

    public static final CC NULL = CC.WHITE;
    public static final Map<Integer, CC> ccMap = new HashMap<>();

    static {
        for (CC cc : CC.values()) {
            ccMap.put(cc.getId(), cc);
        }
    }

    public static CC getCC(int id) {
        return ccMap.get(id);
    }

    public static CC getCC(Color color) {
        for (CC cc : values()) {
            if (cc.getColor().equals(color))
                return cc;
        }
        return CC.NULL;
    }


//    @Override
//    public String toString(){
//        return Integer.toString(getId());
//    }

}
