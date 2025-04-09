package br.insper.produto.security;

import br.insper.produto.login.LoginService;
import br.insper.produto.usuario.Usuario;
import br.insper.produto.usuario.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String method = request.getMethod();

        if (method.equals("POST")) {
            String token = request.getHeader("Authorization");

            Usuario usuario = loginService.validateToken(token);

            if (!usuario.getPapel().equals("ADMIN")) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }

            filterChain.doFilter(request, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
