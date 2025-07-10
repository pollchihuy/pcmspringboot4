package com.juaracoding.pcmspringboot4.service;

import com.juaracoding.pcmspringboot4.core.IReport;
import com.juaracoding.pcmspringboot4.core.IService;
import com.juaracoding.pcmspringboot4.dto.response.RespKategoriProdukDTO;
import com.juaracoding.pcmspringboot4.dto.validasi.ValKategoriProdukDTO;
import com.juaracoding.pcmspringboot4.model.KategoriProduk;
import com.juaracoding.pcmspringboot4.model.LogKategoriProduk;
import com.juaracoding.pcmspringboot4.repo.KategoriProdukRepo;
import com.juaracoding.pcmspringboot4.repo.LogKategoriProdukRepo;
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
 * Modul Code : 01
 * FV - FE --> fv = failed validation, fe = failed error
 */
@Service
@Transactional
public class KategoriProdukService implements IService<KategoriProduk>, IReport<KategoriProduk> {

    @Autowired
    private KategoriProdukRepo kategoriProdukRepo;

    @Autowired
    private LogKategoriProdukRepo logKategoriProdukRepo;

    @Autowired
    private ModelMapper modelMapper ;

    private String className = "KategoriProdukService";

    @Autowired
    private TransformPagination transformPagination;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private PdfGenerator pdfGenerator;


    private StringBuilder sBuild = new StringBuilder();

    @Override
    public ResponseEntity<Object> save(KategoriProduk kategoriProduk, HttpServletRequest request) {

        if(kategoriProduk==null){
            return GlobalResponse.objectNull("TRN01FV001",request);
        }
        try{
            kategoriProdukRepo.save(kategoriProduk);
            logKategoriProdukRepo.save(mapToModelLog(kategoriProduk,1L,'s'));
        }catch (Exception e){
            LoggingFile.logException(className,"save(KategoriProduk kategoriProduk, HttpServletRequest request) SQLException Request Package : "+RequestCapture.allRequest(request),e);
            return GlobalResponse.internalServerError("TRN01FE001",request);
        }

        return GlobalResponse.dataBerhasilDisimpan(request);
    }

