package com.example.livinglab.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CbsendDTO {
    private Long userid;
    private Long marketcode;
    private String oneliner;
}
