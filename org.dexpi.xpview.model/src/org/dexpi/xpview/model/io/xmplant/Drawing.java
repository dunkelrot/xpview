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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
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
 *         &lt;element ref="{}Presentation"/>
 *         &lt;element ref="{}Extent"/>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element ref="{}Component"/>
 *           &lt;element ref="{}Curve"/>
 *           &lt;element ref="{}Text"/>
 *           &lt;element ref="{}DrawingBorder"/>
 *           &lt;element ref="{}Label"/>
 *           &lt;element ref="{}InsulationSymbol"/>
 *           &lt;element ref="{}ScopeBubble"/>
 *           &lt;element ref="{}GenericAttributes"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="Name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="PID" />
 *       &lt;attribute name="Revision" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Title" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Size" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
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
    "componentOrCurveOrText"
})
@XmlRootElement(name = "Drawing")
public class Drawing {

    @XmlElement(name = "Presentation", required = true)
    protected Presentation presentation;
    @XmlElement(name = "Extent", required = true)
    protected Extent extent;
    @XmlElementRefs({
        @XmlElementRef(name = "Text", type = Text.class, required = false),
        @XmlElementRef(name = "GenericAttributes", type = GenericAttributes.class, required = false),
        @XmlElementRef(name = "Curve", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "InsulationSymbol", type = InsulationSymbol.class, required = false),
        @XmlElementRef(name = "DrawingBorder", type = DrawingBorder.class, required = false),
        @XmlElementRef(name = "ScopeBubble", type = ScopeBubble.class, required = false),
        @XmlElementRef(name = "Label", type = Label.class, required = false),
        @XmlElementRef(name = "Component", type = Component.class, required = false)
    })
    protected List<Object> componentOrCurveOrText;
    @XmlAttribute(name = "Name", required = true)
    protected java.lang.String name;
    @XmlAttribute(name = "Type", required = true)
    protected java.lang.String type;
    @XmlAttribute(name = "Revision")
    protected java.lang.String revision;
    @XmlAttribute(name = "Title")
    protected java.lang.String title;
    @XmlAttribute(name = "Size")
    protected java.lang.String size;

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
     * Gets the value of the componentOrCurveOrText property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the componentOrCurveOrText property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComponentOrCurveOrText().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link Line }{@code >}
     * {@link Text }
     * {@link JAXBElement }{@code <}{@link Curve }{@code >}
     * {@link JAXBElement }{@code <}{@link PolyLine }{@code >}
     * {@link JAXBElement }{@code <}{@link Circle }{@code >}
     * {@link InsulationSymbol }
     * {@link DrawingBorder }
     * {@link JAXBElement }{@code <}{@link Shape }{@code >}
     * {@link Component }
     * {@link Label }
     * {@link GenericAttributes }
     * {@link JAXBElement }{@code <}{@link TrimmedCurve }{@code >}
     * {@link JAXBElement }{@code <}{@link CompositeCurve }{@code >}
     * {@link ScopeBubble }
     * {@link JAXBElement }{@code <}{@link BsplineCurve }{@code >}
     * {@link JAXBElement }{@code <}{@link Ellipse }{@code >}
     * 
     * 
     */
    public List<Object> getComponentOrCurveOrText() {
        if (componentOrCurveOrText == null) {
            componentOrCurveOrText = new ArrayList<Object>();
        }
        return this.componentOrCurveOrText;
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
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getType() {
        if (type == null) {
            return "PID";
        } else {
            return type;
        }
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
     * Gets the value of the revision property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getRevision() {
        return revision;
    }

    /**
     * Sets the value of the revision property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setRevision(java.lang.String value) {
        this.revision = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setTitle(java.lang.String value) {
        this.title = value;
    }

    /**
     * Gets the value of the size property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getSize() {
        return size;
    }

    /**
     * Sets the value of the size property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setSize(java.lang.String value) {
        this.size = value;
    }

}
