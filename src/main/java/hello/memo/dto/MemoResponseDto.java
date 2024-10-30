package hello.memo.dto;

import hello.memo.model.Memo;
import lombok.Getter;

@Getter
public class MemoResponseDto {


    private Long id;
    private String title;
    private String content;

    public MemoResponseDto(Memo memo) {
        this.id = memo.getId();
        this.title = memo.getTitle();
        this.content = memo.getContents();
    }
}
