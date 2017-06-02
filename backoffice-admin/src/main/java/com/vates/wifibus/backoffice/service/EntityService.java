package com.vates.wifibus.backoffice.service;

import java.util.Collection;
import java.util.Optional;

/**
 * 
 * @author Gaston Napoli
 *
 */
public interface EntityService<T> {

	/**
	 * @param id
	 * @return
	 */
	Optional<T> getById(long id);

	/**
	 * @param name
	 * @return
	 */
	Optional<T> getByName(String name);

	/**
	 * @return
	 */
	Collection<T> getAll();

	/**
	 * @param entity
	 * @return
	 */
	T save(T entity);

	/**
	 * @param id
	 */
	void deleteById(long id);

}
