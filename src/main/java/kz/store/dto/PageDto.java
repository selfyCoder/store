package kz.store.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public record PageDto<T>(List<T> content,
                         int page,
                         int size,
                         int totalPages,
                         long totalElements) {
    public static <T> PageDto<T> from(Page<T> page) {
        return new PageDto<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements()
        );
    }
}
