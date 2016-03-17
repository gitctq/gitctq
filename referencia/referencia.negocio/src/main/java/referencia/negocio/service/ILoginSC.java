package referencia.negocio.service;

import referencia.dominio.Usuario;
import referencia.dominio.enumeracoes.StatusUsuarioEnum;

public interface ILoginSC {

    StatusUsuarioEnum login(Usuario user);

}
