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
 *     &lt;extension base="{}PlantItem">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element ref="{}NominalDiameter"/>
 *         &lt;element ref="{}InsideDiameter"/>
 *         &lt;element ref="{}OutsideDiameter"/>
 *         &lt;element ref="{}StartDiameter"/>
 *         &lt;element ref="{}EndDiameter"/>
 *         &lt;element ref="{}NormalDesignPressure"/>
 *         &lt;element ref="{}MinimumDesignPressure"/>
 *         &lt;element ref="{}MaximumDesignPressure"/>
 *         &lt;element ref="{}NormalDesignTemperature"/>
 *         &lt;element ref="{}MinimumDesignTemperature"/>
 *         &lt;element ref="{}MaximumDesignTemperature"/>
 *         &lt;element ref="{}NormalOperatingPressure"/>
 *         &lt;element ref="{}MinimumOperatingPressure"/>
 *         &lt;element ref="{}MaximumOperatingPressure"/>
 *         &lt;element ref="{}TestPressure"/>
 *         &lt;element ref="{}NormalOperatingTemperature"/>
 *         &lt;element ref="{}MinimumOperatingTemperature"/>
 *         &lt;element ref="{}MaximumOperatingTemperature"/>
 *         &lt;element ref="{}WallThickness"/>
 *         &lt;element ref="{}Connection" minOccurs="0"/>
 *         &lt;element ref="{}CenterLine"/>
 *         &lt;element ref="{}Equipment"/>
 *         &lt;element ref="{}PipingComponent"/>
 *         &lt;element ref="{}ProcessInstrument"/>
 *         &lt;element ref="{}Component"/>
 *         &lt;element ref="{}PipeConnectorSymbol"/>
 *         &lt;element ref="{}PipeFlowArrow"/>
 *         &lt;element ref="{}Label"/>
 *         &lt;element ref="{}InsulationSymbol"/>
 *         &lt;element ref="{}InstrumentConnection"/>
 *       &lt;/choice>
 *       &lt;attribute name="DualFlow" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "nominalDiameterOrInsideDiameterOrOutsideDiameter"
})
@XmlRootElement(name = "PipingNetworkSegment")
public class PipingNetworkSegment
    extends PlantItem
{

    @XmlElementRefs({
        @XmlElementRef(name = "CenterLine", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "MaximumDesignTemperature", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "EndDiameter", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "Equipment", type = Equipment.class, required = false),
        @XmlElementRef(name = "NominalDiameter", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "WallThickness", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "InsulationSymbol", type = InsulationSymbol.class, required = false),
        @XmlElementRef(name = "NormalDesignPressure", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "InsideDiameter", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "PipeFlowArrow", type = PipeFlowArrow.class, required = false),
        @XmlElementRef(name = "MinimumDesignPressure", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "Label", type = Label.class, required = false),
        @XmlElementRef(name = "Component", type = Component.class, required = false),
        @XmlElementRef(name = "MaximumDesignPressure", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "NormalOperatingTemperature", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "ProcessInstrument", type = ProcessInstrument.class, required = false),
        @XmlElementRef(name = "MaximumOperatingPressure", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "Connection", type = Connection.class, required = false),
        @XmlElementRef(name = "MinimumDesignTemperature", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "InstrumentConnection", type = InstrumentConnection.class, required = false),
        @XmlElementRef(name = "PipingComponent", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "PipeConnectorSymbol", type = PipeConnectorSymbol.class, required = false),
        @XmlElementRef(name = "MaximumOperatingTemperature", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "NormalOperatingPressure", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "StartDiameter", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "MinimumOperatingTemperature", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "MinimumOperatingPressure", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "OutsideDiameter", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "NormalDesignTemperature", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "TestPressure", type = JAXBElement.class, required = false)
    })
    protected List<Object> nominalDiameterOrInsideDiameterOrOutsideDiameter;
    @XmlAttribute(name = "DualFlow")
    protected Boolean dualFlow;

    /**
     * Gets the value of the nominalDiameterOrInsideDiameterOrOutsideDiameter property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nominalDiameterOrInsideDiameterOrOutsideDiameter property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNominalDiameterOrInsideDiameterOrOutsideDiameter().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link CenterLine }{@code >}
     * {@link JAXBElement }{@code <}{@link TemperatureDouble }{@code >}
     * {@link JAXBElement }{@code <}{@link DistanceDouble }{@code >}
     * {@link Equipment }
     * {@link JAXBElement }{@code <}{@link DistanceDouble }{@code >}
     * {@link JAXBElement }{@code <}{@link DistanceString }{@code >}
     * {@link InsulationSymbol }
     * {@link JAXBElement }{@code <}{@link PressureDouble }{@code >}
     * {@link JAXBElement }{@code <}{@link DistanceDouble }{@code >}
     * {@link PipeFlowArrow }
     * {@link JAXBElement }{@code <}{@link PressureDouble }{@code >}
     * {@link Label }
     * {@link Component }
     * {@link JAXBElement }{@code <}{@link PressureDouble }{@code >}
     * {@link JAXBElement }{@code <}{@link TemperatureDouble }{@code >}
     * {@link ProcessInstrument }
     * {@link JAXBElement }{@code <}{@link PressureDouble }{@code >}
     * {@link Connection }
     * {@link JAXBElement }{@code <}{@link TemperatureDouble }{@code >}
     * {@link InstrumentConnection }
     * {@link JAXBElement }{@code <}{@link PipingComponent }{@code >}
     * {@link PipeConnectorSymbol }
     * {@link JAXBElement }{@code <}{@link TemperatureDouble }{@code >}
     * {@link JAXBElement }{@code <}{@link PressureDouble }{@code >}
     * {@link JAXBElement }{@code <}{@link DistanceDouble }{@code >}
     * {@link JAXBElement }{@code <}{@link TemperatureDouble }{@code >}
     * {@link JAXBElement }{@code <}{@link PressureDouble }{@code >}
     * {@link JAXBElement }{@code <}{@link DistanceDouble }{@code >}
     * {@link JAXBElement }{@code <}{@link PressureDouble }{@code >}
     * {@link JAXBElement }{@code <}{@link TemperatureDouble }{@code >}
     * 
     * 
     */
    public List<Object> getNominalDiameterOrInsideDiameterOrOutsideDiameter() {
        if (nominalDiameterOrInsideDiameterOrOutsideDiameter == null) {
            nominalDiameterOrInsideDiameterOrOutsideDiameter = new ArrayList<Object>();
        }
        return this.nominalDiameterOrInsideDiameterOrOutsideDiameter;
    }

    /**
     * Gets the value of the dualFlow property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDualFlow() {
        return dualFlow;
    }

    /**
     * Sets the value of the dualFlow property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDualFlow(Boolean value) {
        this.dualFlow = value;
    }

}
