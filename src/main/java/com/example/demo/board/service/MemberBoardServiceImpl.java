package com.example.demo.board.service;


import com.example.demo.board.entity.FilePaths;
import com.example.demo.board.entity.MemberBoard;
import com.example.demo.board.form.BoardResForm;
import com.example.demo.board.form.CommentResForm;
import com.example.demo.board.form.RequestModifyBoardForm;
import com.example.demo.board.form.RequestRegisterBoardForm;
import com.example.demo.board.reposiitory.FilePathsRepository;
import com.example.demo.board.reposiitory.MemberBoardRepository;
import com.example.demo.comment.repository.CommentRepository;
import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.redis.RedisService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

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
    final private RedisService redisService;
    final private CommentRepository commentRepository;

    @Override
    public List<BoardResForm> list() {
//        return boardRepository.findAll(Sort.by(Sort.Direction.DESC, "boardId"));
        List<MemberBoard> memberBoardList = boardRepository.findAll(Sort.by(Sort.Direction.DESC, "boardId"));
        List<BoardResForm> boardResFormList = memberBoardList.stream().map((mb) -> BoardResForm
                .builder()
                .boardId(mb.getBoardId())
                .title(mb.getTitle())
                .filePathList(mb.getFilePathList())
                .cafeTitle(mb.getCafeTitle())
                .createDate(mb.getCreateDate())
                .member(mb.getMember())
                .build()).toList();
        return boardResFormList;
    }

    @Override
    public List<BoardResForm> list(Integer page) {
        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("boardId").descending());
        List<MemberBoard> memberBoardList = boardRepository.findAllwithPage(pageable);
        List<BoardResForm> boardResFormList = memberBoardList.stream().map((mb) -> BoardResForm
                .builder()
                .boardId(mb.getBoardId())
                .title(mb.getTitle())
                .createDate(mb.getCreateDate())
                .member(mb.getMember())
                .build()).toList();
        return boardResFormList;
    }

    @Override
    public List<BoardResForm> listWithMember(HttpHeaders headers, int page) {
        Long memberId = redisService.getValueByKey(Objects.requireNonNull(headers.get("authorization")).get(0));
        Optional<Member> isMember = memberRepository.findById(memberId);
        if (isMember.isEmpty()) {
            log.info("회원이 아닙니다.");
            return null;
        }
        Member findMember = isMember.get();
        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("boardId").descending());
        List<MemberBoard> memberBoardList = boardRepository.findAllByMemberId(findMember.getId(), pageable);

        List<BoardResForm> boardResFormList = memberBoardList.stream().map((mb) -> BoardResForm
                .builder()
                .boardId(mb.getBoardId())
                .title(mb.getTitle())
                .nickname(mb.getNickname())
                .createDate(mb.getCreateDate())
                .build()).toList();
        return boardResFormList;
    }


    @Override
    @Transactional
    public MemberBoard register(RequestRegisterBoardForm requestForm) {
        Long memberId = redisService.getValueByKey(requestForm.getUserToken());
        Optional<Member> isMember = memberRepository.findById(memberId);
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
        // 스프링에서 멤버보드 가져왔어 콘텐트는 두고왔어 게을러서 직접 불러야 가져올 수 있어 부른다 = select한다.
//        // joinFetch로 사용 가능
        List<FilePaths> savedFilePath = savedBoard.getFilePathList();
//        final ResponseBoardForm responseBoardForm = new ResponseBoardForm(maybeBoard.get(), savedFilePath);

        BoardResForm board = BoardResForm
                .builder()
                .boardId(savedBoard.getBoardId())
                .title(savedBoard.getTitle())
                .cafeTitle(savedBoard.getCafeTitle())
                .content(savedBoard.getContent())
                .createDate(savedBoard.getCreateDate())
                .member(new Member(savedBoard.getMember().getId(), savedBoard.getMember().getEmail(), savedBoard.getNickname()))
                .filePathList(savedBoard.getFilePathList())
                .commentList(savedBoard.getCommentList().stream().map((c) -> CommentResForm.builder()
                        .commentId(c.getCommentId())
                        .createdDate(c.getCreatedDate())
                        .modifiedDate(c.getModifiedDate())
                        .text(c.getText())
                        .member(new Member(c.getMember().getId(), c.getMember().getEmail(), c.getMember().getNickname()))
                        .build()).toList())
                .build();
        return board;
    }

    @Override
    @Transactional
    public List<BoardResForm> search(String keyword) {
        List<MemberBoard> findBoards = boardRepository.findByContentContaining(keyword);
        List<BoardResForm> boardResFormList = findBoards.stream().map((fb) -> BoardResForm
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
        Long memberId = redisService.getValueByKey(requestForm.getUserToken());
        Optional<Member> isMember = memberRepository.findById(memberId);
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

        memberBoard.setContent(requestForm.getContent());
        memberBoard.setTitle(requestForm.getTitle());
        memberBoard.setCafeTitle(requestForm.getCafeTitle());
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
                .cafeTitle(memberBoard.getCafeTitle())
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
        Long memberId = redisService.getValueByKey(Objects.requireNonNull(headers.get("authorization")).get(0));
        Optional<Member> isMember = memberRepository.findById(memberId);
        if (finedBoard.getMember().getId().equals(memberId)){

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
        if (totalBoard % size == 0) {
            return totalBoard / size;
        } else {
            return totalBoard / size + 1;
        }
    }
    @Override
    public Integer getMyBoardTotalPage(HttpHeaders headers) {
        Long memberId = redisService.getValueByKey(Objects.requireNonNull(headers.get("authorization")).get(0));
        Optional<Member> isMember = memberRepository.findById(memberId);
        if (isMember.isEmpty()) {
            log.info("회원이 아닙니다.");
            return null;
        }
        Member findMember = isMember.get();
        Integer totalBoard = (int) boardRepository.findById(findMember.getId()).stream().count();
        log.info(String.valueOf(totalBoard));
        Integer size = 10;
        if (totalBoard % size == 0) {
            return totalBoard / size;
        } else {
            return totalBoard / size + 1;
        }
    }
}
