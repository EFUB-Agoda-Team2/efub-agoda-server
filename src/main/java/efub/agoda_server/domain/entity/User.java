package efub.agoda_server.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String username;

    @Column(name = "profile_img")
    private String profileImg;

    public void updateProfile(String username, String profileImg) {
        if (username != null && !username.isBlank()) {
            this.username = username;
        }
        if (profileImg != null && !profileImg.isBlank()) {
            this.profileImg = profileImg;
        }
    }
}