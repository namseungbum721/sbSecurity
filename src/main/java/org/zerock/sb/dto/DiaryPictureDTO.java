package org.zerock.sb.dto;


import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "uuid")
public class DiaryPictureDTO {

    private String uuid;
    private String fileName;
    private String savePath;
    private int idx;
}
