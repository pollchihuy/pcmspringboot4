package com.juaracoding.pcmspringboot4.controller;


import com.juaracoding.pcmspringboot4.model.KategoriProduk;
import com.juaracoding.pcmspringboot4.service.KategoriProdukService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("kategoriproduk")
public class KategoriProdukController {

    @Autowired
    KategoriProdukService kategoriProdukService;

    @PostMapping
    public Object save(@RequestBody KategoriProduk kategoriProduk){
        return kategoriProdukService.save(kategoriProduk);
    }
}
