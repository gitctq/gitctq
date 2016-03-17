package referencia.negocio.service;

import java.util.List;

import referencia.dominio.Produto;
import referencia.dominio.enumeracoes.StatusProdutoEnum;
import referencia.negocio.excecoes.BCException;

public interface IProdutoSC {

    List<Produto> listarProduto() throws BCException;

    StatusProdutoEnum salvar(Produto produtoSelecionado);

    StatusProdutoEnum excluir(Integer id);

    Produto buscarProduto(Integer id) throws BCException;

}
