package pe.edu.unfv.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import pe.edu.unfv.persistence.entity.model.ImageDataModel;

public interface IImageDataService {

	ImageDataModel uploadImage(MultipartFile file) throws IOException;
}
