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
 *       &lt;sequence>
 *         &lt;element ref="{}PLocation"/>
 *         &lt;element ref="{}PAxis"/>
 *         &lt;element ref="{}PReference"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pLocation",
    "pAxis",
    "pReference"
})
@XmlRootElement(name = "PPosition")
public class PPosition {

    @XmlElement(name = "PLocation", required = true)
    protected PLocation pLocation;
    @XmlElement(name = "PAxis", required = true)
    protected PAxis pAxis;
    @XmlElement(name = "PReference", required = true)
    protected PReference pReference;

    /**
     * Gets the value of the pLocation property.
     * 
     * @return
     *     possible object is
     *     {@link PLocation }
     *     
     */
    public PLocation getPLocation() {
        return pLocation;
    }

    /**
     * Sets the value of the pLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link PLocation }
     *     
     */
    public void setPLocation(PLocation value) {
        this.pLocation = value;
    }

    /**
     * Gets the value of the pAxis property.
     * 
     * @return
     *     possible object is
     *     {@link PAxis }
     *     
     */
    public PAxis getPAxis() {
        return pAxis;
    }

    /**
     * Sets the value of the pAxis property.
     * 
     * @param value
     *     allowed object is
     *     {@link PAxis }
     *     
     */
    public void setPAxis(PAxis value) {
        this.pAxis = value;
    }

    /**
     * Gets the value of the pReference property.
     * 
     * @return
     *     possible object is
     *     {@link PReference }
     *     
     */
    public PReference getPReference() {
        return pReference;
    }

    /**
     * Sets the value of the pReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link PReference }
     *     
     */
    public void setPReference(PReference value) {
        this.pReference = value;
    }

}
