//package com.nepse.ui;
//
///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//
//import javafx.application.Application;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Font;
//import javafx.scene.text.FontWeight;
//import javafx.scene.text.Text;
//import javafx.stage.Stage;
//
///**
// *
// * @author BPun
// */
//public class FormDemo extends Application {
//    
//    @Override
//    public void start(Stage primaryStage) {
//        primaryStage.setTitle("Nepse App");    
//        GridPane root = new GridPane();
//        root.setAlignment(Pos.CENTER);
//        root.setHgap(10);
//        root.setVgap(10);
//        root.setPadding(new Insets(10, 10, 10, 10));
//        
//        Text text = new Text("Login Page");
//        text.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
//        root.add(text, 0, 0, 2, 1);
//        
//        Label targetDirectory = new Label("Target Directory");
//        root.add(targetDirectory, 0, 1);
//        
//        
//        TextField target = new TextField();
//        target.setText("c:\\tmp");
//        root.add(target, 1, 1);
// 
//        Label pw = new Label("For Live Data press here");
//        root.add(pw, 0, 2);
//        
//        
//
//        Button liveButton = new Button("Live Data");
//        root.add(liveButton, 1, 2);
//        
//        Button btn = new Button("submit");
//        
//        HBox hbox = new HBox();
//        hbox.setAlignment(Pos.BOTTOM_RIGHT);
//        hbox.getChildren().add(btn);
//        
//        final Text textValue = new Text();
//        
//        btn.setOnAction(new EventHandler<ActionEvent>() {
//
//             @Override
//             public void handle(ActionEvent event) {
//                textValue.setFill(Color.FIREBRICK);
//                textValue.setText("Incorect Passoword");
//             }
//         });
//        
//        root.add(hbox, 1, 3);
//        
//        root.add(textValue, 1, 4);
//        Scene scene = new Scene(root, 300, 275);
//        
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) {
//        launch(args);
//    }
//    
//}
//
