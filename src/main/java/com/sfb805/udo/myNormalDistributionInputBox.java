package com.sfb805.udo;


//==============================================================================
//WARNING!!  This file is overwritten by the Block UI Styler while generating
//the automation code. Any modifications to this file will be lost after
//generating the code again.
//
//     Filename:  C:\Users\max\Desktop\myNormalDistributionInputBox.java
//
//      This file was generated by the NX Block UI Styler
//      Created by: max
//            Version: NX 10
//            Date: 02-15-2016  (Format: mm-dd-yyyy)
//            Time: 11:42 (Format: hh-mm)
//
//==============================================================================

import java.rmi.Naming;

//==============================================================================
//Purpose:  This TEMPLATE file contains JAVA source to guide you in the
//construction of your Block application dialog. The generation of your
//dialog file (.dlx extension) is the first step towards dialog construction
//within NX.  You must now create a NX Open application that
//utilizes this file (.dlx).
//
//The information in this file provides you with the following:
//
//1.  Help on how to load and display your Block UI Styler dialog in NX
//    using APIs provided in NXOpen.BlockStyler namespace
//2.  The empty callback methods (stubs) associated with your dialog items
//    have also been placed in this file. These empty methods have been
//    created simply to start you along with your coding requirements.
//    The method name, argument list and possible return values have already
//    been provided for you.
//==============================================================================

//------------------------------------------------------------------------------
//These imports are needed for the following template code
//------------------------------------------------------------------------------
import java.rmi.RemoteException;

import nxopen.*;
import nxopen.blockstyler.*;

//------------------------------------------------------------------------------
//Represents Block Styler application class
//------------------------------------------------------------------------------
public class myNormalDistributionInputBox implements BlockDialog.Initialize, BlockDialog.DialogShown, BlockDialog.Ok, BlockDialog.Update
{
  //class members
  public static Session theSession = null;
  public static UI theUI = null;
  private String theDlxFileName;
  private nxopen.blockstyler.BlockDialog theDialog;
  private nxopen.blockstyler.Group group;// Block type: Group
  private nxopen.blockstyler.DoubleBlock expectationX;// Block type: Double
  private nxopen.blockstyler.DoubleBlock expectationY;// Block type: Double
  private nxopen.blockstyler.DoubleBlock expectationZ;// Block type: Double
  private nxopen.blockstyler.Group group1;// Block type: Group
  private nxopen.blockstyler.DoubleBlock varianceX;// Block type: Double
  private nxopen.blockstyler.DoubleBlock varianceY;// Block type: Double
  private nxopen.blockstyler.DoubleBlock varianceZ;// Block type: Double
  private nxopen.blockstyler.Group group2;// Block type: Group
  private nxopen.blockstyler.IntegerBlock sampleNumber;// Block type: Integer
  
  //------------------------------------------------------------------------------
  //Constructor for NX Styler class
  //------------------------------------------------------------------------------
  public myNormalDistributionInputBox() throws Exception, RemoteException
  {
      try
      {
          theSession = (Session)SessionFactory.get("Session");
          theUI = (UI)SessionFactory.get("UI");
          theDlxFileName = "myNormalDistributionInputBox.dlx";
          theDialog = theUI.createDialog(theDlxFileName);
          theDialog.addOkHandler(this);
          theDialog.addUpdateHandler(this);
          theDialog.addInitializeHandler(this);
          theDialog.addDialogShownHandler(this);
      }
      catch(Exception ex)
      {
          //---- Enter your exception handling code here -----
          throw new Exception(ex);
      }
  }
  //------------------------------- DIALOG LAUNCHING ---------------------------------
  //
  //    Before invoking this application one needs to open any part/empty part in NX
  //    because of the behavior of the blocks.
  //
  //    Make sure the dlx file is in one of the following locations:
  //        1.) From where NX session is launched
  //        2.) $UGII_USER_DIR/application
  //        3.) For released applications, using UGII_CUSTOM_DIRECTORY_FILE is highly
  //            recommended. This variable is set to a full directory path to a file 
  //            containing a list of root directories for all custom applications.
  //            e.g., UGII_CUSTOM_DIRECTORY_FILE=$UGII_ROOT_DIR\menus\custom_dirs.dat
  //
  //    You can create the dialog using one of the following way:
  //
  //    1. USER EXIT
  //
  //        1) Create the Shared Library -- Refer "Block UI Styler programmer's guide"
  //        2) Invoke the Shared Library through File->Execute->NX Open menu.
  //
  //------------------------------------------------------------------------------
  public static void main(String [] argv) throws Exception
  {
      myNormalDistributionInputBox themyNormalDistributionInputBox = null;
      try
      {
          themyNormalDistributionInputBox = new myNormalDistributionInputBox();
          // The following method shows the dialog immediately
          themyNormalDistributionInputBox.show();
      }
      catch(Exception ex)
      {
          //---- Enter your exception handling code here -----
          theUI.nxmessageBox().show("Block Styler", nxopen.NXMessageBox.DialogType.ERROR, ex.getMessage());
      }
      finally
      {
          if(themyNormalDistributionInputBox != null)
          {
              themyNormalDistributionInputBox.dispose();
              themyNormalDistributionInputBox = null;
          }
      }
  }
  
