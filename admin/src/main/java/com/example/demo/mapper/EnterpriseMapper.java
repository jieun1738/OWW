package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.EnterpriseVO;

@Mapper
public interface EnterpriseMapper {

    List<EnterpriseVO> findAll();

    EnterpriseVO findById(Long id);

    int insert(EnterpriseVO vo);

    int update(EnterpriseVO vo);

    int delete(Long id);

    Long nextId();
}
