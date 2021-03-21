package vn.nuce.ducnh.controllers.DTO.request;

import lombok.Data;
import sun.security.util.ECUtil;
import vn.nuce.ducnh.entity.EUserType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
 
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    private String userType;

}
