package com.sgonzalez.pedido_equipos_api.dtos;

import org.springframework.data.domain.Page;

public class PaginationMetaDto {

	private Integer page;
	private Integer size;
	private Long totalElements;
	private Integer totalPages;
	private Boolean hasNext;
	private Boolean hasPrevious;
	private String sortBy;
	private String sortDirection;

	public PaginationMetaDto() {
	}

	public PaginationMetaDto(
			Integer page,
			Integer size,
			Long totalElements,
			Integer totalPages,
			Boolean hasNext,
			Boolean hasPrevious,
			String sortBy,
			String sortDirection
	) {
		this.page = page;
		this.size = size;
		this.totalElements = totalElements;
		this.totalPages = totalPages;
		this.hasNext = hasNext;
		this.hasPrevious = hasPrevious;
		this.sortBy = sortBy;
		this.sortDirection = sortDirection;
	}

	public static PaginationMetaDto from(Page<?> page, String sortBy, String sortDirection) {
		return new PaginationMetaDto(
				page.getNumber(),
				page.getSize(),
				page.getTotalElements(),
				page.getTotalPages(),
				page.hasNext(),
				page.hasPrevious(),
				sortBy,
				sortDirection
		);
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(Long totalElements) {
		this.totalElements = totalElements;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public Boolean getHasNext() {
		return hasNext;
	}

	public void setHasNext(Boolean hasNext) {
		this.hasNext = hasNext;
	}

	public Boolean getHasPrevious() {
		return hasPrevious;
	}

	public void setHasPrevious(Boolean hasPrevious) {
		this.hasPrevious = hasPrevious;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}
}
