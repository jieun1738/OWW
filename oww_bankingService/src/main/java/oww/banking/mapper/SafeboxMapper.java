package oww.banking.mapper;

import oww.banking.vo.SafeboxVO;
import oww.banking.vo.SafeboxGoalVO;
import oww.banking.vo.SafeboxHistoryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Mapper
public interface SafeboxMapper {
    
    // Safebox 관련
    void createSafebox(SafeboxVO safebox);
    SafeboxVO findSafeboxByEmail(String userEmail);
    SafeboxVO findSafeboxBySafeboxId(@Param("safeboxId") int safeboxId);
    void updateSafeboxBalance(Map<String, Object> params);
    boolean existsByEmail(String userEmail);
    
    // SafeboxGoal 관련
    void createSafeboxGoal(SafeboxGoalVO goal);
    List<SafeboxGoalVO> findGoalsBySafeboxId(@Param("safeboxId") int safeboxId);
    SafeboxGoalVO findGoalById(@Param("goalId") int goalId);
    
    
    // SafeboxHistory 관련
    void createSafeboxHistory(SafeboxHistoryVO history);
    List<SafeboxHistoryVO> findHistoryByGoalId(@Param("goalId") int goalId);
    BigDecimal getTotalSavedAmountByGoalId(@Param("goalId") int goalId);
    List<SafeboxHistoryVO> findHistoryByUserEmail(String userEmail);
}