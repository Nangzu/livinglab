package com.example.livinglab.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileDTO {
    private String filename;
    private String filetype;
    private byte[] filedata;
}
