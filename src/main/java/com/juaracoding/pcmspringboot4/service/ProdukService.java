package com.juaracoding.pcmspringboot4.service;

import com.juaracoding.pcmspringboot4.core.IReport;
import com.juaracoding.pcmspringboot4.core.IService;
import com.juaracoding.pcmspringboot4.dto.relation.RelSupplierDTO;
import com.juaracoding.pcmspringboot4.dto.response.RespKategoriProdukDTO;
import com.juaracoding.pcmspringboot4.dto.response.RespProdukDTO;
import com.juaracoding.pcmspringboot4.dto.validasi.ValProdukDTO;
import com.juaracoding.pcmspringboot4.model.KategoriProduk;
import com.juaracoding.pcmspringboot4.model.Produk;
import com.juaracoding.pcmspringboot4.model.Supplier;
import com.juaracoding.pcmspringboot4.repo.ProdukRepo;
import com.juaracoding.pcmspringboot4.util.GlobalResponse;
import com.juaracoding.pcmspringboot4.util.LoggingFile;
import com.juaracoding.pcmspringboot4.util.TransformPagination;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Platform : Training -> TRN
 * Modul Code : 02
 * FV - FE --> fv = failed validation, fe = failed error
 */
@Service
@Transactional
public class ProdukService implements IService<Produk>, IReport<Produk> {

    @Autowired
    private ProdukRepo produkRepo;

    @Autowired
    private ModelMapper modelMapper ;

    private String className = "ProdukService";

    @Autowired
    private TransformPagination transformPagination;

    @Override
    public ResponseEntity<Object> save(Produk produk, HttpServletRequest request) {

        if(produk==null){
            return GlobalResponse.objectNull("TRN02FV001",request);
        }
        try{
            produkRepo.save(produk);
        }catch (Exception e){
            LoggingFile.logException(className,"save(Produk produk, HttpServletRequest request) SQLException",e);
            return GlobalResponse.internalServerError("TRN02FE001",request);
        }

        return GlobalResponse.dataBerhasilDisimpan(request);
    }

    @Override
    public ResponseEntity<Object> update(Long id, Produk produk, HttpServletRequest request) {
        if(produk==null){
            return GlobalResponse.objectNull("TRN02FV011",request);
        }
        try{

        }catch (Exception e){
            return GlobalResponse.internalServerError("TRN02FE011",request);
        }
        return GlobalResponse.dataBerhasilDisimpan(request);
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<Produk> pageData = null;
        Map<String,Object> mapResponse = null;
        try{
            pageData =  produkRepo.findAll(pageable);
            if(pageData.isEmpty()){
                return GlobalResponse.dataTidakDitemukan("TRN02FV031",request);
            }
            mapResponse = transformPagination.transform(mapToModel(pageData.getContent()),
                    pageData,"id",null);

        }catch (Exception e){
            return GlobalResponse.internalServerError("TRN02FE031",request);
        }
        return GlobalResponse.dataDitemukan(mapResponse,request);
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String column, String value, HttpServletRequest request) {
        Page<Produk> page = null;
        Map<String,Object> mapResponse = null;
        try{
            switch (column) {
                case "nama":page= produkRepo.findByNamaContainsIgnoreCase(value,pageable);break;
                case "deskripsi":page= produkRepo.findByDeskripsiContainsIgnoreCase(value,pageable);break;
                case "model":page= produkRepo.findByModelContainsIgnoreCase(value,pageable);break;
                case "merk":page= produkRepo.findByMerkContainsIgnoreCase(value,pageable);break;
                case "stok":page= produkRepo.cariStok(value,pageable);break;
                case "warna":page= produkRepo.findByWarnaContainsIgnoreCase(value,pageable);break;
                default:page = produkRepo.findAll(pageable);break;
            }
            mapResponse = transformPagination.transform(mapToModelMapper(page.getContent()),page,column,value);
        }catch (Exception e){
            return GlobalResponse.internalServerError("TRN02FE041",request);
        }
        return GlobalResponse.dataDitemukan(mapResponse,request);
    }

    @Override
    public ResponseEntity<Object> uploadExcel(MultipartFile file, HttpServletRequest request) {
        return null;
    }

    @Override
    public List<Produk> convertListWorkBookToListEntity(List<Map<String, String>> workBookData, Long userId) {
        return List.of();
    }

    @Override
    public Object downloadReportExcel(String column, String value, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @Override
    public Object downloadReportPDF(String column, String value, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    /** cara manual untuk dto validasi request dalam bentuk single object */
    public Produk mapToModel(ValProdukDTO valProdukDTO){
        Produk produk = new Produk();
        produk.setNama(valProdukDTO.getNama());
        produk.setDeskripsi(valProdukDTO.getDeskripsi());
        produk.setModel(valProdukDTO.getModel());
        produk.setWarna(valProdukDTO.getWarna());
        produk.setStok(valProdukDTO.getStok());
        produk.setMerk(valProdukDTO.getMerk());
        KategoriProduk kategoriProduk = new KategoriProduk();
        produk.setId(valProdukDTO.getKategoriProduk().getId());
        produk.setKategoriProduk(kategoriProduk);
        List<Supplier> listSupplier = new ArrayList<>();
        for (RelSupplierDTO supplierDTO: valProdukDTO.getSuppliers()) {
            Supplier supplier = new Supplier();
            supplier.setId(supplierDTO.getId());
            listSupplier.add(supplier);
        }
        produk.setSuppliers(listSupplier);
        return produk;
    }

    /** cara manual untuk dto response dalam bentuk multi object array */
    public List<RespProdukDTO> mapToModel(List<Produk> produkList){
        List<RespProdukDTO> list = new ArrayList<RespProdukDTO>();
        for (Produk produk : produkList) {
            RespProdukDTO respProdukDTO = new RespProdukDTO();
            respProdukDTO.setId(produk.getId());
            respProdukDTO.setNama(produk.getNama());
            respProdukDTO.setDeskripsi(produk.getDeskripsi());
            respProdukDTO.setModel(produk.getModel());
            respProdukDTO.setStok(produk.getStok());
            respProdukDTO.setWarna(produk.getWarna());
            respProdukDTO.setMerk(produk.getMerk());
            respProdukDTO.setIdKategoriProduk(produk.getKategoriProduk().getId());
            respProdukDTO.setNamaKategoriProduk(produk.getKategoriProduk().getNama());
            respProdukDTO.setDeskripsiKategoriProduk(produk.getKategoriProduk().getDeskripsi());
            list.add(respProdukDTO);
        }
        return list;
    }

    public Produk mapToModelMapper(ValProdukDTO valProdukDTO){
        return modelMapper.map(valProdukDTO, Produk.class);
    }
    public List<RespProdukDTO> mapToModelMapper(List<Produk> produkList){
        return modelMapper.map(produkList, new TypeToken<List<RespProdukDTO>>(){}.getType());
    }
}