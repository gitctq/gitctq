package referencia.persistencia.excecoes;

public class RepoException extends Exception {

    private static final long serialVersionUID = 1L;

    public RepoException(Exception e) {

        super(e);
    }

    public RepoException(String erro) {

        super(erro);
    }
}
