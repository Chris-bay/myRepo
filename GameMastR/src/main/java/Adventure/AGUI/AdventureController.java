package Adventure.AGUI;

import Adventure.CityMap.CityMapGenerator;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class AdventureController {
    @FXML
    private AnchorPane mainPane;

    private ArrayList<TitledPane> panes = new ArrayList<>();
    private CityMapGenerator cityMapGenerator = new CityMapGenerator();

    public void initialize(){
        mainPane.getChildren().addAll(panes);
        //using a two-parameter constructor
        /*
        TitledPane tp = new TitledPane("My Titled Pane", new Button("Button"));
        tp.setText("My Titled Pane");
        tp.setContent(new Button("Button"));
        mainPane.getChildren().add(tp);*/
    }

    public void addCityMap(){
        TitledPane newPane = getNewTitledPane();
        AnchorPane anchorPane = (AnchorPane)newPane.getContent();
        newPane.setText("City Map");
        Canvas canvas = new Canvas();
        canvas.setHeight(500);
        canvas.setWidth(500);

        //gc.fillRect(gc.getCanvas().getLayoutX(), gc.getCanvas().getLayoutY(), gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        anchorPane.getChildren().add(canvas);
        System.out.println("added new City map");
        panes.add(newPane);
        mainPane.getChildren().add(panes.get(panes.size()-1));
    }

    private TitledPane getNewTitledPane(){
        TitledPane titledPane = new TitledPane();
        titledPane.setContent(new AnchorPane());
        titledPane.setAnimated(false);
        return titledPane;
    }

    public void drawMap(){
        /*
        System.out.println();
        System.out.println("##########################################");
        System.out.println("################ Draw Map ################");
        System.out.println("##########################################");
        System.out.println();//*/
        Canvas canvas = (Canvas)((AnchorPane)panes.get(panes.size()-1).getContent()).getChildren().get(0);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        cityMapGenerator.createMap(gc);
    }

}
