package referencia.negocio;

import static referencia.dominio.enumeracoes.StatusProdutoEnum.ERRO;
import static referencia.dominio.enumeracoes.StatusProdutoEnum.ERRO_EXCLUSAO;
import static referencia.dominio.enumeracoes.StatusProdutoEnum.EXCLUIDO;
import static referencia.dominio.enumeracoes.StatusProdutoEnum.SALVO;
import static referencia.dominio.enumeracoes.StatusProdutoEnum.VALIDO;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import referencia.dominio.Categoria;
import referencia.dominio.Produto;
import referencia.dominio.enumeracoes.StatusProdutoEnum;
import referencia.negocio.excecoes.BCException;
import referencia.negocio.service.IProdutoSC;
import referencia.persistencia.CategoriaRepositorio;
import referencia.persistencia.ProdutoRepositorio;
import referencia.persistencia.excecoes.RepoException;

@Stateless
@Slf4j
public class ProdutoBC implements IProdutoSC {

    @Inject
    private ProdutoRepositorio repo;

    @Inject
    private CategoriaRepositorio categoriaSC;

    @Override
    public List<Produto> listarProduto() throws BCException {

        try {

            return repo.listarTodos();

        } catch (RepoException e) {

            throw new BCException(e);
        }
    }

    @Override
    public StatusProdutoEnum salvar(Produto produto) {

        StatusProdutoEnum status = produto.isValido();

        if (VALIDO.equals(status)) {

            try {

                aplicarCategoria(produto);

                if (produto.getId() != null) {

                    repo.atualizar(produto);

                } else {

                    repo.salvar(produto);
                }

                status = SALVO;

            } catch (RepoException e) {

                status = ERRO;

                log.error(status.getFeedback() + ": " + produto, e);
            }
        }

        return status;
    }

    private void aplicarCategoria(Produto produto) throws RepoException {

        Categoria categoria = categoriaSC.buscar(produto.getCategoria().getId());

        produto.setCategoria(categoria);
    }

    @Override
    public StatusProdutoEnum excluir(Integer id) {

        StatusProdutoEnum status = EXCLUIDO;

        try {

            repo.excluir(id);

        } catch (RepoException e) {

            status = ERRO_EXCLUSAO;

            log.error(status + "de id: " + id, e);
        }

        return status;
    }

    @Override
    public Produto buscarProduto(Integer id) throws BCException {

        try {

            return repo.buscar(id);

        } catch (RepoException e) {

            throw new BCException(e);
        }
    }

}
