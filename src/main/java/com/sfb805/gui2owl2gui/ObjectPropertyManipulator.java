package com.sfb805.gui2owl2gui;

import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

/*
 * Allows access and manipulation of OWLObjectProperties
 */
public class ObjectPropertyManipulator {

	public OWLObjectProperty[] getAllOWLObjectProperties(OWLOntology ontologyToBeQueried, OWLOntologyManager manager) {

		IRI iri = ontologyToBeQueried.getOntologyID().getOntologyIRI().get();
		System.out.println(iri.toString());
		DefaultPrefixManager pm = new DefaultPrefixManager(null, null, iri.toString() + "#");
		// Casting
		Set<OWLObjectProperty> ObjectPropertySet = ontologyToBeQueried.getObjectPropertiesInSignature();
		Object[] allObjectProperties = ObjectPropertySet.toArray();
		OWLObjectProperty[] objectProperties = new OWLObjectProperty[allObjectProperties.length];

		for (int i = 0; i < allObjectProperties.length; i++) {
			objectProperties[i] = (OWLObjectProperty) allObjectProperties[i];
			System.out.println("Not Simplified: " + objectProperties[i].getIRI());
		}

		return objectProperties;

	}

	public String[] makeShortForm(OWLOntology ontology, OWLObjectProperty[] objectProperties) {
		String[] objectPropertiesShortForm = new String[objectProperties.length];

		IRI iri = ontology.getOntologyID().getOntologyIRI().get();
		System.out.println(iri.toString());
		DefaultPrefixManager pm = new DefaultPrefixManager(null, null, iri.toString() + "#");

		for (int i = 0; i < objectProperties.length; i++) {
			objectPropertiesShortForm[i] = pm.getShortForm(objectProperties[i]).substring(1);
			System.out.println(objectPropertiesShortForm[i]);
		}

		return objectPropertiesShortForm;
	}

	public void setOWLObjectPropertyAxiomBetween2Points(OWLOntology ontology, OWLOntologyManager manager,
			String objectPropertyShortForm, String point1_hash, String point2_hash) {

		OWLDataFactory dataFactory = manager.getOWLDataFactory();

		IRI iri = ontology.getOntologyID().getOntologyIRI().get();
		DefaultPrefixManager pm = new DefaultPrefixManager(null, null, iri.toString());

		IRI propertyIRI = IRI.create(ontology.getOntologyID().getOntologyIRI().get() + "#" + objectPropertyShortForm);

		OWLObjectProperty objectProperty = manager.getOWLDataFactory().getOWLObjectProperty(propertyIRI);

		OWLNamedIndividual individual1 = dataFactory.getOWLNamedIndividual("#Point3d_" + point1_hash, pm);
		OWLNamedIndividual individual2 = dataFactory.getOWLNamedIndividual("#Point3d_" + point2_hash, pm);

		OWLObjectPropertyAssertionAxiom axiom = dataFactory.getOWLObjectPropertyAssertionAxiom(objectProperty,
				individual1, individual2);

		System.out.println(individual1.getIRI() + "-->" + objectProperty.getIRI() + "-->" + individual2.getIRI());

		manager.addAxiom(ontology, axiom);

	}

	public void setOWLObjectPropertyAxiomBetween1PointAnd1OWLNamedIndividual(OWLOntology ontology,
			OWLOntologyManager manager, String objectPropertyShortForm, String point1_hash,
			String owlNamedIndividualShortForm) {

		OWLDataFactory dataFactory = manager.getOWLDataFactory();

		IRI iri = ontology.getOntologyID().getOntologyIRI().get();
		DefaultPrefixManager pm = new DefaultPrefixManager(null, null, iri.toString());

		IRI propertyIRI = IRI.create(ontology.getOntologyID().getOntologyIRI().get() + "#" + objectPropertyShortForm);

		OWLObjectProperty objectProperty = manager.getOWLDataFactory().getOWLObjectProperty(propertyIRI);

		OWLNamedIndividual individual1 = dataFactory.getOWLNamedIndividual("#Point3d_" + point1_hash, pm);
		OWLNamedIndividual individual2 = dataFactory.getOWLNamedIndividual(owlNamedIndividualShortForm, pm);

		OWLObjectPropertyAssertionAxiom axiom = dataFactory.getOWLObjectPropertyAssertionAxiom(objectProperty,
				individual1, individual2);

		System.out.println(individual1.getIRI() + "-->" + objectProperty.getIRI() + "-->" + individual2.getIRI());

		manager.addAxiom(ontology, axiom);

	}

	public void setOWLObjectPropertyAxiomBetween1PointAnd1OWLNamedIndividualFromDifferentOntology(OWLOntology ontology1,
			OWLOntology ontology2, OWLOntologyManager manager, String objectPropertyShortForm, String point1_hash,
			String owlNamedIndividualShortForm) {
		// connects the point from ontology1 with the individual2 by
		// objectProperty both from ontology2

		OWLDataFactory dataFactory = manager.getOWLDataFactory();

		IRI iri1 = ontology1.getOntologyID().getOntologyIRI().get();
		IRI iri2 = ontology2.getOntologyID().getOntologyIRI().get();
		DefaultPrefixManager pm1 = new DefaultPrefixManager(null, null, iri1.toString());
		DefaultPrefixManager pm2 = new DefaultPrefixManager(null, null, iri2.toString());

		IRI propertyIRI = IRI.create(ontology2.getOntologyID().getOntologyIRI().get() + "#" + objectPropertyShortForm);

		OWLObjectProperty objectProperty = manager.getOWLDataFactory().getOWLObjectProperty(propertyIRI);

		OWLNamedIndividual individual1 = dataFactory.getOWLNamedIndividual("#Point3d_" + point1_hash, pm1);
		OWLNamedIndividual individual2 = dataFactory.getOWLNamedIndividual(owlNamedIndividualShortForm, pm2);

		OWLObjectPropertyAssertionAxiom axiom = dataFactory.getOWLObjectPropertyAssertionAxiom(objectProperty,
				individual1, individual2);

		System.out.println(individual1.getIRI() + "-->" + objectProperty.getIRI() + "-->" + individual2.getIRI());

		manager.addAxiom(ontology1, axiom);

	}

	public static void main(String[] args) throws OWLOntologyCreationException, OWLOntologyStorageException {
		// Prelude

		String point1 = "1111111111_V1";
		String point2 = "2222222222_V2";

		String filePath1 = "file:///c:/users/max/desktop/PELOPS/10303_108_Populated.owl";
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology1 = manager.loadOntologyFromOntologyDocument(IRI.create(filePath1));

		// Get, Select and Add:
		ObjectPropertyManipulator myTestManipulator = new ObjectPropertyManipulator();
		// Get all ObjectProperties of the Ontology
		OWLObjectProperty[] allObjectProperties = myTestManipulator.getAllOWLObjectProperties(ontology1, manager);

		String[] allObjectPropertyNames = myTestManipulator.makeShortForm(ontology1, allObjectProperties);

		// Select one ObjectProperty
		String selectedPropertyName = allObjectPropertyNames[10];
		// OWLObjectProperty selectedProperty = null;
		// Add the selectedObjectProperty to the Ontology
		myTestManipulator.setOWLObjectPropertyAxiomBetween2Points(ontology1, manager, selectedPropertyName, point1,
				point2);

		manager.saveOntology(ontology1, new RDFXMLDocumentFormat());
	}
}
