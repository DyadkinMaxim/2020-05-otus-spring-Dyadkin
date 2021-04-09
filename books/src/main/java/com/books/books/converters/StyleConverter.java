package com.books.books.converters;

import com.books.books.dto.StyleDTO;
import com.books.books.models.Style;

public interface StyleConverter {
    StyleDTO toDTO(Style style);
}
