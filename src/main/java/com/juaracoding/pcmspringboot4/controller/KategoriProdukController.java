package com.juaracoding.pcmspringboot4.controller;


import com.juaracoding.pcmspringboot4.dto.validasi.ValKategoriProdukDTO;
import com.juaracoding.pcmspringboot4.service.KategoriProdukService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @PutMapping("/{id}")
    public Object update(
                        @PathVariable Long id,
                        @Valid @RequestBody ValKategoriProdukDTO valKategoriProdukDTO,
                        HttpServletRequest request){
        return kategoriProdukService.update(id,kategoriProdukService.mapToModelMapper(valKategoriProdukDTO),request);
    }

    @DeleteMapping("/{id}")
    public Object delete(
            @PathVariable Long id,
            HttpServletRequest request){
        return kategoriProdukService.delete(id,request);
    }

    @GetMapping("/{id}")
    public Object findById(
            @PathVariable Long id,
            HttpServletRequest request){
        return kategoriProdukService.findById(id,request);
    }

    @PostMapping("/upload-excel")
    public Object uploadExcel(
            @RequestParam MultipartFile file,
            HttpServletRequest request){
        return kategoriProdukService.uploadExcel(file,request);
    }

    @GetMapping
    public Object findAll(HttpServletRequest request){
        Pageable pageable = PageRequest.of(1,2, Sort.by("id").descending());
        return kategoriProdukService.findAll(pageable,request);
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
        return kategoriProdukService.findByParam(pageable,column,value,request);
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