package com.example.demo.user.service;

import com.example.demo.board.dto.BoardDto;
import com.example.demo.board.entity.Board;
import com.example.demo.board.entity.BoardLike;
import com.example.demo.board.repository.BoardRepository;
import com.example.demo.user.controller.form.UserSignInResponseForm;
import com.example.demo.user.dto.UserDto;
import com.example.demo.user.entity.*;
import com.example.demo.user.repository.*;
import com.example.demo.user.service.request.UserLogInRequest;
import com.example.demo.user.service.request.UserSignInRequest;
import com.example.demo.user.service.request.UserSignUpRequest;
import com.example.demo.user.service.request.UserSignValidateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    final private UserRepository userRepository;
    final private UserRoleRepository userRoleRepository;
    final private BoardRepository boardRepository;
    final private BookmarkRepository bookmarkRepository;
    final private UserTokenRepository userTokenRepository = UserTokenRepositoryImpl.getInstance();

    @Override
    public List<Board> bookmarkList(Long userId) {
        List<Bookmark> bookmarks = bookmarkRepository.findByUserId(userId);
        List<Board> boardList = new ArrayList<Board>();
        for(Bookmark bookmark : bookmarks){
            boardList.add(bookmark.getBoard());
        }
        return boardList;
    }

    @Override
    public User logIn(String uid) {
        final Optional<User> maybeUser = userRepository.findByUid(uid);
        if (!maybeUser.isPresent()) {
            return null;
        }
        return maybeUser.get();
    }

    @Override
    public Boolean signUp(UserSignUpRequest userSignUpRequest) {
        final User user = userRepository.save(userSignUpRequest.toUser());
        return true;
    }

    @Override
    public List<String> SignValidate(UserSignValidateRequest userSignValidateRequest) {
        final Optional<User> maybeEmail = userRepository.findByEmail(userSignValidateRequest.getEmail());
        final Optional<User> maybeNickName = userRepository.findByNickName(userSignValidateRequest.getNickName());
        List<String> maybe = new ArrayList<String>();
        if (maybeEmail.isPresent()) {
            maybe.add("email");
        }
        if (maybeNickName.isPresent()) {
            maybe.add("nickName");
        }
        return maybe;
    }

    @Override
    @Transactional
    public UserSignInResponseForm signIn(UserSignInRequest request) {
        final Optional<User> maybeUser = userRepository.findByEmail(request.getEmail());
        if (maybeUser.isEmpty()) {
            return null;
        }
        final User user = maybeUser.get();
        if (user.getUid().equals(request.getPassword())){
            final String userToken = UUID.randomUUID().toString();
            userTokenRepository.save(userToken, user.getUserId());

            final Role role = userRoleRepository.findRoleInFoByUser(user);

            return new UserSignInResponseForm(userToken, role.getRoleType().name());
        }
        return null;
    }

    @Override
    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public RoleType lookup(String userToken) {
        final Long userId = userTokenRepository.findUserIdByToken(userToken);
        final Optional<User> maybeUser = userRepository.findById(userId);

        if (maybeUser.isEmpty()) {
            return null;
        }
        final User user = maybeUser.get();
        final Role role = userRoleRepository.findRoleInFoByUser(user);

        return role.getRoleType();
    }

    @Override
    public Long findUserId(String userToken) {
        return userTokenRepository.findUserIdByToken(userToken);
    }

    @Override
    public void deleteBookmark(Bookmark bookmark) {
        bookmarkRepository.delete(bookmark);
    }


    @Override
    @Transactional
    public void addBookmark(Long userId, Long boardId) {

        Board board = boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException("게시물을 찾을 수 없습니다"));
        List<Bookmark> bookmarks = bookmarkRepository.findByBoard_BoardIdAndUserId(boardId,userId);

        if (bookmarks.isEmpty()) {
            bookmarkRepository.save(new Bookmark(userId ,board));
        } else {
            log.info("=========================================================");
            log.info(bookmarks.get(0).getBoard().getTitle().toString());
            bookmarkRepository.deleteByBoard_BoardIdAndUserId(boardId,userId);
        }
    }

}
