package com.juaracoding.pcmspringboot4.service;

import com.juaracoding.pcmspringboot4.core.IReport;
import com.juaracoding.pcmspringboot4.core.IService;
import com.juaracoding.pcmspringboot4.dto.response.RespSupplierDTO;
import com.juaracoding.pcmspringboot4.dto.validasi.ValSupplierDTO;
import com.juaracoding.pcmspringboot4.model.Supplier;
import com.juaracoding.pcmspringboot4.model.LogSupplier;
import com.juaracoding.pcmspringboot4.repo.SupplierRepo;
import com.juaracoding.pcmspringboot4.repo.LogSupplierRepo;
import com.juaracoding.pcmspringboot4.util.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Platform : Training -> TRN
 * Modul Code : 02
 * FV - FE --> fv = failed validation, fe = failed error
 */
@Service
@Transactional
public class SupplierService implements IService<Supplier>, IReport<Supplier> {

    @Autowired
    private SupplierRepo supplierRepo;

    @Autowired
    private LogSupplierRepo logSupplierRepo;

    @Autowired
    private ModelMapper modelMapper ;

    private String className = "SupplierService";

    @Autowired
    private TransformPagination transformPagination;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private PdfGenerator pdfGenerator;


    private StringBuilder sBuild = new StringBuilder();

    @Override
    public ResponseEntity<Object> save(Supplier supplier, HttpServletRequest request) {

        if(supplier==null){
            return GlobalResponse.objectNull("TRN02FV001",request);
        }
        try{
            supplierRepo.save(supplier);
            logSupplierRepo.save(mapToModelLog(supplier,1L,'s'));
        }catch (Exception e){
            LoggingFile.logException(className,"save(Supplier supplier, HttpServletRequest request) SQLException Request Package : "+RequestCapture.allRequest(request),e);
            return GlobalResponse.internalServerError("TRN02FE001",request);
        }

        return GlobalResponse.dataBerhasilDisimpan(request);
    }

