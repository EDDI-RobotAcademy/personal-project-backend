package com.example.demo.board.service;


import com.example.demo.board.entity.FilePaths;
import com.example.demo.board.entity.MemberBoard;
import com.example.demo.board.form.RequestModifyBoardForm;
import com.example.demo.board.form.RequestRegisterBoardForm;
import com.example.demo.board.form.ResponseBoardForm;
import com.example.demo.board.reposiitory.FilePathsRepository;
import com.example.demo.board.reposiitory.MemberBoardRepository;
import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.comment.entity.Comment;
import com.example.demo.comment.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    final private CommentRepository commentRepository;

    @Override
    public List<MemberBoard> list() {
        return boardRepository.findAll(Sort.by(Sort.Direction.DESC, "boardId"));
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
    public ResponseBoardForm read(Long boardId) {
        Optional<MemberBoard> maybeBoard = boardRepository.findByIdWithMember(boardId);
        if (maybeBoard.isEmpty()) {
            return null;
        }
        List<Long> idList = maybeBoard.get().getFilePathList().stream().map(FilePaths::getFileId).toList();
        // lazy 걸려있어서 proxy patten안에 있어서 못가져옴 Transactional 하면 해결, 그치만 조회해야 거기서 쿼리가 한번 나간다.
        // joinFetch로 사용 가능
        List<FilePaths> savedFilePath = maybeBoard.get().getFilePathList();

//        List<Comment> commentList = commentRepository.findByMemberBoard(maybeBoard.get());
//        .stream().toList();
        final ResponseBoardForm responseBoardForm = new ResponseBoardForm(maybeBoard.get(), savedFilePath);

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
            return boardRepository.save(memberBoard);
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
}
