// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 25.04.2016 10:48:17
// Home Page:  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   DLQueriesWithHermiT.java

package com.sfb805.testing;

// Referenced classes of package com.sfb805.testing:
//            DLQueryPrinter, DLQueryEngine

public class DLQueriesWithHermiT {

	// public DLQueriesWithHermiT() {
	// }
	//
	// public static void main(String args[])
	// throws Exception
	// {
	// OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
	// OWLOntology ontology = manager.loadOntologyFromOntologyDocument(
	// new StringDocumentSource("");
	//// "<?xml version=\"1.0\"?>\n<rdf:RDF
	// xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"
	// xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"
	// xmlns:owl=\"http://www.w3.org/2002/07/owl#\"
	// xmlns=\"http://protege.stanford.edu/plugins/owl/owl-library/koala.owl#\"
	// xml:base=\"http://protege.stanford.edu/plugins/owl/owl-library/koala.owl\">\n
	// <owl:Ontology rdf:about=\"\"/>\n <owl:Class
	// rdf:ID=\"Female\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:FunctionalProperty
	// rdf:about=\"#hasGender\"/></owl:onProperty><owl:hasValue><Gender
	// rdf:ID=\"female\"/></owl:hasValue></owl:Restriction></owl:equivalentClass></owl:Class>\n
	// <owl:Class rdf:ID=\"Marsupials\"><owl:disjointWith><owl:Class
	// rdf:about=\"#Person\"/></owl:disjointWith><rdfs:subClassOf><owl:Class
	// rdf:about=\"#Animal\"/></rdfs:subClassOf></owl:Class>\n <owl:Class
	// rdf:ID=\"Student\"><owl:equivalentClass><owl:Class><owl:intersectionOf
	// rdf:parseType=\"Collection\"><owl:Class
	// rdf:about=\"#Person\"/><owl:Restriction><owl:onProperty><owl:FunctionalProperty
	// rdf:about=\"#isHardWorking\"/></owl:onProperty><owl:hasValue
	// rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\">true</owl:hasValue></owl:Restriction><owl:Restriction><owl:someValuesFrom><owl:Class
	// rdf:about=\"#University\"/></owl:someValuesFrom><owl:onProperty><owl:ObjectProperty
	// rdf:about=\"#hasHabitat\"/></owl:onProperty></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>\n
	// <owl:Class
	// rdf:ID=\"KoalaWithPhD\"><owl:versionInfo>1.2</owl:versionInfo><owl:equivalentClass><owl:Class><owl:intersectionOf
	// rdf:parseType=\"Collection\"><owl:Restriction><owl:hasValue><Degree
	// rdf:ID=\"PhD\"/></owl:hasValue><owl:onProperty><owl:ObjectProperty
	// rdf:about=\"#hasDegree\"/></owl:onProperty></owl:Restriction><owl:Class
	// rdf:about=\"#Koala\"/></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>\n
	// <owl:Class rdf:ID=\"University\"><rdfs:subClassOf><owl:Class
	// rdf:ID=\"Habitat\"/></rdfs:subClassOf></owl:Class>\n <owl:Class
	// rdf:ID=\"Koala\"><rdfs:subClassOf><owl:Restriction><owl:hasValue
	// rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\">false</owl:hasValue><owl:onProperty><owl:FunctionalProperty
	// rdf:about=\"#isHardWorking\"/></owl:onProperty></owl:Restriction></rdfs:subClassOf><rdfs:subClassOf><owl:Restriction><owl:someValuesFrom><owl:Class
	// rdf:about=\"#DryEucalyptForest\"/></owl:someValuesFrom><owl:onProperty><owl:ObjectProperty
	// rdf:about=\"#hasHabitat\"/></owl:onProperty></owl:Restriction></rdfs:subClassOf><rdfs:subClassOf
	// rdf:resource=\"#Marsupials\"/></owl:Class>\n <owl:Class
	// rdf:ID=\"Animal\"><rdfs:seeAlso>Male</rdfs:seeAlso><rdfs:subClassOf><owl:Restriction><owl:onProperty><owl:ObjectProperty
	// rdf:about=\"#hasHabitat\"/></owl:onProperty><owl:minCardinality
	// rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></rdfs:subClassOf><rdfs:subClassOf><owl:Restriction><owl:cardinality
	// rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:cardinality><owl:onProperty><owl:FunctionalProperty
	// rdf:about=\"#hasGender\"/></owl:onProperty></owl:Restriction></rdfs:subClassOf><owl:versionInfo>1.1</owl:versionInfo></owl:Class>\n
	// <owl:Class rdf:ID=\"Forest\"><rdfs:subClassOf
	// rdf:resource=\"#Habitat\"/></owl:Class>\n <owl:Class
	// rdf:ID=\"Rainforest\"><rdfs:subClassOf rdf:resource=
	//// \"#Forest\"/></owl:Class>\n <owl:Class
	// rdf:ID=\"GraduateStudent\"><rdfs:subClassOf><owl:Restriction><owl:onProperty><owl:ObjectProperty
	// rdf:about=\"#hasDegree\"/></owl:onProperty><owl:someValuesFrom><owl:Class><owl:oneOf
	// rdf:parseType=\"Collection\"><Degree rdf:ID=\"BA\"/><Degree
	// rdf:ID=\"BS\"/></owl:oneOf></owl:Class></owl:someValuesFrom></owl:Restriction></rdfs:subClassOf><rdfs:subClassOf
	// rdf:resource=\"#Student\"/></owl:Class>\n <owl:Class
	// rdf:ID=\"Parent\"><owl:equivalentClass><owl:Class><owl:intersectionOf
	// rdf:parseType=\"Collection\"><owl:Class
	// rdf:about=\"#Animal\"/><owl:Restriction><owl:onProperty><owl:ObjectProperty
	// rdf:about=\"#hasChildren\"/></owl:onProperty><owl:minCardinality
	// rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass><rdfs:subClassOf
	// rdf:resource=\"#Animal\"/></owl:Class>\n <owl:Class
	// rdf:ID=\"DryEucalyptForest\"><rdfs:subClassOf
	// rdf:resource=\"#Forest\"/></owl:Class>\n <owl:Class
	// rdf:ID=\"Quokka\"><rdfs:subClassOf><owl:Restriction><owl:hasValue
	// rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\">true</owl:hasValue><owl:onProperty><owl:FunctionalProperty
	// rdf:about=\"#isHardWorking\"/></owl:onProperty></owl:Restriction></rdfs:subClassOf><rdfs:subClassOf
	// rdf:resource=\"#Marsupials\"/></owl:Class>\n <owl:Class
	// rdf:ID=\"TasmanianDevil\"><rdfs:subClassOf
	// rdf:resource=\"#Marsupials\"/></owl:Class>\n <owl:Class
	// rdf:ID=\"MaleStudentWith3Daughters\"><owl:equivalentClass><owl:Class><owl:intersectionOf
	// rdf:parseType=\"Collection\"><owl:Class
	// rdf:about=\"#Student\"/><owl:Restriction><owl:onProperty><owl:FunctionalProperty
	// rdf:about=\"#hasGender\"/></owl:onProperty><owl:hasValue><Gender
	// rdf:ID=\"male\"/></owl:hasValue></owl:Restriction><owl:Restriction><owl:onProperty><owl:ObjectProperty
	// rdf:about=\"#hasChildren\"/></owl:onProperty><owl:cardinality
	// rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">3</owl:cardinality></owl:Restriction><owl:Restriction><owl:allValuesFrom
	// rdf:resource=\"#Female\"/><owl:onProperty><owl:ObjectProperty
	// rdf:about=\"#hasChildren\"/></owl:onProperty></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>\n
	// <owl:Class rdf:ID=\"Degree\"/>\n <owl:Class rdf:ID=\"Gender\"/>\n
	// <owl:Class
	// rdf:ID=\"Male\"><owl:equivalentClass><owl:Restriction><owl:hasValue
	// rdf:resource=\"#male\"/><owl:onProperty><owl:FunctionalProperty
	// rdf:about=\"#hasGender\"/></owl:onProperty></owl:Restriction></owl:equivalentClass></owl:Class>\n
	// <owl:Class rdf:ID=\"Person\"><rdfs:subClassOf
	// rdf:resource=\"#Animal\"/><owl:disjointWith
	// rdf:resource=\"#Marsupials\"/></owl:Class>\n <owl:ObjectProperty
	// rdf:ID=\"hasHabitat\"><rdfs:range rdf:resource=\"#Habitat\"/><rdfs:domain
	// rdf:resource=\"#Animal\"/></owl:ObjectProperty>\n <owl:ObjectProperty
	// rdf:ID=\"hasDegree\"><rdfs:domain rdf:resource=\"#Person\"/><rdfs:range
	// rdf:resource=\"#Degree\"/></owl:ObjectProperty>\n <owl:ObjectProperty
	// rdf:ID=\"hasChildren\"><rdfs:range rdf:resource=\"#Animal\"/><rdfs:domain
	// rdf:resource=\"#Animal\"/></owl:ObjectProperty>\n <owl:FunctionalProperty
	// rdf:ID=\"hasGender\"><rdfs:range rdf:resource=\"#Gender\"/><rdf:type
	// rdf:resource=\"http://www.w3.org/2002/07/owl#ObjectProperty\"/><rdfs:domain
	// rdf:resource=\"#Animal\"/></owl:FunctionalProperty>\n
	// <owl:FunctionalProperty rdf:ID=\"isHardWorking\"><rdfs:range
	// rdf:resource=
	//// \"http://www.w3.org/2001/XMLSchema#boolean\"/><rdfs:domain
	// rdf:resource=\"#Person\"/><rdf:type
	// rdf:resource=\"http://www.w3.org/2002/07/owl#DatatypeProperty\"/></owl:FunctionalProperty>\n
	// <Degree rdf:ID=\"MA\"/>\n</rdf:RDF>"));
	// OWLReasoner reasoner = (new
	// org.semanticweb.HermiT.Reasoner.ReasonerFactory()).createReasoner(ontology);
	// ShortFormProvider shortFormProvider = new SimpleShortFormProvider();
	// DLQueryPrinter dlQueryPrinter = new DLQueryPrinter(new
	// DLQueryEngine(reasoner, shortFormProvider), shortFormProvider);
	// BufferedReader br = new BufferedReader(new InputStreamReader(System.in,
	// "UTF-8"));
	// do
	// {
	// System.out.println("Type a class expression in Manchester Syntax and
	// press Enter (or press x to exit):");
	// String classExpression = br.readLine();
	// if(classExpression != null && !classExpression.equalsIgnoreCase("x"))
	// {
	// dlQueryPrinter.askQuery(classExpression.trim());
	// System.out.println();
	// } else
	// {
	// return;
	// }
	// } while(true);
	// }
	//
	// private static final String koala = "<?xml version=\"1.0\"?>\n<rdf:RDF
	// xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"
	// xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"
	// xmlns:owl=\"http://www.w3.org/2002/07/owl#\"
	// xmlns=\"http://protege.stanford.edu/plugins/owl/owl-library/koala.owl#\"
	// xml:base=\"http://protege.stanford.edu/plugins/owl/owl-library/koala.owl\">\n
	// <owl:Ontology rdf:about=\"\"/>\n <owl:Class
	// rdf:ID=\"Female\"><owl:equivalentClass><owl:Restriction><owl:onProperty><owl:FunctionalProperty
	// rdf:about=\"#hasGender\"/></owl:onProperty><owl:hasValue><Gender
	// rdf:ID=\"female\"/></owl:hasValue></owl:Restriction></owl:equivalentClass></owl:Class>\n
	// <owl:Class rdf:ID=\"Marsupials\"><owl:disjointWith><owl:Class
	// rdf:about=\"#Person\"/></owl:disjointWith><rdfs:subClassOf><owl:Class
	// rdf:about=\"#Animal\"/></rdfs:subClassOf></owl:Class>\n <owl:Class
	// rdf:ID=\"Student\"><owl:equivalentClass><owl:Class><owl:intersectionOf
	// rdf:parseType=\"Collection\"><owl:Class
	// rdf:about=\"#Person\"/><owl:Restriction><owl:onProperty><owl:FunctionalProperty
	// rdf:about=\"#isHardWorking\"/></owl:onProperty><owl:hasValue
	// rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\">true</owl:hasValue></owl:Restriction><owl:Restriction><owl:someValuesFrom><owl:Class
	// rdf:about=\"#University\"/></owl:someValuesFrom><owl:onProperty><owl:ObjectProperty
	// rdf:about=\"#hasHabitat\"/></owl:onProperty></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>\n
	// <owl:Class
	// rdf:ID=\"KoalaWithPhD\"><owl:versionInfo>1.2</owl:versionInfo><owl:equivalentClass><owl:Class><owl:intersectionOf
	// rdf:parseType=\"Collection\"><owl:Restriction><owl:hasValue><Degree
	// rdf:ID=\"PhD\"/></owl:hasValue><owl:onProperty><owl:ObjectProperty
	// rdf:about=\"#hasDegree\"/></owl:onProperty></owl:Restriction><owl:Class
	// rdf:about=\"#Koala\"/></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>\n
	// <owl:Class rdf:ID=\"University\"><rdfs:subClassOf><owl:Class
	// rdf:ID=\"Habitat\"/></rdfs:subClassOf></owl:Class>\n <owl:Class
	// rdf:ID=\"Koala\"><rdfs:subClassOf><owl:Restriction><owl:hasValue
	// rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\">false</owl:hasValue><owl:onProperty><owl:FunctionalProperty
	// rdf:about=\"#isHardWorking\"/></owl:onProperty></owl:Restriction></rdfs:subClassOf><rdfs:subClassOf><owl:Restriction><owl:someValuesFrom><owl:Class
	// rdf:about=\"#DryEucalyptForest\"/></owl:someValuesFrom><owl:onProperty><owl:ObjectProperty
	// rdf:about=\"#hasHabitat\"/></owl:onProperty></owl:Restriction></rdfs:subClassOf><rdfs:subClassOf
	// rdf:resource=\"#Marsupials\"/></owl:Class>\n <owl:Class
	// rdf:ID=\"Animal\"><rdfs:seeAlso>Male</rdfs:seeAlso><rdfs:subClassOf><owl:Restriction><owl:onProperty><owl:ObjectProperty
	// rdf:about=\"#hasHabitat\"/></owl:onProperty><owl:minCardinality
	// rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></rdfs:subClassOf><rdfs:subClassOf><owl:Restriction><owl:cardinality
	// rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:cardinality><owl:onProperty><owl:FunctionalProperty
	// rdf:about=\"#hasGender\"/></owl:onProperty></owl:Restriction></rdfs:subClassOf><owl:versionInfo>1.1</owl:versionInfo></owl:Class>\n
	// <owl:Class rdf:ID=\"Forest\"><rdfs:subClassOf
	// rdf:resource=\"#Habitat\"/></owl:Class>\n <owl:Class
	// rdf:ID=\"Rainforest\"><rdfs:subClassOf
	// rdf:resource=\"#Forest\"/></owl:Class>\n <owl:Class
	// rdf:ID=\"GraduateStudent\">
	// <rdfs:subClassOf><owl:Restriction><owl:onProperty><owl:
	// ObjectProperty
	// rdf:about=\"#hasDegree\"/></owl:onProperty><owl:someValuesFrom><owl:Class><owl:oneOf
	// rdf:parseType=\"Collection\"><Degree rdf:ID=\"BA\"/><Degree
	// rdf:ID=\"BS\"/></owl:oneOf></owl:Class></owl:someValuesFrom></owl:Restriction></rdfs:subClassOf><rdfs:subClassOf
	// rdf:resource=\"#Student\"/></owl:Class>\n <owl:Class
	// rdf:ID=\"Parent\"><owl:equivalentClass><owl:Class><owl:intersectionOf
	// rdf:parseType=\"Collection\"><owl:Class
	// rdf:about=\"#Animal\"/><owl:Restriction><owl:onProperty><owl:ObjectProperty
	// rdf:about=\"#hasChildren\"/></owl:onProperty><owl:minCardinality
	// rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">1</owl:minCardinality></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass><rdfs:subClassOf
	// rdf:resource=\"#Animal\"/></owl:Class>\n <owl:Class
	// rdf:ID=\"DryEucalyptForest\"><rdfs:subClassOf
	// rdf:resource=\"#Forest\"/></owl:Class>\n <owl:Class
	// rdf:ID=\"Quokka\"><rdfs:subClassOf><owl:Restriction><owl:hasValue
	// rdf:datatype=\"http://www.w3.org/2001/XMLSchema#boolean\">true</owl:hasValue><owl:onProperty><owl:FunctionalProperty
	// rdf:about=\"#isHardWorking\"/></owl:onProperty></owl:Restriction></rdfs:subClassOf><rdfs:subClassOf
	// rdf:resource=\"#Marsupials\"/></owl:Class>\n <owl:Class
	// rdf:ID=\"TasmanianDevil\"><rdfs:subClassOf
	// rdf:resource=\"#Marsupials\"/></owl:Class>\n <owl:Class
	// rdf:ID=\"MaleStudentWith3Daughters\"><owl:equivalentClass><owl:Class><owl:intersectionOf
	// rdf:parseType=\"Collection\"><owl:Class
	// rdf:about=\"#Student\"/><owl:Restriction><owl:onProperty><owl:FunctionalProperty
	// rdf:about=\"#hasGender\"/></owl:onProperty><owl:hasValue><Gender
	// rdf:ID=\"male\"/></owl:hasValue></owl:Restriction><owl:Restriction><owl:onProperty><owl:ObjectProperty
	// rdf:about=\"#hasChildren\"/></owl:onProperty><owl:cardinality
	// rdf:datatype=\"http://www.w3.org/2001/XMLSchema#int\">3</owl:cardinality></owl:Restriction><owl:Restriction><owl:allValuesFrom
	// rdf:resource=\"#Female\"/><owl:onProperty><owl:ObjectProperty
	// rdf:about=\"#hasChildren\"/></owl:onProperty></owl:Restriction></owl:intersectionOf></owl:Class></owl:equivalentClass></owl:Class>\n
	// <owl:Class rdf:ID=\"Degree\"/>\n <owl:Class rdf:ID=\"Gender\"/>\n
	// <owl:Class
	// rdf:ID=\"Male\"><owl:equivalentClass><owl:Restriction><owl:hasValue
	// rdf:resource=\"#male\"/><owl:onProperty><owl:FunctionalProperty
	// rdf:about=\"#hasGender\"/></owl:onProperty></owl:Restriction></owl:equivalentClass></owl:Class>\n
	// <owl:Class rdf:ID=\"Person\"><rdfs:subClassOf
	// rdf:resource=\"#Animal\"/><owl:disjointWith
	// rdf:resource=\"#Marsupials\"/></owl:Class>\n <owl:ObjectProperty
	// rdf:ID=\"hasHabitat\"><rdfs:range rdf:resource=\"#Habitat\"/><rdfs:domain
	// rdf:resource=\"#Animal\"/></owl:ObjectProperty>\n <owl:ObjectProperty
	// rdf:ID=\"hasDegree\"><rdfs:domain rdf:resource=\"#Person\"/><rdfs:range
	// rdf:resource=\"#Degree\"/></owl:ObjectProperty>\n <owl:ObjectProperty
	// rdf:ID=\"hasChildren\"><rdfs:range rdf:resource=\"#Animal\"/><rdfs:domain
	// rdf:resource=\"#Animal\"/></owl:ObjectProperty>\n <owl:FunctionalProperty
	// rdf:ID=\"hasGender\"><rdfs:range rdf:resource=\"#Gender\"/><rdf:type
	// rdf:resource=\"http://www.w3.org/2002/07/owl#ObjectProperty\"/><rdfs:domain
	// rdf:resource=\"#Animal\"/></owl:FunctionalProperty>\n
	// <owl:FunctionalProperty rdf:ID=\"isHardWorking\"><rdfs:range
	// rdf:resource=\"http://www.w3.org/2001/XMLSchema#boolean\"/><rdfs:domain
	// rdf:resource=\"#Person\"/><rdf:type
	// rdf:resource=\"http://www.w3.org/2002/07/owl#DatatypeProperty\"/></owl:FunctionalProperty>\n
	// <Degree rdf:ID=\"MA\"/>\n</rdf:RDF>";
}
