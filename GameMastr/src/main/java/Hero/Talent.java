package Hero;

public class Talent {
    String name;
    Property.PropertyName prop1;
    Property.PropertyName prop2;
    Property.PropertyName prop3;
    Integer skilled;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Property.PropertyName getProp1() {
        return prop1;
    }

    public void setProp1(Property.PropertyName prop1) {
        this.prop1 = prop1;
    }

    public Property.PropertyName getProp2() {
        return prop2;
    }

    public void setProp2(Property.PropertyName prop2) {
        this.prop2 = prop2;
    }

    public Property.PropertyName getProp3() {
        return prop3;
    }

    public void setProp3(Property.PropertyName prop3) {
        this.prop3 = prop3;
    }

    public Integer getSkilled() {
        return skilled;
    }

    public void setSkilled(Integer skilled) {
        this.skilled = skilled;
    }

    @Override
    public String toString(){
        return name + " " + prop1 + " " + prop2 + " " + prop3 + " | " + skilled.toString();
    }
}
