package com.example.library.image;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class ImageService {

    public String storeBookImage(MultipartFile file) {
        // Create the upload directory if it doesn't exist
        String BOOK_UPLOAD_DIR = "image/books/";
        File directory = new File(BOOK_UPLOAD_DIR);
        if (!directory.exists()) {
            boolean dirsCreated = directory.mkdirs();
            if (!dirsCreated) {
                throw new RuntimeException("Failed to create directory: " + BOOK_UPLOAD_DIR);
            }
        }

        // Generate a unique file name
        String fileName = UUID.randomUUID() + "." + getFileExtension(file.getOriginalFilename());
        String filePath = BOOK_UPLOAD_DIR + fileName;

        try {
            // Save the file to the upload directory
            file.transferTo(new File(filePath));
            return fileName; // Return just the filename
        } catch (IOException e) {
            throw new RuntimeException("Failed to store the book image", e);
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName == null) return "";
        int lastIndexOf = fileName.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // No file extension
        }
        return fileName.substring(lastIndexOf + 1);
    }
}