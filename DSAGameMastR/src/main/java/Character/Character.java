package Character;

import Universe.*;

public class Character {
    private String Name;
    private Race race;
    private Culture culture;
    private String profession; //Enum WIP
    private String secondProfession;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Culture getCulture() {
        return culture;
    }

    public void setCulture(Culture culture) {
        this.culture = culture;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getSecondProfession() {
        return secondProfession;
    }

    public void setSecondProfession(String secondProfession) {
        this.secondProfession = secondProfession;
    }
}
