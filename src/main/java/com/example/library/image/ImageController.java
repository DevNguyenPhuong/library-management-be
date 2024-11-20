package com.example.library.image;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ImageController {
    ImageService imageService;

    @GetMapping("/{folderName}/{uniqueFileName}")
    @ResponseBody
    public ResponseEntity<byte[]> getImage(@PathVariable String uniqueFileName, @PathVariable String folderName) {
        byte[] image = imageService.getImage(uniqueFileName, folderName);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(image);
    }
}
