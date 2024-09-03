package pe.edu.unfv.service.implement;

import java.io.IOException;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import pe.edu.unfv.persistence.entity.model.ImageDataModel;
import pe.edu.unfv.persistence.repository.IImageDataRepository;
import pe.edu.unfv.service.IImageDataService;
import pe.edu.unfv.util.ImageUtils;

@Service
@Primary
@AllArgsConstructor
public class ImageDataServiceImpl implements IImageDataService{
	
	private IImageDataRepository iImageDataRepository;
		
	public ImageDataModel uploadImage(MultipartFile file, String nombreImagen) throws IOException {
		
		ImageDataModel imageDataModel = this.iImageDataRepository.save(ImageDataModel.builder()
				.name(nombreImagen)
				.type(file.getContentType())
				.imageData(ImageUtils.compressImage(file.getBytes()))
				.build());
		
		return imageDataModel;
	}
}
