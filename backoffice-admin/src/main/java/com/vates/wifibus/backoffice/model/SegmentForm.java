package com.vates.wifibus.backoffice.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.springframework.util.CollectionUtils;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO: Campaign data transfer object.
 * 
 * @author Luis Stubbia
 *
 */
@Data
@Getter
@Setter
public class SegmentForm {
		
	private Long id;
	
	private String name;
	
	private String descripcion;
	
	private Collection<SegmentItem> items =  new ArrayList<SegmentItem>();
	
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
	
	public void addSegmentItem(SegmentItem item){
		item.setIndex(this.items.size() + 1);
		this.items.add(item);
	}

	public Collection<SegmentItem> getItems() {
		return items;
	}

	public void setItems(Collection<SegmentItem> items) {
		this.items = items;
	}

	public void buildItem(Set<SegmentItem> items) {
		if(!CollectionUtils.isEmpty(items)){
			int index = 1;
			for(SegmentItem itm : items){
				itm.setIndex(index++);
			}
		}
	}

	public void addSegmentItem() {
		SegmentItem itm = new SegmentItem();
		itm.setIndex(items.size() + 1);
		items.add(itm);
	}
}