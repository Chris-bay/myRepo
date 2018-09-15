package GUI;

import javafx.beans.property.SimpleStringProperty;

public class SimpleTableRowClass {
    private SimpleStringProperty c1;
    private SimpleStringProperty c2;

    SimpleTableRowClass(String c1, String c2){
        this.c1 = new SimpleStringProperty(c1);
        this.c2 = new SimpleStringProperty(c2);
    }

    public String getC1() {
        return c1.get();
    }

    public SimpleStringProperty c1Property() {
        return c1;
    }

    public void setC1(String c1) {
        this.c1.set(c1);
    }

    public String getC2() {
        return c2.get();
    }

    public SimpleStringProperty c2Property() {
        return c2;
    }

    public void setC2(String c2) {
        this.c2.set(c2);
    }
}
