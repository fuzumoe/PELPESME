package com.sfb805.main;

import java.io.File;
import java.rmi.RemoteException;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import com.sfb805.nx.NXRemoteGrabber;
import com.sfb805.nx2owl2nx.NXpointExtractor;
import com.sfb805.nx2owl2nx.NXpointWriter;
import com.sfb805.nx2owl2nx.OWLpointWriter;
import com.sfb805.owl2matlab2owl.MATLABpointWriter;
import com.sfb805.sparql.SPARQLderivedPointExtractor;

import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;
import matlabcontrol.MatlabProxy;
import matlabcontrol.MatlabProxyFactory;
import matlabcontrol.MatlabProxyFactoryOptions;
import nxopen.NXException;

public class Main {

	// static MatlabProxyFactory factory=null;
	String home = System.getProperty("user.home").replace("\\", "/");
	public String filePath1 = "file:///" + home + "/Desktop/PELOPS/10303_108.owl";
	public String filePath2 = home + "/Desktop/PELOPS/10303_108_Populated.owl";
	String filePath3 = home + "/Desktop/PELOPS/10303_108_Populated_Derived.owl";
	String matlabCurrentFolderString = home + "/Desktop/PELOPS/";
	public File file2 = new File(this.filePath2);
	File file3 = new File(this.filePath3);
	public OWLOntologyManager manager;
	public OWLOntology ontologyT = null;
	public OWLOntology ontologyAT = null;
	public NXRemoteGrabber nxRemoteGrabber = new NXRemoteGrabber();
	public MatlabProxyFactory factory = null;
	public MatlabProxy proxy = null;
	public Object[][][] derivedCurrentPointRep;
	public Object[][] owl2matlab_matrix;

