package com.vates.wifibus.backoffice.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.vates.wifibus.backoffice.model.Brand;

/**
 * Repository: branding repository.
 * 
 * @author Luis Stubbia
 *
 */
public interface BrandingRepository extends JpaRepository<Brand, Long> {
	
	Optional<Brand> findOneByName(String name);

	@Modifying
	@Transactional
	@Query("update Brand b set b.name = ?1, b.descripcion = ?2, b.cobrand = ?3, b.logoImage = ?4, "
			+ "b.backgroundImage = ?5 where b.id = ?6")
	void setBrandInfoByNameAndDescriptionAndCobrandAndLogoImageAndBackgroundImge(String name, String descripcion, 
			String cobrand, String logoImage, String backgroundImage, Long brandId);
	
	Page<Brand> findByNameContainsAllIgnoreCase(String brandName, Pageable page);
	
	@Modifying
	@Transactional
	@Query("update Brand b set b.enabled = false where b.id = ?1")
	void deleteById(Long brandId);
	
	Long countByName(String name);
	
	List<Brand> findByEnabledTrueOrderByNameDesc();
}

