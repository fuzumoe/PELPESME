// package com.sfb805.controller;
//
// import java.io.BufferedReader;
// import java.io.IOException;
// import java.io.InputStreamReader;
// import java.io.OutputStream;
// import java.io.PrintStream;
// import java.rmi.RemoteException;
//
// import com.sfb805.application.GUImain;
// import com.sfb805.application.Main;
// import com.sfb805.view.NetworkGraphViewer;
//
// import javafx.fxml.FXML;
// import javafx.scene.Scene;
// import javafx.scene.control.Accordion;
// import javafx.scene.control.Button;
// import javafx.scene.control.TextArea;
// import javafx.scene.layout.Pane;
// import javafx.scene.paint.Color;
// import javafx.stage.Stage;
// import nxopen.NXException;
//
/// **
// *
// * @author Max
// *
// */
// public class Controller extends FlowControll {
//
// private GUImain gUimain;
// private Main main;
//
// private PrintStream printStream;
// public BufferedReader reader;
// private Stage primaryStage;
// private NetworkGraphViewer networkGraphViewer;
// private String viewAddress;
//
// public Controller() {
// super();
// }
//
// /**
// *
// * @param main
// */
// public void setMain(Main main) {
// // file:///F:/workspace/OntologyGUI/src/view/index.html#sioc
// // file:///F:/workspace/OntologyGUI/src/view/index.html#muto
// // file:///F:/workspace/OntologyGUI/src/view/index.html#foaf
// this.main = main;
// System.out.println("Constructing flow controll...");
// this.setFlowControll(restoreOne, restoreTwo, restoreThree, btnOne, btnTwo,
// btnThree, btnFour, btnFive,
// firstUpperPane, firstLowerPane, secondUpperPane, secondLowerPane,
// thirdUpperPane, thirdLowerPane,
// btnBoxOne, btnBoxTwo, btnBoxThree, paneBoxThree, paneBoxOne, paneBoxTwo);
//
// this.intilize();
// System.out.println("Hello World");
// System.out.println("main.toString() " + main.toString());
// // System.out.println("Pelops:" + main.getPelops().toString());
// //System.out.println("UdoCollection:" + main.getUdoCollection().toString());
//
// System.out.println("Constructed flow controll");
// this.main.getPrimaryStage().setResizable(false);
// // this.main.getPrimaryStage().setFullScreen(true);
// this.setFlow(1);
//
// }
//
// /**
// *
// * @param main
// */
// public void setMain(GUImain gUimain) {
// // file:///F:/workspace/OntologyGUI/src/view/index.html#sioc
// // file:///F:/workspace/OntologyGUI/src/view/index.html#muto
// // file:///F:/workspace/OntologyGUI/src/view/index.html#foaf
// this.gUimain = gUimain;
// System.out.println("Constructing flow controll...");
// this.setFlowControll(restoreOne, restoreTwo, restoreThree, btnOne, btnTwo,
// btnThree, btnFour, btnFive,
// firstUpperPane, firstLowerPane, secondUpperPane, secondLowerPane,
// thirdUpperPane, thirdLowerPane,
// btnBoxOne, btnBoxTwo, btnBoxThree, paneBoxThree, paneBoxOne, paneBoxTwo);
//
// this.intilize();
// System.out.println("Constructed flow controll");
// this.main.getPrimaryStage().setResizable(false);
// // this.main.getPrimaryStage().setFullScreen(true);
// this.setFlow(1);
//
// }
//
// /*** Scene to stage setter ***/
// public void setSceneToStage() {
// // create the scene
// this.setPrimaryStage(new Stage());
//
// this.primaryStage.setTitle("Ontology Network Graph");
// Scene scene = new Scene(getNetworkGraphViewer(), 750, 500,
// Color.web("#666970"));
// this.primaryStage.setScene(scene);
//
// primaryStage.show();
// }
//
// /*** Network Graph view (scene) getters and setters ***/
// /**
// *
// * @return
// */
// public NetworkGraphViewer getNetworkGraphViewer() {
// return networkGraphViewer;
// }
//
// /**
// *
// * @param networkGraphViewer
// */
// public void setNetworkGraphViewer(NetworkGraphViewer networkGraphViewer) {
// this.networkGraphViewer = networkGraphViewer;
// }
//
// /*** Network Graph stage setters and getters ****/
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
// /**** Button Event handlers ****/
//
// /**
// * handle for T-BOX 1
// *
// * @throws IOException
// */
// public void handlebtnBoxOne() throws IOException {
// viewAddress = "file:///F:/workspace/OntologyFxGUI/index.html#foaf";
// GraphController graphController = new GraphController();
// graphController.setComand(Comands.TYPE_ONE);
// graphController.proccessGraph();
//
// setNetworkGraphViewer(new NetworkGraphViewer(viewAddress));
// setSceneToStage();
//
// }
//
// /**
// * handle for T-BOX
// *
// * @throws IOException
// */
// public void handlebtnBoxTwo() throws IOException {
//
// viewAddress = "file:///F:/workspace/OntologyFxGUI/index.html#muto";
//
// // Code Goes here
// GraphController graphController = new GraphController();
// graphController.setComand(Comands.TYPE_TWO);
// graphController.proccessGraph();
// setNetworkGraphViewer(new NetworkGraphViewer(viewAddress));
// setSceneToStage();
//
// System.out.println("yap working just fine T-BOX");
// }
//
// /**
// * handle for A BOX 2
// *
// * @throws IOException
// */
// public void handlebtnBoxThree() throws IOException {
//
// viewAddress = "file:///F:/workspace/OntologyFxGUI/index.html#ontovibe";
//
// // Code Goes here
// GraphController graphController = new GraphController();
// graphController.setComand(Comands.TYPE_THREE);
// graphController.proccessGraph();
// setNetworkGraphViewer(new NetworkGraphViewer(viewAddress));
// setSceneToStage();
//
// }
//
// /**
// * handle for buttonOne
// */
// public void handleButtonOne() {
//
// // do not remove the three first three statements
// this.setFlow(1);
// this.setFlowView(FlowControll.EVT_SOURCE_BUTTON_ONE);
//
// // Code Goes here
// System.out.println("yap working just fine ONE");
//
// }
//
// /**
// * handle for buttonTwo
// */
// public void handleButtonTwo() {
// // do not remove the three first three statements
//
// this.setFlowView(FlowControll.EVT_SOURCE_BUTTON_TWO);
//
// System.out.println("yap working just fine TWO");
//
// }
//
// /**
// * /** handle for buttonThree
// */
//
// public void handleButtonThree() {
// // Code Goes here
// this.setFlowView(FlowControll.EVT_SOURCE_BUTTON_THREE);
// System.out.println("yap working just fine THREE");
//
// }
//
// /**
// * handle for buttonFour
// */
// public void handleButtonFour() {
// btnFour.getStyleClass().remove("backward-button");
// btnFour.getStyleClass().add("backward-one-button");
// paneBoxThree.getStyleClass().remove("big-container-borderless");
// paneBoxThree.getStyleClass().add("big-container");
// btnBoxThree.setVisible(true);
// // Code Goes here
//
// System.out.println("yap working just fine FOUR");
//
// }
//
// /**
// * handle for buttonFive
// */
// public void handleButtonFive() {
// // Code Goes here
//
// System.out.println("yap working just fine FIVE");
//
// }
//
// /**
// * handle for button P
// *
// * @throws NXException
// * @throws RemoteException
// */
// public void handleButtonOneOne() throws RemoteException, NXException {
// // Code Goes here
// System.out.println("yap working just fine handleButtonOneOne");
//
// // System.out.println("TheUI: " +
// // main.getUdoCollection().theUI.toString());
// main.setUdoCollection();
// main.getUdoCollection().normalDistributionUDO();
//
// }
//
// /**
// * handle for button E
// */
// public void handlButtonOneTwo() {
// // Code Goes here
// System.out.println("yap working just fine handleButtonOneTwo");
//
// }
//
// /**
// * handle for button Individual
// */
// public void handleButtonTwoOne() {
// // Code Goes here
//
// System.out.println("yap working just fine handleButtonTwoOne");
//
// }
//
// /**
// * handle for button Class
// */
// public void handleButtonTwoTwo() {
// // Code Goes here
//
// System.out.println("yap working just fine handleButtonTwoTwo");
//
// }
//
// /**
// * handle for button S
// */
// public void handleButtonThreeOne() {
// System.out.println("yap working just fine handleButtonThreeOne");
//
// }
//
// /**
// * handle for button p
// */
// public void handleButtonThreeTwo() {
// System.out.println("yap working just fine handleButtonThreeTwo");
//
// }
//
// /**
// * handle for button O
// */
// public void handleButtonThreeThree() {
// System.out.println("yap working just fine handleButtonThreeThree");
//
// }
//
// public void hanldeRestoreButtonOne() {
// this.setViewNum(FlowControll.VIEW_ONE);
// this.restoreView();
// System.out.println("yap working fine restore button one");
// }
//
// public void hanldeRestoreButtonTwo() {
// this.setViewNum(FlowControll.VIEW_TWO);
// this.restoreView();
// System.out.println("yap working fine restore button two");
// }
//
// public void hanldeRestoreButtonThree() {
// this.setViewNum(FlowControll.VIEW_THREE);
// this.restoreView();
// System.out.println("yap working fine restore button three");
// }
//
// /**
// * this method will connect the logging text area with the buffered reader
// * system.in
// */
// public void setTextAreaStreamLogger() {
// reader = new BufferedReader(new InputStreamReader(System.in));
// printStream = new PrintStream(new CustomOutputStream(textArea));
// System.setOut(printStream);
// System.setErr(printStream);
// System.out.println("***********************************");
// System.out.println(":....System-Intiating....:");
// System.out.println("***********************************");
// }
//
// /** :: view compnents declared down:: **/
//
// // Buttons declaration
// @FXML
// Button btnOne;
// @FXML
// Button btnTwo;
// @FXML
// Button btnThree;
// @FXML
// Button btnFour;
// @FXML
// Button btnFive;
// @FXML
// Button btnOneOne;
// @FXML
// Button btnOneTwo;
// @FXML
// Button btnTwoOne;
// @FXML
// Button btnTwoTwo;
// @FXML
// Button btnThreeOne;
// @FXML
// Button btnThreeTwo;
// @FXML
// Button btnThreeThree;
// @FXML
// Accordion accordionOne;
// @FXML
// Button btnBoxOne;
// @FXML
// Button btnBoxTwo;
// @FXML
// Button btnBoxThree;
//
// @FXML
// Button restoreOne;
// @FXML
// Button restoreTwo;
// @FXML
// Button restoreThree;
//
// // Panes declaration
// @FXML
// Pane paneBoxOne;
// @FXML
// Pane paneBoxTwo;
// @FXML
// Pane paneBoxThree;
// @FXML
// Pane firstLowerPane;
// @FXML
// Pane firstUpperPane;
// @FXML
// Pane secondLowerPane;
// @FXML
// Pane secondUpperPane;
// @FXML
// Pane thirdLowerPane;
// @FXML
// Pane thirdUpperPane;
//
// // TextAreas declaration
// @FXML
// TextArea textArea;
// }
//
/// **
// *
// * @author Max
// *
// */
// class CustomOutputStream extends OutputStream {
//
// private TextArea textArea;
//
// /**
// *
// * @param textArea
// */
// public CustomOutputStream(TextArea textArea) {
// this.textArea = textArea;
// }
//
// /**
// * @param b
// */
// @Override
// public void write(int b) throws IOException {
// // redirects data to the text area
// textArea.appendText(String.valueOf((char) b));
// // scrolls the text area to the end of data
// textArea.positionCaret(textArea.getText().length());
//
// }
// }