	public Main() {
		try {
			System.out.print("Trying to create OWLManager ");
			manager = OWLManager.createOWLOntologyManager();
			System.out.print("OWLManager created sucessfully.");

			System.out.print("Trying to load " + filePath1 + "...");
			ontologyT = manager.loadOntologyFromOntologyDocument(IRI.create(filePath1));
			System.out.print("File " + filePath1 + "loaded sucessfully.");

		} catch (OWLOntologyCreationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	// public Main(SPARQLudo sparqludo) {
	//
	// this.sparqludo = sparqludo;
	// }

	public static void main(String[] args)
			throws OWLOntologyStorageException, OWLOntologyCreationException, RemoteException, NXException {

		@SuppressWarnings("unused")
		Main main = new Main();

		// Button2
		// main.nx2owl();
		// Button3
		// SPARQLudo sparqludo = new SPARQLudo();
		// main.owl2matlab_matrix = sparqludo.run(new String("file:" +
		// main.filePath2));
		// main.owl2matlab();
		// Button4
		// main.matlab2owl();
		// Button5
		// main.owl2nx();
		System.out.println("Happy!");
	}

	// public static MatlabProxyFactory getInstance(){
	// if(factory == null){
	// factory = new MatlabProxyFactory();
	// }
	// return factory;
	// }

	public void nx2owl() throws OWLOntologyStorageException {
		// Button2

		OWLOntologyID id = ontologyT.getOntologyID();
		System.out.println("Loaded Ontology has ID:  " + id);

		// Write NX-Points into OWL Ontology
		NXpointExtractor.PartIterator(nxRemoteGrabber, ontologyT, manager, file2);

		System.out.print("Trying to save " + file2 + "...");
		manager.saveOntology(ontologyT, new RDFXMLDocumentFormat(), IRI.create(file2.toURI()));
		System.out.print("File " + file2 + " saved successfully.");
		// Button2_End

	}

	public void owl2matlab()
			throws OWLOntologyCreationException, RemoteException, NXException, OWLOntologyStorageException {
		// Button3

		// Button3a

		// Button3b
		// DLquery dlquery = new DLquery();
		//
		// // DL-Query: which Point has Influence on the selected Point?
		// int NumberOfPointsToBeSelected = 1;
		// NXObjectSelector nxObjectSelector = new
		// NXObjectSelector(nxRemoteGrabber.sess.parts().baseDisplay(),
		// nxRemoteGrabber.ui, nxRemoteGrabber.sess,
		// NumberOfPointsToBeSelected);
		//
		// // Identify Vertexes closest to the click pick points (V1 or V2)
		// String[] selectedPoints = nxObjectSelector.getSelectedPoints();
		// NXObject[] selectedObjects =
		// nxObjectSelector.getLinkableParentObjects();
		OWLOntologyManager manager1 = OWLManager.createOWLOntologyManager();
		// String nameOfClassToBeQueried = null;
		// ClassManipulator classmanipulator = new ClassManipulator();
		//
		try {
			ontologyAT = manager1.loadOntologyFromOntologyDocument(IRI.create(file2));
		} catch (OWLOntologyCreationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//
		// for (int i = 0; i < NumberOfPointsToBeSelected; i++) {
		// String selectedObjectHash =
		// Integer.toString(selectedObjects[i].hashCode());
		// String selectedPointIdentifier = selectedPoints[i];
		// String pointHash = selectedObjectHash + "_" +
		// selectedPointIdentifier;
		//
		// nameOfClassToBeQueried = "#Point3d_" + pointHash;
		//
		// classmanipulator.setClassAxiomToPoint(ontologyAT, manager,
		// nameOfClassToBeQueried, pointHash);
		// }
		//
		// System.out.print("Trying to load " + file2 + "...");
		//
		// classmanipulator.createEquivalentClassOfInfluencingIndividuals(ontologyAT,
		// manager1, nameOfClassToBeQueried);
		//
		// manager.saveOntology(ontologyAT, new RDFXMLDocumentFormat(),
		// IRI.create(file2.toURI()));
		//
		// owl2matlab_matrix = dlquery.queryPointsInClassOfInterest(ontologyT,
		// manager1, nameOfClassToBeQueried);

		// generic variational point representation
		derivedCurrentPointRep = new Object[owl2matlab_matrix.length][][];

		// Matlab Session Provider: Current Matlab Session is not registered in
		// registry-registry as usually by RMI
		// Thus, a new matlab session is started everytime
		// Create a proxy, which we will use to control MATLAB
		factory = this.getInstance(matlabCurrentFolderString);
		proxy = this.getMatlabProxy(factory);
		MATLABpointWriter matlabpointwriter = new MATLABpointWriter();

		// owl2matlab_matrix[][0] = IRI of the current point representation aka
		// parent
		// point
		// owl2matlab_matrix[][1] = NX-Hash of the current point representation
		// aka parent
		// point
		// owl2matlab_matrix[][2] = X-coordinate of the derived current
		// representation
		// owl2matlab_matrix[][3] = Y-coordinate of the derived current
		// representation
		// owl2matlab_matrix[][4] = Z-coordinate of the derived current
		// representation
		// owl2matlab_matrix[][5] = variance_x;
		// owl2matlab_matrix[][6] = varinace_y;
		// owl2matlab_matrix[][7] = variance_z;
		// owl2matlab_matrix[][8] = sampleNumber;
		// owl2matlab_matrix[j][] = point sample of the derived current
		// representation
		System.out.println("derivedCurrentPointRep.length(): " + derivedCurrentPointRep.length);
		for (int i = 0; i < owl2matlab_matrix.length; i++) { // i is the current
																// point

			try {
				derivedCurrentPointRep[i] = new Object[(int) owl2matlab_matrix[i][8]][owl2matlab_matrix[i].length];
				// (MatlabProxy proxy, String parentIRI, String parentHash,
				// double expectationX, double expectationY, double
				// expectationZ, double varianceX, double varianceY, double
				// varianceZ, int numberSamples)
				// index
				System.out.println("derivedCurrentPointRep[" + i + "].length(): " + derivedCurrentPointRep[i].length);
				System.out.println(this.getClass().getName() + ".matrix: " + owl2matlab_matrix[i][0] + "/"
						+ owl2matlab_matrix[i][1] + "/" + owl2matlab_matrix[i][2] + "/" + owl2matlab_matrix[i][3] + "/"
						+ owl2matlab_matrix[i][4] + "/" + owl2matlab_matrix[i][5] + "/" + owl2matlab_matrix[i][6] + "/"
						+ owl2matlab_matrix[i][7] + "/" + owl2matlab_matrix[i][8]);

				derivedCurrentPointRep[i] = matlabpointwriter.writer(proxy, (String) owl2matlab_matrix[i][0],
						(String) owl2matlab_matrix[i][1], (double) owl2matlab_matrix[i][2],
						(double) owl2matlab_matrix[i][3], (double) owl2matlab_matrix[i][4],
						(double) owl2matlab_matrix[i][5], (double) owl2matlab_matrix[i][6],
						(double) owl2matlab_matrix[i][7], (int) owl2matlab_matrix[i][8]);

				System.out.println("derivedCurrentPointRep: ");
				for (int j = 0; j < derivedCurrentPointRep[i].length; j++) { // j
					// is the derived current point index
					// IRI+Hash+X+Y+Z+SampleNumber = 6
					System.out.println("derivedCurrentPointRep[" + i + "][" + j + "].length(): "
							+ derivedCurrentPointRep[i][j].length);
					for (int k = 0; k < 6; k++) {

						System.out.println("i: " + i + " j: " + j + " k:" + k);
						if (derivedCurrentPointRep[i][j][k] == null) {
							System.out.println("Query returns no result.");
						} else {
							System.out.println(
									"[" + i + "]" + "[" + j + "]" + "[" + k + "]:" + derivedCurrentPointRep[i][j][k]);
						}
					}
				}

			} catch (MatlabConnectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MatlabInvocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//
		}
		// Disconnect the proxy from MATLAB
		proxy.disconnect();
		// Button3_End
	}

	public void matlab2owl() throws OWLOntologyStorageException {

		// Button4
		for (int i = 0; i < derivedCurrentPointRep.length; i++) {
			// For each point: Write the simulation results, which are a generic
			// derivative of the current point into the derived current point
			// representation of the points into the ontology
			try {
				OWLpointWriter.writeDerivedCurrentPoint(derivedCurrentPointRep[i], ontologyAT, manager);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		// Save ontology after adding the axioms for all points

		System.out.print("Trying to save " + file3 + "...");

		// RDFXMLOntologyFormat rdfxmlFormat = new RDFXMLOntologyFormat();
		manager.saveOntology(ontologyAT, new RDFXMLDocumentFormat(), IRI.create(file3.toURI()));
		// Button4_End
		System.out.println("File saved successfully.");
	}

	@SuppressWarnings("static-access")
	public void owl2nx() {

		// Button5
		// Extract derived points from ontology and give object array back
		// OWLderivedPointExtractor owlderivedpointextractor = new
		// OWLderivedPointExtractor();
		// Object[][] derivedPointsFromOntology =
		// owlderivedpointextractor.extract("file:///c:/users/max/desktop/PELOPS/10303_108_Populated_Derived.owl");
		SPARQLderivedPointExtractor sparqlderivedpointextractor = new SPARQLderivedPointExtractor();

		System.out.println("file:" + filePath3);

		Object[][] derivedPointsFromOntology = sparqlderivedpointextractor.extract("file:" + filePath3);

		// Write derived points back into the running nx-session
		NXpointWriter nxpointwriter = new NXpointWriter();
		try {
			nxpointwriter.write(derivedPointsFromOntology, nxRemoteGrabber.sess);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Button5_End

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
