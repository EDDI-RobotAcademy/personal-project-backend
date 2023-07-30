package com.example.demo.account.service;

import com.example.demo.account.controller.form.modify.AddressModifyForm;
import com.example.demo.account.controller.form.modify.NicknameModifyForm;
import com.example.demo.account.controller.form.modify.PasswordModifyForm;
import com.example.demo.account.controller.form.request.AccountLoginRequestForm;
import com.example.demo.account.controller.form.request.AccountReadRequestForm;
import com.example.demo.account.controller.form.request.CheckPasswordRequestForm;
import com.example.demo.account.entity.Account;
import com.example.demo.account.entity.AccountRole;
import com.example.demo.account.entity.Role;
import com.example.demo.account.entity.RoleType;
import com.example.demo.account.repository.*;
import com.example.demo.account.service.request.BusinessAccountRegisterRequest;
import com.example.demo.account.service.request.NormalAccountRegisterRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static com.example.demo.account.entity.RoleType.BUSINESS;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{

    final private AccountRepository accountRepository;
    final private RoleRepository roleRepository;
    final private AccountRoleRepository accountRoleRepository;
    final private UserTokenRepository userTokenRepository = UserTokenRepositoryImpl.getInstance();
    final private JavaMailSender emailsender; // Bean 등록해둔 MailConfig 를 emailsender 라는 이름으로 autowired
    private String ePw; // 인증번호


    @Override
    public Boolean normalAccountRegister(NormalAccountRegisterRequest request) {
        final Optional<Account> maybeAccount = accountRepository.findByEmail(request.getEmail());
        // 중복 이메일 확인
        if (maybeAccount.isPresent()) {
            return false;
        }

        // 계정 생성
        final Account account = accountRepository.save(request.toAccount());

        // 회원 타입 부여
        final Role role = roleRepository.findByRoleType(request.getRoleType()).get();
        final AccountRole accountRole = new AccountRole(role, account);
        accountRoleRepository.save(accountRole);

        return true;
    }

    @Override
    public Boolean businessAccountRegister(BusinessAccountRegisterRequest request) {
        final Optional<Account> maybeAccount =
                accountRepository.findByEmail(request.getEmail());
        // 중복 이메일 확인
        if (maybeAccount.isPresent()) {
            return false;
        }

        final String businessNumber = request.getBusinessNumber();

        // 중복 사업자 번호 확인
        final Optional<AccountRole> maybeAccountRole =
                accountRoleRepository.findByBusinessNumber(businessNumber);

        if(maybeAccountRole.isPresent()) {
            return false;
        }

        // 계정 생성
        final Account account = accountRepository.save(request.toAccount());

        // 회원 타입 부여
        final Role role = roleRepository.findByRoleType(request.getRoleType()).get();

        final AccountRole accountRole = new AccountRole(role, account, businessNumber);
        accountRoleRepository.save(accountRole);

        return true;
    }

    @Override
    public Boolean checkEmailDuplication(String email) {
        Optional<Account> maybeAccount = accountRepository.findByEmail(email);

        if (maybeAccount.isPresent()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String login(AccountLoginRequestForm requestForm) {
        Optional<Account> maybeAccount = accountRepository.findByEmail(requestForm.getEmail());

        if(maybeAccount.isPresent()) {
            final Account account = maybeAccount.get();

            if(requestForm.getPassword().equals(maybeAccount.get().getPassword())) {
                final String userToken = UUID.randomUUID().toString();
                userTokenRepository.save(userToken, account.getId());
                return userToken;
            }
        }

        return "";
    }

    @Override
    public RoleType lookup(String email) {
        final Optional<Account> maybeAccount = accountRepository.findByEmail(email);

        if (maybeAccount.isEmpty()) {
            return null;
        }

        final Account account = maybeAccount.get();
        final Role role = accountRoleRepository.findRoleByAccount(account);

        log.info("roleType: " + role.getRoleType());
        return role.getRoleType();
    }

    @Override
    public Long findAccountId(String userToken) {
        final Long accountId = userTokenRepository.findAccountIdByUserToken(userToken);

        log.info("accountId: " + accountId);

        return accountId;
    }

    @Override
    public Boolean businessCheck(Long accountId) {
        Optional<AccountRole> maybeAccountRole = accountRoleRepository.findByAccountIdWithRole(accountId);

        log.info("가져온 어카운트 롤 ID: " + String.valueOf(maybeAccountRole.get().getId()));
        log.info("어카운트 롤의 롤타입: " + String.valueOf(maybeAccountRole.get().getRole().getRoleType()));

        Role role= maybeAccountRole.get().getRole();
        if (role.getRoleType().equals(BUSINESS)){
            return true;
        }

        return false;
    }

    @Override
    public String returnEmail(String userToken) {
        Long accountId = userTokenRepository.findAccountIdByUserToken(userToken);

        Optional<Account> maybeAccount = accountRepository.findById(accountId);
        if (maybeAccount.isPresent()) {
            String returnEmail = maybeAccount.get().getEmail();

            return returnEmail;
        }

        return "";
    }

    // 메일 내용 작성
    @Override
    public MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException {
//		System.out.println("보내는 대상 : " + to);
//		System.out.println("인증 번호 : " + ePw);

        MimeMessage message = emailsender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, to);// 보내는 대상
        message.setSubject("풋풋(FoodFoot) 회원가입 이메일 인증");// 제목

        String msgg = "";
        msgg += "<div style='margin:100px;'>";
        msgg += "<h1> 안녕하세요</h1>";
        msgg += "<h1> 맛있는 발자취를 걷는 FootFood 입니다</h1>";
        msgg += "<br>";
        msgg += "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
        msgg += "<br>";
        msgg += "<p>가입을 진심으로 감사드립니다<p>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg += "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg += "<div style='font-size:130%'>";
        msgg += "CODE : <strong>";
        msgg += ePw + "</strong><div><br/> "; // 메일에 인증번호 넣기
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html");// 내용, charset 타입, subtype
        // 보내는 사람의 이메일 주소, 보내는 사람 이름
        message.setFrom(new InternetAddress("wlsdbwls321@naver.com", "FoodFoot"));// 보내는 사람

        return message;
    }

    // 랜덤 인증 코드 전송
    @Override
    public String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤, rnd 값에 따라서 아래 switch 문이 실행됨

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    // a~z (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    // A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }

        return key.toString();
    }

    // 메일 발송
    // sendSimpleMessage 의 매개변수로 들어온 to 는 곧 이메일 주소가 되고,
    // MimeMessage 객체 안에 내가 전송할 메일의 내용을 담는다.
    // 그리고 bean 으로 등록해둔 javaMail 객체를 사용해서 이메일 send!!
    @Override
    public String sendSimpleMessage(String to) throws MessagingException, UnsupportedEncodingException {

        ePw = createKey(); // 랜덤 인증번호 생성

        // TODO Auto-generated method stub
        MimeMessage message = createMessage(to); // 메일 발송
        try {// 예외처리
            emailsender.send(message);
        } catch (MailException es) {
            es.printStackTrace();
            throw new IllegalArgumentException();
        }


        return ePw; // 메일로 보냈던 인증 코드를 서버로 반환
    }

    @Override
    public Account read(AccountReadRequestForm requestForm) {
        final String userToken = requestForm.getUserToken();
        final Long accountId = userTokenRepository.findAccountIdByUserToken(userToken);

        Optional <Account> maybeAccount = accountRepository.findById(accountId);
        if (maybeAccount.isPresent()) {
            return maybeAccount.get();
        }

        return null;
    }

    @Override
    public String returnPassword(CheckPasswordRequestForm requestForm) {

        final String email = requestForm.getEmail();
        Optional<Account> maybeAccount = accountRepository.findByEmail(email);

        if (maybeAccount.isPresent()) {
            final String returnPassword = maybeAccount.get().getPassword();

            return returnPassword;
        }

        return null;
    }

    @Override
    public String modifyNickname(Long id, NicknameModifyForm modifyForm) {
        Optional<Account> maybeAccount = accountRepository.findById(id);

        if (maybeAccount.isEmpty()) {
            log.info("존재하지 않는 회원입니다.");
            return null;
        }

        Account account = maybeAccount.get();
        account.setNickName(modifyForm.getNickName());

        accountRepository.save(account);

        return modifyForm.getNickName();
    }

    @Override
    public String modifyAddress(Long id, AddressModifyForm modifyForm) {
        Optional<Account> maybeAccount = accountRepository.findById(id);

        if (maybeAccount.isEmpty()) {
            log.info("존재하지 않는 회원입니다.");
            return null;
        }

        Account account = maybeAccount.get();
        account.setAddress(modifyForm.getAddress());

        accountRepository.save(account);

        return modifyForm.getAddress();
    }

    @Override
    public String modifyPassword(Long id, PasswordModifyForm modifyForm) {
        Optional<Account> maybeAccount = accountRepository.findById(id);

        if (maybeAccount.isEmpty()) {
            log.info("존재하지 않는 회원입니다.");
            return null;
        }

        Account account = maybeAccount.get();
        account.setPassword(modifyForm.getPassword());

        accountRepository.save(account);

        return modifyForm.getPassword();
    }

    @Override
    public void delete(Long id) {
        accountRoleRepository.deleteAllByAccountId(id);
        accountRepository.deleteById(id);
    }

    @Override
    public String returnNickname(Long id) {

        Optional<Account> maybeAccount = accountRepository.findById(id);
        if (maybeAccount.isPresent()) {
            return maybeAccount.get().getNickName();
        }

        return null;
    }

    @Override
    public RoleType returnRoleType(Long id) {

        Optional<Account> maybeAccount = accountRepository.findById(id);
        if (maybeAccount.isPresent()) {
            RoleType roleType = accountRoleRepository.findRoleByAccount(maybeAccount.get()).getRoleType();

            return roleType;
        }

        return null;
    }

    @Override
    public Boolean checkBusinessNumberDuplication(String businessNumber) {

        final Optional<AccountRole> maybeAccountRole = accountRoleRepository.findByBusinessNumber(businessNumber);
        if (maybeAccountRole.isPresent()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Boolean checkNickNameDuplication(String nickName) {
        Optional<Account> maybeAccount = accountRepository.findByNickName(nickName);

        if (maybeAccount.isPresent()) {
            return false;
        } else {
            return true;
        }
    }
}
