package us.mrassassin.xml;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {
	public ObjectFactory(){}
	
	public Position createPosition(){
		return new Position();
	}
	
	public Assassin createAssassin(){
		return new Assassin();
	}

}
