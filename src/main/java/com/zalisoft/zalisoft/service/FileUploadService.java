package com.zalisoft.zalisoft.service;

import com.uploadcare.upload.UploadFailureException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadService {

    String uploadFile(MultipartFile file) throws UploadFailureException, IOException;

    void deleteFile(String url) throws UploadFailureException, IOException;
}
