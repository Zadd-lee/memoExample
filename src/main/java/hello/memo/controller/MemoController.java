package hello.memo.controller;

import hello.memo.dto.MemoRequestDto;
import hello.memo.dto.MemoResponseDto;
import hello.memo.model.Memo;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController()
@RequestMapping("/memos")
public class MemoController {

    private final Map<Long, Memo> memoList = new HashMap<>();

    @PostMapping
    MemoResponseDto crateMemo(@RequestBody MemoRequestDto dto) {
        //식별자가 1씩 증가 되도록 생성

        Long id = memoList.isEmpty() ? 1L : Collections.max(memoList.keySet());


        //요청 받은 메모로 객체 생성
        Memo memo = new Memo(id, dto.getTitle(), dto.getContent());

        //memorepository 에 memo 생성

        memoList.put(id, memo);

        return new MemoResponseDto(memo);

    }


    @GetMapping("/{id}")
    MemoResponseDto findById(@PathVariable Long id) {
            return new MemoResponseDto(memoList.get(id));
    }
}
