package referencia.api.m.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_ATOM_XML;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.WILDCARD;
import static javax.ws.rs.core.Response.Status.ACCEPTED;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import lombok.extern.slf4j.Slf4j;
import referencia.dominio.Produto;
import referencia.negocio.service.IProdutoSC;

@Path("/referencia")
@Stateless
@Slf4j
public class Provider {

    @EJB
    private IProdutoSC produtoSC;

    /**
     * Lista os produtos cadastrados.
     * 
     * @return a lista dos produtos cadastrados
     */
    @POST
    @GET
    @Path("/v1.0/produtos")
    @Consumes({ WILDCARD })
    @Produces({ APPLICATION_ATOM_XML, APPLICATION_JSON + "; charset=UTF-8" })
    public Response listarProdutos() {

        log.debug("listarProdutos: {}");

        try {

            List<Produto> produtos = produtoSC.listarProduto();

            GenericEntity<List<Produto>> entity = new GenericEntity<List<Produto>>(produtos) {
            };

            return Response.status(ACCEPTED).entity(entity).build();

        } catch (Exception e) {

            log.error("Exception: {}", e);

            return Response.status(BAD_REQUEST).entity("Erro de processamento: " + e.getMessage()).build();
        }
    }

    /**
     * Busca os dados de um produto pelo seu Id
     * 
     * @return o produto cadastrado
     */
    @POST
    @GET
    @Path("/v1.0/produto/{nId}")
    @Consumes({ WILDCARD })
    @Produces({ APPLICATION_ATOM_XML, APPLICATION_JSON + "; charset=UTF-8" })
    public Response buscarProduto(@PathParam(value = "nId") Integer id) {

        log.debug("buscarProduto: {}", id);

        try {

            Produto produto = produtoSC.buscarProduto(id);

            return Response.status(ACCEPTED).entity(produto).build();

        } catch (Exception e) {

            log.error("Exception: {}", e);

            return Response.status(BAD_REQUEST).entity("Erro de processamento: " + e.getMessage()).build();
        }
    }
}
