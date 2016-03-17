package referencia.web;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;

@ManagedBean(name = "referenciaMBean")
public class ReferenciaMBean implements Serializable {

    private static final long serialVersionUID = 1L;

    public String callPagProdutos() {

        return "/pages/produtos";
    }

    public String callPagCategorias() {

        return "/pages/categorias";
    }

    public String callPagContato() {

        return "/public/contato";
    }

    public String callPagSobre() {

        return "/public/sobre";
    }

    public String callPagDetalheProjeto() {

        return "/pages/detalheProjeto";
    }

    public String callPagHome() {

        return "/pages/home";
    }
}
