package referencia.persistencia;

import static referencia.dominio.enumeracoes.StatusUsuarioEnum.LOGADO;
import static referencia.dominio.enumeracoes.StatusUsuarioEnum.NAO_LOGADO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import referencia.dominio.Usuario;
import referencia.dominio.enumeracoes.StatusUsuarioEnum;
import referencia.persistencia.core.BaseRepositorio;
import referencia.persistencia.excecoes.RepoException;

public class UsuarioRepositorio extends BaseRepositorio<Usuario> {

    public StatusUsuarioEnum verificarCredenciais(Usuario usuario) throws RepoException {

        Map<String, Object> propriedades = new HashMap<>();

        propriedades.put("login", usuario.getLogin());

        propriedades.put("senha", usuario.getSenha());

        List<Usuario> usuarios = construirQuery("Usuario.verificarCredenciais", propriedades).getResultList();

        if (usuarios != null && usuarios.size() == 1) {

            return LOGADO;
        }

        return NAO_LOGADO;
    }

    public boolean existe(Usuario usuario) throws RepoException {

        Map<String, Object> propriedades = new HashMap<>();

        propriedades.put("login", usuario.getLogin());

        List<Usuario> usuarios = construirQuery("Usuario.buscarPorLogin", propriedades).getResultList();

        if (usuarios != null && !usuarios.isEmpty()) {

            return true;
        }

        return false;
    }

}
