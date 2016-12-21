/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.b2b.home.a108public;

import java.io.InputStream;
import java.util.Date;

/**
 *
 * @author antof
 */
public class Customer_Case {
    private String case_id;
    private String cust_id;
    private String type;
    private String latitude;
    private String longitude;
    private String no_ppl_affected;
    private Date time;
    private String description;
    public InputStream image;


    /**
     * @return the cust_id
     */
    public String getCase_id() {
        return case_id;
    }

    /**
     * @param case_id the cust_id to set
     */
    public void setCase_id(String case_id) {
        this.case_id = case_id;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the no_ppl_affected
     */
    public String getNo_ppl_affected() {
        return no_ppl_affected;
    }

    /**
     * @param no_ppl_affected the no_ppl_affected to set
     */
    public void setNo_ppl_affected(String no_ppl_affected) {
        this.no_ppl_affected = no_ppl_affected;
    }

    /**
     * @return the time
     */
    public Date getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(Date time) {
        this.time = time;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getCust_id() {
        return cust_id;
    }

    public void setCust_id(String cust_id) {
        this.cust_id = cust_id;
    }
}
