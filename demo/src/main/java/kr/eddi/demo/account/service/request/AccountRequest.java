package kr.eddi.demo.account.service.request;

import kr.eddi.demo.account.entity.Account;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AccountRequest {
    final private String email;
    final private String password;
    final private String memberType;
    final private String nickname;

    public Account toAccount () {
        return new Account(email, password, memberType, nickname);
    }

}
