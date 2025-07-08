package com.juaracoding.pcmspringboot4.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class MappingReport {
    private StringBuilder sBuild = new StringBuilder();

    public void mappingReportExcel(List ls,
                              String fileTitle,
                              Object objReflection,
                              HttpServletResponse response) throws IllegalAccessException {
        String headerKey = "Content-Disposition";
        sBuild.setLength(0);
        String headerValue = sBuild.append("attachment; filename="+fileTitle+"_").
                append(new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss").format(new Date())).
                append(".xlsx").toString();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(headerKey, headerValue);
        Map<String,Object> map = convertClassToMap(objReflection);
        List<String> listTemp = new ArrayList<>();
        for (Map.Entry<String,Object> entry : map.entrySet()) {
            listTemp.add(entry.getKey());
        }
        int intListTemp = listTemp.size();
        String [] headerArr = new String[intListTemp];//kolom judul di excel
        String [] loopDataArr = new String[intListTemp];//kolom judul java reflection

        for (int i = 0; i < intListTemp; i++) {
            headerArr[i] = camelToStandard(listTemp.get(i));
            loopDataArr[i] = listTemp.get(i);
        }
        int intListDTOSize = ls.size();
        String [][] strBody = new String[intListDTOSize][intListTemp];
        for (int i = 0; i < intListDTOSize; i++) {
            map = convertClassToMap(ls.get(i));
            for (int j = 0; j < intListTemp; j++) {
                strBody[i][j] = String.valueOf(map.get(loopDataArr[j]));
            }
        }
        new ExcelWriter(strBody,headerArr,"sheet-1",response);
    }
    public void mappingReportPDF(List ls,
                                   String fileTitle,
                                   String reportTitle,
                                   Object objReflection,
                                   SpringTemplateEngine springTemplateEngine,
                                   PdfGenerator pdfGenerator,
                                   HttpServletResponse response) throws IllegalAccessException {
        int intRepGroupMenuDTOSize = ls.size();
        Map<String,Object> mapResponse = new HashMap<>();
        String strHtml = null;
        Context context = new Context();
        Map<String,Object> mapColumnName = convertClassToMap(objReflection);
        List<String> listTemp = new ArrayList<>();
        List<String> listHelper = new ArrayList<>();
        for (Map.Entry<String,Object> m:mapColumnName.entrySet()) {
            listTemp.add(camelToStandard(m.getKey()));
            listHelper.add(m.getKey());
        }

        Map<String,Object> mapTemp = null;
        List<Map<String,Object>> listMap = new ArrayList<>();
        for (int i = 0; i < intRepGroupMenuDTOSize; i++) {
            mapTemp = convertClassToMap(ls.get(i));
            listMap.add(mapTemp);
        }

        mapResponse.put("title",reportTitle);
        mapResponse.put("listKolom",listTemp);
        mapResponse.put("timestamp",new Date());
        mapResponse.put("listHelper",listHelper);
        mapResponse.put("listContent",listMap);
        mapResponse.put("totalData",intRepGroupMenuDTOSize);
        mapResponse.put("username","Paul");
        context.setVariables(mapResponse);
        strHtml = springTemplateEngine.process("report/global-report",context);
        pdfGenerator.htmlToPdf(strHtml,fileTitle,response);
    }

    private Map<String,Object> convertClassToMap(Object object) throws IllegalAccessException {
        Map<String,Object> map = new LinkedHashMap<>();
        Field[] fields = object.getClass().getDeclaredFields();//Reflection
        for(Field field : fields){
            field.setAccessible(true);
            map.put(field.getName(),field.get(object));
        }
        return map;
    }

    /** fungsi ini hanya digunakan jika penulisan variable menggunakan convention naming java */
    private String camelToStandard(String camel){
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