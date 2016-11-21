package com.sfb805.udo;

import java.rmi.RemoteException;

import org.apache.commons.lang3.ArrayUtils;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import nxopen.BasePart;
import nxopen.DisplayableObject;
import nxopen.Edge;
import nxopen.Edge.VerticesData;
import nxopen.Line;
import nxopen.Matrix3x3;
import nxopen.NXException;
import nxopen.NXMessageBox;
import nxopen.NXObject;
import nxopen.NXObject.AttributeInformation;
import nxopen.NXObject.AttributeType;
import nxopen.Point;
import nxopen.Point3d;
import nxopen.PointCollection;
import nxopen.Scalar;
import nxopen.Selection;
import nxopen.Session;
import nxopen.SessionFactory;
import nxopen.SmartObject;
import nxopen.UFSession;
import nxopen.UI;
import nxopen.uf.UFConstants;
import nxopen.userdefinedobjects.UserDefinedClass;
import nxopen.userdefinedobjects.UserDefinedDisplayEvent;
import nxopen.userdefinedobjects.UserDefinedEvent;
import nxopen.userdefinedobjects.UserDefinedLinkEvent;
import nxopen.userdefinedobjects.UserDefinedObject;
import nxopen.userdefinedobjects.UserDefinedObject.LinkType;
import nxopen.userdefinedobjects.UserDefinedObjectDisplayContext;
import nxopen.userdefinedobjects.UserDefinedObjectManager;
import nxopen.userdefinedobjects.UserDefinedObjectManager.LinkedUdoDefinition;

