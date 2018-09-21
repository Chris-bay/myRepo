package Adventure.AGUI;

import Adventure.CityMap.CityMapGenerator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class AdventureController {
    @FXML
    private AnchorPane mainPane;

    private ArrayList<TitledPane> panes = new ArrayList<>();
    private ArrayList<MapTitledPane> mapPanes = new ArrayList<>();
    private CityMapGenerator cityMapGenerator = new CityMapGenerator();
    public static boolean drawFillPolyPreset = true;
    public static boolean drawPolyEdgesPreset = true;
    public static boolean drawPolyPointsPreset = false;
    public static boolean drawStartPointsPreset = false;
    public static boolean fillPolyUnicolorPreset = false;

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
        MapTitledPane newPane = getNewMapTitledPane();
        AnchorPane contentPane = newPane.contentPane;
        newPane.setText("City Map");
        newPane.canvas.setHeight(500);
        newPane.canvas.setWidth(500);
        MenuBar menuBar = newPane.menuBar;
        Menu debug = new Menu();
        debug.setText("debug");
        CheckMenuItem drawStartPoints = new CheckMenuItem("Draw Start Points");
        drawStartPoints.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setDrawStartPoints(drawStartPoints.isSelected());
            }
        });
        drawStartPoints.setSelected(drawStartPointsPreset);
        CheckMenuItem drawPolyPoints = new CheckMenuItem("Draw Poly Points");
        drawPolyPoints.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setDrawPolyPoints(drawPolyPoints.isSelected());
            }
        });
        drawPolyPoints.setSelected(drawPolyPointsPreset);
        CheckMenuItem drawPolyEdges = new CheckMenuItem("Draw Poly Edges");
        drawPolyEdges.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setDrawPolyEdges(drawPolyEdges.isSelected());
            }
        });
        drawPolyEdges.setSelected(drawPolyEdgesPreset);
        CheckMenuItem drawFillPoly = new CheckMenuItem("Fill Poly");
        drawFillPoly.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setFillPoly(drawFillPoly.isSelected());
            }
        });
        drawFillPoly.setSelected(drawFillPolyPreset);
        Menu newMap = new Menu("Generate");
        MenuItem newMapItem = new MenuItem("new Map");
        newMapItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                drawMap(newPane);
            }
        });

        debug.getItems().addAll(drawFillPoly, drawPolyEdges, drawPolyPoints, drawStartPoints);
        newMap.getItems().add(newMapItem);
        menuBar.getMenus().addAll(debug, newMap);

        newPane.menuBar = menuBar;

        System.out.println("added new City map");
        mapPanes.add(newPane);
        mainPane.getChildren().add(mapPanes.get(mapPanes.size()-1));

        menuBar.setPrefHeight(25d);

        AnchorPane.setTopAnchor(menuBar, 0d);
        AnchorPane.setLeftAnchor(menuBar,0d);
        AnchorPane.setRightAnchor(menuBar,0d);
        //AnchorPane.setBottomAnchor(menuBar, ((AnchorPane)newPane.getContent()).getHeight()-menuBar.getHeight());

        AnchorPane.setTopAnchor(contentPane, 25d);
        //System.out.println("Menu Bar Height: " + menuBar.getHeight());
        AnchorPane.setLeftAnchor(contentPane,0d);
        AnchorPane.setRightAnchor(contentPane,0d);
        AnchorPane.setBottomAnchor(contentPane, 0d);
    }

    private TitledPane getNewTitledPane(){
        TitledPane titledPane = new TitledPane();
        titledPane.setContent(new AnchorPane());
        titledPane.setAnimated(false);
        return titledPane;
    }

    private MapTitledPane getNewMapTitledPane(){
        MapTitledPane titledPane = new MapTitledPane();
        AnchorPane anchorPane = new AnchorPane();
        MenuBar menuBar = new MenuBar();
        AnchorPane contentPane = new AnchorPane();

        Canvas canvas = new Canvas();
        contentPane.getChildren().add(canvas);

        anchorPane.getChildren().add(menuBar);
        anchorPane.getChildren().add(contentPane);

        titledPane.setContent(anchorPane);
        titledPane.menuBar = menuBar;
        titledPane.canvas = canvas;
        titledPane.graphicsContext = canvas.getGraphicsContext2D();
        titledPane.contentPane = contentPane;
        titledPane.setAnimated(false);
        return titledPane;
    }


    public void drawMap(MapTitledPane mapTitledPane){
        /*
        System.out.println();
        System.out.println("##########################################");
        System.out.println("################ Draw Map ################");
        System.out.println("##########################################");
        System.out.println();
        //*/

        GraphicsContext gc = mapTitledPane.graphicsContext;
        cityMapGenerator.initialize(gc);
        cityMapGenerator.createMap();
        cityMapGenerator.draw();
    }

    public void toggleDrawStartPoints(){
        cityMapGenerator.toggleDrawStartPoints();
        cityMapGenerator.draw();
    }
    public void toggleDrawPolyPoints(){
        cityMapGenerator.toggleDrawPolyPoints();
        cityMapGenerator.draw();
    }
    public void toggleDrawPolyEdges(){
        cityMapGenerator.toggleDrawPolyEdges();
        cityMapGenerator.draw();
    }
    public void toggleFillPoly(){
        cityMapGenerator.toggleFillPoly();
        cityMapGenerator.draw();
    }
    public void setDrawStartPoints(boolean value){
        cityMapGenerator.setDrawStartPoints(value);
        cityMapGenerator.draw();
    }
    public void setDrawPolyPoints(boolean value){
        cityMapGenerator.setDrawPolyPoints(value);
        cityMapGenerator.draw();
    }
    public void setDrawPolyEdges(boolean value){
        cityMapGenerator.setDrawPolyEdges(value);
        cityMapGenerator.draw();
    }
    public void setFillPoly(boolean value){
        cityMapGenerator.setFillPoly(value);
        cityMapGenerator.draw();
    }

}
