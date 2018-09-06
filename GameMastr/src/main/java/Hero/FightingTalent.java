package Hero;

public class FightingTalent {
    private String name;
    private Integer skilledAT;
    private Integer skilledPA;
    private Integer skilledFK;
    private Boolean necessaryAT;
    private Boolean necessaryPA;
    private Boolean necessaryFK;

    public FightingTalent(){
    }

    public FightingTalent(String name, Integer at, Integer pa, Integer fk, boolean bat, boolean bpa, boolean bfk){
        this.name = name;
        this.skilledAT = at;
        this.skilledPA = pa;
        this.skilledFK = fk;
        this.necessaryAT = bat;
        this.necessaryPA = bpa;
        this.necessaryFK = bfk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSkilledAT() {
        return skilledAT;
    }

    public void setSkilledAT(Integer skilledAT) {
        this.skilledAT = skilledAT;
    }

    public Integer getSkilledPA() {
        return skilledPA;
    }

    public void setSkilledPA(Integer skilledPA) {
        this.skilledPA = skilledPA;
    }

    public Integer getSkilledFK() {
        return skilledFK;
    }

    public void setSkilledFK(Integer skilledFK) {
        this.skilledFK = skilledFK;
    }

    public Boolean getNecessaryAT() {
        return necessaryAT;
    }

    public void setNecessaryAT(Boolean necessaryAT) {
        this.necessaryAT = necessaryAT;
    }

    public Boolean getNecessaryPA() {
        return necessaryPA;
    }

    public void setNecessaryPA(Boolean necessaryPA) {
        this.necessaryPA = necessaryPA;
    }

    public Boolean getNecessaryFK() {
        return necessaryFK;
    }

    public void setNecessaryFK(Boolean necessaryFK) {
        this.necessaryFK = necessaryFK;
    }

    @Override
    public String toString(){
        return name + " " + necessaryAT + necessaryPA + necessaryFK;
    }
}
