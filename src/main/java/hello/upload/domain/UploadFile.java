package hello.upload.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadFile {

    private String uploadFileName;
    private String storeFileName; // uuid 같이 안겹치게
}
