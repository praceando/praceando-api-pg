package blomera.praceando.praceandoapipg.authentication.service;

import blomera.praceando.praceandoapipg.model.Acesso;
import blomera.praceando.praceandoapipg.model.Usuario;
import blomera.praceando.praceandoapipg.service.AcessoService;
import blomera.praceando.praceandoapipg.service.UsuarioService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UsuarioService userService;
    private final AcessoService acessoService;

    public CustomUserDetailsService(UsuarioService userService, AcessoService acessoService) {
        this.userService = userService;
        this.acessoService = acessoService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario user = userService.getUsuarioByEmail(email);
        Acesso role = acessoService.getAcessoById(user.getAcesso().getId());

        return new org.springframework.security.core.userdetails.User(
                user.getDsEmail(),
                user.getDsSenha(),
                true,
                true,
                true,
                true,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.getNmAcesso()))
        );
    }
}
