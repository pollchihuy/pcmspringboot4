package com.juaracoding.pcmspringboot4.service;

import com.juaracoding.pcmspringboot4.model.KategoriProduk;
import com.juaracoding.pcmspringboot4.repo.KategoriProdukRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class KategoriProdukService {

    @Autowired
    private KategoriProdukRepo kategoriProdukRepo;

    public Object save(KategoriProduk kategoriProduk){
        
        kategoriProdukRepo.save(kategoriProduk);
        return "Data Berhasil Disimpan";
    }
}
