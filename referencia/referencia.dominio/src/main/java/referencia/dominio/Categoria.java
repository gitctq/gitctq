package referencia.dominio;

import static javax.persistence.GenerationType.IDENTITY;
import static referencia.dominio.enumeracoes.StatusCategoriaEnum.INVALIDA;
import static referencia.dominio.enumeracoes.StatusCategoriaEnum.VALIDA;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import referencia.dominio.core.BaseEntity;
import referencia.dominio.enumeracoes.StatusCategoriaEnum;

@Entity
@Table(name = "Category")
@ToString
@EqualsAndHashCode(callSuper = true, of = "id")
@NamedQueries({ @NamedQuery(name = "Categoria.listar", query = "SELECT categoria FROM Categoria categoria"), 
    @NamedQuery(name = "Categoria.buscarPorProduto", query = "SELECT categoria FROM Categoria categoria WHERE categoria.id = (SELECT categoria.id FROM Produto produto WHERE produto.id = :id))") })
public class Categoria extends BaseEntity<Integer> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = IDENTITY)
    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    @Column(name = "Description", columnDefinition = "nvarchar")
    private String descricao;

    public StatusCategoriaEnum isValida() {

        if (StringUtils.isEmpty(descricao)) {

            return INVALIDA;
        }

        return VALIDA;
    }
}
