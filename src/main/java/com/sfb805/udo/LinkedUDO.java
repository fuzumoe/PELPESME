package com.sfb805.udo;

import java.rmi.Naming;
import java.rmi.RemoteException;

//import com.sfb805.nx.SelectedObjectServer;
//add other project to build path before import
//import com.sfb805.nx.SelectedObjectServerImpl;
import com.sfb805.udo.myNormalDistributionInputBox;

import nxopen.BasePart;
import nxopen.BaseSession;
import nxopen.DisplayableObject;
import nxopen.ListingWindow;
import nxopen.DisplayableObject.ObjectWidth;
import nxopen.Edge;
import nxopen.Edge.VerticesData;
import nxopen.Line;
import nxopen.Matrix3x3;
import nxopen.NXException;
import nxopen.NXMessageBox;
import nxopen.NXObject;
import nxopen.Point;
import nxopen.Point3d;
import nxopen.PointCollection;
import nxopen.Selection;
import nxopen.Selection.SelectObjectData;
import nxopen.Selection.SelectObjectsData;
import nxopen.Session;
import nxopen.SessionFactory;
import nxopen.TaggedObject;
import nxopen.UFSession;

import nxopen.UI;
import nxopen.blockstyler.PropertyList;
import nxopen.uf.UFConstants;
import nxopen.userdefinedobjects.*;
import nxopen.userdefinedobjects.UserDefinedObject.LinkDefinition;
import nxopen.userdefinedobjects.UserDefinedObject.LinkType;
import nxopen.userdefinedobjects.UserDefinedObjectManager.LinkedUdoDefinition;

