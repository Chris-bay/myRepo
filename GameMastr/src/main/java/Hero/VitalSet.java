package Hero;


public class VitalSet {
    public Vital le = new Vital();
    public Vital au = new Vital();
    public Vital ae = new Vital();
    public Vital ke = new Vital();
    public Vital mr = new Vital();
    public Vital ini = new Vital();
    public Vital at = new Vital();
    public Vital pa = new Vital();
    public Vital fk = new Vital();
    public Vital ws = new Vital();

    public Vital fromShort(String s){
        switch (s){
            case "LE":
                return this.le;
            case "AU":
                return this.au;
            case "AE":
                return this.ae;
            case "KE":
                return this.ke;
            case "MR":
                return this.mr;
            case "INI":
                return this.ini;
            case "AT":
                return this.at;
            case "PA":
                return this.pa;
            case "FK":
                return this.fk;
            case "WS":
                return this.ws;
        }
        return null;
    }
}
