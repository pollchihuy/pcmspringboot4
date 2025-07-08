package com.juaracoding.pcmspringboot4.coretan;

import com.juaracoding.pcmspringboot4.dto.response.RespKategoriProdukDTO;
import com.juaracoding.pcmspringboot4.dto.response.RespProdukDTO;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

public class ContohReflection {
    public static void main(String[] args) {
        RespKategoriProdukDTO respKategoriProdukDTO = new RespKategoriProdukDTO();
        respKategoriProdukDTO.setId(1L);
        respKategoriProdukDTO.setNama("Kategori Produk");
//        respKategoriProdukDTO.setDeskripsi("Deskripsi Kategori Produk");
//
//
//        System.out.println("ID : "+respKategoriProdukDTO.getId());
//        System.out.println("Nama : "+respKategoriProdukDTO.getNama());
//        System.out.println("Deskripsi : "+respKategoriProdukDTO.getDeskripsi());

//        Map<String,Object> map = new HashMap<>();
//        map.put("id",1L);
//        map.put("nama","Kategori Produk");
//        map.put("deskripsi","Deskripsi Kategori Produk");
//
//
//        for (Map.Entry<String,Object> entry : map.entrySet()) {
//            System.out.println("Key : "+entry.getKey()+" Value : "+entry.getValue());
//        }

        try {
            Map<String,Object> map = convertClassToMap(new RespProdukDTO());
            for (Map.Entry<String,Object> entry : map.entrySet()) {
                System.out.println("Key : "+entry.getKey()+" Value : "+entry.getValue());
             }
//            map.get("idKategoriProduk");// untuk ngambil nilai dari interface nya / map nya
//            ID KATEGORI PRODUK --> Nama Kolom
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        System.out.println("After Camel : "+camelToStandard("idKategoriProduk"));
    }

    private static Map<String,Object> convertClassToMap(Object object) throws IllegalAccessException {
        Map<String,Object> map = new LinkedHashMap<>();
        Field[] fields = object.getClass().getDeclaredFields();//Reflection
        for(Field field : fields){
            field.setAccessible(true);
            map.put(field.getName(),field.get(object));
        }
        return map;
    }

    /** fungsi ini hanya digunakan jika penulisan variable menggunakan convention naming java */
    private  static String camelToStandard(String camel){
        StringBuilder sb = new StringBuilder();
        char [] chArr = camel.toCharArray();

        for (int i = 0; i < chArr.length; i++) {
            char c1 = chArr[i];
            if(Character.isUpperCase(c1)){
                sb.append(' ').append(Character.toLowerCase(c1));// mengubah karakter yang sebelumnya huruf kapital menjadi huruf kecil
            }
            else {
                sb.append(c1);
            }
        }
        return sb.toString().toUpperCase();
    }
}