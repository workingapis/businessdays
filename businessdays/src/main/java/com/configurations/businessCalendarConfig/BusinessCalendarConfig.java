//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.06.15 at 04:32:26 PM IST 
//


package com.configurations.businessCalendarConfig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="pivotDate" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="businessDays" type="{}BusinessDays"/&gt;
 *         &lt;element name="businessHours" type="{}BusinessHours"/&gt;
 *         &lt;element name="holidays" type="{}Holidays"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pivotDate",
    "businessDays",
    "businessHours",
    "holidays"
})
@XmlRootElement(name = "BusinessCalendarConfig")
public class BusinessCalendarConfig {

    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar pivotDate;
    @XmlElement(required = true)
    protected BusinessDays businessDays;
    @XmlElement(required = true)
    protected BusinessHours businessHours;
    @XmlElement(required = true)
    protected Holidays holidays;

    /**
     * Gets the value of the pivotDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPivotDate() {
        return pivotDate;
    }

    /**
     * Sets the value of the pivotDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPivotDate(XMLGregorianCalendar value) {
        this.pivotDate = value;
    }

    /**
     * Gets the value of the businessDays property.
     * 
     * @return
     *     possible object is
     *     {@link BusinessDays }
     *     
     */
    public BusinessDays getBusinessDays() {
        return businessDays;
    }

    /**
     * Sets the value of the businessDays property.
     * 
     * @param value
     *     allowed object is
     *     {@link BusinessDays }
     *     
     */
    public void setBusinessDays(BusinessDays value) {
        this.businessDays = value;
    }

    /**
     * Gets the value of the businessHours property.
     * 
     * @return
     *     possible object is
     *     {@link BusinessHours }
     *     
     */
    public BusinessHours getBusinessHours() {
        return businessHours;
    }

    /**
     * Sets the value of the businessHours property.
     * 
     * @param value
     *     allowed object is
     *     {@link BusinessHours }
     *     
     */
    public void setBusinessHours(BusinessHours value) {
        this.businessHours = value;
    }

    /**
     * Gets the value of the holidays property.
     * 
     * @return
     *     possible object is
     *     {@link Holidays }
     *     
     */
    public Holidays getHolidays() {
        return holidays;
    }

    /**
     * Sets the value of the holidays property.
     * 
     * @param value
     *     allowed object is
     *     {@link Holidays }
     *     
     */
    public void setHolidays(Holidays value) {
        this.holidays = value;
    }

}