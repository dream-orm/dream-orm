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
    private double phase;
    @Tag
    private int groupid;
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

    public double getPhase() {
        return phase;
    }

    public void setPhase(double phase) {
        this.phase = phase;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
