package com.mehana.smschat.model;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author maruen email: maruen@gmail.com
 * 
 */

@Entity
public class Config extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Column
    private String            property;

    @Column
    private String            value;

    @Column
    private String            server;

    @Column
    private String            description;

    public Config() {
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

}