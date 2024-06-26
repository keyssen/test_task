package com.task.mediasoft.product.controller.model;

import com.task.mediasoft.product.model.dto.ViewProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProductPaginationModel {
    private List<ViewProductDTO> products;
    private Long totalItems;
    private Integer totalPages;
}