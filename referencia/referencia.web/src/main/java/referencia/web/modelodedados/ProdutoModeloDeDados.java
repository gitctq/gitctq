package referencia.web.modelodedados;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import lombok.extern.slf4j.Slf4j;
import referencia.dominio.Produto;

@Slf4j
public class ProdutoModeloDeDados extends LazyDataModel<Produto> {

    private static final long serialVersionUID = 1L;

    private List<Produto> produtos;

    public ProdutoModeloDeDados(List<Produto> produtos) {

        this.produtos = produtos;
    }

    @Override
    public Produto getRowData(String id) {

        for (Produto produto : produtos) {

            if (produto.getId().equals(id))

                return produto;
        }

        return null;
    }

    @Override
    public Object getRowKey(Produto produto) {

        return produto.getId();
    }

    @Override
    public List<Produto> load(int primeiro, int tamanhoDaPagina, String sortField, SortOrder sortOrder, Map<String, Object> filtros) {

        List<Produto> data = new ArrayList<Produto>();

        for (Produto produto : produtos) {

            data.add(produto);
        }

        int dataSize = data.size();

        this.setRowCount(dataSize);

        if (dataSize > tamanhoDaPagina) {

            try {

                return data.subList(primeiro, primeiro + tamanhoDaPagina);

            } catch (IndexOutOfBoundsException e) {

                log.debug("", e);
                
                return data.subList(primeiro, primeiro + (dataSize % tamanhoDaPagina));
            }

        } else {

            return data;
        }
    }
}
