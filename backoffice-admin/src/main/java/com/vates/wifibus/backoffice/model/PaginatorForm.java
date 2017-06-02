package com.vates.wifibus.backoffice.model;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;

import com.google.common.collect.Lists;

import lombok.Data;

/**
 * 
 * @author Luis Stubbia
 *
 */
@Data
public class PaginatorForm {
	
	public final static int DEFAULT_ROWS_TO_SHOW = 10;
	
	private String query = "";
	
	private Integer queryNumber;
	
	protected Integer selectedRowsToShow;
	
	private Integer totalRow;
	
	private List<Integer> pages;
	
	private Integer selectedPage;
	
	private String rowDetail;
	
	public static List<Integer> ROWS_TO_SHOW = Lists.newArrayList(10, 20, 30, 40, 50, 60, 70, 80, 90, 100);
	
	public PaginatorForm() {
		
	}
	
	public PaginatorForm(Page<?> page) {
	    update(page);
	}

	public void update(Page<?> page) {
		this.selectedPage = page.getNumber() + 1;
		this.totalRow = page.getNumberOfElements();
		this.selectedRowsToShow = Optional.ofNullable(this.selectedRowsToShow).orElse(DEFAULT_ROWS_TO_SHOW);
		this.pages = Lists.newArrayListWithCapacity(page.getTotalPages());
		this.pages = IntStream.range(1, page.getTotalPages() + 1).boxed().collect(Collectors.toList());
		
		int from = Math.max(1, selectedPage - 5);
		int to = Math.min(from + 10, page.getTotalPages());
		this.rowDetail =  from + " - " + to + " of " + this.totalRow;
	}
	
}
