// package com.sfb805.application;
//
// import javax.swing.JOptionPane;
//
// import com.sfb805.controller.Controller;
// import com.sfb805.udo.UDOCollection;
//
// import javafx.application.Application;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Scene;
// import javafx.scene.image.Image;
// import javafx.scene.layout.AnchorPane;
// import javafx.stage.Stage;
//
/// **
// *
// * @author Max
// *
// */
// public class GUImain extends Application implements Runnable {
// private Main pelops;
// private UDOCollection udoCollection;
// private Stage primaryStage;
// private Controller controller;
// private FXMLLoader loader;
//
// // public GUImain() {
// //
// // }
//
// /*
// * (non-Javadoc)
// *
// * @see javafx.application.Application#start(javafx.stage.Stage)
// */
// @Override
// public void start(Stage primaryStage) {
//
// this.primaryStage = primaryStage;
// mainWindow();
// }
//
// /**
// * X this method constructs the scene and the stage to be displayed
// */
// public void mainWindow() {
//
// try {
//
// this.setLoader();
// AnchorPane pane = this.getLoader().load();
//
// this.setController(loader);
// controller.setTextAreaStreamLogger();
// controller.setMain(this);
//
// // this.setUdoCollection();
// // this.setPelops();
//
// Scene scene = new Scene(pane);
//
// primaryStage.setScene(scene);
// primaryStage.getIcons()
// .add(new
// Image(getClass().getResource("/com/sfb805/view/icons/Logo_SFB.png").toString()));
//
// primaryStage.show();
// } catch (Exception e) {
// e.printStackTrace();
// }
//
// }
//
// /**
// * this method sets the FXML doc loader objects
// */
// public void setLoader() {
// //
// System.out.println(getClass().getResource("/com/sfb805/view/OntologyGUIView.fxml").toString());
// loader = new
// FXMLLoader(getClass().getResource("/com/sfb805/view/OntologyGUIView.fxml"));
// }
//
// /**
// *
// * @return
// */
// public FXMLLoader getLoader() {
// return loader;
// }
//
// public void setPelops() {
// this.pelops = new Main();
// }
//
// public Main getPelops() {
// return pelops;
// }
//
// public void setUdoCollection() {
// this.udoCollection = new UDOCollection();
// }
//
// public UDOCollection getUdoCollection() {
// return udoCollection;
// }
//
// /**
// *
// * @return
// */
// public Stage getPrimaryStage() {
// return primaryStage;
// }
//
// /**
// *
// * @param primaryStage
// */
// public void setPrimaryStage(Stage primaryStage) {
// this.primaryStage = primaryStage;
// }
//
// /**
// *
// * @param loader
// */
// public void setController(FXMLLoader loader) {
// this.controller = loader.getController();
// }
//
// /**
// *
// * @return
// */
// public Controller getController() {
// return controller;
// }
//
// /**
// *
// * @param args
// */
// public static void main(String[] args) {
//
// try {
// new GUImain().run();
// } catch (Exception e) {
// JOptionPane.showMessageDialog(null, e);
// }
// }
//
// @Override
// public void run() {
// javafx.application.Application.launch();
// }
// }
