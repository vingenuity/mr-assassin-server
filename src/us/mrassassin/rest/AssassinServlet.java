package us.mrassassin.rest;

import java.util.Random;
import us.mrassassin.c2dm.*;

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

import us.mrassassin.c2dm.MessageUtil;
import us.mrassassin.c2dm.SecureStorage;
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

@Path("/")
public class AssassinServlet {
	@GET
	@Path("get/assassins")
	@Produces(MediaType.APPLICATION_XML)
	public List<Assassin> test(){		
		//ObjectMapper map = new ObjectMapper();
		
		//Position ps = new Position();
		//ps.setYPos(1);
		//ps.setXPos(2);
		EntityManager em = EMF.get().createEntityManager();
		String s = "SELECT x FROM Assassin x";
		List<Assassin> ls = new ArrayList<Assassin>();
		try
		{
			Query q = em.createQuery(s);
			List<Assassin> res = (List<Assassin>)q.getResultList();
			for(int k = 0; k < res.size(); k++)
			{				
				ls.add(res.get(k));
			}
		}catch(Exception e)
		{
			
		}
		
		return ls;
	}
	
	@POST
	@Path("get/leaderboard/bymoney")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.TEXT_PLAIN)
	public List<Assassin> getLeadersByMoney(String num){
		EntityManager em = EMF.get().createEntityManager();
		String s = "SELECT x FROM Assassin x ORDER BY money DESC LIMIT ";
		s.concat(num);
		List<Assassin> ls = new ArrayList<Assassin>();
		try
		{
			int limit = Integer.parseInt(num);
			Query q = em.createQuery(s);
			List<Assassin> res = (List<Assassin>)q.getResultList();
			for(int k = 0; k < limit; k++)
			{				
				ls.add(res.get(k));
			}
		}catch(Exception e)
		{
			
		}
		
		return ls;
	}
	
	@POST
	@Path("get/leaderboard/bybounty")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.TEXT_PLAIN)
	public List<Assassin> getLeadersByBounty(String num){
		EntityManager em = EMF.get().createEntityManager();
		String s = "SELECT x FROM Assassin x ORDER BY bounty DESC LIMIT ";
		s.concat(num);
		List<Assassin> ls = new ArrayList<Assassin>();
		try
		{
			int limit = Integer.parseInt(num);
			Query q = em.createQuery(s);
			List<Assassin> res = (List<Assassin>)q.getResultList();
			for(int k = 0; k < limit; k++)
			{				
				ls.add(res.get(k));
			}
		}catch(Exception e)
		{
			
		}
		
		return ls;
	}
	
	@POST
	@Path("get/leaderboard/bykills")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.TEXT_PLAIN)
	public List<Assassin> getLeadersByKills(String num){
		EntityManager em = EMF.get().createEntityManager();
		String s = "SELECT x FROM Assassin x ORDER BY kills DESC LIMIT ";
		s.concat(num);
		List<Assassin> ls = new ArrayList<Assassin>();
		try
		{
			int limit = Integer.parseInt(num);
			Query q = em.createQuery(s);
			List<Assassin> res = (List<Assassin>)q.getResultList();
			for(int k = 0; k < limit; k++)
			{				
				ls.add(res.get(k));
			}
		}catch(Exception e)
		{
			
		}
		
		return ls;
	}
	
	@POST
	@Path("get/assassins")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes({MediaType.TEXT_PLAIN})
	public Assassin getAssassin(String tag){
		Assassin ret;
		EntityManager em = EMF.get().createEntityManager();
		try{
			Query q = em.createQuery("SELECT x from Assassin x where x.tag = \"".concat(tag).concat("\""));
			ret = (Assassin)q.getSingleResult();

			//em.persist(ret);
		}
		finally{
			em.close();
		}
		
		return ret;
	}
	
	@POST
	@Path("get/target")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes({MediaType.TEXT_PLAIN})
	public List<Assassin> getTarget(String attackerTag){
		//String targetTag = getAssassin(attackerTag).getTarget();
		//return getAssassin(targetTag);
		
		List<Assassin> ret = new ArrayList<Assassin>();
		ret.add(getAssassin(attackerTag));
		ret.add(getAssassin(ret.get(0).getTarget()));
		
		return ret;
	}
	
	@POST
	@Path("update/assassin")
	@Consumes({MediaType.APPLICATION_XML})
	public void updateAssassin(Assassin a)
	{
		EntityManager em = EMF.get().createEntityManager();
		try
		{		
			Query q = em.createQuery("SELECT x from Assassin x where x.tag = \"".concat(a.getTag()).concat("\""));
			Assassin p = (Assassin)q.getSingleResult();
			if(a.getBounty() != null)
			{
				if(a.getBounty() <= 300){
					a.setBounty(300);
				}
				p.setBounty(a.getBounty());
			}
			
			if(a.getMACAddr() != null)
			{
				p.setMACAddr(a.getMACAddr());
			}
			
			if(a.getMoney() != null)
			{
				if(a.getMoney() < 100){
					a.setMoney(100);
				}
				p.setMoney(a.getMoney());
			}
			
			if(a.getLon() != null)
			{
				p.setLon(a.getLon());
			}
			
			if(a.getLat() != null)
			{
				p.setLat(a.getLat());
			}
			
			if(a.getMACAddr() != null)
			{
				p.setMACAddr(a.getMACAddr());
			}
			if(a.getKills() != null)
			{
				p.setKills(a.getKills());
			}
			if(a.getRegID() != null)
			{
				p.setRegID(a.getRegID());
			}
			
			em.persist(p);
		}
		finally
		{
			em.close();
		}
		
	}

	@POST
	@Path("get/newtarget")
	@Consumes({MediaType.APPLICATION_XML})
	@Produces(MediaType.APPLICATION_XML)
	public Assassin getNewTarget(Assassin player)
	{
		EntityManager em = EMF.get().createEntityManager();
		Assassin ret = null;
		try
		{		
			Query q = em.createQuery("SELECT x from Assassin x where x.tag = \"".concat(player.getTag()).concat("\""));
			Assassin p = (Assassin)q.getSingleResult();
			
			Query newTargetQ;
			
			if(p.getTarget() == null)
			{
				newTargetQ = em.createQuery("SELECT x from Assassin x where x.tag <> \"".concat(player.getTag()).concat("\""));
			}
			else
			{				
				newTargetQ = em.createQuery("SELECT x from Assassin x where x.tag <> \"".concat(player.getTag()).concat("\" and x.tag <> \"").concat(p.getTarget()).concat("\""));
			}
			
			 
			@SuppressWarnings("unchecked")
			List<Assassin> list = newTargetQ.getResultList();

			int max = 0;
			for(int i = 0; i < list.size(); i++)
			{
				if(list.get(i).getBounty() != null)
					max += list.get(i).getBounty();
			}
			Random generator = new Random();
			
			
			int select = generator.nextInt(max);
			int temp = 0;
				
			for(int i = 0; i < list.size(); i++)
			{				
				if(list.get(i).getBounty() != null)
					temp += list.get(i).getBounty();
				if(temp >= select){
					ret = list.get(i);
					break;
				}
			}			
			if(ret != null)
			{		
				p.setTarget(ret.getTag());
				em.merge(p);
				return ret;
			}
		}
		finally
		{
			em.close();	
		}
		Assassin error = new Assassin();
		error.setTag("Error");
		return error;
	}
	
	@POST
	@Path("add/assassin")
	@Consumes({MediaType.APPLICATION_XML})
	@Produces(MediaType.TEXT_PLAIN)
	public String addAssassin(Assassin toAdd) throws Exception{
		EntityManager em = EMF.get().createEntityManager();
		try{ //note: when debugging, eclipse will highlight this line and execute the next two lines
			@SuppressWarnings("unused")
			Assassin temp = null;
			Query q = em.createQuery("SELECT x from Assassin x where x.tag = \"".concat(toAdd.getTag()).concat("\""));
			@SuppressWarnings("unchecked")
			List<Assassin> ls = q.getResultList();
			if(ls.size() > 0)
			{
				throw new Exception("Duplicate tag!");
			}
			if(toAdd.getMoney() == null)
			{							
				toAdd.setMoney(500);
			}
			if(toAdd.getBounty() == null)
			{
				toAdd.setBounty(500);
			}
			if(toAdd.getTarget() == null){
				toAdd.setTarget(getNewTarget(toAdd).getTag());
			}
			toAdd.setKills(0);
			em.persist(toAdd);
		}
		catch(Exception e){
			return "Failed to add ".concat(toAdd.getTag()).concat("! ").concat(e.getMessage());
		}
		finally{
				em.close();
		}
		
		return "Succuessfully added ".concat(toAdd.getTag()).concat("!");
	}
	
	@POST
	@Path("update/kill")
	@Consumes({MediaType.APPLICATION_XML})
	@Produces(MediaType.APPLICATION_XML)
	public Assassin confirmKill(Assassin murderer)
	{
		EntityManager em = null;
		try
		{
			em = EMF.get().createEntityManager();
			Query query = em.createQuery("SELECT x FROM Assassin x WHERE x.tag = \"".concat(murderer.getTag()).concat("\""));
			List<Assassin> a = query.getResultList();
			//Assassin l = getAssassin(murderer.getTag());
			if(a.size() > 0) 
			//if(l != null)
			{		
				
				Query tQ = em.createQuery("SELECT x FROM Assassin x WHERE x.tag = \"" + a.get(0).getTarget() + "\"");
				Assassin killed = (Assassin)tQ.getSingleResult();
				
				int response;
				//Notify of death
				if(killed.getRegID() != null)
				{
					try
					{
						response = MessageUtil.sendMessage(AuthenticationUtil.getToken(SecureStorage.USER, SecureStorage.PASSWORD), killed.getRegID(), "killed");
					}
					catch(Exception e)
					{
						
					}
				}
				
				Assassin l = a.get(0);
				l.setMoney(l.getMoney() + killed.getBounty());
				if(l.getKills() == null)
				{
					l.setKills(0);
				}
				
				l.setKills(l.getKills() + 1);
				if(killed.getBounty() > 200)
				{
					killed.setBounty(killed.getBounty() - 200);	
				}
				else
				{
					killed.setBounty(200);
				}
				
				em.persist(l);
				em.persist(killed);
				//TODO: and push code here
				em.close();
				return getNewTarget(l);
			}
		}
		finally
		{
			if(em.isOpen())
			{
				em.close();
			}
		}
		Assassin failure = new Assassin();
		failure.setTag("failure");
		return failure;
	}
	
	@POST
	@Path("update/position")
	@Produces(MediaType.APPLICATION_XML)
	public Assassin updatePosition(Assassin p)
	{
		EntityManager em = null;
		try
		{			
			em = EMF.get().createEntityManager();
			Query q = em.createQuery("SELECT x FROM Assassin x WHERE x.tag = \"".concat(p.getTag()).concat("\"") );
			Assassin res = (Assassin)q.getSingleResult();
			res.setLat(p.getLat());
			res.setLon(p.getLon());
			
			
			
			Query t = em.createQuery("SELECT x FROM Assassin x WHERE x.tag = \"".concat(res.getTarget()).concat("\"") );
			Assassin ret = (Assassin)t.getSingleResult();
			
			//Check if we're within 30 m for warning
			if(ret.getRegID() != null)
			{
				double latitude = ret.getLat() - res.getLat();
				double longitude = ret.getLon() - res.getLon();
				
				latitude /= 57.29577951;
				longitude /= 57.29577951;
				
				double med = Math.pow(Math.sin(latitude / 2.0), 2) + Math.cos(res.getLat()) * Math.cos(ret.getLat()) * Math.pow(Math.sin(longitude / 2.0), 2);
				
				int response;
				try
				{			
					if(2 * Math.atan2(Math.sqrt(med), Math.sqrt(1 - med)) * 6372.8 * 1000.0 < 30.0)
					{
						//Within 30 m so send warning
						response = MessageUtil.sendMessage(AuthenticationUtil.getToken(SecureStorage.USER, SecureStorage.PASSWORD), ret.getRegID(), "warning");
					}
				}catch(Exception e)
				{				
				
				}
			}
			
			em.persist(res);
			return ret;
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
