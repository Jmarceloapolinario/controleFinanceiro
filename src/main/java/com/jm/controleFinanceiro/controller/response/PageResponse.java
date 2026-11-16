package com.jm.controleFinanceiro.controller.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class PageResponse<T> extends PageImpl<T> {

    @JsonCreator
    public PageResponse(
            @JsonProperty("content") List<T> content,
            @JsonProperty("number") int number,
            @JsonProperty("size") int size,
            @JsonProperty("totalElements") long totalElements,
            @JsonProperty("totalPages") int totalPages,
            @JsonProperty("last") boolean last,
            @JsonProperty("first") boolean first,
            @JsonProperty("empty") boolean empty
    ) {
        super(content, PageRequest.of(number, size == 0 ? 1 : size), totalElements);
    }

    public PageResponse(Page<T> page) {
        super(page.getContent(), page.getPageable(), page.getTotalElements());
    }
}