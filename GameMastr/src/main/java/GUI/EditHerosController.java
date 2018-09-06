package GUI;

import Database.DBOverwatch;
import Hero.Property;
import Hero.ResolvedHero;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.sql.ResultSet;
import java.sql.SQLException;

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
    private TableView<TableRowClass> table;
    @FXML
    private ComboBox<String> propDropDown;
    @FXML
    private ComboBox<String> specDropDown;
    @FXML
    private TextField editProps;
    @FXML
    private Text infoText;

    //private ArrayList<MenuItem> items = new ArrayList<MenuItem>();

    private int heroId = -1;
    private DBOverwatch dbOverwantch = DBOverwatch.instance;
    private ResolvedHero hero = new ResolvedHero();
    private String[] spec = {"Start", "Mod", "Aktuell", "Max"};
    private String[] prop = {"Mut", "Klugheit", "Intuition", "Karisma", "Fingerfertigkeit", "Gewandtheit",
            "Konstitution", "Körperkraft", "Geschwindigkeit", "Lebenenergie", "Ausdauer", "Astralenergie",
            "Karmaenergie", "Magieresistenz", "INI-Basiswert", "AT-Basiswert", "PA-Basiswert", "FK-Basiswert",
            "Wundschwelle"};

    public void initialize(){
        cultureDropDown.getItems().addAll(dbOverwantch.loadCultures());
        raceDropDown.getItems().addAll(dbOverwantch.loadRaces());
        specDropDown.getItems().addAll(spec);
        specDropDown.getSelectionModel().select(0);
        propDropDown.getItems().addAll(prop);
        propDropDown.getSelectionModel().select(0);
        menu.getItems().clear();
        for (int i = 0; i < dbOverwantch.heros.size(); i++) {
            ResolvedHero hero = dbOverwantch.heros.get(i);
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
        ResolvedHero hero = dbOverwantch.heros.get(id-1);
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

        final ObservableList<TableRowClass> data = FXCollections.observableArrayList();
        TableColumn col1 = table.getColumns().get(0);
        TableColumn col2 = table.getColumns().get(1);
        TableColumn col3 = table.getColumns().get(2);
        TableColumn col4 = table.getColumns().get(3);
        TableColumn col5 = table.getColumns().get(4);
        col1.setCellValueFactory(new PropertyValueFactory<TableRowClass, String>("c1"));
        col2.setCellValueFactory(new PropertyValueFactory<TableRowClass, String>("c2"));
        col3.setCellValueFactory(new PropertyValueFactory<TableRowClass, String>("c3"));
        col4.setCellValueFactory(new PropertyValueFactory<TableRowClass, String>("c4"));
        col5.setCellValueFactory(new PropertyValueFactory<TableRowClass, String>("c5"));
        data.add(new TableRowClass("Name", "Start", "Mod", "Aktuell", "Max"));
        data.add(new TableRowClass("Mut", hero.properties.mu.getStart().toString(),
                hero.properties.mu.getMod().toString(),
                hero.properties.mu.getCurently().toString(),
                hero.properties.mu.getMax().toString()));
        data.add(new TableRowClass("Klugheit", hero.properties.kl.getStart().toString(),
                hero.properties.kl.getMod().toString(),
                hero.properties.kl.getCurently().toString(),
                hero.properties.kl.getMax().toString()));
        data.add(new TableRowClass("Intuition", hero.properties.in.getStart().toString(),
                hero.properties.in.getMod().toString(),
                hero.properties.in.getCurently().toString(),
                hero.properties.in.getMax().toString()));
        data.add(new TableRowClass("Charisma", hero.properties.ch.getStart().toString(),
                hero.properties.ch.getMod().toString(),
                hero.properties.ch.getCurently().toString(),
                hero.properties.ch.getMax().toString()));
        data.add(new TableRowClass("Fingerfertigkeit", hero.properties.ff.getStart().toString(),
                hero.properties.ff.getMod().toString(),
                hero.properties.ff.getCurently().toString(),
                hero.properties.ff.getMax().toString()));
        data.add(new TableRowClass("Gewandtheit", hero.properties.ge.getStart().toString(),
                hero.properties.ge.getMod().toString(),
                hero.properties.ge.getCurently().toString(),
                hero.properties.ge.getMax().toString()));
        data.add(new TableRowClass("Konstitution", hero.properties.ko.getStart().toString(),
                hero.properties.ko.getMod().toString(),
                hero.properties.ko.getCurently().toString(),
                hero.properties.ko.getMax().toString()));
        data.add(new TableRowClass("Körperkraft", hero.properties.kk.getStart().toString(),
                hero.properties.kk.getMod().toString(),
                hero.properties.kk.getCurently().toString(),
                hero.properties.kk.getMax().toString()));
        data.add(new TableRowClass("Geschwindigkeit", hero.properties.gs.getStart().toString(),
                hero.properties.gs.getMod().toString(),
                hero.properties.gs.getCurently().toString(),
                hero.properties.gs.getMax().toString()));

        data.add(new TableRowClass("", "", "", "", ""));
        data.add(new TableRowClass("Lebensenergie",
                hero.vitals.le.getStart().toString(),
                hero.vitals.le.getMod().toString(),
                hero.vitals.le.getCurrent().toString(),""));
        data.add(new TableRowClass("Ausdauer",
                hero.vitals.au.getStart().toString(),
                hero.vitals.au.getMod().toString(),
                hero.vitals.au.getCurrent().toString(),""));
        data.add(new TableRowClass("Astralenergie",
                hero.vitals.ae.getStart().toString(),
                hero.vitals.ae.getMod().toString(),
                hero.vitals.ae.getCurrent().toString(),""));
        data.add(new TableRowClass("Karmaenergie",
                hero.vitals.ke.getStart().toString(),
                hero.vitals.ke.getMod().toString(),
                hero.vitals.ke.getCurrent().toString(),""));
        data.add(new TableRowClass("Magieresistenz",
                hero.vitals.mr.getStart().toString(),
                hero.vitals.mr.getMod().toString(),
                hero.vitals.mr.getCurrent().toString(),""));
        data.add(new TableRowClass("INI-Basiswert",
                hero.vitals.ini.getStart().toString(),
                hero.vitals.ini.getMod().toString(),
                hero.vitals.ini.getCurrent().toString(),""));
        data.add(new TableRowClass("AT-Basiswert",
                hero.vitals.at.getStart().toString(),
                hero.vitals.at.getMod().toString(),
                hero.vitals.at.getCurrent().toString(),""));
        data.add(new TableRowClass("PA-Basiswert",
                hero.vitals.pa.getStart().toString(),
                hero.vitals.pa.getMod().toString(),
                hero.vitals.pa.getCurrent().toString(),""));
        data.add(new TableRowClass("FK-Basiswert",
                hero.vitals.fk.getStart().toString(),
                hero.vitals.fk.getMod().toString(),
                hero.vitals.fk.getCurrent().toString(),""));
        data.add(new TableRowClass("Wundschwelle",
                hero.vitals.ws.getStart().toString(),
                hero.vitals.ws.getMod().toString(),
                hero.vitals.ws.getCurrent().toString(),""));
        table.setItems(data);
        table.setEditable(false);
        return hero;
    }

    void displayHero(int id){
        hero = loadHero(id);
    }

    public void editName(){
        dbOverwantch.execute("UPDATE HEROS SET NAME='" + nameText.getText() + "' WHERE ID=" + heroId);
        hero.setName(nameText.getText());
    }

    public void editAge(){
        if (isNumber(ageText.getText())){
            dbOverwantch.execute("UPDATE HEROS SET AGE=" + Integer.parseInt(ageText.getText()) + " WHERE ID=" + heroId);
            hero.setAge(Integer.parseInt(ageText.getText()));
        }else{
            ageText.setText(hero.getAge().toString());
        }
    }

    public void editBirthday(){
        dbOverwantch.execute("UPDATE HEROS SET BIRTHDAY='" + birthdayText.getText() + "' WHERE ID=" + heroId);
        hero.setBirthdate(birthdayText.getText());
    }

    public void editWeight(){
        if(isNumber(weightText.getText())){
            dbOverwantch.execute("UPDATE HEROS SET WEIGHT=" + weightText.getText() + " WHERE ID=" + heroId);
            hero.setWeight(Integer.parseInt(weightText.getText()));
        }else{
            weightText.setText(hero.getWeight().toString());
        }
    }

    public void editHeight(){
        if(isNumber(heightText.getText())){
            dbOverwantch.execute("UPDATE HEROS SET HEIGHT=" + heightText.getText() + " WHERE ID=" + heroId);
            hero.setHeight(Integer.parseInt(heightText.getText()));
        }else{
            heightText.setText(hero.getHeight().toString());
        }
    }

    public void editEyeColor(){
        dbOverwantch.execute("UPDATE HEROS SET EYECOLOR='" + eyecolorText.getText() + "' WHERE ID=" + heroId);
        hero.setBirthdate(eyecolorText.getText());
    }

    public void editHaircolor(){
        dbOverwantch.execute("UPDATE HEROS SET HAIRCOLOR='" + haircolorText.getText() + "' WHERE ID=" + heroId);
        hero.setHair_color(haircolorText.getText());
    }

    public void editProfession(){
        dbOverwantch.execute("UPDATE HEROS SET PROFESSION='" + profText.getText() + "' WHERE ID=" + heroId);
        hero.setProfession(profText.getText());
    }

    public void editScndProfession(){
        dbOverwantch.execute("UPDATE HEROS SET SCND_PROFESSION='" + scndprofText.getText() + "' WHERE ID=" + heroId);
        hero.setSecondProfession(scndprofText.getText());
    }

    public void editTitle(){
        dbOverwantch.execute("UPDATE HEROS SET TITLE='" + titleText.getText() + "' WHERE ID=" + heroId);
        hero.setTitle(titleText.getText());
    }

    public void editSocial(){
        if(isNumber(socialText.getText())){
            dbOverwantch.execute("UPDATE HEROS SET SOCIAL=" + socialText.getText() + " WHERE ID=" + heroId);
            hero.setSocial(Integer.parseInt(socialText.getText()));
        }else{
            socialText.setText(hero.getSocial().toString());
        }
    }

    public void editRace(){
        hero.setRace(raceDropDown.getSelectionModel().getSelectedItem());
        ResultSet resultSet = dbOverwantch.executeSQL("SELECT * FROM RACES WHERE NAME='" + hero.getRace() + "'");
        try {
            if(resultSet.next()){
                dbOverwantch.execute("UPDATE HEROS SET RACE=" + resultSet.getInt(1) + " WHERE ID=" + heroId);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void editCulture(){
        hero.setCulture(cultureDropDown.getSelectionModel().getSelectedItem());
        ResultSet resultSet = dbOverwantch.executeSQL("SELECT * FROM CULTURE WHERE NAME='" + hero.getCulture() + "'");
        try {
            if(resultSet.next()){
                dbOverwantch.execute("UPDATE HEROS SET CULTURE=" + resultSet.getInt(1) + " WHERE ID=" + heroId);
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
            dbOverwantch.execute("UPDATE " + t + " SET " + prop + "_" + spec + "=" + editProps.getText() +
                    " WHERE ID=" + heroId);
            switch (col){
                case 1:
                    table.getItems().get(row).setC2(editProps.getText());
                    break;
                case 2:
                    table.getItems().get(row).setC3(editProps.getText());
                    break;
                case 3:
                    table.getItems().get(row).setC4(editProps.getText());
                    break;
                case 4:
                    table.getItems().get(row).setC5(editProps.getText());
                    break;
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
