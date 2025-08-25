package com.oww.oww1.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.oww.oww1.vo.EnterpriseVO;

@Mapper
public interface EnterpriseMapper {

    List<EnterpriseVO> findAll();

    EnterpriseVO findById(Long id);

    int insert(EnterpriseVO vo);

    int update(EnterpriseVO vo);

    int delete(Long id);

    Long nextId();
}
