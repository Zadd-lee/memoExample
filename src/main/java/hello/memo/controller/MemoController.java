package hello.memo.controller;

import hello.memo.dto.MemoRequestDto;
import hello.memo.dto.MemoResponseDto;
import hello.memo.model.Memo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController()
@RequestMapping("/memos")
public class MemoController {

    private final Map<Long, Memo> memoList = new HashMap<>();

    @PostMapping
    ResponseEntity<MemoResponseDto> crateMemo(@RequestBody MemoRequestDto dto) {
        //식별자가 1씩 증가 되도록 생성

        Long id = memoList.isEmpty() ? 1 : Collections.max(memoList.keySet()) + 1;


        //요청 받은 메모로 객체 생성
        Memo memo = new Memo(id, dto.getTitle(), dto.getContents());

        //memorepository 에 memo 생성

        memoList.put(id, memo);

        return new ResponseEntity<>(new MemoResponseDto(memo), HttpStatus.CREATED);

    }

    @GetMapping()
    public ResponseEntity<List<MemoResponseDto>> findAllMemo() {
        List<MemoResponseDto> responseList = new ArrayList<MemoResponseDto>();

        /*for (Memo memo : memoList.values()) {
            responseList.add(new MemoResponseDto(memo));
        }*/

        //스트림 이용
        responseList = memoList.values().stream()
                .map(MemoResponseDto::new).toList();

        return new ResponseEntity<>(responseList, HttpStatus.OK);


    }

    @GetMapping("/{id}")
    public ResponseEntity<MemoResponseDto> findMemoById(@PathVariable Long id) {

        Memo memo = memoList.get(id);

        if (memo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new MemoResponseDto(memo), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    ResponseEntity<MemoResponseDto> updateMemoById(@PathVariable Long id,
                                                   @RequestBody MemoRequestDto dto) {
        Memo memo = memoList.get(id);

        if (memo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (dto.getTitle() == null || dto.getContents() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        memo.update(dto);

        return new ResponseEntity<>(new MemoResponseDto(memo), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MemoResponseDto> updateTitle(@PathVariable Long id,
                                                       @RequestBody MemoRequestDto dto) {
        Memo memo = memoList.get(id);
        if (memo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (dto.getTitle() == null || dto.getContents() != null) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        memo.updateTitle(dto);

        return new ResponseEntity<>(new MemoResponseDto(memo), HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMemo(@PathVariable Long id) {

        if (memoList.containsKey(id)) {
            memoList.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
