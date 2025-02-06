package com.padillatom.TAG_Board.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ImageResponse {

    private byte[] imageData;

    public static ImageResponse toDto(byte[] data) {
        return ImageResponse.builder()
                .imageData(data)
                .build();
    }
}
