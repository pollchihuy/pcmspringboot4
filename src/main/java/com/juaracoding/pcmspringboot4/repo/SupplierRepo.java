package com.juaracoding.pcmspringboot4.repo;

import com.juaracoding.pcmspringboot4.model.KategoriProduk;
import com.juaracoding.pcmspringboot4.model.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SupplierRepo extends JpaRepository<Supplier, Long> {

    Page<Supplier> findByNamaContainsIgnoreCase(String nama, Pageable pageable);
    Page<Supplier> findByAlamatContainsIgnoreCase(String nama, Pageable pageable);

    List<Supplier> findByNamaContainsIgnoreCase(String nama);
    List<Supplier> findByAlamatContainsIgnoreCase(String nama);
    Optional<Supplier> findTop1ByOrderByIdDesc();
}
