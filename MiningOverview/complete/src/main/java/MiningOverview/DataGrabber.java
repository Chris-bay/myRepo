package MiningOverview;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.time.LocalDate;
import java.time.LocalTime;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.net.ssl.HttpsURLConnection;

import java.net.URL;

// cA29A952103a59e0d62417307742A23e7476AB8e

class DataGrabber{

    static Miner miner = new Miner();
    static Pricing pricing = new Pricing();

    private final static String USER_AGENT = "Mozilla/5.0";

    static String minerHash = "cA29A952103a59e0d62417307742A23e7476AB8e";
    static String poolURL = "https://api.ethermine.org";


    //pathroot in src/main/
    static String PricingPath = "Pricing.json";
    static String minerPath = "Miner.json";
    static String completeMiningHistoryPath = "MiningHistory.json";

    //API URLs

    static String MinerURL = poolURL + "/miner/"+ minerHash + "/currentStats";
    static String PricingURL = poolURL + "/networkStats";

    public static void run(){

        Miner tmpMiner = new Miner();
        miner = readData();
        LocalTime messureTime;
        Integer messureNano;
        Integer messureSecond;
        Integer diffTime = 0;

        while(true){

            if(true || LocalTime.now().getMinute()%2 == 0){
                messureTime = LocalTime.now();
                messureNano = messureTime.getNano();
                messureSecond = messureTime.getSecond();
                try {
                    miner = getMinerDataFromURL();

                    addToMiningHistoryData(miner);
                    rewriteMinerData(miner);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                diffTime = (LocalTime.now().getSecond()-messureSecond)*1000000000
                        + LocalTime.now().getNano() - messureNano;

                System.out.println(getStandartDebugHeader()+"Refreshing current Mining data in: "
                        + diffTime/1000000 + " ms");
            }

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static Miner readData(){
        Miner tempminer = new Miner();



        return tempminer;
    }

    public static void rewriteMinerData(Miner miner){

        JSONObject obj = new JSONObject();

        obj.put("activeWorkers", miner.getActiveWorkers());
        obj.put("averageHashRate", miner.getAverageHashrate());
        obj.put("currentHashRate", miner.getCurrentHashRate());
        obj.put("validShares", miner.getValidShares());
        obj.put("invalidShares", miner.getInvalidShares());
        obj.put("staleShares", miner.getStaleShares());
        obj.put("unpaid", miner.getUnpaid());


        try (FileWriter file = new FileWriter(minerPath)) {

            ObjectMapper mapper = new ObjectMapper();
            file.write(mapper.defaultPrettyPrintingWriter().writeValueAsString(obj));
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addToMiningHistoryData(Miner miner){

        JSONArray array = new JSONArray();
        JSONObject obj = new JSONObject();

        JSONParser parser = new JSONParser();

        Object oldFileContent = null;

        try {
            BufferedReader in = new BufferedReader(new FileReader(completeMiningHistoryPath));//load File
            // buffer for storing file contents in memory
            StringBuffer stringBuffer = new StringBuffer("");
            // for reading one line
            String oldLine = null;
            // keep reading till readLine returns null
            while ((oldLine = in.readLine()) != null) {
                // keep appending last line read to buffer
                stringBuffer.append(oldLine);
            }

            //System.out.println("Length: " + stringBuffer.length());
            oldFileContent = parser.parse(stringBuffer.toString());//parse to object

        } catch (Exception e) {
            e.printStackTrace();
        }
        //JSONArray jsonArray = (JSONArray) oldFileContent;//convert object to jsonArray
        JSONObject jsonObject = (JSONObject) oldFileContent;




        //array.addAll(jsonArray);

        obj.put("activeWorkers", miner.getActiveWorkers());
        obj.put("averageHashRate", miner.getAverageHashrate());
        obj.put("currentHashRate", miner.getCurrentHashRate());
        obj.put("validShares", miner.getValidShares());
        obj.put("invalidShares", miner.getInvalidShares());
        obj.put("staleShares", miner.getStaleShares());
        obj.put("unpaid", miner.getUnpaid());

        //array.add(obj);

        jsonObject.put(jsonObject.size(),obj);

        //System.out.println("refresh Mining History");

        ObjectMapper mapper = new ObjectMapper();
        //mapper.writeValueAsString(jsonObject)
        try (FileWriter file = new FileWriter(completeMiningHistoryPath)) {
            file.write(mapper.defaultPrettyPrintingWriter().writeValueAsString(jsonObject));//NOT deprecated
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Miner getMinerDataFromURL(){
        Miner minerurl = new Miner();
        JSONParser parser = new JSONParser();

        Object obj = null;
        try {
            obj = parser.parse(sendGet(MinerURL));
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = (JSONObject) obj;
        /*
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("Parsed JSON object:");
        try {
            System.out.println(mapper.defaultPrettyPrintingWriter().writeValueAsString(jsonObject));
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        JSONObject entries = (JSONObject) jsonObject.get("data");


        minerurl.setDate(LocalTime.now().toString());

        if ((Integer)entries.get("activeWorkers") == null){
            minerurl.setActiveWorkers(0);
        }else{
            minerurl.setActiveWorkers((Integer)entries.get("activeWorkers"));
        }
        if (entries.get("currentHashrate")==null){
            minerurl.setCurrentHashRate(0.0);
        }else{
            minerurl.setCurrentHashRate((Double)entries.get("currentHashrate"));
        }
        if (entries.get("averageHashrate")==null){
            minerurl.setAverageHashrate(0.0);
        }else{
            minerurl.setAverageHashrate((Double)entries.get("averageHashrate"));
        }
        if (entries.get("invalidShares")==null){
            minerurl.setInvalidShares(Long.valueOf(0));
        }else{
            minerurl.setInvalidShares((Long)entries.get("invalidShares"));
        }
        if (entries.get("staleShares")==null){
            minerurl.setStaleShares(Long.valueOf(0));
        }else{
            minerurl.setStaleShares((Long)entries.get("staleShares"));
        }
        if (entries.get("validShares")==null){
            minerurl.setValidShares(Long.valueOf(0));
        }else{
            minerurl.setStaleShares((Long)entries.get("validShares"));
        }

        return minerurl;
    }

    private static String sendGet(String url) throws Exception {

        String retresp;

        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        //System.out.println("\nSending 'GET' request to URL : " + url);
        //System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        //System.out.println(response.toString());
        retresp = response.toString();
        return retresp;
    }

    public static Double parseStringToDouble(String string){
        Double returnValue;

        int i;
        for (i = string.length() - 1; i>=0; i--) {
            if (string.charAt(i) == 'e' || string.charAt(i) == 'E') {
                break;
            }
        }

        returnValue = Double.parseDouble(string.substring(0,i-1)) * Math.pow(10.0, Double.parseDouble(string.substring(i+1)));

        return returnValue;
    }
    public static String getStandartDebugHeader(){
        return LocalDate.now().toString() + " " + LocalTime.now().toString() + "  INFO " +
                ManagementFactory.getRuntimeMXBean().getName().substring(
                        0,ManagementFactory.getRuntimeMXBean().getName().indexOf('@'))+" --- " +
                "[    DataGrabber] MiningOverview.DataGrabber               : ";
    }
}