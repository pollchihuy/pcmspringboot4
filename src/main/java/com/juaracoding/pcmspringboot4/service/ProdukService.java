package com.juaracoding.pcmspringboot4.service;

import com.juaracoding.pcmspringboot4.core.IReport;
import com.juaracoding.pcmspringboot4.core.IService;
import com.juaracoding.pcmspringboot4.dto.relation.RelSupplierDTO;
import com.juaracoding.pcmspringboot4.dto.response.RespProdukDTO;
import com.juaracoding.pcmspringboot4.dto.validasi.ValProdukDTO;
import com.juaracoding.pcmspringboot4.model.KategoriProduk;
import com.juaracoding.pcmspringboot4.model.LogProduk;
import com.juaracoding.pcmspringboot4.model.Produk;
import com.juaracoding.pcmspringboot4.model.Supplier;
import com.juaracoding.pcmspringboot4.repo.LogProdukRepo;
import com.juaracoding.pcmspringboot4.repo.ProdukRepo;
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
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.*;

/**
 * Platform : Training -> TRN
 * Modul Code : 03
 * FV - FE --> fv = failed validation, fe = failed error
 */
@Service
@Transactional
public class ProdukService implements IService<Produk>, IReport<Produk> {

    @Autowired
    private ProdukRepo produkRepo;

    @Autowired
    private LogProdukRepo logProdukRepo;

    @Autowired
    private ModelMapper modelMapper ;

    private String className = "ProdukService";

    @Autowired
    private TransformPagination transformPagination;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private PdfGenerator pdfGenerator;


    private StringBuilder sBuild = new StringBuilder();

    @Override
    public ResponseEntity<Object> save(Produk produk, HttpServletRequest request) {

        if(produk==null){
            return GlobalResponse.objectNull("TRN03FV001",request);
        }
        try{
            produkRepo.save(produk);
            logProdukRepo.save(mapToModelLog(produk,1L,'s'));
        }catch (Exception e){
            LoggingFile.logException(className,"save(Produk produk, HttpServletRequest request) SQLException Request Package : "+RequestCapture.allRequest(request),e);
            return GlobalResponse.internalServerError("TRN03FE001",request);
        }

        return GlobalResponse.dataBerhasilDisimpan(request);
    }

