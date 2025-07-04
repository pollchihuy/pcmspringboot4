package com.juaracoding.pcmspringboot4.repo;

import com.juaracoding.pcmspringboot4.model.KategoriProduk;
import com.juaracoding.pcmspringboot4.model.Produk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProdukRepo extends JpaRepository<Produk, Long> {

    Page<Produk> findByNamaContainsIgnoreCase(String value, Pageable pageable);
    Page<Produk> findByDeskripsiContainsIgnoreCase(String value, Pageable pageable);
    Page<Produk> findByModelContainsIgnoreCase(String value, Pageable pageable);
    Page<Produk> findByWarnaContainsIgnoreCase(String value, Pageable pageable);
    Page<Produk> findByMerkContainsIgnoreCase(String value, Pageable pageable);
    //SELECT * FROM MstKategoriProduk WHERE lower(stok) LIKE '%lower(?)%'

    @Query(value = "SELECT p FROM Produk p WHERE cast(p.stok as string) LIKE  concat('%',?1,'%') ")
    Page<Produk> cariStok(String value, Pageable pageable);
}