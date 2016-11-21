package com.sfb805.udo;

import java.rmi.RemoteException;

import javax.swing.JOptionPane;

import nxopen.BasePart;
import nxopen.Edge;
import nxopen.Edge.VerticesData;
import nxopen.Line;
import nxopen.NXException;
import nxopen.NXMessageBox;
import nxopen.NXObject;
import nxopen.Plane;
import nxopen.Point;
import nxopen.Point3d;
import nxopen.Selection;
import nxopen.Selection.MaskTriple;
import nxopen.Sense;
import nxopen.Session;
import nxopen.UI;
import nxopen.Vector3d;
import nxopen.uf.UFConstants;

public class NXObjectSelector {

	private UI theUI = null;
	private int numberOfObjectsLinkableToUDO;
	private NXObject[] udoLinkableParentObjects;
	private String[] selectedPoints;
	private Selection.SelectTaggedObjectData[] selectedNXObjects;
	// private Point3d[] RealcursorLocation;
	private Point[] RealcursorLocation;
	private String selectedPropertyName = null;
	private String selectedClassName = null;

	public NXObjectSelector(BasePart myBasePart, UI theUI, Session theSession, int NumberOfPointsToBeSelected) {
		try {

			// String filePath1
			// ="file:///c:/users/max/desktop/PELOPS/10303_108_Test.owl";
			// OWLOntologyManager manager =
			// OWLManager.createOWLOntologyManager();
			// OWLOntology ontology1 =
			// manager.loadOntologyFromOntologyDocument(IRI.create(filePath1));
			// ObjectPropertyManipulator myTestManipulator = new
			// ObjectPropertyManipulator();
			// //Get all ObjectProperties of the Ontology
			// OWLObjectProperty[] allObjectProperties =
			// myTestManipulator.getAllOWLObjectProperties(ontology1, manager);
			//
			// frame = new Dialog();
			// //frame.getController().setNxObjectSelector(/*this*/);
			// frame.setJCamboBoxModel(frame.comboBox_2,
			// (Object[])myTestManipulator.makeShortForm(ontology1,
			// allObjectProperties));
			// frame.setResizable(false);

			this.setTheUI(theUI);
			this.setNumberOfObjectsToBeSelected(NumberOfPointsToBeSelected);
			// this.RealcursorLocation = new
			// Point3d[this.numberOfObjectsLinkableToUDO];
			this.RealcursorLocation = new Point[this.numberOfObjectsLinkableToUDO];
			// hide cursor pick click point
			// Point point =
			// theSession.parts().work().points().createPoint(this.RealcursorLocation[0]);
			// point.setVisibility(nxopen.SmartObject.VisibilityOption.INVISIBLE);

			this.selectedNXObjects = new Selection.SelectTaggedObjectData[this.numberOfObjectsLinkableToUDO];
			this.selectedPoints = new String[this.numberOfObjectsLinkableToUDO];
			this.udoLinkableParentObjects = new NXObject[this.numberOfObjectsLinkableToUDO];

			MaskTriple[] mask = this.setSelectionMask(myBasePart, theSession);

			for (int i = 0; i < NumberOfPointsToBeSelected; i++) {

				this.selectObjects(mask, theSession, i);

			}

			this.setUdoLinkableParentObjects();
			this.setSelectedPoints();

		} catch (RemoteException | NXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setSelectedPropertyName(String selectedPropertyName) {
		this.selectedPropertyName = selectedPropertyName;
	}

	public void setTheUI(UI ui) {
		theUI = ui;
	}

	public String[] getSelectedPoints() {
		return selectedPoints;
	}

	public NXObject[] getLinkableParentObjects() {
		return udoLinkableParentObjects;
	}

	public void setNumberOfObjectsToBeSelected(int number) {
		numberOfObjectsLinkableToUDO = number;
	}

	private Point calculateCursorLocation(Selection.SelectTaggedObjectData selectedNXObjects, Session theSession)
			throws RemoteException, NXException {

		ModelingViewSelector viewSelector = new ModelingViewSelector();
		Point nullPoint = null;

		Line lineOfFire = viewSelector.calculateModelingViewNormalVectorLine(theSession,
				viewSelector.getModelingViewNormalVector(theSession), selectedNXObjects.cursor, 1000000);
		lineOfFire.setVisibility(nxopen.SmartObject.VisibilityOption.INVISIBLE);
		NXObject selectedObject = (NXObject) selectedNXObjects.object;

		if (selectedObject instanceof Edge) {

			Edge selectedEdge = (Edge) selectedObject;

			// create Midpoint for Plane construction:
			Point3d vertex1 = selectedEdge.getVertices().vertex1;
			Point3d vertex2 = selectedEdge.getVertices().vertex2;
			Point3d midpoint = new Point3d((vertex1.x + vertex2.x) / 2, (vertex1.y + vertex2.y) / 2,
					(vertex1.z + vertex2.z) / 2);

			// create normalVector through Edge for Plane construction:
			Vector3d normalVectorToEdge = theSession.parts().work().directions()
					.createDirection(selectedEdge.getFaces()[0], Sense.FORWARD,
							nxopen.SmartObject.UpdateOption.WITHIN_MODELING)
					.vector();

			// Plane auxiliaryFace =
			// theSession.parts().work().planes().createPlane(new
			// Point3d(1,2,3), notNormalVector,
			// nxopen.SmartObject.UpdateOption.WITHIN_MODELING);
			Plane auxiliaryFace = theSession.parts().work().planes().createPlane(midpoint, normalVectorToEdge,
					nxopen.SmartObject.UpdateOption.WITHIN_MODELING);
			// calculate the cursor as intersection of view axis and edge / face
			// build up from edge and another vector
			// auxiliaryFace.setVisibility(nxopen.SmartObject.VisibilityOption.VISIBLE);
			auxiliaryFace.setVisibility(nxopen.SmartObject.VisibilityOption.INVISIBLE);
			Point intersectionPoint = theSession.parts().work().points().createPoint(auxiliaryFace, lineOfFire,
					nullPoint, nullPoint, nxopen.SmartObject.UpdateOption.WITHIN_MODELING);
			intersectionPoint.setVisibility(nxopen.SmartObject.VisibilityOption.INVISIBLE);
			return intersectionPoint;
		} else {
			return theSession.parts().work().points().createPoint(selectedNXObjects.cursor);
		}

	}

	@SuppressWarnings("deprecation")
	public MaskTriple[] setSelectionMask(BasePart myBasePart, Session theSession) throws RemoteException, NXException {
		// Selection.MaskTriple( Type, Subtype, SolidBodySubtype)
		// http://www.nxjournaling.com/content/using-mask-triples-selectobject-routine
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

		return mask;

	}

	public void selectObjects(MaskTriple[] mask, Session theSession, int numberOfPoint) {

		try {

			// first Vertex
			Selection selection1a = theUI.selectionManager();
			// instantiate Array for casted NXObject, e.g. Edge since Vertexes
			// can´t be linked to UDOs
			selectedNXObjects[numberOfPoint] = selection1a.selectTaggedObject("Info", "Select Vertex 1 on Edge",
					Selection.SelectionScope.WORK_PART_AND_OCCURRENCE,
					Selection.SelectionAction.CLEAR_AND_ENABLE_SPECIFIC, false, false, mask);

			// selectedNXObjects[numberOfPoint] =
			// selection1a.selectObject("Info", "Select Vertex 1 on Edge",
			// Selection.SelectionScope.WORK_PART,
			// Selection.SelectionAction.CLEAR_AND_ENABLE_SPECIFIC, false,
			// false, mask);

			// theUI.nxmessageBox().show("Info",
			// NXMessageBox.DialogType.INFORMATION,
			// "SelectedObject_Hash: " +
			// selectedNXObjects[numberOfPoint].object.hashCode());
			Edge selectedEdge0 = (Edge) selectedNXObjects[numberOfPoint].object;

			// theUI.nxmessageBox().show("Info",
			// NXMessageBox.DialogType.INFORMATION,
			// "SelectedObject_V1: " + selectedEdge0.getVertices().vertex1.x +
			// "/"
			// + selectedEdge0.getVertices().vertex1.y + "/" +
			// selectedEdge0.getVertices().vertex1.z);
			// theUI.nxmessageBox().show("Info",
			// NXMessageBox.DialogType.INFORMATION,
			// "SelectedObject_V1: " + selectedEdge0.getVertices().vertex1.x +
			// "/"
			// + selectedEdge0.getVertices().vertex1.y + "/" +
			// selectedEdge0.getVertices().vertex1.z);
			// theUI.nxmessageBox().show("Info",
			// NXMessageBox.DialogType.INFORMATION,
			// "SelectedObject_V2_Hash: " +
			// selectedEdge0.getVertices().vertex2.hashCode());

			RealcursorLocation[numberOfPoint] = this.calculateCursorLocation(selectedNXObjects[numberOfPoint],
					theSession);

			// //Property
			// this.setObjectProperty();
			// this.setSelectedPropertyName(this.frame.getController().getSelectedValue());
			// JOptionPane.showMessageDialog(null, this.getObjectProperty());
			//
			// theUI.nxmessageBox().show("Info",
			// NXMessageBox.DialogType.INFORMATION,"Name of selected Property:
			// "+this.getObjectProperty());
			//

			// theUI.nxmessageBox().show("Info",
			// NXMessageBox.DialogType.INFORMATION,
			// "CursorLocation: " + RealcursorLocation[1].x + "/" +
			// RealcursorLocation[1].y + "/" + RealcursorLocation[1].z);
			//
			// theUI.nxmessageBox().show("Info",
			// NXMessageBox.DialogType.INFORMATION,"Edge2Hash:
			// "+selectedNXObjects[1].hashCode());

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// public void setSelectedClassName(String selectedClassName) {
	//
	// try {
	// this.selectedClassName = selectedClassName;
	//
	// } catch (Exception e) {
	// JOptionPane.showMessageDialog(null, e);
	// }
	//
	// }
	//
	// public String getSelectedClassName() {
	// return this.selectedClassName;
	// }

	public void setObjectProperty(String selectedPropertyName) {

		try {

			this.selectedPropertyName = selectedPropertyName;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public String getObjectProperty() {
		return selectedPropertyName;
	}

	private void setUdoLinkableParentObjects() throws RemoteException, NXException {

		Selection.Response criterium = Selection.Response.OK;

		for (int i = 0; i < selectedNXObjects.length; i++) {

			// if (selectedNXObjects[i].response == criterium) {

			// put the selected objects into array
			udoLinkableParentObjects[i] = (NXObject) selectedNXObjects[i].object;

			// theUI.nxmessageBox().show("Info",
			// NXMessageBox.DialogType.INFORMATION,"Cast from SelectObjectData
			// to NXObject");

			// } else {

			// Not Ok...no problem
			// }

		}
	}

	private void setSelectedPoints() throws RemoteException, NXException {

		for (int i = 0; i < udoLinkableParentObjects.length; i++) {

			if (udoLinkableParentObjects[i] instanceof Edge) {
				Edge selectedEdge = (Edge) udoLinkableParentObjects[i];
				VerticesData selectedVerteces = selectedEdge.getVertices();

				// Select the Vertex which is closest to the cusor click
				// Point EdgeMidPoint = this.createEdgeMidpoint(selectedEdge,
				// myBasePart);

				Point3d selectedStartPoint3d = (Point3d) selectedVerteces.vertex1;
				Point3d selectedEndPoint3d = (Point3d) selectedVerteces.vertex2;

				double distance2StartPoint = this.calculateDistancePoints3d(RealcursorLocation[i].coordinates(),
						selectedStartPoint3d);
				double distance2EndPoint = this.calculateDistancePoints3d(RealcursorLocation[i].coordinates(),
						selectedEndPoint3d);

				if (distance2StartPoint <= distance2EndPoint) {

					// selectedPoints[i]= selectedStartPoint3d;
					selectedPoints[i] = new String("V1");

					// theUI.nxmessageBox().show("Info",
					// NXMessageBox.DialogType.INFORMATION,
					// distance2StartPoint + "<=" + distance2EndPoint);
				}

				else if (distance2StartPoint > distance2EndPoint) {

					// selectedPoints[i]= selectedEndPoint3d;

					selectedPoints[i] = new String("V2");

					// theUI.nxmessageBox().show("Info",
					// NXMessageBox.DialogType.INFORMATION,
					// distance2StartPoint + ">" + distance2EndPoint);
				}

				else {
					theUI.nxmessageBox().show("Info", NXMessageBox.DialogType.INFORMATION,
							"Something went wrong when comparing the distance between 2 points");
				}
				// theUI.nxmessageBox().show("Info",
				// NXMessageBox.DialogType.INFORMATION,
				// "NXObjectSelector.selectedPoints[i].hashCode():
				// "+selectedPoints[i]);

			}

			else if (udoLinkableParentObjects[i] instanceof Line) {
				Line selectedLine = (Line) udoLinkableParentObjects[i];

				theUI.nxmessageBox().show("Info", NXMessageBox.DialogType.INFORMATION,
						"Line selected. Method needs to be implemented!");

			}

			else {
				theUI.nxmessageBox().show("Info", NXMessageBox.DialogType.INFORMATION,
						"Neighter Edge nor Line selected!");
			}

		}

		// theUI.nxmessageBox().show("Info",
		// NXMessageBox.DialogType.INFORMATION, "Check
		// C:/Users/max/desktop/PELOPS/ArrayExchange.txt!");

	}

	public double calculateDistancePoints3d(Point3d Point1, Point3d Point2) {

		double distanceX = Math.pow(Point2.x - Point1.x, 2);
		double distanceY = Math.pow(Point2.y - Point1.y, 2);
		double distanceZ = Math.pow(Point2.z - Point1.z, 2);

		double distance = Math.sqrt(distanceX + distanceY + distanceZ);

		return distance;
	}

}
