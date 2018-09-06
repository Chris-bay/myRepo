package GUI;

import Universe.Money;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.h2.jdbcx.JdbcDataSource;
//import javafx.application.Application;
import org.h2.tools.Server;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class GameMastR extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));

        Scene scene = new Scene(root, 300, 275);

        stage.setTitle("FXML Welcome");
        stage.setScene(scene);
        stage.show();

        Database.DBOverwatch dbOverwatch = new Database.DBOverwatch();
        Money money1 = new Money(0,0,1,1);
        Money money2 = new Money(10,1,9,18);
        System.out.println(money1);
        System.out.println(money2);
        money2.addMoney(money1);
        System.out.println(money2);
        money2.substractMoney(money1);
        System.out.println(money2);

        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:./data/database");
        ds.setUser("sa");
        ds.setPassword("");
        try {
            Connection conn = ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalStateException e){
            System.out.println("File is locked");
        }
        //dbOverwatch.newTable("testname");
    }
}
