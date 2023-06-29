package com.icia.hexagon.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberPhotoDTO {
    private Long id;
    private String OriginalFileName;
    private String storedFileName;
    private Long memberId;
}
