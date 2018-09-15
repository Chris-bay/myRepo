package GUI;

import Database.DBOverwatch;
import Hero.FightingTalent;
import Hero.Property;
import Hero.ResolvedHero;
import Hero.Talent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EditHerosController extends AnchorPane {

    @FXML
    private Menu menu = new Menu();
    @FXML
    private TextField nameText;
    @FXML
    private TextField ageText = new TextField();
    @FXML
    private TextField birthdayText = new TextField();
    @FXML
    private TextField eyecolorText = new TextField();
    @FXML
    private TextField haircolorText = new TextField();
    @FXML
    private TextField profText = new TextField();
    @FXML
    private TextField scndprofText = new TextField();
    @FXML
    private TextField titleText = new TextField();
    @FXML
    private TextField socialText = new TextField();
    @FXML
    private TextField heightText = new TextField();
    @FXML
    private TextField weightText = new TextField();
    @FXML
    private ComboBox<String> raceDropDown;
    @FXML
    private ComboBox<String> cultureDropDown;
    @FXML
    private TableView<TableRowClass> propTable;
    @FXML
    private ComboBox<String> propDropDown;
    @FXML
    private ComboBox<String> specDropDown;
    @FXML
    private TextField editProps;
    @FXML
    private TableView<TableRowClass> talentTable;
    @FXML
    private ComboBox<String> talentDropDown;
    @FXML
    private ComboBox<String> talentGroupDropDown;
    @FXML
    private TextField editTalents;
    @FXML
    private TextField editAT;
    @FXML
    private TextField editPA;
    @FXML
    private TextField editFK;
    @FXML
    private Text infoATText;
    @FXML
    private Text infoPAText;
    @FXML
    private Text infoFKText;
    @FXML
    private Text infoValueText;
    @FXML
    private Text infoText;

    //private ArrayList<MenuItem> items = new ArrayList<MenuItem>();

    private int heroId = -1;
    private DBOverwatch dbOverwatch = DBOverwatch.instance;
    private ResolvedHero hero = new ResolvedHero();
    private String[] spec = {"Start", "Mod", "Aktuell", "Max"};
    private String[] prop = {"Mut", "Klugheit", "Intuition", "Karisma", "Fingerfertigkeit", "Gewandtheit",
            "Konstitution", "Körperkraft", "Geschwindigkeit", "Lebenenergie", "Ausdauer", "Astralenergie",
            "Karmaenergie", "Magieresistenz", "INI-Basiswert", "AT-Basiswert", "PA-Basiswert", "FK-Basiswert",
            "Wundschwelle"};
    private String[] talentGroup = {"Kampf", "Ritual", "Körperlich", "Gesellschaftlich", "Natur", "Wissen", "Handwerk",
            "Sprachen und Schriften"};
    private ArrayList<String> fight = new ArrayList<>();
    private ArrayList<String> physical = new ArrayList<>();
    private ArrayList<String> ritual = new ArrayList<>();
    private ArrayList<String> society = new ArrayList<>();
    private ArrayList<String> nature = new ArrayList<>();
    private ArrayList<String> knowledge = new ArrayList<>();
    private ArrayList<String> crafting = new ArrayList<>();
    private ArrayList<String> language = new ArrayList<>();

    public void initialize(){
        cultureDropDown.getItems().addAll(dbOverwatch.loadCultures());
        raceDropDown.getItems().addAll(dbOverwatch.loadRaces());
        specDropDown.getItems().addAll(spec);
        specDropDown.getSelectionModel().select(0);
        propDropDown.getItems().addAll(prop);
        propDropDown.getSelectionModel().select(0);
        talentGroupDropDown.getItems().addAll(talentGroup);
        //talentGroupDropDown.getSelectionModel().select(0);
        menu.getItems().clear();
        for (int i = 0; i < dbOverwatch.heros.size(); i++) {
            ResolvedHero hero = dbOverwatch.heros.get(i);
            int id = i;
            MenuItem menuItem = new MenuItem();
            menuItem.setText(hero.getName());
            menuItem.setOnAction(event -> EditHerosController.this.hero = loadHero(id+1));
            menu.getItems().add(menuItem);
        }

    }

    void setHeroId(int id){
        //System.out.println("Hero to load next:" + id);
        this.heroId = id;
    }

    private ResolvedHero loadHero(int id){
        fight.clear();
        physical.clear();
        ritual.clear();
        society.clear();
        nature.clear();
        knowledge.clear();
        crafting.clear();
        language.clear();

        hero = dbOverwatch.heros.get(id-1);
        //System.out.println("Displaying hero: " + hero.getName());
        nameText.setText(hero.getName());
        ageText.setText(hero.getAge().toString());
        birthdayText.setText(hero.getBirthdate());
        profText.setText(hero.getProfession());
        scndprofText.setText(hero.getProfession());
        titleText.setText(hero.getTitle());
        socialText.setText(hero.getSocial().toString());
        eyecolorText.setText(hero.getEye_color());
        haircolorText.setText(hero.getHair_color());
        heightText.setText(hero.getHeight().toString());
        weightText.setText(hero.getWeight().toString());
        raceDropDown.getSelectionModel().select(hero.getRace());
        cultureDropDown.getSelectionModel().select(hero.getCulture());
        propDropDown.getSelectionModel().select(0);
        specDropDown.getSelectionModel().select(0);

        final ObservableList<TableRowClass> propData = FXCollections.observableArrayList();
        TableColumn col1 = propTable.getColumns().get(0);
        TableColumn col2 = propTable.getColumns().get(1);
        TableColumn col3 = propTable.getColumns().get(2);
        TableColumn col4 = propTable.getColumns().get(3);
        TableColumn col5 = propTable.getColumns().get(4);
        col1.setCellValueFactory(new PropertyValueFactory<TableRowClass, String>("c1"));
        col2.setCellValueFactory(new PropertyValueFactory<TableRowClass, String>("c2"));
        col3.setCellValueFactory(new PropertyValueFactory<TableRowClass, String>("c3"));
        col4.setCellValueFactory(new PropertyValueFactory<TableRowClass, String>("c4"));
        col5.setCellValueFactory(new PropertyValueFactory<TableRowClass, String>("c5"));
        
        final ObservableList<TableRowClass> talentData = FXCollections.observableArrayList();
        TableColumn col11 = (TableColumn)talentTable.getColumns().get(0);
        TableColumn col12 = (TableColumn)talentTable.getColumns().get(1);
        TableColumn col13 = (TableColumn)talentTable.getColumns().get(2);
        TableColumn col14 = (TableColumn)talentTable.getColumns().get(3);
        TableColumn col15 = (TableColumn)talentTable.getColumns().get(4);
        col11.setCellValueFactory(new PropertyValueFactory<TableRowClass, String>("c1"));
        col12.setCellValueFactory(new PropertyValueFactory<TableRowClass, String>("c2"));
        col13.setCellValueFactory(new PropertyValueFactory<TableRowClass, String>("c3"));
        col14.setCellValueFactory(new PropertyValueFactory<TableRowClass, String>("c4"));
        col15.setCellValueFactory(new PropertyValueFactory<TableRowClass, String>("c5"));
        
        propData.add(new TableRowClass("Name", "Start", "Mod", "Aktuell", "Max"));
        propData.add(new TableRowClass("Mut", hero.properties.mu.getStart().toString(),
                hero.properties.mu.getMod().toString(),
                hero.properties.mu.getCurently().toString(),
                hero.properties.mu.getMax().toString()));
        propData.add(new TableRowClass("Klugheit", hero.properties.kl.getStart().toString(),
                hero.properties.kl.getMod().toString(),
                hero.properties.kl.getCurently().toString(),
                hero.properties.kl.getMax().toString()));
        propData.add(new TableRowClass("Intuition", hero.properties.in.getStart().toString(),
                hero.properties.in.getMod().toString(),
                hero.properties.in.getCurently().toString(),
                hero.properties.in.getMax().toString()));
        propData.add(new TableRowClass("Charisma", hero.properties.ch.getStart().toString(),
                hero.properties.ch.getMod().toString(),
                hero.properties.ch.getCurently().toString(),
                hero.properties.ch.getMax().toString()));
        propData.add(new TableRowClass("Fingerfertigkeit", hero.properties.ff.getStart().toString(),
                hero.properties.ff.getMod().toString(),
                hero.properties.ff.getCurently().toString(),
                hero.properties.ff.getMax().toString()));
        propData.add(new TableRowClass("Gewandtheit", hero.properties.ge.getStart().toString(),
                hero.properties.ge.getMod().toString(),
                hero.properties.ge.getCurently().toString(),
                hero.properties.ge.getMax().toString()));
        propData.add(new TableRowClass("Konstitution", hero.properties.ko.getStart().toString(),
                hero.properties.ko.getMod().toString(),
                hero.properties.ko.getCurently().toString(),
                hero.properties.ko.getMax().toString()));
        propData.add(new TableRowClass("Körperkraft", hero.properties.kk.getStart().toString(),
                hero.properties.kk.getMod().toString(),
                hero.properties.kk.getCurently().toString(),
                hero.properties.kk.getMax().toString()));
        propData.add(new TableRowClass("Geschwindigkeit", hero.properties.gs.getStart().toString(),
                hero.properties.gs.getMod().toString(),
                hero.properties.gs.getCurently().toString(),
                hero.properties.gs.getMax().toString()));

        propData.add(new TableRowClass("", "", "", "", ""));
        propData.add(new TableRowClass("Lebensenergie",
                hero.vitals.le.getStart().toString(),
                hero.vitals.le.getMod().toString(),
                hero.vitals.le.getCurrent().toString(),""));
        propData.add(new TableRowClass("Ausdauer",
                hero.vitals.au.getStart().toString(),
                hero.vitals.au.getMod().toString(),
                hero.vitals.au.getCurrent().toString(),""));
        propData.add(new TableRowClass("Astralenergie",
                hero.vitals.ae.getStart().toString(),
                hero.vitals.ae.getMod().toString(),
                hero.vitals.ae.getCurrent().toString(),""));
        propData.add(new TableRowClass("Karmaenergie",
                hero.vitals.ke.getStart().toString(),
                hero.vitals.ke.getMod().toString(),
                hero.vitals.ke.getCurrent().toString(),""));
        propData.add(new TableRowClass("Magieresistenz",
                hero.vitals.mr.getStart().toString(),
                hero.vitals.mr.getMod().toString(),
                hero.vitals.mr.getCurrent().toString(),""));
        propData.add(new TableRowClass("INI-Basiswert",
                hero.vitals.ini.getStart().toString(),
                hero.vitals.ini.getMod().toString(),
                hero.vitals.ini.getCurrent().toString(),""));
        propData.add(new TableRowClass("AT-Basiswert",
                hero.vitals.at.getStart().toString(),
                hero.vitals.at.getMod().toString(),
                hero.vitals.at.getCurrent().toString(),""));
        propData.add(new TableRowClass("PA-Basiswert",
                hero.vitals.pa.getStart().toString(),
                hero.vitals.pa.getMod().toString(),
                hero.vitals.pa.getCurrent().toString(),""));
        propData.add(new TableRowClass("FK-Basiswert",
                hero.vitals.fk.getStart().toString(),
                hero.vitals.fk.getMod().toString(),
                hero.vitals.fk.getCurrent().toString(),""));
        propData.add(new TableRowClass("Wundschwelle",
                hero.vitals.ws.getStart().toString(),
                hero.vitals.ws.getMod().toString(),
                hero.vitals.ws.getCurrent().toString(),""));
        propTable.setItems(propData);
        propTable.setEditable(false);

        talentData.add(new TableRowClass("Kampf", "", "", "", ""));
        for (FightingTalent ft : hero.getTalents().fightingTalents){
            if (ft.getNecessaryAT()) {
                if (ft.getNecessaryPA()) {
                    talentData.add(new TableRowClass(ft.getName(), "", "AT: " + ft.getSkilledAT().toString(), "PA: " + ft.getSkilledPA().toString(), ""));
                } else if (ft.getNecessaryFK()) {
                    talentData.add(new TableRowClass(ft.getName(), "", "AT: " + ft.getSkilledAT().toString(), "", "FK: " + ft.getSkilledFK().toString()));
                } else {
                    talentData.add(new TableRowClass(ft.getName(), "", "AT: " + ft.getSkilledAT().toString(), "", ""));
                }
            } else if (ft.getNecessaryPA()) {
                if (ft.getNecessaryFK()) {
                    talentData.add(new TableRowClass(ft.getName(), "", "", "PA: " + ft.getSkilledPA().toString(), "FK: " + ft.getSkilledFK().toString()));
                } else {
                    talentData.add(new TableRowClass(ft.getName(), "", "", "PA: " + ft.getSkilledPA().toString(), ""));
                }
            } else if (ft.getNecessaryFK()) {
                talentData.add(new TableRowClass(ft.getName(), "", "", "", "FK: " + ft.getSkilledFK().toString()));
            }
            fight.add(ft.getName());
        }
        talentData.add(new TableRowClass("Ritual", "", "", "", ""));
        for (Talent t : hero.getTalents().ritualTalents){
            talentData.add(new TableRowClass(t.getName(), t.getSkilled().toString(),
                    Property.convertToShort(t.getProp1()),
                    Property.convertToShort(t.getProp2()),
                    Property.convertToShort(t.getProp3())));
            ritual.add(t.getName());
        }
        talentData.add(new TableRowClass("Körperlich", "", "", "", ""));
        for (Talent t : hero.getTalents().physicalTalents){
            talentData.add(new TableRowClass(t.getName(), t.getSkilled().toString(),
                    Property.convertToShort(t.getProp1()),
                    Property.convertToShort(t.getProp2()),
                    Property.convertToShort(t.getProp3())));
            physical.add(t.getName());
        }
        talentData.add(new TableRowClass("Gesellschaftlich", "", "", "", ""));
        for (Talent t : hero.getTalents().societyTalents){
            talentData.add(new TableRowClass(t.getName(), t.getSkilled().toString(),
                    Property.convertToShort(t.getProp1()),
                    Property.convertToShort(t.getProp2()),
                    Property.convertToShort(t.getProp3())));
            society.add(t.getName());
        }
        talentData.add(new TableRowClass("Natur", "", "", "", ""));
        for (Talent t : hero.getTalents().natureTalents){
            talentData.add(new TableRowClass(t.getName(), t.getSkilled().toString(),
                    Property.convertToShort(t.getProp1()),
                    Property.convertToShort(t.getProp2()),
                    Property.convertToShort(t.getProp3())));
            nature.add(t.getName());
        }
        talentData.add(new TableRowClass("Wissen", "", "", "", ""));
        for (Talent t : hero.getTalents().knowledgeTalents){
            talentData.add(new TableRowClass(t.getName(), t.getSkilled().toString(),
                    Property.convertToShort(t.getProp1()),
                    Property.convertToShort(t.getProp2()),
                    Property.convertToShort(t.getProp3())));
            knowledge.add(t.getName());
        }
        talentData.add(new TableRowClass("Handwerk", "", "", "", ""));
        for (Talent t : hero.getTalents().craftingTalents){
            talentData.add(new TableRowClass(t.getName(), t.getSkilled().toString(),
                    Property.convertToShort(t.getProp1()),
                    Property.convertToShort(t.getProp2()),
                    Property.convertToShort(t.getProp3())));
            crafting.add(t.getName());
        }
        talentData.add(new TableRowClass("Sprachen/Schriften", "", "", "", ""));
        for (Talent t : hero.getTalents().languagesAndWritingTalents){
            talentData.add(new TableRowClass(t.getName(), t.getSkilled().toString(),
                    Property.convertToShort(t.getProp1()),
                    Property.convertToShort(t.getProp2()),
                    Property.convertToShort(t.getProp3())));
            language.add(t.getName());
        }
        
        talentTable.setItems(talentData);
        talentTable.setEditable(false);

        talentDropDown.getItems().clear();
        talentDropDown.getItems().addAll(fight);

        talentGroupDropDown.getSelectionModel().select(0);
        //talentDropDown.getSelectionModel().select(0);
        //editTalentDropDown();

        return hero;
    }

    void displayHero(int id){
        hero = loadHero(id);
    }

    public void editName(){
        dbOverwatch.execute("UPDATE HEROS SET NAME='" + nameText.getText() + "' WHERE ID=" + heroId);
        hero.setName(nameText.getText());
    }

    public void editAge(){
        if (isNumber(ageText.getText())){
            dbOverwatch.execute("UPDATE HEROS SET AGE=" + Integer.parseInt(ageText.getText()) + " WHERE ID=" + heroId);
            hero.setAge(Integer.parseInt(ageText.getText()));
        }else{
            ageText.setText(hero.getAge().toString());
        }
    }

    public void editBirthday(){
        dbOverwatch.execute("UPDATE HEROS SET BIRTHDAY='" + birthdayText.getText() + "' WHERE ID=" + heroId);
        hero.setBirthdate(birthdayText.getText());
    }

    public void editWeight(){
        if(isNumber(weightText.getText())){
            dbOverwatch.execute("UPDATE HEROS SET WEIGHT=" + weightText.getText() + " WHERE ID=" + heroId);
            hero.setWeight(Integer.parseInt(weightText.getText()));
        }else{
            weightText.setText(hero.getWeight().toString());
        }
    }

    public void editHeight(){
        if(isNumber(heightText.getText())){
            dbOverwatch.execute("UPDATE HEROS SET HEIGHT=" + heightText.getText() + " WHERE ID=" + heroId);
            hero.setHeight(Integer.parseInt(heightText.getText()));
        }else{
            heightText.setText(hero.getHeight().toString());
        }
    }

    public void editEyeColor(){
        dbOverwatch.execute("UPDATE HEROS SET EYECOLOR='" + eyecolorText.getText() + "' WHERE ID=" + heroId);
        hero.setBirthdate(eyecolorText.getText());
    }

    public void editHaircolor(){
        dbOverwatch.execute("UPDATE HEROS SET HAIRCOLOR='" + haircolorText.getText() + "' WHERE ID=" + heroId);
        hero.setHair_color(haircolorText.getText());
    }

    public void editProfession(){
        dbOverwatch.execute("UPDATE HEROS SET PROFESSION='" + profText.getText() + "' WHERE ID=" + heroId);
        hero.setProfession(profText.getText());
    }

    public void editScndProfession(){
        dbOverwatch.execute("UPDATE HEROS SET SCND_PROFESSION='" + scndprofText.getText() + "' WHERE ID=" + heroId);
        hero.setSecondProfession(scndprofText.getText());
    }

    public void editTitle(){
        dbOverwatch.execute("UPDATE HEROS SET TITLE='" + titleText.getText() + "' WHERE ID=" + heroId);
        hero.setTitle(titleText.getText());
    }

    public void editSocial(){
        if(isNumber(socialText.getText())){
            dbOverwatch.execute("UPDATE HEROS SET SOCIAL=" + socialText.getText() + " WHERE ID=" + heroId);
            hero.setSocial(Integer.parseInt(socialText.getText()));
        }else{
            socialText.setText(hero.getSocial().toString());
        }
    }

    public void editRace(){
        hero.setRace(raceDropDown.getSelectionModel().getSelectedItem());
        ResultSet resultSet = dbOverwatch.executeSQL("SELECT * FROM RACES WHERE NAME='" + hero.getRace() + "'");
        try {
            if(resultSet.next()){
                dbOverwatch.execute("UPDATE HEROS SET RACE=" + resultSet.getInt(1) + " WHERE ID=" + heroId);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void editCulture(){
        hero.setCulture(cultureDropDown.getSelectionModel().getSelectedItem());
        ResultSet resultSet = dbOverwatch.executeSQL("SELECT * FROM CULTURE WHERE NAME='" + hero.getCulture() + "'");
        try {
            if(resultSet.next()){
                dbOverwatch.execute("UPDATE HEROS SET CULTURE=" + resultSet.getInt(1) + " WHERE ID=" + heroId);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void editPropTable(){
        int row = 0;
        int col = 0;
        String prop = "";
        String spec = "";
        String t;
        String s = editProps.getText();

        infoText.setText("");
        switch (specDropDown.getSelectionModel().getSelectedItem()){
            case "Start":
                col = 1;
                spec = "START";
                break;
            case "Mod":
                col = 2;
                spec = "MOD";
                break;
            case "Aktuell":
                col = 3;
                spec = "CURRENT";
                break;
            case"Max":
                col = 4;
                spec = "MAX";
                break;
        }
        switch (propDropDown.getSelectionModel().getSelectedItem()) {
            case "Mut":
                row = 1;
                prop = "MU";
                break;
            case "Klugheit":
                row = 2;
                prop = "KL";
                break;
            case "Intuition":
                row = 3;
                prop = "IN";
                break;
            case "Charisma":
                row = 4;
                prop = "CH";
                break;
            case "Fingerfertigkeit":
                row = 5;
                prop = "FF";
                break;
            case"Gewandtheit":
                row = 6;
                prop = "GE";
                break;
            case "Konstitution":
                row = 7;
                prop = "KO";
                break;
            case "Körperkraft":
                row = 8;
                prop = "KK";
                break;
            case "Geschwindigkeit":
                row = 9;
                prop = "GS";
                break;
            case "Lebenenergie":
                row = 11;
                prop = "LE";
                break;
            case "Ausdauer":
                row = 12;
                prop = "AU";
                break;
            case "Astralenergie":
                row = 13;
                prop = "AE";
                break;
            case "Karmaenergie":
                row = 14;
                prop = "KE";
                break;
            case "Magieresistenz":
                row = 15;
                prop = "MR";
                break;
            case "INI-Basiswert":
                row = 16;
                prop = "INI";
                break;
            case "AT-Basiswert":
                row = 17;
                prop = "AT";
                break;
            case "PA-Basiswert":
                row = 18;
                prop = "PA";
                break;
            case"FK-Basiswert":
                row = 19;
                prop = "FK";
                break;
            case "Wundschwelle":
                row = 20;
                prop = "WS";
                break;
        }
        if (row < 10){
            t = "PROPERTIES";
        }else {
            t = "VITALS";
        }

        if (!isNumber(s)){
            editProps.setText("");
            infoText.setText("Bitte nur Zahlen eintragen");
        }else{
            if (row < 10){
                //if it is a property
                switch (col){
                    case 1:
                        hero.properties.setStart(Property.convertFromShort(prop), Integer.parseInt(editProps.getText()));
                        break;
                    case 2:
                        hero.properties.setMod(Property.convertFromShort(prop), Integer.parseInt(editProps.getText()));
                        break;
                    case 3:
                        hero.properties.setCurrently(Property.convertFromShort(prop), Integer.parseInt(editProps.getText()));
                        break;
                    case 4:
                        hero.properties.setMax(Property.convertFromShort(prop), Integer.parseInt(editProps.getText()));
                        break;
                }
            }else{
                //if it is a vital
                switch (col){
                    case 1:
                        hero.vitals.fromShort(prop).setStart(Integer.parseInt(editProps.getText()));
                        break;
                    case 2:
                        hero.vitals.fromShort(prop).setMod(Integer.parseInt(editProps.getText()));
                        break;
                    case 3:
                        hero.vitals.fromShort(prop).setCurrent(Integer.parseInt(editProps.getText()));
                        break;
                    case 4:
                        infoText.setText("Kein Max-Wert möglich");
                        break;
                }
            }
            dbOverwatch.execute("UPDATE " + t + " SET " + prop + "_" + spec + "=" + editProps.getText() +
                    " WHERE ID=" + heroId);
            switch (col){
                case 1:
                    propTable.getItems().get(row).setC2(editProps.getText());
                    break;
                case 2:
                    propTable.getItems().get(row).setC3(editProps.getText());
                    break;
                case 3:
                    propTable.getItems().get(row).setC4(editProps.getText());
                    break;
                case 4:
                    propTable.getItems().get(row).setC5(editProps.getText());
                    break;
            }
        }
    }

    public void editTalentGroupDropDown(){
        switch (talentGroupDropDown.getSelectionModel().getSelectedItem()){
            case "Kampf":
                talentDropDown.getItems().clear();
                talentDropDown.getItems().addAll(fight);
                editTalents.setVisible(false);
                infoATText.setVisible(false);
                infoPAText.setVisible(false);
                infoFKText.setVisible(false);
                editAT.setVisible(false);
                editPA.setVisible(false);
                editFK.setVisible(false);
                break;
            case "Ritual":
                talentDropDown.getItems().clear();
                talentDropDown.getItems().addAll(ritual);
                infoATText.setVisible(false);
                infoPAText.setVisible(false);
                infoFKText.setVisible(false);
                editAT.setVisible(false);
                editPA.setVisible(false);
                editFK.setVisible(false);
                break;
            case "Körperlich":
                talentDropDown.getItems().clear();
                talentDropDown.getItems().addAll(physical);
                infoATText.setVisible(false);
                infoPAText.setVisible(false);
                infoFKText.setVisible(false);
                editAT.setVisible(false);
                editPA.setVisible(false);
                editFK.setVisible(false);
                break;
            case "Gesellschaftlich":
                talentDropDown.getItems().clear();
                talentDropDown.getItems().addAll(society);
                infoATText.setVisible(false);
                infoPAText.setVisible(false);
                infoFKText.setVisible(false);
                editAT.setVisible(false);
                editPA.setVisible(false);
                editFK.setVisible(false);
                break;
            case "Natur":
                talentDropDown.getItems().clear();
                talentDropDown.getItems().addAll(nature);
                infoATText.setVisible(false);
                infoPAText.setVisible(false);
                infoFKText.setVisible(false);
                editAT.setVisible(false);
                editPA.setVisible(false);
                editFK.setVisible(false);
                break;
            case "Wissen":
                talentDropDown.getItems().clear();
                talentDropDown.getItems().addAll(knowledge);
                infoATText.setVisible(false);
                infoPAText.setVisible(false);
                infoFKText.setVisible(false);
                editAT.setVisible(false);
                editPA.setVisible(false);
                editFK.setVisible(false);
                break;
            case "Handwerk":
                talentDropDown.getItems().clear();
                talentDropDown.getItems().addAll(crafting);
                infoATText.setVisible(false);
                infoPAText.setVisible(false);
                infoFKText.setVisible(false);
                editAT.setVisible(false);
                editPA.setVisible(false);
                editFK.setVisible(false);
                break;
            case "Sprachen und Schriften":
                talentDropDown.getItems().clear();
                talentDropDown.getItems().addAll(language);
                infoATText.setVisible(false);
                infoPAText.setVisible(false);
                infoFKText.setVisible(false);
                editAT.setVisible(false);
                editPA.setVisible(false);
                editFK.setVisible(false);
                break;
        }
        talentDropDown.getSelectionModel().select(0);
    }

    public void editTalentDropDown(){
        infoValueText.setVisible(false);
        editTalents.setVisible(false);
        infoATText.setVisible(false);
        infoPAText.setVisible(false);
        infoFKText.setVisible(false);
        editAT.setVisible(false);
        editPA.setVisible(false);
        editFK.setVisible(false);
        //System.out.println(talentGroupDropDown.getSelectionModel().getSelectedItem());
        switch (talentGroupDropDown.getSelectionModel().getSelectedItem()){
            case "Kampf":
                editTalents.setVisible(false);
                infoValueText.setVisible(false);
                for (FightingTalent t:hero.talents.fightingTalents) {
                    //System.out.println(t.getName());
                    if (t.getName().equals(talentDropDown.getSelectionModel().getSelectedItem())){
                        if (t.getNecessaryAT()){
                            editAT.setText(t.getSkilledAT().toString());
                            editAT.setVisible(true);
                            infoATText.setVisible(true);
                        }
                        if (t.getNecessaryPA()) {
                            editPA.setText(t.getSkilledPA().toString());
                            editPA.setVisible(true);
                            infoPAText.setVisible(true);
                        }
                        if (t.getNecessaryFK()) {
                            editFK.setText(t.getSkilledFK().toString());
                            editFK.setVisible(true);
                            infoFKText.setVisible(true);
                            //System.out.println("Loading talent");
                        }
                        break;
                    }
                }
                break;
            case "Ritual":
                editTalents.setVisible(true);
                infoValueText.setVisible(true);
                for (Talent t:hero.talents.ritualTalents) {
                    if (t.getName().equals(talentDropDown.getSelectionModel().getSelectedItem())){
                        editTalents.setText(t.getSkilled().toString());
                    }
                }
                break;
            case "Körperlich":
                editTalents.setVisible(true);
                infoValueText.setVisible(true);
                for (Talent t:hero.talents.physicalTalents) {
                    if (t.getName().equals(talentDropDown.getSelectionModel().getSelectedItem())){
                        editTalents.setText(t.getSkilled().toString());
                    }
                }
                break;
            case "Gesellschaftlich":
                editTalents.setVisible(true);
                infoValueText.setVisible(true);
                for (Talent t:hero.talents.societyTalents) {
                    if (t.getName().equals(talentDropDown.getSelectionModel().getSelectedItem())){
                        editTalents.setText(t.getSkilled().toString());
                    }
                }
                break;
            case "Natur":
                editTalents.setVisible(true);
                infoValueText.setVisible(true);
                for (Talent t:hero.talents.natureTalents) {
                    if (t.getName().equals(talentDropDown.getSelectionModel().getSelectedItem())){
                        editTalents.setText(t.getSkilled().toString());
                    }
                }
                break;
            case "Wissen":
                editTalents.setVisible(true);
                infoValueText.setVisible(true);
                for (Talent t:hero.talents.knowledgeTalents) {
                    if (t.getName().equals(talentDropDown.getSelectionModel().getSelectedItem())){
                        editTalents.setText(t.getSkilled().toString());
                    }
                }
                break;
            case "Handwerk":
                editTalents.setVisible(true);
                infoValueText.setVisible(true);
                for (Talent t:hero.talents.craftingTalents) {
                    if (t.getName().equals(talentDropDown.getSelectionModel().getSelectedItem())){
                        editTalents.setText(t.getSkilled().toString());
                    }
                }
                break;
            case "Sprachen und Schriften":
                editTalents.setVisible(true);
                infoValueText.setVisible(true);
                for (Talent t:hero.talents.languagesAndWritingTalents) {
                    if (t.getName().equals(talentDropDown.getSelectionModel().getSelectedItem())){
                        editTalents.setText(t.getSkilled().toString());
                    }
                }
                break;
        }
    }

    public void editTalentTable(){
        boolean editable = true;

        if (editTalents.isVisible())
            editable = isNumber(editTalents.getText());
        if (editAT.isVisible())
            editable = editable && isNumber(editAT.getText());
        if (editPA.isVisible())
            editable = editable && isNumber(editPA.getText());
        if (editFK.isVisible())
            editable = editable && isNumber(editFK.getText());

        if (editable){
            System.out.println("editTable");
            switch (talentGroupDropDown.getSelectionModel().getSelectedItem()){
                case "Kampf":
                    int tAT = 0;
                    int tPA = 0;
                    int tFK = 0;
                    if (editAT.isVisible())
                        tAT = Integer.parseInt(editAT.getText());
                    if (editPA.isVisible())
                        tPA = Integer.parseInt(editPA.getText());
                    if (editFK.isVisible())
                        tFK = Integer.parseInt(editFK.getText());
                    dbOverwatch.editTalent("FIGHT", talentDropDown.getSelectionModel().getSelectedItem(),
                            tAT, tPA, tFK, heroId);
                    for (FightingTalent t:hero.talents.fightingTalents) {
                        //System.out.println(t.getName());
                        if (t.getName().equals(talentDropDown.getSelectionModel().getSelectedItem())){
                            t.setSkilledAT(tAT);
                            t.setSkilledPA(tPA);
                            t.setSkilledFK(tFK);
                            break;
                        }
                    }
                    break;
                case "Ritual":
                    dbOverwatch.editTalent("RITUALS", talentDropDown.getSelectionModel().getSelectedItem(),
                            Integer.parseInt(editTalents.getText()), heroId);
                    for (Talent t:hero.talents.ritualTalents) {
                        //System.out.println(t.getName());
                        if (t.getName().equals(talentDropDown.getSelectionModel().getSelectedItem())){
                            t.setSkilled(Integer.parseInt(editTalents.getText()));
                            break;
                        }
                    }
                    break;
                case "Körperlich":
                    dbOverwatch.editTalent("PHYSICAL", talentDropDown.getSelectionModel().getSelectedItem(),
                            Integer.parseInt(editTalents.getText()), heroId);
                    for (Talent t:hero.talents.physicalTalents) {
                        //System.out.println(t.getName());
                        if (t.getName().equals(talentDropDown.getSelectionModel().getSelectedItem())){
                            t.setSkilled(Integer.parseInt(editTalents.getText()));
                            break;
                        }
                    }
                    break;
                case "Gesellschaftlich":
                    dbOverwatch.editTalent("SOCIETY", talentDropDown.getSelectionModel().getSelectedItem(),
                            Integer.parseInt(editTalents.getText()), heroId);
                    for (Talent t:hero.talents.societyTalents) {
                        //System.out.println(t.getName());
                        if (t.getName().equals(talentDropDown.getSelectionModel().getSelectedItem())){
                            t.setSkilled(Integer.parseInt(editTalents.getText()));
                            break;
                        }
                    }
                    break;
                case "Natur":
                    dbOverwatch.editTalent("NATURE", talentDropDown.getSelectionModel().getSelectedItem(),
                            Integer.parseInt(editTalents.getText()), heroId);
                    for (Talent t:hero.talents.natureTalents) {
                        //System.out.println(t.getName());
                        if (t.getName().equals(talentDropDown.getSelectionModel().getSelectedItem())){
                            t.setSkilled(Integer.parseInt(editTalents.getText()));
                            break;
                        }
                    }
                    break;
                case "Wissen":
                    dbOverwatch.editTalent("KNOWLEDGE", talentDropDown.getSelectionModel().getSelectedItem(),
                            Integer.parseInt(editTalents.getText()), heroId);
                    for (Talent t:hero.talents.knowledgeTalents) {
                        //System.out.println(t.getName());
                        if (t.getName().equals(talentDropDown.getSelectionModel().getSelectedItem())){
                            t.setSkilled(Integer.parseInt(editTalents.getText()));
                            break;
                        }
                    }
                    break;
                case "Handwerk":
                    dbOverwatch.editTalent("CRAFTING", talentDropDown.getSelectionModel().getSelectedItem(),
                            Integer.parseInt(editTalents.getText()), heroId);
                    for (Talent t:hero.talents.craftingTalents) {
                        //System.out.println(t.getName());
                        if (t.getName().equals(talentDropDown.getSelectionModel().getSelectedItem())){
                            t.setSkilled(Integer.parseInt(editTalents.getText()));
                            break;
                        }
                    }
                    break;
                case "Sprachen und Schriften":
                    dbOverwatch.editTalent("LANGUAGE", talentDropDown.getSelectionModel().getSelectedItem(),
                            Integer.parseInt(editTalents.getText()), heroId);
                    for (Talent t:hero.talents.languagesAndWritingTalents) {
                        //System.out.println(t.getName());
                        if (t.getName().equals(talentDropDown.getSelectionModel().getSelectedItem())){
                            t.setSkilled(Integer.parseInt(editTalents.getText()));
                            break;
                        }
                    }
                    break;
            }
            for (int i = 0; i < talentTable.getItems().size(); i++) {
                if (talentTable.getItems().get(i).getC1().equals(talentDropDown.getSelectionModel().getSelectedItem())){
                    System.out.println("Found in line " + i);
                    if (talentGroupDropDown.getSelectionModel().getSelectedItem().equals("Kampf")){
                        if (!talentTable.getItems().get(i).getC3().equals(""))
                        talentTable.getItems().get(i).setC3("AT: " + editAT.getText());
                        if (!talentTable.getItems().get(i).getC4().equals(""))
                        talentTable.getItems().get(i).setC4("PA: " + editPA.getText());
                        if (!talentTable.getItems().get(i).getC5().equals(""))
                        talentTable.getItems().get(i).setC5("FK: " + editFK.getText());
                    }else{
                        talentTable.getItems().get(i).setC2(editTalents.getText());
                    }
                    break;
                }
            }
        }
    }

    private boolean isNumber(String s){
        try {
            Double.parseDouble(s);
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }
}
