//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.03.11 at 08:37:52 PM CET 
//


package org.dexpi.xpview.model.io.xmplant;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="BasisSurface" type="{}BsplineSurface"/>
 *         &lt;element ref="{}OuterBoundary" minOccurs="0"/>
 *         &lt;element ref="{}InnerBoundary" maxOccurs="unbounded" minOccurs="0"/>
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
    "basisSurface",
    "outerBoundary",
    "innerBoundary"
})
public class CurveBoundedSurface {

    @XmlElement(name = "BasisSurface", required = true)
    protected BsplineSurface basisSurface;
    @XmlElement(name = "OuterBoundary")
    protected OuterBoundary outerBoundary;
    @XmlElement(name = "InnerBoundary")
    protected List<InnerBoundary> innerBoundary;

    /**
     * Gets the value of the basisSurface property.
     * 
     * @return
     *     possible object is
     *     {@link BsplineSurface }
     *     
     */
    public BsplineSurface getBasisSurface() {
        return basisSurface;
    }

    /**
     * Sets the value of the basisSurface property.
     * 
     * @param value
     *     allowed object is
     *     {@link BsplineSurface }
     *     
     */
    public void setBasisSurface(BsplineSurface value) {
        this.basisSurface = value;
    }

    /**
     * Gets the value of the outerBoundary property.
     * 
     * @return
     *     possible object is
     *     {@link OuterBoundary }
     *     
     */
    public OuterBoundary getOuterBoundary() {
        return outerBoundary;
    }

    /**
     * Sets the value of the outerBoundary property.
     * 
     * @param value
     *     allowed object is
     *     {@link OuterBoundary }
     *     
     */
    public void setOuterBoundary(OuterBoundary value) {
        this.outerBoundary = value;
    }

    /**
     * Gets the value of the innerBoundary property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the innerBoundary property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInnerBoundary().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InnerBoundary }
     * 
     * 
     */
    public List<InnerBoundary> getInnerBoundary() {
        if (innerBoundary == null) {
            innerBoundary = new ArrayList<InnerBoundary>();
        }
        return this.innerBoundary;
    }

}
