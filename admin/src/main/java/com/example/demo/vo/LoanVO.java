package com.example.demo.vo;
import lombok.Data;
import java.util.Date;

@Data
public class LoanVO {
    private Long   applicationId;
    private String userEmail;
    private String productName;
    private Long   amount;
    private Integer termMonths;
    private String status;       
    private Date   requestedAt;
    private Date   decidedAt;
    private String decidedBy;
    private String memo;
}
