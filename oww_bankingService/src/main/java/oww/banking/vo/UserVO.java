package oww.banking.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserVO {
	private String userEmail;
	private String name;
	private int userNo;
    private LocalDateTime createdAt;   
    private LocalDateTime updatedAt;   
    private boolean isActive;          
    private String providerId;        
    private String provider;          
    private String role; 
	

}
