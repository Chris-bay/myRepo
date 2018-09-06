package Hero;

public class PropertySet {
    /**
     *
     * PropertySet
     *      |_mu:
     *      |   |_name
     *      |   |_current
     *      |   |_max
     *      |   |_start
     *      |   |_mod
     *      |
     *      |_in:...
     *      |_...
     *
     *
     */
    public Property mu = new Property();
    public Property in = new Property();
    public Property kl = new Property();
    public Property ch = new Property();
    public Property ff = new Property();
    public Property ko = new Property();
    public Property kk = new Property();
    public Property ge = new Property();
    public Property gs = new Property();

    public PropertySet(){
        mu.propertyName = Property.PropertyName.MUT;
        in.propertyName = Property.PropertyName.INTUITION;
        ch.propertyName = Property.PropertyName.CHARISMA;
        kl.propertyName = Property.PropertyName.KLUGHEIT;
        ff.propertyName = Property.PropertyName.FINGERFERTIGKEIT;
        ko.propertyName = Property.PropertyName.KONSTITUTION;
        kk.propertyName = Property.PropertyName.KOERPERKRAFT;
        ge.propertyName = Property.PropertyName.GEWANDTHEIT;
        gs.propertyName = Property.PropertyName.GESCHWINDIGKEIT;
    }

    public void setMod(Property.PropertyName prop, Integer value){
        switch (prop){
            case MUT:
                mu.mod = value;
                break;
            case CHARISMA:
                ch.mod = value;
                break;
            case INTUITION:
                in.mod = value;
                break;
            case KLUGHEIT:
                kl.mod = value;
                break;
            case FINGERFERTIGKEIT:
                ff.mod = value;
                break;
            case KONSTITUTION:
                ko.mod = value;
                break;
            case KOERPERKRAFT:
                kk.mod = value;
                break;
            case GEWANDTHEIT:
                ge.mod = value;
                break;
            case GESCHWINDIGKEIT:
                gs.mod = value;
                break;
            default:
                break;
        }
    }
    public void setCurrently(Property.PropertyName prop, Integer value){
        switch (prop){
            case MUT:
                mu.curently = value;
                break;
            case CHARISMA:
                ch.curently = value;
                break;
            case INTUITION:
                in.curently = value;
                break;
            case KLUGHEIT:
                kl.curently = value;
                break;
            case FINGERFERTIGKEIT:
                ff.curently = value;
                break;
            case KONSTITUTION:
                ko.curently = value;
                break;
            case KOERPERKRAFT:
                kk.curently = value;
                break;
            case GEWANDTHEIT:
                ge.curently = value;
                break;
            case GESCHWINDIGKEIT:
                gs.curently = value;
                break;
            default:
                break;
        }
    }
    public void setStart(Property.PropertyName prop, Integer value){
        switch (prop){
            case MUT:
                mu.start = value;
                break;
            case CHARISMA:
                ch.start = value;
                break;
            case INTUITION:
                in.start = value;
                break;
            case KLUGHEIT:
                kl.start = value;
                break;
            case FINGERFERTIGKEIT:
                ff.start = value;
                break;
            case KONSTITUTION:
                ko.start = value;
                break;
            case KOERPERKRAFT:
                kk.start = value;
                break;
            case GEWANDTHEIT:
                ge.start = value;
                break;
            case GESCHWINDIGKEIT:
                gs.start = value;
                break;
            default:
                break;
        }
    }
    public void setMax(Property.PropertyName prop, Integer value){
        switch (prop){
            case MUT:
                mu.max = value;
                break;
            case CHARISMA:
                ch.max = value;
                break;
            case INTUITION:
                in.max = value;
                break;
            case KLUGHEIT:
                kl.max = value;
                break;
            case FINGERFERTIGKEIT:
                ff.max = value;
                break;
            case KONSTITUTION:
                ko.max = value;
                break;
            case KOERPERKRAFT:
                kk.max = value;
                break;
            case GEWANDTHEIT:
                ge.max = value;
                break;
            case GESCHWINDIGKEIT:
                gs.max = value;
                break;
            default:
                break;
        }
    }
}
