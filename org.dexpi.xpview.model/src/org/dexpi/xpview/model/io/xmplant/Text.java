//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.03.11 at 08:37:52 PM CET 
//


package org.dexpi.xpview.model.io.xmplant;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
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
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded">
 *         &lt;element ref="{}Presentation"/>
 *         &lt;element ref="{}Extent"/>
 *         &lt;element ref="{}Position"/>
 *         &lt;element name="String" type="{}String" minOccurs="0"/>
 *         &lt;element ref="{}GenericAttributes" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/choice>
 *       &lt;attribute name="NumLines" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="String" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Font" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Justification" default="LeftBottom">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="LeftTop"/>
 *             &lt;enumeration value="LeftCenter"/>
 *             &lt;enumeration value="LeftBottom"/>
 *             &lt;enumeration value="CenterTop"/>
 *             &lt;enumeration value="CenterCenter"/>
 *             &lt;enumeration value="CenterBottom"/>
 *             &lt;enumeration value="RightTop"/>
 *             &lt;enumeration value="RightCenter"/>
 *             &lt;enumeration value="RightBottom"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="Width" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="Height" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="TextAngle" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="SlantAngle" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="ItemID" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
 *       &lt;attribute name="Set" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="DependantAttribute" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "presentationOrExtentOrPosition"
})
@XmlRootElement(name = "Text")
public class Text {

    @XmlElements({
        @XmlElement(name = "Presentation", type = Presentation.class),
        @XmlElement(name = "Extent", type = Extent.class),
        @XmlElement(name = "Position", type = Position.class),
        @XmlElement(name = "String", type = org.dexpi.xpview.model.io.xmplant.String.class),
        @XmlElement(name = "GenericAttributes", type = GenericAttributes.class)
    })
    protected List<Object> presentationOrExtentOrPosition;
    @XmlAttribute(name = "NumLines")
    protected BigInteger numLines;
    @XmlAttribute(name = "String")
    protected java.lang.String string;
    @XmlAttribute(name = "Font", required = true)
    protected java.lang.String font;
    @XmlAttribute(name = "Justification")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected java.lang.String justification;
    @XmlAttribute(name = "Width", required = true)
    protected double width;
    @XmlAttribute(name = "Height", required = true)
    protected double height;
    @XmlAttribute(name = "TextAngle")
    protected java.lang.Double textAngle;
    @XmlAttribute(name = "SlantAngle")
    protected java.lang.Double slantAngle;
    @XmlAttribute(name = "ItemID")
    @XmlIDREF
    @XmlSchemaType(name = "IDREF")
    protected Object itemID;
    @XmlAttribute(name = "Set")
    protected java.lang.String set;
    @XmlAttribute(name = "DependantAttribute")
    protected java.lang.String dependantAttribute;

    /**
     * Gets the value of the presentationOrExtentOrPosition property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the presentationOrExtentOrPosition property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPresentationOrExtentOrPosition().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Presentation }
     * {@link Extent }
     * {@link Position }
     * {@link org.dexpi.xpview.model.io.xmplant.String }
     * {@link GenericAttributes }
     * 
     * 
     */
    public List<Object> getPresentationOrExtentOrPosition() {
        if (presentationOrExtentOrPosition == null) {
            presentationOrExtentOrPosition = new ArrayList<Object>();
        }
        return this.presentationOrExtentOrPosition;
    }

    /**
     * Gets the value of the numLines property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumLines() {
        return numLines;
    }

    /**
     * Sets the value of the numLines property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumLines(BigInteger value) {
        this.numLines = value;
    }

    /**
     * Gets the value of the string property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getString() {
        return string;
    }

    /**
     * Sets the value of the string property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setString(java.lang.String value) {
        this.string = value;
    }

    /**
     * Gets the value of the font property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getFont() {
        return font;
    }

    /**
     * Sets the value of the font property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setFont(java.lang.String value) {
        this.font = value;
    }

    /**
     * Gets the value of the justification property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getJustification() {
        if (justification == null) {
            return "LeftBottom";
        } else {
            return justification;
        }
    }

    /**
     * Sets the value of the justification property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setJustification(java.lang.String value) {
        this.justification = value;
    }

    /**
     * Gets the value of the width property.
     * 
     */
    public double getWidth() {
        return width;
    }

    /**
     * Sets the value of the width property.
     * 
     */
    public void setWidth(double value) {
        this.width = value;
    }

    /**
     * Gets the value of the height property.
     * 
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets the value of the height property.
     * 
     */
    public void setHeight(double value) {
        this.height = value;
    }

    /**
     * Gets the value of the textAngle property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.Double }
     *     
     */
    public java.lang.Double getTextAngle() {
        return textAngle;
    }

    /**
     * Sets the value of the textAngle property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.Double }
     *     
     */
    public void setTextAngle(java.lang.Double value) {
        this.textAngle = value;
    }

    /**
     * Gets the value of the slantAngle property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.Double }
     *     
     */
    public java.lang.Double getSlantAngle() {
        return slantAngle;
    }

    /**
     * Sets the value of the slantAngle property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.Double }
     *     
     */
    public void setSlantAngle(java.lang.Double value) {
        this.slantAngle = value;
    }

    /**
     * Gets the value of the itemID property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getItemID() {
        return itemID;
    }

    /**
     * Sets the value of the itemID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setItemID(Object value) {
        this.itemID = value;
    }

    /**
     * Gets the value of the set property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getSet() {
        return set;
    }

    /**
     * Sets the value of the set property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setSet(java.lang.String value) {
        this.set = value;
    }

    /**
     * Gets the value of the dependantAttribute property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getDependantAttribute() {
        return dependantAttribute;
    }

    /**
     * Sets the value of the dependantAttribute property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setDependantAttribute(java.lang.String value) {
        this.dependantAttribute = value;
    }

}
