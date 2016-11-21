package com.sfb805.testing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.search.EntitySearcher;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

public class DLquery {

	public String filePath2;
	public OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

	public DLquery() {
	}

	public static void main(String args[]) throws OWLOntologyCreationException {
		DLquery testdlquery = new DLquery();
		String timeDataIRIString = "http://www.hozo.jp/owl/EXPOApr19";
		DLquery myQuery = new DLquery();
		DefaultPrefixManager pm = new DefaultPrefixManager(null, null, "http://www.hozo.jp/owl/EXPOApr19#");
		// myQuery.filePath2 =
		// "c:/users/max/desktop/PELOPS/10303_108_Populated.owl";
		myQuery.filePath2 = "c:/users/max/desktop/OWL_Ontologien/ExperimentalDataOntology.owl";
		OWLOntology ont = myQuery.manager.loadOntologyFromOntologyDocument(IRI.create("file:///" + myQuery.filePath2));
		System.out.println("try to set time points...");

		String selectedClassShortForm = "http://www.hozo.jp/owl/EXPOApr19#TimePoint";
		Set setOfIndividuals = myQuery.getIndividualsOfClassByLongName(selectedClassShortForm, ont);
		OWLNamedIndividual[] arrayOfIndividuals = (OWLNamedIndividual[]) setOfIndividuals
				.toArray(new OWLNamedIndividual[setOfIndividuals.size()]);

		for (int i = 0; i < arrayOfIndividuals.length; i++) {
			System.out.println(arrayOfIndividuals[i]);

			OWLClass[] superclass = myQuery.getDirectClassOfIndividual(arrayOfIndividuals[i], ont);

			for (int j = 0; j < superclass.length; j++) {
				System.out.println("Direct superclass: " + superclass[j]);

				OWLNamedIndividual[] individuals = testdlquery.query1(superclass[j], ont);

				for (int k = 0; k < individuals.length; k++) {
					testdlquery.query2(individuals[k], ont, superclass[j]);
				}

			}

		}

		String[] namedIndividualsShortForm = new String[arrayOfIndividuals.length];

		for (int i = 0; i < arrayOfIndividuals.length; i++) {
			namedIndividualsShortForm[i] = pm.getShortForm(arrayOfIndividuals[i]).substring(1);
			System.out.println(namedIndividualsShortForm[i]);
		}

		if (namedIndividualsShortForm.length == 0) {
			System.out.println("No uncertainty time data available.");
		}

		else {
			for (int i = 0; i < namedIndividualsShortForm.length; i++) {
				System.out.println(namedIndividualsShortForm[i]);
			}

			// String[] dummynames = { "Hello", "Dummy" };

			System.out.println("time points set successful!");
		}
	}

	public OWLNamedIndividual[] query1(OWLClass superclass, OWLOntology ontology) {

		DLquery myQuery = new DLquery();
		String timeDataIRIString = "http://www.hozo.jp/owl/EXPOApr19";

		OWLObjectProperty has_time_point = myQuery.manager.getOWLDataFactory()
				.getOWLObjectProperty(timeDataIRIString + "#has_time_point");
		OWLObjectProperty has_measurement_time_point = myQuery.manager.getOWLDataFactory()
				.getOWLObjectProperty(timeDataIRIString + "#has_measurement_time_point");
		OWLObjectProperty has_measurement_series = myQuery.manager.getOWLDataFactory()
				.getOWLObjectProperty(timeDataIRIString + "#has_measurement_series");
		OWLObjectProperty has_point = myQuery.manager.getOWLDataFactory()
				.getOWLObjectProperty(timeDataIRIString + "#has_point");
		OWLObjectProperty is_time_point_of_measurement_series = myQuery.manager.getOWLDataFactory()
				.getOWLObjectProperty(timeDataIRIString + "#is_time_point_of_measurement_series");

		myQuery.manager.getOWLDataFactory().getOWLObjectSomeValuesFrom(has_time_point, superclass);

		OWLObjectSomeValuesFrom someValuesFrom1 = myQuery.manager.getOWLDataFactory()
				.getOWLObjectSomeValuesFrom(has_measurement_time_point, superclass);

		OWLObjectSomeValuesFrom someValuesFrom3 = myQuery.manager.getOWLDataFactory()
				.getOWLObjectSomeValuesFrom(has_measurement_series, someValuesFrom1);

		// someValuesFrom.
		// myQuery.manager.getOWLDataFactory().getOWLObjectUnionOf(classexpression1,
		// classexpression2);

		OWLReasoner reasoner = (new org.semanticweb.HermiT.Reasoner.ReasonerFactory()).createReasoner(ontology);

		// Query 1: has_measurement_series some
		// (has_measurement_time_point some T1)
		Set namedIndividuals = reasoner.getInstances(someValuesFrom3).getFlattened();

		OWLNamedIndividual[] individuals = (OWLNamedIndividual[]) namedIndividuals
				.toArray(new OWLNamedIndividual[namedIndividuals.size()]);

		System.out.println("query1: " + individuals[0]);

		return individuals;

	}

