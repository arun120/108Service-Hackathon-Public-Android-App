/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.b2b.home.a108public;

import java.util.Date;

/**
 *
 * @author antof
 */
public class Acceptance {
private String case_id;
private String driver;
private Date time;

    /**
     * @return the case_id
     */
    public String getCase_id() {
        return case_id;
    }

    /**
     * @param case_id the case_id to set
     */
    public void setCase_id(String case_id) {
        this.case_id = case_id;
    }

    /**
     * @return the driver
     */
    public String getDriver() {
        return driver;
    }

    /**
     * @param driver the driver to set
     */
    public void setDriver(String driver) {
        this.driver = driver;
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

}
