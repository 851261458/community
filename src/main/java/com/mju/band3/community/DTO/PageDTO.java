package com.mju.band3.community.DTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class PageDTO {
    private List<QuestionDTO> questionDTOS;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pages=new ArrayList<>();
    private Integer totalPage;

    public void setPagination(Integer totalCount, Integer page, Integer size,Integer totalPage) {
        this.page=page;
        this.totalPage=totalPage;
        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page-i>=1){
                pages.add(0,page-i);
            }
            if (page+i<=totalPage){
                pages.add(page+i);
            }
        }

        if (page==1){
            showPrevious=false;
        }else{
            showPrevious=true;
        }


        if (page==totalPage){
            showNext=false;
        }else{
            showNext=true;
        }

        if (pages.contains(1)){
            showFirstPage=false;
        }else{
            showFirstPage=true;
        }

        if (pages.contains(totalPage)){
            showEndPage=false;
        }else{
            showEndPage=true;
        }

    }
}
