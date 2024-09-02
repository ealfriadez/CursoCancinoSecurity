package pe.edu.unfv.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import pe.edu.unfv.persistence.dto.ProductDTO;
import pe.edu.unfv.persistence.entity.model.CategoriasModel;
import pe.edu.unfv.persistence.entity.model.ProductosModel;
import pe.edu.unfv.persistence.repository.IProductosRepository;
import pe.edu.unfv.service.IProductosService;
import pe.edu.unfv.util.Utilidades;

@Service
@Primary
@AllArgsConstructor
public class ProductosServiceImpl implements IProductosService {
	
	private IProductosRepository iProductosRepository;
	
	public List<ProductosModel> getAllProducts() {
		
		return this.iProductosRepository.findAll(Sort.by("id").descending());
	}
	
	@Override
	public ProductDTO getProductDTOById(int id) {
		return this.iProductosRepository.findById(id)
				.map(this::convertProductToDTO)
				.orElse(null);
	}

	@Override
	public ProductosModel getProductModelById(int id) {
		
		Optional<ProductosModel> optional = this.iProductosRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		
		return null;
	}	
	
	@Override
	public boolean existsProductByName(String name) {
		
		return this.iProductosRepository.existsProductByNombre(name);
	}
	
	@Override
	public void saveProductoModel(ProductosModel productosModel) {
		
		this.iProductosRepository.save(productosModel);		
	}	

	@Override
	public ProductDTO saveProduct(ProductDTO productDTO) {
		
		ProductosModel productosModel = convertDTOtoProduct(productDTO);
		return convertProductToDTO(this.iProductosRepository.save(productosModel));
	} 	

	@Override
	public void deleteProduct(Integer id) {
		
		this.iProductosRepository.deleteById(id);
	}

	@Override
	public boolean existsProductById(int id) {
		
		return iProductosRepository.existsById(id);
	}	
	
	private ProductDTO convertProductToDTO(ProductosModel productosModel) {
		
		ProductDTO productDTO = new ProductDTO();
		productDTO.setNombre(productosModel.getNombre());
		productDTO.setDescripcion(productosModel.getDescripcion());
		
		return productDTO;
	}	
	
	private ProductosModel convertDTOtoProduct(ProductDTO productDTO) {
		
		ProductosModel productosModel = new ProductosModel();
		productosModel.setNombre(productDTO.getNombre());
		productosModel.setSlug(Utilidades.getSlug(productDTO.getNombre()));
		productosModel.setDescripcion(productDTO.getDescripcion());
		productosModel.setPrecio(productDTO.getPrecio());
		
		CategoriasModel categoriasModel = new CategoriasModel();
		categoriasModel.setId(productDTO.getCategoriaId());
		
		productosModel.setCategoriaId(categoriasModel);
		
		return productosModel;
	}	
}
