package GUI;

import Database.DBOverwatch;
import Hero.FightingTalent;
import Hero.Property;
import Hero.ResolvedHero;
import Hero.Talent;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.WindowEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainController extends AnchorPane {
    @FXML
    private Text debugText;
    @FXML
    private Button debugButton;
    @FXML
    private MenuBar menuBar;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab tableTab;
    @FXML
    private TableView mainTable;
    @FXML
    private TableColumn c1;
    @FXML
    private ArrayList<Tab> tabs = new ArrayList<Tab>();
    @FXML
    private Tab ttab = new Tab();
    @FXML
    private ComboBox heroDropDown = new ComboBox();
    @FXML
    private Menu editMenu = new Menu();
    @FXML
    private Menu editSubMenu = new Menu();
    @FXML
    private ArrayList<MenuItem> editSubMenuItems = new ArrayList<>();

    private DBOverwatch dbOverwatch = DBOverwatch.instance;

    public void initialize(){
        heroDropDown.getItems().add("Alle Helden");
        loadAndDisplayAllHeros();
        //dbOverwatch.parseFightTalents("5/0A0P1;7/1A1P0;9/0A0P0;15/1A1P0;16/0A1P0;17/0A1P0;19/3A2P0;21/1A0P0;23/0A0P0;");
    }

    public void setText(String text){
        debugText.setText(text);
    }

    public Tab addNewTab(String name){
        Tab temp_tab = new Tab();
        temp_tab.setText(name);
        tabs.add(temp_tab);
        tabPane.getTabs().add(tabs.get(tabs.size()-1));
        return temp_tab;
    }

    public Tab newTab(String name){
        Tab temp_tab = new Tab();
        temp_tab.setText(name);
        tabs.add(temp_tab);
        return temp_tab;
    }

    public void setConn(String text){
        debugText.setText(text);
    }

    @FXML
    protected void SearchButton() {

    }

    private void loadAndDisplayAllHeros(){
        int heroCountInDatabase = dbOverwatch.getAmountOfRows("HEROS");
        //ResolvedHero tmpHero = new ResolvedHero();
        Tab tab = new Tab();
        debugText.setText(String.valueOf(heroCountInDatabase) + " Helden in der Datenbank");
        int currentTabsCount = tabPane.getTabs().size();
        if (currentTabsCount - 1 != heroCountInDatabase && currentTabsCount >= 1 && heroCountInDatabase >= 0){
            tabPane.getTabs().remove(1,currentTabsCount);
            tabs.clear();
            dbOverwatch.heros.clear();
            for (int i = 1; i<=heroCountInDatabase; i++){
                int currentId = i;
                ResolvedHero hero = dbOverwatch.getChar(i);
                MenuItem tmpItem = new MenuItem();
                tmpItem.setText(hero.getName());
                tmpItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        GameMastR.editHeros(currentId);
                    }
                });
                editSubMenuItems.add(tmpItem);
                ArrayList<TableColumn> columns1 = new ArrayList<TableColumn>();
                ArrayList<TableColumn> columns2 = new ArrayList<TableColumn>();
                TableView tableView1 = new TableView();
                TableView tableView2 = new TableView();
                AnchorPane anchorPane = new AnchorPane();
                //System.out.println(tabPane.getWidth());
                for(int j = 0; j<5;j++){
                    columns1.add(new TableColumn("" + j));
                    columns2.add(new TableColumn("" + j));
                }
                tableView1.getColumns().addAll(columns1);
                tableView2.getColumns().addAll(columns2);
                final ObservableList<TableRowClass> data = FXCollections.observableArrayList();
                TableColumn col1 = (TableColumn)tableView1.getColumns().get(0);
                TableColumn col2 = (TableColumn)tableView1.getColumns().get(1);
                TableColumn col3 = (TableColumn)tableView1.getColumns().get(2);
                TableColumn col4 = (TableColumn)tableView1.getColumns().get(3);
                TableColumn col5 = (TableColumn)tableView1.getColumns().get(4);
                col1.setCellValueFactory(new PropertyValueFactory<TableRowClass, String>("c1"));
                col2.setCellValueFactory(new PropertyValueFactory<TableRowClass, String>("c2"));
                col3.setCellValueFactory(new PropertyValueFactory<TableRowClass, String>("c3"));
                col4.setCellValueFactory(new PropertyValueFactory<TableRowClass, String>("c4"));
                col5.setCellValueFactory(new PropertyValueFactory<TableRowClass, String>("c5"));

                final ObservableList<TableRowClass> data1 = FXCollections.observableArrayList();
                TableColumn col11 = (TableColumn)tableView2.getColumns().get(0);
                TableColumn col12 = (TableColumn)tableView2.getColumns().get(1);
                TableColumn col13 = (TableColumn)tableView2.getColumns().get(2);
                TableColumn col14 = (TableColumn)tableView2.getColumns().get(3);
                TableColumn col15 = (TableColumn)tableView2.getColumns().get(4);
                col11.setCellValueFactory(new PropertyValueFactory<TableRowClass, String>("c1"));
                col12.setCellValueFactory(new PropertyValueFactory<TableRowClass, String>("c2"));
                col13.setCellValueFactory(new PropertyValueFactory<TableRowClass, String>("c3"));
                col14.setCellValueFactory(new PropertyValueFactory<TableRowClass, String>("c4"));
                col15.setCellValueFactory(new PropertyValueFactory<TableRowClass, String>("c5"));

                data.add(new TableRowClass("", "", "", "", ""));
                data.add(new TableRowClass("", "Name", hero.getName(), "", ""));
                data.add(new TableRowClass("","Rasse",hero.getRace(),"",""));
                data.add(new TableRowClass("","Kultur",hero.getCulture(),"",""));
                data.add(new TableRowClass("","Profession",hero.getProfession(),"",""));
                data.add(new TableRowClass("","Größe",hero.getHeight().toString(),"",""));
                data.add(new TableRowClass("","Gewicht",hero.getWeight().toString(),"",""));
                data.add(new TableRowClass("","Augenfarbe",hero.getEye_color(),"",""));
                data.add(new TableRowClass("","Haarfarbe",hero.getHair_color(),"",""));
                data.add(new TableRowClass("","Geburtstag",hero.getBirthdate(),"",""));
                data.add(new TableRowClass("","Alter",hero.getAge().toString(),"",""));
                data.add(new TableRowClass("","Titel",hero.getTitle(),"",""));
                data.add(new TableRowClass("","Sozialstatus",hero.getSocial().toString(),"",""));
                data.add(new TableRowClass("", "", "", "", ""));
                data.add(new TableRowClass("", "", "", "", ""));
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

                data1.add(new TableRowClass("Kampf", "", "", "", ""));
                for (FightingTalent ft : hero.getTalents().fightingTalents){
                    if (ft.getNecessaryAT()) {
                        if (ft.getNecessaryPA()) {
                            data1.add(new TableRowClass(ft.getName(), "", "AT: " + ft.getSkilledAT().toString(), "PA" + ft.getSkilledPA().toString(), ""));
                        } else if (ft.getNecessaryFK()) {
                            data1.add(new TableRowClass(ft.getName(), "", "AT: " + ft.getSkilledAT().toString(), "", "FK: " + ft.getSkilledFK().toString()));
                        } else {
                            data1.add(new TableRowClass(ft.getName(), "", "AT: " + ft.getSkilledAT().toString(), "", ""));
                        }
                    } else if (ft.getNecessaryPA()) {
                        if (ft.getNecessaryFK()) {
                            data1.add(new TableRowClass(ft.getName(), "", "", "PA: " + ft.getSkilledPA().toString(), "FK: " + ft.getSkilledFK().toString()));
                        } else {
                            data1.add(new TableRowClass(ft.getName(), "", "", "PA: " + ft.getSkilledPA().toString(), ""));
                        }
                    } else if (ft.getNecessaryFK()) {
                        data1.add(new TableRowClass(ft.getName(), "", "", "", "FK: " + ft.getSkilledFK().toString()));
                    }
                }
                data1.add(new TableRowClass("Ritual", "", "", "", ""));
                for (Talent t : hero.getTalents().ritualTalents){
                    data1.add(new TableRowClass(t.getName(), t.getSkilled().toString(),
                            Property.convertToShort(t.getProp1()),
                            Property.convertToShort(t.getProp2()),
                            Property.convertToShort(t.getProp3())));
                }
                data1.add(new TableRowClass("Körperlich", "", "", "", ""));
                for (Talent t : hero.getTalents().physicalTalents){
                    data1.add(new TableRowClass(t.getName(), t.getSkilled().toString(),
                            Property.convertToShort(t.getProp1()),
                            Property.convertToShort(t.getProp2()),
                            Property.convertToShort(t.getProp3())));
                }
                data1.add(new TableRowClass("Gesellschaftlich", "", "", "", ""));
                for (Talent t : hero.getTalents().societyTalents){
                    data1.add(new TableRowClass(t.getName(), t.getSkilled().toString(),
                            Property.convertToShort(t.getProp1()),
                            Property.convertToShort(t.getProp2()),
                            Property.convertToShort(t.getProp3())));
                }
                data1.add(new TableRowClass("Natur", "", "", "", ""));
                for (Talent t : hero.getTalents().natureTalents){
                    data1.add(new TableRowClass(t.getName(), t.getSkilled().toString(),
                            Property.convertToShort(t.getProp1()),
                            Property.convertToShort(t.getProp2()),
                            Property.convertToShort(t.getProp3())));
                }
                data1.add(new TableRowClass("Wissen", "", "", "", ""));
                for (Talent t : hero.getTalents().knowledgeTalents){
                    data1.add(new TableRowClass(t.getName(), t.getSkilled().toString(),
                            Property.convertToShort(t.getProp1()),
                            Property.convertToShort(t.getProp2()),
                            Property.convertToShort(t.getProp3())));
                }
                data1.add(new TableRowClass("Handwerk", "", "", "", ""));
                for (Talent t : hero.getTalents().craftingTalents){
                    data1.add(new TableRowClass(t.getName(), t.getSkilled().toString(),
                            Property.convertToShort(t.getProp1()),
                            Property.convertToShort(t.getProp2()),
                            Property.convertToShort(t.getProp3())));
                }
                data1.add(new TableRowClass("Sprachen/Schriften", "", "", "", ""));
                for (Talent t : hero.getTalents().languagesAndWritingTalents){
                    data1.add(new TableRowClass(t.getName(), t.getSkilled().toString(),
                            Property.convertToShort(t.getProp1()),
                            Property.convertToShort(t.getProp2()),
                            Property.convertToShort(t.getProp3())));
                }

                tableView1.setItems(data);
                tableView2.setItems(data1);

                tab = newTab(hero.getName());
                //tab.getGraphic().wid
                setTopAnchor(tableView1, 0.0);
                setBottomAnchor(tableView1, 0.0);
                setLeftAnchor(tableView1, 0.0);
                //anchorPane.setRightAnchor(tableView1, );
                setTopAnchor(tableView2, 0.0);
                setBottomAnchor(tableView2, 0.0);
                setRightAnchor(tableView2, 0.0);
                tab.setContent(anchorPane);
                tabs.add(tab);
                //System.out.println(tableView1.getColumns().getClass().toString());
                anchorPane.getChildren().addAll(tableView1);
                anchorPane.getChildren().addAll(tableView2);
                tabPane.getTabs().add(tabs.get(tabs.size()-1));
                dbOverwatch.heros.add(hero);
                heroDropDown.getItems().add(hero.getName());
            }
            editSubMenu.getItems().addAll(editSubMenuItems);
        }else if (heroCountInDatabase<0){
            Alert alert = new Alert(Alert.AlertType.NONE, "The program is unable to start bacause the " +
                    "database is already in use. Make sure to close all other \"GameMastR\" programs. If this error " +
                    "continues to show please consult the admin.", ButtonType.CLOSE);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.CLOSE) {
                Platform.exit();
            }
        }else{
            System.out.println("all heros already loaded");
        }
    }

    public void resizeScene(){
        for (Tab tab:tabPane.getTabs()) {
            if(!tab.getText().equals("Überblick")){
                AnchorPane anchorPane = (AnchorPane) tab.getContent();
                TableView tableView1 = (TableView) anchorPane.getChildren().get(0);
                TableView tableView2 = (TableView) anchorPane.getChildren().get(1);
                setRightAnchor(tableView1, anchorPane.getWidth()/2);
                setLeftAnchor(tableView2, anchorPane.getWidth()/2);
                for (TableColumn column: (ObservableList<TableColumn>)tableView1.getColumns()) {
                    column.setPrefWidth(anchorPane.getWidth()/10);
                    column.setSortable(false);
                }
                for (TableColumn column: (ObservableList<TableColumn>)tableView2.getColumns()) {
                    column.setPrefWidth(anchorPane.getWidth()/10);
                    column.setSortable(false);
                }
            }
        }
    }

    public void editHero(){
        GameMastR.setScene("editHero");
    }

    public void showCreateHero(){
        GameMastR.setScene("createChar");
    }
    public void loadFromExcel(){
        GameMastR.setScene("loadFromExcel");
    }
}
