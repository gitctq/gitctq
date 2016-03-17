package referencia.negocio;

import static referencia.dominio.enumeracoes.RegistroUsuarioEnum.VALIDO;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import referencia.dominio.Usuario;
import referencia.dominio.enumeracoes.RegistroUsuarioEnum;
import referencia.negocio.service.IRegistroSC;
import referencia.persistencia.RegistroRepositorio;
import referencia.persistencia.UsuarioRepositorio;
import referencia.persistencia.excecoes.RepoException;

@Stateless
@Slf4j
public class RegistroBC implements IRegistroSC {

    @Inject
    RegistroRepositorio repo;

    @Inject
    private UsuarioRepositorio usuarioRepo;

    @Override
    public RegistroUsuarioEnum registrar(Usuario usuario) {

        RegistroUsuarioEnum status = usuario.isValido();

        if (VALIDO.equals(status)) {

            boolean existeUsuario;

            try {

                existeUsuario = usuarioRepo.existe(usuario);

                if (existeUsuario) {

                    status = RegistroUsuarioEnum.LOGIN_JA_EXISTE;

                    log.info("Usuário já existe: {}", usuario);

                } else {

                    repo.salvar(usuario);

                    log.info("Usuário registrado: {}", usuario);
                }

            } catch (RepoException e) {

                status = RegistroUsuarioEnum.ERRO;

                log.error(status.getFeedback() + ": " + usuario, e);
            }
        }

        return status;
    }

}
