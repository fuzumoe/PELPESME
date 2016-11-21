 package com.sfb805.controller;

 import java.io.BufferedReader;
 import java.io.IOException;
 import java.io.InputStreamReader;

 public class GraphController {

 private String comand_type = "";
 private String comand = "";
 private BufferedReader stdInput;
 private BufferedReader stdError;
 private String comandResult = "";
 private Process p;

 /***
 * Default constructor
 */
 public GraphController() {

 }

 /***/
 /**
 *
 * @param comand
 */
 public void setComand(String comand) {
 this.comand = comand;
 }

 /**
 *
 * @return
 */
 public String getComand() {
 return comand;
 }

 /**
 *
 * @return
 */
 public String getComand_type() {
 return comand_type;
 }

 /**
 *
 * @param comand_type
 */
 public void setComand_type(String comand_type) {
 this.comand_type = comand_type;
 }

 public void runComand(String Comand) throws IOException {
 // set the command
 p = Runtime.getRuntime().exec(Comand);
 // set the input stream to send to the command through
 stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
 // set the output stream to get the results of the output through
 stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
 // read the output from the command
 while ((comandResult = stdInput.readLine()) != null) {
 System.out.println(comandResult);
 }

 // read any errors from the attempted command
 while ((comandResult = stdError.readLine()) != null) {
 System.out.println(comandResult);
 }
 }

 /**
 * Method that conditional runs the commands
 *
 * @throws IOException
 */
 public void proccessGraph() throws IOException {
 if (this.getComand() == Comands.TYPE_ONE) {
 this.runComand(Comands.DELETE_FILE_ONE);
 this.runComand(Comands.PARSE_FILE_ONE);
 this.runComand(Comands.COPY_FILE_ONE);

 }
 if (this.getComand() == Comands.TYPE_TWO) {
 this.runComand(Comands.DELETE_FILE_TWO);
 this.runComand(Comands.PARSE_FILE_TWO);
 this.runComand(Comands.COPY_FILE_TWO);
 }
 if (this.getComand() == Comands.TYPE_THREE) {
 this.runComand(Comands.DELETE_FILE_ONE);
 this.runComand(Comands.PARSE_FILE_THREE);
 this.runComand(Comands.COPY_FILE_THREE);
 }
 }

 public static void main(String args[]) throws IOException {
 GraphController graphController = new GraphController();
 graphController.setComand(Comands.TYPE_TWO);
 graphController.proccessGraph();

 }
 }