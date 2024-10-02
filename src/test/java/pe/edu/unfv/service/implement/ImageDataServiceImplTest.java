package pe.edu.unfv.service.implement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import pe.edu.unfv.persistence.entity.model.ImageDataModel;
import pe.edu.unfv.persistence.repository.IImageDataRepository;
import pe.edu.unfv.util.ImageUtils;

@ExtendWith(MockitoExtension.class)
class ImageDataServiceImplTest {

	@Mock
	IImageDataRepository iImageDataRepository;

	@InjectMocks
	ImageDataServiceImpl imageDataServiceImpl;

	@Test
	void testUploadImage() throws IOException {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		String nombreImagen = "imagenTest.jpg";
		byte[] fileBytes = "datos de imagen".getBytes();
		MultipartFile mockFile = mock(MultipartFile.class);

		// When -> Cuando
		// Simular el comportamiento del repositorio
		Mockito.when(mockFile.getBytes()).thenReturn(fileBytes);
		Mockito.when(mockFile.getContentType()).thenReturn("image/jpeg");

		// Crear el objeto esperado
		ImageDataModel expectedImageData = ImageDataModel.builder().name(nombreImagen).type("image/jpeg")
				.imageData(ImageUtils.compressImage(fileBytes)).build();

		// Configurar el repositorio mock
		Mockito.when(iImageDataRepository.save(any(ImageDataModel.class))).thenReturn(expectedImageData);

		// Llamar al método
		ImageDataModel result = imageDataServiceImpl.uploadImage(mockFile, nombreImagen);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertNotNull(result);
		assertEquals(expectedImageData.getName(), result.getName());
		assertEquals(expectedImageData.getType(), result.getType());

		// Verificar que el repositorio fue llamado
		verify(iImageDataRepository).save(any(ImageDataModel.class));
	}

	@Test
	void testUploadImageIOException() throws IOException {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		MultipartFile mockFile = mock(MultipartFile.class);

		// When -> Cuando
		// Simular el comportamiento del repositorio
		// Configurar el comportamiento del mock para lanzar excepción
		Mockito.when(mockFile.getBytes()).thenThrow(new IOException("Error al leer el archivo"));

		// Then -> entonces
		// Verificar los resultados correctamentes
		// Llamar al método y verificar que lanza IOException
		assertThrows(IOException.class, () -> {
			imageDataServiceImpl.uploadImage(mockFile, "imagenTest.jpg");
		});
	}
}
