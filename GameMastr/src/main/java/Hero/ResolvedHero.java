package Hero;

import Universe.Culture;
import Universe.Race;

public class ResolvedHero {
    private Integer id;
    private String race;
    private String culture;
    public PropertySet properties;
    private String Name;
    private Integer age;
    private Integer height;
    private Integer weight;
    private String eye_color;
    private String hair_color;
    private String birthdate;
    private String title;
    private Integer social;
    private String profession;
    private String secondProfession;
    public VitalSet vitals;
    public Talents talents;

    public ResolvedHero(){
        talents = new Talents();
        properties = new PropertySet();
        vitals = new VitalSet();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getCulture() {
        return culture;
    }

    public void setCulture(String culture) {
        this.culture = culture;
    }

    public PropertySet getProperties() {
        return properties;
    }

    public void setProperties(PropertySet properties) {
        this.properties = properties;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getEye_color() {
        return eye_color;
    }

    public void setEye_color(String eye_color) {
        this.eye_color = eye_color;
    }

    public String getHair_color() {
        return hair_color;
    }

    public void setHair_color(String hair_color) {
        this.hair_color = hair_color;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSocial() {
        return social;
    }

    public void setSocial(Integer social) {
        this.social = social;
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

    public Talents getTalents() {
        return talents;
    }

    public void setTalents(Talents talents) {
        this.talents = talents;
    }

    public VitalSet getVitals() {
        return vitals;
    }

    public void setVitals(VitalSet vitals) {
        this.vitals = vitals;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
