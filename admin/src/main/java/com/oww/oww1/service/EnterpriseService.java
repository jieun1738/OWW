package com.oww.oww1.service;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.Url;
import com.cloudinary.utils.ObjectUtils;
import com.oww.oww1.mapper.EnterpriseMapper;
import com.oww.oww1.vo.EnterpriseVO;

@Service
public class EnterpriseService {

    private static final long MAX_IMAGE_BYTES = 10 * 1024 * 1024; // 10MB

    private final EnterpriseMapper mapper;
    private final Cloudinary cloudinary;

    public EnterpriseService(EnterpriseMapper mapper, Cloudinary cloudinary) {
        this.mapper = mapper;
        this.cloudinary = cloudinary;
    }

    public List<EnterpriseVO> findAll() {
        return mapper.findAll();
    }

    public EnterpriseVO findById(Long id) {
        EnterpriseVO e = mapper.findById(id);
        if (e == null) throw new IllegalArgumentException("업체가 없습니다: " + id);
        return e;
    }

    @Transactional
    public EnterpriseVO saveOrUpdate(EnterpriseVO form, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            validateImage(file);
        }

        final boolean creating = (form.getId() == null);

        if (creating) {
            // 1) 시퀀스에서 ID 생성
            Long newId = mapper.nextId();
            form.setId(newId);

            // 2) DB Insert
            mapper.insert(form);

            // 3) 이미지 업로드가 있으면 반영
            if (file != null && !file.isEmpty()) {
                UploadResult r = uploadToCloudinary(file, buildPublicId(form.getId()));
                form.setImageUrl(r.secureUrl);
                mapper.update(form);
            }
            return form;
        } else {
            // 수정 흐름
            EnterpriseVO current = findById(form.getId());
            current.setName(form.getName());
            current.setRegion(form.getRegion());

            if (file != null && !file.isEmpty()) {
                UploadResult r = uploadToCloudinary(file, buildPublicId(current.getId()));
                current.setImageUrl(r.secureUrl);
            }

            mapper.update(current);
            return current;
        }
    }

    public void delete(Long id) {
        EnterpriseVO e = mapper.findById(id);
        if (e != null) {
            String publicId = "admin/enterprise/ent-" + id; // 삭제 시 단수형 publicId
            try {
                cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            } catch (Exception ignore) {}
            mapper.delete(id);
        }
    }

    // ----------------- 내부 유틸 -----------------
    private void validateImage(MultipartFile file) {
        if (file.getSize() > MAX_IMAGE_BYTES) {
            throw new IllegalArgumentException("이미지 용량은 10MB 이하여야 합니다.");
        }
        String ct = file.getContentType();
        if (ct == null || !ct.toLowerCase().startsWith("image/")) {
            throw new IllegalArgumentException("이미지 파일만 업로드할 수 있습니다.");
        }
    }

    private String buildPublicId(Long id) {
        return "admin/enterprise/ent-" + id + "-" + Instant.now().getEpochSecond();
    }

    private UploadResult uploadToCloudinary(MultipartFile file, String publicId) throws IOException {
        Map<String, Object> options = ObjectUtils.asMap(
                "public_id", publicId,
                "overwrite", true,
                "resource_type", "auto",
                "invalidate", true
        );
        Map<?, ?> upload = cloudinary.uploader().upload(file.getBytes(), options);

        Object secureUrl = upload.get("secure_url");
        Object publicIdRes = upload.get("public_id");
        if (secureUrl == null || publicIdRes == null) {
            throw new IOException("Cloudinary 업로드 실패(secure_url/public_id 누락).");
        }

        String thumb = buildThumbUrl(publicIdRes.toString(), 160, 160);
        return new UploadResult(secureUrl.toString(), publicIdRes.toString(), thumb);
    }

    private String buildThumbUrl(String publicId, int w, int h) {
        Url url = cloudinary.url()
                .secure(true)
                .transformation(
                        new Transformation()
                                .width(w).height(h)
                                .crop("fill")
                                .gravity("auto")
                                .quality("auto")
                                .fetchFormat("auto")
                );
        return url.generate(publicId);
    }

    private record UploadResult(String secureUrl, String publicId, String thumbUrl) {}
}
