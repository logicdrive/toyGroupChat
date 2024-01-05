package toyGroupChat.user.reqDtos;

import lombok.Data;

@Data
public class SignUpReqDto {
    private String email;
    private String password;
    private String name;
    private String dataUrl;

    public String toString() { 
        return String.format("%s(email=%s, name=%s, dataUrlLength=%d)",
            this.getClass().getSimpleName(), this.email, this.name, this.dataUrl.length());
    }
}