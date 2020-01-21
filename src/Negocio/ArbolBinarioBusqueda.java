
package Negocio;

import Negocio.Excepcion.ExcepcionDatoNoExiste;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * @param <T>
 */
public class ArbolBinarioBusqueda<T extends Comparable<T>> implements IArbolBusqueda<T> {

    protected NodoBinario<T> raiz;

    public ArbolBinarioBusqueda() {
        this.raiz = (NodoBinario<T>) NodoBinario.nodoVacio();
    }

    public ArbolBinarioBusqueda(List<T> recorridoPreOrden, List<T> recorridoInOrden) {
        this.raiz = reconstruccionConPreOrden(recorridoPreOrden, recorridoInOrden);
    }

    private NodoBinario<T> reconstruccionConPreOrden(List<T> recorridoPreOrden, List<T> recorridoInOrden) {
        NodoBinario<T> nodoActual = new NodoBinario<>();
        if (recorridoPreOrden.isEmpty() && recorridoInOrden.isEmpty()) {
            return (NodoBinario< T>) NodoBinario.nodoVacio();
        }
        T datoDelNodoActual = recorridoPreOrden.get(0);
        nodoActual.setDato(datoDelNodoActual);
        int posicionDelNodoActual = recorridoInOrden.indexOf(datoDelNodoActual);

        List<T> subListaInOrdenPorIzq = recorridoInOrden.subList(0, posicionDelNodoActual);
        List<T> subListaPreOrdenPorIzq = recorridoPreOrden.subList(1, posicionDelNodoActual + 1);
        NodoBinario<T> hijoIzquierdo = reconstruccionConPreOrden(subListaPreOrdenPorIzq, subListaInOrdenPorIzq);

        List<T> subListaInOrdenPorDer = recorridoInOrden.subList(posicionDelNodoActual + 1, recorridoInOrden.size());
        List<T> subListaPreOrdenPorDer = recorridoPreOrden.subList(posicionDelNodoActual + 1, recorridoPreOrden.size());
        NodoBinario<T> hijoDerecho = reconstruccionConPreOrden(subListaPreOrdenPorDer, subListaInOrdenPorDer);

        nodoActual.setHijoIzquierdo(hijoIzquierdo);
        nodoActual.setHijoDerecho(hijoDerecho);

        return nodoActual;
    }

    @Override
    public boolean insertar(T datoAInsertar) {
        if (esArbolVacio()) {
            raiz = new NodoBinario<>(datoAInsertar); // nuevo nodo 
            return true;
        }
        NodoBinario<T> nodoAnterior = (NodoBinario<T>) NodoBinario.nodoVacio();
        NodoBinario<T> nodoActual = this.raiz;

        while (!NodoBinario.esNodoVacio(nodoActual)) {
            T datoNodoActual = nodoActual.getDato();
            if (datoAInsertar.compareTo(datoNodoActual) == 0) {
                return false;
            }
            nodoAnterior = nodoActual;
            if (datoAInsertar.compareTo(datoNodoActual) < 0) {
                nodoActual = nodoActual.getHijoIzquierdo();
            } else {
                nodoActual = nodoActual.getHijoDerecho();
            }
        }
        NodoBinario<T> nuevoNodo = new NodoBinario<>(datoAInsertar);
        if (datoAInsertar.compareTo(nodoAnterior.getDato()) < 0) {
            nodoAnterior.setHijoIzquierdo(nuevoNodo);
        } else {
            nodoAnterior.setHijoDerecho(nuevoNodo);
        }
        return true;
    }

    @Override
    public boolean eliminar(T dato) {
        try {
            this.raiz = eliminar(raiz, dato);
            return true;
        } catch (ExcepcionDatoNoExiste ex) {
            return false;
        }

    }

    private NodoBinario<T> eliminar(NodoBinario<T> nodoActual, T datoAEliminar) throws ExcepcionDatoNoExiste {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            throw new ExcepcionDatoNoExiste();
        }
        T datoDelNodoActual = nodoActual.getDato();
        if (datoAEliminar.compareTo(datoDelNodoActual) < 0) {
            NodoBinario<T> supuestoHijoIzquierdo = eliminar(nodoActual.getHijoIzquierdo(), datoAEliminar);
            nodoActual.setHijoIzquierdo(supuestoHijoIzquierdo);
            return nodoActual;
        }
        if (datoAEliminar.compareTo(datoDelNodoActual) > 0) {
            NodoBinario<T> supuestoHijoDerecho = eliminar(nodoActual.getHijoDerecho(), datoAEliminar);
            nodoActual.setHijoDerecho(supuestoHijoDerecho);
            return nodoActual;
        }//Ya lo Encontre 

