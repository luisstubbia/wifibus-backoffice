package com.vates.wifibus.backoffice.model;

import java.util.Set;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO: Router data transfer object.
 * 
 * @author Luis Stubbia
 *
 */
@Data
@Getter
@Setter
public class RouterForm {
	
	private Long id;
	
	private String name;
	
	private String descripcion;
	
	private String macAddress;
	
	private String ipv4address;
	
	private String location;
	
	private Double latitude;
	
	private Double longitude;
	
	private RouterGroup group;
	
    private Set<Hotspot> hotspots;

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

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getIpv4address() {
		return ipv4address;
	}

	public void setIpv4address(String ipv4address) {
		this.ipv4address = ipv4address;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public RouterGroup getGroup() {
		return group;
	}

	public void setGroup(RouterGroup group) {
		this.group = group;
	}

	public Set<Hotspot> getHotspots() {
		return hotspots;
	}

	public void setHotspots(Set<Hotspot> hotspots) {
		this.hotspots = hotspots;
	}        
}
