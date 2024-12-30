package maratik.budget_manager.security;

import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import maratik.budget_manager.api.services.UserService;
import maratik.budget_manager.model.entities.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

/**
 * Проверяет цифровой отпечаток юзера, который шлет запрос. Если юзер есть в бд, то кладет его в
 * {@link org.springframework.security.core.context.SecurityContextHolder}. Если нет, то регистрирует и затем кладет.
 */
@Component
@RequiredArgsConstructor
public class FingerprintFilter extends OncePerRequestFilter {

    private final UserService userService;

    @Override
    protected void doFilterInternal(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull FilterChain filterChain) throws ServletException, IOException {
        String fingerprint = request.getHeader("fingerprint");

        if (fingerprint == null || fingerprint.isEmpty()) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("No fingerprint provided.");
            return;
        }

        Optional<User> userOptional = userService.loadUserByFingerprint(fingerprint);
        UserDetails userDetails;

        if (userOptional.isPresent()) {
            userDetails = userOptional.get();
        } else {
            String username = request.getParameter("username");
            if (username == null || username.isEmpty()) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("No username provided for registration.");
                return;
            }
            User user = new User();
            user.setUsername(username);
            user.setFingerprint(fingerprint);
            user = userService.save(user);
            userDetails = user;
        }

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        context.setAuthentication(authToken);
        SecurityContextHolder.setContext(context);

        filterChain.doFilter(request, response);
    }
}
