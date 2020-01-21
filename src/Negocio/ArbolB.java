package Negocio;

import Negocio.Excepcion.ExcepcionOrdenArbolInvalido;
import java.util.Stack;

/**

 * @param <T>
 */
public class ArbolB<T extends Comparable<T>> extends ArbolMViasBusqueda<T> {

    private final int nroMaximoDeDatos;
    private final int nroMinimoDeDatos;
    private final int nroMinimoDeHijos;

    public ArbolB() {
        super();    //LLamando de manera explicita al constructor Padre ArbolMViasBusqueda
        this.nroMaximoDeDatos = 2;
        this.nroMinimoDeDatos = 1;
        this.nroMinimoDeHijos = 2;
    }

    public ArbolB(int orden) throws ExcepcionOrdenArbolInvalido {
        super(orden);
        this.nroMaximoDeDatos = this.orden - 1;
        this.nroMinimoDeDatos = this.nroMaximoDeDatos / 2;
        this.nroMinimoDeHijos = nroMinimoDeDatos + 1;
    }

    @Override
    public boolean insertar(T datoAInsetar) {
        if (this.esArbolVacio()) {
            this.raiz = new NodoMVias<>(orden + 1, datoAInsetar);
            return true;
        }
        NodoMVias<T> nodoActual = this.raiz;
        Stack<NodoMVias<T>> pilaDePadres = new Stack<>();
        while (!NodoMVias.esNodoVacio(nodoActual)) {
            if (this.datoEstaEnNodo(nodoActual, datoAInsetar)) {
                return false;
            }
            if (nodoActual.esNodoHoja()) {
                setDatoOrdenadoEnNodo(nodoActual, datoAInsetar);
                if (nodoActual.cantidadDeDatosNoVacios() > this.nroMaximoDeDatos) {
                    dividir(nodoActual, pilaDePadres);
                }
                nodoActual = NodoMVias.nodoVacio();
            } else {//el nodoActual no es hoja
                int posicionPorDondeBajar = this.getPosicionDeHijo(nodoActual, datoAInsetar);
                pilaDePadres.push(nodoActual);
                nodoActual = nodoActual.getHijo(posicionPorDondeBajar);
            }
        }
        return true;
    }
    // 
    private void dividir(NodoMVias<T> nodoActual, Stack<NodoMVias<T>> pilaDePadres) {
        if (pilaDePadres.isEmpty()) {  // Caso: el nodo a dividir es la raiz
            T nuevoDato = nodoActual.getDato(nroMinimoDeDatos);
            NodoMVias<T> nuevaRaiz = new NodoMVias<>(orden + 1, nuevoDato);
            NodoMVias<T> nodoIzquierda = new NodoMVias<>(orden + 1);
            NodoMVias<T> nodoDerecha = new NodoMVias<>(orden + 1);
            dividirUnNodoADos(nodoActual, nodoIzquierda, nodoDerecha);
            nuevaRaiz.setHijo(0, nodoIzquierda);
            nuevaRaiz.setHijo(1, nodoDerecha);
            this.raiz = nuevaRaiz;
        } else {  //Caso: el nodo a dividir no es la raiz
            NodoMVias<T> nodoPadre = pilaDePadres.pop();
            int posicionDeDatoASubir = nroMinimoDeDatos;
            int posicionDelDatoQueSubio = setDatoOrdenadoEnNodo(nodoPadre, nodoActual.getDato(posicionDeDatoASubir), orden + 1);
            NodoMVias<T> nodoIzquierda = new NodoMVias<>(orden + 1);
            NodoMVias<T> nodoDerecha = new NodoMVias<>(orden + 1);
            dividirUnNodoADos(nodoActual, nodoIzquierda, nodoDerecha);
            nodoPadre.setHijo(posicionDelDatoQueSubio, nodoIzquierda);
            nodoPadre.setHijo(posicionDelDatoQueSubio + 1, nodoDerecha);
            // Verifica que si despues de dividir el nodoActual el padre tambien se debe dividir 
            if (nodoPadre.cantidadDeDatosNoVacios() > nroMaximoDeDatos) {
                dividir(nodoPadre, pilaDePadres);
            }
        }
    }