  //------------------------------------------------------------------------------
  // This method specifies how a shared image is unloaded from memory
  // within NX. This method gives you the capability to unload an
  // internal NX Open application or user  exit from NX. Specify any
  // one of the three constants as a return value to determine the type
  // of unload to perform:
  //
  //
  //    Immediately : unload the library as soon as the automation program has completed
  //    Explicitly  : unload the library from the "Unload Shared Image" dialog
  //    AtTermination : unload the library when the NX session terminates
  //
  //
  // NOTE:  A program which associates NX Open applications with the menubar
  // MUST NOT use this option since it will UNLOAD your NX Open application image
  // from the menubar.
  //------------------------------------------------------------------------------
   public static final int getUnloadOption()
  {
      //return BaseSession.LibraryUnloadOption.EXPLICITLY;
       return BaseSession.LibraryUnloadOption.IMMEDIATELY;
      // return BaseSession.LibraryUnloadOption.AT_TERMINATION;
  }
  
  //------------------------------------------------------------------------------
  // Following method cleanup any housekeeping chores that may be needed.
  // This method is automatically called by NX.
  //------------------------------------------------------------------------------
  public static void onUnload() throws NXException, RemoteException
  {
      try
      {
      }
      catch(Exception ex)
      {
          //---- Enter your exception handling code here -----
          theUI.nxmessageBox().show("Block Styler", nxopen.NXMessageBox.DialogType.ERROR, ex.getMessage());
      }
  }
  
  //------------------------------------------------------------------------------
  //This method shows the dialog on the screen
  //------------------------------------------------------------------------------
  public int show() throws NXException, RemoteException
  {
      try
      {
          theDialog.show();
      }
      catch(Exception ex)
      {
          //---- Enter your exception handling code here -----
          theUI.nxmessageBox().show("Block Styler", nxopen.NXMessageBox.DialogType.ERROR, ex.getMessage());
      }
      return 0;
  }
  
  //------------------------------------------------------------------------------
  //Method Name: dispose
  //------------------------------------------------------------------------------
  public void dispose() throws NXException, RemoteException
  {
      if(theDialog != null)
      {
          theDialog.dispose();
          theDialog = null;
      }
  }
  
  //------------------------------------------------------------------------------
  //---------------------Block UI Styler Callback Functions--------------------------
  //------------------------------------------------------------------------------
  
  //------------------------------------------------------------------------------
  //Callback Name: initialize
  //------------------------------------------------------------------------------
  public void initialize() throws NXException, RemoteException
  {
      try
      {
          group = (nxopen.blockstyler.Group)theDialog.topBlock().findBlock("group");
          expectationX = (nxopen.blockstyler.DoubleBlock)theDialog.topBlock().findBlock("expectationX");
          expectationY = (nxopen.blockstyler.DoubleBlock)theDialog.topBlock().findBlock("expectationY");
          expectationZ = (nxopen.blockstyler.DoubleBlock)theDialog.topBlock().findBlock("expectationZ");
          group1 = (nxopen.blockstyler.Group)theDialog.topBlock().findBlock("group1");
          varianceX = (nxopen.blockstyler.DoubleBlock)theDialog.topBlock().findBlock("varianceX");
          varianceY = (nxopen.blockstyler.DoubleBlock)theDialog.topBlock().findBlock("varianceY");
          varianceZ = (nxopen.blockstyler.DoubleBlock)theDialog.topBlock().findBlock("varianceZ");
          group2 = (nxopen.blockstyler.Group)theDialog.topBlock().findBlock("group2");
          sampleNumber = (nxopen.blockstyler.IntegerBlock)theDialog.topBlock().findBlock("sampleNumber");
      }
      catch(Exception ex)
      {
          //---- Enter your exception handling code here -----
          theUI.nxmessageBox().show("Block Styler", nxopen.NXMessageBox.DialogType.ERROR, ex.getMessage());
      }
  }
  