    @Override
    public ResponseEntity<Object> update(Long id, Supplier supplier, HttpServletRequest request) {
        if(supplier==null){
            return GlobalResponse.objectNull("TRN02FV011",request);
        }
        try{
            Optional<Supplier> optionalSupplier= supplierRepo.findById(id);
            if(!optionalSupplier.isPresent()){
                return GlobalResponse.dataTidakDitemukan("TRN02FV012",request);
            }
            Supplier nextSupplier = optionalSupplier.get();
            nextSupplier.setNama(supplier.getNama());
            nextSupplier.setAlamat(supplier.getAlamat());
            nextSupplier.setModifiedBy(1L);
            nextSupplier.setModifiedAt(new Date());
            logSupplierRepo.save(mapToModelLog(nextSupplier,1L,'u'));
        }catch (Exception e){
            LoggingFile.logException(className,"update(Long id, Supplier supplier, HttpServletRequest request) Request Package : "+RequestCapture.allRequest(request),e);
            return GlobalResponse.internalServerError("TRN02FE011",request);
        }
        return GlobalResponse.dataBerhasilDiubah(request);
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        if(id==null){
            return GlobalResponse.objectNull("TRN02FV021",request);
        }
        if(id==0){
            return GlobalResponse.objectNull("TRN02FV022",request);
        }
        try{
            Optional<Supplier> optionalSupplier= supplierRepo.findById(id);
            if(!optionalSupplier.isPresent()){
                return GlobalResponse.dataTidakDitemukan("TRN02FV023",request);
            }
            supplierRepo.deleteById(id);
            logSupplierRepo.save(mapToModelLog(optionalSupplier.get(),1L,'d'));
        }catch (Exception e){
            LoggingFile.logException(className,"delete(Long id, HttpServletRequest request) Request Package : "+RequestCapture.allRequest(request),e);
            return GlobalResponse.internalServerError("TRN02FE021",request);
        }
        return GlobalResponse.dataBerhasilDihapus(request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<Supplier> pageData = null;
        Map<String,Object> mapResponse = null;
        try{
            pageData =  supplierRepo.findAll(pageable);
            if(pageData.isEmpty()){
                return GlobalResponse.dataTidakDitemukan("TRN02FV031",request);
            }
            mapResponse = transformPagination.transform(mapToModelMapper(pageData.getContent()),
                    pageData,"id",null);

        }catch (Exception e){
            LoggingFile.logException(className,"findAll(Pageable pageable, HttpServletRequest request) Request Package : "+RequestCapture.allRequest(request),e);
            return GlobalResponse.internalServerError("TRN02FE031",request);
        }
        return GlobalResponse.dataDitemukan(mapResponse,request);
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        if(id==null){
            return GlobalResponse.objectNull("TRN02FV041",request);
        }
        if(id==0){
            return GlobalResponse.objectNull("TRN02FV042",request);
        }
        Optional<Supplier> optionalSupplier = null;
        try{
            optionalSupplier= supplierRepo.findById(id);
            if(!optionalSupplier.isPresent()){
                return GlobalResponse.dataTidakDitemukan("TRN02FV043",request);
            }
        }catch (Exception e){
            LoggingFile.logException(className,"findById(Long id, HttpServletRequest request) Request Package : "+RequestCapture.allRequest(request),e);
            return GlobalResponse.internalServerError("TRN02FE041",request);
        }
        return GlobalResponse.dataDitemukan(mapToDTOMapper(optionalSupplier.get()), request);
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String column, String value, HttpServletRequest request) {
        Page<Supplier> page = null;
        Map<String,Object> mapResponse = null;
        try{
            switch (column) {
                case "nama":page= supplierRepo.findByNamaContainsIgnoreCase(value,pageable);break;
                case "alamat":page= supplierRepo.findByAlamatContainsIgnoreCase(value,pageable);break;
                default:page = supplierRepo.findAll(pageable);break;
            }
            mapResponse = transformPagination.transform(mapToModelMapper(page.getContent()),page,column,value);
        }catch (Exception e){
            LoggingFile.logException(className,"findByParam(Pageable pageable, String column, String value, HttpServletRequest request) Request Package : "+RequestCapture.allRequest(request),e);
            return GlobalResponse.internalServerError("TRN02FE051",request);
        }
        return GlobalResponse.dataDitemukan(mapResponse,request);
    }

    @Override
    public ResponseEntity<Object> uploadExcel(MultipartFile file, HttpServletRequest request) {
        String message = "";
        try{
            if(!ExcelReader.hasWorkBookFormat(file)){
                return GlobalResponse.formatFileHarusExcel("TRN02FV061",request);
            }
            List lt = new ExcelReader(file.getInputStream(),"kategori").getDataMap();
            if(lt.isEmpty()){
                return GlobalResponse.fileExcelKosong("TRN02FV062",request);
            }
            List<Supplier> listSupplier=convertListWorkBookToListEntity(lt,1L);
            List<Supplier> ls = supplierRepo.saveAll(listSupplier);
            logSupplierRepo.saveAll(mapToLog(ls));
        }catch (Exception e){
            LoggingFile.logException(className,"uploadExcel(MultipartFile file, HttpServletRequest request) Request Package : "+RequestCapture.allRequest(request),e);
            return GlobalResponse.fileExcelKosong("TRN02FE061",request);
        }
        return GlobalResponse.dataBerhasilDisimpan(request);
    }

    @Override
    public List<Supplier> convertListWorkBookToListEntity(List<Map<String, String>> workBookData, Long userId) {
        List<Supplier> supplierList = new ArrayList<>();
        for (Map<String, String> map : workBookData) {
            Supplier supplier = new Supplier();
            supplier.setNama(map.get("NAMA SUPPLIER"));
            supplier.setAlamat(map.get("ALAMAT SUPPLIER"));
            supplier.setCreatedBy(userId);
            supplierList.add(supplier);
        }
        return supplierList;
    }

    @Override
    public Object downloadReportExcel(String column, String value,
                                      HttpServletRequest request, HttpServletResponse response) {
        List<Supplier> listSupplier = null;
        try {
            switch (column){
                case "nama" : listSupplier = supplierRepo.findByNamaContainsIgnoreCase(value);break;
                case "alamat" : listSupplier = supplierRepo.findByAlamatContainsIgnoreCase(value);break;
                default:listSupplier= supplierRepo.findAll();break;
            }
            if(listSupplier.isEmpty()){
                return GlobalResponse.dataTidakDitemukan("TRN02FV071",request);
            }
            /** langkah pertama , convert dulu object ke dto yang benar-benar akan di display di file excel */
            List<RespSupplierDTO> listDTO = mapToModelMapper(listSupplier);
            new MappingReport().mappingReportExcel(listDTO,
                    "supplier",
                    new RespSupplierDTO(),
                    response);
        }catch (Exception e){
            return GlobalResponse.internalServerError("TRN02FE071",request);
        }

        return "";
    }

    @Override
    public Object downloadReportPDF(String column, String value, HttpServletRequest request, HttpServletResponse response) {
        List<Supplier> listSupplier = null;
        try {
            switch (column){
                case "nama" : listSupplier = supplierRepo.findByNamaContainsIgnoreCase(value);break;
                case "alamat" : listSupplier = supplierRepo.findByAlamatContainsIgnoreCase(value);break;
                default:listSupplier= supplierRepo.findAll();break;
            }
            if(listSupplier.isEmpty()){
                return GlobalResponse.dataTidakDitemukan("TRN02FV081",request);
            }
            List<RespSupplierDTO> listDTO = mapToModelMapper(listSupplier);
            new MappingReport().mappingReportPDF(listDTO,
                    "supplier",
                    "REPORT DATA KATEGORI PRODUK",
                    new RespSupplierDTO(),springTemplateEngine,pdfGenerator,response);
            }catch (Exception e){
                return GlobalResponse.internalServerError("TRN02FE081",request);
            }
        return "Download Report PDF Berhasil";
    }

    /** cara manual untuk dto validasi request dalam bentuk single object */
    public Supplier mapToModel(ValSupplierDTO valSupplierDTO){
        Supplier supplier = new Supplier();
        supplier.setNama(valSupplierDTO.getNama());
        supplier.setAlamat(valSupplierDTO.getAlamat());
        return supplier;
    }

    private List<LogSupplier> mapToLog(List<Supplier> supplierList){
        List<LogSupplier> logSupplierList = new ArrayList<>();
        for (Supplier supplier : supplierList) {
            LogSupplier logSupplier = new LogSupplier();
            logSupplier.setIdSupplier(supplier.getId());
            logSupplier.setNama(supplier.getNama());
            logSupplier.setAlamat(supplier.getAlamat());
            logSupplier.setCreatedBy(supplier.getCreatedBy());
            logSupplier.setFlag('s');
            logSupplierList.add(logSupplier);
        }
        return logSupplierList;
    }
    public RespSupplierDTO mapToDTO(Supplier supplier){
        RespSupplierDTO respSupplierDTO = new RespSupplierDTO();
        respSupplierDTO.setId(supplier.getId());
        respSupplierDTO.setNama(supplier.getNama());
        respSupplierDTO.setAlamat(supplier.getAlamat());
        return respSupplierDTO;
    }

    public RespSupplierDTO mapToDTOMapper(Supplier supplier){
        return modelMapper.map(supplier, RespSupplierDTO.class);
    }

    /** cara manual untuk dto response dalam bentuk multi object array */
    public List<RespSupplierDTO> mapToModel(List<Supplier> supplierList){
        List<RespSupplierDTO> list = new ArrayList<RespSupplierDTO>();
        for (Supplier supplier : supplierList) {
            RespSupplierDTO respSupplierDTO = new RespSupplierDTO();
            respSupplierDTO.setId(supplier.getId());
            respSupplierDTO.setNama(supplier.getNama());
            respSupplierDTO.setAlamat(supplier.getAlamat());
            list.add(respSupplierDTO);
        }
        return list;
    }

    public LogSupplier mapToModelLog(Supplier supplier,Long userId,Character flag){
        LogSupplier logSupplier = new LogSupplier();
        logSupplier.setIdSupplier(supplier.getId());
        logSupplier.setNama(supplier.getNama());
        logSupplier.setAlamat(supplier.getAlamat());
        logSupplier.setCreatedBy(userId);
        logSupplier.setFlag(flag);
        return logSupplier;
    }

    public Supplier mapToModelMapper(ValSupplierDTO valSupplierDTO){
        return modelMapper.map(valSupplierDTO, Supplier.class);
    }
    public List<RespSupplierDTO> mapToModelMapper(List<Supplier> supplierList){
        return modelMapper.map(supplierList, new TypeToken<List<RespSupplierDTO>>(){}.getType());
    }
}