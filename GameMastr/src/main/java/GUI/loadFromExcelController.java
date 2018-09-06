package GUI;

import Database.DBOverwatch;
import Hero.FightingTalent;
import Hero.Property;
import Hero.ResolvedHero;
import Hero.Talent;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;

public class loadFromExcelController extends AnchorPane {

    @FXML
    private TableView<TableRowClass> table;
    @FXML
    private TableView<TableRowClass> table2;

    DBOverwatch dbOverwatch = DBOverwatch.instance;
    ResolvedHero hero = new ResolvedHero();

    public void openFileChooser(){
        File file = GameMastR.openFileChooser();
        if (file == null) {
            System.out.println("Got empty file");
            return;
        }
        try {
            OPCPackage fs = OPCPackage.open(file);
            XSSFWorkbook wb = new XSSFWorkbook(fs);
            XSSFSheet baseStats = wb.getSheetAt(0);
            XSSFSheet skills = wb.getSheetAt(1);
            XSSFRow row;
            XSSFCell cell;

            //Setting up Table data construct
            final ObservableList<TableRowClass> data = FXCollections.observableArrayList();
            TableColumn col1 = (TableColumn)table.getColumns().get(0);
            TableColumn col2 = (TableColumn)table.getColumns().get(1);
            TableColumn col3 = (TableColumn)table.getColumns().get(2);
            TableColumn col4 = (TableColumn)table.getColumns().get(3);
            TableColumn col5 = (TableColumn)table.getColumns().get(4);
            col1.setCellValueFactory(new PropertyValueFactory<TableRowClass, String>("c1"));
            col2.setCellValueFactory(new PropertyValueFactory<TableRowClass, String>("c2"));
            col3.setCellValueFactory(new PropertyValueFactory<TableRowClass, String>("c3"));
            col4.setCellValueFactory(new PropertyValueFactory<TableRowClass, String>("c4"));
            col5.setCellValueFactory(new PropertyValueFactory<TableRowClass, String>("c5"));

            final ObservableList<TableRowClass> data1 = FXCollections.observableArrayList();
            TableColumn col11 = (TableColumn)table2.getColumns().get(0);
            TableColumn col12 = (TableColumn)table2.getColumns().get(1);
            TableColumn col13 = (TableColumn)table2.getColumns().get(2);
            TableColumn col14 = (TableColumn)table2.getColumns().get(3);
            TableColumn col15 = (TableColumn)table2.getColumns().get(4);
            col11.setCellValueFactory(new PropertyValueFactory<TableRowClass, String>("c1"));
            col12.setCellValueFactory(new PropertyValueFactory<TableRowClass, String>("c2"));
            col13.setCellValueFactory(new PropertyValueFactory<TableRowClass, String>("c3"));
            col14.setCellValueFactory(new PropertyValueFactory<TableRowClass, String>("c4"));
            col15.setCellValueFactory(new PropertyValueFactory<TableRowClass, String>("c5"));

            hero.setName(baseStats.getRow(7).getCell(2).getStringCellValue().replaceAll("'", "’"));
            hero.setRace(baseStats.getRow(3).getCell(2).getStringCellValue());
            hero.setCulture(baseStats.getRow(4).getCell(2).getStringCellValue());
            hero.setProfession(baseStats.getRow(5).getCell(2).getStringCellValue());
            //hero.setGender(baseStats.getRow(8).getCell(2).toString());
            hero.setHeight((int)baseStats.getRow(13).getCell(2).getNumericCellValue());
            hero.setWeight((int)baseStats.getRow(14).getCell(2).getNumericCellValue());
            hero.setHair_color(baseStats.getRow(11).getCell(2).getStringCellValue());
            hero.setEye_color(baseStats.getRow(12).getCell(2).getStringCellValue());
            hero.setBirthdate(baseStats.getRow(9).getCell(2).getStringCellValue());
            hero.setAge((int)baseStats.getRow(10).getCell(2).getNumericCellValue());
            hero.setTitle(baseStats.getRow(15).getCell(2).getStringCellValue());
            hero.setSocial((int)baseStats.getRow(27).getCell(4).getNumericCellValue());

            hero.properties.setStart(Property.PropertyName.MUT,(int)baseStats.getRow(18).getCell(2).getNumericCellValue());
            hero.properties.setMod(Property.PropertyName.MUT,(int)baseStats.getRow(18).getCell(3).getNumericCellValue());
            hero.properties.setCurrently(Property.PropertyName.MUT,(int)baseStats.getRow(18).getCell(4).getNumericCellValue());
            hero.properties.setMax(Property.PropertyName.MUT,0);

            hero.properties.setStart(Property.PropertyName.KLUGHEIT,(int)baseStats.getRow(19).getCell(2).getNumericCellValue());
            hero.properties.setMod(Property.PropertyName.KLUGHEIT,(int)baseStats.getRow(19).getCell(3).getNumericCellValue());
            hero.properties.setCurrently(Property.PropertyName.KLUGHEIT,(int)baseStats.getRow(19).getCell(4).getNumericCellValue());
            hero.properties.setMax(Property.PropertyName.KLUGHEIT,0);

            hero.properties.setStart(Property.PropertyName.INTUITION,(int)baseStats.getRow(20).getCell(2).getNumericCellValue());
            hero.properties.setMod(Property.PropertyName.INTUITION,(int)baseStats.getRow(20).getCell(3).getNumericCellValue());
            hero.properties.setCurrently(Property.PropertyName.INTUITION,(int)baseStats.getRow(20).getCell(4).getNumericCellValue());
            hero.properties.setMax(Property.PropertyName.INTUITION,0);

            hero.properties.setStart(Property.PropertyName.CHARISMA,(int)baseStats.getRow(21).getCell(2).getNumericCellValue());
            hero.properties.setMod(Property.PropertyName.CHARISMA,(int)baseStats.getRow(21).getCell(3).getNumericCellValue());
            hero.properties.setCurrently(Property.PropertyName.CHARISMA,(int)baseStats.getRow(21).getCell(4).getNumericCellValue());
            hero.properties.setMax(Property.PropertyName.CHARISMA,0);

            hero.properties.setStart(Property.PropertyName.FINGERFERTIGKEIT,(int)baseStats.getRow(22).getCell(2).getNumericCellValue());
            hero.properties.setMod(Property.PropertyName.FINGERFERTIGKEIT,(int)baseStats.getRow(22).getCell(3).getNumericCellValue());
            hero.properties.setCurrently(Property.PropertyName.FINGERFERTIGKEIT,(int)baseStats.getRow(22).getCell(4).getNumericCellValue());
            hero.properties.setMax(Property.PropertyName.FINGERFERTIGKEIT,0);

            hero.properties.setStart(Property.PropertyName.GEWANDTHEIT,(int)baseStats.getRow(23).getCell(2).getNumericCellValue());
            hero.properties.setMod(Property.PropertyName.GEWANDTHEIT,(int)baseStats.getRow(23).getCell(3).getNumericCellValue());
            hero.properties.setCurrently(Property.PropertyName.GEWANDTHEIT,(int)baseStats.getRow(23).getCell(4).getNumericCellValue());
            hero.properties.setMax(Property.PropertyName.GEWANDTHEIT,0);

            hero.properties.setStart(Property.PropertyName.KONSTITUTION,(int)baseStats.getRow(24).getCell(2).getNumericCellValue());
            hero.properties.setMod(Property.PropertyName.KONSTITUTION,(int)baseStats.getRow(24).getCell(3).getNumericCellValue());
            hero.properties.setCurrently(Property.PropertyName.KONSTITUTION,(int)baseStats.getRow(24).getCell(4).getNumericCellValue());
            hero.properties.setMax(Property.PropertyName.KONSTITUTION,0);

            hero.properties.setStart(Property.PropertyName.KOERPERKRAFT,(int)baseStats.getRow(25).getCell(2).getNumericCellValue());
            hero.properties.setMod(Property.PropertyName.KOERPERKRAFT,(int)baseStats.getRow(25).getCell(3).getNumericCellValue());
            hero.properties.setCurrently(Property.PropertyName.KOERPERKRAFT,(int)baseStats.getRow(25).getCell(4).getNumericCellValue());
            hero.properties.setMax(Property.PropertyName.KOERPERKRAFT,0);

            hero.properties.setStart(Property.PropertyName.GESCHWINDIGKEIT,(int)baseStats.getRow(26).getCell(2).getNumericCellValue());
            hero.properties.setMod(Property.PropertyName.GESCHWINDIGKEIT,(int)baseStats.getRow(26).getCell(3).getNumericCellValue());
            hero.properties.setCurrently(Property.PropertyName.GESCHWINDIGKEIT,(int)baseStats.getRow(26).getCell(4).getNumericCellValue());
            hero.properties.setMax(Property.PropertyName.GESCHWINDIGKEIT,0);

            hero.vitals.le.setStart((int)baseStats.getRow(18).getCell(8).getNumericCellValue());
            hero.vitals.le.setMod((int)baseStats.getRow(18).getCell(9).getNumericCellValue());
            hero.vitals.le.setCurrent((int)baseStats.getRow(18).getCell(10).getNumericCellValue());
            hero.vitals.au.setStart((int)baseStats.getRow(19).getCell(8).getNumericCellValue());
            hero.vitals.au.setMod((int)baseStats.getRow(19).getCell(9).getNumericCellValue());
            hero.vitals.au.setCurrent((int)baseStats.getRow(19).getCell(10).getNumericCellValue());
            hero.vitals.ae.setStart((int)baseStats.getRow(20).getCell(8).getNumericCellValue());
            hero.vitals.ae.setMod((int)baseStats.getRow(20).getCell(9).getNumericCellValue());
            hero.vitals.ae.setCurrent((int)baseStats.getRow(20).getCell(10).getNumericCellValue());
            hero.vitals.ke.setStart((int)baseStats.getRow(21).getCell(8).getNumericCellValue());
            hero.vitals.ke.setMod((int)baseStats.getRow(21).getCell(9).getNumericCellValue());
            hero.vitals.ke.setCurrent((int)baseStats.getRow(21).getCell(10).getNumericCellValue());
            hero.vitals.mr.setStart((int)baseStats.getRow(22).getCell(8).getNumericCellValue());
            hero.vitals.mr.setMod((int)baseStats.getRow(22).getCell(9).getNumericCellValue());
            hero.vitals.mr.setCurrent((int)baseStats.getRow(22).getCell(10).getNumericCellValue());
            hero.vitals.ini.setStart((int)baseStats.getRow(23).getCell(8).getNumericCellValue());
            hero.vitals.ini.setMod((int)baseStats.getRow(23).getCell(9).getNumericCellValue());
            hero.vitals.ini.setCurrent((int)baseStats.getRow(23).getCell(10).getNumericCellValue());
            hero.vitals.at.setStart((int)baseStats.getRow(24).getCell(8).getNumericCellValue());
            hero.vitals.at.setMod((int)baseStats.getRow(24).getCell(9).getNumericCellValue());
            hero.vitals.at.setCurrent((int)baseStats.getRow(24).getCell(10).getNumericCellValue());
            hero.vitals.pa.setStart((int)baseStats.getRow(25).getCell(8).getNumericCellValue());
            hero.vitals.pa.setMod((int)baseStats.getRow(25).getCell(9).getNumericCellValue());
            hero.vitals.pa.setCurrent((int)baseStats.getRow(25).getCell(10).getNumericCellValue());
            hero.vitals.fk.setStart((int)baseStats.getRow(26).getCell(8).getNumericCellValue());
            hero.vitals.fk.setMod((int)baseStats.getRow(26).getCell(9).getNumericCellValue());
            hero.vitals.fk.setCurrent((int)baseStats.getRow(26).getCell(10).getNumericCellValue());
            hero.vitals.ws.setStart((int)baseStats.getRow(27).getCell(8).getNumericCellValue());
            hero.vitals.ws.setMod((int)baseStats.getRow(27).getCell(9).getNumericCellValue());
            hero.vitals.ws.setCurrent((int)baseStats.getRow(27).getCell(10).getNumericCellValue());

            //Setup table Data

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

            int maxFightingTalents = 0;
            int startFightTalents = 0;
            int prevMax = 0;
            for (int i = 0; i < skills.getPhysicalNumberOfRows(); i++) {
                if (!(skills.getRow(i) == null)) {
                    Cell c = skills.getRow(i).getCell(1);
                    if (!(c == null || c.getCellType() == Cell.CELL_TYPE_BLANK)) {
                        if (skills.getRow(i).getCell(1).getStringCellValue().equals("Kampf")) startFightTalents = i + 1;
                        maxFightingTalents = i;
                    }else if (i>5){
                        break;
                    }
                }
            }
            //System.out.println(startFightTalents + " to " + maxFightingTalents);
            data1.add(new TableRowClass("Kampf", "", "", "", ""));

            for (int i = startFightTalents; i < maxFightingTalents-1; i++){
                if (!(skills.getRow(i).getCell(2).getCellType() == Cell.CELL_TYPE_BLANK)) {
                    FightingTalent ft = loadFightingTalents(skills, i);
                    //System.out.println(ft.toString());
                    //System.out.println(i);
                    hero.getTalents().fightingTalents.add(ft);
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
            }
            prevMax = maxFightingTalents;
            int maxTalents = 0;
            int startTalents = 0;
            //System.out.println("start: " + maxFightingTalents);
            for (int i = maxFightingTalents+1; i < skills.getPhysicalNumberOfRows(); i++) {
                if (!(skills.getRow(i) == null)) {
                    Cell c = skills.getRow(i).getCell(1);
                    if (!(c == null || c.getCellType() == Cell.CELL_TYPE_BLANK)) {
                        if (skills.getRow(i).getCell(1).getStringCellValue().equals("Körperlich")) startTalents = i + 1;
                        maxTalents = i;
                    }else if(i>prevMax+5){
                        //System.out.println("end: " + maxTalents);
                        break;
                    }
                }
            }
            //System.out.println(startTalents + " to " + maxTalents);
            data1.add(new TableRowClass("Körperlich", "", "", "", ""));

            for (int i = startTalents; i < maxTalents-1; i++) {
                //System.out.println(i);
                if (!(skills.getRow(i).getCell(2).getCellType() == Cell.CELL_TYPE_BLANK)) {
                    Talent t = loadTalents(skills, i);
                    hero.getTalents().physicalTalents.add(t);
                    //System.out.println(t);
                    data1.add(new TableRowClass(t.getName(), t.getSkilled().toString(),
                            Property.convertToShort(t.getProp1()),
                            Property.convertToShort(t.getProp2()),
                            Property.convertToShort(t.getProp3())));
                }
            }

            prevMax = maxTalents;

            //System.out.println("start: " + maxTalents);
            for (int i = prevMax+1; i < skills.getPhysicalNumberOfRows(); i++) {
                if (!(skills.getRow(i) == null)) {
                    Cell c = skills.getRow(i).getCell(1);
                    if (!(c == null || c.getCellType() == Cell.CELL_TYPE_BLANK)) {
                        if (skills.getRow(i).getCell(1).getStringCellValue().equals("Gesellschaftlich")) startTalents = i + 1;
                        maxTalents = i;
                    }else if(i>prevMax+5){
                        //System.out.println("end: " + maxTalents);
                        break;
                    }
                }
            }
            //System.out.println(startTalents + " to " + maxTalents);
            data1.add(new TableRowClass("Gesellschaftlich", "", "", "", ""));

            for (int i = startTalents; i <= maxTalents; i++) {
                //System.out.println("line: " + (i+1));
                //System.out.println(skills.getRow(i).getCell(1) + " " + skills.getRow(i).getCell(2).getCellType());
                if (!(skills.getRow(i).getCell(2).getCellType() == Cell.CELL_TYPE_BLANK)) {
                    Talent t = loadTalents(skills, i);
                    hero.getTalents().societyTalents.add(t);
                    //System.out.println(t);
                    data1.add(new TableRowClass(t.getName(), t.getSkilled().toString(),
                            Property.convertToShort(t.getProp1()),
                            Property.convertToShort(t.getProp2()),
                            Property.convertToShort(t.getProp3())));
                }
            }

            prevMax = maxTalents;

            //System.out.println("start: " + maxTalents);
            for (int i = prevMax+1; i < skills.getPhysicalNumberOfRows(); i++) {
                if (!(skills.getRow(i) == null)) {
                    Cell c = skills.getRow(i).getCell(1);
                    if (!(c == null || c.getCellType() == Cell.CELL_TYPE_BLANK)) {
                        if (skills.getRow(i).getCell(1).getStringCellValue().equals("Natur")) startTalents = i + 1;
                        maxTalents = i;
                    }else if(i>prevMax+5){
                        //System.out.println("end: " + maxTalents);
                        break;
                    }
                }
            }
            //System.out.println(startTalents + " to " + maxTalents);
            data1.add(new TableRowClass("Natur", "", "", "", ""));

            for (int i = startTalents; i <= maxTalents; i++) {
                //System.out.println("line: " + (i+1));
                //System.out.println(skills.getRow(i).getCell(1) + " " + skills.getRow(i).getCell(2).getCellType());
                if (!(skills.getRow(i).getCell(2).getCellType() == Cell.CELL_TYPE_BLANK)) {
                    Talent t = loadTalents(skills, i);
                    hero.getTalents().natureTalents.add(t);
                    //System.out.println(t);
                    data1.add(new TableRowClass(t.getName(), t.getSkilled().toString(),
                            Property.convertToShort(t.getProp1()),
                            Property.convertToShort(t.getProp2()),
                            Property.convertToShort(t.getProp3())));
                }
            }

            prevMax = maxTalents;

            //System.out.println("start: " + maxTalents);
            for (int i = prevMax+1; i < skills.getPhysicalNumberOfRows(); i++) {
                if (!(skills.getRow(i) == null)) {
                    Cell c = skills.getRow(i).getCell(1);
                    if (!(c == null || c.getCellType() == Cell.CELL_TYPE_BLANK)) {
                        if (skills.getRow(i).getCell(1).getStringCellValue().equals("Wissen")) startTalents = i + 1;
                        maxTalents = i;
                    }else if(i>prevMax+5){
                        //System.out.println("end: " + maxTalents);
                        break;
                    }
                }
            }
            //System.out.println(startTalents + " to " + maxTalents);
            data1.add(new TableRowClass("Wissen", "", "", "", ""));

            for (int i = startTalents; i <= maxTalents; i++) {
                //System.out.println("line: " + (i+1));
                //System.out.println(skills.getRow(i).getCell(1) + " " + skills.getRow(i).getCell(2).getCellType());
                if (!(skills.getRow(i).getCell(2).getCellType() == Cell.CELL_TYPE_BLANK)) {
                    Talent t = loadTalents(skills, i);
                    hero.getTalents().knowledgeTalents.add(t);
                    //System.out.println(t);
                    data1.add(new TableRowClass(t.getName(), t.getSkilled().toString(),
                            Property.convertToShort(t.getProp1()),
                            Property.convertToShort(t.getProp2()),
                            Property.convertToShort(t.getProp3())));
                }
            }

            prevMax = maxTalents;

            //System.out.println("start: " + maxTalents);
            for (int i = prevMax+1; i < skills.getPhysicalNumberOfRows(); i++) {
                if (!(skills.getRow(i) == null)) {
                    Cell c = skills.getRow(i).getCell(1);
                    if (!(c == null || c.getCellType() == Cell.CELL_TYPE_BLANK)) {
                        if (skills.getRow(i).getCell(1).getStringCellValue().equals("Sprachen/Schriften")) startTalents = i + 1;
                        maxTalents = i;
                        if(skills.getRow(i).getCell(1).getStringCellValue().equals("Handwerk")){
                            maxTalents -= 1;
                            //System.out.println("end: " + maxTalents);
                            break;
                        }
                    }else if(i>prevMax+5&&false){
                        //System.out.println("end: " + maxTalents);
                        break;
                    }
                }
            }
            //System.out.println(startTalents + " to " + maxTalents);
            data1.add(new TableRowClass("Sprachen/Schriften", "", "", "", ""));

            for (int i = startTalents; i <= maxTalents; i++) {
                //System.out.println("line: " + (i+1));
                //System.out.println(skills.getRow(i).getCell(1) + " " + skills.getRow(i).getCell(2).getCellType());
                if (!(skills.getRow(i).getCell(2).getCellType() == Cell.CELL_TYPE_BLANK)) {
                    Talent t = loadTalents(skills, i);
                    hero.getTalents().languagesAndWritingTalents.add(t);
                    //System.out.println(t);
                    data1.add(new TableRowClass(t.getName(), t.getSkilled().toString(),
                            Property.convertToShort(t.getProp1()),
                            Property.convertToShort(t.getProp2()),
                            Property.convertToShort(t.getProp3())));
                }
            }

            prevMax = maxTalents;

            //System.out.println("start: " + maxTalents);
            for (int i = prevMax+1; i < skills.getPhysicalNumberOfRows(); i++) {
                if (!(skills.getRow(i) == null)) {
                    Cell c = skills.getRow(i).getCell(1);
                    if (!(c == null || c.getCellType() == Cell.CELL_TYPE_BLANK)) {
                        if (skills.getRow(i).getCell(1).getStringCellValue().equals("Handwerk")) startTalents = i + 1;
                        maxTalents = i;
                    }else if(i>prevMax+5){
                        //System.out.println("end: " + maxTalents);
                        break;
                    }
                }
            }
            //System.out.println(startTalents + " to " + maxTalents);
            data1.add(new TableRowClass("Handwerk", "", "", "", ""));

            for (int i = startTalents; i <= maxTalents; i++) {
                //System.out.println("line: " + (i+1));
                //System.out.println(skills.getRow(i).getCell(1) + " " + skills.getRow(i).getCell(2).getCellType());
                if (!(skills.getRow(i).getCell(2).getCellType() == Cell.CELL_TYPE_BLANK)) {
                    Talent t = loadTalents(skills, i);
                    hero.getTalents().craftingTalents.add(t);
                    //System.out.println(t);
                    data1.add(new TableRowClass(t.getName(), t.getSkilled().toString(),
                            Property.convertToShort(t.getProp1()),
                            Property.convertToShort(t.getProp2()),
                            Property.convertToShort(t.getProp3())));
                }
            }

            table.setItems(data);
            table2.setItems(data1);
            //System.out.println("Found Hero: \"" + hero.getName()+"\"");

        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }

    }

    private FightingTalent loadFightingTalents(XSSFSheet sheet, int row){
        FightingTalent retTalent = new FightingTalent();

        if(sheet.getRow(row).getCell(3).getStringCellValue().equals("AT")){
            retTalent.setNecessaryAT(true);
            retTalent.setSkilledAT((int)sheet.getRow(row).getCell(4).getNumericCellValue());
        }else{
            retTalent.setNecessaryAT(false);
            retTalent.setSkilledAT(0);
        }
        if(sheet.getRow(row).getCell(5).getStringCellValue().equals("PA")){
            retTalent.setNecessaryPA(true);
            retTalent.setSkilledPA((int)sheet.getRow(row).getCell(6).getNumericCellValue());
        }else{
            retTalent.setNecessaryPA(false);
            retTalent.setSkilledPA(0);
        }
        if(sheet.getRow(row).getCell(7).getStringCellValue().equals("FK")){
            retTalent.setNecessaryFK(true);
            retTalent.setSkilledFK((int)sheet.getRow(row).getCell(8).getNumericCellValue());
        }else{
            retTalent.setNecessaryFK(false);
            retTalent.setSkilledFK(0);
        }
        retTalent.setName(sheet.getRow(row).getCell(1).getStringCellValue().replaceAll("\\s+",""));

        return retTalent;
    }

    private Talent loadTalents(XSSFSheet sheet, int row){
        Talent retTalent = new Talent();
        String props[] = new String[3];
        for (int i = 0; i<3; i++){
            props[i] = sheet.getRow(row).getCell(3+i).getStringCellValue();
            //System.out.print(props[i]);
        }
        //System.out.println();
        retTalent.setProp1(Property.convertFromShort(props[0]));
        retTalent.setProp2(Property.convertFromShort(props[1]));
        retTalent.setProp3(Property.convertFromShort(props[2]));
        retTalent.setSkilled((int)sheet.getRow(row).getCell(2).getNumericCellValue());
        retTalent.setName(sheet.getRow(row).getCell(1).getStringCellValue().replaceAll("\\s+",""));

        return retTalent;
    }
    public void Close(){
        GameMastR.setScene("main");
    }

    public void Save(){
        dbOverwatch.saveHero(hero);
        System.out.println("finished");
        Close();
    }
}