  //------------------------------------------------------------------------------
  //Callback Name: dialogShown
  //This callback is executed just before the dialog launch. Thus any value set 
  //here will take precedence and dialog will be launched showing that value. 
  //------------------------------------------------------------------------------
  public void dialogShown() throws NXException, RemoteException
  {
      try
      {
//      	SelectedObjectServer NXObjects4GUIsetup = (SelectedObjectServer) Naming.lookup("//127.0.0.1/SelectedObjectServer");
//  		 //get the array of the selected NXObjects
//      	 theUI.nxmessageBox().show("Block Styler - dialogShown()", nxopen.NXMessageBox.DialogType.INFORMATION, "NXObjects4GUIsetup"); 
//      	NXObject[]	theNXObjects= NXObjects4GUIsetup.getTheNXObjects();
//      	theUI.nxmessageBox().show("Block Styler - dialogShown()", nxopen.NXMessageBox.DialogType.INFORMATION,"theNXObjects");
//      	//get the current pointer on the array
//  		 int count = NXObjects4GUIsetup.getCount();
//  		 theUI.nxmessageBox().show("Block Styler - dialogShown()", nxopen.NXMessageBox.DialogType.INFORMATION,"count");
//  		 //increment counter after reading;
//  		 NXObjects4GUIsetup.incrementCount();
//  		 NXObject currentNXObject = theNXObjects[count];
//  		 Point currentPoint = (Point) currentNXObject;
//  		 Point3d currentPoint3d = currentPoint.coordinates();
  		
      	String filepath = "C:/Users/max/desktop/PELOPS/ArrayExchange.txt";
      	Array2File file2array = new Array2File();
      	 double [][] myDefaultValues = file2array.readFile2Array(filepath);
  		
			expectationX.setValue(myDefaultValues[0][1]);
			expectationY.setValue(myDefaultValues[0][2]);
			expectationZ.setValue(myDefaultValues[0][3]);
			
			varianceX.setValue(myDefaultValues[0][4]);
			varianceY.setValue(myDefaultValues[0][5]);
			varianceZ.setValue(myDefaultValues[0][6]);
			
//			Rounds the number of sample			
			int roundedSampleNumber = Math.round(100/myDefaultValues.length);
			
			sampleNumber.setValue(roundedSampleNumber);
			
			file2array.removeFirstLineFromFile(filepath);
//			theUI.nxmessageBox().show("Block Styler", nxopen.NXMessageBox.DialogType.INFORMATION,"expectationX: "+ expectationX.toString());
//  		 //---- Enter your callback code here -----
//  		 theUI.nxmessageBox().show("dialogShown() - try", nxopen.NXMessageBox.DialogType.INFORMATION, "Hello");		
      }
      catch(Exception ex)
      {
          //---- Enter your exception handling code here -----
          theUI.nxmessageBox().show("Block Styler - dialogShown()", nxopen.NXMessageBox.DialogType.ERROR, ex.getMessage() + " "+ex.getLocalizedMessage());
      }
  }
  //------------------------------------------------------------------------------
  //Callback Name: update
  //Following callback is associated with the "theDialog" Block.
  //------------------------------------------------------------------------------
  public int update( nxopen.blockstyler.UIBlock block) throws NXException, RemoteException
  {
      try
      {
          if(block == expectationX)
          {
          //---------Enter your code here-----------
          }
          else if(block == expectationY)
          {
          //---------Enter your code here-----------
          }
          else if(block == expectationZ)
          {
          //---------Enter your code here-----------
          }
          else if(block == varianceX)
          {
          //---------Enter your code here-----------
          }
          else if(block == varianceY)
          {
          //---------Enter your code here-----------
          }
          else if(block == varianceZ)
          {
          //---------Enter your code here-----------
          }
          else if(block == sampleNumber)
          {
          //---------Enter your code here-----------
          }
      }
      catch(Exception ex)
      {
          //---- Enter your exception handling code here -----
          theUI.nxmessageBox().show("Block Styler", nxopen.NXMessageBox.DialogType.ERROR, ex.getMessage());
      }
      return 0;
  }
  
  //------------------------------------------------------------------------------
  //Callback Name: ok
  //------------------------------------------------------------------------------
  public int ok() throws NXException, RemoteException
  {
      int errorCode = 0;
      try
      {	
      	
      	LinkedUDO.udoselected = 1;
      }
      catch(Exception ex)
      {
          //---- Enter your exception handling code here -----
          errorCode = 1;
          theUI.nxmessageBox().show("Block Styler", nxopen.NXMessageBox.DialogType.ERROR, ex.getMessage());
      }
      return errorCode;
  }
  
  //------------------------------------------------------------------------------
  //Function Name: GetBlockProperties
  //------------------------------------------------------------------------------
  public PropertyList getBlockProperties(String blockID) throws NXException, RemoteException
  {
      PropertyList plist = null;
      try
      {
          plist = theDialog.getBlockProperties(blockID);
      }
      catch(Exception ex)
      {
          //---- Enter your exception handling code here -----
          theUI.nxmessageBox().show("Block Styler", nxopen.NXMessageBox.DialogType.ERROR, ex.getMessage());
      }
      return plist;
  }
  
}
