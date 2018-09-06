package GUI;

import javafx.beans.property.SimpleStringProperty;

public class TableRowClass {
    private SimpleStringProperty c1;
    private SimpleStringProperty c2;
    private SimpleStringProperty c3;
    private SimpleStringProperty c4;
    private SimpleStringProperty c5;

    TableRowClass(String c1, String c2, String c3, String c4, String c5){
        this.c1 = new SimpleStringProperty(c1);
        this.c2 = new SimpleStringProperty(c2);
        this.c3 = new SimpleStringProperty(c3);
        this.c4 = new SimpleStringProperty(c4);
        this.c5 = new SimpleStringProperty(c5);
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

    public String getC3() {
        return c3.get();
    }

    public SimpleStringProperty c3Property() {
        return c3;
    }

    public void setC3(String c3) {
        this.c3.set(c3);
    }

    public String getC4() {
        return c4.get();
    }

    public SimpleStringProperty c4Property() {
        return c4;
    }

    public void setC4(String c4) {
        this.c4.set(c4);
    }

    public String getC5() {
        return c5.get();
    }

    public SimpleStringProperty c5Property() {
        return c5;
    }

    public void setC5(String c5) {
        this.c5.set(c5);
    }
}
