package kr.eddi.demo.account.controller.form;

import kr.eddi.demo.account.entity.Account;
import kr.eddi.demo.account.service.request.AccountRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AccountRegisterForm {

    final private String email;
    final private String password;
    final private String memberType;

  public AccountRequest toAccountRequest(){
      return new AccountRequest(email,password,memberType);
  }
}