	public OWLNamedIndividual query2(OWLNamedIndividual namedIndividuals, OWLOntology ontology, OWLClass punningclass) {
		// returns the normal distribution that are linked to the timepoint
		// individuals, given by
		// its punningclass, for the given points in namedIndividuals

		// query2>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		// (has_time_point some
		// (T1 and is_time_point_of_measurement_series some
		// (measurement_series
		// and has_point some Query)))
		String timeDataIRIString = "http://www.hozo.jp/owl/EXPOApr19";
		DLquery myQuery = new DLquery();
		OWLReasoner reasoner = (new org.semanticweb.HermiT.Reasoner.ReasonerFactory()).createReasoner(ontology);

		OWLObjectProperty has_time_point = myQuery.manager.getOWLDataFactory()
				.getOWLObjectProperty(timeDataIRIString + "#has_time_point");
		OWLObjectProperty is_time_point_of_measurement_series = myQuery.manager.getOWLDataFactory()
				.getOWLObjectProperty(timeDataIRIString + "#is_time_point_of_measurement_series");
		OWLObjectProperty has_point = myQuery.manager.getOWLDataFactory()
				.getOWLObjectProperty(timeDataIRIString + "#has_point");

		OWLClass measurement_series = myQuery.manager.getOWLDataFactory()
				.getOWLClass(timeDataIRIString + "#measurement_series");

		DefaultPrefixManager pm = new DefaultPrefixManager(null, null, "http://www.hozo.jp/owl/EXPOApr19#");

		OWLObjectSomeValuesFrom someValuesFrom2 = myQuery.manager.getOWLDataFactory()
				.getOWLObjectSomeValuesFrom(has_time_point, punningclass);

		OWLClassExpression enumeration = myQuery.manager.getOWLDataFactory().getOWLObjectOneOf(namedIndividuals);

		Set namedIndividuals2 = reasoner.getInstances(enumeration).getFlattened();

		OWLNamedIndividual[] individuals2 = (OWLNamedIndividual[]) namedIndividuals2
				.toArray(new OWLNamedIndividual[namedIndividuals2.size()]);

		// has_point some Query
		OWLObjectSomeValuesFrom someValuesFrom4 = myQuery.manager.getOWLDataFactory()
				.getOWLObjectSomeValuesFrom(has_point, enumeration);

		// (measurement_series and has_point some Query)
		OWLClassExpression intersection1 = myQuery.manager.getOWLDataFactory()
				.getOWLObjectIntersectionOf(measurement_series, someValuesFrom4);

		// is_time_point_of_measurement_series some
		// (measurement_series
		// and has_point some Query)))
		OWLObjectSomeValuesFrom someValuesFrom5 = myQuery.manager.getOWLDataFactory()
				.getOWLObjectSomeValuesFrom(is_time_point_of_measurement_series, intersection1);

		// (T1 and is_time_point_of_measurement_series some
		// (measurement_series
		// and has_point some Query)))
		OWLClassExpression intersection2 = myQuery.manager.getOWLDataFactory().getOWLObjectIntersectionOf(punningclass,
				someValuesFrom5);

		// (has_time_point some
		// (T1 and is_time_point_of_measurement_series some
		// (measurement_series
		// and has_point some Query)))
		OWLObjectSomeValuesFrom someValuesFrom6 = myQuery.manager.getOWLDataFactory()
				.getOWLObjectSomeValuesFrom(has_time_point, intersection2);

		Set namedIndividuals3 = reasoner.getInstances(someValuesFrom6).getFlattened();

		OWLNamedIndividual[] individuals3 = (OWLNamedIndividual[]) namedIndividuals3
				.toArray(new OWLNamedIndividual[namedIndividuals3.size()]);

		for (int k = 0; k < individuals3.length; k++) {
			System.out.println("query2: " + individuals3[k]);
		}

		return individuals3[0];

	}

