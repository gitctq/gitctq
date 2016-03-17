package referencia.web;

import static javax.ws.rs.core.HttpHeaders.ACCEPT;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import referencia.dominio.Categoria;
import referencia.dominio.Produto;
import referencia.dominio.enumeracoes.StatusProdutoEnum;
import referencia.negocio.excecoes.BCException;
import referencia.negocio.service.IProdutoSC;
import referencia.web.modelodedados.ProdutoModeloDeDados;

@ManagedBean(name = "produtoMBean")
@ViewScoped
@Slf4j
public class ProdutoMBean extends AbstractManagedBean {

    private static final long serialVersionUID = 1L;

    @ManagedProperty(value = "#{categoriaMBean}")
    @Setter
    private CategoriaMBean categoriaMBean;

    @EJB
    private transient IProdutoSC produtoSC;

    @Getter
    private List<Produto> produtos;

    @Getter
    private List<String> nomesProdutos;

    @Getter
    private Produto produtoSelecionado;

    @Getter
    @Setter
    private String nomeProdutoSelecionado;

    private boolean exibeDetalhes;

    @Getter
    private String feedback;

    @Getter
    private String tipoFeedback;

    @Getter
    private String titulo;

    @Getter
    private LazyDataModel<Produto> lazyModel;

    @PostConstruct
    public void loadProdutos() throws BCException {

        produtos = loadProdutosRest();

        nomesProdutos = new ArrayList<>();

        for (Produto produto : produtos) {

            nomesProdutos.add(produto.getDescricao());
        }

        Collections.sort(nomesProdutos);

        lazyModel = new ProdutoModeloDeDados(produtos);
    }

    public List<Produto> loadProdutosRest() {

        List<Produto> produtosRest = null;

        try {

            URL url = new URL("http://10.200.45.234/referencia.api/api/referencia/v1.0/produtos/");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");

            con.setRequestProperty(ACCEPT, APPLICATION_JSON);

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String output;

            Type type = new TypeToken<List<Produto>>() {
            }.getType();

            while ((output = br.readLine()) != null) {

                Gson gson = new Gson();

                produtosRest = gson.fromJson(URLDecoder.decode(output, "UTF-8"), type);
            }

        } catch (IOException e) {

            log.error("Erro ao buscar os produtos via REST", e);

            produtosRest = new ArrayList<>();
        }

        return produtosRest;
    }

    public void salvar() throws BCException {

        Categoria categoria = categoriaMBean.getCategoriaSelecionada();

        produtoSelecionado.setCategoria(categoria);

        StatusProdutoEnum status = produtoSC.salvar(produtoSelecionado);

        feedback = status.getFeedback();

        tipoFeedback = status.getTipoFeedback();

        loadProdutos();

        exibeDetalhes = false;
    }

    public void excluir() throws BCException {

        StatusProdutoEnum status = produtoSC.excluir(produtoSelecionado.getId());

        feedback = status.getFeedback();

        tipoFeedback = status.getTipoFeedback();

        reset();

        exibeDetalhes = false;

        loadProdutos();
    }

    public void excluir(Produto produto) throws BCException {

        StatusProdutoEnum status = produtoSC.excluir(produto.getId());

        feedback = status.getFeedback();

        tipoFeedback = status.getTipoFeedback();

        reset();

        exibeDetalhes = false;

        loadProdutos();
    }

    public void reset() {

        produtoSelecionado = null;

        nomeProdutoSelecionado = null;

        exibeDetalhes = false;

        categoriaMBean.reset();
    }

    public void onRowSelect(SelectEvent event) throws BCException {

        produtoSelecionado = (Produto) event.getObject();

        nomeProdutoSelecionado = produtoSelecionado.getDescricao();

        categoriaMBean.aplicarCategoriaSelecionadaPor(produtoSelecionado);

        titulo = "Editar " + nomeProdutoSelecionado;

        exibeDetalhes = true;
    }

    public void onSelecionaProduto(SelectEvent event) throws BCException {

        String nomeProduto = (String) event.getObject();

        produtoSelecionado = new Produto();

        for (Produto produto : produtos) {

            if (produto.getDescricao().equals(nomeProduto)) {

                produtoSelecionado = produtoSC.buscarProduto(produto.getId());

                categoriaMBean.aplicarCategoriaSelecionadaPor(produtoSelecionado);

                exibeDetalhes = true;

                titulo = "Editar " + nomeProdutoSelecionado;

                break;
            }
        }

        nomeProdutoSelecionado = null;
    }

    public List<String> autoCompleteProdutos(String textoDigitado) {

        List<String> produtosAutoComplete = new ArrayList<>();

        for (Produto produto : produtos) {

            if (produto.getDescricao().toLowerCase().contains(textoDigitado.toLowerCase())) {

                produtosAutoComplete.add(produto.getDescricao());
            }
        }

        return produtosAutoComplete;
    }

    public boolean isExibeDetalhes() {

        return exibeDetalhes;
    }

    public void ocultaDetalhes() {

        this.exibeDetalhes = false;
    }

    public void formNovoProduto() {

        reset();

        titulo = "Novo Produto";

        produtoSelecionado = new Produto();

        this.exibeDetalhes = true;
    }
}
