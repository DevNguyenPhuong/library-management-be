package com.example.library.image;

import com.example.library.exception.AppException;
import com.example.library.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ImageService {

    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public String storeImage(MultipartFile file, String folderName) {
        String UPLOAD_DIR = getUploadDir(folderName);
        if (file.isEmpty())
            throw new AppException(ErrorCode.CANNOT_STORE_EMPTY_IMG);

        String fileName = UUID.randomUUID() + "." + getFileExtension(file.getOriginalFilename());
        String filePath = UPLOAD_DIR + fileName;
        try {
            file.transferTo(new File(filePath));
            return fileName;
        } catch (IOException e) {
            throw new AppException(ErrorCode.FAIL_STORE_IMG);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public void deleteImage(String uniqueFileName, String folderName) {
        String imagePath = getUploadDir(folderName) + uniqueFileName;
        File file = new File(imagePath);

        if (file.exists()) {
            if (!file.delete())
                throw new AppException(ErrorCode.FAIL_DELETE_IMG);
        } else {
            throw new AppException(ErrorCode.IMAGE_NOT_FOUND);
        }
    }

    private static String getUploadDir(String folderName) {
        String basePath = new File(".").getAbsolutePath();
        String UPLOAD_DIR = basePath + "/images/" + folderName + "/";
        File directory = new File(UPLOAD_DIR);

        if (!directory.exists()) {
            boolean dirsCreated = directory.mkdirs();
            if (!dirsCreated)
                throw new AppException(ErrorCode.FAIL_CREATE_DIR);
        }
        return UPLOAD_DIR;
    }

    private String getFileExtension(String fileName) {
        if (fileName == null) return "";
        int lastIndexOf = fileName.lastIndexOf(".");
        if (lastIndexOf == -1)  return "";
        return fileName.substring(lastIndexOf + 1);
    }


    public byte[] getImage(String uniqueFileName, String folderName) {
        String basePath = new File(".").getAbsolutePath();
        String UPLOAD_DIR = basePath + "/images/" + folderName + "/";
        String imagePath = UPLOAD_DIR + uniqueFileName;
        log.info("Looking for image at path: {}", imagePath); // Log the path
        Path path = Paths.get(imagePath);

        if (!Files.exists(path)) {
            throw new AppException(ErrorCode.IMAGE_NOT_FOUND);
        }

        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new AppException(ErrorCode.FAIL_READ_IMG);
        }
    }
}