	public Set getIndividualsOfClass(OWLClass classOfInterest, OWLOntology ont) {

		OWLReasoner reasoner = (new org.semanticweb.HermiT.Reasoner.ReasonerFactory()).createReasoner(ont);
		NodeSet individualsNodeSet = reasoner.getInstances(classOfInterest, false);
		Set individuals = individualsNodeSet.getFlattened();

		return individuals;
	}

	public OWLClass[] getDirectClassOfIndividual(OWLNamedIndividual individual, OWLOntology ontology) {
		OWLReasoner reasoner = (new org.semanticweb.HermiT.Reasoner.ReasonerFactory()).createReasoner(ontology);
		Set<OWLClass> classes = reasoner.getTypes(individual, true).getFlattened();
		OWLClass[] directSuperClasses = (OWLClass[]) classes.toArray(new OWLClass[classes.size()]);

		return directSuperClasses;
	}

	public Set getIndividualsOfClassByName(String selectedClassShortForm, OWLOntology ont) {

		OWLDataFactory factory = manager.getOWLDataFactory();
		OWLClass classOfInterest = factory
				.getOWLClass(IRI.create(ont.getOntologyID().getOntologyIRI().get() + selectedClassShortForm));

		OWLReasoner reasoner = (new org.semanticweb.HermiT.Reasoner.ReasonerFactory()).createReasoner(ont);
		NodeSet individualsNodeSet = reasoner.getInstances(classOfInterest, false);
		Set individuals = individualsNodeSet.getFlattened();

		return individuals;
	}

	public Set getIndividualsOfClassByLongName(String selectedClassLongForm, OWLOntology ont) {

		OWLDataFactory factory = manager.getOWLDataFactory();
		OWLClass classOfInterest = factory.getOWLClass(selectedClassLongForm);

		OWLReasoner reasoner = (new org.semanticweb.HermiT.Reasoner.ReasonerFactory()).createReasoner(ont);
		NodeSet individualsNodeSet = reasoner.getInstances(classOfInterest, false);
		Set individuals = individualsNodeSet.getFlattened();

		return individuals;
	}

	public Set getIndividualsByObjectProperty(OWLNamedIndividual individuum, OWLObjectProperty objectProperty,
			OWLOntology ont) {

		OWLReasoner reasoner = (new org.semanticweb.HermiT.Reasoner.ReasonerFactory()).createReasoner(ont);
		Set individuals = reasoner.getObjectPropertyValues(individuum, objectProperty).getFlattened();

		return individuals;
	}

