package oww.banking.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SafeboxVO {
	    private int safeboxId;
	    private String userEmail;
	    private BigDecimal balance;
	    private LocalDateTime createdAt;
	    
	    public SafeboxVO(String userEmail, BigDecimal balance) {
	        this.userEmail = userEmail;
	        this.balance = balance;
	    }
	    
	    
}