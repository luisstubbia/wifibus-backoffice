package com.vates.wifibus.backoffice.model;

import java.util.Date;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO: Advertisement data transfer object.
 * 
 * @author Luis Stubbia
 *
 */
@Data
@Getter
@Setter
public abstract class AdvertisementForm<T, K> {
	
	private Long id;
	
	private String name;
	
	private String descripcion;
	
	private Date startDate;

	private Date endDate;
	
	private Integer duration;

	private Integer priority;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
	/**
	 * Convert DTO to model object.
	 * @return T
	 */
	public abstract T toModel();
	
	/**
	 * Convert Model object to DTO.
	 * @param model
	 * @return K
	 */
	public abstract K toForm(T model);
}