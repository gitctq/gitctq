package referencia.dominio;

import static javax.persistence.GenerationType.IDENTITY;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static referencia.dominio.enumeracoes.RegistroUsuarioEnum.INVALIDO;
import static referencia.dominio.enumeracoes.RegistroUsuarioEnum.LOGIN_INVALIDO;
import static referencia.dominio.enumeracoes.RegistroUsuarioEnum.SENHAS_DIFERENTES;
import static referencia.dominio.enumeracoes.RegistroUsuarioEnum.SENHA_INVALIDA;
import static referencia.dominio.enumeracoes.RegistroUsuarioEnum.VALIDO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import referencia.dominio.core.BaseEntity;
import referencia.dominio.enumeracoes.RegistroUsuarioEnum;

/**
 * 
 * @author Cindy
 *
 */
@Entity
@Table(name = "Usuario")
@ToString
@NamedQueries({

@NamedQuery(name = "Usuario.verificarCredenciais", query = "SELECT usuario FROM Usuario usuario WHERE usuario.login = :login AND usuario.senha = :senha"),
@NamedQuery(name = "Usuario.buscarPorLogin", query = "SELECT usuario FROM Usuario usuario WHERE usuario.login = :login")
})
@EqualsAndHashCode(callSuper = true, of = "login")
public class Usuario extends BaseEntity<Integer> {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @Column(name = "Id")
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Getter
    @Setter
    @Column(name = "Login", columnDefinition="varchar")
    private String login;

    @Getter
    @Setter
    @Column(name = "Senha", columnDefinition="varchar")
    private String senha;

    @Getter
    @Setter
    @Column(name = "Nome", columnDefinition="nvarchar")
    private String nome;

    @Setter
    private transient String confirmacaoSenha;

    public RegistroUsuarioEnum isValido() {

        RegistroUsuarioEnum status = VALIDO;

        if (isBlank(login) || isBlank(senha) || isBlank(confirmacaoSenha) || isBlank(nome)) {

            status = INVALIDO;

        } else if (login.length() < 5) {

            status = LOGIN_INVALIDO;

        } else if (senha.length() < 6) {

            status = SENHA_INVALIDA;

        } else if (!senha.equals(confirmacaoSenha)) {

            status = SENHAS_DIFERENTES;
        }

        return status;
    }
}