        if (nodoActual.esNodoHoja()) {    //Caso 1
            return (NodoBinario< T>) NodoBinario.nodoVacio();
        }
        //Caso 2   
        if (nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho()) {
            return nodoActual.getHijoDerecho();
        }
        if (!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho()) {
            return nodoActual.getHijoIzquierdo();
        }
        //Caso 3
        T sucesor = getSucesor(nodoActual.getHijoDerecho());
        nodoActual = eliminar(nodoActual, sucesor);
        nodoActual.setDato(sucesor);
        return nodoActual;
    }

    protected T getSucesor(NodoBinario<T> nodoActual) throws ExcepcionDatoNoExiste {
        while (!NodoBinario.esNodoVacio(nodoActual.getHijoIzquierdo())) {
            nodoActual = nodoActual.getHijoIzquierdo();
        }
        return nodoActual.getDato();
    }
    /************/
    @Override
    public T getDatoEnNodo(T datoABuscar){
        if (esArbolVacio()) {
            return (T)NodoBinario.nodoVacio();
        }
        NodoBinario<T> nodoAnterior = (NodoBinario<T>) NodoBinario.nodoVacio();
        NodoBinario<T> nodoActual = this.raiz;

        while (!NodoBinario.esNodoVacio(nodoActual)) {
            T datoNodoActual = nodoActual.getDato();
            if (datoABuscar.compareTo(datoNodoActual) == 0) {
                return nodoActual.getDato();
            }
            nodoAnterior = nodoActual;
            if (datoABuscar.compareTo(datoNodoActual) < 0) {
                nodoActual = nodoActual.getHijoIzquierdo();
            } else {
                nodoActual = nodoActual.getHijoDerecho();
            }
        }
        return (T)nodoActual;
    }
    
    /************/
    @Override
    public boolean buscar(T datoABuscar) {
        if (esArbolVacio()) {
            return false;
        }
        NodoBinario<T> nodoAnterior = (NodoBinario<T>) NodoBinario.nodoVacio();
        NodoBinario<T> nodoActual = this.raiz;

        while (!NodoBinario.esNodoVacio(nodoActual)) {
            T datoNodoActual = nodoActual.getDato();
            if (datoABuscar.compareTo(datoNodoActual) == 0) {
                return true;
            }
            nodoAnterior = nodoActual;
            if (datoABuscar.compareTo(datoNodoActual) < 0) {
                nodoActual = nodoActual.getHijoIzquierdo();
            } else {
                nodoActual = nodoActual.getHijoDerecho();
            }
        }
        return false;
    }

    @Override
    public List<T> recorridoEnPreOrden() {
        List<T> recorrido = new ArrayList<>();
        recorridoEnPreOrden(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnPreOrden(NodoBinario<T> nodoActual, List<T> recorrido) {
        if (!NodoBinario.esNodoVacio(nodoActual)) {
            recorrido.add(nodoActual.getDato());
            recorridoEnPreOrden(nodoActual.getHijoIzquierdo(), recorrido);
            recorridoEnPreOrden(nodoActual.getHijoDerecho(), recorrido);
        }
    }

    @Override
    public List<T> recorridoEnInOrden() {
        List<T> recorrido = new ArrayList<>();
        recorridoEnInOrden(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnInOrden(NodoBinario<T> nodoActual, List<T> recorrido) {
        if (!NodoBinario.esNodoVacio(nodoActual)) {
            recorridoEnInOrden(nodoActual.getHijoIzquierdo(), recorrido);
            recorrido.add(nodoActual.getDato());
            recorridoEnInOrden(nodoActual.getHijoDerecho(), recorrido);
        }
    }

    @Override
    public List<T> recorridoEnPostOrden() {
        List<T> recorrido = new ArrayList<>();
        recorridoEnPostOrden(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnPostOrden(NodoBinario<T> nodoActual, List<T> recorrido) {
        if (!NodoBinario.esNodoVacio(nodoActual)) {
            recorridoEnPostOrden(nodoActual.getHijoIzquierdo(), recorrido);
            recorridoEnPostOrden(nodoActual.getHijoDerecho(), recorrido);
            recorrido.add(nodoActual.getDato());
        }
    }

    @Override
    public List<T> recorridoPorNiveles() {
        List<T> listaDeRecorrido = new ArrayList<>();
        if (esArbolVacio()) {
            return listaDeRecorrido;
        }
        Queue<NodoBinario> colaDeNodos = new LinkedList();
        colaDeNodos.offer(this.raiz);
        NodoBinario<T> nodoActual;
        while (!colaDeNodos.isEmpty()) {
            nodoActual = colaDeNodos.poll();
            if (!nodoActual.esVacioHijoIzquierdo()) {
                colaDeNodos.offer(nodoActual.getHijoIzquierdo());
            }
            if (!nodoActual.esVacioHijoDerecho()) {
                colaDeNodos.offer(nodoActual.getHijoDerecho());
            }
            listaDeRecorrido.add(nodoActual.getDato());
        }
        return listaDeRecorrido;
    }

    public List<T> recorridoEnPreOrdenI() {
        List<T> recorrido = new ArrayList<>();
        if (esArbolVacio()) {
            return recorrido;
        }
        Stack<NodoBinario<T>> pila = new Stack<>();
        pila.push(this.raiz);
        NodoBinario<T> nodoActual;
        while (!pila.isEmpty()) {
            nodoActual = pila.pop();
            recorrido.add(nodoActual.getDato());
            if (!nodoActual.esVacioHijoDerecho()) {
                pila.push(nodoActual.getHijoDerecho());
            }
            if (!nodoActual.esVacioHijoIzquierdo()) {
                pila.push(nodoActual.getHijoIzquierdo());
            }
        }
        return recorrido;
    }

    public List<T> recorridoEnInOrdenI() {
        List<T> recorrido = new ArrayList<>();
        if (esArbolVacio()) {
            return recorrido;
        }
        Stack<NodoBinario<T>> pila = new Stack<>();
        pila.push(this.raiz);
        apilarHijosIzquierdos(this.raiz, pila);
        NodoBinario<T> nodoActual;
        while (!pila.isEmpty()) {
            nodoActual = pila.pop();
            recorrido.add(nodoActual.getDato());
            if (!nodoActual.esVacioHijoDerecho()) {
                pila.push(nodoActual.getHijoDerecho());
                apilarHijosIzquierdos(nodoActual.getHijoDerecho(), pila);
            }
        }
        return recorrido;
    }

    public List<T> recorridoEnPostOrdenI() {
        List<T> recorrido = new ArrayList<>();
        if (esArbolVacio()) {
            return recorrido;
        }
        NodoBinario<T> nodoActual;
        Stack<NodoBinario<T>> pilaDeNodos = new Stack<>();
        pilaDeNodos.push(this.raiz);
        apilarHijosIzquierdos(this.raiz, pilaDeNodos);
        while ( ! pilaDeNodos.isEmpty()) {
            nodoActual = pilaDeNodos.peek();
            if (nodoActual.esNodoHoja()|| nodoActual.esVacioHijoDerecho()) {
                recorrido.add(nodoActual.getDato());
                pilaDeNodos.pop();
            }else if (recorrido.get(recorrido.size() - 1).compareTo(nodoActual.getHijoDerecho().getDato()) == 0 ) { 
                recorrido.add(nodoActual.getDato());
                pilaDeNodos.pop();
            }else if ( ! nodoActual.esVacioHijoDerecho()) {
                pilaDeNodos.push(nodoActual.getHijoDerecho());
                apilarHijosIzquierdos(nodoActual.getHijoDerecho(), pilaDeNodos);
            }
        }
        return recorrido;
    }

    private void apilarHijosIzquierdos(NodoBinario<T> nodoActual, Stack<NodoBinario<T>> pila) {
        while (!NodoBinario.esNodoVacio(nodoActual.getHijoIzquierdo())) {
            pila.push(nodoActual.getHijoIzquierdo());
            nodoActual = nodoActual.getHijoIzquierdo();
        }
    }

    @Override
    public int size() {
        if (this.esArbolVacio()) {
            return 0;
        }
        Queue<NodoBinario<T>> cola = new LinkedList<>();
        cola.offer(raiz);
        int cantidadDeNodos = 0;
        while (!cola.isEmpty()) {
            NodoBinario<T> nodoActual = cola.poll();
            cantidadDeNodos++;
            if (!nodoActual.esVacioHijoIzquierdo()) {
                cola.offer(nodoActual.getHijoIzquierdo());
            }
            if (!nodoActual.esVacioHijoDerecho()) {
                cola.offer(nodoActual.getHijoDerecho());
            }
        }
        return cantidadDeNodos;
    }


    @Override
    public int altura() {
        return altura(this.raiz);
    }

    protected int altura(NodoBinario<T> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        int alturaPorIzquierda = altura(nodoActual.getHijoIzquierdo());
        int alturaPorDerecha = altura(nodoActual.getHijoDerecho());
        return alturaPorIzquierda > alturaPorDerecha ? alturaPorIzquierda + 1 : alturaPorDerecha + 1;
    }
    

    @Override
    public void vaciar() {
        this.raiz = (NodoBinario< T>) NodoBinario.nodoVacio();
    }

    @Override
    public boolean esArbolVacio() {
        return NodoBinario.nodoVacio() == raiz;
    }

    @Override
    public int nivel() {
        return nivel(this.raiz);
    }

    private int nivel(NodoBinario<T> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return -1;
        }
        int nivelPorIzquierda = nivel(nodoActual.getHijoIzquierdo());
        int nivelPorDerecha = nivel(nodoActual.getHijoDerecho());
        return nivelPorIzquierda > nivelPorDerecha ? nivelPorIzquierda + 1 : nivelPorDerecha + 1;
    }

}
