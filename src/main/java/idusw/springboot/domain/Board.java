package idusw.springboot.domain;

import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@ToString

public class Board {
    private Long bno; // 유일성있음
    private String title;
    private String content;

    private Long writerSeq;
    private String writerEmail;
    private String writerName;

    private LocalDateTime regDate;
    private LocalDateTime modDate;

    private Long replyCount; // JPA에서 count() 함수로 값을 채움
}
