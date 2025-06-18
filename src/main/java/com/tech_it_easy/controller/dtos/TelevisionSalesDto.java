package com.tech_it_easy.controller.dtos;

public class TelevisionSalesDto {
    public Long id;
    public Double price;
    public int originalStock;
    public int sold;

    public TelevisionSalesDto(Long id, Double price, int originalStock, int sold) {
        this.id = id;
        this.price = price;
        this.originalStock = originalStock;
        this.sold = sold;
    }
}


