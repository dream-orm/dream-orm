package com.dream.tdhelloworld.table;

import com.dream.system.annotation.Column;
import com.dream.system.annotation.Table;
import com.dream.tdengine.annotation.Tag;

import java.util.Date;

@Table
public class Meters {
    @Column
    private Date ts;
    @Column
    private double current;
    @Column
    private int voltage;
    @Column
    private double phaseId;
    @Tag
    private int groupId;
    @Tag
    private String location;

    public Date getTs() {
        return ts;
    }

    public void setTs(Date ts) {
        this.ts = ts;
    }

    public double getCurrent() {
        return current;
    }

    public void setCurrent(double current) {
        this.current = current;
    }

    public int getVoltage() {
        return voltage;
    }

    public void setVoltage(int voltage) {
        this.voltage = voltage;
    }

    public double getPhaseId() {
        return phaseId;
    }

    public void setPhaseId(double phaseId) {
        this.phaseId = phaseId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
