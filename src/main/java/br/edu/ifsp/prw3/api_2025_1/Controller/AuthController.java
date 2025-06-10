package br.edu.ifsp.prw3.api_2025_1.Controller;

import br.edu.ifsp.prw3.api_2025_1.dto.DadosAuth;
import br.edu.ifsp.prw3.api_2025_1.Models.Usuario;
import br.edu.ifsp.prw3.api_2025_1.utils.DadosTokenJWT;
import br.edu.ifsp.prw3.api_2025_1.Services.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {
    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;


    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAuth dados) {

        var token = new UsernamePasswordAuthenticationToken( dados.login(), dados.password() );

        var authentication = manager.authenticate(token);

        var tokenJWT = tokenService.generateToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}
