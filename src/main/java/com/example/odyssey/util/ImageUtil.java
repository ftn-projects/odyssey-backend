package com.example.odyssey.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

public abstract class ImageUtil {
	public static void saveImage(String uploadDir, String fileName,
            MultipartFile multipartFile) throws IOException {
		
        Path uploadPath = Paths.get(uploadDir);
         
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            
            System.out.println("FilePath is:" + uploadPath.toAbsolutePath());
            
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {        
            throw new IOException("Could not save image file: " + fileName, ioe);
        }      
    }

    public static void copyImages(String sourceDir, String destinationDir) throws IOException {
        Path uploadPath = Paths.get(destinationDir);

        if (!Files.exists(uploadPath))
            Files.createDirectories(uploadPath);
        else
            FileUtils.cleanDirectory(new File(destinationDir));

        FileUtils.copyDirectory(new File(sourceDir), new File(destinationDir));
    }
}
