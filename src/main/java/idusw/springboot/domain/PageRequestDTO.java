package idusw.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {
    private int page;
    private int perPage;
    private int perPagination;

    private String type;
    private String keyword;

    // 생성자
    public PageRequestDTO() {
        this.page = 1;
        this.perPage = 10;
    }

    public Pageable getPageable(Sort sort) {
        return PageRequest.of(page - 1, perPage, sort);
    }
}
