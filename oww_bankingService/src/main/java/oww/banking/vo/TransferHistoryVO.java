package oww.banking.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferHistoryVO {
    private int txId;
    private int accountId;
    private String txType; // "TRANSFER_OUT", "TRANSFER_IN"
    private BigDecimal amount;
    private String memo;
    private LocalDateTime txDate;
    
    // 추가 정보
    private String accountNumber;
    private String otherAccountNumber; // 상대방 계좌번호
    private String otherUserName; // 상대방 이름
}