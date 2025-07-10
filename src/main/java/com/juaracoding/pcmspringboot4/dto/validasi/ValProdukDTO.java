package com.juaracoding.pcmspringboot4.dto.validasi;

import com.juaracoding.pcmspringboot4.dto.relation.RelKategoriProdukDTO;
import com.juaracoding.pcmspringboot4.dto.relation.RelSupplierDTO;
import com.juaracoding.pcmspringboot4.model.KategoriProduk;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

/*
IntelliJ IDEA 2024.1.4 (Ultimate Edition)
Build #IU-241.18034.62, built on June 21, 2024
@Author pollc a.k.a. Paul Christian
Java Developer
Created on 04/07/2025 20:45
@Last Modified 04/07/2025 20:45
Version 1.0
*/
public class ValProdukDTO {
    
    private String nama;
    private String deskripsi;
    private String model;
    private String warna;
    private Integer stok;
    private String merk;
    private RelKategoriProdukDTO kategoriProduk;
    private List<RelSupplierDTO> suppliers;

    public List<RelSupplierDTO> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<RelSupplierDTO> suppliers) {
        this.suppliers = suppliers;
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

    public RelKategoriProdukDTO getKategoriProduk() {
        return kategoriProduk;
    }

    public void setKategoriProduk(RelKategoriProdukDTO kategoriProduk) {
        this.kategoriProduk = kategoriProduk;
    }
}
