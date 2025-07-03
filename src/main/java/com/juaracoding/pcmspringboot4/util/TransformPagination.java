package com.juaracoding.pcmspringboot4.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TransformPagination {

    private String sortByColumn;
    private String sort;

    String [] sortArr = new String[2];

    public Map<String,Object> transform(List ls, Page page,String column,String value){
        Sort s = page.getSort();
        sortArr = s.toString().split(":");
        sortByColumn = sortArr[0];
        Boolean isSorted = sortByColumn.equals("UNSORTED");
        sortByColumn = isSorted ? "id":sortByColumn;
        sort = isSorted ? "asc":sortArr[1];

        Map<String,Object> m = new HashMap<String,Object>();
        m.put("content",ls);
        m.put("total_data",page.getTotalElements());
        m.put("current_page",page.getNumber());
        m.put("size_per_page",page.getSize());
        m.put("total_pages",page.getTotalPages());
        m.put("sort",sort.trim().toLowerCase());
        m.put("sort_by",sortByColumn);
        m.put("column_name",column);
        m.put("value",value==null?"":value);
        return m;
    }
}
