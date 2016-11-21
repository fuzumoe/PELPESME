package com.sfb805.matlab;

import java.io.File;

import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabProxy;
import matlabcontrol.MatlabProxyFactory;
import matlabcontrol.MatlabProxyFactoryOptions;

public class MatlabStarter {

	public MatlabProxyFactory factory = null;
	public MatlabProxy proxy = null;
	String matlabCurrentFolderString = "C:/Users/max/Desktop/PELOPS/";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MatlabStarter firestarter = new MatlabStarter();
		firestarter.factory = firestarter.getInstance(firestarter.matlabCurrentFolderString);
		firestarter.proxy = firestarter.getMatlabProxy(firestarter.factory);

	}

	public MatlabProxyFactory getInstance(String matlabCurrentFolderString) {

		File matlabStartingDirectory = new File(matlabCurrentFolderString);

		MatlabProxyFactoryOptions options = new MatlabProxyFactoryOptions.Builder()
				.setUsePreviouslyControlledSession(true)
				// .setHidden(true)
				.setMatlabLocation(null).setMatlabStartingDirectory(matlabStartingDirectory).build();

		// .setMatlabStartingDirectory(matlabStartingDirectory);

		// MatlabProxyFactory factory = new MatlabProxyFactory();
		MatlabProxyFactory factory = new MatlabProxyFactory(options);

		return factory;
	}

	public MatlabProxy getMatlabProxy(MatlabProxyFactory factory) {

		try {
			proxy = factory.getProxy();
			System.out.println("MatlabProxy uses existing Session:" + proxy.isExistingSession());
		} catch (MatlabConnectionException e) {
			System.out.println("Matlab Proxy could not be created");
			e.printStackTrace();
		}
		return proxy;
	}

}
