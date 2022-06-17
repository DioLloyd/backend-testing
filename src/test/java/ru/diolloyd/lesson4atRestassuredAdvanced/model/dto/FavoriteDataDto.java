package ru.diolloyd.lesson4atRestassuredAdvanced.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FavoriteDataDto {
    FAVORITED("favorited"),
    UNFAVORITED("unfavorited");

    private String value;
}
