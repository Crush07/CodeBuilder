package com.hysea.hyseaappapi;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Window extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {


        Button btn = new Button("b1");
        btn.setOnAction(new javafx.event.EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Button bu = (Button) event.getSource();

                System.out.println(bu.getText() + "单击");
            }
        }); 

        FlowPane pane=new FlowPane();
        pane.getChildren().add(btn);

        Scene scene=new Scene(pane);
        
        primaryStage.setScene(scene);
        primaryStage.setWidth(1000);
        primaryStage.setHeight(500);
        primaryStage.show();
    }   
}