public class UDOCollection implements nxopen.userdefinedobjects.UserDefinedClass.DisplayCallback,
		nxopen.userdefinedobjects.UserDefinedClass.GenericCallback,
		nxopen.userdefinedobjects.UserDefinedClass.LinkCallback {

	public static UserDefinedClass myUDOclass = null;
	public static Session theSession = null;
	public static UFSession theUFSession = null;
	public static UI theUI;
	public static BasePart myBasePart;
	public static int udoselected = 1; // 0/1
	public static UserDefinedObjectManager myUDOmanager = null;
	public String selectedUDOtype;

	// Class Assertion
	public NXObject[] classAssertion_selectedObjects;
	public String[] classAssertion_selectedPoints;
	public String selectedClassName;

	// Object Property Assertion
	public NXObject[] selectedSubjectNXObjects;
	public String[] selectedSubjectPoints;
	public String selectedObjectPropertyName;
	public NXObject[] selectedObjectNXObjects;
	public String[] selectedObjectPoints;

	// ObjectPropertyPointUDO: for connecting two Vertexes on one or two Edges
	// with one OWL-ObjectProperty
	// NormalDistributionUDO: for connecting expectation and variance to two
	// Vertexes on one Edge
	// ClassAssertionPointUDO: for connecting one Point with one Class

	public void setSelectedUDOtype(String UDOtype) {
		selectedUDOtype = UDOtype;
	}

	public String getSelectedUDOtype() {
		return selectedUDOtype;
	}

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

	public void myNormalDistributionDisplayCB(nxopen.userdefinedobjects.UserDefinedDisplayEvent e) {
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

					}
					if (myUDOLinks.length == 0) {
						// theUI.nxmessageBox().show("Info",
						// NXMessageBox.DialogType.INFORMATION, "//Don´t bother
						// to display UDO without linked points!");
						// The linked point was missing (the point may have been
						// deleted)
						// Don't bother to display any UDO's without linked
						// points.

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

							// theUI.nxmessageBox().show("Info",
							// NXMessageBox.DialogType.INFORMATION,
							// "Display selected Edge!");

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

						}

						else if (myUDOLinks[0].associatedObject instanceof Line) {
							Point3d myPointCoordinatesV1 = null;
							Point3d myPointCoordinatesV2 = null;
							Line selectedLine = (Line) myUDOLinks[0].associatedObject;
							myPointCoordinatesV1 = (Point3d) selectedLine.startPoint();
							myPointCoordinatesV2 = (Point3d) selectedLine.endPoint();

							// theUI.nxmessageBox().show("Info",
							// NXMessageBox.DialogType.INFORMATION,
							// "Display selected Line!");

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

						}

						else if (myUDOLinks[0].associatedObject instanceof Point) {
							Point3d myPointCoordinates = null;
							Point selectedPoint = (Point) myUDOLinks[0].associatedObject;
							myPointCoordinates = selectedPoint.coordinates();

							// theUI.nxmessageBox().show("Info",
							// NXMessageBox.DialogType.INFORMATION,
							// "Display selected Point!");
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

	}

	public int myDirectionDisplayCB(nxopen.userdefinedobjects.UserDefinedDisplayEvent e) {

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
						// myUDODisplayString = "Type 1";
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

						Point3d[] arrowline = new Point3d[2]; // indicates the
																// direction of
																// the Property
																// visually
						Point3d arrowhead = null;
						Matrix3x3 myMatrix = new Matrix3x3();

						for (int i = 0; i < myUDOLinks.length; i++) {

							if (myUDOLinks[i].associatedObject instanceof Edge) {

								Edge selectedEdge = (Edge) myUDOLinks[i].associatedObject;
								VerticesData selectedVerteces = selectedEdge.getVertices();

								Point3d myPointCoordinatesV1 = (Point3d) selectedVerteces.vertex1;
								Point3d myPointCoordinatesV2 = (Point3d) selectedVerteces.vertex2;
								Point3d mySelectedSubjectVertex = null;
								Point3d mySelectedObjectVertex = null;

								// Select Vertexes out of Edges
								LinkedUdoDefinition[] udolinks = myUDOmanager.getLinksToObject(selectedEdge);
								UserDefinedObject udo = udolinks[0].associatedUdo;

								// Selected Vertex in Subject Edge
								Point3d subject = null;

								// Selected Vertex in Object Edge
								Point3d object = null;

								// Selected ObjectProperty between Subject Edge
								// and Object Edge
								AttributeInformation info_predicat = udo.getUserAttribute("Predicat",
										AttributeType.STRING, -1);
								myUDODisplayString = info_predicat.stringValue;

								// subject
								if (i == 0) {
									AttributeInformation info_subject_x = udo.getUserAttribute("Subject",
											AttributeType.STRING, -1);

									String subjectID = info_subject_x.stringValue;

									// V1 or V2 are set in NXObjectSelector
									if (subjectID.equals("V1")) {
										mySelectedSubjectVertex = myPointCoordinatesV1;

										// theUI.nxmessageBox().show("Info",
										// NXMessageBox.DialogType.INFORMATION,
										// "vertex1.");
									} else if (subjectID.equals("V2")) {
										mySelectedSubjectVertex = myPointCoordinatesV2;

										// theUI.nxmessageBox().show("Info",
										// NXMessageBox.DialogType.INFORMATION,
										// "vertex2.");
									} else {
										// theUI.nxmessageBox().show("Info",
										// NXMessageBox.DialogType.INFORMATION,
										// "hash codes do no match.");
									}
									arrowline[0] = mySelectedSubjectVertex;

								}
								// object
								else if (i == 1) {
									AttributeInformation info_object_x = udo.getUserAttribute("Object",
											AttributeType.STRING, -1);

									String objectID = info_object_x.stringValue;

									if (objectID.equals("V1")) {
										mySelectedObjectVertex = myPointCoordinatesV1;

										// theUI.nxmessageBox().show("Info",
										// NXMessageBox.DialogType.INFORMATION,
										// "vertex1.");
									} else if (objectID.equals("V2")) {
										mySelectedObjectVertex = myPointCoordinatesV2;

										// theUI.nxmessageBox().show("Info",
										// NXMessageBox.DialogType.INFORMATION,
										// "vertex2.");
									} else {
										// theUI.nxmessageBox().show("Info",
										// NXMessageBox.DialogType.INFORMATION,
										// "hash codes do no match.");
									}
									arrowline[1] = mySelectedObjectVertex;

									myMatrix.xx = 1;
									myMatrix.xy = 0;
									myMatrix.xz = 0;
									myMatrix.yx = 0;
									myMatrix.yy = 0;
									myMatrix.yz = 1;
									myMatrix.zx = 0;
									myMatrix.zy = -1;
									myMatrix.zz = 0;

									arrowhead = theSession.mathUtils().multiply(myMatrix, mySelectedObjectVertex);

								}

							} else if (myUDOLinks[i].associatedObject instanceof Line) {
								theUI.nxmessageBox().show("Info", NXMessageBox.DialogType.INFORMATION,
										"Function not yet defined for Line.");

							}

							else if (myUDOLinks[i].associatedObject instanceof Point3d) {

								theUI.nxmessageBox().show("Info", NXMessageBox.DialogType.INFORMATION,
										"Function not yet defined for Point3d.");

							}

						}

						// theUI.nxmessageBox().show("Info",
						// NXMessageBox.DialogType.INFORMATION,
						// "arrowline from:"+ arrowline[0].x+"/"+
						// arrowline[0].y+"/"+ arrowline[0].z+"/_to:
						// "+arrowline[1].x+"/"+arrowline[1].y+"/"+arrowline[1].z);

						// if i == 1 then this is the second associated
						// object
						// which is the object where the object property
						// shall point at
						Point3d arrowlineMidpoint = new Point3d();
						arrowlineMidpoint.x = (arrowline[0].x + arrowline[1].x) / 2;
						arrowlineMidpoint.y = (arrowline[0].y + arrowline[1].y) / 2;
						arrowlineMidpoint.z = (arrowline[0].z + arrowline[1].z) / 2;

						// theUI.nxmessageBox().show("Info",
						// NXMessageBox.DialogType.INFORMATION,
						// "Display direction of selected property!");
						e.displayContext().displayCircle(arrowhead, myMatrix, 10, false);
						e.displayContext().displayPolyline(arrowline);
						e.displayContext().displayText(myUDODisplayString, arrowlineMidpoint,
								UserDefinedObjectDisplayContext.TextRef.BOTTOM_LEFT);

						return 0;

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

	public void myClassAssertionDisplayCB(nxopen.userdefinedobjects.UserDefinedDisplayEvent e) {
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

					}
					if (myUDOLinks.length == 0) {
						// theUI.nxmessageBox().show("Info",
						// NXMessageBox.DialogType.INFORMATION, "//Don´t bother
						// to display UDO without linked points!");
						// The linked point was missing (the point may have been
						// deleted)
						// Don't bother to display any UDO's without linked
						// points.

					} else {
						// theUI.nxmessageBox().show("Info",
						// NXMessageBox.DialogType.INFORMATION, "//Let´s
						// display!");

						if (myUDOLinks[0].associatedObject instanceof Edge) {

							Edge selectedEdge = (Edge) myUDOLinks[0].associatedObject;
							VerticesData selectedVerteces = selectedEdge.getVertices();

							Point3d myPointCoordinatesV1 = (Point3d) selectedVerteces.vertex1;
							Point3d myPointCoordinatesV2 = (Point3d) selectedVerteces.vertex2;
							Point3d mySelectedIndividualVertex = null;
							// Point3d mySelectedObjectVertex = null;

							// Select Vertexes out of Edges
							LinkedUdoDefinition[] udolinks = myUDOmanager.getLinksToObject(selectedEdge);
							UserDefinedObject udo = udolinks[0].associatedUdo;

							// Selected Vertex in Subject Edge
							Point3d subject = null;

							// Selected Vertex in Object Edge
							Point3d object = null;

							AttributeInformation info_individual = udo.getUserAttribute("Individual",
									AttributeType.STRING, -1);
							String individualID = info_individual.stringValue;

							AttributeInformation info_class = udo.getUserAttribute("Class", AttributeType.STRING, -1);
							myUDODisplayString = info_class.stringValue;

							// V1 or V2 are set in NXObjectSelector
							if (individualID.equals("V1")) {
								mySelectedIndividualVertex = myPointCoordinatesV1;

								// theUI.nxmessageBox().show("Info",
								// NXMessageBox.DialogType.INFORMATION,
								// "vertex1.");
							} else if (individualID.equals("V2")) {
								mySelectedIndividualVertex = myPointCoordinatesV2;

								// theUI.nxmessageBox().show("Info",
								// NXMessageBox.DialogType.INFORMATION,
								// "vertex2.");
							}
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

							Point3d xformedPointInClass = theSession.mathUtils().multiply(myMatrix,
									mySelectedIndividualVertex);

							e.displayContext().displayCircle(xformedPointInClass, myMatrix, 30, false);

							e.displayContext().displayText(myUDODisplayString, mySelectedIndividualVertex,
									UserDefinedObjectDisplayContext.TextRef.BOTTOM_LEFT);
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

	}

	public int myDisplayCB(nxopen.userdefinedobjects.UserDefinedDisplayEvent e) throws RemoteException, NXException {

		// String UDOtype = null;
		// try {
		// UDOtype = e.userDefinedObject().getUserAttribute("UDOtype",
		// AttributeType.STRING, -1).stringValue;
		// theUI.nxmessageBox().show("Info",
		// NXMessageBox.DialogType.INFORMATION, "UDOtype: "+UDOtype);
		// } catch (RemoteException | NXException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }

		if (new String("ObjectPropertyPointUDO")
				.equals(e.userDefinedObject().getUserAttribute("UDOtype", AttributeType.STRING, -1).stringValue)) {
			// if(selectedUDOtype==1){

			this.myDirectionDisplayCB(e);
			return 0;
		} else if (new String("NormalDistributionUDO")
				.equals(e.userDefinedObject().getUserAttribute("UDOtype", AttributeType.STRING, -1).stringValue)) {

			this.myNormalDistributionDisplayCB(e);
			return 0;
		} else if (new String("ClassAssertionPointUDO")
				.equals(e.userDefinedObject().getUserAttribute("UDOtype", AttributeType.STRING, -1).stringValue)) {

			this.myClassAssertionDisplayCB(e);
			return 0;
		}

		return 0;
	}

	public int myEditCB(nxopen.userdefinedobjects.UserDefinedEvent editEvent) {
		// '--------------------------------------------------------------------------
		// ' Callback: myEditCB
		// ' This is a callback method associated with editing a UDO.
		// '--------------------------------------------------------------------------
		try {
			theUI.nxmessageBox().show("Info", NXMessageBox.DialogType.INFORMATION, "Edit UDO!");
		} catch (RemoteException | NXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

			if (theUI == null) {
				theUI = (UI) SessionFactory.get("UI");

				// theUI.nxmessageBox().show("Info",
				// NXMessageBox.DialogType.INFORMATION,
				// "theUI == null ->get theUI!");

			}

			if (theSession == null) {
				theSession = (Session) SessionFactory.get("Session");
				// theUI.nxmessageBox().show("Info",
				// NXMessageBox.DialogType.INFORMATION,
				// "theSession == null ->get the Session!");
			}

			if (theUFSession == null) {
				theUFSession = (UFSession) SessionFactory.get("UFSession");

				// theUI.nxmessageBox().show("Info",
				// NXMessageBox.DialogType.INFORMATION,
				// "thUFSession == null -> get UFSession!");
			}

			myBasePart = theSession.parts().baseDisplay();
			if (myBasePart == null) {
				myBasePart = theSession.parts().newBaseDisplay("test_direction_udo.prt", BasePart.Units.MILLIMETERS);
			}

			try {
				myUDOmanager = myBasePart.userDefinedObjectManager();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (myUDOclass == null) {
				if (alertUser == true) {
					// theUI.nxmessageBox().show("Registering UDO Class",
					// NXMessageBox.DialogType.INFORMATION, "Sounds OK");

				}
				// theUI.nxmessageBox().show("Info",
				// NXMessageBox.DialogType.INFORMATION,
				// "myUDOclass == null -> initialize myUDOclass!");
				// theUI.nxmessageBox().show("Defining UDO Class",
				// NXMessageBox.DialogType.INFORMATION, "Defining UDO Class!");
				// Define your custom UDO class
				myUDOclass = theSession.userDefinedClassManager().createUserDefinedObjectClass("UncertaintyUDO",
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

	public UDOCollection()
	// MZ
	{
		initUDO(true);

	}

	public static void main(String args[]) throws RemoteException, NXException, OWLOntologyCreationException {

		// get and set all necessary class attributes
		UDOCollection udoCollection = new UDOCollection();

		// connect two points with an object property
		udoCollection.objectPropertyPointUDO();

		// connect one point to a class
		udoCollection.classAssertionPointUDO();

		// connect one or more points to a normal distribution
		udoCollection.normalDistributionUDO();

	}

	public void normalDistributionUDO() throws RemoteException, NXException {
		// set to normal distribution udo
		this.setSelectedUDOtype("NormalDistributionUDO");
		NXMessageBox nxmessageBox = theUI.nxmessageBox();
		// nxmessageBox.show("Info", NXMessageBox.DialogType.INFORMATION,
		// "normalDistributionUDO()!");

		if (new String("NormalDistributionUDO").equals(this.selectedUDOtype)) {

			NXObject[] theNXObjects = this.selectNormalDistributedObjects();

			// for (int i = 0; i < theNXObjects.length; i++) {
			for (int i = theNXObjects.length - 1; i >= 0; i--) {
				// theUI.nxmessageBox().show("Info",
				// NXMessageBox.DialogType.INFORMATION, "Create UDO!");
				this.createNormalDistributionUDO(theNXObjects[i], myBasePart);
			}

		}
	}

	public void classAssertionPointUDO() throws OWLOntologyCreationException, RemoteException, NXException {

		this.selectPoint4classAssertion();
		// set class name
		// nxObjectSelector.setSelectedClassName(":dummyClass");
		// get class name
		this.selectClass4ClassAssertion();
		// nxObjectSelector.getSelectedClassName();
		// Create UDO linked to the Point and specifying its class
		// affiliation
		this.createClassAssertionUDOs(this.classAssertion_selectedObjects, this.classAssertion_selectedPoints,
				this.selectedClassName, myBasePart);

	}

	public void selectPoint4classAssertion() throws OWLOntologyCreationException {

		this.setSelectedUDOtype("ClassAssertionPointUDO");

		if (new String("ClassAssertionPointUDO").equals(this.selectedUDOtype)) {
			int NumberOfPointsToBeSelected = 1;
			NXObjectSelector nxObjectSelector = new NXObjectSelector(myBasePart, theUI, theSession,
					NumberOfPointsToBeSelected);
			// Select Class to which the Point shall be assigned to
			// ToDo
			// get the selected Edge Objects
			this.classAssertion_selectedObjects = nxObjectSelector.getLinkableParentObjects();
			// Identify Vertexes closest to the click pick point
			this.classAssertion_selectedPoints = nxObjectSelector.getSelectedPoints();
		}
	}

	public void selectClass4ClassAssertion() {
		this.selectedClassName = ":dummyClass";
	}

	public void objectPropertyPointUDO() throws OWLOntologyCreationException, RemoteException, NXException {

		this.selectSubject4PropertyAssertion();
		this.selectProperty4PropertyAssertion();
		this.selectObject4PropertyAssertion();
		this.createObjectPropertyAssertion(
				ArrayUtils.addAll(this.selectedSubjectNXObjects, this.selectedObjectNXObjects),
				ArrayUtils.addAll(this.selectedSubjectPoints, this.selectedObjectPoints), this.selectedClassName,
				this.myBasePart);
		// set to ObjectPropertyPointUDO

	}

	public void selectSubject4PropertyAssertion() throws OWLOntologyCreationException {
		// Select One Point
		int NumberOfPointsToBeSelected = 1;
		// Create Dialog for selecting two Vertexes on Edges with direction
		// information
		NXObjectSelector nxObjectSelector = new NXObjectSelector(myBasePart, theUI, theSession,
				NumberOfPointsToBeSelected);
		// Select Object Property between Subject and Object
		// ToDo
		// get the selected Edge Objects
		selectedSubjectNXObjects = nxObjectSelector.getLinkableParentObjects();
		// Identify Vertexes closest to the click pick point
		selectedSubjectPoints = nxObjectSelector.getSelectedPoints();

	}

	public void selectProperty4PropertyAssertion() {
		// set ObjectProperty
		this.selectedObjectPropertyName = ":dummyProperty";
	}

	public void selectObject4PropertyAssertion() throws OWLOntologyCreationException {
		// Select One Point
		int NumberOfPointsToBeSelected = 1;
		// Create Dialog for selecting two Vertexes on Edges with direction
		// information
		NXObjectSelector nxObjectSelector = new NXObjectSelector(myBasePart, theUI, theSession,
				NumberOfPointsToBeSelected);
		// Select Object Property between Subject and Object
		// ToDo
		// get the selected Edge Objects

		selectedObjectNXObjects = nxObjectSelector.getLinkableParentObjects();
		selectedObjectPoints = nxObjectSelector.getSelectedPoints();
	}

	public void createObjectPropertyAssertion(NXObject[] selectedObjects, String[] selectedPoints,
			String selectedObjectProperty, BasePart myBasePart) throws RemoteException, NXException {
		this.setSelectedUDOtype("ObjectPropertyPointUDO");

		if (new String("ObjectPropertyPointUDO").equals(this.selectedUDOtype)) {
			// Select the Object Property that shall link the selected Points
			// String selectedObjectProperty = this.selectedObjectPropertyName;
			// // Create UDO out of Result set
			//
			// NXObject[] selectedObjects =
			// ArrayUtils.addAll(selectedSubjectNXObjects,
			// selectedObjectNXObjects);
			// String[] selectedPoints =
			// ArrayUtils.addAll(selectedSubjectPoints, selectedObjectPoints);
			this.createDirectionUDOs(selectedObjects, selectedPoints, selectedObjectProperty, myBasePart);
		}
	}

	public void createClassAssertionUDOs(NXObject[] selectedObjects, String[] selectedPoints, String selectedClassName,
			BasePart myBasePart) throws RemoteException, NXException {

		for (int i = 0; i < selectedObjects.length; i++) {

			this.createPointClassUDO(selectedObjects[i], selectedPoints[i], selectedClassName, myBasePart);

		}
	}

	private void createPointClassUDO(NXObject selectedObject, String selectedPoint, String selectedClassName,
			BasePart myBasePart) throws RemoteException, NXException {

		if (udoselected == 1) {
			// udoselected = 0;

			// ' Prompt user to select a link type to test
			// ' (Owning, Type 1, Type 2, Type 3, or Type 4)

			// ' The user selected a valid point and link type
			// ' go ahead and create the udo

			UserDefinedObject firstUDO = null;

			try {
				firstUDO = myUDOmanager.createUserDefinedObject(myUDOclass);
			} catch (RemoteException | NXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			UserDefinedObject.LinkDefinition[] myLinks = new UserDefinedObject.LinkDefinition[1];

			myLinks[0] = new UserDefinedObject.LinkDefinition();

			Edge selectedEdge = (Edge) selectedObject;

			myLinks[0].associatedObject = selectedEdge;// (NXObject)

			myLinks[0].status = UserDefinedObject.LinkStatus.UP_TO_DATE;

			firstUDO.setLineFont(DisplayableObject.ObjectFont.DASHED);
			firstUDO.setLineWidth(DisplayableObject.ObjectWidth.NORMAL);
			firstUDO.setColor(186);
			firstUDO.setLinks(UserDefinedObject.LinkType.TYPE1, myLinks);

			firstUDO.setUserAttribute("UDOtype", -1, "ClassAssertionPointUDO", nxopen.Update.Option.NOW);
			// Both hash and vertexnumber change from here to display udo.
			// identification via geometric data necessary

			firstUDO.setUserAttribute("Individual", -1, selectedPoint, nxopen.Update.Option.NOW);
			firstUDO.setUserAttribute("Class", -1, selectedClassName, nxopen.Update.Option.NOW);

			// ' store the integer selected by the user with the udo
			// ' this integer will indicate the link type we are testing
			// ' for this given udo
			int[] myUDOints = new int[1];

			myUDOints[0] = 1; // Type1 udoLink
			// theUI.nxmessageBox().show("Info",
			// NXMessageBox.DialogType.INFORMATION, "//returnval =
			// "+returnval);
			firstUDO.setIntegers(myUDOints);
			// ' add the udo to the display list manually
			// ' this will force the udo to display immediately
			// theUI.nxmessageBox().show("Info",
			// NXMessageBox.DialogType.INFORMATION,
			// "Try to add PointClassUDO to Display!");

			theUFSession.disp().addItemToDisplay(firstUDO.tag());

			// theUI.nxmessageBox().show("Info",
			// NXMessageBox.DialogType.INFORMATION,
			// "added PointClassUDO to Display successfully!");

		}

	}

	private void createNormalDistributionUDO(NXObject theNXObject, BasePart myBasePart) {

		if (theNXObject instanceof Point) {
			this.createNormalDistributionPointUDO(theNXObject, myBasePart);
		}

		else if (theNXObject instanceof Edge) {
			this.createNormalDistributionEdgeUDO(theNXObject, myBasePart);

		}

	}

	private void createNormalDistributionPointUDO(NXObject theNXObject, BasePart myBasePart) {
		// try {
		// theUI.nxmessageBox().show("Info",
		// NXMessageBox.DialogType.INFORMATION, "createPointUDO!");
		// } catch (RemoteException | NXException e2) {
		// // TODO Auto-generated catch block
		// e2.printStackTrace();
		// }
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

						firstUDO.setUserAttribute("UDOtype", -1, "NormalDistributionUDO", nxopen.Update.Option.NOW);

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

	private void createNormalDistributionEdgeUDO(NXObject theNXObject, BasePart myBasePart) {

		// try {
		// theUI.nxmessageBox().show("Info",
		// NXMessageBox.DialogType.INFORMATION, "createEdgeUDO!");
		// } catch (RemoteException | NXException e2) {
		// // TODO Auto-generated catch block
		// e2.printStackTrace();
		// }

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

			// Expectation values relative to the point coordinates
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

			// Expectation values relative to the point coordinates
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

		// if (udoselected == 1) {
		// udoselected = 0;

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

					firstUDO.setUserAttribute("UDOtype", -1, "NormalDistributionUDO", nxopen.Update.Option.NOW);

					firstUDO.setUserAttribute("expectationV1X", -1, expectationV1X, nxopen.Update.Option.LATER);
					firstUDO.setUserAttribute("expectationV1Y", -1, expectationV1Y, nxopen.Update.Option.LATER);
					firstUDO.setUserAttribute("expectationV1Z", -1, expectationV1Z, nxopen.Update.Option.LATER);

					firstUDO.setUserAttribute("varianceV1X", -1, varianceV1X, nxopen.Update.Option.LATER);
					firstUDO.setUserAttribute("varianceV1Y", -1, varianceV1Y, nxopen.Update.Option.LATER);
					firstUDO.setUserAttribute("varianceV1Z", -1, varianceV1Z, nxopen.Update.Option.LATER);

					Edge selectedEdge = (Edge) theNXObject;

					// theUI.nxmessageBox().show("Info",
					// NXMessageBox.DialogType.INFORMATION,
					// "//CreateNormalDistributionEdgeUDO_Vertex1Hash: "
					// + selectedEdge.getVertices().vertex1.hashCode());

					firstUDO.setUserAttribute("sampleNumberV1", -1, sampleNumberV1, nxopen.Update.Option.LATER);

					firstUDO.setUserAttribute("expectationV2X", -1, expectationV2X, nxopen.Update.Option.LATER);
					firstUDO.setUserAttribute("expectationV2Y", -1, expectationV2Y, nxopen.Update.Option.LATER);
					firstUDO.setUserAttribute("expectationV2Z", -1, expectationV2Z, nxopen.Update.Option.LATER);

					firstUDO.setUserAttribute("varianceV2X", -1, varianceV2X, nxopen.Update.Option.LATER);
					firstUDO.setUserAttribute("varianceV2Y", -1, varianceV2Y, nxopen.Update.Option.LATER);
					firstUDO.setUserAttribute("varianceV2Z", -1, varianceV2Z, nxopen.Update.Option.LATER);

					firstUDO.setUserAttribute("sampleNumberV2", -1, sampleNumberV2, nxopen.Update.Option.LATER);

					// theUI.nxmessageBox().show("Info",
					// NXMessageBox.DialogType.INFORMATION,
					// "//CreateNormalDistributionEdgeUDO_Vertex2Hash: "
					// + selectedEdge.getVertices().vertex2.hashCode());

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

		// }
	}

	/**
	 * Distributes selected NXObjects to specialized methods according to their
	 * type
	 * 
	 * @param theNXObject
	 * @param myBasePart
	 * @throws NXException
	 * @throws RemoteException
	 */
	private void createDirectionUDOs(NXObject[] selectedObjects, String[] selectedPoints, String selectedObjectProperty,
			BasePart myBasePart) throws RemoteException, NXException {

		// theUI.nxmessageBox().show("Info",
		// NXMessageBox.DialogType.INFORMATION, "createUDOs!");

		this.createPointDirectionUDO(selectedObjects, selectedPoints, selectedObjectProperty, myBasePart);

	}

	private void createPointDirectionUDO(NXObject[] selectedObjects, String[] selectedPoints,
			String selectedObjectProperty, BasePart myBasePart) throws RemoteException, NXException {

		////////////////////////////////////////////////////////////////////////

		if (udoselected == 1) {
			// udoselected = 0;

			// ' Prompt user to select a link type to test
			// ' (Owning, Type 1, Type 2, Type 3, or Type 4)

			// ' The user selected a valid point and link type
			// ' go ahead and create the udo

			UserDefinedObject firstUDO = null;

			try {
				firstUDO = myUDOmanager.createUserDefinedObject(myUDOclass);
			} catch (RemoteException | NXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			UserDefinedObject.LinkDefinition[] myLinks = new UserDefinedObject.LinkDefinition[2];

			myLinks[0] = new UserDefinedObject.LinkDefinition();
			myLinks[1] = new UserDefinedObject.LinkDefinition();
			// try {
			// theUI.nxmessageBox().show("Info",
			// NXMessageBox.DialogType.INFORMATION,
			// "myLinks[0] = new UserDefinedObject.LinkDefinition()!");
			// } catch (RemoteException | NXException e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// }

			Edge selectedEdge1 = (Edge) selectedObjects[0];
			Edge selectedEdge2 = (Edge) selectedObjects[1];

			myLinks[0].associatedObject = selectedEdge1;// (NXObject)
			// theUI.nxmessageBox().show("Info",
			// NXMessageBox.DialogType.INFORMATION,
			// "myLinks[0].associatedObject"+
			// myLinks[0].associatedObject.hashCode());

			myLinks[1].associatedObject = selectedEdge2;// (NXObject)
			// theUI.nxmessageBox().show("Info",
			// NXMessageBox.DialogType.INFORMATION,
			// "myLinks[1].associatedObjectHash: "+
			// myLinks[1].associatedObject.hashCode());
			// try {
			// theUI.nxmessageBox().show("Info",
			// NXMessageBox.DialogType.INFORMATION,
			// "//myLinks[0].associatedObject!");
			// } catch (RemoteException | NXException e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// }
			myLinks[0].status = UserDefinedObject.LinkStatus.UP_TO_DATE;
			myLinks[1].status = UserDefinedObject.LinkStatus.UP_TO_DATE;
			// theUI.nxmessageBox().show("Info",
			// NXMessageBox.DialogType.INFORMATION, "//myLinks[0].status!");

			firstUDO.setLineFont(DisplayableObject.ObjectFont.DASHED);
			firstUDO.setLineWidth(DisplayableObject.ObjectWidth.NORMAL);
			firstUDO.setColor(211);
			firstUDO.setLinks(UserDefinedObject.LinkType.TYPE1, myLinks);

			firstUDO.setUserAttribute("UDOtype", -1, "ObjectPropertyPointUDO", nxopen.Update.Option.NOW);
			// Both hash and vertexnumber change from here to display udo.
			// identification via geometric data necessary

			firstUDO.setUserAttribute("Subject", -1, selectedPoints[0], nxopen.Update.Option.NOW);

			// theUI.nxmessageBox().show("Info",
			// NXMessageBox.DialogType.INFORMATION,
			// "//DirectionUDO_SubjectHash: " + selectedPoints[0].hashCode());

			firstUDO.setUserAttribute("Predicat", -1, selectedObjectProperty, nxopen.Update.Option.NOW);

			// Both hash and vertexnumber change from here to display udo.
			// identification via geometric data necessary
			firstUDO.setUserAttribute("Object", -1, selectedPoints[1], nxopen.Update.Option.NOW);

			// theUI.nxmessageBox().show("Info",
			// NXMessageBox.DialogType.INFORMATION,
			// "//DirectionUDO_ObjectHash: " + selectedPoints[1].hashCode());

			// theUI.nxmessageBox().show("Info",
			// NXMessageBox.DialogType.INFORMATION, "//LinkType 1
			// selected!");

			// ' store the integer selected by the user with the udo
			// ' this integer will indicate the link type we are testing
			// ' for this given udo
			int[] myUDOints = new int[1];

			myUDOints[0] = 1; // Type1 udoLink
			// theUI.nxmessageBox().show("Info",
			// NXMessageBox.DialogType.INFORMATION, "//returnval =
			// "+returnval);
			firstUDO.setIntegers(myUDOints);
			// ' add the udo to the display list manually
			// ' this will force the udo to display immediately
			// theUI.nxmessageBox().show("Info",
			// NXMessageBox.DialogType.INFORMATION, "Try to add UDO to
			// Display!");

			theUFSession.disp().addItemToDisplay(firstUDO.tag());

			// theUI.nxmessageBox().show("Info",
			// NXMessageBox.DialogType.INFORMATION,
			// "added ObjectPropertyPointUDO to Display successfully!");

			// theUI.nxmessageBox().show("Info",
			// NXMessageBox.DialogType.INFORMATION,
			// "Main finished without exception!");

		}

	}

	public NXObject[] selectNormalDistributedObjects() throws RemoteException, NXException {
		Selection.MaskTriple mask[] = { new Selection.MaskTriple(),
				new Selection.MaskTriple(nxopen.uf.UFConstants.UF_point_type, 0, 0),
				new Selection.MaskTriple(nxopen.uf.UFConstants.UF_point_type, nxopen.uf.UFConstants.UF_all_subtype, 0),
				new Selection.MaskTriple(nxopen.uf.UFConstants.UF_point_subtype, 0, 0),
				new Selection.MaskTriple(nxopen.uf.UFConstants.UF_point_type, nxopen.uf.UFConstants.UF_point_subtype,
						0),
				new Selection.MaskTriple(nxopen.uf.UFConstants.UF_point_type, nxopen.uf.UFConstants.UF_point_type,
						nxopen.uf.UFConstants.UF_point_type),
				new Selection.MaskTriple(nxopen.uf.UFConstants.UF_solid_type, 0, nxopen.uf.UFConstants.UF_point_type),
				new Selection.MaskTriple(nxopen.uf.UFConstants.UF_solid_type, 0,
						nxopen.uf.UFConstants.UF_point_subtype),
				new Selection.MaskTriple(nxopen.uf.UFConstants.UF_solid_type, UFConstants.UF_UI_SEL_FEATURE_ANY_EDGE,
						nxopen.uf.UFConstants.UF_point_subtype),
				new Selection.MaskTriple(nxopen.uf.UFConstants.UF_solid_type, UFConstants.UF_UI_SEL_FEATURE_ANY_EDGE,
						nxopen.uf.UFConstants.UF_point_type),

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

		Selection selection2 = theUI.selectionManager();

		Selection.SelectTaggedObjectsData selectedNXObjects = selection2.selectTaggedObjects("Info",
				"Select one or more points", Selection.SelectionScope.WORK_PART,
				Selection.SelectionAction.CLEAR_AND_ENABLE_SPECIFIC, false, false, mask);// includeFeatures=false,keepHighlighted=false

		Selection.Response criterium = Selection.Response.OK;

		// theUI.nxmessageBox().show("Info",
		// NXMessageBox.DialogType.INFORMATION,
		// "NormalDistribution_SelectedObject_Hash: " +
		// selectedNXObjects.objectArray[0].hashCode());
		Edge selectedEdge0 = (Edge) selectedNXObjects.objectArray[0];
		// theUI.nxmessageBox().show("Info",
		// NXMessageBox.DialogType.INFORMATION,
		// "NormalDistribution_V1: " +
		// selectedEdge0.getVertices().vertex1.hashCode());
		// theUI.nxmessageBox().show("Info",
		// NXMessageBox.DialogType.INFORMATION,
		// "NormalDistribution_V2: " +
		// selectedEdge0.getVertices().vertex2.hashCode());

		// instantiate Array
		NXObject[] theNXObjects = new NXObject[selectedNXObjects.objectArray.length];

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

				// theUI.nxmessageBox().show("Info",
				// NXMessageBox.DialogType.INFORMATION, "Edge selected!");

				double[] firstline = new double[13];

				// defaultLineValues[i][0] = (double) selectedEdge.hashCode();
				firstline[0] = (double) selectedEdge.hashCode();
				// Start Point Coordinates -> Expectation Values
				// defaultLineValues[i][1] = selectedStartPoint3d.x;
				firstline[1] = selectedStartPoint3d.x;
				// theUI.nxmessageBox().show("Info",
				// NXMessageBox.DialogType.INFORMATION, String.valueOf(eX));
				// defaultLineValues[i][2] = selectedStartPoint3d.y;
				firstline[2] = selectedStartPoint3d.y;
				// theUI.nxmessageBox().show("Info",
				// NXMessageBox.DialogType.INFORMATION, String.valueOf(eY));
				// defaultLineValues[i][3] = selectedStartPoint3d.z;
				firstline[3] = selectedStartPoint3d.z;
				// theUI.nxmessageBox().show("Info",
				// NXMessageBox.DialogType.INFORMATION, String.valueOf(eZ));

				// Default values for the variance of vertex 1
				// defaultLineValues[i][4] = 0.51;
				firstline[4] = 0.51;
				// defaultLineValues[i][5] = 1.01;
				firstline[5] = 1.01;
				// defaultLineValues[i][6] = 1.51;
				firstline[6] = 1.51;
				// End Point Coordinates -> Expectation Values
				// defaultLineValues[i][7] = selectedEndPoint3d.x;
				firstline[7] = selectedEndPoint3d.x;
				// defaultLineValues[i][8] = selectedEndPoint3d.y;
				firstline[8] = selectedEndPoint3d.y;
				// defaultLineValues[i][9] = selectedEndPoint3d.z;
				firstline[9] = selectedEndPoint3d.z;
				// theUI.nxmessageBox().show("Info",
				// NXMessageBox.DialogType.INFORMATION,
				// "selectedEndPointz: " + selectedEndPoint3d.z);
				// Default values for the variance of vertex 2
				// defaultLineValues[i][10] = 0.52;
				firstline[10] = 0.52;
				// defaultLineValues[i][11] = 1.02;
				firstline[11] = 1.02;
				// defaultLineValues[i][12] = 1.52;
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

				// theUI.nxmessageBox().show("Info",
				// NXMessageBox.DialogType.INFORMATION, "Line selected!");

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

				// theUI.nxmessageBox().show("Info",
				// NXMessageBox.DialogType.INFORMATION, "Point selected!");

				double[] firstline = new double[7];

				// defaultPointValues[i][0] = (double)
				// selectedPoint3d.hashCode();
				firstline[0] = (double) selectedPoint3d.hashCode();
				// defaultPointValues[i][1] = selectedPoint3d.x;
				firstline[1] = selectedPoint3d.x;
				// theUI.nxmessageBox().show("Info",
				// NXMessageBox.DialogType.INFORMATION, String.valueOf(eX));
				// defaultPointValues[i][2] = selectedPoint3d.y;
				firstline[2] = selectedPoint3d.y;
				// theUI.nxmessageBox().show("Info",
				// NXMessageBox.DialogType.INFORMATION, String.valueOf(eY));
				// defaultPointValues[i][3] = selectedPoint3d.z;
				firstline[3] = selectedPoint3d.z;
				// theUI.nxmessageBox().show("Info",
				// NXMessageBox.DialogType.INFORMATION, String.valueOf(eZ));

				// Default values for the variance
				// defaultPointValues[i][4] = 0.5;
				firstline[4] = 0.5;
				// defaultPointValues[i][5] = 1.0;
				firstline[5] = 1.0;
				// defaultPointValues[i][6] = 1.5;
				firstline[6] = 1.5;
				// write the point coordinates of the selected points to
				// file. nxopen problems prohibit exchange over rmi or
				// setting the values in the dialog object.
				String filepath = "C:/Users/max/desktop/PELOPS/ArrayExchange.txt";
				Array2File fileDialog = new Array2File();
				// fileDialog.writeArray2File(defaultPointValues, filepath);
				fileDialog.writeArrayToFirstLineInFile(firstline, filepath);

			}

			else {
				theUI.nxmessageBox().show("Info", NXMessageBox.DialogType.INFORMATION,
						"Neighter Point nor Line selected!");
			}
			// udoCollection.createNormalDistributionUDO(theNXObjects[i],
			// myBasePart);
		}
		return theNXObjects;
	}

	public Point createEdgeMidpoint(Edge theEdge, BasePart myBasePart) throws NXException, java.rmi.RemoteException {

		Scalar halfway = myBasePart.scalars().createScalar(50.0, Scalar.DimensionalityType.NONE,
				SmartObject.UpdateOption.WITHIN_MODELING);
		Point point = myBasePart.points().createPoint(theEdge, halfway,
				PointCollection.PointOnCurveLocationOption.PERCENT_ARC_LENGTH,
				SmartObject.UpdateOption.WITHIN_MODELING);

		// theUI.nxmessageBox().show("Info",
		// NXMessageBox.DialogType.INFORMATION, "createEdgeMidpoint!");

		return point;
	}

	public double calculateDistancePoints3d(Point3d Point1, Point3d Point2) {

		double distanceX = Math.pow(Point2.x - Point1.x, 2);
		double distanceY = Math.pow(Point2.y - Point1.y, 2);
		double distanceZ = Math.pow(Point2.z - Point1.z, 2);

		double distance = Math.sqrt(distanceX + distanceY + distanceZ);

		return distance;
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
		// return nxopen.Session.LibraryUnloadOption.AT_TERMINATION;
		return nxopen.Session.LibraryUnloadOption.IMMEDIATELY;
		// return BaseSession.LibraryUnloadOption.EXPLICITLY;
	}

}
