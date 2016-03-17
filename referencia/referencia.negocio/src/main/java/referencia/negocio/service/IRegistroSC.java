package referencia.negocio.service;

import referencia.dominio.Usuario;
import referencia.dominio.enumeracoes.RegistroUsuarioEnum;

public interface IRegistroSC {

    RegistroUsuarioEnum registrar(Usuario user);

}