	public Object[][] queryPointsInClassOfInterest(OWLOntology ont, OWLOntologyManager manager,
			String classNameOfInterest) throws OWLOntologyCreationException {
		// gets iri, hash, sampleNumber, Expectation and Variance of the
		// Individuals wich are:
		// linked with the individual in the class of interest by the specified
		// ObjectProperty
		int index = 12;
		Object[] point = new Object[index];
		List<Object[]> listOfPoints = new ArrayList<Object[]>(1);

		OWLDataFactory factory = manager.getOWLDataFactory();
		// OWLOntology ont =
		// manager.loadOntologyFromOntologyDocument(IRI.create("file:///" +
		// filePath));
		IRI ontology_iri = (IRI) ont.getOntologyID().getOntologyIRI().get();
		PrefixManager pm = new DefaultPrefixManager(null, null, ontology_iri.toString());

		OWLClass classOfInterest = factory
				.getOWLClass(IRI.create(ont.getOntologyID().getOntologyIRI().get() + classNameOfInterest));
		OWLObjectProperty objectPropertyOfInterest = factory.getOWLObjectProperty("#ordered_list_has_Slot", pm);

		System.out.println(
				(new StringBuilder()).append("class of Interest: ").append(classOfInterest.getIRI()).toString());

		// Get all individuals in the class of interest
		Set individuals = this.getIndividualsOfClass(classOfInterest, ont);

		OWLNamedIndividual i;
		// for each individual in the class of interest...
		for (Iterator iterator = individuals.iterator(); iterator.hasNext();) {

			i = (OWLNamedIndividual) iterator.next();
			System.out.println("///////////////////////////////////////////////////////");
			System.out.println(i.getIRI());

			// get all individuals that are linked with the individual in the
			// class of interest by the specified ObjectProperty
			Set individuals2 = getIndividualsByObjectProperty(i, objectPropertyOfInterest, ont);

			// for each individual that is linked with the individual in the
			// class of interest...

			for (Iterator iterator2 = individuals2.iterator(); iterator2.hasNext();) {
				OWLNamedIndividual indi = (OWLNamedIndividual) iterator2.next();

				// ToDo: check if the point has uncertainty...create a better
				// indicator: Class assertion: if VarianceX,Y or Z -> Uncertain
				// Point.

				// ...check to which class the individual belongs and get
				// its
				// dataProperty
				OWLDataPropertyExpression hasValue = factory.getOWLDataProperty("#hasValue", pm);
				OWLDataPropertyExpression hasNXhashCode = factory.getOWLDataProperty("#hasNXhashCode", pm);
				OWLLiteral literal = this.getDataPropertyValue(indi, hasValue, ont);

				OWLClass check = factory.getOWLClass("#Cartesian_X_Expectation", pm);
				if (this.checkIndividualClassAffiliation(indi, check, ont)) {
					point[5] = Double.parseDouble(literal.getLiteral());
					System.out.println(
							"Check: " + check.getIRI().getShortForm() + " has literal " + literal.getLiteral());

				}

				check = factory.getOWLClass("#Cartesian_Y_Expectation", pm);
				if (this.checkIndividualClassAffiliation(indi, check, ont)) {
					point[6] = Double.parseDouble(literal.getLiteral());
					System.out.println(
							"Check: " + check.getIRI().getShortForm() + " has literal " + literal.getLiteral());
				}

				check = factory.getOWLClass("#Cartesian_Z_Expectation", pm);
				if (this.checkIndividualClassAffiliation(indi, check, ont)) {
					point[7] = Double.parseDouble(literal.getLiteral());
					System.out.println(
							"Check: " + check.getIRI().getShortForm() + " has literal " + literal.getLiteral());
				}

				check = factory.getOWLClass("#Cartesian_X_Variance", pm);
				if (this.checkIndividualClassAffiliation(indi, check, ont)) {
					point[8] = Double.parseDouble(literal.getLiteral());
					System.out.println(
							"Check: " + check.getIRI().getShortForm() + " has literal " + literal.getLiteral());
				}

				check = factory.getOWLClass("#Cartesian_Y_Variance", pm);
				if (this.checkIndividualClassAffiliation(indi, check, ont)) {
					point[9] = Double.parseDouble(literal.getLiteral());
					System.out.println(
							"Check: " + check.getIRI().getShortForm() + " has literal " + literal.getLiteral());
				}

				check = factory.getOWLClass("#Cartesian_Z_Variance", pm);
				if (this.checkIndividualClassAffiliation(indi, check, ont)) {
					point[10] = Double.parseDouble(literal.getLiteral());
					System.out.println(
							"Check: " + check.getIRI().getShortForm() + " has literal " + literal.getLiteral());
				}

				check = factory.getOWLClass("#Sample_Number", pm);
				if (this.checkIndividualClassAffiliation(indi, check, ont)) {
					double sampleNumber = Double.parseDouble(literal.getLiteral());
					int sampleNumberInt = (int) sampleNumber;
					point[11] = sampleNumberInt;
					System.out.println(
							"Check: " + check.getIRI().getShortForm() + " has literal " + literal.getLiteral());
				}

				check = factory.getOWLClass("#Cartesian_X_Coordinate", pm);
				if (this.checkIndividualClassAffiliation(indi, check, ont)) {
					// Attention: iri comes from the point individual, not
					// from
					// the check individual!!!
					point[0] = i.getIRI().toString();
					System.out.println("point[0]: " + point[0]);
					// Attention: hash comes from the point individual, not
					// from
					// the check individual!!!
					point[1] = this.getDataPropertyValue(i, hasNXhashCode, ont).getLiteral();
					System.out.println(this.getDataPropertyValue(i, hasNXhashCode, ont).getLiteral());
					double x_coordinate = Double.parseDouble(literal.getLiteral());
					point[2] = x_coordinate;
					System.out.println(
							"Check: " + check.getIRI().getShortForm() + " has literal " + literal.getLiteral());
				}
				check = factory.getOWLClass("#Cartesian_Y_Coordinate", pm);
				if (this.checkIndividualClassAffiliation(indi, check, ont)) {
					double y_coordinate = Double.parseDouble(literal.getLiteral());
					point[3] = y_coordinate;
					System.out.println(
							"Check: " + check.getIRI().getShortForm() + " has literal " + literal.getLiteral());
				}
				check = factory.getOWLClass("#Cartesian_Z_Coordinate", pm);
				if (this.checkIndividualClassAffiliation(indi, check, ont)) {
					double z_coordinate = Double.parseDouble(literal.getLiteral());
					point[4] = z_coordinate;
					System.out.println(
							"Check: " + check.getIRI().getShortForm() + " has literal " + literal.getLiteral());
				}
				// when all values are written, add Object[] to list
				if (point[0] != null && point[1] != null && point[2] != null && point[3] != null && point[4] != null
						&& point[5] != null && point[6] != null && point[7] != null && point[8] != null
						&& point[9] != null && point[10] != null && point[11] != null) {
					listOfPoints.add(point);
					System.out.println("add point to listOfPoints");
					System.out.println("listOfPoints.size(): " + listOfPoints.size());
					point = new Object[index];
				}
			}

		}

		// point[i][0] = iri_string;
		// point[i][1] = hash_string
		// point[i][2] = x_coordinate
		// point[i][3] = y_coordinate
		// point[i][4] = z_coordinate
		// point[i][5] = expectation_x;
		// point[i][6] = expectation_y;
		// point[i][7] = expectation_z;
		// point[i][8] = variance_y;
		// point[i][9] = varinace_y;
		// point[i][10] = variance_z;
		// point[i][11] = sampleNumberValue;

		Object[][] points = new Object[listOfPoints.size()][];
		points = (Object[][]) listOfPoints.toArray(new Object[listOfPoints.size()][]);
		return points;

	}

