package com.juaracoding.pcmspringboot4.controller;


import com.juaracoding.pcmspringboot4.dto.validasi.ValProdukDTO;
import com.juaracoding.pcmspringboot4.service.ProdukService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("produk")
public class ProdukController {

    @Autowired
    ProdukService produkService;

    @PostMapping
    @PreAuthorize("hasAuthority('Produk')")
    public ResponseEntity<Object> save(@Valid @RequestBody ValProdukDTO valProdukDTO,
                                       HttpServletRequest request){
        return produkService.save(produkService.mapToModelMapper(valProdukDTO),request);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Produk')")
    public ResponseEntity<Object> update(
                        @PathVariable Long id,
                        @Valid @RequestBody ValProdukDTO valProdukDTO,
                        HttpServletRequest request){
        return produkService.update(id, produkService.mapToModelMapper(valProdukDTO),request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Produk')")
    public ResponseEntity<Object> delete(
            @PathVariable Long id,
            HttpServletRequest request){
        return produkService.delete(id,request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Produk')")
    public ResponseEntity<Object> findById(
            @PathVariable Long id,
            HttpServletRequest request){
        return produkService.findById(id,request);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('Produk')")
    public ResponseEntity<Object> findAll(HttpServletRequest request){
        Pageable pageable = PageRequest.of(0,50, Sort.by("id"));
        return produkService.findAll(pageable,request);
    }

    @GetMapping("/{sort}/{sort-by}/{page}")
    @PreAuthorize("hasAuthority('Produk')")
    public ResponseEntity<Object> findByParam(
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

    @PostMapping("/upload-excel")
    @PreAuthorize("hasAuthority('Produk')")
    public ResponseEntity<Object> uploadExcel(
            @RequestParam MultipartFile file,
            HttpServletRequest request){
        return produkService.uploadExcel(file,request);
    }

//    http://localhost:8081/produk/download-excel?column=id&size=2&value=aa
    @GetMapping("/download-excel")
    @PreAuthorize("hasAuthority('Produk')")
    public Object downloadExcel(
            @RequestParam String column,
            @RequestParam String value,
            HttpServletRequest request,
            HttpServletResponse response){
        return produkService.downloadReportExcel(column,value,request,response);
    }

    //    http://localhost:8081/produk/download-pdf?column=id&size=2&value=aa
    @GetMapping("/download-pdf")
    @PreAuthorize("hasAuthority('Produk')")
    public Object downloadPDF(
            @RequestParam String column,
            @RequestParam String value,
            HttpServletRequest request,
            HttpServletResponse response){
        return produkService.downloadReportPDF(column,value,request,response);
    }

    private String switchColumnSorting(String sortBy){
        switch(sortBy){
            case "nama":sortBy = "nama";
            case "deskripsi":sortBy = "deskripsi";
            case "merk":sortBy = "merk";
            case "model":sortBy = "model";
            case "warna":sortBy = "warna";
            case "stok":sortBy = "stok";
            default:sortBy = "id";
        }
        return sortBy;
    }
}