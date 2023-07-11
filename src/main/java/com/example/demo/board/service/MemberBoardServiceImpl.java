package com.example.demo.board.service;


import com.example.demo.board.entity.FilePaths;
import com.example.demo.board.entity.MemberBoard;
import com.example.demo.board.form.RequestRegisterBoardForm;
import com.example.demo.board.reposiitory.FilePathsRepository;
import com.example.demo.board.reposiitory.MemberBoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class MemberBoardServiceImpl implements MemberBoardService {

    final private MemberBoardRepository boardRepository;
    final private FilePathsRepository filePathsRepository;

    @Override
    public List<MemberBoard> list() {
        return boardRepository.findAll(Sort.by(Sort.Direction.DESC, "boardId"));
    }

    @Override
    public MemberBoard register(RequestRegisterBoardForm requestForm) {
        log.info(String.valueOf(requestForm));
        List<FilePaths> filePathList = requestForm.getAwsFileList();
        MemberBoard savedBoard = boardRepository.save(requestForm.toMemberBoard());


        log.info(requestForm.getAwsFileList().toString());

        for (FilePaths filePaths : filePathList){
            String imagePath = filePaths.getImagePath();
            FilePaths imageFilePath = new FilePaths(imagePath, savedBoard);
            filePathsRepository.save(imageFilePath);

        }
        return savedBoard;
    }

    @Override
    public MemberBoard read(Long boardId) {
        Optional<MemberBoard> maybeBoard = boardRepository.findById(boardId);
        if(maybeBoard.isEmpty()){
            return null;
        }
        return maybeBoard.get();
    }
}
