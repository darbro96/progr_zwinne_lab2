package com.project.rest.service;

import com.project.rest.utilities.FileStorageException;
import com.project.rest.utilities.MyFileNotFoundException;
import com.project.rest.utilities.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileStorageService {
    private static final String DIR = "uploads";
    private Path fileStorageLocation;

    @Autowired
    public FileStorageService() {
        this.fileStorageLocation = Paths.get(DIR)
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file, int projektId) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(projektId + "/" + fileName);
            if (!Files.exists(Paths.get(DIR + "/" + projektId))) {
                Files.createDirectory(Paths.get(DIR + "/" + projektId));
            }

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName, int idProject) {
        try {
            Path filePath = this.fileStorageLocation.resolve(idProject + "/" + fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    public List<Track> filesFromProject(int idProject) {
        File f = new File(DIR + "/" + idProject);
        String[] files = f.list();
        List<Track> listOfFiles = new ArrayList<>();
        for (String s : files) {
            listOfFiles.add(new Track("https://localhost:8443/downloadFile/" + idProject + "/" + s, s));
        }
        return listOfFiles;
    }
}
