//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.03.11 at 08:37:52 PM CET 
//


package com.basf.xpview.model.io.xmplant;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element ref="{}UnitsOfMeasure"/>
 *       &lt;/sequence>
 *       &lt;attribute name="SchemaVersion" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="3.3.3" />
 *       &lt;attribute name="OriginatingSystem" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ModelName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Date" use="required" type="{http://www.w3.org/2001/XMLSchema}date" />
 *       &lt;attribute name="ProjectName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ProjectCode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ProjectDescription" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="CompanyName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Time" use="required" type="{http://www.w3.org/2001/XMLSchema}time" />
 *       &lt;attribute name="Is3D" use="required" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN" fixed="no" />
 *       &lt;attribute name="Units" use="required" type="{}DistanceUnitsType" />
 *       &lt;attribute name="Discipline" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="PID" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "unitsOfMeasure"
})
@XmlRootElement(name = "PlantInformation")
public class PlantInformation {

    @XmlElement(name = "UnitsOfMeasure", required = true)
    protected UnitsOfMeasure unitsOfMeasure;
    @XmlAttribute(name = "SchemaVersion", required = true)
    protected java.lang.String schemaVersion;
    @XmlAttribute(name = "OriginatingSystem", required = true)
    protected java.lang.String originatingSystem;
    @XmlAttribute(name = "ModelName")
    protected java.lang.String modelName;
    @XmlAttribute(name = "Date", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar date;
    @XmlAttribute(name = "ProjectName")
    protected java.lang.String projectName;
    @XmlAttribute(name = "ProjectCode")
    protected java.lang.String projectCode;
    @XmlAttribute(name = "ProjectDescription")
    protected java.lang.String projectDescription;
    @XmlAttribute(name = "CompanyName")
    protected java.lang.String companyName;
    @XmlAttribute(name = "Time", required = true)
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar time;
    @XmlAttribute(name = "Is3D", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    protected java.lang.String is3D;
    @XmlAttribute(name = "Units", required = true)
    protected DistanceUnitsType units;
    @XmlAttribute(name = "Discipline", required = true)
    protected java.lang.String discipline;

    /**
     * Gets the value of the unitsOfMeasure property.
     * 
     * @return
     *     possible object is
     *     {@link UnitsOfMeasure }
     *     
     */
    public UnitsOfMeasure getUnitsOfMeasure() {
        return unitsOfMeasure;
    }

    /**
     * Sets the value of the unitsOfMeasure property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitsOfMeasure }
     *     
     */
    public void setUnitsOfMeasure(UnitsOfMeasure value) {
        this.unitsOfMeasure = value;
    }

    /**
     * Gets the value of the schemaVersion property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getSchemaVersion() {
        if (schemaVersion == null) {
            return "3.3.3";
        } else {
            return schemaVersion;
        }
    }

    /**
     * Sets the value of the schemaVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setSchemaVersion(java.lang.String value) {
        this.schemaVersion = value;
    }

    /**
     * Gets the value of the originatingSystem property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getOriginatingSystem() {
        return originatingSystem;
    }

    /**
     * Sets the value of the originatingSystem property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setOriginatingSystem(java.lang.String value) {
        this.originatingSystem = value;
    }

    /**
     * Gets the value of the modelName property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getModelName() {
        return modelName;
    }

    /**
     * Sets the value of the modelName property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setModelName(java.lang.String value) {
        this.modelName = value;
    }

    /**
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate(XMLGregorianCalendar value) {
        this.date = value;
    }

    /**
     * Gets the value of the projectName property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getProjectName() {
        return projectName;
    }

    /**
     * Sets the value of the projectName property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setProjectName(java.lang.String value) {
        this.projectName = value;
    }

    /**
     * Gets the value of the projectCode property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getProjectCode() {
        return projectCode;
    }

    /**
     * Sets the value of the projectCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setProjectCode(java.lang.String value) {
        this.projectCode = value;
    }

    /**
     * Gets the value of the projectDescription property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getProjectDescription() {
        return projectDescription;
    }

    /**
     * Sets the value of the projectDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setProjectDescription(java.lang.String value) {
        this.projectDescription = value;
    }

    /**
     * Gets the value of the companyName property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getCompanyName() {
        return companyName;
    }

    /**
     * Sets the value of the companyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setCompanyName(java.lang.String value) {
        this.companyName = value;
    }

    /**
     * Gets the value of the time property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTime() {
        return time;
    }

    /**
     * Sets the value of the time property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTime(XMLGregorianCalendar value) {
        this.time = value;
    }

    /**
     * Gets the value of the is3D property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getIs3D() {
        if (is3D == null) {
            return "no";
        } else {
            return is3D;
        }
    }

    /**
     * Sets the value of the is3D property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setIs3D(java.lang.String value) {
        this.is3D = value;
    }

    /**
     * Gets the value of the units property.
     * 
     * @return
     *     possible object is
     *     {@link DistanceUnitsType }
     *     
     */
    public DistanceUnitsType getUnits() {
        return units;
    }

    /**
     * Sets the value of the units property.
     * 
     * @param value
     *     allowed object is
     *     {@link DistanceUnitsType }
     *     
     */
    public void setUnits(DistanceUnitsType value) {
        this.units = value;
    }

    /**
     * Gets the value of the discipline property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
    public java.lang.String getDiscipline() {
        if (discipline == null) {
            return "PID";
        } else {
            return discipline;
        }
    }

    /**
     * Sets the value of the discipline property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
    public void setDiscipline(java.lang.String value) {
        this.discipline = value;
    }

}
