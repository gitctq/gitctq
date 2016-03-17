package referencia.dominio.enumeracoes;

import lombok.Getter;

public enum StatusCategoriaEnum {

    VALIDA("", ""), SALVA("Categoria salva com sucesso;", "sucesso"), ERRO("Erro ao salvar categoriao", "erro"), INVALIDA("Preencha os campos obrigatórios", "erro"), 
    ERRO_EXCLUSAO("Ocorreu algum erro ao tentar excluir essa categoria.", "erro"), EXCLUIDA("Categoria excluída com sucesso", "sucesso");

    @Getter
    private String feedback;

    @Getter
    private String tipoFeedback;

    private StatusCategoriaEnum(String feedback, String tipoFeedback) {

        this.feedback = feedback;

        this.tipoFeedback = tipoFeedback;
    }
}
