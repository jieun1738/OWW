package com.example.demo.repo;

import com.example.demo.service.MiniStoreService.PurchaseView;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class MiniStoreRepository {

    public List<PurchaseView> findPayments(String email){
        return List.of(
            new PurchaseView("O-20240801-00001","웨딩 플래너 노트", 25000, "결제완료",
                    LocalDateTime.now().minusDays(2),
                    "https://res.cloudinary.com/demo/image/upload/w_120,h_120,c_fill/sample.jpg"),
            new PurchaseView("O-20240728-00002","셀프 촬영 소품세트", 48000, "결제완료",
                    LocalDateTime.now().minusDays(6),
                    "https://res.cloudinary.com/demo/image/upload/w_120,h_120,c_fill/coffee.jpg"),
            new PurchaseView("O-20240710-00003","식전영상 템플릿", 19000, "환불완료",
                    LocalDateTime.now().minusDays(24),
                    "https://res.cloudinary.com/demo/image/upload/w_120,h_120,c_fill/balloons.jpg")
        );
    }
}
