package com.example.demo.board.service;


import com.example.demo.board.entity.FilePaths;
import com.example.demo.board.entity.MemberBoard;
import com.example.demo.board.form.RequestModifyBoardForm;
import com.example.demo.board.form.RequestRegisterBoardForm;
import com.example.demo.board.form.ResponseBoardForm;
import com.example.demo.board.reposiitory.FilePathsRepository;
import com.example.demo.board.reposiitory.MemberBoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class MemberBoardServiceImpl implements MemberBoardService {

    final private MemberBoardRepository boardRepository;
    final private FilePathsRepository filePathsRepository;

    @Override
    @Transactional
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
    @Transactional
    public ResponseBoardForm read(Long boardId) {
        Optional<MemberBoard> maybeBoard = boardRepository.findById(boardId);
        if(maybeBoard.isEmpty()){
            return null;
        }
        List<Long> idList = maybeBoard.get().getFilePathList().stream().map(FilePaths::getFileId).toList();
        // lazy 걸려있어서 proxy patten안에 있어서 못가져옴 Transactional 하면 해결, 그치만 조회해야 거기서 쿼리가 한번 나간다.
        // joinFetch로 사용 가능
        List<FilePaths> savedFilePath = boardRepository.findById(boardId).get().getFilePathList();
        final ResponseBoardForm responseBoardForm = new ResponseBoardForm(maybeBoard.get(),savedFilePath);

        return responseBoardForm;
    }

    @Override
    @Transactional
    public List<MemberBoard> search(String keyword) {
        List<MemberBoard> findBoards = boardRepository.findByContentContaining(keyword);
        return findBoards;
    }

    @Override
    @Transactional
    public MemberBoard modify(RequestModifyBoardForm requestForm, Long boardId) {
        Optional<MemberBoard> maybeMemberBoard = boardRepository.findById(boardId);

        if (maybeMemberBoard.isEmpty()) {
            log.info("정보가 없습니다!");
            return null;
        }
        MemberBoard memberBoard = maybeMemberBoard.get();
        memberBoard.setContent(requestForm.getContent());
        memberBoard.setTitle(requestForm.getTitle());


        filePathsRepository.deleteAll(memberBoard.getFilePathList());

        List<FilePaths> filePathList = requestForm.getAwsFileList();
        for (FilePaths filePaths : filePathList){
            String imagePath = filePaths.getImagePath();
            FilePaths imageFilePath = new FilePaths(imagePath, memberBoard);
            filePathsRepository.save(imageFilePath);
        }
        return boardRepository.save(memberBoard);
    }
}
