package com.vates.wifibus.backoffice.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Gaston Napoli
 *
 */
@MappedSuperclass
@Getter
@Setter
public abstract class AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", nullable = false)
	@JsonIgnore
	private Long id;
	
	@CreatedDate
	@Column(name = "CREATED_DATE")
	@JsonIgnore
	private Date createdDate;

	@CreatedBy
	@Column(name = "CREATED_BY", length = 50)
	@JsonIgnore
	private String createdBy;
	
	@LastModifiedDate
	@Column(name = "LAST_MODIFIED_DATE")
	@JsonIgnore
	private Date lastUpdatedDate;
	
	@LastModifiedBy
	@Column(name = "LAST_MODIFIED_BY", length = 50)
	@JsonIgnore
	private String lastUpdatedBy;

	@Version
	@Column(name = "LOCK_VERSION")
	@JsonIgnore
	private Long Version;
	
	@Column(name = "ENABLED", nullable = false)
	@NotNull
	private boolean enabled = true;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
