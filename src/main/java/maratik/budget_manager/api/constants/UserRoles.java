package maratik.budget_manager.api.constants;

import lombok.Getter;

@Getter
public enum UserRoles {
    USER("USER_ROLE");

    private String role;

    UserRoles(String role) {
        this.role = role;
    }
}
