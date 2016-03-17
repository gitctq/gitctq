package referencia.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import lombok.Getter;
import lombok.Setter;
import referencia.dominio.Categoria;
import referencia.dominio.Produto;
import referencia.dominio.enumeracoes.StatusCategoriaEnum;
import referencia.negocio.excecoes.BCException;
import referencia.negocio.service.ICategoriaSC;

@ManagedBean(name = "categoriaMBean")
@ViewScoped
public class CategoriaMBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private transient ICategoriaSC categoriaSC;

    @Getter
    private List<Categoria> categorias;

    @Getter
    private List<String> categoriasSelecao;

    @Getter
    @Setter
    private String nomeCategoriaSelecionada;

    @Getter
    @Setter
    private Categoria categoriaSelecionada;

    private boolean exibeDetalhes;

    @Getter
    private String feedback;

    @Getter
    private String tipoFeedback;
    
    @Getter
    private String titulo;

    @PostConstruct
    public void loadCategorias() throws BCException {

        categorias = categoriaSC.listarCategoria();

        categoriasSelecao = new ArrayList<>();
        
        for (Categoria categoria : categorias) {

            categoriasSelecao.add(categoria.getDescricao());
        }

        Collections.sort(categoriasSelecao);
    }

    public void onSelecionaCategoria() {

        if (nomeCategoriaSelecionada != null && !"Selecione".equals(nomeCategoriaSelecionada)) {

            for (Categoria categoria : categorias) {

                if (categoria.getDescricao().equals(nomeCategoriaSelecionada)) {

                    categoriaSelecionada = categoria;

                    titulo = "Editar " + nomeCategoriaSelecionada;
                    
                    exibeDetalhes = true;

                    break;
                }
            }
        }
        
        nomeCategoriaSelecionada = null;
    }

    public List<String> autoCompleteCategorias(String textoDigitado) {

        List<String> categoriasAutoComplete = new ArrayList<>();

        for (String categoria : categoriasSelecao) {

            if (categoria.toLowerCase().contains(textoDigitado.toLowerCase())) {

                categoriasAutoComplete.add(categoria);
            }
        }

        return categoriasAutoComplete;
    }

    public void excluir() throws BCException {

        StatusCategoriaEnum status = categoriaSC.excluir(categoriaSelecionada.getId());

        feedback = status.getFeedback();

        tipoFeedback = status.getTipoFeedback();
        
        exibeDetalhes = false;
        
        loadCategorias();
    }

    public void excluir(Categoria categoria) throws BCException {

        StatusCategoriaEnum status = categoriaSC.excluir(categoria.getId());

        feedback = status.getFeedback();

        tipoFeedback = status.getTipoFeedback();
        
        exibeDetalhes = false;
        
        loadCategorias();
    }

    public void salvar() throws BCException {

        StatusCategoriaEnum status = categoriaSC.salvar(categoriaSelecionada);

        feedback = status.getFeedback();

        tipoFeedback = status.getTipoFeedback();
        
        exibeDetalhes = false;
        
        loadCategorias();
    }

    public void aplicarCategoriaSelecionadaPor(Produto produto) throws BCException {

        categoriaSelecionada = categoriaSC.buscar(produto.getCategoria().getId());

        if (categoriaSelecionada != null) {

            nomeCategoriaSelecionada = categoriaSelecionada.getDescricao();
        }
    }

    public void onRowSelect(SelectEvent event) {

        categoriaSelecionada = (Categoria) event.getObject();

        nomeCategoriaSelecionada = categoriaSelecionada.getDescricao();

        titulo = "Editar " + nomeCategoriaSelecionada;

        exibeDetalhes = true;
    }

    public void reset() {

        categoriaSelecionada = null;

        nomeCategoriaSelecionada = "";

        exibeDetalhes = false;
    }

    public boolean isExibeDetalhes() {

        return exibeDetalhes;
    }

    public void ocultaDetalhes() {

        this.exibeDetalhes = false;
    }

    public void formNovaCategoria() {

        reset();

        titulo = "Nova Categoria";

        this.exibeDetalhes = true;

        categoriaSelecionada = new Categoria();
    }
}
