package com.vates.wifibus.backoffice.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

/**
 * Modela los routers dentro de un grupo.
 * 
 * @author Gaston Napoli
 *
 */
@Entity
@Table(name = "ROUTERS")
@EntityListeners(AuditingEntityListener.class)
@Data
public class Router extends BaseEntity {

	@Column(name = "MAC_ADDRESS", nullable = false, unique = true, length = 17)
	@NotNull
	private String macAddress;

	@Column(name = "IP_V4_ADDRESS", nullable = false, unique = true, length = 15)
	@NotNull
	private String ipV4Address;
	
	@ManyToOne
    @JoinColumn(name = "GROUP_ID")
	@NotNull
	private RouterGroup group;
	
	@OneToMany(mappedBy = "router", cascade = CascadeType.ALL)
    private Set<Hotspot> hotspots;
	
	@Column(name = "LOCATION", nullable = false, length = 500)
	@NotNull
	private String location;
	
	@Column(name = "LATITUDE", nullable = false, length = 500)
	private Double latitude;

	@Column(name = "LONGITUDE", nullable = false, length = 500)
	private Double longitude;
	
}
