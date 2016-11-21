package com.sfb805.controller;

/**
 * 
 * @author Max
 *
 */
public interface Comands {
	/** PELEPS WORK FILE **/
	public static String LOCATION = System.getProperty("user.home")+ "\\Desktop\\PELOPS\\";
	/** COMAND TYPES **/
	public static String TYPE_ONE = "FILE_ONE";
	public static String TYPE_TWO = "FILE_TWO";
	public static String TYPE_THREE = "TYPE_THREE";

	/** DELETE COMANDS **/
	public static String DELETE_FILE_ONE = "cmd /c " + LOCATION + "deleteFileOne.bat";
	public static String DELETE_FILE_TWO = "cmd /c " + LOCATION + "deleteFileTwo.bat";
	public static String DLETE_FILE_THREE = "cmd /c " + LOCATION + "deleteFileThree.bat";

	/*** COPY COMANDS **/
	public static String COPY_FILE_ONE = "cmd /c " + LOCATION + "copyFileOne.bat ";
	public static String COPY_FILE_TWO = "cmd /c " + LOCATION + "copyFileTwo.bat";
	public static String COPY_FILE_THREE = "cmd /c " + LOCATION + "copyFileThree.bat";

	/** PARSE COMANDS **/
	public static String PARSE_FILE_ONE = "cmd /c " + LOCATION + "parseFileOne.bat";
	public static String PARSE_FILE_TWO = "cmd /c " + LOCATION + "parseFileTwo.bat ";
	public static String PARSE_FILE_THREE = "cmd /c " + LOCATION + "parseFileThree.bat ";
   /** Network Graph Views **/
	public static String VIEW_ONE ="file:///"+LOCATION.replace("\\", "/")+"index.html#foaf";
	public static String VIEW_TWO ="file:///"+LOCATION.replace("\\", "/")+"index.html#foaf";
	public static String VIEW_THREE ="file:///"+LOCATION.replace("\\", "/")+"index.html#foaf";
}
