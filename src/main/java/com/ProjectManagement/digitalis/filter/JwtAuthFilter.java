package com.ProjectManagement.digitalis.filter;

/*import com.springAuth.services.jwt.UserServiceImpl;
import com.springAuth.utils.JwtUtilService;*/
import com.ProjectManagement.digitalis.service.jwt.UserServiceImpl;
import com.ProjectManagement.digitalis.utils.JwtUtilService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter{


        private final HandlerExceptionResolver handlerExceptionResolver;
        private final JwtUtilService jwtService;
        private final UserServiceImpl userService;

        // Méthode qui s'exécute pour chaque requête HTTP interceptée par le filtre.
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {

            if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
                response.setStatus(HttpServletResponse.SC_OK);
                return;
            }
            // Récupère l'en-tête Authorization de la requête.
            final String authHeader = request.getHeader("Authorization");

            // Si l'en-tête est vide ou ne commence pas par "Bearer ", on continue sans traitement.
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            try {
                // Extrait le token JWT en ignorant le préfixe "Bearer ".
                final String jwt = authHeader.split(" ")[1];
                // Utilise le service JwtUtilService pour extraire l'email (ou nom d'utilisateur) du JWT.
                final String userEmail = jwtService.extractUsername(jwt);


                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                // Si l'email est extrait du JWT et qu'il n'y a pas d'utilisateur déjà authentifié.
                if (userEmail != null && authentication == null) {

                    UserDetails userDetails = userService.loadUserByUsername(userEmail);

                    // Vérifie si le token est valide pour l'utilisateur.
                    if (jwtService.isTokenValid(jwt, userDetails)) {
                        // Crée un objet UsernamePasswordAuthenticationToken avec les informations de l'utilisateur.
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }

                filterChain.doFilter(request, response);

            } catch (Exception exception) {
                handlerExceptionResolver.resolveException(request, response, null, exception);
            }
        }


}
