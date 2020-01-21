/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Negocio.Excepcion.ExcepcionDatoNoExiste;
import Negocio.Excepcion.ExcepcionOrdenArbolInvalido;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 */
public class ArbolMViasBusqueda <T extends Comparable<T>> implements IArbolBusqueda<T> {
    protected NodoMVias<T> raiz;
    protected int orden;
    
    public ArbolMViasBusqueda(){
        this.orden=3;
    }
    
    public ArbolMViasBusqueda(int orden) throws ExcepcionOrdenArbolInvalido{
        if(orden<3){
            throw new ExcepcionOrdenArbolInvalido();
        }
        this.orden=orden;
    }

    @Override
    public boolean insertar(T datoAInsertar) {
        if(esArbolVacio()){
            this.raiz=new NodoMVias<>(orden,datoAInsertar);
            return true;
        }
        NodoMVias<T> nodoActual=this.raiz;
        while(!NodoMVias.esNodoVacio(nodoActual)){
            if(datoEstaEnNodo(nodoActual,datoAInsertar)){
                return false;
            }
            if(nodoActual.esNodoHoja()){
                if(nodoActual.estanDatosLlenos()){  //S esta lleno y ademas es una hoja se crea un nuevo nodo
                    NodoMVias<T> nuevoNodo=new NodoMVias<>(orden,datoAInsertar);
                    int posicionDeHijo=getPosicionDeHijo(nodoActual,datoAInsertar);
                    nodoActual.setHijo(posicionDeHijo,nuevoNodo);
                    return true;
                }
                setDatoOrdenadoEnNodo(nodoActual,datoAInsertar);
                return true;
            }
            int posicionDeHijo=getPosicionDeHijo(nodoActual,datoAInsertar);
            if(NodoMVias.esNodoVacio(nodoActual.getHijo(posicionDeHijo))){
                 NodoMVias<T> nuevoNodo=new NodoMVias<>(orden,datoAInsertar);
                 nodoActual.setHijo(posicionDeHijo, nuevoNodo);
                 return true;
            }
            nodoActual=nodoActual.getHijo(posicionDeHijo); 
        }
        return false;
    }
    protected boolean datoEstaEnNodo(NodoMVias<T> nodoActual, T datoAInsertar) {
         int i=0;
         while(i<nodoActual.cantidadDeDatosNoVacios()&&!(datoAInsertar.compareTo(nodoActual.getDato(i))==0)){
             i++;
         }
        return i<nodoActual.cantidadDeDatosNoVacios();
    }

    protected int getPosicionDeHijo(NodoMVias<T> nodoActual, T datoAInsertar) {
        int i=0;     
        while( i < nodoActual.cantidadDeDatosNoVacios()&&datoAInsertar.compareTo(nodoActual.getDato(i))>0){
            i++;
        }
        return i;
    }

    protected int setDatoOrdenadoEnNodo(NodoMVias<T> nodoActual, T datoAInsertar) {
        int i=0;
        while(i<nodoActual.cantidadDeDatosNoVacios() && datoAInsertar.compareTo(nodoActual.getDato(i))>0){
            i++;
        }
        
        if(i==nodoActual.cantidadDeDatosNoVacios()){
            nodoActual.setDato(i, datoAInsertar);
            
        }else{
            for(int j=nodoActual.cantidadDeDatosNoVacios()-1; j>=i ;j--){
                  nodoActual.setDato(j+1, nodoActual.getDato(j));
                  nodoActual.setHijo(j+1, nodoActual.getHijo(j));
            }
            nodoActual.setDato(i,datoAInsertar);
        }return i;
    }
    protected int setDatoOrdenadoEnNodo(NodoMVias<T>nodoActual,T datoABuscar,int ordenLocal){
        int posicionDelDato=-1;
        for (int i = 0; i < (ordenLocal-1)&&(posicionDelDato==-1); i++) {
            if (!nodoActual.esDatoVacio(i)) {
                if (datoABuscar.compareTo(nodoActual.getDato(i))<0) {
                    posicionDelDato=i;
                }
            }else{
                posicionDelDato=i;
            }
        }    
        for (int i = ordenLocal-2; i > posicionDelDato; i--) {
            nodoActual.setDato(i, nodoActual.getDato(i-1));
            nodoActual.setHijo(i+1,nodoActual.getHijo(i));
        }
        nodoActual.setDato(posicionDelDato, datoABuscar);
        return posicionDelDato;
    }

