package referencia.dominio.enumeracoes;

import lombok.Getter;

public enum RegistroUsuarioEnum {

    INVALIDO("Preencha todos os campos", "erro"), SENHAS_DIFERENTES("A senha e a confirmação não conferem", "erro"), VALIDO("Usuário registrado com sucesso", "sucesso"), LOGIN_INVALIDO("O login deve conter pelo menos 5 caracteres",
            "erro"), SENHA_INVALIDA("A senha deve conter pelo menos 6 caracteres", "erro"), LOGIN_JA_EXISTE("Login já existe no sistema", "erro"), ERRO("Ocorreu algum erro ao registrar o usuário.", "erro");

    @Getter
    private String feedback;

    @Getter
    private String tipoFeedback;

    private RegistroUsuarioEnum(String feedback, String tipoFeedback) {

        this.feedback = feedback;

        this.tipoFeedback = tipoFeedback;
    }
}
