/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package referencia.dominio.core;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

/**
 *
 * @author roger
 */
@MappedSuperclass
public abstract class BaseEntity<T extends Serializable> extends AbstractEntity<T> {

    private static final long serialVersionUID = 1L;

}
