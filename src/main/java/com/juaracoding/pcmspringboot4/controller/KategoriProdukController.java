package com.juaracoding.pcmspringboot4.controller;


import com.juaracoding.pcmspringboot4.dto.validasi.ValKategoriProdukDTO;
import com.juaracoding.pcmspringboot4.service.KategoriProdukService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
//        return kategoriProdukService.uploadExcel(file,request);
        return kategoriProdukService.uploadExcelManual(file,request);
    }

    @GetMapping
    public Object findAll(HttpServletRequest request){
        Pageable pageable = PageRequest.of(0,50, Sort.by("id"));
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
//    http://localhost:8081/kategoriproduk/download-excel?column=id&size=2&value=aa
    @GetMapping("/download-excel")
    public Object downloadExcel(
            @RequestParam String column,
            @RequestParam String value,
            HttpServletRequest request,
            HttpServletResponse response){
        return kategoriProdukService.downloadReportExcel(column,value,request,response);
    }
//    http://localhost:8081/kategoriproduk/download-excel-manual?column=id&size=2&value=aa
    @GetMapping("/download-excel-manual")
    public Object downloadExcelManual(
            @RequestParam String column,
            @RequestParam String value,
            HttpServletRequest request,
            HttpServletResponse response){
        return kategoriProdukService.downloadReportExcelManual(column,value,request,response);
    }

    //    http://localhost:8081/kategoriproduk/download-pdf?column=id&size=2&value=aa
    @GetMapping("/download-pdf")
    public Object downloadPDF(
            @RequestParam String column,
            @RequestParam String value,
            HttpServletRequest request,
            HttpServletResponse response){
        return kategoriProdukService.downloadReportPDF(column,value,request,response);
    }
//    http://localhost:8081/kategoriproduk/download-pdf-manual?column=id&size=2&value=aa
    @GetMapping("/download-pdf-manual")
    public Object downloadPDFManual(
            @RequestParam String column,
            @RequestParam String value,
            HttpServletRequest request,
            HttpServletResponse response){
        return kategoriProdukService.downloadReportPDFManual(column,value,request,response);
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