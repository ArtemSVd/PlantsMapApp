package org.example.plantsmap.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class ListResult<T> {

    private List<T> items;
    private Long total;

    public ListResult(List<T> items, Long total) {
        this.items = items;
        this.total = total;
    }

    public ListResult(List<T> items) {
        if (items != null) {
            this.total = (long) items.size();
        }
        this.items = items;
    }

    @SuppressWarnings("rawtypes")
    public static final ListResult EMPTY_LIST = new ListResult<>(Collections.emptyList());

    @SuppressWarnings("unchecked")
    public static <T> ListResult<T> empty() {
        return (ListResult<T>) EMPTY_LIST;
    }

}

