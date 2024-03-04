package com.zalisoft.zalisoft.service.impl;


import com.uploadcare.upload.FileUploader;
import com.uploadcare.upload.UploadFailureException;
import com.uploadcare.upload.Uploader;
import com.zalisoft.zalisoft.config.UploadFileConfig;
import com.zalisoft.zalisoft.enums.ResponseMessageEnum;
import com.zalisoft.zalisoft.exception.BusinessException;
import com.zalisoft.zalisoft.service.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;


@Service
@Slf4j
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private UploadFileConfig fileConfig;


    public  String uploadFile(MultipartFile file) {
        log.info("Uploading file: {}",file.isEmpty());
        if(file.isEmpty()){
            throw  new BusinessException(ResponseMessageEnum.BACK_FILE_MSG_003);
        }
        try {
            checkFileSize(file);
            Uploader uploader = new FileUploader(fileConfig.UploadFileConfig(), file.getBytes(), file.getOriginalFilename());
            return uploader.upload().getOriginalFileUrl().toString();
        }catch (UploadFailureException e){
            throw  new BusinessException(ResponseMessageEnum.BACK_FILE_MSG_001+e.getMessage());
        } catch (IOException e) {
            throw  new BusinessException(ResponseMessageEnum.BACK_FILE_MSG_001+e.getMessage());
        }
    }


    public  void deleteFile(String url) {
        try {
            String imageId=getImageId(url);
            log.info("imageId: " + imageId);
            fileConfig.UploadFileConfig().deleteFile(imageId);
        }catch (Exception e){
            throw  new BusinessException(ResponseMessageEnum.BACK_FILE_MSG_002+e.getMessage());
        }}

    private  String  getImageId(String url){
        int startIndex = url.indexOf(".com/")+5;
        int endIndex = url.lastIndexOf("/");
        return url.substring(startIndex, endIndex);
    }

    private  void checkFileSize(MultipartFile file){
        BigDecimal bigDecimal=BigDecimal.valueOf(file.getSize())
                .divide(BigDecimal.valueOf(1024)).divide(BigDecimal.valueOf(1024));
        log.info("fileSize: " + bigDecimal);
        if(bigDecimal.compareTo(BigDecimal.TEN) > 0){
            throw  new BusinessException(ResponseMessageEnum.BACK_FILE_MSG_003);
        }

    }
}
