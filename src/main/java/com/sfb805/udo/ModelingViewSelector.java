package com.sfb805.udo;

import java.rmi.Naming;
import java.rmi.RemoteException;

import nxopen.Line;
import nxopen.NXException;
import nxopen.Point;
import nxopen.Point3d;
import nxopen.Session;
import nxopen.Session.MarkVisibility;
import nxopen.UFSession;
import nxopen.UI;
import nxopen.Vector3d;
import nxopen.XYZAxis;

public class ModelingViewSelector {

	private static UI ui;
	private static UFSession ufsession;
	private static Session sess;
	static Boolean WinRTE = true;

	public void connectToNx() {
		try {
			sess = (Session) Naming.lookup("//127.0.0.1/NXSession");
			ui = (UI) Naming.lookup("//127.0.0.1/NXgui");
			ufsession = (UFSession) Naming.lookup("//127.0.0.1/NXUFSession");
			// NXRemotableObject.RemotingProtocol remotingProtocol =
			// NXRemotableObject.RemotingProtocol.create();
			// sess = (Session)SessionFactory.get("NXSession",
			// remotingProtocol);
			// ui = (UI)SessionFactory.get("UI", remotingProtocol);

			// System.out.println(ui.selectionManager().getNumSelectedObjects());
			WinRTE = false;

		} catch (Exception e) {

		}
	}

	public Vector3d getModelingViewNormalVector(Session theSession) throws RemoteException, NXException {
		Vector3d normalvector = theSession.parts().work().modelingViews().workView().getAxis(XYZAxis.ZAXIS);
		return normalvector;
	}

	public Line calculateModelingViewNormalVectorLine(Session theSession, Vector3d normalvector, Point3d pickedPoint,
			int LineLength) throws RemoteException, NXException {

		System.out.println("Normalvector: " + normalvector.x + "/" + normalvector.y + "/" + normalvector.z);

		Point3d coordinates1 = new Point3d(pickedPoint.x - normalvector.x * LineLength / 2,
				pickedPoint.y - normalvector.y * LineLength / 2, pickedPoint.z - normalvector.z * LineLength / 2);

		Point3d translatedCoordinates2 = new Point3d(pickedPoint.x + normalvector.x * LineLength / 2,
				pickedPoint.y + normalvector.y * LineLength / 2, pickedPoint.z + normalvector.z * LineLength / 2);

		System.out.println("translatedCoordinates2: " + translatedCoordinates2.x + "/" + translatedCoordinates2.y + "/"
				+ translatedCoordinates2.z);

		Point point1 = theSession.parts().work().points().createPoint(coordinates1);
		Point point2 = theSession.parts().work().points().createPoint(translatedCoordinates2);

		System.out.println(
				"Point2: " + point2.coordinates().x + "/" + point2.coordinates().y + "/" + point2.coordinates().z);

		Line lineOfFire = theSession.parts().work().curves().createLine(point1, point2);

		Point pick = theSession.parts().work().points().createPoint(pickedPoint);
		pick.setVisibility(nxopen.SmartObject.VisibilityOption.INVISIBLE);
		lineOfFire.setVisibility(nxopen.SmartObject.VisibilityOption.INVISIBLE);

		int markId1 = theSession.setUndoMark(MarkVisibility.VISIBLE, "Normalvector");
		theSession.setUndoMarkName(markId1, "NormalvectorName2");

		// theSession.measurement().getMinimumDistance(arg0, arg1);

		return lineOfFire;
	}

	public static void main(String args[]) throws RemoteException, NXException {
		ModelingViewSelector viewSelector = new ModelingViewSelector();
		viewSelector.connectToNx();
		viewSelector.calculateModelingViewNormalVectorLine(sess, viewSelector.getModelingViewNormalVector(sess),
				new Point3d(30, 30, 30), 1000000);

	}
}
