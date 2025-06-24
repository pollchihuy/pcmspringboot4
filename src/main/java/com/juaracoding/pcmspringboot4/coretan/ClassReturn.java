package com.juaracoding.pcmspringboot4.coretan;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/*
IntelliJ IDEA 2024.1.4 (Ultimate Edition)
Build #IU-241.18034.62, built on June 21, 2024
@Author pollc a.k.a. Paul Christian
Java Developer
Created on 24/06/2025 20:48
@Last Modified 24/06/2025 20:48
Version 1.0
*/
public class ClassReturn {
    private String data;
    private LocalDateTime timestamp;
    private Integer version;
    private List<String> list;

    private ClassStudent cs;

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public ClassStudent getCs() {
        return cs;
    }

    public void setCs(ClassStudent cs) {
        this.cs = cs;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
