//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.03.11 at 08:37:52 PM CET 
//


package org.dexpi.xpview.model.io.xmplant;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 * 			An Association references other objects either by ID, Name or Tag attribute. 
 * 			If ID elements are persistent (see PlantModel element) then ID references may be to items not in this file.
 * 			
 * 
 * <p>Java class for Association complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Association">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="Type" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *             &lt;enumeration value="is about"/>
 *             &lt;enumeration value="is a subject of"/>
 *             &lt;enumeration value="is associated with"/>
 *             &lt;enumeration value="refers to"/>
 *             &lt;enumeration value="is referenced in"/>
 *             &lt;enumeration value="describes"/>
 *             &lt;enumeration value="is described in"/>
 *             &lt;enumeration value="indirectly describes"/>
 *             &lt;enumeration value="is indirectly described in"/>
 *             &lt;enumeration value="defines"/>
 *             &lt;enumeration value="is defined in"/>
 *             &lt;enumeration value="indirectly defines"/>
 *             &lt;enumeration value="is indirectly defined in"/>
 *             &lt;enumeration value="is connected to"/>
 *             &lt;enumeration value="is logically connected to"/>
 *             &lt;enumeration value="is involved with role in"/>
 *             &lt;enumeration value="is an activity with role involving"/>
 *             &lt;enumeration value="is fulfilled by"/>
 *             &lt;enumeration value="fulfills"/>
 *             &lt;enumeration value="is a part of"/>
 *             &lt;enumeration value="is an assembly including"/>
 *             &lt;enumeration value="is a component of"/>
 *             &lt;enumeration value="is an composition including"/>
 *             &lt;enumeration value="is an element of"/>
 *             &lt;enumeration value="is a collection including"/>
 *             &lt;enumeration value="is identified by"/>
 *             &lt;enumeration value="is an identifier for"/>
 *             &lt;enumeration value="is a template including"/>
 *             &lt;enumeration value="is a component of template"/>
 *             &lt;enumeration value="is a template that refers to"/>
 *             &lt;enumeration value="is a reference in template"/>
 *             &lt;enumeration value="is classified as"/>
 *             &lt;enumeration value="is incidentally classified as"/>
 *             &lt;enumeration value="has dataset"/>
 *             &lt;enumeration value="is a dataset of"/>
 *             &lt;enumeration value="is a comment referring to"/>
 *             &lt;enumeration value="is referenced in comment"/>
 *             &lt;enumeration value="has document"/>
 *             &lt;enumeration value="is a document for"/>
 *             &lt;enumeration value="has dataset"/>
 *             &lt;enumeration value="is the location of"/>
 *             &lt;enumeration value="is located in"/>
 *             &lt;enumeration value="is upstream of"/>
 *             &lt;enumeration value="is downstream of"/>
 *             &lt;enumeration value="is the responsibility of"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="ItemID" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
 *       &lt;attribute name="TagName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="URI" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       &lt;attribute name="Context" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Association")
public class Association {

    @XmlAttribute(name = "Type", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected java.lang.String type;
    @XmlAttribute(name = "ItemID")
    @XmlIDREF
    @XmlSchemaType(name = "IDREF")
    protected Object itemID;
    @XmlAttribute(name = "TagName")
    protected java.lang.String tagName;
    @XmlAttribute(name = "Name")
    protected java.lang.String name;
    @XmlAttribute(name = "URI")
    @XmlSchemaType(name = "anyURI")
    protected java.lang.String uri;
    @XmlAttribute(name = "Context")
    protected java.lang.String context;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setType(java.lang.String value) {
        this.type = value;
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
     * Gets the value of the tagName property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getTagName() {
        return tagName;
    }

    /**
     * Sets the value of the tagName property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setTagName(java.lang.String value) {
        this.tagName = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setName(java.lang.String value) {
        this.name = value;
    }

    /**
     * Gets the value of the uri property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getURI() {
        return uri;
    }

    /**
     * Sets the value of the uri property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setURI(java.lang.String value) {
        this.uri = value;
    }

    /**
     * Gets the value of the context property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getContext() {
        return context;
    }

    /**
     * Sets the value of the context property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setContext(java.lang.String value) {
        this.context = value;
    }

}
