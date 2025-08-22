// src/main/java/com/example/demo/service/PackageService.java
package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mapper.PackageMapper;
import com.example.demo.vo.PackageVO;
import com.example.demo.vo.ProductVO;

@Service
public class PackageService {

    private final PackageMapper packageMapper;

    public PackageService(PackageMapper packageMapper) {
        this.packageMapper = packageMapper;
    }

    public List<PackageVO> findAll(String q, Integer type, int page, int size) {
        int offset = page * size;
        return packageMapper.findAll(q, type, offset, size);
    }

    public int count(String q, Integer type) {
        return packageMapper.count(q, type);
    }

    public PackageVO findByPackageNo(Long packageNo) {
        return packageMapper.findByPackageNo(packageNo);
    }

    @Transactional
    public void insert(PackageVO vo) {
        packageMapper.insert(vo);
    }

    @Transactional
    public void update(PackageVO vo) {
        packageMapper.update(vo);
    }

    @Transactional
    public void updateDiscount(Long packageNo, int discount) {
        packageMapper.updateDiscount(packageNo, discount);
    }

    @Transactional
    public void delete(Long packageNo) {
        packageMapper.delete(packageNo);
    }

    public List<ProductVO> findProductsByCategory(String category) {
        return packageMapper.findProductsByCategory(category);
    }
}
