package referencia.dominio.excecoes;


public class DominioException extends Exception {
    
    private static final long serialVersionUID = 1L;

    public DominioException(Exception ex) {

        super(ex);
    }

}
