package com.books.books.dto;

import com.books.books.models.Style;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StyleDTO {

    private long id;
    private String styleName;

    public static StyleDTO toDTO(Style style) {
        return new StyleDTO(
                style.getId(),
                style.getStyleName()
        );
    }
}
