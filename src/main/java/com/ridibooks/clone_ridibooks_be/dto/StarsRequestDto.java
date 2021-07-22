package com.ridibooks.clone_ridibooks_be.dto;

import lombok.Getter;

@Getter
public class StarsRequestDto {
    private Double avgStar;
    private Long totalCount;
    private int star5Count;
    private int star4Count;
    private int star3Count;
    private int star2Count;
    private int star1Count;
}