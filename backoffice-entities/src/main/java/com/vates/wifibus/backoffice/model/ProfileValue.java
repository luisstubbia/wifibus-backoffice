package com.vates.wifibus.backoffice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

/**
 * Cada profile de usuario posee una lista de elementos de tipo key-value.
 * 
 * @author Luis Stubbia
 *
 */
@Entity
@Table(name = "PROFILE_VALUES")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class ProfileValue extends AbstractEntity {

	@ManyToOne
    @JoinColumn(name = "PROFILE_ID")
	@JsonIgnore
	private Profile profile;
	
	@Column(name = "KEY_ID", nullable = false)
	private String key;
	
	@Column(name = "VALUE", nullable = false)
	private String value;
}
