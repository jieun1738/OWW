package com.example.demo.mapper;

import com.example.demo.vo.LoanVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface LoanMapper {
    List<LoanVO> findAll(@Param("status") String status,
                         @Param("q") String q,
                         @Param("offset") int offset,
                         @Param("limit") int limit);
    int count(@Param("status") String status, @Param("q") String q);

    int approve(@Param("id") long id, @Param("admin") String admin);
    int reject(@Param("id") long id, @Param("admin") String admin, @Param("memo") String memo);
}
