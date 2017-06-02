package com.vates.wifibus.stats.api.repository;

import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.vates.wifibus.stats.api.model.ProductInfo;

@EnableScan
public interface ProductInfoRepository extends CrudRepository<ProductInfo, String> {

	List<ProductInfo> findById(String id);

}
