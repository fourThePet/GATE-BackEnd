package com.ureca.gate.global.util.file;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
public class UploadFile {

    private String uploadFileName;
    private String storeFileName;

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
