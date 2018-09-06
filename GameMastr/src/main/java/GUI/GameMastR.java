package GUI;

import Database.DBOverwatch;
import GUI.SceneSingleton;
import Universe.Money;
import com.sun.javafx.tk.Toolkit;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.h2.jdbc.JdbcSQLException;
import org.h2.jdbcx.JdbcDataSource;
//import javafx.application.Application;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

public class GameMastR extends Application {
    private static SceneSingleton staticSceneManager = SceneSingleton.instance;
    private SceneSingleton sceneManager = SceneSingleton.instance;
    private static Stage mainStage;

    public static void setScene(String name){
        //Scene scene = new Scene((staticSceneManager.parentMap.get(name)), 1060, 634);
        // close the main Window to make navigation easier

        mainStage.close();
        Stage newStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        try {
            //newStage.setScene(new Scene((Parent) FXMLLoader.load(new File(staticSceneManager.parentMap.get(name)).toURL()), 1060, 634));
            loader.setLocation(new File(staticSceneManager.parentMap.get(name)).toURL());
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root, 1060, 634);
            newStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //reopens the main Window if the current one is closed
        if (!name.equals("main")){

            newStage.setOnHiding(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            //System.out.println("Application Closed by click to Close Button(X)");
                            setScene("main");
                        }
                    });
                }
            });
        }
        newStage.getIcons().add(new Image("logo.png"));
        newStage.setTitle(name);
        mainStage = newStage;
        mainStage.show();
        if(name.equals("main")){
            MainController mainController = loader.getController();
            mainController.resizeScene();
        }
        System.out.println("Changing window to " + name);
    }

    public static void editHeros(int id){
        mainStage.close();
        Stage newStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(new File(staticSceneManager.parentMap.get("editChar")).toURL());
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root, 1060, 634);
            newStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //reopens the main Window if the current one is closed
        newStage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.runLater(() -> {
                    //System.out.println("Application Closed by click to Close Button(X)");
                    setScene("main");
                });
            }
        });
        newStage.getIcons().add(new Image("logo.png"));
        newStage.setTitle("Edit Hero");
        EditHerosController editHerosController = loader.getController();
        editHerosController.setHeroId(id);
        mainStage = newStage;
        mainStage.show();
        System.out.println("Changing window to editHeros");
        editHerosController.displayHero(id);
    }

    public static File openFileChooser()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel XLSM", "*.xlsm"),
                new FileChooser.ExtensionFilter("Excel XLSX", "*.xlsx")
        );
        fileChooser.setTitle("HeldenDatei öffnen");
        return fileChooser.showOpenDialog(mainStage);
    }

    @Override
    public void start(Stage stage) throws IOException {

//        MainController mainController = new MainController();
//
//        stage.setScene(new Scene(mainController));
//        stage.setTitle("GameMastR");
//        stage.setWidth(1060);
//        stage.setHeight(634);
//        stage.show();

        ///*
        mainStage = stage;
        SceneSingleton sceneManager = SceneSingleton.instance;
        sceneManager.parentMap.put("main", "src/main/java/GUI/Main.fxml");
        sceneManager.parentMap.put("editChar", "src/main/java/GUI/editHeros.fxml");
        sceneManager.parentMap.put("loadFromExcel", "src/main/java/GUI/loadFromExcel.fxml");
        sceneManager.parentMap.put("createChar", "src/main/java/GUI/createHero.fxml");
        stage.getIcons().add(new Image("logo.png"));
        //URL url = new File("src/main/java/GUI/Main.fxml").toURL();
        //Parent root = FXMLLoader.load(url);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new File("src/main/java/GUI/Main.fxml").toURL());
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root, 1060, 634);

        mainStage.setTitle("GameMastR");
        mainStage.setScene(scene);
        mainStage.show();
        MainController mainController = loader.getController();
        mainController.resizeScene();
        //*/
        DBOverwatch dboverwatch = DBOverwatch.instance;


        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:./data/database");
        ds.setUser("sa");
        ds.setPassword("");
        try {
            Connection conn = ds.getConnection();
        } catch (IllegalStateException e){
            System.out.println("File is locked");
        } catch (JdbcSQLException e){
            System.out.println("Datenbank momentan in Benutzung");
            //e.printStackTrace();
        } catch (SQLException e)
        {
            //e.printStackTrace();
        }

    }
}
