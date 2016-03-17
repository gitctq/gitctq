package referencia.negocio.service;

import java.util.List;

import referencia.dominio.Categoria;
import referencia.dominio.Produto;
import referencia.dominio.enumeracoes.StatusCategoriaEnum;
import referencia.negocio.excecoes.BCException;

public interface ICategoriaSC {

    List<Categoria> listarCategoria() throws BCException;

    Categoria buscarPor(Produto produto) throws BCException;

    StatusCategoriaEnum salvar(Categoria categoriaSelecionada);

    StatusCategoriaEnum excluir(Integer id);

    Categoria buscar(Integer id);

}
