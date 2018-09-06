package Character;

public class Property {
    Integer mod;
    Integer start;
    Integer curently;
    Integer max;

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

    public String convertToShort(PropertyName prop){
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
}