    @Override
    public ResponseEntity<Object> update(Long id, Produk produk, HttpServletRequest request) {
        if(produk==null){
            return GlobalResponse.objectNull("TRN03FV011",request);
        }
        try{
            Optional<Produk> optionalProduk= produkRepo.findById(id);
            if(!optionalProduk.isPresent()){
                return GlobalResponse.dataTidakDitemukan("TRN03FV012",request);
            }
            Produk nextProduk = optionalProduk.get();
            nextProduk.setNama(produk.getNama());
            nextProduk.setDeskripsi(produk.getDeskripsi());
            nextProduk.setStok(produk.getStok());
            nextProduk.setModel(produk.getModel());
            nextProduk.setMerk(produk.getMerk());
            nextProduk.setWarna(produk.getWarna());
            nextProduk.setKategoriProduk(produk.getKategoriProduk());
            nextProduk.setSuppliers(produk.getSuppliers());
            nextProduk.setModifiedBy(1L);
            nextProduk.setModifiedAt(new Date());
            logProdukRepo.save(mapToModelLog(nextProduk,1L,'u'));
        }catch (Exception e){
            LoggingFile.logException(className,"update(Long id, Produk produk, HttpServletRequest request) Request Package : "+RequestCapture.allRequest(request),e);
            return GlobalResponse.internalServerError("TRN03FE011",request);
        }
        return GlobalResponse.dataBerhasilDiubah(request);
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        if(id==null){
            return GlobalResponse.objectNull("TRN03FV021",request);
        }
        if(id==0){
            return GlobalResponse.objectNull("TRN03FV022",request);
        }
        try{
            Optional<Produk> optionalProduk= produkRepo.findById(id);
            if(!optionalProduk.isPresent()){
                return GlobalResponse.dataTidakDitemukan("TRN03FV023",request);
            }
            produkRepo.deleteById(id);
            logProdukRepo.save(mapToModelLog(optionalProduk.get(),1L,'d'));
        }catch (Exception e){
            LoggingFile.logException(className,"delete(Long id, HttpServletRequest request) Request Package : "+RequestCapture.allRequest(request),e);
            return GlobalResponse.internalServerError("TRN03FE021",request);
        }
        return GlobalResponse.dataBerhasilDihapus(request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<Produk> pageData = null;
        Map<String,Object> mapResponse = null;
        try{
            pageData =  produkRepo.findAll(pageable);
            if(pageData.isEmpty()){
                return GlobalResponse.dataTidakDitemukan("TRN03FV031",request);
            }
            mapResponse = transformPagination.transform(mapToModelMapper(pageData.getContent()),
                    pageData,"id",null);

        }catch (Exception e){
            LoggingFile.logException(className,"findAll(Pageable pageable, HttpServletRequest request) Request Package : "+RequestCapture.allRequest(request),e);
            return GlobalResponse.internalServerError("TRN03FE031",request);
        }
        return GlobalResponse.dataDitemukan(mapResponse,request);
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        if(id==null){
            return GlobalResponse.objectNull("TRN03FV041",request);
        }
        if(id==0){
            return GlobalResponse.objectNull("TRN03FV042",request);
        }
        Optional<Produk> optionalProduk = null;
        try{
            optionalProduk= produkRepo.findById(id);
            if(!optionalProduk.isPresent()){
                return GlobalResponse.dataTidakDitemukan("TRN03FV043",request);
            }
        }catch (Exception e){
            LoggingFile.logException(className,"findById(Long id, HttpServletRequest request) Request Package : "+RequestCapture.allRequest(request),e);
            return GlobalResponse.internalServerError("TRN03FE041",request);
        }
        return GlobalResponse.dataDitemukan(mapToDTOMapper(optionalProduk.get()), request);
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String column, String value, HttpServletRequest request) {
        Page<Produk> page = null;
        Map<String,Object> mapResponse = null;
        try{
            switch (column) {
                case "nama":page= produkRepo.findByNamaContainsIgnoreCase(value,pageable);break;
                case "deskripsi":page= produkRepo.findByDeskripsiContainsIgnoreCase(value,pageable);break;
                case "warna":page= produkRepo.findByWarnaContainsIgnoreCase(value,pageable);break;
                case "merk":page= produkRepo.findByMerkContainsIgnoreCase(value,pageable);break;
                case "model":page= produkRepo.findByModelContainsIgnoreCase(value,pageable);break;
                case "stok":page= produkRepo.cariStok(value,pageable);break;
                default:page = produkRepo.findAll(pageable);break;
            }
            mapResponse = transformPagination.transform(mapToModelMapper(page.getContent()),page,column,value);
        }catch (Exception e){
            LoggingFile.logException(className,"findByParam(Pageable pageable, String column, String value, HttpServletRequest request) Request Package : "+RequestCapture.allRequest(request),e);
            return GlobalResponse.internalServerError("TRN03FE051",request);
        }
        return GlobalResponse.dataDitemukan(mapResponse,request);
    }

    @Override
    public ResponseEntity<Object> uploadExcel(MultipartFile file, HttpServletRequest request) {
        String message = "";
        try{
            if(!ExcelReader.hasWorkBookFormat(file)){
                return GlobalResponse.formatFileHarusExcel("TRN03FV061",request);
            }
            List lt = new ExcelReader(file.getInputStream(),"produk").getDataMap();
            if(lt.isEmpty()){
                return GlobalResponse.fileExcelKosong("TRN03FV062",request);
            }
            List<Produk> listProduk=convertListWorkBookToListEntity(lt,1L);
            List<Produk> ls = produkRepo.saveAll(listProduk);
            logProdukRepo.saveAll(mapToLog(ls));
        }catch (Exception e){
            LoggingFile.logException(className,"uploadExcel(MultipartFile file, HttpServletRequest request) Request Package : "+RequestCapture.allRequest(request),e);
            return GlobalResponse.fileExcelKosong("TRN03FE061",request);
        }
        return GlobalResponse.dataBerhasilDisimpan(request);
    }

    @Override
    public List<Produk> convertListWorkBookToListEntity(List<Map<String, String>> workBookData, Long userId) {
        List<Produk> produkList = new ArrayList<>();
        for (Map<String, String> map : workBookData) {
            Produk produk = new Produk();
            produk.setNama(map.get("NAMA PRODUK"));
            produk.setDeskripsi(map.get("DESKRIPSI PRODUK"));
            produk.setWarna(map.get("WARNA"));
            produk.setModel(map.get("MODEL"));
            produk.setMerk(map.get("MERK"));
            produk.setStok(Integer.parseInt(map.get("STOK")));
            KategoriProduk kategoriProduk = new KategoriProduk();
            kategoriProduk.setId(Long.parseLong(map.get("KATEGORI").toString()));
            produk.setKategoriProduk(kategoriProduk);
            produk.setCreatedBy(userId);
            produkList.add(produk);
        }
        return produkList;
    }

    @Override
    public Object downloadReportExcel(String column, String value,
                                      HttpServletRequest request, HttpServletResponse response) {
        List<Produk> listProduk = null;
        try {
            switch (column){
                case "nama" : listProduk = produkRepo.findByNamaContainsIgnoreCase(value);break;
                case "deskripsi" : listProduk = produkRepo.findByDeskripsiContainsIgnoreCase(value);break;
                case "model" : listProduk = produkRepo.findByModelContainsIgnoreCase(value);break;
                case "warna" : listProduk = produkRepo.findByWarnaContainsIgnoreCase(value);break;
                case "stok" : listProduk = produkRepo.cariStok(value);break;
                case "merk" : listProduk = produkRepo.findByMerkContainsIgnoreCase(value);break;
                default:listProduk= produkRepo.findAll();break;
            }
            if(listProduk.isEmpty()){
                return GlobalResponse.dataTidakDitemukan("TRN03FV071",request);
            }
            /** langkah pertama , convert dulu object ke dto yang benar-benar akan di display di file excel */
            List<RespProdukDTO> listDTO = mapToModelMapper(listProduk);
            new MappingReport().mappingReportExcel(listDTO,
                    "produk",
                    new RespProdukDTO(),
                    response);
        }catch (Exception e){
            return GlobalResponse.internalServerError("TRN03FE071",request);
        }

        return "";
    }

    @Override
    public Object downloadReportPDF(String column, String value, HttpServletRequest request, HttpServletResponse response) {
        List<Produk> listProduk = null;
        try {
            switch (column){
                case "nama" : listProduk = produkRepo.findByNamaContainsIgnoreCase(value);break;
                case "deskripsi" : listProduk = produkRepo.findByDeskripsiContainsIgnoreCase(value);break;
                case "model" : listProduk = produkRepo.findByModelContainsIgnoreCase(value);break;
                case "warna" : listProduk = produkRepo.findByWarnaContainsIgnoreCase(value);break;
                case "stok" : listProduk = produkRepo.cariStok(value);break;
                case "merk" : listProduk = produkRepo.findByMerkContainsIgnoreCase(value);break;
                default:listProduk= produkRepo.findAll();break;
            }
            if(listProduk.isEmpty()){
                return GlobalResponse.dataTidakDitemukan("TRN03FV081",request);
            }
            List<RespProdukDTO> listDTO = mapToModelMapper(listProduk);
            new MappingReport().mappingReportPDF(listDTO,
                    "produk",
                    "REPORT DATA KATEGORI PRODUK",
                    new RespProdukDTO(),springTemplateEngine,pdfGenerator,response);
            }catch (Exception e){
                return GlobalResponse.internalServerError("TRN03FE081",request);
            }
        return "Download Report PDF Berhasil";
    }

    /** cara manual untuk dto validasi request dalam bentuk single object */
    public Produk mapToModel(ValProdukDTO valProdukDTO){
        Produk produk = new Produk();
        produk.setNama(valProdukDTO.getNama());
        produk.setModel(valProdukDTO.getModel());
        produk.setWarna(valProdukDTO.getWarna());
        produk.setMerk(valProdukDTO.getMerk());
        produk.setDeskripsi(valProdukDTO.getDeskripsi());
        produk.setStok(valProdukDTO.getStok());
        KategoriProduk kategoriProduk = new KategoriProduk();
        kategoriProduk.setId(valProdukDTO.getKategoriProduk().getId());
        produk.setKategoriProduk(kategoriProduk);
        List<RelSupplierDTO> listSupplierDTO = valProdukDTO.getSuppliers();
        List<Supplier> listSupplier = new ArrayList<>();
        for (RelSupplierDTO supplierDTO : listSupplierDTO) {
            Supplier supplier = new Supplier();
            supplier.setId(supplierDTO.getId());
            listSupplier.add(supplier);
        }
        produk.setSuppliers(listSupplier);
        return produk;
    }

    private List<LogProduk> mapToLog(List<Produk> produkList){
        List<LogProduk> logProdukList = new ArrayList<>();
        for (Produk produk : produkList) {
            LogProduk logProduk = new LogProduk();
            logProduk.setIdProduk(produk.getId());
            logProduk.setNama(produk.getNama());
            logProduk.setWarna(produk.getWarna());
            logProduk.setDeskripsi(produk.getDeskripsi());
            logProduk.setStok(produk.getStok());
            logProduk.setMerk(produk.getMerk());
            logProduk.setModel(produk.getModel());
            logProduk.setIdKategoriProduk(produk.getKategoriProduk().getId());
            logProduk.setCreatedBy(produk.getCreatedBy());
            logProduk.setFlag('s');
            logProdukList.add(logProduk);
        }
        return logProdukList;
    }
//    public RespProdukDTO mapToDTO(Produk produk){
//        RespProdukDTO respProdukDTO = new RespProdukDTO();
//        respProdukDTO.setId(produk.getId());
//        respProdukDTO.setNama(produk.getNama());
//        respProdukDTO.setAlamat(produk.getAlamat());
//        return respProdukDTO;
//    }

    public RespProdukDTO mapToDTOMapper(Produk produk){
        return modelMapper.map(produk, RespProdukDTO.class);
    }

//    /** cara manual untuk dto response dalam bentuk multi object array */
//    public List<RespProdukDTO> mapToModel(List<Produk> produkList){
//        List<RespProdukDTO> list = new ArrayList<RespProdukDTO>();
//        for (Produk produk : produkList) {
//            RespProdukDTO respProdukDTO = new RespProdukDTO();
//            respProdukDTO.setId(produk.getId());
//            respProdukDTO.setNama(produk.getNama());
//            respProdukDTO.setAlamat(produk.getAlamat());
//            list.add(respProdukDTO);
//        }
//        return list;
//    }

    public LogProduk mapToModelLog(Produk produk,Long userId,Character flag){
        LogProduk logProduk = new LogProduk();
        logProduk.setIdProduk(produk.getId());
        logProduk.setNama(produk.getNama());
        logProduk.setWarna(produk.getWarna());
        logProduk.setDeskripsi(produk.getDeskripsi());
        logProduk.setStok(produk.getStok());
        logProduk.setMerk(produk.getMerk());
        logProduk.setModel(produk.getModel());
        logProduk.setIdKategoriProduk(produk.getKategoriProduk().getId());
        logProduk.setCreatedBy(userId);
        logProduk.setFlag(flag);
        return logProduk;
    }

    public Produk mapToModelMapper(ValProdukDTO valProdukDTO){
        return modelMapper.map(valProdukDTO, Produk.class);
    }
//    public List<RespProdukDTO> mapToModelMapper(List<Produk> produkList){
//        return modelMapper.map(produkList, new TypeToken<List<RespProdukDTO>>(){}.getType());
//    }
    public List<RespProdukDTO> mapToModelMapper(List<Produk> produkList){
        List<RespProdukDTO> respProdukDTOList = new ArrayList<>();
        for (Produk produk : produkList) {
            RespProdukDTO respProdukDTO = new RespProdukDTO();
            respProdukDTO.setNama(produk.getNama());
            respProdukDTO.setModel(produk.getModel());
            respProdukDTO.setWarna(produk.getWarna());
            respProdukDTO.setMerk(produk.getMerk());
            respProdukDTO.setDeskripsi(produk.getDeskripsi());
            respProdukDTO.setStok(produk.getStok());
            respProdukDTO.setIdKategoriProduk(produk.getKategoriProduk().getId());
            respProdukDTO.setNamaKategoriProduk(produk.getKategoriProduk().getNama());
            respProdukDTO.setDeskripsiKategoriProduk(produk.getKategoriProduk().getDeskripsi());
            respProdukDTOList.add(respProdukDTO);
        }
        return respProdukDTOList;
    }
}