	public boolean checkIndividualClassAffiliation(OWLNamedIndividual individuum, OWLClass checkClass,
			OWLOntology ontology) {
		boolean affiliation = false;

		OWLClass c;
		// get all asserted class for the individuum and compare it to the
		// checkClass
		for (Iterator iterator3 = EntitySearcher.getTypes(individuum, ontology).iterator(); iterator3.hasNext();) {
			c = (OWLClass) iterator3.next();
			if (c.equals(checkClass)) {
				affiliation = true;
			}
		}

		return affiliation;
	}

	public OWLLiteral getDataPropertyValue(OWLNamedIndividual individuum,
			OWLDataPropertyExpression dataPropertyExpression, OWLOntology onto) {
		OWLLiteral lit = null;

		// get all DataPropertyAxioms of the individual...
		Set<OWLDataPropertyAssertionAxiom> properties = onto.getDataPropertyAssertionAxioms(individuum);
		// and iterate through...
		for (OWLDataPropertyAssertionAxiom ax : properties) {
			System.out.println("ax.getProperty(): " + ax.getProperty());

			if (EntitySearcher.getDataPropertyValues(individuum, ax.getProperty(), onto).iterator().hasNext()) {

				if (ax.getProperty().equals(dataPropertyExpression)) {
					lit = EntitySearcher.getDataPropertyValues(individuum, ax.getProperty(), onto).iterator().next();
					System.out.println("OWLLiteral lit: " + lit.toString());
				}

			}
		}
		return lit;

	}

