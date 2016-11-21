package com.sfb805.nx;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.sfb805.udo.UDOCollection;

import nxopen.Session;
import nxopen.UI;

public class NXRemoteGrabber {

	public static UI ui;
	public static Session sess;
	static Boolean WinRTE = true;

	public NXRemoteGrabber() {

		NXRemoteGrabber.setRemoteObjects();

	}

	public static void setTheSession() {

		sess = UDOCollection.theSession;
		// try {
		// sess = (Session) Naming.lookup("//127.0.0.1/NXSession");
		// } catch (MalformedURLException | RemoteException | NotBoundException
		// e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		WinRTE = false;

	}

	public static void setTheUI() {
		try {
			ui = (UI) Naming.lookup("//127.0.0.1/NXgui");
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void setRemoteObjects() {

		NXRemoteGrabber.setTheSession();
		NXRemoteGrabber.setTheUI();
	}
}
