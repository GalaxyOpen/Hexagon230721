package com.icia.hexagon.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameDTO {

    private Long id;
    private String gameTitle;
    private String gameGenre;
    private String gameCreator;
    private String gameDistr;
    private String gameGrade;
    private String gameIntro;
    private Long releasePrice;
    private int discountRate;
    private Long salesPrice;
    private String createdAt;
    private List<MultipartFile> gameFile;
    private int fileAttached;
    private List<String> originalFileName;
    private List<String> storedFileName;
    private Long memberId;





}
