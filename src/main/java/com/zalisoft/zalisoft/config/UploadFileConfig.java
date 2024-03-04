package com.zalisoft.zalisoft.config;

import com.uploadcare.api.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UploadFileConfig {

    @Value("${uploadCare.publicKey}")
    private   String publicKey;

    @Value("${uploadCare.secretKey}")
    private String secretKey;
    public Client UploadFileConfig(){
        Client client = new Client(publicKey,secretKey);
        return  client;
    }
}
