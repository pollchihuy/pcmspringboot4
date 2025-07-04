package com.juaracoding.pcmspringboot4.repo;

import com.juaracoding.pcmspringboot4.model.KategoriProduk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface KategoriProdukRepo extends JpaRepository<KategoriProduk,Long> {

    //SELECT * FROM MstKategoriProduk WHERE toLower(nama) LIKE '%toLower(?)%'
    Page<KategoriProduk> findByNamaContainsIgnoreCase(String nama, Pageable pageable);
    //SELECT * FROM MstKategoriProduk WHERE toLower(nama) LIKE '%toLower(?)%'
    Page<KategoriProduk> findByDeskripsiContainsIgnoreCase(String nama, Pageable pageable);

//    //SELECT * FROM MstKategoriProduk WHERE toLower(nama) LIKE '%toLower(?)%' OR toLower(deskripsi) LIKE '%toLower(?)%'
//    Page<KategoriProduk> findByNamaContainsIgnoreCaseOrDeskripsiContainsIgnoreCase(String nama,String deskripsi, Pageable pageable);
}
