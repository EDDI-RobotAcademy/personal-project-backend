package com.example.demo.board.service;


import com.example.demo.board.entity.FilePaths;
import com.example.demo.board.entity.MemberBoard;
import com.example.demo.board.form.BoardResForm;
import com.example.demo.board.form.RequestModifyBoardForm;
import com.example.demo.board.form.RequestRegisterBoardForm;
import com.example.demo.board.form.ResponseBoardForm;
import com.example.demo.board.reposiitory.FilePathsRepository;
import com.example.demo.board.reposiitory.MemberBoardRepository;
import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.comment.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class MemberBoardServiceImpl implements MemberBoardService {

    final private MemberBoardRepository boardRepository;
    final private FilePathsRepository filePathsRepository;
    final private MemberRepository memberRepository;
    final private CommentRepository commentRepository;

    @Override
    public List<BoardResForm> list() {
//        return boardRepository.findAll(Sort.by(Sort.Direction.DESC, "boardId"));
        List<MemberBoard> memberBoardList = boardRepository.findAll(Sort.by(Sort.Direction.DESC, "boardId"));
        List<BoardResForm> boardResFormList = memberBoardList.stream().map((mb)-> BoardResForm
                .builder()
                .boardId(mb.getBoardId())
                .title(mb.getTitle())
                .createDate(mb.getCreateDate())
                .member(mb.getMember())
                .build()).toList();
        return boardResFormList;
    }
    @Override
    public List<BoardResForm> list(Integer page) {
        Pageable pageable = PageRequest.of(page-1, 10 ,Sort.by("boardId").descending());
        List<MemberBoard> memberBoardList = boardRepository.findAllwithPage(pageable);
        List<BoardResForm> boardResFormList = memberBoardList.stream().map((mb)-> BoardResForm
                .builder()
                .boardId(mb.getBoardId())
                .title(mb.getTitle())
                .createDate(mb.getCreateDate())
                .member(mb.getMember())
                .build()).toList();
        return boardResFormList;
    }


    @Override
    @Transactional
    public MemberBoard register(RequestRegisterBoardForm requestForm) {
        log.info(String.valueOf(requestForm));
        Optional<Member> isMember = memberRepository.findByUserToken(requestForm.getUserToken());
        if (isMember.isEmpty()) {
            log.info("회원이 아닙니다.");
            return null;
        }
        Member savedMember = isMember.get();
        List<FilePaths> filePathList = requestForm.getAwsFileList();

        MemberBoard newMemberBoard = requestForm.toMemberBoard(savedMember);
        MemberBoard savedBoard = boardRepository.save(newMemberBoard);

        log.info(requestForm.getAwsFileList().toString());

        for (FilePaths filePaths : filePathList) {
            String imagePath = filePaths.getImagePath();
            FilePaths imageFilePath = new FilePaths(imagePath, savedBoard);
            filePathsRepository.save(imageFilePath);
        }
        return savedBoard;
    }

    @Override
    @Transactional
    public BoardResForm read(Long boardId) {
        Optional<MemberBoard> maybeBoard = boardRepository.findByIdWithMember(boardId);
        if (maybeBoard.isEmpty()) {
            return null;
        }
        MemberBoard savedBoard = maybeBoard.get();
//        List<Long> idList = maybeBoard.get().getFilePathList().stream().map(FilePaths::getFileId).toList();
//        // lazy 걸려있어서 proxy patten안에 있어서 못가져옴 Transactional 하면 해결, 그치만 조회해야 거기서 쿼리가 한번 나간다.
//        // joinFetch로 사용 가능
        List<FilePaths> savedFilePath = savedBoard.getFilePathList();
//        final ResponseBoardForm responseBoardForm = new ResponseBoardForm(maybeBoard.get(), savedFilePath);

        BoardResForm board = BoardResForm
                .builder()
                .boardId(savedBoard.getBoardId())
                .title(savedBoard.getTitle())
                .content(savedBoard.getContent())
                .createDate(savedBoard.getCreateDate())
                .member(savedBoard.getMember())
                .filePathList(savedBoard.getFilePathList())
                .build();


        return board;
    }

    @Override
    @Transactional
    public List<BoardResForm> search(String keyword) {
        List<MemberBoard> findBoards = boardRepository.findByContentContaining(keyword);
        List<BoardResForm> boardResFormList = findBoards.stream().map((fb)-> BoardResForm
                .builder()
                .boardId(fb.getBoardId())
                .title(fb.getTitle())
                .createDate(fb.getCreateDate())
                .member(fb.getMember())
                .build()).toList();
        return boardResFormList;
    }

    @Override
    @Transactional
    public BoardResForm modify(RequestModifyBoardForm requestForm, Long boardId) {
        Optional<Member> isMember = memberRepository.findByUserToken(requestForm.getUserToken());
        if (isMember.isEmpty()) {
            log.info("회원이 아닙니다.");
            return null;
        }
        Optional<MemberBoard> maybeMemberBoard = boardRepository.findById(boardId);
        if (maybeMemberBoard.isEmpty()) {
            log.info("정보가 없습니다!");
            return null;
        }
        MemberBoard memberBoard = maybeMemberBoard.get();

        if (!memberBoard.getMember().getUserToken().equals(requestForm.getUserToken())) {
            log.info("토큰이 일치하지 않습니다.");
            return null;
        }
            memberBoard.setContent(requestForm.getContent());
            memberBoard.setTitle(requestForm.getTitle());
            log.info("지울리스트" + memberBoard.getFilePathList().toString());
            memberBoard.getFilePathList().clear();
            log.info(memberBoard.getFilePathList().toString());
//            filePathsRepository.deleteAll(memberBoard.getFilePathList());
            boardRepository.save(memberBoard);

            List<FilePaths> filePathList = requestForm.getAwsFileList();
        if (filePathList != null) {
            for (FilePaths filePaths : filePathList) {
                String imagePath = filePaths.getImagePath();
                FilePaths imageFilePath = new FilePaths(imagePath, memberBoard);
                filePathsRepository.save(imageFilePath);
            }
        }
        BoardResForm board = BoardResForm
                .builder()
                .boardId(memberBoard.getBoardId())
                .title(memberBoard.getTitle())
                .content(memberBoard.getContent())
                .createDate(memberBoard.getCreateDate())
                .member(memberBoard.getMember())
                .filePathList(memberBoard.getFilePathList())
                .build();
            return board;
        }


    @Override
    @Transactional
    public boolean delete(Long boardId, HttpHeaders headers) {
        Optional<MemberBoard> maybeBoard = boardRepository.findById(boardId);
        if (maybeBoard.isEmpty()) {
            return false;
        }
        MemberBoard finedBoard = maybeBoard.get();
        if (finedBoard.getMember().getUserToken().equals(Objects.requireNonNull(headers.get("authorization")).get(0))) {

            filePathsRepository.deleteAll(finedBoard.getFilePathList());
            boardRepository.deleteById(boardId);

            return true;
        }
        return false;
    }

    @Override
    public Integer getTotalPage() {
        Integer totalBoard = (int) boardRepository.count();
        Integer size = 10;
        if(totalBoard % size ==0){
            return totalBoard/size;
        }else{
        return totalBoard/size+1;}
    }
}
