package us.mrassassin.rest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import common.EMF;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import us.mrassassin.xml.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringWriter;
import javax.xml.bind.JAXBElement;
import java.io.StringReader;

//Imports for Google Datastorage
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import java.util.ArrayList;
import java.util.List;




@Path("/assassin")
public class AssassinServlet {
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<Assassin> test(){		
		//ObjectMapper map = new ObjectMapper();
		
		Position ps = new Position();
		ps.setYPos(1);
		ps.setXPos(2);
		EntityManager em = EMF.get().createEntityManager();
		String s = "SELECT x FROM Assassin x";
		List<Assassin> ls = new ArrayList<Assassin>();
		try
		{
			Query q = em.createQuery(s);
			ls = q.getResultList();
		}catch(Exception e)
		{
			
		}
		
		return ls;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_XML})
	public void addAssassin(Assassin toAdd)
	{
		EntityManager em = EMF.get().createEntityManager();
		try{		
		em.persist(toAdd);
		}
		catch(Exception e)
		{
			Integer brreak = 1;
		}
		finally
		{
			em.close();
		}
	}
	
	private static String objToXML(Object obj){
		StringWriter sw = new StringWriter();
		String result = "";
		
		try{
		JAXBContext jc = JAXBContext.newInstance("us.mrassassin.xml");
		
		Marshaller m = jc.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(obj, sw);
		result = sw.toString();
		}
		catch(Exception e)
		{
			
		}
		
		return result;
	}
}
