package com.sfb805.testing;

import java.rmi.RemoteException;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import com.sfb805.nx.NXRemoteGrabber;

import nxopen.BasePart;
import nxopen.Body;
import nxopen.BodyCollection;
import nxopen.Edge;
import nxopen.Face;
import nxopen.NXException;
import nxopen.Part;
import nxopen.PartCollection;
import nxopen.userdefinedobjects.UserDefinedObject;
import nxopen.userdefinedobjects.UserDefinedObject.LinkDefinition;
import nxopen.userdefinedobjects.UserDefinedObjectManager;
import nxopen.userdefinedobjects.UserDefinedObjectManager.LinkedUdoDefinition;

public class MultipleUDO2oneNXobjectTest {
	
	public static void main(String[] args) throws RemoteException, NXException{
		MultipleUDO2oneNXobjectTest test = new MultipleUDO2oneNXobjectTest();
		NXRemoteGrabber grabber = new NXRemoteGrabber();
		 OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		
		test.nxObjectIterator(grabber, manager);
	}
/**
 * 	
 * @param nxRemoteGrabber
 * @param manager
 * @throws RemoteException
 * @throws NXException
 */
public static void nxObjectIterator(NXRemoteGrabber nxRemoteGrabber, OWLOntologyManager manager) throws RemoteException, NXException{
	
	BasePart myBasePart = nxRemoteGrabber.sess.parts().baseDisplay();
	UserDefinedObjectManager myUDOmanager = myBasePart.userDefinedObjectManager();
	
	
	Part workpart = nxRemoteGrabber.sess.parts().work();

	//Part iterator
	PartCollection.Iterator iter;
	PartCollection partList =nxRemoteGrabber.sess.parts();
	//Iterate through all Parts, which are no sketches
	for (iter = partList.iterator(); iter.hasNext();)
	{
		
		Part part = (Part) iter.next();
		
	//iterate the B-Reps
	BodyCollection bodyList = part.bodies();
	BodyCollection.Iterator itr;
	for (itr = bodyList.iterator(); itr.hasNext();)
	{ 
		Body bodyObject =(Body)itr.next();


		//tuWas.doThis(bodyObject);

		//run through all Faces of the current B-Rep
		Face faceArray[] = bodyObject.getFaces();
		for (int inx=0; inx <faceArray.length; ++inx)
		{
			Face faceObject = faceArray[inx];
		//	tuWas.doThis(faceObject);

			//run through all Edges of the current B-Rep
			Edge edgeArray[] = faceObject.getEdges();
			for (int indx=0; indx < edgeArray.length; ++indx)
			{ 
				Edge edgeObject = edgeArray[indx];
	
				if(myUDOmanager.isObjectLinkedToUserDefinedObject(edgeObject)){
					
					LinkedUdoDefinition [] udolinks = myUDOmanager.getLinksToObject(edgeObject);
					
					for(LinkedUdoDefinition udolink: udolinks){
		
						if (new String("ObjectPropertyPointUDO").equals(udolink.associatedUdo.getStringUserAttribute("UDOtype", -1))){
							System.out.println("ObjectPropertyPointUDO: "+"EdgeObject "+edgeObject.hashCode() +" is linked to UDO"+udolink.associatedUdo.hashCode()+" with UDOtype: "+udolink.associatedUdo.getStringUserAttribute("UDOtype", -1));
						LinkDefinition[] links = udolink.associatedUdo.getLinks(UserDefinedObject.LinkType.TYPE1);
						
						System.out.println("link[0]"+links[0].associatedObject.hashCode()+"/link[1] "+ links[1].associatedObject.hashCode());
						}
						if (new String("NormalDistributionUDO").equals((udolink.associatedUdo.getStringUserAttribute("UDOtype", -1)))){
							System.out.println("NormalDistributionUDO: "+"EdgeObject "+edgeObject.hashCode() +" is linked to UDO"+udolink.associatedUdo.hashCode()+" with UDOtype: "+udolink.associatedUdo.getStringUserAttribute("UDOtype", -1));
						}
		

	}
			}
		}
	}
}
}
}
}