package com.mindgeek.samplecode.datatransferobject.response;

import org.springframework.data.domain.Page;

public class PaginationData {
    Long offset;
    Integer pageNumber;
    Integer totalPages;
    Long totalRecords;


    public PaginationData(Page page){
        this.offset = page.getPageable().getOffset();
        this.pageNumber = page.getPageable().getPageNumber();
        this.totalPages = page.getTotalPages();
        this.totalRecords = page.getTotalElements();
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Long totalRecords) {
        this.totalRecords = totalRecords;
    }
}
