package com.example.demo.vo;

public class ProductVO {

    private Long id;           // 상품 ID (PK)
    private String name;       // 상품명
    private int price;         // 가격
    private String imageUrl;   // 이미지 URL (Cloudinary 등)
    private Long enterpriseId; // 업체 ID (FK)

    // === 기본 생성자 ===
    public ProductVO() {}

    // === 전체 필드 생성자 ===
    public ProductVO(Long id, String name, int price, String imageUrl, Long enterpriseId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.enterpriseId = enterpriseId;
    }

    // === Getter / Setter ===
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }
}
