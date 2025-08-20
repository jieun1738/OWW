package com.example.demo.vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class EnterpriseVO {
    private Long id;
    private String name;
    private String region;
    private String imageUrl;
    private transient MultipartFile file;
}