    @Override
    public boolean eliminar(T datoAEliminar)  {
        try{
            this.raiz=eliminar(this.raiz,datoAEliminar);
            return true;
        }catch(ExcepcionDatoNoExiste ex){
            return false;
        }
    }
    private NodoMVias<T> eliminar(NodoMVias<T> nodoActual,T datoAEliminar) throws ExcepcionDatoNoExiste{
        if(NodoMVias.esNodoVacio(nodoActual)){
            throw new ExcepcionDatoNoExiste();
        }
        T datoDelNodoActual;
        for(int i=0; i < this.orden-1;i++ ){
             if(nodoActual.esDatoVacio(i)){
                throw new ExcepcionDatoNoExiste();
            }
            datoDelNodoActual=nodoActual.getDato(i);
            //Ya encontro el dato a eliminar:
            if(datoAEliminar.compareTo(datoDelNodoActual)==0){
                //Caso 1: El nodoActual es un Nodo Hoja:
                if(nodoActual.esNodoHoja()){
                    nodoActual=eliminarDatoEnNodo(nodoActual,i);
                    if(nodoActual.cantidadDeDatosNoVacios()==0){
                        return NodoMVias.nodoVacio();
                    }return nodoActual;
                }
                //Caso 2: Existen datos despues de la posicion i (busca sucecesor inOrden)
                if(hayHijosDespuesDeLaPosicionI(nodoActual,i)){
                    T datoSucesor=getSucesorInorden(nodoActual,i);
                    nodoActual=eliminar(nodoActual,datoSucesor);
                    nodoActual.setDato(i, datoSucesor);
                    return nodoActual;
                }  
                //Caso 3: No Existen datos despues de la posicion i ,pero si Existen datos antes de la posicion i (busca PredeCesor)
                T datoPredecesor=getPredecesorInorden(nodoActual,i);
                nodoActual=eliminar(nodoActual,datoPredecesor);
                nodoActual.setDato(i,datoPredecesor);
                return nodoActual;
            }else if(datoAEliminar.compareTo(datoDelNodoActual)<0){ //Procede a Buscar el datoAEliminar
                NodoMVias<T> supuestoNuevoHijoI=eliminar(nodoActual.getHijo(i),datoAEliminar);
                nodoActual.setHijo(i,supuestoNuevoHijoI);
                return nodoActual;
            }   
        } //Fin for 
        // Se  procesa el ultimo hijo (Hijo orden-1)
        NodoMVias<T> supuestoNuevoUltimoHijo=eliminar(nodoActual.getHijo(orden-1),datoAEliminar);
        nodoActual.setHijo(orden-1,supuestoNuevoUltimoHijo);
        return nodoActual;
    }
    
    private NodoMVias<T> eliminarDatoEnNodo(NodoMVias<T> nodoActual,int posicion){
        for(int i=posicion; i < nodoActual.cantidadDeDatosNoVacios()-1;i++){
            nodoActual.setDato(i,nodoActual.getDato(i+1));
        }
        nodoActual.setDatoVacio(nodoActual.cantidadDeDatosNoVacios()-1);
        return nodoActual;
    }
    
    private boolean hayHijosDespuesDeLaPosicionI(NodoMVias<T> nodoActual,int posicion){
        int i=posicion+1;
        while(i < this.orden){
            if(! NodoMVias.esNodoVacio(nodoActual.getHijo(i))){
                return true;
            }
            i++;
        }
        return false;
    }
    
