package com.books.books.converters;

import com.books.books.dto.StyleDTO;
import com.books.books.models.Style;
import org.springframework.stereotype.Service;

@Service
public class StyleConverterImpl implements  StyleConverter{

    public StyleDTO toDTO(Style style) {
        return new StyleDTO(
                style.getId(),
                style.getStyleName()
        );
    }
}
