package com.juaracoding.pcmspringboot4.model;


import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "MstProduk")
public class Produk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NamaProduk", nullable = false,unique = true,length = 50)
    private String nama;

    @Column(name = "DeskripsiProduk",nullable = false,length = 255,unique = true)
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
    @Column(name = "ModifiedBy",insertable = false)
    private Long modifiedBy;
    @Column(name = "CreatedAt",nullable = false,updatable = false)
    private Date createdAt=new Date();
    @Column(name = "ModifiedAt",insertable = false)
    private Date modifiedAt;

    /**
     * Edit script when migration
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDKategoriProduk",nullable = false,foreignKey = @ForeignKey(name = "fk-to-katprod"))
    private KategoriProduk kategoriProduk;

    @ManyToMany
    @JoinTable(name = "MapProdukSupplier",uniqueConstraints = @UniqueConstraint(name = "unq-produk-to-supplier",columnNames = {"IDProduk","IDSupplier"}),
        joinColumns = @JoinColumn(name = "IDProduk",foreignKey = @ForeignKey(name = "fk-to-Produk")),
        inverseJoinColumns = @JoinColumn(name = "IDSupplier",foreignKey = @ForeignKey(name = "fk-to-supplier"))
    )
    private List<Supplier> suppliers;

    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<Supplier> suppliers) {
        this.suppliers = suppliers;
    }

    public KategoriProduk getKategoriProduk() {
        return kategoriProduk;
    }

    public void setKategoriProduk(KategoriProduk kategoriProduk) {
        this.kategoriProduk = kategoriProduk;
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