public class LinkedUDO implements nxopen.userdefinedobjects.UserDefinedClass.DisplayCallback,
		nxopen.userdefinedobjects.UserDefinedClass.GenericCallback,
		nxopen.userdefinedobjects.UserDefinedClass.LinkCallback {

	public static UserDefinedClass myUDOclass = null;
	public static Session theSession = null;
	public static UFSession theUFSession = null;
	public static UI theUI = null;
	public static int udoselected; // 0/1

	double va = 12;

	@Override
	public int genericCallback(UserDefinedEvent arg0) throws NXException, RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int displayCallback(UserDefinedDisplayEvent e) throws NXException, RemoteException {
		this.myDisplayCB(e);
		return 0;
	}

	@Override
	public int linkCallback(UserDefinedLinkEvent arg0) throws NXException, RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int myDisplayCB(nxopen.userdefinedobjects.UserDefinedDisplayEvent e) {
		// '--------------------------------------------------------------------------
		// ' Callback: myDisplayCB ' This is a callback method associated with
		// displaying a UDO.
		// ' This same callback is registered for display, select, fit, and
		// ' attention point.
		// '--------------------------------------------------------------------------
		try {
			if (e.eventReason() == nxopen.userdefinedobjects.UserDefinedEvent.Reason.DISPLAY) {
				try {
					// theUI.nxmessageBox().show("Info",
					// NXMessageBox.DialogType.INFORMATION, "//Entering
					// myDisplayCB!");

					String myUDODisplayString = null;
					// LinkDefinition myUDOLinks = new LinkDefinition();
					// UserDefinedObject.LinkDefinition();

					UserDefinedObject.LinkDefinition[] myUDOLinks = new UserDefinedObject.LinkDefinition[1];
					// myUDOLinks[0] = new UserDefinedObject.LinkDefinition();
					// What type of link did we store with this udo?
					int[] myUDOints = new int[1];
					myUDOints = e.userDefinedObject().getIntegers();
					// theUI.nxmessageBox().show("Info",
					// NXMessageBox.DialogType.INFORMATION, "//myUDOints[0]=
					// "+myUDOints[0]);

					if (myUDOints[0] == 0) {
						myUDOLinks = e.userDefinedObject().getLinks(UserDefinedObject.LinkType.OWNING);
						myUDODisplayString = "Own";
					} else if (myUDOints[0] == 1) {
						myUDOLinks = e.userDefinedObject().getLinks(LinkType.TYPE1);
						myUDODisplayString = "Type 1";
						// theUI.nxmessageBox().show("Info",
						// NXMessageBox.DialogType.INFORMATION, "//myUDOLinks
						// "+e.userDefinedObject().getLinks(LinkType.TYPE1).toString());
						// theUI.nxmessageBox().show("Info",
						// NXMessageBox.DialogType.INFORMATION,
						// "//myUDOLinks.length "+myUDOLinks.length);
					} else if (myUDOints[0] == 2) {
						myUDOLinks = e.userDefinedObject().getLinks(LinkType.TYPE2);
						myUDODisplayString = "Type 2";
					} else if (myUDOints[0] == 3) {
						myUDOLinks = e.userDefinedObject().getLinks(LinkType.TYPE3);
						myUDODisplayString = "Type 3";
					} else if (myUDOints[0] == 4) {
						myUDOLinks = e.userDefinedObject().getLinks(LinkType.TYPE4);
						myUDODisplayString = "Type 4";
					} else {
						// There was no link type defined...
						// We should never get here, but if we do, stop trying
						// to display this UDO.
						return 0;
					}
					if (myUDOLinks.length == 0) {
						// theUI.nxmessageBox().show("Info",
						// NXMessageBox.DialogType.INFORMATION, "//Don´t bother
						// to display UDO without linked points!");
						// The linked point was missing (the point may have been
						// deleted)
						// Don't bother to display any UDO's without linked
						// points.
						return 0;
					} else {
						// theUI.nxmessageBox().show("Info",
						// NXMessageBox.DialogType.INFORMATION, "//Let´s
						// display!");

						if (myUDOLinks[0].associatedObject instanceof Edge) {
							Point3d myPointCoordinatesV1 = null;
							Point3d myPointCoordinatesV2 = null;
							Edge selectedEdge = (Edge) myUDOLinks[0].associatedObject;
							VerticesData selectedVerteces = selectedEdge.getVertices();

							myPointCoordinatesV1 = (Point3d) selectedVerteces.vertex1;
							myPointCoordinatesV2 = (Point3d) selectedVerteces.vertex2;

							theUI.nxmessageBox().show("Info", NXMessageBox.DialogType.INFORMATION,
									"Display selected Edge!");

							// The point linked to the UDO will define the
							// location of
							// the UDO we're about to display
							// Point myPoint = (Point)
							// myUDOLinks[0].associatedObject;
							// Point3d myPointCoordinates =
							// myPoint.coordinates();
							// Draw some text at this location to indicate the
							// link type in use
							e.displayContext().displayText(myUDODisplayString, myPointCoordinatesV1,
									UserDefinedObjectDisplayContext.TextRef.BOTTOM_LEFT);
							e.displayContext().displayText(myUDODisplayString, myPointCoordinatesV2,
									UserDefinedObjectDisplayContext.TextRef.BOTTOM_LEFT);
							// theUI.nxmessageBox().show("Info",
							// NXMessageBox.DialogType.INFORMATION,
							// "//e.displayContext().displayText(myUDODisplayString,
							// myPointCoordinates, null)");
							// ' Draw a circle around the linked point in the
							// X-Z plane
							// ' First we must define a matrix to describe the
							// transform from
							// ' Absolute coordinates into the X-Z plane this
							// matrix is the
							// ' "rotation" matrix for our circle.
							Matrix3x3 myMatrix = new Matrix3x3();
							myMatrix.xx = 1;
							myMatrix.xy = 0;
							myMatrix.xz = 0;
							myMatrix.yx = 0;
							myMatrix.yy = 0;
							myMatrix.yz = 1;
							myMatrix.zx = 0;
							myMatrix.zy = -1;
							myMatrix.zz = 0;
							// ' Now we must transform the origin of the circle
							// from Absolute coordinates
							// ' to the coordinates of the circle (ie apply the
							// rotation transform).
							Point3d xformedPointV1 = theSession.mathUtils().multiply(myMatrix, myPointCoordinatesV1);
							Point3d xformedPointV2 = theSession.mathUtils().multiply(myMatrix, myPointCoordinatesV2);
							// ' Draw the circle now
							// theUI.nxmessageBox().show("Info",
							// NXMessageBox.DialogType.INFORMATION,
							// "//theSession.mathUtils().multiply(myMatrix,
							// myPointCoordinates)");
							e.displayContext().displayCircle(xformedPointV1, myMatrix, 20, false);
							e.displayContext().displayCircle(xformedPointV2, myMatrix, 20, false);

							return 0;
						}

						else if (myUDOLinks[0].associatedObject instanceof Line) {
							Point3d myPointCoordinatesV1 = null;
							Point3d myPointCoordinatesV2 = null;
							Line selectedLine = (Line) myUDOLinks[0].associatedObject;
							myPointCoordinatesV1 = (Point3d) selectedLine.startPoint();
							myPointCoordinatesV2 = (Point3d) selectedLine.endPoint();

							theUI.nxmessageBox().show("Info", NXMessageBox.DialogType.INFORMATION,
									"Display selected Line!");

							// The point linked to the UDO will define the
							// location of
							// the UDO we're about to display
							// Point myPoint = (Point)
							// myUDOLinks[0].associatedObject;
							// Point3d myPointCoordinates =
							// myPoint.coordinates();
							// Draw some text at this location to indicate the
							// link type in use
							e.displayContext().displayText(myUDODisplayString, myPointCoordinatesV1,
									UserDefinedObjectDisplayContext.TextRef.BOTTOM_LEFT);
							e.displayContext().displayText(myUDODisplayString, myPointCoordinatesV2,
									UserDefinedObjectDisplayContext.TextRef.BOTTOM_LEFT);
							// theUI.nxmessageBox().show("Info",
							// NXMessageBox.DialogType.INFORMATION,
							// "//e.displayContext().displayText(myUDODisplayString,
							// myPointCoordinates, null)");
							// ' Draw a circle around the linked point in the
							// X-Z plane
							// ' First we must define a matrix to describe the
							// transform from
							// ' Absolute coordinates into the X-Z plane this
							// matrix is the
							// ' "rotation" matrix for our circle.
							Matrix3x3 myMatrix = new Matrix3x3();
							myMatrix.xx = 1;
							myMatrix.xy = 0;
							myMatrix.xz = 0;
							myMatrix.yx = 0;
							myMatrix.yy = 0;
							myMatrix.yz = 1;
							myMatrix.zx = 0;
							myMatrix.zy = -1;
							myMatrix.zz = 0;
							// ' Now we must transform the origin of the circle
							// from Absolute coordinates
							// ' to the coordinates of the circle (ie apply the
							// rotation transform).
							Point3d xformedPointV1 = theSession.mathUtils().multiply(myMatrix, myPointCoordinatesV1);
							Point3d xformedPointV2 = theSession.mathUtils().multiply(myMatrix, myPointCoordinatesV2);
							// ' Draw the circle now
							// theUI.nxmessageBox().show("Info",
							// NXMessageBox.DialogType.INFORMATION,
							// "//theSession.mathUtils().multiply(myMatrix,
							// myPointCoordinates)");
							e.displayContext().displayCircle(xformedPointV1, myMatrix, 20, false);
							e.displayContext().displayCircle(xformedPointV2, myMatrix, 20, false);

							return 0;
						}

						else if (myUDOLinks[0].associatedObject instanceof Point) {
							Point3d myPointCoordinates = null;
							Point selectedPoint = (Point) myUDOLinks[0].associatedObject;
							myPointCoordinates = selectedPoint.coordinates();

							theUI.nxmessageBox().show("Info", NXMessageBox.DialogType.INFORMATION,
									"Display selected Point!");
							// The point linked to the UDO will define the
							// location of
							// the UDO we're about to display
							// Point myPoint = (Point)
							// myUDOLinks[0].associatedObject;
							// Point3d myPointCoordinates =
							// myPoint.coordinates();
							// Draw some text at this location to indicate the
							// link type in use
							e.displayContext().displayText(myUDODisplayString, myPointCoordinates,
									UserDefinedObjectDisplayContext.TextRef.BOTTOM_LEFT);

							// theUI.nxmessageBox().show("Info",
							// NXMessageBox.DialogType.INFORMATION,
							// "//e.displayContext().displayText(myUDODisplayString,
							// myPointCoordinates, null)");
							// ' Draw a circle around the linked point in the
							// X-Z plane
							// ' First we must define a matrix to describe the
							// transform from
							// ' Absolute coordinates into the X-Z plane this
							// matrix is the
							// ' "rotation" matrix for our circle.
							Matrix3x3 myMatrix = new Matrix3x3();
							myMatrix.xx = 1;
							myMatrix.xy = 0;
							myMatrix.xz = 0;
							myMatrix.yx = 0;
							myMatrix.yy = 0;
							myMatrix.yz = 1;
							myMatrix.zx = 0;
							myMatrix.zy = -1;
							myMatrix.zz = 0;
							// ' Now we must transform the origin of the circle
							// from Absolute coordinates
							// ' to the coordinates of the circle (ie apply the
							// rotation transform).
							Point3d xformedPoint = theSession.mathUtils().multiply(myMatrix, myPointCoordinates);
							// ' Draw the circle now
							// theUI.nxmessageBox().show("Info",
							// NXMessageBox.DialogType.INFORMATION,
							// "//theSession.mathUtils().multiply(myMatrix,
							// myPointCoordinates)");
							e.displayContext().displayCircle(xformedPoint, myMatrix, 20, false);

							return 0;
						}
					}

				} catch (NXException nex) {
					// ' the display/selection/fit/attention callback is called
					// so many times
					// ' that it's best to print this error handling stuff in
					// the syslog
					// ' any interactive messages in the UI would drive the user
					// crazy ;)
					System.out.println("Caught Exception");
					System.out.println(nex.getMessage());
					try {
						if (theUFSession == null) {
							theUFSession = (UFSession) SessionFactory.get("UFSession");
						}
						theUFSession.ui().writeListingWindow(nex.getMessage());
					} catch (Exception ex1) {
						System.out.println("Caught Exception");
						System.out.println(ex1.getMessage());
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return 0;

	}

	public int myEditCB(nxopen.userdefinedobjects.UserDefinedEvent editEvent) {
		// '--------------------------------------------------------------------------
		// ' Callback: myEditCB
		// ' This is a callback method associated with editing a UDO.
		// '--------------------------------------------------------------------------

		return 0;
	}

	public int myUpdateCB(nxopen.userdefinedobjects.UserDefinedLinkEvent updateEvent) {
		// '--------------------------------------------------------------------------
		// ' Callback: myUpdateCB
		// ' This is a callback method allows you to define the update
		// ' behavior of your UDO. It is only used for specific link types.
		// '
		// ' If your associated object goes through update and it was linked
		// via:
		// ' * owning link - this callback is NOT used
		// ' * type l link - this callback is executed
		// ' * type 2 link - this callback is NOT used
		// ' * type 3 link - this callback is executed
		// ' * type 4 link - this callback is NOT used
		// '--------------------------------------------------------------------------
		return 0;
	}

	public int myDeleteCB(nxopen.userdefinedobjects.UserDefinedLinkEvent updateEven) {
		// '--------------------------------------------------------------------------
		// ' Callback: myDeleteCB
		// ' This is callback is invoked whenever your linked objects get
		// deleted.
		// ' When the linked object is deleted different things can happen
		// ' depending on the link type used.
		// '
		// ' If your associated object goes through delete and it was linked
		// via:
		// ' * owning link - Not Available - you can not delete an object
		// ' when it's owned by a UDO (unless that object is
		// ' a solid body, but this example links to points
		// ' so we don't have to worry about that case).
		// ' * type l link - After executing this callback the UDO itself is
		// deleted
		// ' along with the point.
		// ' * type 2 link - The linked object isn't really deleted it's marked
		// as
		// ' "condemned" and then this callback is invoked.
		// ' * type 3 link - After executing this callback the point is deleted,
		// the
		// ' link is removed from the UDO, and UDO itself is updated.
		// ' * type 4 link - After executing this callback the point is deleted,
		// and
		// ' the link is removed from the UDO.
		// '
		// ' NOTE: With the way this particular UDO's display callback was
		// designed,
		// ' if the links are removed from the UDO, it will no longer be
		// visible.
		// ' Just because you don't see it anymore, does not necessary mean
		// ' the UDO has been deleted (as what happens with both link type 3
		// ' and link type 4). UDO's with link type 1 are the only UDO's to
		// ' commit suicide (delete themselves) whenever the linked object gets
		// ' deleted.
		// '--------------------------------------------------------------------------
		return 0;
	}

	public int initUDO(boolean alertUser) {
		// '--------------------------------------------------------------------------
		// ' initUDO ' Checks to see which (if any) of the application's static
		// ' variables are uninitialized, and sets them accordingly.
		// ' Initializes the UDO class and registers all of its
		// ' callback methods.
		// '--------------------------------------------------------------------------
		try {
			if (theSession == null) {
				theSession = (Session) SessionFactory.get("Session");
			}

			if (theUI == null) {
				theUI = (UI) SessionFactory.get("UI");
			}

			if (myUDOclass == null) {
				if (alertUser == true) {
					// theUI.nxmessageBox().show("Registering UDO Class",
					// NXMessageBox.DialogType.INFORMATION, "Sounds OK");

				}
				// theUI.nxmessageBox().show("Defining UDO Class",
				// NXMessageBox.DialogType.INFORMATION, "Defining UDO Class!");
				// Define your custom UDO class
				myUDOclass = theSession.userDefinedClassManager().createUserDefinedObjectClass("Sample_Linked_UDO",
						"Sample Java Linked UDO");

				// ListingWindow theLW = theSession.listingWindow();
				// theLW.open(); theLW.writeLine(" ");
				// theLW.writeLine("------------------------------------------------------------");
				//
				// theUFSession.ui().writeListingWindow("UDU object is created
				// \n");
				// Setup properties on the custom UDO class
				myUDOclass.setAllowQueryClassFromName(nxopen.userdefinedobjects.UserDefinedClass.AllowQueryClass.ON);
				myUDOclass.setAllowOwnedObjectSelectionOption(
						nxopen.userdefinedobjects.UserDefinedClass.AllowOwnedObjectSelection.ON);
				// Register callbacks for the UDO class
				myUDOclass.addDisplayHandler(this);
				myUDOclass.addAttentionPointHandler(this);
				myUDOclass.addFitHandler(this);
				myUDOclass.addSelectionHandler(this);
				myUDOclass.addEditHandler(this);
				myUDOclass.addUpdateHandler(this);
				myUDOclass.addDeleteHandler(this);
				myUDOclass.addInformationHandler(this);
				// Add this class to the list of object types available for
				// selection in NX.
				// If you skip this step you won't be able to select UDO's of
				// this class,
				// even though you registered a selection callback.
				theUI.selectionManager().setSelectionStatusOfUserDefinedClass(myUDOclass, true);
				// theUI.nxmessageBox().show("Registered UDO Class",
				// NXMessageBox.DialogType.INFORMATION, "UDO Class Registered");
			}
			// theUI.nxmessageBox().show("UDO Class not Null",
			// NXMessageBox.DialogType.INFORMATION, "UDO Class UDO Class not
			// Null!");

		} catch (Exception ex) {
			// try {
			// theUI.nxmessageBox().show("Exception",
			// NXMessageBox.DialogType.INFORMATION, "initUDO() failed!");
			// } catch (RemoteException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// } catch (NXException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
		}
		return 0;
	}

	public int startup() {
		// '--------------------------------------------------------------------------
		// ' NX Startup
		// ' Startup entrypoint used when program is loaded automatically
		// ' as NX starts up. Note this application must be placed in a
		// ' special folder for NX to find and load it during startup.
		// ' Refer to the NX Open documentation for more details on how
		// ' NX finds and loads applications during startup.
		// '--------------------------------------------------------------------------
		initUDO(false);

		return 0;
	}

	public LinkedUDO() throws Exception, RemoteException
	// MZ
	{
		try {
			initUDO(true);
		} catch (Exception ex) {
			System.out.println("Caught Exception");
			System.out.println(ex.getMessage());
			throw new Exception(ex);
		}
	}

	public static void main(String args[]) {
		// '--------------------------------------------------------------------------
		// ' Explicit Activation
		// ' This entry point is used to activate the application explicitly
		// ' via File->Execute->NX Open...
		// '--------------------------------------------------------------------------
		try {
			// theLinkedJavaUDO = new LinkedUDO();
			LinkedUDO theLinkedJavaUDO = new LinkedUDO();
			// ' in case we didn't load this dll at startup...
			// ' attempt to initialize the UDO class

			if (theUFSession == null) {
				theUFSession = (UFSession) SessionFactory.get("UFSession");
			}
			// ' if we don't have any parts open create one
			BasePart myBasePart = theSession.parts().baseDisplay();
			if (myBasePart == null) {
				myBasePart = theSession.parts().newBaseDisplay("test_java_link_udo.prt", BasePart.Units.MILLIMETERS);
			}
			// nxopen.View myView = null;
			// Point3d myCursor = null;
			// myCursor.x = 0;
			// myCursor.y = 0;
			// myCursor.z = 0;

			// ' Prompt user to select a point
			// NXObject selectedPoint = null;
			// Selection.MaskTriple mask= null;
			// mask.type = nxopen.uf.UFConstants.UF_point_type;
			// mask.subtype = nxopen.uf.UFConstants.UF_point_subtype;
			// TODO : SelectObject nur Points!
			// Selection.SelectScreenPositionData selectedPoint =
			// theUI.selectionManager().selectScreenPosition("Select Origin of
			// Java UDO");

			// Selection.MaskTriple( Type, Subtype, SolidBodySubtype)
			// http://www.nxjournaling.com/content/using-mask-triples-selectobject-routine
			Selection.MaskTriple mask[] = { new Selection.MaskTriple(),
					new Selection.MaskTriple(nxopen.uf.UFConstants.UF_point_type, 0, 0),
					new Selection.MaskTriple(nxopen.uf.UFConstants.UF_point_type, nxopen.uf.UFConstants.UF_all_subtype,
							0),
					new Selection.MaskTriple(nxopen.uf.UFConstants.UF_point_subtype, 0, 0),
					new Selection.MaskTriple(nxopen.uf.UFConstants.UF_point_type,
							nxopen.uf.UFConstants.UF_point_subtype, 0),
					new Selection.MaskTriple(nxopen.uf.UFConstants.UF_point_type, nxopen.uf.UFConstants.UF_point_type,
							nxopen.uf.UFConstants.UF_point_type),
					new Selection.MaskTriple(nxopen.uf.UFConstants.UF_solid_type, 0,
							nxopen.uf.UFConstants.UF_point_type),
					new Selection.MaskTriple(nxopen.uf.UFConstants.UF_solid_type, 0,
							nxopen.uf.UFConstants.UF_point_subtype),
					new Selection.MaskTriple(nxopen.uf.UFConstants.UF_solid_type,
							UFConstants.UF_UI_SEL_FEATURE_ANY_EDGE, nxopen.uf.UFConstants.UF_point_subtype),
					new Selection.MaskTriple(nxopen.uf.UFConstants.UF_solid_type,
							UFConstants.UF_UI_SEL_FEATURE_ANY_EDGE, nxopen.uf.UFConstants.UF_point_type),
					// new
					// Selection.MaskTriple(UFConstants.UF_UI_SEL_FEATURE_ANY_EDGE,0,nxopen.uf.UFConstants.UF_point_subtype),
					// new
					// Selection.MaskTriple(UFConstants.UF_UI_SEL_FEATURE_ANY_EDGE,0,nxopen.uf.UFConstants.UF_point_type),
					// new
					// Selection.MaskTriple(UFConstants.UF_UI_SEL_FEATURE_ANY_EDGE,nxopen.uf.UFConstants.UF_point_type,0),
					// new
					// Selection.MaskTriple(UFConstants.UF_UI_SEL_FEATURE_ANY_EDGE,nxopen.uf.UFConstants.UF_point_subtype,0),
					// new
					// Selection.MaskTriple(0,UFConstants.UF_UI_SEL_FEATURE_ANY_EDGE,nxopen.uf.UFConstants.UF_point_type),
					// new
					// Selection.MaskTriple(0,UFConstants.UF_UI_SEL_FEATURE_ANY_EDGE,nxopen.uf.UFConstants.UF_point_subtype),
					new Selection.MaskTriple(UFConstants.UF_line_type, nxopen.uf.UFConstants.UF_point_type, 0),
					new Selection.MaskTriple(UFConstants.UF_line_type, nxopen.uf.UFConstants.UF_point_subtype, 0),
					// new
					// Selection.MaskTriple(UFConstants.UF_UI_SEL_FEATURE_ANY_EDGE,UFConstants.UF_line_type,nxopen.uf.UFConstants.UF_point_type),
					new Selection.MaskTriple(UFConstants.UF_drafting_entity_type, UFConstants.UF_all_subtype, 0),
					new Selection.MaskTriple(UFConstants.UF_dimension_type, UFConstants.UF_all_subtype, 0),
					new Selection.MaskTriple(nxopen.uf.UFConstants.UF_solid_type, 0, UFConstants.UF_all_subtype),
					new Selection.MaskTriple(UFConstants.UF_drafting_entity_type, UFConstants.UF_all_subtype, 0),
					new Selection.MaskTriple(UFConstants.UF_view_type, UFConstants.UF_member_subtype, 0)
					// new
					// Selection.MaskTriple(nxopen.uf.UFConstants.UF_solid_type,0,UFConstants.UF_UI_SEL_FEATURE_ANY_EDGE)

			};
			// new
			// Selection.MaskTriple(nxopen.uf.UFConstants.UF_PARAM_START_POINT,0,0),
			// new
			// Selection.MaskTriple(nxopen.uf.UFConstants.UF_standard_tracking_point_subtype,0,0)};
			// https://solutions.industrysoftware.automation.siemens.com/view.php?sort=desc&q=nxopen+mask+triple+point&p=10&file_type=text&i=nx_api4409&k=7&o=180

			Selection.SelectTaggedObjectsData selectedNXObjects = theUI.selectionManager().selectTaggedObjects("Info",
					"Select one or more points", Selection.SelectionScope.WORK_PART,
					Selection.SelectionAction.CLEAR_AND_ENABLE_SPECIFIC, false, false, mask);// includeFeatures=false,keepHighlighted=false
			// NXObject selectedPoint = (NXObject)
			// theUI.selectionManager().selectObject("Select point to link to
			// UDO", "Select point", Selection.SelectionScope.WORK_PART, false,
			// false);

			// choose selection response:
			// choose Selection.Response.OK for
			// theUI.selectionManager().selectTaggedObjects
			// choose Selection.Response.OBJECT_SELECTED for
			// theUI.selectionManager().selectTaggedObject
			Selection.Response criterium = Selection.Response.OK;

			// instantiate Array
			NXObject[] theNXObjects = new NXObject[selectedNXObjects.objectArray.length];
			// SelectedObjectServerImpl NXObjects4GUIsetup = new
			// SelectedObjectServerImpl();
			// SelectedObjectServer NXObjects4GUIsetup = (SelectedObjectServer)
			// Naming.lookup("//127.0.0.1/NXServer");
			// NXObjects4GUIsetup.setTheNXObjects(theNXObjects);
			// NXObjects4GUIsetup.setCount(theNXObjects.length);

			///// >>>>>>>>>>>>>>>>>>>

			if (selectedNXObjects.response == criterium) {
				// theUI.nxmessageBox().show("Info",
				// NXMessageBox.DialogType.INFORMATION, "//Point(s) selected!");

				// put the selected objects into array
				for (int i = 0; i < selectedNXObjects.objectArray.length; i++)
					theNXObjects[i] = (NXObject) selectedNXObjects.objectArray[i];

			} else {

				// theUI.nxmessageBox().show("Info",
				// NXMessageBox.DialogType.INFORMATION, "//No Points selected!
				// "+ selectedPoint.response.toString() + " != "+
				// Selection.Response.OK.toString());
			}
			// ' Prompt user to select a link type to test
			// ' (Owning, Type 1, Type 2, Type 3, or Type 4)

			// iterate through the selected points to instantiate the array for
			// the default values of the normal distribution
			// setting the values via set method or rmi- object is not possible
			double[][] defaultPointValues = new double[theNXObjects.length][7];
			double[][] defaultLineValues = new double[theNXObjects.length][13];

			// Filter and save all points from the selected Edges, Lines and
			// Points into new array

			for (int i = 0; i < theNXObjects.length; i++) {

				Point3d selectedPoint3d = null;

				if (theNXObjects[i] instanceof Edge) {
					Edge selectedEdge = (Edge) theNXObjects[i];
					VerticesData selectedVerteces = selectedEdge.getVertices();

					Point3d selectedStartPoint3d = (Point3d) selectedVerteces.vertex1;
					Point3d selectedEndPoint3d = (Point3d) selectedVerteces.vertex2;

					theUI.nxmessageBox().show("Info", NXMessageBox.DialogType.INFORMATION, "Edge selected!");

					
					double[] firstline = new double[13];
					
					//defaultLineValues[i][0] = (double) selectedEdge.hashCode();
					firstline[0] = (double) selectedEdge.hashCode();
					// Start Point Coordinates -> Expectation Values
					//defaultLineValues[i][1] = selectedStartPoint3d.x;
					firstline[1] =selectedStartPoint3d.x;
					// theUI.nxmessageBox().show("Info",
					// NXMessageBox.DialogType.INFORMATION, String.valueOf(eX));
					//defaultLineValues[i][2] = selectedStartPoint3d.y;
					firstline[2] = selectedStartPoint3d.y;
					// theUI.nxmessageBox().show("Info",
					// NXMessageBox.DialogType.INFORMATION, String.valueOf(eY));
					//defaultLineValues[i][3] = selectedStartPoint3d.z;
					firstline[3]= selectedStartPoint3d.z;
					// theUI.nxmessageBox().show("Info",
					// NXMessageBox.DialogType.INFORMATION, String.valueOf(eZ));

					// Default values for the variance of vertex 1
					//defaultLineValues[i][4] = 0.51;
					firstline[4]= 0.51;
					//defaultLineValues[i][5] = 1.01;
					firstline[5]= 1.01;
					//defaultLineValues[i][6] = 1.51;
					firstline[6]= 1.51;
					// End Point Coordinates -> Expectation Values
					//defaultLineValues[i][7] = selectedEndPoint3d.x;
					firstline[7] = selectedEndPoint3d.x;
					//defaultLineValues[i][8] = selectedEndPoint3d.y;
					firstline[8] = selectedEndPoint3d.y;
					//defaultLineValues[i][9] = selectedEndPoint3d.z;
					firstline[9] = selectedEndPoint3d.z;
					// theUI.nxmessageBox().show("Info",
					// NXMessageBox.DialogType.INFORMATION,
					// "selectedEndPointz: " + selectedEndPoint3d.z);
					// Default values for the variance of vertex 2
					//defaultLineValues[i][10] = 0.52;
					firstline[10] = 0.52;
					//defaultLineValues[i][11] = 1.02;
					firstline[11] = 1.02;
					//defaultLineValues[i][12] = 1.52;
					firstline[12] = 1.52;
					// write the point coordinates of the selected points to
					// file. nxopen problems prohibit exchange over rmi or
					// setting the values in the dialog object.
					String filepath = "C:/Users/max/desktop/PELOPS/LineArrayExchange.txt";
					LineArray2File fileDialog = new LineArray2File();
					fileDialog.writeArrayToFirstLineInFile(firstline, filepath);
				}

				else if (theNXObjects[i] instanceof Line) {
					Line selectedLine = (Line) theNXObjects[i];

					Point3d selectedStartPoint3d = (Point3d) selectedLine.startPoint();
					Point3d selectedEndPoint3d = (Point3d) selectedLine.endPoint();

					theUI.nxmessageBox().show("Info", NXMessageBox.DialogType.INFORMATION, "Line selected!");

					defaultLineValues[i][0] = (double) selectedLine.hashCode();

					// Start Point
					defaultLineValues[i][1] = selectedStartPoint3d.x;
					// theUI.nxmessageBox().show("Info",
					// NXMessageBox.DialogType.INFORMATION, String.valueOf(eX));
					defaultLineValues[i][2] = selectedStartPoint3d.y;
					// theUI.nxmessageBox().show("Info",
					// NXMessageBox.DialogType.INFORMATION, String.valueOf(eY));
					defaultLineValues[i][3] = selectedStartPoint3d.z;
					// theUI.nxmessageBox().show("Info",
					// NXMessageBox.DialogType.INFORMATION, String.valueOf(eZ));

					// Default values for the variance of vertex 1
					defaultLineValues[i][4] = 0.51;
					defaultLineValues[i][5] = 1.01;
					defaultLineValues[i][6] = 1.51;

					// End Point
					defaultLineValues[i][7] = selectedEndPoint3d.x;
					defaultLineValues[i][8] = selectedEndPoint3d.y;
					defaultLineValues[i][9] = selectedEndPoint3d.z;

					// Default values for the variance of vertex 2
					defaultLineValues[i][10] = 0.52;
					defaultLineValues[i][11] = 1.02;
					defaultLineValues[i][12] = 1.52;

					// write the point coordinates of the selected points to
					// file. nxopen problems prohibit exchange over rmi or
					// setting the values in the dialog object.
					String filepath = "C:/Users/max/desktop/PELOPS/LineArrayExchange.txt";
					LineArray2File fileDialog = new LineArray2File();
					fileDialog.writeArray2File(defaultLineValues, filepath);
				}

				else if (theNXObjects[i] instanceof Point) {
					Point selectedPoint = (Point) theNXObjects[i];
					selectedPoint3d = selectedPoint.coordinates();

					theUI.nxmessageBox().show("Info", NXMessageBox.DialogType.INFORMATION, "Point selected!");
					
					double[] firstline = new double[7];
					
					//defaultPointValues[i][0] = (double) selectedPoint3d.hashCode();
					firstline[0] = (double) selectedPoint3d.hashCode();
					//defaultPointValues[i][1] = selectedPoint3d.x;
					firstline[1] = selectedPoint3d.x;
					// theUI.nxmessageBox().show("Info",
					// NXMessageBox.DialogType.INFORMATION, String.valueOf(eX));
					//defaultPointValues[i][2] = selectedPoint3d.y;
					firstline[2] = selectedPoint3d.y;
					// theUI.nxmessageBox().show("Info",
					// NXMessageBox.DialogType.INFORMATION, String.valueOf(eY));
					//defaultPointValues[i][3] = selectedPoint3d.z;
					firstline[3] = selectedPoint3d.z;
					// theUI.nxmessageBox().show("Info",
					// NXMessageBox.DialogType.INFORMATION, String.valueOf(eZ));

					// Default values for the variance
					//defaultPointValues[i][4] = 0.5;
					firstline[4] = 0.5;
					//defaultPointValues[i][5] = 1.0;
					firstline[5] = 1.0;
					//defaultPointValues[i][6] = 1.5;
					firstline[6] = 1.5;
					// write the point coordinates of the selected points to
					// file. nxopen problems prohibit exchange over rmi or
					// setting the values in the dialog object.
					String filepath = "C:/Users/max/desktop/PELOPS/ArrayExchange.txt";
					Array2File fileDialog = new Array2File();
					//fileDialog.writeArray2File(defaultPointValues, filepath);
					fileDialog.writeArrayToFirstLineInFile(firstline, filepath);

				}

				else {
					theUI.nxmessageBox().show("Info", NXMessageBox.DialogType.INFORMATION,
							"Neighter Point nor Line selected!");
				}
				theLinkedJavaUDO.createUDOs(theNXObjects[i], myBasePart);
			}
			
			// theUI.nxmessageBox().show("Info",
			// NXMessageBox.DialogType.INFORMATION, "Check
			// C:/Users/max/desktop/PELOPS/ArrayExchange.txt!");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void createUDOs(NXObject theNXObject, BasePart myBasePart) {

		if (theNXObject instanceof Point) {
			this.createPointUDO(theNXObject, myBasePart);
		}

		else if (theNXObject instanceof Edge) {
			this.createEdgeUDO(theNXObject, myBasePart);

		}

	}

	private void createPointUDO(NXObject theNXObject, BasePart myBasePart) {
		try {
			theUI.nxmessageBox().show("Info", NXMessageBox.DialogType.INFORMATION, "createPointUDO!");
		} catch (RemoteException | NXException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		double expectationX = 0;
		double expectationY = 0;
		double expectationZ = 0;

		double varianceX = 0;
		double varianceY = 0;
		double varianceZ = 0;

		double sampleNumber = 0;

		myNormalDistributionInputBox themyNormalDistributionInputBox = null;
		DisplayableObject displayableObj = null;

		if (theNXObject instanceof DisplayableObject) {
			displayableObj = (DisplayableObject) theNXObject;

			try {
				displayableObj.highlight();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				theUI.nxmessageBox().show("Info", NXMessageBox.DialogType.INFORMATION,
						"A non-displayable Object has been selected!");
			} catch (RemoteException | NXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			themyNormalDistributionInputBox = new myNormalDistributionInputBox();

			// The following method shows the dialog immediately
			themyNormalDistributionInputBox.show();

			expectationX = themyNormalDistributionInputBox.getBlockProperties("expectationX").getDouble("Value");
			expectationY = themyNormalDistributionInputBox.getBlockProperties("expectationY").getDouble("Value");
			expectationZ = themyNormalDistributionInputBox.getBlockProperties("expectationZ").getDouble("Value");

			varianceX = themyNormalDistributionInputBox.getBlockProperties("varianceX").getDouble("Value");
			varianceY = themyNormalDistributionInputBox.getBlockProperties("varianceY").getDouble("Value");
			varianceZ = themyNormalDistributionInputBox.getBlockProperties("varianceZ").getDouble("Value");

			sampleNumber = (double) themyNormalDistributionInputBox.getBlockProperties("sampleNumber")
					.getInteger("Value");
			int IntsampleNumber = (int) sampleNumber;
			// theUI.nxmessageBox().show("Info",
			// NXMessageBox.DialogType.INFORMATION, "sampleNumber= "+
			// IntsampleNumber);
			// theUI.nxmessageBox().show("Info",
			// NXMessageBox.DialogType.INFORMATION, "udoselected= "+
			// udoselected);

		} catch (Exception ex) {
			// ---- Enter your exception handling code here -----

		} finally {
			if (theNXObject instanceof DisplayableObject) {
				displayableObj = (DisplayableObject) theNXObject;
				try {
					displayableObj.unhighlight();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (themyNormalDistributionInputBox != null) {
				try {
					themyNormalDistributionInputBox.dispose();
				} catch (RemoteException | NXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				themyNormalDistributionInputBox = null;
			}
		}

		////////////////////////////////////////////////////////////////////////

		if (udoselected == 1) {
			udoselected = 0;

			// ' Prompt user to select a link type to test
			// ' (Owning, Type 1, Type 2, Type 3, or Type 4)

			int returnval;
			returnval = 1;

			if (returnval < 0) {
				return;
			} else if (returnval > 4) {
				return;
			} else {

				// ' The user selected a valid point and link type
				// ' go ahead and create the udo
				UserDefinedObjectManager myUDOmanager = null;
				try {
					myUDOmanager = myBasePart.userDefinedObjectManager();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				UserDefinedObject firstUDO = null;
				try {
					firstUDO = myUDOmanager.createUserDefinedObject(myUDOclass);
				} catch (RemoteException | NXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// theUI.nxmessageBox().show("Info",
				// NXMessageBox.DialogType.INFORMATION, "//firstUDO created!");
				// ' set the display properties so users can "see" what
				// ' link type is used for the udo and add the old link objects
				// ' back onto the udo with the new link type
				UserDefinedObject.LinkDefinition[] myLinks = new UserDefinedObject.LinkDefinition[1];
				// theUI.nxmessageBox().show("Info",
				// NXMessageBox.DialogType.INFORMATION, "//myLinks instanciated
				// successfully!");
				myLinks[0] = new UserDefinedObject.LinkDefinition();
				// try {
				// theUI.nxmessageBox().show("Info",
				// NXMessageBox.DialogType.INFORMATION, "//myLinks[0] = new
				// UserDefinedObject.LinkDefinition()!");
				// } catch (RemoteException | NXException e1) {
				// // TODO Auto-generated catch block
				// e1.printStackTrace();
				// }
				myLinks[0].associatedObject = theNXObject;// (NXObject)
															// selectedPoint.objectArray;

//				try {
//					theUI.nxmessageBox().show("Info", NXMessageBox.DialogType.INFORMATION,
//							"//myLinks[0].associatedObject!");
//				} catch (RemoteException | NXException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
				myLinks[0].status = UserDefinedObject.LinkStatus.UP_TO_DATE;
				// theUI.nxmessageBox().show("Info",
				// NXMessageBox.DialogType.INFORMATION, "//myLinks[0].status!");

				try {
					if (returnval == 0) {
						firstUDO.setLineFont(DisplayableObject.ObjectFont.SOLID);
						firstUDO.setLineWidth(DisplayableObject.ObjectWidth.NORMAL);
						firstUDO.setColor(186);
						firstUDO.setLinks(UserDefinedObject.LinkType.OWNING, myLinks);

					}
					if (returnval == 1) {
						firstUDO.setLineFont(DisplayableObject.ObjectFont.DASHED);
						firstUDO.setLineWidth(DisplayableObject.ObjectWidth.NORMAL);
						firstUDO.setColor(36);
						firstUDO.setLinks(UserDefinedObject.LinkType.TYPE1, myLinks);
						firstUDO.setUserAttribute("expectationX", -1, expectationX, nxopen.Update.Option.LATER);
						firstUDO.setUserAttribute("expectationY", -1, expectationY, nxopen.Update.Option.LATER);
						firstUDO.setUserAttribute("expectationZ", -1, expectationZ, nxopen.Update.Option.LATER);

						firstUDO.setUserAttribute("varianceX", -1, varianceX, nxopen.Update.Option.LATER);
						firstUDO.setUserAttribute("varianceY", -1, varianceY, nxopen.Update.Option.LATER);
						firstUDO.setUserAttribute("varianceZ", -1, varianceZ, nxopen.Update.Option.LATER);

						firstUDO.setUserAttribute("sampleNumber", -1, sampleNumber, nxopen.Update.Option.LATER);

						// theUI.nxmessageBox().show("Info",
						// NXMessageBox.DialogType.INFORMATION, "//LinkType 1
						// selected!");
					} else if (returnval == 2) {
						firstUDO.setLineFont(DisplayableObject.ObjectFont.DOTTED);
						firstUDO.setLineWidth(DisplayableObject.ObjectWidth.NORMAL);
						firstUDO.setColor(36);
						firstUDO.setLinks(UserDefinedObject.LinkType.TYPE2, myLinks);
					} else if (returnval == 3) {
						firstUDO.setLineFont(DisplayableObject.ObjectFont.DASHED);
						firstUDO.setLineWidth(DisplayableObject.ObjectWidth.THICK);
						firstUDO.setColor(211);
						firstUDO.setLinks(UserDefinedObject.LinkType.TYPE3, myLinks);
					} else {
						firstUDO.setLineFont(DisplayableObject.ObjectFont.DOTTED);
						firstUDO.setLineWidth(DisplayableObject.ObjectWidth.THICK);
						firstUDO.setColor(211);
						firstUDO.setLinks(UserDefinedObject.LinkType.TYPE4, myLinks);
					}

					// ' store the integer selected by the user with the udo
					// ' this integer will indicate the link type we are testing
					// ' for this given udo
					int[] myUDOints = new int[1];

					myUDOints[0] = returnval;
					// theUI.nxmessageBox().show("Info",
					// NXMessageBox.DialogType.INFORMATION, "//returnval =
					// "+returnval);
					firstUDO.setIntegers(myUDOints);
					// ' add the udo to the display list manually
					// ' this will force the udo to display immediately
					theUFSession.disp().addItemToDisplay(firstUDO.tag());
					// theUI.nxmessageBox().show("Info",
					// NXMessageBox.DialogType.INFORMATION, "//Main finished
					// without exception!");
				} catch (Exception e) {

				}
			}

		}
	}

	private void createEdgeUDO(NXObject theNXObject, BasePart myBasePart) {

		try {
			theUI.nxmessageBox().show("Info", NXMessageBox.DialogType.INFORMATION, "createEdgeUDO!");
		} catch (RemoteException | NXException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		double expectationV1X = 0;
		double expectationV1Y = 0;
		double expectationV1Z = 0;

		double varianceV1X = 0;
		double varianceV1Y = 0;
		double varianceV1Z = 0;

		double expectationV2X = 0;
		double expectationV2Y = 0;
		double expectationV2Z = 0;

		double varianceV2X = 0;
		double varianceV2Y = 0;
		double varianceV2Z = 0;

		double sampleNumberV1 = 0;
		double sampleNumberV2 = 0;

		NormalDistributedLine normalDistributedLineInputBox = null;
		DisplayableObject displayableObj = null;

		if (theNXObject instanceof DisplayableObject) {
			displayableObj = (DisplayableObject) theNXObject;

			try {
				displayableObj.highlight();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				theUI.nxmessageBox().show("Info", NXMessageBox.DialogType.INFORMATION,
						"A non-displayable Object has been selected!");
			} catch (RemoteException | NXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			normalDistributedLineInputBox = new NormalDistributedLine();

			// The following method shows the dialog immediately
			normalDistributedLineInputBox.show();

			// Line Start Point
			expectationV1X = normalDistributedLineInputBox.getBlockProperties("expectationV1X").getDouble("Value");
			expectationV1Y = normalDistributedLineInputBox.getBlockProperties("expectationV1Y").getDouble("Value");
			expectationV1Z = normalDistributedLineInputBox.getBlockProperties("expectationV1Z").getDouble("Value");

			varianceV1X = normalDistributedLineInputBox.getBlockProperties("varianceV1X").getDouble("Value");
			varianceV1Y = normalDistributedLineInputBox.getBlockProperties("varianceV1Y").getDouble("Value");
			varianceV1Z = normalDistributedLineInputBox.getBlockProperties("varianceV1Z").getDouble("Value");

			sampleNumberV1 = (double) normalDistributedLineInputBox.getBlockProperties("sampleNumberV1")
					.getInteger("Value");
			int IntsampleNumberV1 = (int) sampleNumberV1;

			// Line End Point
			expectationV2X = normalDistributedLineInputBox.getBlockProperties("expectationV2X").getDouble("Value");
			expectationV2Y = normalDistributedLineInputBox.getBlockProperties("expectationV2Y").getDouble("Value");
			expectationV2Z = normalDistributedLineInputBox.getBlockProperties("expectationV2Z").getDouble("Value");

			varianceV2X = normalDistributedLineInputBox.getBlockProperties("varianceV2X").getDouble("Value");
			varianceV2Y = normalDistributedLineInputBox.getBlockProperties("varianceV2Y").getDouble("Value");
			varianceV2Z = normalDistributedLineInputBox.getBlockProperties("varianceV2Z").getDouble("Value");

			sampleNumberV2 = (double) normalDistributedLineInputBox.getBlockProperties("sampleNumberV2")
					.getInteger("Value");
			int IntsampleNumberV2 = (int) sampleNumberV2;

			// theUI.nxmessageBox().show("Info",
			// NXMessageBox.DialogType.INFORMATION, "sampleNumber= "+
			// IntsampleNumber);
			// theUI.nxmessageBox().show("Info",
			// NXMessageBox.DialogType.INFORMATION, "udoselected= "+
			// udoselected);

		} catch (Exception ex) {
			// ---- Enter your exception handling code here -----

		} finally {
			if (theNXObject instanceof DisplayableObject) {
				displayableObj = (DisplayableObject) theNXObject;
				try {
					displayableObj.unhighlight();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (normalDistributedLineInputBox != null) {
				try {
					normalDistributedLineInputBox.dispose();
				} catch (RemoteException | NXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				normalDistributedLineInputBox = null;
			}
		}

		////////////////////////////////////////////////////////////////////////

		if (udoselected == 1) {
			udoselected = 0;

			// ' Prompt user to select a link type to test
			// ' (Owning, Type 1, Type 2, Type 3, or Type 4)

			int returnval;
			returnval = 1;

			if (returnval < 0) {
				return;
			} else if (returnval > 4) {
				return;
			} else {

				// ' The user selected a valid point and link type
				// ' go ahead and create the udo
				UserDefinedObjectManager myUDOmanager = null;
				try {
					myUDOmanager = myBasePart.userDefinedObjectManager();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				UserDefinedObject firstUDO = null;
				try {
					firstUDO = myUDOmanager.createUserDefinedObject(myUDOclass);
				} catch (RemoteException | NXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// theUI.nxmessageBox().show("Info",
				// NXMessageBox.DialogType.INFORMATION, "//firstUDO created!");
				// ' set the display properties so users can "see" what
				// ' link type is used for the udo and add the old link objects
				// ' back onto the udo with the new link type
				UserDefinedObject.LinkDefinition[] myLinks = new UserDefinedObject.LinkDefinition[1];
				// theUI.nxmessageBox().show("Info",
				// NXMessageBox.DialogType.INFORMATION, "//myLinks instanciated
				// successfully!");
				myLinks[0] = new UserDefinedObject.LinkDefinition();
				// try {
				// theUI.nxmessageBox().show("Info",
				// NXMessageBox.DialogType.INFORMATION, "//myLinks[0] = new
				// UserDefinedObject.LinkDefinition()!");
				// } catch (RemoteException | NXException e1) {
				// // TODO Auto-generated catch block
				// e1.printStackTrace();
				// }
				myLinks[0].associatedObject = theNXObject;// (NXObject)

				// try {
				// theUI.nxmessageBox().show("Info",
				// NXMessageBox.DialogType.INFORMATION,
				// "//myLinks[0].associatedObject!");
				// } catch (RemoteException | NXException e1) {
				// // TODO Auto-generated catch block
				// e1.printStackTrace();
				// }
				myLinks[0].status = UserDefinedObject.LinkStatus.UP_TO_DATE;
				// theUI.nxmessageBox().show("Info",
				// NXMessageBox.DialogType.INFORMATION, "//myLinks[0].status!");

				try {
					if (returnval == 0) {
						firstUDO.setLineFont(DisplayableObject.ObjectFont.SOLID);
						firstUDO.setLineWidth(DisplayableObject.ObjectWidth.NORMAL);
						firstUDO.setColor(186);
						firstUDO.setLinks(UserDefinedObject.LinkType.OWNING, myLinks);

					}
					if (returnval == 1) {
						firstUDO.setLineFont(DisplayableObject.ObjectFont.DASHED);
						firstUDO.setLineWidth(DisplayableObject.ObjectWidth.NORMAL);
						firstUDO.setColor(36);
						firstUDO.setLinks(UserDefinedObject.LinkType.TYPE1, myLinks);
						firstUDO.setUserAttribute("expectationV1X", -1, expectationV1X, nxopen.Update.Option.LATER);
						firstUDO.setUserAttribute("expectationV1Y", -1, expectationV1Y, nxopen.Update.Option.LATER);
						firstUDO.setUserAttribute("expectationV1Z", -1, expectationV1Z, nxopen.Update.Option.LATER);

						firstUDO.setUserAttribute("varianceV1X", -1, varianceV1X, nxopen.Update.Option.LATER);
						firstUDO.setUserAttribute("varianceV1Y", -1, varianceV1Y, nxopen.Update.Option.LATER);
						firstUDO.setUserAttribute("varianceV1Z", -1, varianceV1Z, nxopen.Update.Option.LATER);

						firstUDO.setUserAttribute("sampleNumberV1", -1, sampleNumberV1, nxopen.Update.Option.LATER);

						firstUDO.setUserAttribute("expectationV2X", -1, expectationV2X, nxopen.Update.Option.LATER);
						firstUDO.setUserAttribute("expectationV2Y", -1, expectationV2Y, nxopen.Update.Option.LATER);
						firstUDO.setUserAttribute("expectationV2Z", -1, expectationV2Z, nxopen.Update.Option.LATER);

						firstUDO.setUserAttribute("varianceV2X", -1, varianceV2X, nxopen.Update.Option.LATER);
						firstUDO.setUserAttribute("varianceV2Y", -1, varianceV2Y, nxopen.Update.Option.LATER);
						firstUDO.setUserAttribute("varianceV2Z", -1, varianceV2Z, nxopen.Update.Option.LATER);

						firstUDO.setUserAttribute("sampleNumberV2", -1, sampleNumberV2, nxopen.Update.Option.LATER);

						// theUI.nxmessageBox().show("Info",
						// NXMessageBox.DialogType.INFORMATION, "//LinkType 1
						// selected!");
					} else if (returnval == 2) {
						firstUDO.setLineFont(DisplayableObject.ObjectFont.DOTTED);
						firstUDO.setLineWidth(DisplayableObject.ObjectWidth.NORMAL);
						firstUDO.setColor(36);
						firstUDO.setLinks(UserDefinedObject.LinkType.TYPE2, myLinks);
					} else if (returnval == 3) {
						firstUDO.setLineFont(DisplayableObject.ObjectFont.DASHED);
						firstUDO.setLineWidth(DisplayableObject.ObjectWidth.THICK);
						firstUDO.setColor(211);
						firstUDO.setLinks(UserDefinedObject.LinkType.TYPE3, myLinks);
					} else {
						firstUDO.setLineFont(DisplayableObject.ObjectFont.DOTTED);
						firstUDO.setLineWidth(DisplayableObject.ObjectWidth.THICK);
						firstUDO.setColor(211);
						firstUDO.setLinks(UserDefinedObject.LinkType.TYPE4, myLinks);
					}

					// ' store the integer selected by the user with the udo
					// ' this integer will indicate the link type we are testing
					// ' for this given udo
					int[] myUDOints = new int[1];

					myUDOints[0] = returnval;
					// theUI.nxmessageBox().show("Info",
					// NXMessageBox.DialogType.INFORMATION, "//returnval =
					// "+returnval);
					firstUDO.setIntegers(myUDOints);
					// ' add the udo to the display list manually
					// ' this will force the udo to display immediately
					theUFSession.disp().addItemToDisplay(firstUDO.tag());
					// theUI.nxmessageBox().show("Info",
					// NXMessageBox.DialogType.INFORMATION, "//Main finished
					// without exception!");
				} catch (Exception e) {

				}
			}

		}
	}

	public int getUnloadOption(String dummy) {
		// '--------------------------------------------------------------------------
		// ' GetUnloadOption
		// ' Tells NX when to unload this application.
		// ' This MUST return AtTermination because we have UDO callbacks
		// ' defined in this program. Otherwise NX could try to call
		// ' one of the UDO callbacks, after it had already unloaded
		// ' the application.
		// '--------------------------------------------------------------------------

		// return BaseSession.LibraryUnloadOption.EXPLICITLY;
		return nxopen.Session.LibraryUnloadOption.AT_TERMINATION;

	}

}
