package com.juaracoding.pcmspringboot4.repo;

import com.juaracoding.pcmspringboot4.model.KategoriProduk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface KategoriProdukRepo extends JpaRepository<KategoriProduk,Long> {

    //SELECT * FROM MstKategoriProduk WHERE toLower(nama) LIKE '%toLower(?)%'
    Page<KategoriProduk> findByNamaContainsIgnoreCase(String nama, Pageable pageable);
    //SELECT * FROM MstKategoriProduk WHERE toLower(nama) LIKE '%toLower(?)%'
    Page<KategoriProduk> findByDeskripsiContainsIgnoreCase(String nama, Pageable pageable);
    Page<KategoriProduk> findByNotesContainsIgnoreCase(String nama, Pageable pageable);

    /** ini khusus untuk report */
    List<KategoriProduk> findByNamaContainsIgnoreCase(String nama);

    //SELECT * FROM MstKategoriProduk WHERE toLower(nama) LIKE '%toLower(?)%'
    List<KategoriProduk> findByDeskripsiContainsIgnoreCase(String nama);
    List<KategoriProduk> findByNotesContainsIgnoreCase(String nama);
//    //SELECT * FROM MstKategoriProduk WHERE toLower(nama) LIKE '%toLower(?)%' OR toLower(deskripsi) LIKE '%toLower(?)%'
//    Page<KategoriProduk> findByNamaContainsIgnoreCaseOrDeskripsiContainsIgnoreCase(String nama,String deskripsi, Pageable pageable);

    /** Query untuk unit Testing
     * SELECT TOP 1 * FROM MstKategoriProduk ORDER BY ID DESC
     * */
    Optional<KategoriProduk> findTop1ByOrderByIdDesc();
}
