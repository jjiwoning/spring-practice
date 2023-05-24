package com.example.springpractice.util;

import com.example.springpractice.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileUtil {

    @Value("${file.dir}")
    private String fileDir;

    // 파일 path 리턴
    public String getFullPath(String fileName){
        return fileDir + fileName;
    }

    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty()){
                storeFileResult.add(storeFile(multipartFile));
            }
        }
        return storeFileResult;
    }

    // 파일 저장
    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        if(multipartFile.isEmpty()){
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));

        return UploadFile
                .builder()
                .userFileName(originalFilename)
                .storedFileName(storeFileName)
                .build();
    }

    private String createStoreFileName(String originalFilename) {
        // 확장자 꺼내기
        String ext = extractExt(originalFilename);
        // 서버에 저장하는 파일명
        String uuid = UUID.randomUUID().toString();
//      "asdasd-asdasd.png 형태로 확장자 이름을 가져오고 싶다."
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int index = originalFilename.lastIndexOf(".");
        return originalFilename.substring(index + 1);
    }

}
