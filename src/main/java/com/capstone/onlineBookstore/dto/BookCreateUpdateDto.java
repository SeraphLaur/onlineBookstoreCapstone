package com.capstone.onlineBookstore.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record BookCreateUpdateDto(
        @NotBlank String isbn,
        @NotBlank String title,
        @NotBlank String author,
        String description,
        @NotNull Long categoryId,
        @NotNull @DecimalMin("0.0") BigDecimal price,
        @NotNull @Min(0) Integer stock,
        String imageUrl
) {}