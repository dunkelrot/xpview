//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.03.11 at 08:37:52 PM CET 
//


package org.dexpi.xpview.model.io.xmplant;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="Pcurve" type="{}Pcurve"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pcurve"
})
@XmlRootElement(name = "OuterBoundary")
public class OuterBoundary {

    @XmlElement(name = "Pcurve")
    protected Pcurve pcurve;

    /**
     * Gets the value of the pcurve property.
     * 
     * @return
     *     possible object is
     *     {@link Pcurve }
     *     
     */
    public Pcurve getPcurve() {
        return pcurve;
    }

    /**
     * Sets the value of the pcurve property.
     * 
     * @param value
     *     allowed object is
     *     {@link Pcurve }
     *     
     */
    public void setPcurve(Pcurve value) {
        this.pcurve = value;
    }

}