package referencia.persistencia;

import java.util.HashMap;
import java.util.Map;

import referencia.dominio.Categoria;
import referencia.dominio.Produto;
import referencia.persistencia.core.BaseRepositorio;
import referencia.persistencia.excecoes.RepoException;

public class CategoriaRepositorio extends BaseRepositorio<Categoria> {

    public Categoria buscar(Produto produto) throws RepoException {

        Map<String, Object> filtros = new HashMap<>();

        filtros.put("idProduto", produto.getId());

        return buscar(produto.getId());
    }
}
