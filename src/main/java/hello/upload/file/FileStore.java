package hello.upload.file;

import hello.upload.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir+filename;
    }

    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
       List<UploadFile> uploadFileList = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty()){
                UploadFile uploadFile = storeFile(multipartFile);
                uploadFileList.add(uploadFile);
            }
        }
        return uploadFileList;
    }

    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        if(multipartFile.isEmpty()){
            return null;
        }

        //사용자가 업로드한 파일네임
        String originalFilename = multipartFile.getOriginalFilename();

        //*****
        //image.png .png 추출
        String ext = extracteExt(originalFilename);

        //*****
        //서버에 저장하는 파일명
        String uuid = UUID.randomUUID().toString();

        //추출한 확장자를 uuid뒤에 붙임
        //서버에 저장하는 파일명
        String storeFileName = uuid+"."+ext;
        //*****

        //업로드
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        return new UploadFile(originalFilename,storeFileName);
    }

    private static String extracteExt(String originalFilename) {
        int pos = Objects.requireNonNull(originalFilename).lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
