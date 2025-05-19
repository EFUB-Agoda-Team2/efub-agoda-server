package efub.agoda_server.security.oauth2;

import efub.agoda_server.domain.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import java.util.*;

public class CustomOAuth2User implements OAuth2User {
    @Getter
    private final User user;
    private final Map<String, Object> attrs;
    private final String nameKey;

    public CustomOAuth2User(User user, Map<String, Object> attrs, String nameKey) {
        this.user = user;
        this.attrs = attrs;
        this.nameKey = nameKey;
    }

    @Override public Map<String, Object> getAttributes() { return attrs; }
    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return Collections.emptyList(); }
    @Override public String getName() { return String.valueOf(attrs.get(nameKey)); }
}