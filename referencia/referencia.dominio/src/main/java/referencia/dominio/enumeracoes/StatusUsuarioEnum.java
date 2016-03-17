package referencia.dominio.enumeracoes;

import lombok.Getter;

public enum StatusUsuarioEnum {

    LOGADO(""), SEM_PERMISSAO(""), NAO_LOGADO("Usuário ou senha inválidos");

    @Getter
    private String feedback;

    private StatusUsuarioEnum(String feedback) {

        this.feedback = feedback;

    }
}
