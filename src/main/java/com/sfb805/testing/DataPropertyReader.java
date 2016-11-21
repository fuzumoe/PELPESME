package com.sfb805.testing;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

import com.sfb805.gui2owl2gui.ClassManipulator;

public class DataPropertyReader {

	public static void main(String[] args) {

		DataPropertyReader test = new DataPropertyReader();

		test.test();

	}

	public void test() {
		String home = System.getProperty("user.home").replace("\\", "/");
		String selectedObjectPropertyShortForm = "#dummyProperty";
		String selectedClassShortForm = "#representation_context";
		// select points from class of interest
		Object[][] pointsInClassOfInterest = null;

		OWLOntology ontology = null;
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLDataFactory factory = manager.getOWLDataFactory();

		DLquery myQuery = new DLquery();
		try {
			ontology = myQuery.manager.loadOntologyFromOntologyDocument(
					IRI.create("file:///" + home + "/Desktop/PELOPS/10303_108_Populated.owl"));
		} catch (OWLOntologyCreationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		IRI ontology_iri = (IRI) ontology.getOntologyID().getOntologyIRI().get();
		PrefixManager pm = new DefaultPrefixManager(null, null, ontology_iri.toString());
		OWLNamedIndividual individual = null;
		// Set setOfIndividuals =
		// myQuery.getIndividualsOfClassByName(selectedClassShortForm,
		// ontology);
		// if (setOfIndividuals.isEmpty()) {
		// System.out.println("Query returns no result.");
		// } else {
		// Object[][] point = new Object[setOfIndividuals.size()][9];
		System.out.println("Query has answer: ");
		// for (Iterator iterator = setOfIndividuals.iterator();
		// iterator.hasNext();) {
		// individual = (OWLNamedIndividual) iterator.next();
		// System.out.println("" + individual.getIRI());
		individual = factory.getOWLNamedIndividual("#Point3d_950329871_V1", pm);

		// change the point into a class and add it to the ontology
		// get the equivalent class of points that influence the
		// individuals in
		// the class of the selected point

		OWLDataPropertyExpression hasNXhashCode = factory.getOWLDataProperty("#hasNXhashCode", pm);
		OWLLiteral nxHash = myQuery.getDataPropertyValue(individual, hasNXhashCode, ontology);

		ClassManipulator classManipulator = new ClassManipulator();
		String PunningClassName = classManipulator.convertPointIndividuumToClass(ontology, myQuery.manager,
				nxHash.getLiteral());
		// add the point to its own class
		classManipulator.setClassAxiomToPoint(ontology, myQuery.manager, PunningClassName, PunningClassName);
		System.out.println("selectedClassShortForm: " + PunningClassName);

		// }

	}
}
// }
