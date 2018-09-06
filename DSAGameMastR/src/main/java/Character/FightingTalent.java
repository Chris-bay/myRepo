package Character;

public class FightingTalent {
    private String name;
    private Integer skilledAT;
    private Integer skilledPA;

    FightingTalent(String name, Integer at, Integer pa){
        this.name = name;
        this.skilledAT = at;
        this.skilledPA = pa;
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
}