	public void testQuery() throws OWLOntologyCreationException {
		String filePath = "file:///c:/Users/max/Desktop/ExampleOntologies/Difference_Ontology_Database.owl";
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLDataFactory factory = manager.getOWLDataFactory();
		OWLOntology ont = manager.loadOntologyFromOntologyDocument(IRI.create(filePath));
		IRI ontology_iri = (IRI) ont.getOntologyID().getOntologyIRI().get();
		PrefixManager pm = new DefaultPrefixManager(null, null, ontology_iri.toString());
		OWLReasoner reasoner = (new org.semanticweb.HermiT.Reasoner.ReasonerFactory()).createReasoner(ont);
		reasoner.precomputeInferences(new InferenceType[0]);
		boolean consistent = reasoner.isConsistent();
		System.out.println((new StringBuilder()).append("Ontology is consistent: ").append(consistent).toString());
		Set classSet = ont.getClassesInSignature();
		Object allClasses[] = classSet.toArray();
		OWLClass classArray[] = new OWLClass[allClasses.length];
		for (int i = 0; i < allClasses.length; i++) {
			classArray[i] = (OWLClass) allClasses[i];
			System.out.println((new StringBuilder()).append("Class[").append(i).append("]")
					.append(classArray[i].getIRI()).toString());
		}

		IRI iri = IRI.create((new StringBuilder()).append(ont.getOntologyID().getOntologyIRI().get())
				.append("#Points_of_Interrest").toString());
		System.out.println((new StringBuilder()).append("ontology iri: ").append(iri).toString());
		OWLClass classOfInterest = factory.getOWLClass(classArray[3].getIRI());
		System.out.println(
				(new StringBuilder()).append("class of Interest: ").append(classOfInterest.getIRI()).toString());
		NodeSet individualsNodeSet = reasoner.getInstances(classOfInterest, false);
		Set individuals = individualsNodeSet.getFlattened();
		OWLNamedIndividual i;
		for (Iterator iterator = individuals.iterator(); iterator.hasNext();) {

			i = (OWLNamedIndividual) iterator.next();
			System.out.println(i.getIRI());

			Set<OWLDataPropertyAssertionAxiom> properties = ont.getDataPropertyAssertionAxioms(i);
			for (OWLDataPropertyAssertionAxiom ax : properties) {
				System.out.println(ax.getProperty());

				if (EntitySearcher.getDataPropertyValues(i, ax.getProperty(), ont).iterator().hasNext()) {
					OWLLiteral lit = EntitySearcher.getDataPropertyValues(i, ax.getProperty(), ont).iterator().next();
					System.out.println(lit.toString());
				}

			}
			OWLObjectProperty pointHasSlot = factory.getOWLObjectProperty("#ordered_list_has_Slot", pm);
			Set individuals2 = reasoner.getObjectPropertyValues(i, pointHasSlot).getFlattened();
			OWLNamedIndividual indi;
			for (Iterator iterator2 = individuals2.iterator(); iterator2.hasNext();) {
				indi = (OWLNamedIndividual) iterator2.next();
				System.out.println("indi: " + indi);
				OWLClass c;
				OWLClass referenceClass = factory.getOWLClass("#A", pm);
				for (Iterator iterator3 = EntitySearcher.getTypes(indi, ont).iterator(); iterator3.hasNext();) {
					c = (OWLClass) iterator3.next();
					System.out.println("EntitySearcher.getTypes: " + c.getIRI());
					if (c.equals(referenceClass)) {
						System.out.println("Individual " + i.getIRI() + "-->" + pointHasSlot.getIRI() + "-->"
								+ indi.getIRI() + " of class " + referenceClass.getIRI());
					}

				}

			}

		}

	}
}
