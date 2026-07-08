package com.sgonzalez.pedido_equipos_api.dtos;

import org.springframework.data.domain.Page;

public class PaginationMetaDto {

	private Integer page;
	private Integer size;
	private Integer limit;
	private Long total;
	private Long totalElements;
	private Integer totalPages;
	private Boolean hasNext;
	private Boolean hasPrevious;
	private String sortBy;
	private String sortDirection;
	private String order;

	public PaginationMetaDto() {
	}

	public PaginationMetaDto(
			Integer page,
			Integer size,
			Integer limit,
			Long total,
			Long totalElements,
			Integer totalPages,
			Boolean hasNext,
			Boolean hasPrevious,
			String sortBy,
			String sortDirection,
			String order
	) {
		this.page = page;
		this.size = size;
		this.limit = limit;
		this.total = total;
		this.totalElements = totalElements;
		this.totalPages = totalPages;
		this.hasNext = hasNext;
		this.hasPrevious = hasPrevious;
		this.sortBy = sortBy;
		this.sortDirection = sortDirection;
		this.order = order;
	}

	public static PaginationMetaDto from(Page<?> page, String sortBy, String sortDirection) {
		String direction = sortDirection == null ? "asc" : sortDirection;
		return new PaginationMetaDto(
				page.getNumber() + 1,
				page.getSize(),
				page.getSize(),
				page.getTotalElements(),
				page.getTotalElements(),
				page.getTotalPages(),
				page.hasNext(),
				page.hasPrevious(),
				sortBy,
				direction,
				direction
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

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
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

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
}