    @Override
    public ResponseEntity<Object> update(Long id, KategoriProduk kategoriProduk, HttpServletRequest request) {
        if(kategoriProduk==null){
            return GlobalResponse.objectNull("TRN01FV011",request);
        }
        try{
            Optional<KategoriProduk> optionalKategoriProduk= kategoriProdukRepo.findById(id);
            if(!optionalKategoriProduk.isPresent()){
                return GlobalResponse.dataTidakDitemukan("TRN01FV012",request);
            }
            KategoriProduk nextKategoriProduk = optionalKategoriProduk.get();
            nextKategoriProduk.setNama(kategoriProduk.getNama());
            nextKategoriProduk.setDeskripsi(kategoriProduk.getDeskripsi());
            nextKategoriProduk.setNotes(kategoriProduk.getNotes());
            nextKategoriProduk.setModifiedBy(1L);
            nextKategoriProduk.setModifiedAt(new Date());
            logKategoriProdukRepo.save(mapToModelLog(nextKategoriProduk,1L,'u'));
        }catch (Exception e){
            LoggingFile.logException(className,"update(Long id, KategoriProduk kategoriProduk, HttpServletRequest request) Request Package : "+RequestCapture.allRequest(request),e);
            return GlobalResponse.internalServerError("TRN01FE011",request);
        }
        return GlobalResponse.dataBerhasilDiubah(request);
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        if(id==null){
            return GlobalResponse.objectNull("TRN01FV021",request);
        }
        if(id==0){
            return GlobalResponse.objectNull("TRN01FV022",request);
        }
        try{
            Optional<KategoriProduk> optionalKategoriProduk= kategoriProdukRepo.findById(id);
            if(!optionalKategoriProduk.isPresent()){
                return GlobalResponse.dataTidakDitemukan("TRN01FV023",request);
            }
            kategoriProdukRepo.deleteById(id);
            logKategoriProdukRepo.save(mapToModelLog(optionalKategoriProduk.get(),1L,'d'));
        }catch (Exception e){
            LoggingFile.logException(className,"delete(Long id, HttpServletRequest request) Request Package : "+RequestCapture.allRequest(request),e);
            return GlobalResponse.internalServerError("TRN01FE021",request);
        }
        return GlobalResponse.dataBerhasilDihapus(request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<KategoriProduk> pageData = null;
        Map<String,Object> mapResponse = null;
        try{
            pageData =  kategoriProdukRepo.findAll(pageable);
            if(pageData.isEmpty()){
                return GlobalResponse.dataTidakDitemukan("TRN01FV031",request);
            }
            mapResponse = transformPagination.transform(mapToModelMapper(pageData.getContent()),
                    pageData,"id",null);

        }catch (Exception e){
            LoggingFile.logException(className,"findAll(Pageable pageable, HttpServletRequest request) Request Package : "+RequestCapture.allRequest(request),e);
            return GlobalResponse.internalServerError("TRN01FE031",request);
        }
        return GlobalResponse.dataDitemukan(mapResponse,request);
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        if(id==null){
            return GlobalResponse.objectNull("TRN01FV041",request);
        }
        if(id==0){
            return GlobalResponse.objectNull("TRN01FV042",request);
        }
        Optional<KategoriProduk> optionalKategoriProduk = null;
        try{
            optionalKategoriProduk= kategoriProdukRepo.findById(id);
            if(!optionalKategoriProduk.isPresent()){
                return GlobalResponse.dataTidakDitemukan("TRN01FV043",request);
            }
        }catch (Exception e){
            LoggingFile.logException(className,"findById(Long id, HttpServletRequest request) Request Package : "+RequestCapture.allRequest(request),e);
            return GlobalResponse.internalServerError("TRN01FE041",request);
        }
        return GlobalResponse.dataDitemukan(mapToDTOMapper(optionalKategoriProduk.get()), request);
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String column, String value, HttpServletRequest request) {
        Page<KategoriProduk> page = null;
        Map<String,Object> mapResponse = null;
        try{
            switch (column) {
                case "nama":page=kategoriProdukRepo.findByNamaContainsIgnoreCase(value,pageable);break;
                case "deskripsi":page=kategoriProdukRepo.findByDeskripsiContainsIgnoreCase(value,pageable);break;
                default:page = kategoriProdukRepo.findAll(pageable);break;
            }
            mapResponse = transformPagination.transform(mapToModelMapper(page.getContent()),page,column,value);
        }catch (Exception e){
            LoggingFile.logException(className,"findByParam(Pageable pageable, String column, String value, HttpServletRequest request) Request Package : "+RequestCapture.allRequest(request),e);
            return GlobalResponse.internalServerError("TRN01FE051",request);
        }
        return GlobalResponse.dataDitemukan(mapResponse,request);
    }

    @Override
    public ResponseEntity<Object> uploadExcel(MultipartFile file, HttpServletRequest request) {
        String message = "";
        try{
            if(!ExcelReader.hasWorkBookFormat(file)){
                return GlobalResponse.formatFileHarusExcel("TRN01FV061",request);
            }
            List lt = new ExcelReader(file.getInputStream(),"kategori").getDataMap();
            if(lt.isEmpty()){
                return GlobalResponse.fileExcelKosong("TRN01FV062",request);
            }
            kategoriProdukRepo.saveAll(convertListWorkBookToListEntity(lt,1L));
        }catch (Exception e){
            LoggingFile.logException(className,"uploadExcel(MultipartFile file, HttpServletRequest request) Request Package : "+RequestCapture.allRequest(request),e);
            return GlobalResponse.fileExcelKosong("TRN01FE061",request);
        }
        return GlobalResponse.dataBerhasilDisimpan(request);
    }

    /** contoh saja untuk manual upload dengan mapping di class lain */
    public ResponseEntity<Object> uploadExcelManual(MultipartFile file, HttpServletRequest request) {
        String message = "";
        try{
            if(!ExcelReader.hasWorkBookFormat(file)){
                return GlobalResponse.formatFileHarusExcel("TRN01FV061",request);
            }
            List<KategoriProduk> list = new UploadExcel().dataKategoriProduk(file.getInputStream(),"kategori",1L);
            if(list.isEmpty()){
                return GlobalResponse.fileExcelKosong("TRN01FV062",request);
            }
            kategoriProdukRepo.saveAll(list);
        }catch (Exception e){
            LoggingFile.logException(className,"uploadExcelManual(MultipartFile file, HttpServletRequest request) Request Package : "+RequestCapture.allRequest(request),e);
            return GlobalResponse.fileExcelKosong("TRN01FE061",request);
        }
        return GlobalResponse.dataBerhasilDisimpan(request);
    }

    @Override
    public List<KategoriProduk> convertListWorkBookToListEntity(List<Map<String, String>> workBookData, Long userId) {

//        Map<String,Object> m  = new HashMap<>();
//        try{
//            int intCheck = 0;
//            for (Map<String, String> map : workBookData) {
//                String strNama = map.get("NAMA KATEGORI PRODUK");
//                String strDeskripsi = map.get("DESKRIPSI KATEGORI PRODUK");
//                String strNotes = map.get("NOTES KATEGORI PRODUK");
//                intCheck = GlobalFunction.matchingPattern(strNama,"^[a-zA-Z\\s]{10,50}$")==false?1:intCheck;
//                intCheck = GlobalFunction.matchingPattern(strDeskripsi,"^[a-zA-Z\\s]{20,255}$")==false?1:intCheck;
//                intCheck = GlobalFunction.matchingPattern(strNotes,"^[a-zA-Z\\s]{20,255}$")==false?1:intCheck;
//                if(intCheck!=0){
//                    m.put("ok",false);
//                    break;
//                }
//            }
//
//        }catch (Exception e){
//
//        }
        List<KategoriProduk> kategoriProdukList = new ArrayList<>();
        for (Map<String, String> map : workBookData) {
            KategoriProduk kategoriProduk = new KategoriProduk();
            kategoriProduk.setNama(map.get("NAMA KATEGORI PRODUK"));
            kategoriProduk.setDeskripsi(map.get("DESKRIPSI KATEGORI PRODUK"));
            kategoriProduk.setNotes(map.get("NOTES KATEGORI PRODUK"));
            kategoriProduk.setCreatedBy(userId);
            kategoriProdukList.add(kategoriProduk);
        }
//        m.put("data",kategoriProdukList);
        return kategoriProdukList;
    }

    @Override
    public Object downloadReportExcel(String column, String value,
                                      HttpServletRequest request, HttpServletResponse response) {
        List<KategoriProduk> listKategoriProduk = null;
        try {
            switch (column){
                case "nama" : listKategoriProduk = kategoriProdukRepo.findByNamaContainsIgnoreCase(value);break;
                case "deskripsi" : listKategoriProduk = kategoriProdukRepo.findByDeskripsiContainsIgnoreCase(value);break;
                case "notes" : listKategoriProduk = kategoriProdukRepo.findByNotesContainsIgnoreCase(value);break;
                default:listKategoriProduk=kategoriProdukRepo.findAll();break;
            }
            if(listKategoriProduk.isEmpty()){
                return GlobalResponse.dataTidakDitemukan("TRN01FV071",request);
            }
            /** langkah pertama , convert dulu object ke dto yang benar-benar akan di display di file excel */
            List<RespKategoriProdukDTO> listDTO = mapToModelMapper(listKategoriProduk);
            new MappingReport().mappingReportExcel(listDTO,
                    "kategori-produk",
                    new RespKategoriProdukDTO(),
                    response);
        }catch (Exception e){
            return GlobalResponse.internalServerError("TRN01FE071",request);
        }

        return "";
    }

    public Object downloadReportExcelManual(String column, String value,
                                            HttpServletRequest request,
                                            HttpServletResponse response) {
        List<KategoriProduk> listKategoriProduk = null;
        try {
            switch (column){
                case "nama" : listKategoriProduk = kategoriProdukRepo.findByNamaContainsIgnoreCase(value);break;
                case "deskripsi" : listKategoriProduk = kategoriProdukRepo.findByDeskripsiContainsIgnoreCase(value);break;
                case "notes" : listKategoriProduk = kategoriProdukRepo.findByNotesContainsIgnoreCase(value);break;
                default:listKategoriProduk=kategoriProdukRepo.findAll();break;
            }
            if(listKategoriProduk.isEmpty()){
                return GlobalResponse.dataTidakDitemukan("TRN01FV071",request);
            }
            String headerKey = "Content-Disposition";
            sBuild.setLength(0);
            String headerValue = sBuild.append("attachment; filename=kategori-produk_").
                    append(new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss").format(new Date())).
                    append(".xlsx").toString();// mime type / content type
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader(headerKey, headerValue);
            int listSize = listKategoriProduk.size();
            String [] headerArr = new String[4];//kolom judul di excel

            headerArr[0]="ID";
            headerArr[1]="NAMA";
            headerArr[2]="DESKRIPSI";
            headerArr[3]="NOTEz";

            String [][] strBody = new String[listSize][4];
            for (int i = 0; i < listSize; i++) {
                strBody[i][0]=listKategoriProduk.get(i).getId().toString();
                strBody[i][1]=listKategoriProduk.get(i).getNama().toString();
                strBody[i][2]=listKategoriProduk.get(i).getDeskripsi().toString();
                strBody[i][3]=listKategoriProduk.get(i).getNotes().toString()+" Data ke 4";
            }
            new ExcelWriter(strBody,headerArr,"sheet-1",response);
        }catch (Exception e){
            return GlobalResponse.internalServerError("TRN01FE071",request);
        }
        return "File Berhasil Di download";
    }

    @Override
    public Object downloadReportPDF(String column, String value, HttpServletRequest request, HttpServletResponse response) {
        List<KategoriProduk> listKategoriProduk = null;
        try {
            switch (column){
                case "nama" : listKategoriProduk = kategoriProdukRepo.findByNamaContainsIgnoreCase(value);break;
                case "deskripsi" : listKategoriProduk = kategoriProdukRepo.findByDeskripsiContainsIgnoreCase(value);break;
                case "notes" : listKategoriProduk = kategoriProdukRepo.findByNotesContainsIgnoreCase(value);break;
                default:listKategoriProduk=kategoriProdukRepo.findAll();break;
            }
            if(listKategoriProduk.isEmpty()){
                return GlobalResponse.dataTidakDitemukan("TRN01FV081",request);
            }
            List<RespKategoriProdukDTO> listDTO = mapToModelMapper(listKategoriProduk);
            new MappingReport().mappingReportPDF(listDTO,
                    "kategori-produk",
                    "REPORT DATA KATEGORI PRODUK",
                    new RespKategoriProdukDTO(),springTemplateEngine,pdfGenerator,response);
            }catch (Exception e){
                return GlobalResponse.internalServerError("TRN01FE081",request);
            }
        return "Download Report PDF Berhasil";
    }

    public Object downloadReportPDFManual(String column, String value, HttpServletRequest request, HttpServletResponse response) {
        List<KategoriProduk> listKategoriProduk = null;
        try {
            switch (column){
                case "nama" : listKategoriProduk = kategoriProdukRepo.findByNamaContainsIgnoreCase(value);break;
                case "deskripsi" : listKategoriProduk = kategoriProdukRepo.findByDeskripsiContainsIgnoreCase(value);break;
                case "notes" : listKategoriProduk = kategoriProdukRepo.findByNotesContainsIgnoreCase(value);break;
                default:listKategoriProduk=kategoriProdukRepo.findAll();break;
            }
            if(listKategoriProduk.isEmpty()){
                return GlobalResponse.dataTidakDitemukan("TRN01FV081",request);
            }
            List<RespKategoriProdukDTO> listDTO = mapToModelMapper(listKategoriProduk);
            int intRepKategoriProdukDTOSize = listDTO.size();
            Map<String,Object> mapResponse = new HashMap<>();
            String strHtml = null;
            Context context = new Context();

            mapResponse.put("title","REPORT DATA KATEGORI PRODUK");
            mapResponse.put("listContent",listDTO);
            mapResponse.put("timestamp", LocalDateTime.now());
            mapResponse.put("totalData",intRepKategoriProdukDTOSize);
            mapResponse.put("username","Paul");
            context.setVariables(mapResponse);
            strHtml = springTemplateEngine.process("report/kategori-produk",context);
            pdfGenerator.htmlToPdf(strHtml,"kategori-produk",response);
        }catch (Exception e){
            return GlobalResponse.internalServerError("AUT01FE081",request);
        }
        return "Download Report PDF Berhasil";
    }

    /** cara manual untuk dto validasi request dalam bentuk single object */
    public KategoriProduk mapToModel(ValKategoriProdukDTO valKategoriProdukDTO){
        KategoriProduk kategoriProduk = new KategoriProduk();
        kategoriProduk.setNama(valKategoriProdukDTO.getNama());
        kategoriProduk.setDeskripsi(valKategoriProdukDTO.getDeskripsi());
        kategoriProduk.setNotes(valKategoriProdukDTO.getNotes());
        return kategoriProduk;
    }
    public RespKategoriProdukDTO mapToDTO(KategoriProduk kategoriProduk){
        RespKategoriProdukDTO respKategoriProdukDTO = new RespKategoriProdukDTO();
        respKategoriProdukDTO.setId(kategoriProduk.getId());
        respKategoriProdukDTO.setNama(kategoriProduk.getNama());
//        respKategoriProdukDTO.setDeskripsi(kategoriProduk.getDeskripsi());
        return respKategoriProdukDTO;
    }

    public RespKategoriProdukDTO mapToDTOMapper(KategoriProduk kategoriProduk){
        return modelMapper.map(kategoriProduk, RespKategoriProdukDTO.class);
    }

    /** cara manual untuk dto response dalam bentuk multi object array */
    public List<RespKategoriProdukDTO> mapToModel(List<KategoriProduk> kategoriProdukList){
        List<RespKategoriProdukDTO> list = new ArrayList<RespKategoriProdukDTO>();
        for (KategoriProduk kategoriProduk : kategoriProdukList) {
            RespKategoriProdukDTO respKategoriProdukDTO = new RespKategoriProdukDTO();
            respKategoriProdukDTO.setId(kategoriProduk.getId());
            respKategoriProdukDTO.setNama(kategoriProduk.getNama());
//            respKategoriProdukDTO.setDeskripsi(kategoriProduk.getDeskripsi());
            list.add(respKategoriProdukDTO);
        }
        return list;
    }

    public LogKategoriProduk mapToModelLog(KategoriProduk kategoriProduk,Long userId,Character flag){
        LogKategoriProduk logKategoriProduk = new LogKategoriProduk();
        logKategoriProduk.setIdKategoriProduk(kategoriProduk.getId());
        logKategoriProduk.setNama(kategoriProduk.getNama());
        logKategoriProduk.setDeskripsi(kategoriProduk.getDeskripsi());
        logKategoriProduk.setNotes(kategoriProduk.getNotes());
        logKategoriProduk.setCreatedBy(userId);
        logKategoriProduk.setFlag(flag);
        return logKategoriProduk;
    }

    public KategoriProduk mapToModelMapper(ValKategoriProdukDTO valKategoriProdukDTO){
        return modelMapper.map(valKategoriProdukDTO, KategoriProduk.class);
    }
    public List<RespKategoriProdukDTO> mapToModelMapper(List<KategoriProduk> kategoriProdukList){
        return modelMapper.map(kategoriProdukList, new TypeToken<List<RespKategoriProdukDTO>>(){}.getType());
    }
}