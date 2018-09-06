package Hero;

public class Property {
    Integer mod;
    Integer start;
    Integer curently;
    Integer max;
    PropertyName propertyName;

    public enum PropertyName{
        MUT,
        KLUGHEIT,
        INTUITION,
        CHARISMA,
        FINGERFERTIGKEIT,
        GEWANDTHEIT,
        KONSTITUTION,
        KOERPERKRAFT,
        GESCHWINDIGKEIT
    }

    public static String convertToShort(PropertyName prop){
        switch (prop){
            case MUT: return "MU";
            case CHARISMA: return "CH";
            case KLUGHEIT: return  "KL";
            case INTUITION: return "IN";
            case GEWANDTHEIT: return "GE";
            case KONSTITUTION: return "KO";
            case GESCHWINDIGKEIT: return "GS";
            case FINGERFERTIGKEIT: return "FF";
            case KOERPERKRAFT: return "KK";
            default: return "";
        }
    }

    public static PropertyName convertFromShort(String prop){
        switch (prop){
            case "MU": return PropertyName.MUT;
            case "CH": return PropertyName.CHARISMA;
            case "KL": return PropertyName.KLUGHEIT;
            case "IN": return PropertyName.INTUITION;
            case "GE": return PropertyName.GEWANDTHEIT;
            case "KO": return PropertyName.KONSTITUTION;
            case "GS": return PropertyName.GESCHWINDIGKEIT;
            case "FF": return PropertyName.FINGERFERTIGKEIT;
            case "KK": return PropertyName.KOERPERKRAFT;
            default: System.out.println("Error finding Short of" + prop);return null;
        }
    }

    public Integer getMod() {
        return mod;
    }

    public void setMod(Integer mod) {
        this.mod = mod;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getCurently() {
        return curently;
    }

    public void setCurently(Integer curently) {
        this.curently = curently;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public PropertyName getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(PropertyName propertyName) {
        this.propertyName = propertyName;
    }
}
