//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.03.11 at 08:37:52 PM CET 
//


package com.basf.xpview.model.io.xmplant;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{}Curve">
 *       &lt;sequence>
 *         &lt;element ref="{}Presentation"/>
 *         &lt;element ref="{}Extent"/>
 *         &lt;element ref="{}Coordinate" maxOccurs="unbounded"/>
 *         &lt;element ref="{}GenericAttributes" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="NumPoints" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="Filled">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="Solid"/>
 *             &lt;enumeration value="Hatch"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "presentation",
    "extent",
    "coordinate",
    "genericAttributes"
})
public class Shape
    extends Curve
{

    @XmlElement(name = "Presentation", required = true)
    protected Presentation presentation;
    @XmlElement(name = "Extent", required = true)
    protected Extent extent;
    @XmlElement(name = "Coordinate", required = true)
    protected List<Coordinate> coordinate;
    @XmlElement(name = "GenericAttributes")
    protected List<GenericAttributes> genericAttributes;
    @XmlAttribute(name = "NumPoints", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected java.lang.String numPoints;
    @XmlAttribute(name = "Filled")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected java.lang.String filled;

    /**
     * Gets the value of the presentation property.
     * 
     * @return
     *     possible object is
     *     {@link Presentation }
     *     
     */
    public Presentation getPresentation() {
        return presentation;
    }

    /**
     * Sets the value of the presentation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Presentation }
     *     
     */
    public void setPresentation(Presentation value) {
        this.presentation = value;
    }

    /**
     * Gets the value of the extent property.
     * 
     * @return
     *     possible object is
     *     {@link Extent }
     *     
     */
    public Extent getExtent() {
        return extent;
    }

    /**
     * Sets the value of the extent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Extent }
     *     
     */
    public void setExtent(Extent value) {
        this.extent = value;
    }

    /**
     * Gets the value of the coordinate property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the coordinate property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCoordinate().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Coordinate }
     * 
     * 
     */
    public List<Coordinate> getCoordinate() {
        if (coordinate == null) {
            coordinate = new ArrayList<Coordinate>();
        }
        return this.coordinate;
    }

    /**
     * Gets the value of the genericAttributes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the genericAttributes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGenericAttributes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GenericAttributes }
     * 
     * 
     */
    public List<GenericAttributes> getGenericAttributes() {
        if (genericAttributes == null) {
            genericAttributes = new ArrayList<GenericAttributes>();
        }
        return this.genericAttributes;
    }

    /**
     * Gets the value of the numPoints property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getNumPoints() {
        return numPoints;
    }

    /**
     * Sets the value of the numPoints property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setNumPoints(java.lang.String value) {
        this.numPoints = value;
    }

    /**
     * Gets the value of the filled property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getFilled() {
        return filled;
    }

    /**
     * Sets the value of the filled property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setFilled(java.lang.String value) {
        this.filled = value;
    }

}
