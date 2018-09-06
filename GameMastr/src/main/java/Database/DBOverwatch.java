package Database;

import Universe.Culture;
import org.h2.jdbc.JdbcSQLException;
import Hero.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

public class DBOverwatch {

    private Connection conn = null;
    private Statement statement;
    public ArrayList<ResolvedHero> heros = new ArrayList<ResolvedHero>();
    public ArrayList<String> cultures = new ArrayList<>();
    public ArrayList<String> races = new ArrayList<>();

    public DBOverwatch(){
        try {
            conn = DriverManager.getConnection("jdbc:h2:./data/database;TRACE_LEVEL_FILE=3;TRACE_LEVEL_SYSTEM_OUT=1", "sa", "");
        } catch (SQLException e) {
            System.out.println("Database Connection failure. please try again after closing all connections");
            //e.printStackTrace();
        }
    }

    public ResultSet executeSQL(String query){
        try {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(conn.nativeSQL(query));
            //System.out.println("Executed: " + conn.nativeSQL(query));
            //System.out.println(resultSet);
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean execute(String query){
        try {
            Boolean succ;
            statement = conn.createStatement();
            succ = statement.execute(conn.nativeSQL(query));
            //System.out.println("Executed: " + conn.nativeSQL(query));
            //System.out.println(resultSet);
            return succ;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private ResultSet executeQuery(String query){
        try {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            //System.out.println("Executed: " + query);
            //System.out.println(resultSet);
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void newTable(String name){
        executeSQL("CREATE TABLE" + name);
    }

    public void closeConn(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Integer getAmountOfRows(String tablename) {
        try {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM " + tablename);
            resultSet.next();
            int count = resultSet.getInt(1);
            //System.out.println("Count from " + tablename + ": " + count);

            return count;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } catch (NullPointerException npe){
            return -1;
        }
    }

    public Hero createChar(){
        Hero hero = new Hero();
        PropertySet propertySet = new PropertySet();
        hero.setId(getAmountOfRows("HEROS") + 1); //Id statr with 1 - fuck me I know - might change that later
        hero.setPropertiesId(getAmountOfRows("PROPERTIES") + 1);
        hero.setTalentsId(getAmountOfRows("TALENTS") + 1);
        return hero;
    }

    public boolean saveHero(ResolvedHero hero){
        try{

            //Checking if all talents exists, when needed creating new ones
            String fightingTalents = "";
            String physicalTalents = "";
            String societyTalents = "";
            String ritualTalents = "";
            String natureTalents = "";
            String knowledgeTalents = "";
            String craftingTalents = "";
            String languageAndWritingTalents = "";
            //Boolean ex = true;
            int id = 0;

            for (FightingTalent t:hero.getTalents().fightingTalents) {
                ResultSet res = executeSQL("SELECT * FROM FIGHT_TALENTS WHERE NAME = '" + t.getName() + "'");
                //System.out.println(res.toString());
                if (!res.next()){
                    id = insertTalent("FIGHT_TALENTS", t);
                }else {
                    id = res.getInt("ID");
                }
                fightingTalents += id + "/" + t.getSkilledAT() + "A" + t.getSkilledPA() + "P" + t.getSkilledFK() + ";";
            }
            for (Talent t:hero.getTalents().physicalTalents) {
                ResultSet res = executeSQL("SELECT * FROM PHYSICAL_TALENTS WHERE NAME = '" + t.getName() + "'");
                if (!res.next()){
                    id = insertTalent("PHYSICAL_TALENTS", t);
                }else {
                    id = res.getInt("ID");
                }
                physicalTalents += id + "/" + t.getSkilled() + ";";
            }
            for (Talent t:hero.getTalents().societyTalents) {
                ResultSet res = executeSQL("SELECT * FROM SOCIETY_TALENTS WHERE NAME = '" + t.getName() + "'");
                if (!res.next()){
                    id = insertTalent("SOCIETY_TALENTS", t);
                }else {
                    id = res.getInt("ID");
                }
                societyTalents += id + "/" + t.getSkilled() + ";";
            }
            for (Talent t:hero.getTalents().ritualTalents) {
                ResultSet res = executeSQL("SELECT * FROM RITUALS_TALENTS WHERE NAME = '" + t.getName() + "'");
                if (!res.next()){
                    id = insertTalent("RITUALS_TALENTS", t);
                }else {
                    id = res.getInt("ID");
                }
                ritualTalents += id + "/" + t.getSkilled() + ";";
            }
            for (Talent t:hero.getTalents().natureTalents) {
                ResultSet res = executeSQL("SELECT * FROM NATURE_TALENTS WHERE NAME = '" + t.getName() + "'");
                if (!res.next()){
                    id = insertTalent("NATURE_TALENTS", t);
                }else {
                    id = res.getInt("ID");
                }
                natureTalents += id + "/" + t.getSkilled() + ";";
            }
            for (Talent t:hero.getTalents().knowledgeTalents) {
                ResultSet res = executeSQL("SELECT * FROM KNOWLEDGE_TALENTS WHERE NAME = '" + t.getName() + "'");
                if (!res.next()){
                    id = insertTalent("KNOWLEDGE_TALENTS", t);
                }else {
                    id = res.getInt("ID");
                }
                knowledgeTalents += id + "/" + t.getSkilled() + ";";
            }
            for (Talent t:hero.getTalents().craftingTalents) {
                ResultSet res = executeSQL("SELECT * FROM CRAFT_TALENTS WHERE NAME = '" + t.getName() + "'");
                if (!res.next()){
                    id = insertTalent("CRAFT_TALENTS", t);
                }else {
                    id = res.getInt("ID");
                }
                craftingTalents += id + "/" + t.getSkilled() + ";";
            }
            for (Talent t:hero.getTalents().languagesAndWritingTalents) {
                ResultSet res = executeSQL("SELECT * FROM LAN_WRITE_TALENTS WHERE NAME = '" + t.getName() + "'");
                if (!res.next()){
                    id = insertTalent("LAN_WRITE_TALENTS", t);
                }else {
                    id = res.getInt("ID");
                }
                languageAndWritingTalents += id + "/" + t.getSkilled() + ";";
            }

            int talentID = getAmountOfRows("TALENTS") + 1;
            execute("INSERT INTO TALENTS VALUES (" + talentID + ",'" +
                    fightingTalents + "','" +
                    physicalTalents + "','" +
                    societyTalents + "','" +
                    natureTalents + "','" +
                    knowledgeTalents + "','" +
                    craftingTalents + "','" +
                    languageAndWritingTalents + "','" +
                    ritualTalents + "')");

            ResultSet tmp_resultSet = executeSQL("SELECT * FROM RACES WHERE NAME='" + hero.getRace() + "'");
            int raceID = 0;
            if(tmp_resultSet.next()) {
                raceID = tmp_resultSet.getInt("ID");
            }else{
                raceID = getAmountOfRows("RACES");
                execute("INSERT INTO RACES VALUES(" +
                        raceID + ",'" +
                        hero.getRace() + "')");
            }

            tmp_resultSet = executeSQL("SELECT * FROM CULTURE WHERE NAME='" + hero.getCulture() + "'");
            //System.out.println("Does culture exists?: " + tmp_resultSet.next());
            int cultureID;
            if(tmp_resultSet.next()) {
                cultureID = tmp_resultSet.getInt("ID");
            }else{
                cultureID = getAmountOfRows("RACES");
                execute("INSERT INTO CULTURE VALUES(" +
                        cultureID + ",'" +
                        hero.getCulture() + "')");
            }

            int propertiesID = getAmountOfRows("PROPERTIES") + 1;
            execute("INSERT INTO PROPERTIES VALUES (" +
                    propertiesID + "," +
                    hero.getProperties().mu.getStart() + "," +
                    hero.getProperties().mu.getMod() + "," +
                    hero.getProperties().mu.getCurently() + "," +
                    hero.getProperties().mu.getMax() + "," +
                    hero.getProperties().kl.getStart() + "," +
                    hero.getProperties().kl.getMod() + "," +
                    hero.getProperties().kl.getCurently() + "," +
                    hero.getProperties().kl.getMax() + "," +
                    hero.getProperties().in.getStart() + "," +
                    hero.getProperties().in.getMod() + "," +
                    hero.getProperties().in.getCurently() + "," +
                    hero.getProperties().in.getMax() + "," +
                    hero.getProperties().ch.getStart() + "," +
                    hero.getProperties().ch.getMod() + "," +
                    hero.getProperties().ch.getCurently() + "," +
                    hero.getProperties().ch.getMax() + "," +
                    hero.getProperties().ff.getStart() + "," +
                    hero.getProperties().ff.getMod() + "," +
                    hero.getProperties().ff.getCurently() + "," +
                    hero.getProperties().ff.getMax() + "," +
                    hero.getProperties().ge.getStart() + "," +
                    hero.getProperties().ge.getMod() + "," +
                    hero.getProperties().ge.getCurently() + "," +
                    hero.getProperties().ge.getMax() + "," +
                    hero.getProperties().ko.getStart() + "," +
                    hero.getProperties().ko.getMod() + "," +
                    hero.getProperties().ko.getCurently() + "," +
                    hero.getProperties().ko.getMax() + "," +
                    hero.getProperties().kk.getStart() + "," +
                    hero.getProperties().kk.getMod() + "," +
                    hero.getProperties().kk.getCurently() + "," +
                    hero.getProperties().kk.getMax() + "," +
                    hero.getProperties().gs.getStart() + "," +
                    hero.getProperties().gs.getMod() + "," +
                    hero.getProperties().gs.getCurently() + "," +
                    hero.getProperties().gs.getMax() + ")");

            if (hero.getProfession()==null){
                hero.setProfession(" ");
            }
            if (hero.getSecondProfession()==null){
                hero.setSecondProfession(" ");
            }

            int heroID = getAmountOfRows("HEROS") + 1;
            System.out.println(execute("INSERT INTO HEROS VALUES (" +
                    heroID + ",'" +
                    hero.getName() + "'," +
                    hero.getAge() + "," +
                    hero.getHeight() + "," +
                    hero.getWeight() + ",'" +
                    hero.getEye_color() + "','" +
                    hero.getHair_color() + "','" +
                    hero.getBirthdate() + "','" +
                    hero.getTitle() + "'," +
                    hero.getSocial() + "," +
                    raceID + "," +
                    cultureID + ",'" +
                    hero.getProfession() + "','" +
                    hero.getSecondProfession() + "'," +
                    propertiesID + "," +
                    talentID + ")"));

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public ResolvedHero getChar(int id){
        Hero hero = new Hero();
        ResolvedHero resHero = new ResolvedHero();
        ResultSet heroResultSet = executeQuery("SELECT * FROM HEROS WHERE ID = " + id);
        try {
            heroResultSet.next();
            hero.setId(heroResultSet.getInt(1));
            hero.setName(heroResultSet.getString(2));
            hero.setAge(heroResultSet.getInt(3));
            hero.setHeight(heroResultSet.getInt(4));
            hero.setWeight(heroResultSet.getInt(5));
            hero.setEye_color(heroResultSet.getString(6));
            hero.setHair_color(heroResultSet.getString(7));
            hero.setBirthdate(heroResultSet.getString(8));
            hero.setTitle(heroResultSet.getString(9));
            hero.setSocial(heroResultSet.getInt(10));
            hero.setRaceId(heroResultSet.getInt(11));
            hero.setCultureId(heroResultSet.getInt(12));
            hero.setProfession(heroResultSet.getString(13));
            hero.setSecondProfession(heroResultSet.getString(14));
            hero.setPropertiesId(heroResultSet.getInt(15));
            hero.setTalentsId(heroResultSet.getInt(16));
            hero.setVitalSetId(heroResultSet.getInt(17));

            /*##      resolving hero      ##*/

            resHero.setId(hero.getId());
            resHero.setName(hero.getName());
            resHero.setAge(hero.getAge());
            resHero.setHeight(hero.getHeight());
            resHero.setWeight(hero.getWeight());
            resHero.setEye_color(hero.getEye_color());
            resHero.setHair_color(hero.getHair_color());
            resHero.setBirthdate(hero.getBirthdate());
            resHero.setTitle(hero.getTitle());
            resHero.setSocial(hero.getSocial());

            ResultSet resultSet = executeQuery("SELECT * FROM RACES WHERE ID = " + hero.getRaceId());
            resultSet.next();
            resHero.setRace(resultSet.getString(2));

            resultSet = executeQuery("SELECT * FROM CULTURE WHERE ID = " + hero.getCultureId());
            resultSet.next();
            resHero.setCulture(resultSet.getString(2));

            resHero.setProfession(hero.getProfession());
            resHero.setSecondProfession(hero.getSecondProfession());

            resultSet = executeQuery("SELECT * FROM PROPERTIES WHERE ID = " + hero.getPropertiesId());

            resultSet.next();
            resHero.properties.setStart(Property.PropertyName.MUT, resultSet.getInt(2));
            resHero.properties.setMod(Property.PropertyName.MUT, resultSet.getInt(3));
            resHero.properties.setCurrently(Property.PropertyName.MUT, resultSet.getInt(4));
            resHero.properties.setMax(Property.PropertyName.MUT, resultSet.getInt(5));

            resHero.properties.setStart(Property.PropertyName.KLUGHEIT, resultSet.getInt(6));
            resHero.properties.setMod(Property.PropertyName.KLUGHEIT, resultSet.getInt(7));
            resHero.properties.setCurrently(Property.PropertyName.KLUGHEIT, resultSet.getInt(8));
            resHero.properties.setMax(Property.PropertyName.KLUGHEIT, resultSet.getInt(9));

            resHero.properties.setStart(Property.PropertyName.INTUITION, resultSet.getInt(10));
            resHero.properties.setMod(Property.PropertyName.INTUITION, resultSet.getInt(11));
            resHero.properties.setCurrently(Property.PropertyName.INTUITION, resultSet.getInt(12));
            resHero.properties.setMax(Property.PropertyName.INTUITION, resultSet.getInt(13));

            resHero.properties.setStart(Property.PropertyName.CHARISMA, resultSet.getInt(14));
            resHero.properties.setMod(Property.PropertyName.CHARISMA, resultSet.getInt(15));
            resHero.properties.setCurrently(Property.PropertyName.CHARISMA, resultSet.getInt(16));
            resHero.properties.setMax(Property.PropertyName.CHARISMA, resultSet.getInt(17));

            resHero.properties.setStart(Property.PropertyName.FINGERFERTIGKEIT, resultSet.getInt(18));
            resHero.properties.setMod(Property.PropertyName.FINGERFERTIGKEIT, resultSet.getInt(19));
            resHero.properties.setCurrently(Property.PropertyName.FINGERFERTIGKEIT, resultSet.getInt(20));
            resHero.properties.setMax(Property.PropertyName.FINGERFERTIGKEIT, resultSet.getInt(21));

            resHero.properties.setStart(Property.PropertyName.GEWANDTHEIT, resultSet.getInt(22));
            resHero.properties.setMod(Property.PropertyName.GEWANDTHEIT, resultSet.getInt(23));
            resHero.properties.setCurrently(Property.PropertyName.GEWANDTHEIT, resultSet.getInt(24));
            resHero.properties.setMax(Property.PropertyName.GEWANDTHEIT, resultSet.getInt(25));

            resHero.properties.setStart(Property.PropertyName.KONSTITUTION, resultSet.getInt(26));
            resHero.properties.setMod(Property.PropertyName.KONSTITUTION, resultSet.getInt(27));
            resHero.properties.setCurrently(Property.PropertyName.KONSTITUTION, resultSet.getInt(28));
            resHero.properties.setMax(Property.PropertyName.KONSTITUTION, resultSet.getInt(29));

            resHero.properties.setStart(Property.PropertyName.KOERPERKRAFT, resultSet.getInt(30));
            resHero.properties.setMod(Property.PropertyName.KOERPERKRAFT, resultSet.getInt(31));
            resHero.properties.setCurrently(Property.PropertyName.KOERPERKRAFT, resultSet.getInt(32));
            resHero.properties.setMax(Property.PropertyName.KOERPERKRAFT, resultSet.getInt(33));

            resHero.properties.setStart(Property.PropertyName.GESCHWINDIGKEIT, resultSet.getInt(34));
            resHero.properties.setMod(Property.PropertyName.GESCHWINDIGKEIT, resultSet.getInt(35));
            resHero.properties.setCurrently(Property.PropertyName.GESCHWINDIGKEIT, resultSet.getInt(36));
            resHero.properties.setMax(Property.PropertyName.GESCHWINDIGKEIT, resultSet.getInt(37));

            resultSet = executeQuery("SELECT * FROM VITALS WHERE ID = " + hero.getVitalSetId());
            //System.out.println(resultSet);
            resultSet.next();
            resHero.vitals.le.setStart(resultSet.getInt(2));
            resHero.vitals.le.setMod(resultSet.getInt(3));
            resHero.vitals.le.setCurrent(resultSet.getInt(4));
            resHero.vitals.au.setStart(resultSet.getInt(5));
            resHero.vitals.au.setMod(resultSet.getInt(6));
            resHero.vitals.au.setCurrent(resultSet.getInt(7));
            resHero.vitals.ae.setStart(resultSet.getInt(8));
            resHero.vitals.ae.setMod(resultSet.getInt(9));
            resHero.vitals.ae.setCurrent(resultSet.getInt(10));
            resHero.vitals.ke.setStart(resultSet.getInt(11));
            resHero.vitals.ke.setMod(resultSet.getInt(12));
            resHero.vitals.ke.setCurrent(resultSet.getInt(13));
            resHero.vitals.mr.setStart(resultSet.getInt(14));
            resHero.vitals.mr.setMod(resultSet.getInt(15));
            resHero.vitals.mr.setCurrent(resultSet.getInt(16));
            resHero.vitals.ini.setStart(resultSet.getInt(17));
            resHero.vitals.ini.setMod(resultSet.getInt(18));
            resHero.vitals.ini.setCurrent(resultSet.getInt(19));
            resHero.vitals.at.setStart(resultSet.getInt(20));
            resHero.vitals.at.setMod(resultSet.getInt(21));
            resHero.vitals.at.setCurrent(resultSet.getInt(22));
            resHero.vitals.pa.setStart(resultSet.getInt(23));
            resHero.vitals.pa.setMod(resultSet.getInt(24));
            resHero.vitals.pa.setCurrent(resultSet.getInt(25));
            resHero.vitals.fk.setStart(resultSet.getInt(26));
            resHero.vitals.fk.setMod(resultSet.getInt(27));
            resHero.vitals.fk.setCurrent(resultSet.getInt(28));
            resHero.vitals.ws.setStart(resultSet.getInt(29));
            resHero.vitals.ws.setMod(resultSet.getInt(30));
            resHero.vitals.ws.setCurrent(resultSet.getInt(31));


            resultSet = executeQuery("SELECT * FROM TALENTS WHERE ID = " + hero.getTalentsId());
            resultSet.next();
            String tmp_FIGHT;
            String tmp_PHYSICAL;
            String tmp_SOCIETY;
            String tmp_NATURE;
            String tmp_KNOWLEDGE;
            String tmp_CRAFTING;
            String tmp_LANGUAGE;
            String tmp_RITUALS;

            tmp_FIGHT = resultSet.getString(2);
            tmp_PHYSICAL = resultSet.getString(3);
            tmp_SOCIETY = resultSet.getString(4);
            tmp_NATURE = resultSet.getString(5);
            tmp_KNOWLEDGE = resultSet.getString(6);
            tmp_CRAFTING = resultSet.getString(7);
            tmp_LANGUAGE = resultSet.getString(8);
            tmp_RITUALS = resultSet.getString(9);

            //System.out.println("Parsing talent attributes");

            resHero.talents.physicalTalents = parseTalents(tmp_PHYSICAL,"PHYSICAL_TALENTS");
            resHero.talents.societyTalents = parseTalents(tmp_SOCIETY,"SOCIETY_TALENTS");
            resHero.talents.natureTalents = parseTalents(tmp_NATURE,"NATURE_TALENTS");
            resHero.talents.knowledgeTalents = parseTalents(tmp_KNOWLEDGE,"KNOWLEDGE_TALENTS");
            resHero.talents.craftingTalents = parseTalents(tmp_CRAFTING,"CRAFT_TALENTS");
            resHero.talents.languagesAndWritingTalents = parseTalents(tmp_LANGUAGE,"LAN_WRITE_TALENTS");
            resHero.talents.ritualTalents = parseTalents(tmp_RITUALS,"RITUALS_TALENTS");
            //System.out.println("Parsing fighting_talent attributes");
            //System.out.println(tmp_FIGHT);
            resHero.talents.fightingTalents = parseFightTalents(tmp_FIGHT);

        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("ResultSet was Empty: Trying to read hero Data resultSet in a empty sql result");
        }
        System.out.println("Found hero with name: " + resHero.getName());
        return resHero;
    }

    /*public ResolvedHero getChar(String name){
        Hero hero = new Hero();
        ResolvedHero resHero = new ResolvedHero();
        return resHero;
    }*/

    public ArrayList<Talent> parseTalents(String talentString, String TABLE){
        ArrayList<Talent> retList = new ArrayList<Talent>();
        ArrayList<String> subStrings = new ArrayList<String>();
        /*

            <TalentId>/<Skilled>;<Tale...

         */
        Talent talent = new Talent();
        //System.out.println("Parsing: " + talentString);
        int lastI = 0;
        int talentId;
        int talentSkill;
        ResultSet resultSet;
        int i = 0;
        while(talentString.length() > 0){
            if (talentString.charAt(i) == ';'){
                subStrings.add(talentString.substring(0,i));
                talentString = talentString.substring(i+1);
                i = 0;
            }
            i+=1;
        }

        for (String s : subStrings){
            talent = new Talent();
            //System.out.println(s);
            //System.out.println(s.substring(0, s.indexOf('/')));
            talentId = Integer.parseInt(s.substring(0, s.indexOf('/')));
            talentSkill = Integer.parseInt(s.substring(s.indexOf("/") + 1, s.length()));
            //System.out.println(talentSkill);
            //System.out.println(talentId + " / " + talentSkill);

            resultSet = executeSQL("SELECT * FROM " + TABLE + " WHERE ID = " + talentId);
            try {
                resultSet.next();
                talent.setName(resultSet.getString(2));
                talent.setProp1(convertPropertyName(resultSet.getString(3)));
                talent.setProp2(convertPropertyName(resultSet.getString(4)));
                talent.setProp3(convertPropertyName(resultSet.getString(5)));
                talent.setSkilled(talentSkill);
                retList.add(talent);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return retList;
    }

    public ArrayList<FightingTalent> parseFightTalents(String talentString){
        ArrayList<FightingTalent> retList = new ArrayList<FightingTalent>();
        ArrayList<String> subStrings = new ArrayList<String>();
        /*

            <TalentId>/<sAT>A<sPA>P<sFK>;<Tale...

         */
        FightingTalent talent = new FightingTalent();

        int talentId;
        int sAT;
        int sPA;
        int sFK;
        ResultSet resultSet;

        int i = 0;
        while(talentString.length() > 0){
            if (talentString.charAt(i) == ';'){
                subStrings.add(talentString.substring(0,i));
                talentString = talentString.substring(i+1);
                i = 0;
            }
            i+=1;
        }

        for (String s : subStrings){
            talent = new FightingTalent();
            talentId = Integer.parseInt(s.substring(0, s.indexOf('/')));
            sAT = Integer.parseInt(s.substring(s.indexOf('/')+1 ,s.indexOf('A')));
            sPA = Integer.parseInt(s.substring(s.indexOf('A')+1 ,s.indexOf('P')));
            sFK = Integer.parseInt(s.substring(s.indexOf('P')+1));


            resultSet = executeSQL("SELECT * FROM FIGHT_TALENTS WHERE ID = " + talentId);
            try {
                resultSet.next();
                talent.setName(resultSet.getString(2));
                talent.setNecessaryAT(resultSet.getBoolean(3));
                talent.setNecessaryPA(resultSet.getBoolean(4));
                talent.setNecessaryFK(resultSet.getBoolean(5));
                talent.setSkilledAT(sAT);
                talent.setSkilledPA(sPA);
                talent.setSkilledFK(sFK);
                retList.add(talent);
                //System.out.println(talent.getName() + " / " + sAT + " " + sPA + " " + sFK);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return retList;
    }

    public ArrayList<String> loadCultures(){
        ArrayList<String> cultures = new ArrayList<>();
        ResultSet resultSet = executeSQL("SELECT * FROM CULTURE");
        try {
            while (resultSet.next()) {
                cultures.add(resultSet.getNString(2));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        Collections.sort(cultures);
        this.cultures = cultures;
        return cultures;
    }

    public ArrayList<String> loadRaces(){
        ArrayList<String> races = new ArrayList<>();
        ResultSet resultSet = executeSQL("SELECT * FROM RACES");
        try {
            while (resultSet.next()) {
                races.add(resultSet.getNString(2));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        Collections.sort(races);
        this.races = races;
        return races;
    }

    private Integer insertTalent(String tableName, Talent t){
        int c = getAmountOfRows(tableName);
        execute("INSERT INTO " + tableName+ " VALUES (" + (c + 1) + ",'" + t.getName() + "','" +
                Property.convertToShort(t.getProp1()) + "','" +
                Property.convertToShort(t.getProp2()) + "','" +
                Property.convertToShort(t.getProp3()) + "')");
        return c;
    }

    private Integer insertTalent(String tableName, FightingTalent t){
        int c = getAmountOfRows(tableName);
        execute("INSERT INTO " + tableName + " VALUES (" + (c + 1) + ",'" + t.getName() + "','" +
                t.getNecessaryAT().toString() + "','" +
                t.getNecessaryPA().toString() + "','" +
                t.getNecessaryFK().toString() + "')");
        return c;
    }

    private Property.PropertyName convertPropertyName(String shorted){
        if(shorted.equals("MU") || shorted.equals("mu")){
            return Property.PropertyName.MUT;
        }else if(shorted.equals("IN") || shorted.equals("in")){
            return Property.PropertyName.INTUITION;
        }else if(shorted.equals("KL") || shorted.equals("kl")){
            return Property.PropertyName.KLUGHEIT;
        }else if(shorted.equals("CH") || shorted.equals("ch")){
            return Property.PropertyName.CHARISMA;
        }else if(shorted.equals("GE") || shorted.equals("ge")){
            return Property.PropertyName.GEWANDTHEIT;
        }else if(shorted.equals("FF") || shorted.equals("ff")){
            return Property.PropertyName.FINGERFERTIGKEIT;
        }else if(shorted.equals("KO") || shorted.equals("ko")){
            return Property.PropertyName.KONSTITUTION;
        }else if(shorted.equals("KK") || shorted.equals("kk")){
            return Property.PropertyName.KOERPERKRAFT;
        }else if(shorted.equals("GS") || shorted.equals("gs")){
            return Property.PropertyName.GESCHWINDIGKEIT;
        }else{
            return null;
        }
    }

    //makes it a Singleton to access it from different classes
    public static DBOverwatch instance = new DBOverwatch();

}
