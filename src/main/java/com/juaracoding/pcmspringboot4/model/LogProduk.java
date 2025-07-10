package com.juaracoding.pcmspringboot4.model;


import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "LogProduk")
public class LogProduk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "IDProduk")
    private Long idProduk;
    @Column(name = "NamaProduk", nullable = false,length = 50)
    private String nama;
    @Column(name = "DeskripsiProduk",nullable = false,length = 255)
    private String deskripsi;
    @Column(name = "Model",nullable = false,length = 50)
    private String model;
    @Column(name = "Warna",nullable = false,length = 30)
    private String warna;
    @Column(name = "Stok",nullable = false)
    private Integer stok;
    @Column(name = "Merk",nullable = false,length = 50)
    private String merk;

    @Column(name = "CreatedBy",nullable = false,updatable = false)
    private Long createdBy=1L;
    @Column(name = "CreatedAt",nullable = false,updatable = false)
    private Date createdAt=new Date();
    @Column(name = "Flag",nullable = false,updatable = false)
    private Character flag;

    @Column(name = "IDKategoriProduk",nullable = false,updatable = false)
    private Long idKategoriProduk;

    public Long getIdKategoriProduk() {
        return idKategoriProduk;
    }

    public void setIdKategoriProduk(Long idKategoriProduk) {
        this.idKategoriProduk = idKategoriProduk;
    }

    public Long getIdProduk() {
        return idProduk;
    }

    public void setIdProduk(Long idProduk) {
        this.idProduk = idProduk;
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

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getWarna() {
        return warna;
    }

    public void setWarna(String warna) {
        this.warna = warna;
    }

    public Integer getStok() {
        return stok;
    }

    public void setStok(Integer stok) {
        this.stok = stok;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
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
