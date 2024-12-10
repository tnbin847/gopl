package com.bins.gopl.domain.user.dto;

import com.bins.gopl.domain.user.dto.constant.LoginType;
import com.bins.gopl.global.util.StatusFormat;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author 박 수 빈
 * @version 1.0.0
 */

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SignUpRequest {
    private Long userId;

    @NotBlank(message = "이름을 입력해주세요.")
    @Pattern(regexp = "^[가-힣]{2,4}$", message = "이름은 2~4자의 한글로 입력해주세요.")
    private String name;

    @NotBlank(message = "아이디를 입력해주세요.")
    @Pattern(regexp = "^[A-Za-z0-9]{4,20}$", message = "아이디는 영문과 숫자 조합의 4~20자로 입력해주세요.")
    private String id;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일의 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$", message = "비밀번호는 영문과 숫자와 특수문자 조합의 8~16자로 입력해주세요.")
    @Setter
    private String password;

    @NotBlank(message = "비밀번호를 한 번 더 입력해주세요.")
    private String passwordConfirm;

    @Setter
    private LoginType loginType;

    /**
     * 아래의 세 필드({@code enabled}, {@code useYn}, {@code deleteYn})의 경우, 새로이 등록 처리하고자 하는 사용자 계정에 대한
     * 정보들이기 때문에 기본적으로 계정 활성화는 <b>활성화</b> 상태로, 사용 여부는 <b>사용</b>으로, 삭제 여부는 <b>삭제 하지 않음</b>으로 값들을 초기화한다.
     */
    private final int enabled = StatusFormat.YES.getNumber();

    private final String useYn = StatusFormat.YES.getSymbol();

    private final String deleteYn = StatusFormat.NO.getSymbol();

    @Builder
    public SignUpRequest(String name, String id, String email, String password, LoginType loginType) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.password = password;
        this.loginType = loginType;
    }
}