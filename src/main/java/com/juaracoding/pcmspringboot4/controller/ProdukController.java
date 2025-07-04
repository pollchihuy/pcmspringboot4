package com.juaracoding.pcmspringboot4.controller;


import com.juaracoding.pcmspringboot4.dto.validasi.ValProdukDTO;
import com.juaracoding.pcmspringboot4.service.ProdukService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("produk")
public class ProdukController {

    @Autowired
    ProdukService produkService;

    /**
     {
         "nama":"Kain Pel Super6",
         "deskripsi":"pokoknya bersih deh semua6",
         "merk":"Toyota6",
         "warna":"abu abu monyet6",
         "model":"panjang tak berujung6",
         "stok":200,
         "kategoriProduk":{
                         "id":1
                         },
                         "suppliers":[
                         {
                         "id":1
                         },
                         {
                         "id":2
                         },
                         {
                         "id":3
                         }
             ]
         }
     */
    @PostMapping
    public Object save(@Valid @RequestBody ValProdukDTO valProdukDTO,
                       HttpServletRequest request){
        return produkService.save(produkService.mapToModelMapper(valProdukDTO),request);
    }

    @GetMapping
    public Object findAll(HttpServletRequest request){
        Pageable pageable = PageRequest.of(0,2, Sort.by("id").descending());
        return produkService.findAll(pageable,request);
    }

    /**
     * Fungsional API ini untuk Filter maupun Sorting
     * @param request
     * @return
     */
    @GetMapping("/{sort}/{sort-by}/{page}")
    public Object findByParam(
            @PathVariable Integer page,
            @PathVariable(value = "sort-by") String sortBy,
            @PathVariable String sort,
            @RequestParam Integer size,
            @RequestParam String column,
            @RequestParam String value,
            HttpServletRequest request){
        Pageable pageable = null;
        sortBy = switchColumnSorting(sortBy);//sudah di filter agar aplikasi tidak error
        if(sort.equals("asc")){
            pageable = PageRequest.of(page,size, Sort.by(sortBy));
        }else {
            pageable = PageRequest.of(page,size, Sort.by(sortBy).descending());
        }
        return produkService.findByParam(pageable,column,value,request);
    }

    private String switchColumnSorting(String sortBy){
        switch(sortBy){
            case "nama":sortBy = "nama";
            case "deskripsi":sortBy = "deskripsi";
            default:sortBy = "id";
        }
        return sortBy;
    }
}