package model.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SignUp {

    private String firstName;
    private String Lastname;
    private String emailAddress;
    private String password;

}
