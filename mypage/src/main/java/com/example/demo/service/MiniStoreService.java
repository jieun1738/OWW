package com.example.demo.service;

import com.example.demo.repo.MiniStoreRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MiniStoreService {

    private final MiniStoreRepository repo;

    public MiniStoreService(MiniStoreRepository repo) {
        this.repo = repo;
    }

    public List<PurchaseView> getPayments(String email){
        return repo.findPayments(email);
    }

    // 화면 바인딩용 뷰 DTO
    public record PurchaseView(
            String id,
            String itemName,
            long price,                 // 원화
            String status,              // 결제 상태 (결제완료 등)
            LocalDateTime paidAt,       // 결제일시
            String imageUrl             // 썸네일 URL (Cloudinary 사용 예정)
    ) {}
}
