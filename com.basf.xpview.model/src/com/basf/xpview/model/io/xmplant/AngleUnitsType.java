//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.03.11 at 08:37:52 PM CET 
//


package com.basf.xpview.model.io.xmplant;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AngleUnitsType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AngleUnitsType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *     &lt;enumeration value="deg"/>
 *     &lt;enumeration value="rad"/>
 *     &lt;enumeration value="Degree"/>
 *     &lt;enumeration value="Radian"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AngleUnitsType")
@XmlEnum
public enum AngleUnitsType {

    @XmlEnumValue("deg")
    DEG("deg"),
    @XmlEnumValue("rad")
    RAD("rad"),
    @XmlEnumValue("Degree")
    DEGREE("Degree"),
    @XmlEnumValue("Radian")
    RADIAN("Radian");
    private final java.lang.String value;

    AngleUnitsType(java.lang.String v) {
        value = v;
    }

    public java.lang.String value() {
        return value;
    }

    public static AngleUnitsType fromValue(java.lang.String v) {
        for (AngleUnitsType c: AngleUnitsType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}