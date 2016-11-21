package com.sfb805.testing;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import nxopen.Matrix3x3;
import nxopen.NXException;
import nxopen.Part;
import nxopen.Point3d;
import nxopen.Session;
import nxopen.assemblies.Component;

public class AssemblyIterator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Session sess = null;
		try {
			sess = (Session) Naming.lookup("//127.0.0.1/NXSession");
		} catch (MalformedURLException | RemoteException | NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			Part part = sess.parts().work();
			// ComponentAssembly.RealQuantityData realQuantityData =
			// part.componentAssembly().getRealQuantity(arg0);
			Component root = part.componentAssembly().rootComponent();
			Component[] children = root.getChildren();
			for (int i = 0; i < children.length; i++) {
				Component.PositionData positionData = children[i].getPosition();
				Point3d position = positionData.position;
				Matrix3x3 orientation = positionData.orientation;
				System.out.println("child[" + i + "]");
				System.out.println("position: " + position.x + "/" + position.y + "/" + position.z);
				System.out.println("Matrix3x3:");
				System.out.println(orientation.xx + "|" + orientation.xy + "|" + orientation.xz);
				System.out.println(orientation.yx + "|" + orientation.yy + "|" + orientation.yz);
				System.out.println(orientation.zx + "|" + orientation.zy + "|" + orientation.zz);
			}

		} catch (RemoteException | NXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
