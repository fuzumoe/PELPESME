package com.sfb805.gui2owl2gui;

import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

public class IndividualManipulator {

	public OWLNamedIndividual[] getAllOWLNamedIndividuals(OWLOntology ontologyToBeQueried, OWLOntologyManager manager) {

		IRI iri = ontologyToBeQueried.getOntologyID().getOntologyIRI().get();
		System.out.println(iri.toString());
		DefaultPrefixManager pm = new DefaultPrefixManager(null, null, iri.toString() + "#");
		// Casting
		Set<OWLNamedIndividual> IndividualSet = ontologyToBeQueried.getIndividualsInSignature();
		Object[] allIndividuals = IndividualSet.toArray();
		OWLNamedIndividual[] individuals = new OWLNamedIndividual[allIndividuals.length];

		for (int i = 0; i < individuals.length; i++) {
			individuals[i] = (OWLNamedIndividual) allIndividuals[i];
			System.out.println("Not Simplified: " + individuals[i].getIRI());
		}

		return individuals;

	}

	public String[] makeShortForm(OWLOntology ontology, OWLNamedIndividual[] namedIndividuals) {
		String[] namedIndividualsShortForm = new String[namedIndividuals.length];

		IRI iri = ontology.getOntologyID().getOntologyIRI().get();
		System.out.println(iri.toString());
		DefaultPrefixManager pm = new DefaultPrefixManager(null, null, iri.toString() + "#");

		for (int i = 0; i < namedIndividuals.length; i++) {
			namedIndividualsShortForm[i] = pm.getShortForm(namedIndividuals[i]).substring(1);
			System.out.println(namedIndividualsShortForm[i]);
		}

		return namedIndividualsShortForm;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filePath1 = "file:///c:/users/max/desktop/PELOPS/10303_108_Populated.owl";
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology1 = null;
		try {
			ontology1 = manager.loadOntologyFromOntologyDocument(IRI.create(filePath1));
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Get, Select and Add:
		IndividualManipulator myTestManipulator = new IndividualManipulator();
		// Get all ObjectProperties of the Ontology
		OWLNamedIndividual[] allindividuals = myTestManipulator.getAllOWLNamedIndividuals(ontology1, manager);
		String[] allIndividualShortNames = myTestManipulator.makeShortForm(ontology1, allindividuals);
	}

}
