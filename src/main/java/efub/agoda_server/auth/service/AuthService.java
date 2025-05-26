package efub.agoda_server.auth.service;

import efub.agoda_server.global.exception.CustomException;
import efub.agoda_server.global.exception.ErrorCode;
import efub.agoda_server.user.domain.User;
import efub.agoda_server.user.repository.UserRepository;

import efub.agoda_server.auth.dto.res.KakaoMemberInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public User upsertKakaoUser(Map<String, Object> attrs) {
        KakaoMemberInfoResponse info = new KakaoMemberInfoResponse(attrs);

        return userRepository.findByEmail(info.getEmail())
                .map(existing -> {

                    existing.updateProfile(info.getNickname(), info.getProfileImageUrl());
                    return existing;
                })
                .orElseGet(() -> {
                    User u = User.builder()
                            .email(info.getEmail())
                            .username(info.getNickname())
                            .profileImg(info.getProfileImageUrl())
                            .build();
                    return userRepository.save(u);
                });
    }

    @Transactional
    public void withdraw(User user) {
        if (user == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS);
        }
        userRepository.delete(user);
    }

    @Transactional(readOnly = true)
    public void logout(User user) {
        if (user == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS);
        }
    }

}
