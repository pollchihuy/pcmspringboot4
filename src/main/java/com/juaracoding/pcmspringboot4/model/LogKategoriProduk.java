package com.juaracoding.pcmspringboot4.model;


import jakarta.persistence.*;

import java.util.Date;
@Entity
@Table(name = "LogKategoriProduk")
public class LogKategoriProduk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "IDKategoriProduk",updatable = false)
    private Long idKategoriProduk;

    @Column(name = "NamaProduk",nullable = false,length = 50,updatable = false)
    private String nama;
    @Column(name = "Deskripsi",nullable = false,length = 255,updatable = false)
    private String deskripsi;

    @Column(name = "Notes",nullable = false,length = 255,updatable = false)
    private String notes;

    @Column(name = "CreatedBy",nullable = false,updatable = false)
    private Long createdBy=1L;
    @Column(name = "CreatedAt",nullable = false,updatable = false)
    private Date createdAt=new Date();

    @Column(name = "Flag",nullable = false,updatable = false)
    private Character flag;

    public Character getFlag() {
        return flag;
    }

    public void setFlag(Character flag) {
        this.flag = flag;
    }

    public Long getIdKategoriProduk() {
        return idKategoriProduk;
    }

    public void setIdKategoriProduk(Long idKategoriProduk) {
        this.idKategoriProduk = idKategoriProduk;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
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