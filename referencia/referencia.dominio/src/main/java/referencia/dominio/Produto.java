package referencia.dominio;

import static java.math.BigDecimal.ZERO;
import static javax.persistence.GenerationType.IDENTITY;
import static referencia.dominio.enumeracoes.StatusProdutoEnum.ESTOQUE_NEGATIVO;
import static referencia.dominio.enumeracoes.StatusProdutoEnum.INVALIDO;
import static referencia.dominio.enumeracoes.StatusProdutoEnum.VALIDO;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import referencia.dominio.core.BaseEntity;
import referencia.dominio.enumeracoes.StatusProdutoEnum;

@Entity
@Table(name = "Product")
@EqualsAndHashCode(callSuper = true, of = "id")
@ToString
@NamedQuery(name = "Produto.listar", query = "SELECT produto FROM Produto produto")
@XmlRootElement(name = "produto")
public class Produto extends BaseEntity<Integer> {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Column(name = "Price", columnDefinition = "decimal")
    @Getter
    @Setter
    private BigDecimal preco;

    @Getter
    @Setter
    @Column(name = "Stock")
    private int estoque;

    @Getter
    @Setter
    @Column(name = "Name", columnDefinition = "nvarchar")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "CategoryId")
    @Getter
    @Setter
    private Categoria categoria;

    public StatusProdutoEnum isValido() {

        if (StringUtils.isEmpty(descricao) || preco == null) {

            return INVALIDO;
        }

        if (estoque < 0) {

            return ESTOQUE_NEGATIVO;
        }

        if (preco.compareTo(ZERO) < 0) {

            return StatusProdutoEnum.PRECO_NEGATIVO;
        }

        return VALIDO;
    }
}
