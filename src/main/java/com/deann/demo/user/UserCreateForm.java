package com.deann.demo.user;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {
    @Size(min = 3,max = 25)
    @NotEmpty(message = "IDを入力してください")
    private String username;
    @NotEmpty(message = "パスワードを入力してください")
    private String password1;
    @NotEmpty(message = "入力したパスワードと違います")
    private String password2;
    @NotEmpty(message = "メールを入力してください")
    @Email
    private String email;

}
