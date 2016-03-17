package referencia.negocio;

import static referencia.dominio.enumeracoes.StatusUsuarioEnum.NAO_LOGADO;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import referencia.dominio.Usuario;
import referencia.dominio.enumeracoes.StatusUsuarioEnum;
import referencia.negocio.service.ILoginSC;
import referencia.persistencia.UsuarioRepositorio;
import referencia.persistencia.excecoes.RepoException;

@Stateless
@Slf4j
public class LoginBC implements ILoginSC {

    private static final String ERRO = "Usuário ou senha inválidos";

    @Inject
    UsuarioRepositorio repo;

    @Override
    public StatusUsuarioEnum login(Usuario usuario) {

        StatusUsuarioEnum status;

        try {

            status = repo.verificarCredenciais(usuario);

        } catch (RepoException e) {

            log.error(ERRO, e);

            status = NAO_LOGADO;

        }

        return status;
    }
}
