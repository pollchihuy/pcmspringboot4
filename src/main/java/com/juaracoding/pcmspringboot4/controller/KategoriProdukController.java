package com.juaracoding.pcmspringboot4.controller;


import com.juaracoding.pcmspringboot4.dto.validasi.ValKategoriProdukDTO;
import com.juaracoding.pcmspringboot4.model.KategoriProduk;
import com.juaracoding.pcmspringboot4.service.KategoriProdukService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("kategoriproduk")
public class KategoriProdukController {

    @Autowired
    KategoriProdukService kategoriProdukService;

    @PostMapping
    public Object save(@Valid @RequestBody ValKategoriProdukDTO valKategoriProdukDTO,
                       HttpServletRequest request){
        return kategoriProdukService.save(kategoriProdukService.mapToModelMapper(valKategoriProdukDTO),request);
    }

    @GetMapping
    public Object findAll(HttpServletRequest request){
        Pageable pageable = PageRequest.of(1,2, Sort.by("id").descending());
        return kategoriProdukService.findAll(pageable,request);
    }
}