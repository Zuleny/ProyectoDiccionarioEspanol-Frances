
package Negocio;

import java.util.List;

/**
 * @param <T>
 */
public interface IArbolBusqueda<T extends Comparable<T>> {

    public boolean insertar(T dato);

    public boolean eliminar(T dato);

    public boolean buscar(T dato);

    public List<T> recorridoEnPreOrden();

    public List<T> recorridoEnInOrden();

    public List<T> recorridoEnPostOrden();

    public List<T> recorridoPorNiveles();

    public int size();

    public int altura();

    public void vaciar();

    public boolean esArbolVacio();

    public int nivel();

    //Nuevo
     public T getDatoEnNodo(T datoABuscar);
}
