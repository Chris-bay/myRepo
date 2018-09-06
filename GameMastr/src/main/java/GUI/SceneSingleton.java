package GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SceneSingleton {
    public Map<String, String> parentMap = new HashMap<String, String>();

    public static SceneSingleton instance = new SceneSingleton();
}
