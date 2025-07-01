package com.juaracoding.pcmspringboot4.model;


import jakarta.persistence.*;

import java.util.Date;

//@Table(name = "MstKategoriProduk",uniqueConstraints = @UniqueConstraint(name = "unq-deskripsi-nama", columnNames = {"nama","deskripsi"}))
//@Table(name = "MstKategoriProduk",indexes = {
//        @Index(name = "idx_nama", columnList = "nama"),
//        @Index(name = "idx_deskripsi", columnList = "deskripsi", unique = true)
//})
@Entity
@Table(name = "MstKategoriProduk")
public class KategoriProduk {
    /**
     * unique combination
     * unique per column
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NamaProduk",nullable = false,length = 50,unique = true)
    private String nama;
    @Column(name = "Deskripsi",nullable = false,length = 255,unique = true)
    private String deskripsi;
    @Column(name = "CreatedBy",nullable = false,updatable = false)
    private Long createdBy=1L;
    @Column(name = "ModifiedBy",insertable = false)
    private Long modifiedBy;
    @Column(name = "CreatedAt",nullable = false,updatable = false)
    private Date createdAt=new Date();
    @Column(name = "ModifiedAt",insertable = false)
    private Date modifiedAt;

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

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
