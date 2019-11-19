package com.mju.band3.community.Controller.cache;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Data
public class HotTageCache {
    private Map<String,Integer> tag=new HashMap<>();
    private List<String> sort=new ArrayList<>();

    public void Sort(Map<String,Integer> unsortMap){
        Map<String, Integer> result = new LinkedHashMap<>();
        unsortMap.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())  // reversed倒序，不指定类型，会默认为<java.util.Map.Entry<java.lang.Object,V>>
                      .forEachOrdered(x -> result.put(x.getKey(), x.getValue()));
        Iterator<Map.Entry<String,Integer>> iterator=result.entrySet().iterator();
        List<String> innerSort=new ArrayList<>();
        while(iterator.hasNext()){
            Map.Entry<String, Integer> next = iterator.next();
            innerSort.add(next.getKey());
        }
        this.sort=innerSort;
        System.out.println(this.sort);
    }


}
