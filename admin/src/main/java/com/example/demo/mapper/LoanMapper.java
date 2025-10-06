package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.vo.LoanVO;

@Mapper
public interface LoanMapper {

    List<LoanVO> findAll(@Param("status") String status,
                         @Param("offset") int offset,
                         @Param("limit") int limit);

    int count(@Param("status") String status);

    LoanVO findById(@Param("id") long id);

    int approve(@Param("id") long id,
                @Param("admin") String admin,
                @Param("memo") String memo);

    int reject(@Param("id") long id,
               @Param("admin") String admin,
               @Param("memo") String memo);
}
