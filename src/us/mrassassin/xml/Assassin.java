package us.mrassassin.xml;

import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import com.google.appengine.api.datastore.Key;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;

import javax.persistence.OneToOne;
import javax.persistence.Embedded;
import javax.persistence.OneToMany;

@XmlRootElement
@Entity
public class Assassin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Key key;
	/**
	 * @return the key
	 */
	public Key getKey() {
		return key;
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(Key key) {
		this.key = key;
	}
	String tag;
	Integer money;
	//Position position;
	Float lat;
	Float lon;
	String MACAddr;
	List<String> following;
	String target;
	Integer bounty;
	
	
	
	/**
	 * @return the tag
	 */
	@XmlElement()
	public String getTag() {
		return tag;
	}
	/**
	 * @param tag the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}
	/**
	 * @return the score
	 */
	@XmlElement()
	public Integer getMoney() {
		return money;
	}
	/**
	 * @param score the score to set
	 */
	public void setMoney(Integer money) {
		this.money = money;
	}
/*	*//**
	 * @return the position
	 *//*
	@XmlElement()
	@OneToOne(fetch=FetchType.EAGER)
	public Position getPosition() {
		return position;
	}
	*//**
	 * @param position the position to set
	 *//*
	public void setPosition(Position position) {
		this.position = position;
	}*/
	/**
	 * @return the following
	 */
	@XmlElement()
	@OneToMany(fetch=FetchType.EAGER)
	public List<String> getFollowing() {
		return following;
	}
	/**
	 * @param following the following to set
	 */	
	public void setFollowing(List<String> following) {
		this.following = following;
	}
	/**
	 * @return the target
	 */
	@XmlElement()
	public String getTarget() {
		return target;
	}
	/**
	 * @param target the target to set
	 */
	public void setTarget(String target) {
		this.target = target;
	}
	/**
	 * @return the mACAddr
	 */
	@XmlElement(name="macaddr")
	public String getMACAddr() {
		return MACAddr;
	}
	/**
	 * @param mACAddr the mACAddr to set
	 */
	public void setMACAddr(String mACAddr) {
		MACAddr = mACAddr;
	}
	/**
	 * @return the bounty
	 */
	@XmlElement()
	public Integer getBounty() {
		return bounty;
	}
	/**
	 * @param bounty the bounty to set
	 */
	public void setBounty(Integer bounty) {
		this.bounty = bounty;
	}
	/**
	 * @return the lat
	 */
	public Float getLat() {
		return lat;
	}
	/**
	 * @param lat the lat to set
	 */
	public void setLat(Float lat) {
		this.lat = lat;
	}
	/**
	 * @return the lon
	 */
	public Float getLon() {
		return lon;
	}
	/**
	 * @param lon the lon to set
	 */
	public void setLon(Float lon) {
		this.lon = lon;
	}
}
