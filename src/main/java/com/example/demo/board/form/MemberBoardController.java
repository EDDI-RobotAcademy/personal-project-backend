package com.example.demo.board.form;

import com.example.demo.board.entity.FilePaths;
import com.example.demo.board.entity.MemberBoard;
import com.example.demo.board.service.MemberBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member-board")
public class MemberBoardController {

    final private MemberBoardService boardService;

    @GetMapping("/list")
    public List<MemberBoard> boardList () {
        log.info("boardList()");

        List<MemberBoard> returnedBoardList = boardService.list();
        return returnedBoardList;
    }

    @PostMapping("/register")
    public boolean registerBoard (@RequestBody RequestRegisterBoardForm requestBoardForm) {
        log.info("registerBoard()");

        return boardService.register(requestBoardForm);
    }


//    @PostMapping(value = "/register",
//            consumes = {
//                    MediaType.APPLICATION_JSON_VALUE,
//                    MediaType.APPLICATION_JSON_VALUE })
//    public Boolean fileRegisterRequestHandler (
//            @RequestPart(value = "files") List<FilePaths> fileList,
//            @RequestPart(value = "boardInfo") RequestRegisterBoardForm info)
//    {
//
//        log.info("fileRegisterRequestHandler(): " + info);
//        return boardService.register(fileList, info);
//    }
}
