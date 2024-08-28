package pe.edu.unfv.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import pe.edu.unfv.persistence.entity.model.ProductosModel;
import pe.edu.unfv.persistence.repository.IProductosRepository;
import pe.edu.unfv.service.IProductosService;

@Service
@Primary
@AllArgsConstructor
public class ProductosServiceImpl implements IProductosService {
	
	private IProductosRepository iProductosRepository;
	
	public List<ProductosModel> getAllProducts() {
		
		return this.iProductosRepository.findAll(Sort.by("id").descending());
	}

	@Override
	public ProductosModel getProductById(Integer id) {
		
		Optional<ProductosModel> optional = this.iProductosRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		
		return null;
	}	

	@Override
	public void saveProduct(ProductosModel product) {
		
		this.iProductosRepository.save(product);
	}

	@Override
	public void deleteProduct(Integer id) {
		
		this.iProductosRepository.deleteById(id);
	}	
}