    private T getSucesorInorden(NodoMVias<T> nodoActual,int posicion){
        List<T> recorrido=new ArrayList<>();
        recorridoEnInOrden(nodoActual,recorrido);
        T elemento=nodoActual.getDato(posicion);
        int posicionSucesor= recorrido.indexOf(elemento);
        return recorrido.get(posicionSucesor+1);
    }
    
    private T getPredecesorInorden(NodoMVias<T> nodoActual,int posicion){
        List<T> recorrido=new ArrayList<>();
        recorridoEnInOrden(nodoActual,recorrido);
        T elemento=nodoActual.getDato(posicion);
        int posicionPrecesor= recorrido.indexOf(elemento);
        return recorrido.get(posicionPrecesor-1);
    }

    @Override
    public boolean buscar(T dato) {
        if(esArbolVacio()){
            return false;
        }
        NodoMVias<T> nodoActual=this.raiz;
        while(!NodoMVias.esNodoVacio(nodoActual)){
            if(datoEstaEnNodo(nodoActual,dato)){
                return true;
            }
            int posicionDeHijo=getPosicionDeHijo(nodoActual,dato);
            nodoActual=nodoActual.getHijo(posicionDeHijo); 
        }
        return false;
    }

    @Override
    public List<T> recorridoEnPreOrden() {
        List<T> recorrido=new ArrayList<>();
        recorridoEnPreOrden(this.raiz,recorrido);
        return recorrido;
    }
    
    private void recorridoEnPreOrden(NodoMVias<T> nodoActual,List<T> recorrido){
        if(!NodoMVias.esNodoVacio(nodoActual)){
            for(int i=0;i<nodoActual.cantidadDeDatosNoVacios();i++){
                recorrido.add(nodoActual.getDato(i));
                recorridoEnPreOrden(nodoActual.getHijo(i),recorrido);
            }
            recorridoEnPreOrden(nodoActual.getHijo(nodoActual.cantidadDeDatosNoVacios()),recorrido);
        }
    }

    @Override
    public List<T> recorridoEnInOrden() {
        List<T> recorrido=new ArrayList<>();
        recorridoEnInOrden(this.raiz,recorrido);
        return recorrido;
    }
    
    private void recorridoEnInOrden(NodoMVias<T> nodoActual, List<T> recorrido) {
        if(!NodoMVias.esNodoVacio(nodoActual)){
            for(int i=0;i<nodoActual.cantidadDeDatosNoVacios();i++){
                recorridoEnInOrden(nodoActual.getHijo(i),recorrido);
                recorrido.add( nodoActual.getDato(i));
            }
            recorridoEnInOrden(nodoActual.getHijo(nodoActual.cantidadDeDatosNoVacios()),recorrido);
        }
    }

    @Override
    public List<T> recorridoEnPostOrden() {
        List<T> recorrido=new ArrayList<>();
        recorridoEnPostOrden(this.raiz,recorrido);
        return recorrido;
    }
    
    private void recorridoEnPostOrden(NodoMVias nodoActual,List<T> recorrido){
        if(!NodoMVias.esNodoVacio(nodoActual)){
            recorridoEnPostOrden(nodoActual.getHijo(0),recorrido);
            for(int i=0;i<nodoActual.cantidadDeDatosNoVacios();i++){
                recorridoEnPostOrden(nodoActual.getHijo(i+1),recorrido);
                recorrido.add((T) nodoActual.getDato(i));
            }
        }
    }

    @Override
    public List<T> recorridoPorNiveles() {
        List<T> recorrido=new ArrayList<>();
        if(esArbolVacio()){
            return recorrido;
        }
        Queue  <NodoMVias<T>> cola=new LinkedList<>();
        cola.offer(this.raiz);
        NodoMVias<T> nodoActual=new NodoMVias<>(this.orden);
        while(!cola.isEmpty()){
            nodoActual=cola.poll();
            for(int i=0;i<nodoActual.cantidadDeDatosNoVacios();i++){
                if(!NodoMVias.esNodoVacio(nodoActual.getHijo(i))){
                    cola.offer(nodoActual.getHijo(i));
                }
                recorrido.add(nodoActual.getDato(i));
            }
            if(!NodoMVias.esNodoVacio(nodoActual.getHijo(orden-1))){
                cola.offer(nodoActual.getHijo(orden-1));
            }
        }
        return recorrido;
    }

