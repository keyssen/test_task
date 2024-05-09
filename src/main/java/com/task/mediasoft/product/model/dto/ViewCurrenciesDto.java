package com.task.mediasoft.product.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Класс, представляющий объект данных о курсах валют.
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ViewCurrenciesDto {
    /**
     * Курс китайского юаня.
     */
    private BigDecimal CNY;
    /**
     * Курс доллара США.
     */
    private BigDecimal USD;
    /**
     * Курс евро.
     */
    private BigDecimal EUR;
}