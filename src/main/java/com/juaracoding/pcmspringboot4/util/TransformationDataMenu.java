package com.juaracoding.pcmspringboot4.util;

import com.juaracoding.pcmspringboot4.dto.validasi.MenuLoginDTO;
import org.modelmapper.ModelMapper;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TransformationDataMenu {
    private int intListMenuSize = 0;
    private final List<Object> lObj = new ArrayList<>();
    private ModelMapper modelMapper = new ModelMapper();

    public List<Object> doTransformAksesMenuLogin(List<MenuLoginDTO> z){
        lObj.clear();
        intListMenuSize = z.size();
        /**
         * Grouping berdasarkan field getNamaGroupMenu
         */
        Map<String, List<MenuLoginDTO>> map = groupBy(z, MenuLoginDTO::getNamaGroupMenu);
        Map<String ,Object> map2 = null;
        for (Map.Entry<String,List<MenuLoginDTO>> x:
                map.entrySet()) {
            map2 = new HashMap<>();
            map2.put("group",x.getKey());
            map2.put("subMenu",x.getValue());
            lObj.add(map2);
        }
        return lObj;
    }

    public <E, K> Map<K, List<E>> groupBy(List<E> list, Function<E, K> keyFunction) {
        return Optional.ofNullable(list)
                .orElseGet(ArrayList::new)
                .stream().skip(0)
                .collect(Collectors.groupingBy(keyFunction,Collectors.toList()));
    }

}