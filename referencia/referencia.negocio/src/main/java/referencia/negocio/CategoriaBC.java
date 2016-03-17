package referencia.negocio;

import static referencia.dominio.enumeracoes.StatusCategoriaEnum.ERRO;
import static referencia.dominio.enumeracoes.StatusCategoriaEnum.ERRO_EXCLUSAO;
import static referencia.dominio.enumeracoes.StatusCategoriaEnum.EXCLUIDA;
import static referencia.dominio.enumeracoes.StatusCategoriaEnum.SALVA;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import referencia.dominio.Categoria;
import referencia.dominio.Produto;
import referencia.dominio.enumeracoes.StatusCategoriaEnum;
import referencia.negocio.excecoes.BCException;
import referencia.negocio.service.ICategoriaSC;
import referencia.persistencia.CategoriaRepositorio;
import referencia.persistencia.excecoes.RepoException;

@Stateless
@Slf4j
public class CategoriaBC implements ICategoriaSC {

    @Inject
    private CategoriaRepositorio repo;

    @Override
    public List<Categoria> listarCategoria() throws BCException {

        List<Categoria> categorias;

        try {

            categorias = repo.listarTodos();

            if (categorias == null) {

                categorias = new ArrayList<>();
            }

        } catch (RepoException e) {

            throw new BCException(e);
        }

        return categorias;
    }

    @Override
    public Categoria buscarPor(Produto produto) throws BCException {

        try {

            return repo.buscar(produto);

        } catch (RepoException e) {

            log.error("Erro ao buscar categoria por produto", e);

            return new Categoria();
        }
    }

    @Override
    public StatusCategoriaEnum salvar(Categoria categoria) {

        StatusCategoriaEnum status = categoria.isValida();

        try {

            if (categoria.getId() != null) {

                repo.atualizar(categoria);

            } else {

                repo.salvar(categoria);
            }

            status = SALVA;

        } catch (RepoException e) {

            status = ERRO;

            log.error(status.getFeedback(), e);
        }

        return status;
    }

    @Override
    public StatusCategoriaEnum excluir(Integer id) {

        StatusCategoriaEnum status = EXCLUIDA;

        try {

            repo.excluir(id);

        } catch (RepoException e) {

            status = ERRO_EXCLUSAO;

            log.error(status.getFeedback(), e);
        }

        return status;
    }

    @Override
    public Categoria buscar(Integer id) {

        try {

            return repo.buscar(id);

        } catch (RepoException e) {

            log.error("Erro ao buscar categoria", e);
            
            return null;
        }
    }

}