    @Override
    public int size() {
        if (this.esArbolVacio()) {
            return 0;
        }
        Queue<NodoMVias<T>> cola = new LinkedList<>();
        cola.offer(raiz);
        int cantidadDeNodos = 0;
        while (!cola.isEmpty()) {
            NodoMVias<T> nodoActual = cola.poll();
            cantidadDeNodos++;
            for (int i = 0; i <= nodoActual.cantidadDeDatosNoVacios(); i++) {
                if (!NodoMVias.esNodoVacio(nodoActual.getHijo(i))) {
                    cola.offer(nodoActual.getHijo(i));
                }
            }
        }
        return cantidadDeNodos;
    }

    @Override
    public int altura() {
        return altura(this.raiz);
    }
    
    protected int altura(NodoMVias<T> nodoActual){
        if(NodoMVias.esNodoVacio(nodoActual)){
            return 0;
        }
        int alturaMayor=0;
        int alturaActual=0;
        for(int i=0;i<nodoActual.cantidadDeDatosNoVacios();i++){
            alturaActual=altura(nodoActual.getHijo(i));
            if(alturaActual>alturaMayor){
                alturaMayor=alturaActual;
            }
        }
        alturaActual=altura(nodoActual.getHijo(orden-1));
        if(alturaActual>alturaMayor){
            alturaMayor=alturaActual;
        }
        return alturaMayor+1;
    }

    @Override
    public void vaciar() {
        this.raiz=NodoMVias.nodoVacio();
    }

    @Override
    public boolean esArbolVacio() {
        return this.raiz==NodoMVias.nodoVacio();
    }

    @Override
    public int nivel() {
          return nivel(this.raiz);
    }
    
    protected int nivel(NodoMVias<T> nodoActual){
        if(NodoMVias.esNodoVacio(nodoActual)){
            return -1;
        }
       
        int nivelMayor=-1;
        int nivelActual=0;
        for(int i=0;i<nodoActual.cantidadDeDatosNoVacios();i++){
            nivelActual=nivel(nodoActual.getHijo(i));
            if(nivelActual>nivelMayor){
                nivelMayor=nivelActual;
            }
        }
        nivelActual=nivel(nodoActual.getHijo(orden-1));
        if(nivelActual>nivelMayor){
            nivelMayor=nivelActual;
        }
        return nivelMayor+1;
    }
    
    @Override
    public T getDatoEnNodo(T datoABuscar){
        if(esArbolVacio()){
            return (T)NodoBinario.nodoVacio();
        }
        NodoMVias<T> nodoActual=this.raiz;
        while(!NodoMVias.esNodoVacio(nodoActual)){
            if(datoEstaEnNodo(nodoActual,datoABuscar)){
                int posicion=getPosicionDatoEnNodo(nodoActual, datoABuscar);
                return nodoActual.getDato(posicion);
            }
            int posicionDeHijo=getPosicionDeHijo(nodoActual,datoABuscar);
            nodoActual=nodoActual.getHijo(posicionDeHijo); 
        }
        return (T)NodoMVias.datoVacio();
    }
    /**
     * 
     * @param nodoActual
     * @param datoABuscar
     * @return 
     */
    protected int getPosicionDatoEnNodo(NodoMVias<T> nodoActual, T datoABuscar) {
        int i=0;
        while(i<nodoActual.cantidadDeDatosNoVacios() && datoABuscar.compareTo(nodoActual.getDato(i))>0){
            i++;
        }
        return i;
    }
}
