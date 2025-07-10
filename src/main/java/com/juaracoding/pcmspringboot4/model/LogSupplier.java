package com.juaracoding.pcmspringboot4.model;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "LogSupplier")
public class LogSupplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "IDSupplier")
    private Long idSupplier;

    @Column(name = "NamaSupplier", nullable = false,length = 50)
    private String nama;

    @Column(name = "AlamatSupplier", nullable = false,length = 255)
    private String alamat;

    @Column(name = "CreatedBy",nullable = false,updatable = false)
    private Long createdBy=1L;
    @Column(name = "CreatedAt",nullable = false,updatable = false)
    private Date createdAt=new Date();

    @Column(name = "Flag",nullable = false,updatable = false)
    private Character flag;

    public Long getIdSupplier() {
        return idSupplier;
    }

    public void setIdSupplier(Long idSupplier) {
        this.idSupplier = idSupplier;
    }

    public Character getFlag() {
        return flag;
    }

    public void setFlag(Character flag) {
        this.flag = flag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }


    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}