    private void dividirUnNodoADos(NodoMVias<T> nodoActual, NodoMVias<T> nodoIzquierda, NodoMVias<T> nodoDerecha) {
        for (int i = 0; i < nroMinimoDeDatos ; i++) {
            nodoIzquierda.setDato(i, nodoActual.getDato(i));
            nodoIzquierda.setHijo(i, nodoActual.getHijo(i));
        }
        nodoIzquierda.setHijo(nroMinimoDeDatos, nodoActual.getHijo(nroMinimoDeDatos));
        int j = 0;

        for (int i = nroMinimoDeDatos+1; i < orden; i++) {
            nodoDerecha.setDato(j, nodoActual.getDato(i));
            nodoDerecha.setHijo(j, nodoActual.getHijo(i));
            j++;
        }
        nodoDerecha.setHijo(j, nodoActual.getHijo(orden));
    }
    
    @Override
    public boolean eliminar(T datoAEliminar) {
        Stack<NodoMVias<T>> pilaDeAncestros = new Stack<>();
        NodoMVias<T> nodoActual = buscarNodoDelDato(datoAEliminar, pilaDeAncestros);
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return false;
        }
        int cantidadDeHijosDelNodoActual = nodoActual.cantidadDeDatosNoVacios() + 1;
        int posicionDeDatoAEliminar = getPosicionDeHijo(nodoActual, datoAEliminar) ;
        if (nodoActual.esNodoHoja()) {
            desplazarDatoEnHoja(nodoActual, posicionDeDatoAEliminar);
            //pregunta si rompio el MinDatos
            if (nodoActual.cantidadDeDatosNoVacios() < this.nroMinimoDeDatos) {
                if (pilaDeAncestros.isEmpty()) {    //si el nodoActual es la raiz
                    if (nodoActual.cantidadDeDatosNoVacios() == 0) {
                        this.vaciar();
                    }
                } else {
                    prestarseOFucionarse(nodoActual, pilaDeAncestros);
                }
            }
        } else {  //no es hoja
            pilaDeAncestros.push(nodoActual);
            NodoMVias<T> nodoDelPredecesor = buscarNodoDelPredecesor(pilaDeAncestros,nodoActual.getHijo(posicionDeDatoAEliminar));
            T datoDelPredecesor = nodoDelPredecesor.getDato(nodoDelPredecesor.cantidadDeDatosNoVacios() - 1);
            nodoDelPredecesor.setDato(posicionDeDatoAEliminar, datoDelPredecesor);
            if (nodoDelPredecesor.cantidadDeDatosNoVacios() < nroMinimoDeDatos) {
                prestarseOFucionarse(nodoDelPredecesor, pilaDeAncestros);
            }
        }
        return true;
    }

    private NodoMVias<T> buscarNodoDelDato(T datoAEliminar, Stack<NodoMVias<T>> pilaDeAncestros) {
        NodoMVias<T> nodoActual=this.raiz;
        while (!datoEstaEnNodo(nodoActual, datoAEliminar)) {            
            pilaDeAncestros.push(nodoActual);
            int posicionDelHijoABajar=getPosicionDeHijo(nodoActual, datoAEliminar);
            nodoActual=nodoActual.getHijo(posicionDelHijoABajar);
            if (NodoMVias.esNodoVacio(nodoActual)) {
                return NodoMVias.nodoVacio();
            }
        }
        return nodoActual;
    }

    private void desplazarDatoEnHoja(NodoMVias<T> nodoActual, int posicionDeDatoAEliminar) {
        for (int posicion = posicionDeDatoAEliminar; posicion < nodoActual.cantidadDeDatosNoVacios(); posicion++) {
            T posicionDelDatoAdelante = nodoActual.getDato(posicion+1);
            nodoActual.setDato(posicion, posicionDelDatoAdelante);           
        }
    }

    private void prestarseOFucionarse(NodoMVias<T> nodoActual, Stack<NodoMVias<T>> pilaDeAncestros) {
        while (!pilaDeAncestros.isEmpty()) {            
            NodoMVias<T> nodoPadre=pilaDeAncestros.pop();
            if (nodoActual.cantidadDeDatosNoVacios()< this.nroMinimoDeDatos) {
                T datoDeReferencia=nodoActual.getDato(0);
                int posicionDelNodoActualEnNodoPadre=obtenerPosicionDelNodo(nodoActual,nodoPadre);
                if (puedePrestarse(nodoPadre,posicionDelNodoActualEnNodoPadre+1)) { //Si puedo prestarme a la derecha
                    //Entra si se pudo prestar
                    //saco el dato a bajar del nodo padre
                    T datoABajar=nodoPadre.getDato(posicionDelNodoActualEnNodoPadre);
                    //inserto dato ordenado con el dato a bajar
                    setDatoOrdenadoEnNodo(nodoActual, datoABajar, nodoActual.cantidadDeDatosNoVacios());
                    //guardo al hermano derecho
                    NodoMVias<T> hermanoDerecho=nodoPadre.getHijo(posicionDelNodoActualEnNodoPadre+1);
                    //saco al sucesor del nodo a bajar(hermano derecho la posicion(0))
                    T datoSucesor=hermanoDerecho.getDato(0);
                    //coloco el dato sucesor en lugar del nodo padre
                    nodoPadre.setDato(posicionDelNodoActualEnNodoPadre, datoSucesor);
                    //elimino del hermano derecho el dato sucesor
                    NodoMVias<T> hijoEspecial=hermanoDerecho.getHijo(0);
                    nodoActual.setHijo(nodoActual.cantidadDeDatosNoVacios(), hijoEspecial);
                    //desplaza para atras de un a posicion del hermano derecho
                    for (int i = 0; i < hermanoDerecho.cantidadDeDatosNoVacios(); i++) {
                        T datoAux=hermanoDerecho.getDato(i+1);
                        NodoMVias<T> nodoAux=hermanoDerecho.getHijo(i+1);
                        hermanoDerecho.setDato(i, datoAux);
                        hermanoDerecho.setHijo(i, nodoAux);
                    }
                    hermanoDerecho.setHijo(hermanoDerecho.cantidadDeDatosNoVacios()-1, hermanoDerecho.getHijo(hermanoDerecho.cantidadDeDatosNoVacios()));
                    
                }else if (puedePrestarse(nodoPadre, posicionDelNodoActualEnNodoPadre-1)) {  //Si puedo prestarme a la izquierda
                    //Entra si se pudo prestar
                    //saco el dato a bajar del nodo padre-1
                    T datoABajar=nodoPadre.getDato(posicionDelNodoActualEnNodoPadre-1);
                    //inserto dato ordenado con el dato a bajar
                    setDatoOrdenadoEnNodo(nodoActual, datoABajar, nodoActual.cantidadDeDatosNoVacios());
                    //guardo al hermano izquierdo
                    NodoMVias<T> hermanoIzquierdo=nodoPadre.getHijo(posicionDelNodoActualEnNodoPadre-1);
                    //saco el predesesor del hermano(el ultimo dato)
                    T datoPredecesor=hermanoIzquierdo.getDato(hermanoIzquierdo.cantidadDeDatosNoVacios()-1);
                    //coloco el dato sucesor en lugar del nodo padre
                    nodoPadre.setDato(posicionDelNodoActualEnNodoPadre-1, datoPredecesor);
                    //elimino del hermano derecho el dato predecesor
                    NodoMVias<T> hijoEspecial=hermanoIzquierdo.getHijo(hermanoIzquierdo.cantidadDeDatosNoVacios()-1);
                    nodoActual.setHijo(0, hijoEspecial);
                    //desplaza para atras de un a posicion del hermano derecho
                    hermanoIzquierdo.setDatoVacio(hermanoIzquierdo.cantidadDeDatosNoVacios()-1);
                    hermanoIzquierdo.setHijoVacio(hermanoIzquierdo.cantidadDeDatosNoVacios());
                }else if (!NodoMVias.esNodoVacio(nodoPadre.getHijo(posicionDelNodoActualEnNodoPadre+1))) {   ////fusionar
                    //me fusiono con la derecha
                    T datoABajar=nodoPadre.getDato(posicionDelNodoActualEnNodoPadre);
                    NodoMVias<T> nodoHermano=nodoPadre.getHijo(posicionDelNodoActualEnNodoPadre+1);
                    nodoActual.setDato(nodoActual.cantidadDeDatosNoVacios(), datoABajar);
                    int indiceInsertado=nodoActual.cantidadDeDatosNoVacios();
                    for (int posicionDelNodoHermano = 0; posicionDelNodoHermano < nodoHermano.cantidadDeDatosNoVacios(); 
                            posicionDelNodoHermano++) {
                        T datoAPasar=nodoHermano.getDato(posicionDelNodoHermano);
                        NodoMVias<T> nodoAPasar=nodoHermano.getHijo(posicionDelNodoHermano);
                        nodoActual.setDato(indiceInsertado, datoAPasar);
                        nodoActual.setHijo(indiceInsertado, nodoAPasar);
                        indiceInsertado++;
                    }
                    NodoMVias<T> nodoAPasar=nodoHermano.getHijo(nodoHermano.cantidadDeDatosNoVacios());
                    nodoActual.setHijo(indiceInsertado, nodoAPasar);
                    //arreglar al NodoPadre
                    for (int posicionNodoPadre = posicionDelNodoActualEnNodoPadre+1; posicionNodoPadre <= nodoPadre.cantidadDeDatosNoVacios(); 
                                posicionNodoPadre++) {  
                        T datoAPasar=nodoHermano.getDato(posicionNodoPadre);
                        nodoAPasar=nodoHermano.getHijo(posicionNodoPadre+1);
                        nodoPadre.setDato(posicionNodoPadre-1, datoAPasar);
                        nodoPadre.setHijo(posicionNodoPadre, nodoAPasar);
                    }
                }else{
                    T datoABajar=nodoPadre.getDato(posicionDelNodoActualEnNodoPadre-1);
                    NodoMVias<T> nodoHermano=nodoPadre.getHijo(posicionDelNodoActualEnNodoPadre-1);
                    nodoHermano.setDato(nodoHermano.cantidadDeDatosNoVacios(), datoABajar);
                    int indiceAInsertar=nodoHermano.cantidadDeDatosNoVacios();
                    for (int posicionNodoActual = 0; posicionNodoActual < nodoActual.cantidadDeDatosNoVacios(); 
                            posicionNodoActual++) {
                        T datoAPasar=nodoActual.getDato(posicionNodoActual);
                        NodoMVias<T> nodoAPasar=nodoActual.getHijo(posicionNodoActual);
                        nodoHermano.setDato(indiceAInsertar, datoAPasar);
                        nodoHermano.setHijo(indiceAInsertar, nodoAPasar);
                        indiceAInsertar++;
                    }
                    NodoMVias<T> nodoAPasar=nodoActual.getHijo(nodoActual.cantidadDeDatosNoVacios());
                    nodoHermano.setHijo(indiceAInsertar, nodoAPasar);
                    
                    nodoPadre.setHijoVacio(posicionDelNodoActualEnNodoPadre);
                    nodoPadre.setDatoVacio(posicionDelNodoActualEnNodoPadre-1);
                }
            }
            nodoActual=nodoPadre;
        }
        if (nodoActual.cantidadDeDatosNoVacios()==0) {
            this.raiz=nodoActual.getHijo(0);
        }
    }
    private boolean puedePrestarse(NodoMVias<T> nodoPadre, int posicionDelNodoActualEnNodoPadre) {
        if (posicionDelNodoActualEnNodoPadre==-1 || posicionDelNodoActualEnNodoPadre==orden) {
            return false;
        }
        if (NodoMVias.esNodoVacio(nodoPadre.getHijo(posicionDelNodoActualEnNodoPadre))) {
            return false;
        }
        NodoMVias<T> nodoHijo=nodoPadre.getHijo(posicionDelNodoActualEnNodoPadre);
        if (nodoHijo.cantidadDeDatosNoVacios()>this.nroMinimoDeDatos) {
            return true;
        }
        return false;
    }
    private NodoMVias<T> buscarNodoDelPredecesor(Stack<NodoMVias<T>> pilaDeAncestros, NodoMVias<T> nodoActual) {
        while (!nodoActual.esNodoHoja()) {
            pilaDeAncestros.push(nodoActual);
            nodoActual = nodoActual.getHijo(nodoActual.cantidadDeDatosNoVacios());
        }
        return nodoActual;
    }
    
    private int obtenerPosicionDelNodo(NodoMVias<T> nodoActual, NodoMVias<T> nodoPadre) {
        int cantidadDeHijos=nodoPadre.cantidadDeDatosNoVacios()+1;
        for (int i = 0; i < cantidadDeHijos; i++) {
            if (nodoActual==nodoPadre.getHijo(i)) {
                return i;
            }
        }
        return -1;
    }
    
   

}
