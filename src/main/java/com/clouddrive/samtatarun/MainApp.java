package com.clouddrive.samtatarun;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ts.SceneNavigator;


public class MainApp extends Application {

    private double xOffset = 0;
    private double yOffset = 0;
    
    @Override
    public void start(final Stage stage) {
         System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty(
                "com.apple.mrj.application.apple.menu.about.name", "FameDen Inc.");
        SceneNavigator sceneNavigator = new SceneNavigator();
        sceneNavigator.loadScreen("registration", "LoginRegistrationSceneFXML.fxml");
        sceneNavigator.setScreen("registration");

        FlowPane root = new FlowPane();
        root.getChildren().addAll(sceneNavigator);
        root.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
        root.autosize();
        Scene scene = new Scene(root);

        stage.initStyle(StageStyle.UNDECORATED);
        stage.sizeToScene();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setTitle("CloudDrive Inc.");

        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
