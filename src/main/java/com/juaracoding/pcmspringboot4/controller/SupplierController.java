package com.juaracoding.pcmspringboot4.controller;


import com.juaracoding.pcmspringboot4.dto.validasi.ValSupplierDTO;
import com.juaracoding.pcmspringboot4.model.Supplier;
import com.juaracoding.pcmspringboot4.service.SupplierService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("supplier")
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    @GetMapping("/miniobject")
    public Object getData(
                       HttpServletRequest request){
        Pageable pageable = PageRequest.of(0,50, Sort.by("id"));
//        List<Supplier> suppliers = (List<Supplier>) supplierService.findAllMiniObject(pageable,request);
        List<Supplier> suppliers = (List<Supplier>) supplierService.findAllMiniObject(pageable,request);
        return suppliers;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('Supplier')")
    public Object save(@Valid @RequestBody ValSupplierDTO valSupplierDTO,
                       HttpServletRequest request){
        return supplierService.save(supplierService.mapToModelMapper(valSupplierDTO),request);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Supplier')")
    public Object update(
                        @PathVariable Long id,
                        @Valid @RequestBody ValSupplierDTO valSupplierDTO,
                        HttpServletRequest request){
        return supplierService.update(id, supplierService.mapToModelMapper(valSupplierDTO),request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Supplier')")
    public Object delete(
            @PathVariable Long id,
            HttpServletRequest request){
        return supplierService.delete(id,request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Supplier')")
    public Object findById(
            @PathVariable Long id,
            HttpServletRequest request){
        return supplierService.findById(id,request);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('Supplier')")
    public Object findAll(HttpServletRequest request){
        Pageable pageable = PageRequest.of(0,50, Sort.by("id"));
        return supplierService.findAll(pageable,request);
    }

    /**
     * Fungsional API ini untuk Filter maupun Sorting
     * @param request
     * @return
     */
    @GetMapping("/{sort}/{sort-by}/{page}")
    @PreAuthorize("hasAuthority('Supplier')")
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
        return supplierService.findByParam(pageable,column,value,request);
    }

    @PostMapping("/upload-excel")
    @PreAuthorize("hasAuthority('Supplier')")
    public Object uploadExcel(
            @RequestParam MultipartFile file,
            HttpServletRequest request){
        return supplierService.uploadExcel(file,request);
    }

//    http://localhost:8081/supplier/download-excel?column=id&size=2&value=aa
    @GetMapping("/download-excel")
    @PreAuthorize("hasAuthority('Supplier')")
    public Object downloadExcel(
            @RequestParam String column,
            @RequestParam String value,
            HttpServletRequest request,
            HttpServletResponse response){
        return supplierService.downloadReportExcel(column,value,request,response);
    }

    //    http://localhost:8081/supplier/download-pdf?column=id&size=2&value=aa
    @GetMapping("/download-pdf")
    @PreAuthorize("hasAuthority('Supplier')")
    public Object downloadPDF(
            @RequestParam String column,
            @RequestParam String value,
            HttpServletRequest request,
            HttpServletResponse response){
        return supplierService.downloadReportPDF(column,value,request,response);
    }

    private String switchColumnSorting(String sortBy){
        switch(sortBy){
            case "nama":sortBy = "nama";
            case "alamat":sortBy = "alamat";
            default:sortBy = "id";
        }
        return sortBy;
    }
}