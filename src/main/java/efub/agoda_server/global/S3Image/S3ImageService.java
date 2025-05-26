package efub.agoda_server.global.S3Image;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import efub.agoda_server.global.exception.CustomException;
import efub.agoda_server.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3ImageService {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;
    private static final List<String> ALLOWED_EXTENSIONS = List.of("jpg", "jpeg", "png", "webp");

    public String uploadFile(MultipartFile file, String dirName){
        validateFileExtension(file.getOriginalFilename());
        String fileName = buildFileName(file, dirName);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        try(InputStream inputStream = file.getInputStream()){
            amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new CustomException(ErrorCode.FILE_UPLOAD_FAILED);
        }

        //aws url 경로 전체 반환
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    //확장자 검사
    private void validateFileExtension(String fileName){
        if(fileName == null || !fileName.contains(".")){
            throw new CustomException(ErrorCode.INVALID_IMAGE_EXTENSION);
        }
        
        String extension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new CustomException(ErrorCode.INVALID_IMAGE_EXTENSION);
        }
    }

    //유니크 파일명 생성
    private String buildFileName(MultipartFile file, String dirName) {
        return dirName + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();
    }
}
