package idusw.springboot.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder

public class Reply {
    private Long rno;
    private String text;
    private String replier;
    private Long bno; // board 와 ManyToOne 관계
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
