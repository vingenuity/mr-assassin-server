package us.mrassassin.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Position {

	private Integer XPos;
	private Integer YPos;
	/**
	 * @return the yPos
	 */
	@XmlElement(nillable = true)
	public Integer getYPos() {
		return YPos;
	}
	/**
	 * @param yPos the yPos to set
	 */
	public void setYPos(Integer yPos) {
		YPos = yPos;
	}
	/**
	 * @return the xPos
	 */
	@XmlElement(nillable = true)
	public Integer getXPos() {
		return XPos;
	}
	/**
	 * @param xPos the xPos to set
	 */
	public void setXPos(Integer xPos) {
		XPos = xPos;
	}
	
	@Override
	public String toString(){
		return "Position [XPos=" + XPos + ", YPOS=" + YPos + "]";
	}
	
}
