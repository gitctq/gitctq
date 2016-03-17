/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package referencia.dominio.core;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Roger
 */
public interface InterfaceEntity<T extends Serializable> extends Serializable {

    /**
     * Método geral para leitura de IDs.
     * 
     * @return Id Entity
     */
    T getId();

    /**
     * Método geral para escrita de IDs.
     * 
     * @param pId
     *            Id da Entity
     */
    void setId(T pId);

    /**
     * Retorna lista de mensagens de tratamento.
     *
     * @return List<String> Lista de mensagens.
     */
    List<String> getMsg();

    String msgList();

    /**
     * Verifica se a entity está com problemas de processamento.
     *
     * @return boolean Verifica se existe mensagens de erro associadas ao Entity
     */
    boolean hasError();
}
