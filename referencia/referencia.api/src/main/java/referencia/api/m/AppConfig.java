/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package referencia.api.m;

import java.util.Set;

import javax.ws.rs.core.Application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 140200
 */
@javax.ws.rs.ApplicationPath("api")
public class AppConfig extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppConfig.class);

    @Override
    public Set<Class<?>> getClasses() {

        LOGGER.debug("getClasses", "getClasses");
        Set<Class<?>> resources = new java.util.HashSet<Class<?>>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {

        resources.add(referencia.api.m.rest.Provider.class);
    }

}
