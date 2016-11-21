// package com.sfb805.controller;
//
// import javafx.scene.control.Button;
// import javafx.scene.layout.Pane;
//
// public abstract class FlowControll {
// /**
// *
// */
// public static int VIEW_ONE = 1;
// public static int VIEW_TWO = 2;
// public static int VIEW_THREE = 3;
// private int viewNum;
// /**
// * The following static int variables are used to illustrate the flow steps
// * of the view
// */
// public static int FLOW_ONE = 1;
// public static int FLOW_TWO = 2;
// public static int FLOW_THREE = 3;
// public static int FLOW_FOUR = 4;
// public static int FLOW_FIVE = 5;
//
// /**
// * the following static int variables are used to illustrate button the
// * event source
// */
// public static int EVT_SOURCE_BUTTON_ONE = 1;
// public static int EVT_SOURCE_BUTTON_TWO = 2;
// public static int EVT_SOURCE_BUTTON_THREE = 3;
// public static int EVT_SOURCE_BUTTON_FOUR = 4;
// public static int EVT_SOURCE_BUTTON_FIVE = 5;
// /**
// * dynamic flow and event source frivolous to be used as events are
// * triggered and view starts to flow proportionally
// */
// private int flow;
// private int eventSource;
// /**
// * Panes which are part of the flow view are declared here
// */
// private Pane FIRST_PANE_UPPER;
// private Pane FIRST_PANE_LOWER;
// private Pane SECOND_PANE_UPPER;
// private Pane SECOND_PANE_LOWER;
// private Pane THIRD_PANE_UPPER;
// private Pane THIRD_PANE_LOWER;
// private Pane SECOND_PANE_BOX_UPPER;
// private Pane SECOND_PANE_BOX_UPPER_LOWER;
// private Pane SECOND_PANE_BOX_UPPER_LOWER_LOWER;
// /**
// * Buttons which are part of the flow view are declared here
// */
// private Button FIRST_FLOW;
// private Button SECOND_FLOW;
// private Button THIRD_FLOW;
// private Button FOURTH_FLOW;
// private Button FIFTH_FLOW;
// private Button SECOND_FLOW_BOX_ONE;
// private Button SECOND_FLOW_BOX_TWO;
// private Button SECOND_FLOW_BOX_THREE;
// private Button RESTORE_BUTTON_ONE;
// private Button RESTORE_BUTTON_TWO;
// private Button RESTORE_BUTTON_THREE;
//
// /**
// *
// * @param FIRST_FLOW
// * @param SECOND_FLOW
// * @param THIRD_FLOW
// * @param FOURTH_FLOW
// * @param FIFTH_FLOW
// * @param FIRST_PANE_UPPER
// * @param FIRST_PANE_LOWER
// * @param SECOND_PANE_UPPER
// * @param SECOND_PANE_LOWER
// * @param THIRD_PANE_UPPER
// * @param THIRD_PANE_LOWER
// * @param SECOND_FLOW_BOX_ONE
// * @param SECOND_FLOW_BOX_TWO
// * @param SECOND_FLOW_BOX_THREE
// * @param SECOND_PANE_BOX_UPPER
// * @param SECOND_PANE_BOX_UPPER_LOWER
// * @param SECOND_PANE_BOX_UPPER_LOWER_LOWER
// */
// public void setFlowControll(Button RESTORE_BUTTON_ONE, Button
// RESTORE_BUTTON_TWO, Button RESTORE_BUTTON_THREE,
// Button FIRST_FLOW, Button SECOND_FLOW, Button THIRD_FLOW, Button FOURTH_FLOW,
// Button FIFTH_FLOW,
// Pane FIRST_PANE_UPPER, Pane FIRST_PANE_LOWER, Pane SECOND_PANE_UPPER, Pane
// SECOND_PANE_LOWER,
// Pane THIRD_PANE_UPPER, Pane THIRD_PANE_LOWER, Button SECOND_FLOW_BOX_ONE,
// Button SECOND_FLOW_BOX_TWO,
// Button SECOND_FLOW_BOX_THREE, Pane SECOND_PANE_BOX_UPPER, Pane
// SECOND_PANE_BOX_UPPER_LOWER,
// Pane SECOND_PANE_BOX_UPPER_LOWER_LOWER) {
// try {
// /** :::Set the restore button ::: **/
// this.setRESTORE_BUTTON_ONE(RESTORE_BUTTON_ONE);
// this.setRESTORE_BUTTON_TWO(RESTORE_BUTTON_TWO);
// this.setRESTORE_BUTTON_THREE(RESTORE_BUTTON_THREE);
// /*** :::Set the Five Sequential button::: ***/
// this.setFIRST_FLOW(FIRST_FLOW);
// this.setSECOND_FLOW(SECOND_FLOW);
// this.setTHIRD_FLOW(THIRD_FLOW);
// this.setFOURTH_FLOW(FOURTH_FLOW);
// this.setFIFTH_FLOW(FIFTH_FLOW);
// /*** :::set Second Flow Buttons::: ***/
// this.setSECOND_FLOW_BOX_ONE(SECOND_FLOW_BOX_ONE);
// this.setSECOND_FLOW_BOX_TWO(SECOND_FLOW_BOX_TWO);
// this.setSECOND_FLOW_BOX_THREE(SECOND_FLOW_BOX_THREE);
// /***
// * :::Set the Three Sequential Panes with their contained Panes:::
// ***/
// // FIRST UPPER AND LOWER PANES
// this.setFIRST_PANE_UPPER(FIRST_PANE_UPPER);
// this.setFIRST_PANE_LOWER(FIRST_PANE_LOWER);
// // SECOND UPPER AND LOWER PANES
// this.setSECOND_PANE_UPPER(SECOND_PANE_UPPER);
// this.setSECOND_PANE_LOWER(SECOND_PANE_LOWER);
// // THIRD UPPER AND LOWER PANES
// this.setTHIRD_PANE_UPPER(THIRD_PANE_UPPER);
// this.setTHIRD_PANE_LOWER(THIRD_PANE_LOWER);
// /*** :::Set the three pane box of the second pane::: ***/
// this.setSECOND_PANE_BOX_UPPER(SECOND_PANE_BOX_UPPER);
// this.setSECOND_PANE_BOX_UPPER_LOWER(SECOND_PANE_BOX_UPPER_LOWER);
// this.setSECOND_PANE_BOX_UPPER_LOWER_LOWER(SECOND_PANE_BOX_UPPER_LOWER_LOWER);
// } catch (Exception e) {
// e.printStackTrace();
// }
//
// }
//
// /**
// * this method initializes the control flow of the view
// */
// public void intilize() {
// /** ::Set the initial phase of the flow:: ***/
// try {
// System.out.println("Initilizing....");
// this.setFlow(1);
//
// this.getSECOND_PANE_BOX_UPPER().getStyleClass().remove("big-container");
// this.getSECOND_PANE_BOX_UPPER().getStyleClass().add("big-container-borderless");
// this.getSECOND_PANE_BOX_UPPER_LOWER().getStyleClass().remove("big-container");
// this.getSECOND_PANE_BOX_UPPER_LOWER().getStyleClass().add("big-container-borderless");
// this.getSECOND_PANE_BOX_UPPER_LOWER_LOWER().getStyleClass().remove("big-container");
// this.getSECOND_PANE_BOX_UPPER_LOWER_LOWER().getStyleClass().add("big-container-borderless");
//
// this.getSECOND_FLOW_BOX_ONE().setVisible(false);
// this.getSECOND_FLOW_BOX_TWO().setVisible(false);
// this.getSECOND_FLOW_BOX_THREE().setVisible(false);
// // FIRST UPPER AND LOWER PANES
// this.getFIRST_PANE_UPPER().setVisible(false);
// this.getFIRST_PANE_LOWER().setVisible(true);
// // SECOND UPPER AND LOWER PANES
// this.getSECOND_PANE_UPPER().setVisible(false);
// this.getSECOND_PANE_LOWER().setVisible(true);
// // THIRD UPPER AND LOWER PANES
// this.getTHIRD_PANE_UPPER().setVisible(false);
// this.getTHIRD_PANE_LOWER().setVisible(true);
//
// System.out.println("Initilized");
// } catch (Exception e) {
// e.printStackTrace();
//
// }
// }
//
// /**
// *
// * @param viewNum
// */
// public void restoreView() {
//
// if (this.getViewNum() == FlowControll.VIEW_ONE) {
//
// this.getFIRST_PANE_UPPER().setVisible(false);
// this.getFIRST_PANE_LOWER().setVisible(true);
//
// this.getFIRST_FLOW().getStyleClass().remove("forward-one-button");
// this.getFIRST_FLOW().getStyleClass().add("forward-button");
//
// System.out.println("restored view one");
//
// }
// if (this.getViewNum() == FlowControll.VIEW_TWO) {
// this.getSECOND_FLOW().getStyleClass().remove("forward-one-button");
// this.getSECOND_FLOW().getStyleClass().add("forward-button");
//
// this.getSECOND_PANE_LOWER().getStyleClass().remove("big-container");
// this.getSECOND_PANE_LOWER().getStyleClass().add("big-container-borderless");
//
// this.getSECOND_PANE_BOX_UPPER_LOWER().getStyleClass().remove("big-container");
// this.getSECOND_PANE_BOX_UPPER_LOWER().getStyleClass().add("big-container-borderless");
//
// this.getSECOND_PANE_BOX_UPPER_LOWER_LOWER().getStyleClass().remove("big-container");
// this.getSECOND_PANE_BOX_UPPER_LOWER_LOWER().getStyleClass().add("big-container-borderless");
//
// this.getSECOND_PANE_UPPER().setVisible(false);
// this.getSECOND_PANE_LOWER().setVisible(true);
//
// this.getSECOND_PANE_BOX_UPPER().setVisible(false);
// this.getSECOND_PANE_BOX_UPPER_LOWER().setVisible(false);
// this.getSECOND_PANE_BOX_UPPER_LOWER_LOWER().setVisible(false);
//
// this.getSECOND_FLOW_BOX_ONE().setVisible(false);
// this.getSECOND_FLOW_BOX_TWO().setVisible(false);
//
// System.out.println("restored view two");
// }
// if (this.getViewNum() == FlowControll.VIEW_THREE) {
// this.getTHIRD_FLOW().getStyleClass().remove("forward-button");
// this.getTHIRD_FLOW().getStyleClass().add("forward-one-button");
//
// this.getTHIRD_PANE_UPPER().setVisible(false);
// this.getTHIRD_PANE_LOWER().setVisible(true);
//
// System.out.println("restored view three");
// }
//
// }
//
// /*** :::FLOW CONTROL METHODS::: ***/
// /**
// *
// * @param currentButton
// */
// public void setFlowView(int EVT_SOURCE_BUTTON) {
// this.setEventSource(EVT_SOURCE_BUTTON);
// try {
// if (this.getFlow() == FlowControll.FLOW_ONE
// && this.getEventSource() == FlowControll.EVT_SOURCE_BUTTON_ONE) {
//
// this.getFIRST_PANE_UPPER().setVisible(true);
// this.getFIRST_PANE_LOWER().setVisible(false);
//
// this.getFIRST_FLOW().getStyleClass().remove("forward-button");
// this.getFIRST_FLOW().getStyleClass().add("forward-one-button");
//
// this.setFlow(2);
//
// }
//
// if (this.getFlow() == FlowControll.FLOW_TWO
// && this.getEventSource() == FlowControll.EVT_SOURCE_BUTTON_TWO) {
// this.getSECOND_FLOW().getStyleClass().remove("forward-button");
// this.getSECOND_FLOW().getStyleClass().add("forward-one-button");
//
// this.getSECOND_PANE_LOWER().getStyleClass().remove("big-container-borderless");
// this.getSECOND_PANE_LOWER().getStyleClass().add("big-container");
//
// this.getSECOND_PANE_BOX_UPPER_LOWER().getStyleClass().remove("big-container-borderless");
// this.getSECOND_PANE_BOX_UPPER_LOWER().getStyleClass().add("big-container");
//
// this.getSECOND_PANE_BOX_UPPER_LOWER_LOWER().getStyleClass().remove("big-container-borderless");
// this.getSECOND_PANE_BOX_UPPER_LOWER_LOWER().getStyleClass().add("big-container");
//
// this.getSECOND_PANE_UPPER().setVisible(true);
// this.getSECOND_PANE_LOWER().setVisible(false);
//
// this.getSECOND_PANE_BOX_UPPER().setVisible(true);
// this.getSECOND_PANE_BOX_UPPER_LOWER().setVisible(true);
// this.getSECOND_PANE_BOX_UPPER_LOWER_LOWER().setVisible(true);
//
// this.getSECOND_FLOW_BOX_ONE().setVisible(true);
// this.getSECOND_FLOW_BOX_TWO().setVisible(true);
// this.setFlow(3);
//
// }
// if (this.getFlow() == FlowControll.FLOW_THREE
// && this.getEventSource() == FlowControll.EVT_SOURCE_BUTTON_THREE) {
//
// this.getTHIRD_FLOW().getStyleClass().remove("forward-button");
// this.getTHIRD_FLOW().getStyleClass().add("forward-one-button");
//
// this.getTHIRD_PANE_UPPER().setVisible(true);
// this.getTHIRD_PANE_LOWER().setVisible(false);
//
// this.setFlow(4);
//
// }
// if (this.getFlow() == FlowControll.FLOW_FOUR
// && this.getEventSource() == FlowControll.EVT_SOURCE_BUTTON_FOUR) {
// this.getTHIRD_PANE_UPPER().setVisible(true);
// this.getTHIRD_PANE_LOWER().setVisible(false);
// this.setFlow(2);
// }
// if (this.getFlow() == FlowControll.FLOW_FIVE
// && this.getEventSource() == FlowControll.EVT_SOURCE_BUTTON_FIVE) {
// this.getFIRST_PANE_UPPER().setVisible(true);
// this.getFIRST_PANE_LOWER().setVisible(false);
// this.setFlow(1);
// }
// } catch (Exception e) {
// // TODO: handle exception
// }
// }
//
// /*** :::GETTERS AND SETTERS::: ***/
// /**
// *
// * @return
// */
// public Pane getFIRST_PANE_UPPER() {
// return FIRST_PANE_UPPER;
// }
//
// /**
// *
// * @param fIRST_PANE_UPPER
// */
// public void setFIRST_PANE_UPPER(Pane fIRST_PANE_UPPER) {
// FIRST_PANE_UPPER = fIRST_PANE_UPPER;
// }
//
// /**
// *
// * @return
// */
// public Pane getFIRST_PANE_LOWER() {
// return FIRST_PANE_LOWER;
// }
//
// /**
// *
// * @param fIRST_UPPER_LOWER
// */
// public void setFIRST_PANE_LOWER(Pane fIRST_PANE_LOWER) {
// FIRST_PANE_LOWER = fIRST_PANE_LOWER;
// }
//
// /**
// *
// * @return
// */
// public Pane getSECOND_PANE_LOWER() {
// return SECOND_PANE_LOWER;
// }
//
// /**
// *
// * @param sECOND_PANE_LOWER
// */
// public void setSECOND_PANE_LOWER(Pane sECOND_PANE_LOWER) {
// SECOND_PANE_LOWER = sECOND_PANE_LOWER;
// }
//
// /**
// *
// * @return
// */
// public Pane getSECOND_PANE_UPPER() {
// return SECOND_PANE_UPPER;
// }
//
// /**
// *
// * @param sECOND_PANE_UPPER
// */
// public void setSECOND_PANE_UPPER(Pane sECOND_PANE_UPPER) {
// SECOND_PANE_UPPER = sECOND_PANE_UPPER;
// }
//
// /**
// *
// * @return
// */
// public Pane getTHIRD_PANE_UPPER() {
// return THIRD_PANE_UPPER;
// }
//
// /**
// *
// * @param tHIRD_PANE_UPPER
// */
// public void setTHIRD_PANE_UPPER(Pane tHIRD_PANE_UPPER) {
// THIRD_PANE_UPPER = tHIRD_PANE_UPPER;
// }
//
// /**
// *
// * @return
// */
// public Pane getTHIRD_PANE_LOWER() {
// return THIRD_PANE_LOWER;
// }
//
// /**
// *
// * @param tHIRD_PANE_LOWER
// */
// public void setTHIRD_PANE_LOWER(Pane tHIRD_PANE_LOWER) {
// THIRD_PANE_LOWER = tHIRD_PANE_LOWER;
// }
//
// /**
// * @return the fIRST_FLOW
// */
// public Button getFIRST_FLOW() {
// return FIRST_FLOW;
// }
//
// /**
// * @param fIRST_FLOW
// * the fIRST_FLOW to set
// */
// public void setFIRST_FLOW(Button fIRST_FLOW) {
// FIRST_FLOW = fIRST_FLOW;
// }
//
// /**
// * @return the sECOND_FLOW
// */
// public Button getSECOND_FLOW() {
// return SECOND_FLOW;
// }
//
// /**
// * @param sECOND_FLOW
// * the sECOND_FLOW to set
// */
// public void setSECOND_FLOW(Button sECOND_FLOW) {
// SECOND_FLOW = sECOND_FLOW;
// }
//
// /**
// * @return the tHIRD_FLOW
// */
// public Button getTHIRD_FLOW() {
// return THIRD_FLOW;
// }
//
// /**
// * @param tHIRD_FLOW
// * the tHIRD_FLOW to set
// */
// public void setTHIRD_FLOW(Button tHIRD_FLOW) {
// THIRD_FLOW = tHIRD_FLOW;
// }
//
// /**
// * @return the fOURTH_FLOW
// */
// public Button getFOURTH_FLOW() {
// return FOURTH_FLOW;
// }
//
// /**
// * @param fOURTH_FLOW
// * the fOURTH_FLOW to set
// */
// public void setFOURTH_FLOW(Button fOURTH_FLOW) {
// FOURTH_FLOW = fOURTH_FLOW;
// }
//
// /**
// * @return the fIFTH_FLOW
// */
// public Button getFIFTH_FLOW() {
// return FIFTH_FLOW;
// }
//
// /**
// * @param fIFTH_FLOW
// * the fIFTH_FLOW to set
// */
// public void setFIFTH_FLOW(Button fIFTH_FLOW) {
// FIFTH_FLOW = fIFTH_FLOW;
// }
//
// /**
// * @return the flow
// */
// public int getFlow() {
// return flow;
// }
//
// /**
// * @param flow
// * the flow to set
// */
// public void setFlow(int flow) {
// this.flow = flow;
// }
//
// /**
// * @return the sECOND_FLOW_BOX_THREE
// */
// public Button getSECOND_FLOW_BOX_THREE() {
// return SECOND_FLOW_BOX_THREE;
// }
//
// /**
// * @param sECOND_FLOW_BOX_THREE
// * the sECOND_FLOW_BOX_THREE to set
// */
// public void setSECOND_FLOW_BOX_THREE(Button sECOND_FLOW_BOX_THREE) {
// SECOND_FLOW_BOX_THREE = sECOND_FLOW_BOX_THREE;
// }
//
// /**
// * @return the sECOND_FLOW_BOX_TWO
// */
// public Button getSECOND_FLOW_BOX_TWO() {
// return SECOND_FLOW_BOX_TWO;
// }
//
// /**
// * @param sECOND_FLOW_BOX_TWO
// * the sECOND_FLOW_BOX_TWO to set
// */
// public void setSECOND_FLOW_BOX_TWO(Button sECOND_FLOW_BOX_TWO) {
// SECOND_FLOW_BOX_TWO = sECOND_FLOW_BOX_TWO;
// }
//
// /**
// * @return the sECOND_FLOW_BOX_ONE
// */
// public Button getSECOND_FLOW_BOX_ONE() {
// return SECOND_FLOW_BOX_ONE;
// }
//
// /**
// * @param sECOND_FLOW_BOX_ONE
// * the sECOND_FLOW_BOX_ONE to set
// */
// public void setSECOND_FLOW_BOX_ONE(Button sECOND_FLOW_BOX_ONE) {
// SECOND_FLOW_BOX_ONE = sECOND_FLOW_BOX_ONE;
// }
//
// /**
// * @return the sECOND_PANE_BOX_UPPER
// */
// public Pane getSECOND_PANE_BOX_UPPER() {
// return SECOND_PANE_BOX_UPPER;
// }
//
// /**
// * @param sECOND_PANE_BOX_UPPER
// * the sECOND_PANE_BOX_UPPER to set
// */
// public void setSECOND_PANE_BOX_UPPER(Pane sECOND_PANE_BOX_UPPER) {
// SECOND_PANE_BOX_UPPER = sECOND_PANE_BOX_UPPER;
// }
//
// /**
// * @return the sECOND_PANE_BOX_UPPER_LOWER
// */
// public Pane getSECOND_PANE_BOX_UPPER_LOWER() {
// return SECOND_PANE_BOX_UPPER_LOWER;
// }
//
// /**
// * @param sECOND_PANE_BOX_UPPER_LOWER
// * the sECOND_PANE_BOX_UPPER_LOWER to set
// */
// public void setSECOND_PANE_BOX_UPPER_LOWER(Pane sECOND_PANE_BOX_UPPER_LOWER)
// {
// SECOND_PANE_BOX_UPPER_LOWER = sECOND_PANE_BOX_UPPER_LOWER;
// }
//
// /**
// * @return the sECOND_PANE_BOX_UPPER_LOWER_LOWER
// */
// public Pane getSECOND_PANE_BOX_UPPER_LOWER_LOWER() {
// return SECOND_PANE_BOX_UPPER_LOWER_LOWER;
// }
//
// /**
// * @param sECOND_PANE_BOX_UPPER_LOWER_LOWER
// * the sECOND_PANE_BOX_UPPER_LOWER_LOWER to set
// */
// public void setSECOND_PANE_BOX_UPPER_LOWER_LOWER(Pane
// sECOND_PANE_BOX_UPPER_LOWER_LOWER) {
// SECOND_PANE_BOX_UPPER_LOWER_LOWER = sECOND_PANE_BOX_UPPER_LOWER_LOWER;
// }
//
// /**
// * @return the currentButton
// */
// public int getEventSource() {
// return eventSource;
// }
//
// /**
// * @param currentButton
// * the currentButton to set
// */
// public void setEventSource(int currentButton) {
// this.eventSource = currentButton;
// }
//
// /**
// * @return the rESTORE_BUTTON_THREE
// */
// public Button getRESTORE_BUTTON_THREE() {
// return RESTORE_BUTTON_THREE;
// }
//
// /**
// * @param rESTORE_BUTTON_THREE
// * the rESTORE_BUTTON_THREE to set
// */
// public void setRESTORE_BUTTON_THREE(Button rESTORE_BUTTON_THREE) {
// RESTORE_BUTTON_THREE = rESTORE_BUTTON_THREE;
// }
//
// /**
// * @return the rESTORE_BUTTON_TWO
// */
// public Button getRESTORE_BUTTON_TWO() {
// return RESTORE_BUTTON_TWO;
// }
//
// /**
// * @param rESTORE_BUTTON_TWO
// * the rESTORE_BUTTON_TWO to set
// */
// public void setRESTORE_BUTTON_TWO(Button rESTORE_BUTTON_TWO) {
// RESTORE_BUTTON_TWO = rESTORE_BUTTON_TWO;
// }
//
// /**
// * @return the rESTORE_BUTTON_ONE
// */
// public Button getRESTORE_BUTTON_ONE() {
// return RESTORE_BUTTON_ONE;
// }
//
// /**
// * @param rESTORE_BUTTON_ONE
// * the rESTORE_BUTTON_ONE to set
// */
// public void setRESTORE_BUTTON_ONE(Button rESTORE_BUTTON_ONE) {
// RESTORE_BUTTON_ONE = rESTORE_BUTTON_ONE;
// }
//
// /**
// * @return the viewNum
// */
// public int getViewNum() {
// return viewNum;
// }
//
// /**
// * @param viewNum
// * the viewNum to set
// */
// public void setViewNum(int viewNum) {
// this.viewNum = viewNum;
// }
//
// }
