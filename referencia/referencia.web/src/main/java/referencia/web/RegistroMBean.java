package referencia.web;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import hammerstone.core.exception.BCException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import referencia.dominio.Usuario;
import referencia.dominio.enumeracoes.RegistroUsuarioEnum;
import referencia.negocio.service.IRegistroSC;

@SessionScoped
@ManagedBean(name = "registroMBean")
@Slf4j
public class RegistroMBean extends AbstractManagedBean {

    private static final long serialVersionUID = 1L;

    private static final String EMPTY = "";

    @Getter
    @Setter
    private String login;

    @Getter
    @Setter
    private String senha;

    @Getter
    @Setter
    private String confirmacaoSenha;

    @Getter
    @Setter
    private String nome;

    @Getter
    @Setter
    private String feedback;

    @Getter
    @Setter
    private String tipoFeedback;

    @EJB
    private transient IRegistroSC registroSC;

    /**
     * Limpa os campos da sessão.
     */
    public void reset() {

        log.debug("reset {}", getLogin());

        setLogin(EMPTY);

        senha = EMPTY;
    }

    public String pagRegistrar() {

        return "/public/registroUsuario";
    }

    /**
     * Login no StarTeam
     * 
     * @param request
     * @param context
     * @return
     * @throws BCException
     */
    public void registrar() {

        Usuario usuario = new Usuario();

        usuario.setLogin(login);

        usuario.setSenha(senha);

        usuario.setConfirmacaoSenha(confirmacaoSenha);
        
        usuario.setNome(nome);

        RegistroUsuarioEnum status = registroSC.registrar(usuario);

        feedback = status.getFeedback();

        tipoFeedback = status.getTipoFeedback();
    }
}
