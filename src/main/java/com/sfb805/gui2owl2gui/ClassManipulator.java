package com.sfb805.gui2owl2gui;

import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

public class ClassManipulator {

	public OWLClass[] getAllOWLClasses(OWLOntology ontologyToBeQueried, OWLOntologyManager manager) {

		// Casting
		Set<OWLClass> classSet = ontologyToBeQueried.getClassesInSignature();
		Object[] allClasses = classSet.toArray();
		OWLClass[] classArray = new OWLClass[allClasses.length];

		for (int i = 0; i < allClasses.length; i++) {
			classArray[i] = (OWLClass) allClasses[i];
			// System.out.println(objectPropertyArray[i].getIRI());
		}

		return classArray;

	}

	public String[] makeShortForm(OWLOntology ontology, OWLClass[] owlClasses) {
		String[] owlClassesShortForm = new String[owlClasses.length];

		IRI iri = ontology.getOntologyID().getOntologyIRI().get();
		System.out.println(iri.toString());
		DefaultPrefixManager pm = new DefaultPrefixManager(null, null, iri.toString() + "#");

		for (int i = 0; i < owlClasses.length; i++) {
			owlClassesShortForm[i] = pm.getShortForm(owlClasses[i]).substring(1);
			System.out.println("owlClassShortForm[" + i + "] " + owlClassesShortForm[i]);
		}

		return owlClassesShortForm;
	}

	public String convertPointIndividuumToClass(OWLOntology ontology, OWLOntologyManager manager, String point_hash) {

		OWLDataFactory dataFactory = manager.getOWLDataFactory();

		IRI iri = ontology.getOntologyID().getOntologyIRI().get();
		DefaultPrefixManager pm = new DefaultPrefixManager(null, null, iri.toString());

		String selectedClassShortForm = new String("#Point3d_" + point_hash);

		return selectedClassShortForm;

	}

	public void setClassAxiomToPoint(OWLOntology ontology, OWLOntologyManager manager, String selectedClassShortForm,
			String point_hash) {

		OWLDataFactory dataFactory = manager.getOWLDataFactory();

		IRI iri = ontology.getOntologyID().getOntologyIRI().get();
		PrefixManager pm = new DefaultPrefixManager(null, null, iri.toString());

		IRI classIRI = IRI.create(ontology.getOntologyID().getOntologyIRI().get() + selectedClassShortForm);

		OWLClass selectedClass = manager.getOWLDataFactory().getOWLClass(classIRI);

		OWLNamedIndividual individual1 = dataFactory.getOWLNamedIndividual("#Point3d_" + point_hash, pm);

		OWLClassAssertionAxiom axiom = dataFactory.getOWLClassAssertionAxiom(selectedClass, individual1);

		System.out.println(individual1.getIRI() + "-->" + selectedClass.getIRI());

		manager.addAxiom(ontology, axiom);

	}

	public String createEquivalentClassOfInfluencingIndividuals(OWLOntology ontology, OWLOntologyManager manager,
			String selectedClassShortForm, String objectPropertyShortForm) {

		IRI iri = ontology.getOntologyID().getOntologyIRI().get();
		DefaultPrefixManager pm = new DefaultPrefixManager(null, null, iri.toString());
		IRI propertyIRI = IRI.create(ontology.getOntologyID().getOntologyIRI().get() + objectPropertyShortForm);
		OWLObjectProperty objectProperty = manager.getOWLDataFactory().getOWLObjectProperty(propertyIRI);

		IRI classIRI = IRI.create(ontology.getOntologyID().getOntologyIRI().get() + selectedClassShortForm);
		OWLClass selectedClass = manager.getOWLDataFactory().getOWLClass(classIRI);

		// OWLObjectPropertyExpression ope = (OWLObjectPropertyExpression)
		// manager.getOWLDataFactory().getOWLObjectSomeValuesFrom(objectProperty,
		// selectedClass );
		String classOfInterestShortForm = "#hasInfluenceOn" + "_" + selectedClassShortForm.substring(1);
		IRI classOfInterestIRI = IRI.create(ontology.getOntologyID().getOntologyIRI().get() + classOfInterestShortForm);

		OWLClass classOfInterest = manager.getOWLDataFactory().getOWLClass(classOfInterestIRI);

		OWLClassExpression influencesOnSelectedClass = manager.getOWLDataFactory()
				.getOWLObjectSomeValuesFrom(objectProperty, selectedClass);

		System.out.println(influencesOnSelectedClass.toString());

		OWLEquivalentClassesAxiom equivalentClass = manager.getOWLDataFactory()
				.getOWLEquivalentClassesAxiom(influencesOnSelectedClass, classOfInterest);

		System.out.println(equivalentClass.toString());

		manager.addAxiom(ontology, equivalentClass);

		return classOfInterestShortForm;

	}

	public static void main(String[] args) throws OWLOntologyCreationException, OWLOntologyStorageException {
		// Prelude

		String point_hash = "1234567_V1";
		String objectPropertyShortForm = "#dummyProperty";
		String filePath1 = "file:///c:/users/max/desktop/PELOPS/10303_108_Populated.owl";
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology1 = manager.loadOntologyFromOntologyDocument(IRI.create(filePath1));

		// Get, Select and Add:
		ClassManipulator myTestManipulator = new ClassManipulator();
		// Punning surogate: Create class with name of point
		OWLClass[] allClasses = myTestManipulator.getAllOWLClasses(ontology1, manager);

		String[] allClassesInShort = myTestManipulator.makeShortForm(ontology1, allClasses);

		// String selectedClassShortForm =
		// myTestManipulator.convertPointIndividuumToClass(ontology1, manager,
		// point_hash);

		String selectedClassShortForm = allClassesInShort[1];
		// add point to the created class
		myTestManipulator.setClassAxiomToPoint(ontology1, manager, selectedClassShortForm, point_hash);
		//
		myTestManipulator.createEquivalentClassOfInfluencingIndividuals(ontology1, manager, selectedClassShortForm,
				objectPropertyShortForm);

		// Get all ObjectProperties of the Ontology
		// OWLClass[] allClasses = myTestManipulator.getAllOWLClasses(ontology1,
		// manager);
		//
		// for (int i = 0; i < allClasses.length; i++) {
		// System.out.println(allClasses[i].getIRI());
		// }
		//
		// String[] selectedClasses = myTestManipulator.makeShortForm(ontology1,
		// allClasses);
		//
		// myTestManipulator.setClassAxiomToPoint(ontology1, manager,
		// selectedClasses[3], point_hash);

		manager.saveOntology(ontology1, new RDFXMLDocumentFormat());
	}
}
