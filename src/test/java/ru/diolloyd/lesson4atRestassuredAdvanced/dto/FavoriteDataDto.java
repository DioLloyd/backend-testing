package ru.diolloyd.lesson4atRestassuredAdvanced.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FavoriteDataDto {
    FAVORITED("favorited"),
    UNFAVORITED("unfavorited");

    private String value;
}
