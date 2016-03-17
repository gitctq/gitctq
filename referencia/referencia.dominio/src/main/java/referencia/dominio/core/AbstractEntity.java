package referencia.dominio.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Transient;

import org.codehaus.jackson.map.ObjectMapper;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Classe abstrata para tratamento de Entitys.
 * 
 * @author Sibinel
 * @param <T>
 */
@Slf4j
public abstract class AbstractEntity<T extends Serializable> implements InterfaceEntity<T> {

    private static final long serialVersionUID = 1L;

    @Transient
    @Getter
    private List<String> msg;

    /**
     * Construtor.
     */
    public AbstractEntity() {
        
        msg = new ArrayList<String>();
    }

    @Override
    public String msgList() {

        StringBuilder sb = new StringBuilder();
        
        for (String s : msg) {
        
            sb.append(s);
            
            sb.append(";");
        }
        
        return sb.toString();
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasError() {

        return !msg.isEmpty();
    }

    /**
     * Motodo generico para transformar Entity em XML
     * 
     * @return String XML da classe de entrada.
     */
    public String toJson() {

        String str = "entity not formated to xml: " + this.getClass().getCanonicalName();

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

            ObjectMapper mapper = new ObjectMapper();

            Map<String, Object> entityMap = new HashMap<String, Object>();

            for (Field f : getAllFields(this.getClass())) {

                if (f.isAnnotationPresent(Column.class)) {

                    f.setAccessible(true);

                    entityMap.put(f.getName(), f.get(this));
                }
            }

            mapper.writeValue(bos, entityMap);

            str = new String(bos.toByteArray());

        } catch (IOException | IllegalAccessException | IllegalArgumentException | SecurityException ex) {

            str += " " + ex.getMessage();

            log.error(str, ex);
        }

        return str;
    }

    /**
     *
     * @author Joe Walker [joe at getahead dot ltd dot uk]
     */
    /**
     * Return a list of all fields (whatever access status, and on whatever superclass they were defined) that can be
     * found on this class. This is like a union of {@link Class#getDeclaredFields()} which ignores and super-classes,
     * and {@link Class#getFields()} which ignored non-public fields
     *
     * @param clazz
     *            The class to introspect
     * @return The complete list of fields
     */
    public static Field[] getAllFields(Class<?> clazz) {

        List<Class<?>> classes = getAllSuperclasses(clazz);
        
        classes.add(clazz);
        
        return getAllFields(classes);
    }

    /**
     * As {@link #getAllFields(Class)} but acts on a list of {@link Class}s and uses only
     * {@link Class#getDeclaredFields()}.
     *
     * @param classes
     *            The list of classes to reflect on
     * @return The complete list of fields
     */
    private static Field[] getAllFields(List<Class<?>> classes) {

        Set<Field> fields = new HashSet<Field>();
        
        for (Class<?> clazz : classes) {
        
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        }

        return fields.toArray(new Field[fields.size()]);
    }

    /**
     * Return a List of super-classes for the given class.
     *
     * @param clazz
     *            the class to look up
     * @return the List of super-classes in order going up from this one
     */
    public static List<Class<?>> getAllSuperclasses(Class<?> clazz) {

        List<Class<?>> classes = new ArrayList<Class<?>>();

        Class<?> superclass = clazz.getSuperclass();
        
        while (superclass != null) {
        
            classes.add(superclass);
            
            superclass = superclass.getSuperclass();
        }

        return classes;
    }

}
