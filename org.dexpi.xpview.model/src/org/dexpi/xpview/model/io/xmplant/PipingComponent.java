//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.03.11 at 08:37:52 PM CET 
//


package org.dexpi.xpview.model.io.xmplant;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;


/**
 * PipingComponent covers all bought components. For Complex components they may have subcomponents that are either mechanical or piping. Component is used for mechanical items such as Valve Topworks.
 * 		
 * 
 * <p>Java class for PipingComponent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PipingComponent">
 *   &lt;complexContent>
 *     &lt;extension base="{}PlantItem">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element ref="{}PipingComponent"/>
 *         &lt;element ref="{}Component"/>
 *         &lt;element ref="{}ConnectionType" minOccurs="0"/>
 *         &lt;element ref="{}NominalDiameter" minOccurs="0"/>
 *         &lt;element ref="{}InsideDiameter" minOccurs="0"/>
 *         &lt;element ref="{}OutsideDiameter" minOccurs="0"/>
 *         &lt;element ref="{}OperatorType" minOccurs="0"/>
 *         &lt;element ref="{}WallThickness" minOccurs="0"/>
 *         &lt;element ref="{}FabricationCategory" minOccurs="0"/>
 *       &lt;/choice>
 *       &lt;attribute name="ConnectionType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Rating" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Standard" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ISOSymbol" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PipingComponent", propOrder = {
    "pipingComponentOrComponentOrConnectionType"
})
public class PipingComponent
    extends PlantItem
{

    @XmlElementRefs({
        @XmlElementRef(name = "FabricationCategory", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "WallThickness", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "NominalDiameter", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "InsideDiameter", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "OperatorType", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "OutsideDiameter", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "ConnectionType", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "Component", type = Component.class, required = false),
        @XmlElementRef(name = "PipingComponent", type = JAXBElement.class, required = false)
    })
    protected List<Object> pipingComponentOrComponentOrConnectionType;
    @XmlAttribute(name = "ConnectionType")
    protected java.lang.String connectionType;
    @XmlAttribute(name = "Rating")
    protected java.lang.String rating;
    @XmlAttribute(name = "Standard")
    protected java.lang.String standard;
    @XmlAttribute(name = "ISOSymbol")
    protected java.lang.String isoSymbol;

    /**
     * Gets the value of the pipingComponentOrComponentOrConnectionType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pipingComponentOrComponentOrConnectionType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPipingComponentOrComponentOrConnectionType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link org.dexpi.xpview.model.io.xmplant.String }{@code >}
     * {@link JAXBElement }{@code <}{@link DistanceDouble }{@code >}
     * {@link JAXBElement }{@code <}{@link DistanceString }{@code >}
     * {@link JAXBElement }{@code <}{@link DistanceDouble }{@code >}
     * {@link JAXBElement }{@code <}{@link DistanceDouble }{@code >}
     * {@link JAXBElement }{@code <}{@link org.dexpi.xpview.model.io.xmplant.String }{@code >}
     * {@link JAXBElement }{@code <}{@link PipingComponent }{@code >}
     * {@link Component }
     * {@link JAXBElement }{@code <}{@link org.dexpi.xpview.model.io.xmplant.String }{@code >}
     * 
     * 
     */
    public List<Object> getPipingComponentOrComponentOrConnectionType() {
        if (pipingComponentOrComponentOrConnectionType == null) {
            pipingComponentOrComponentOrConnectionType = new ArrayList<Object>();
        }
        return this.pipingComponentOrComponentOrConnectionType;
    }

    /**
     * Gets the value of the connectionType property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getConnectionType() {
        return connectionType;
    }

    /**
     * Sets the value of the connectionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setConnectionType(java.lang.String value) {
        this.connectionType = value;
    }

    /**
     * Gets the value of the rating property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getRating() {
        return rating;
    }

    /**
     * Sets the value of the rating property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setRating(java.lang.String value) {
        this.rating = value;
    }

    /**
     * Gets the value of the standard property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getStandard() {
        return standard;
    }

    /**
     * Sets the value of the standard property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setStandard(java.lang.String value) {
        this.standard = value;
    }

    /**
     * Gets the value of the isoSymbol property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getISOSymbol() {
        return isoSymbol;
    }

    /**
     * Sets the value of the isoSymbol property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setISOSymbol(java.lang.String value) {
        this.isoSymbol = value;
    }

}
