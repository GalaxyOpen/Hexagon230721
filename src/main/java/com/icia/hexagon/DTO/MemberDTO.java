package com.icia.hexagon.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    private Long id;
    private String memberId;
    private String memberPassword;
    private String memberName;
    private String memberEmail;
    private String memberMobile;
    private LocalDateTime memberBirth;
    private int photoAttached;
    private String createdAt;
    private Long totalPoint;
}
