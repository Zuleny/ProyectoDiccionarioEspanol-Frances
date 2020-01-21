
package Negocio;

import Negocio.Excepcion.ExcepcionArbolVacio;
import Negocio.Excepcion.ExcepcionDatoNoExiste;
import Negocio.Excepcion.ExcepcionDatoYaExiste;

/**
 * @param <T>
 */
public class ArbolAVL <T extends Comparable<T>> extends ArbolBinarioBusqueda<T>{
     private static final int RANGO_PERMITIDO=1;   
    @Override
    public boolean insertar(T datoAInsertar){
        try{
            this.raiz=insertar(this.raiz,datoAInsertar);
            return true;
        }catch(ExcepcionDatoYaExiste ex){
            return false;
        }
    }
    
    private NodoBinario<T> insertar(NodoBinario<T> nodoActual,T datoAInsertar)throws ExcepcionDatoYaExiste{
        if(NodoBinario.esNodoVacio(nodoActual)){
            NodoBinario<T> nuevoNodo=new NodoBinario<>(datoAInsertar);
            return nuevoNodo;
        }  
        T datoDelNodoActual=nodoActual.getDato();
        if(datoAInsertar.compareTo(datoDelNodoActual)<0){  //por izquierda
            NodoBinario<T>  supuestoHijoIzquierdo=insertar(nodoActual.getHijoIzquierdo(),datoAInsertar);
            nodoActual.setHijoIzquierdo(supuestoHijoIzquierdo);
            return balancear(nodoActual);
        }
        
        if(datoAInsertar.compareTo(datoDelNodoActual)>0){
            NodoBinario<T> supuestoHijoDerecho=insertar(nodoActual.getHijoDerecho(),datoAInsertar);
            nodoActual.setHijoDerecho(supuestoHijoDerecho);
            return balancear(nodoActual);
        }
        //Ya esta en el arbol
        throw new ExcepcionDatoYaExiste();
    }

    private NodoBinario<T> balancear(NodoBinario<T> nodoActual) {
         int alturaIzquierda=this.altura(nodoActual.getHijoIzquierdo());
         int alturaDerecha=this.altura(nodoActual.getHijoDerecho());
         int diferenciasDeAlturas=alturaIzquierda-alturaDerecha;
         NodoBinario<T> nuevoNodo;
         if(diferenciasDeAlturas > RANGO_PERMITIDO){  // El lado  Izquierdo es mas largo   
               nuevoNodo=nodoActual.getHijoIzquierdo();
               alturaIzquierda=altura(nuevoNodo.getHijoIzquierdo());
               alturaDerecha=altura(nuevoNodo.getHijoDerecho());
               if(alturaDerecha > alturaIzquierda){//Rotacion Doble
                   nodoActual=rotacionDobleDerecha(nodoActual);
               }else{   // Rotacion Simple
                   nodoActual=rotacionSimpleDerecha(nodoActual);
               }       
         }else if(diferenciasDeAlturas < -RANGO_PERMITIDO){ // El lado derecho es mas largo
               nuevoNodo=nodoActual.getHijoDerecho();
               alturaIzquierda=altura(nuevoNodo.getHijoIzquierdo());
               alturaDerecha=altura(nuevoNodo.getHijoDerecho());
                if(alturaIzquierda > alturaDerecha){//Rotacion Doble
                   nodoActual=rotacionDobleIzquierda(nodoActual);
               }else{   // Rotacion Simple
                   nodoActual=rotacionSimpleIzquierda(nodoActual);
               }
         }
         //Si llega aqui quiere decir que esta en el Rango(-1,0,1) y no hago nada;
         return nodoActual;
    }

    private NodoBinario<T> rotacionSimpleDerecha(NodoBinario<T> nodoActual) {
        NodoBinario<T> nodoGuardado=nodoActual.getHijoIzquierdo();
        nodoActual.setHijoIzquierdo(nodoGuardado.getHijoDerecho());
        nodoGuardado.setHijoDerecho(nodoActual);
        return nodoGuardado;
    }
   
    private NodoBinario<T> rotacionSimpleIzquierda(NodoBinario<T> nodoActual) {
        NodoBinario<T> nodoGuardado=nodoActual.getHijoDerecho();
        nodoActual.setHijoDerecho(nodoGuardado.getHijoIzquierdo());
        nodoGuardado.setHijoIzquierdo(nodoActual);
        return nodoGuardado;
    }
    private NodoBinario<T> rotacionDobleIzquierda(NodoBinario<T> nodoActual) {
        NodoBinario<T> hijoDerecho=nodoActual.getHijoDerecho();
        hijoDerecho=rotacionSimpleDerecha(hijoDerecho);
        nodoActual.setHijoDerecho(hijoDerecho);
         return rotacionSimpleIzquierda(nodoActual);
    }

    private NodoBinario<T> rotacionDobleDerecha(NodoBinario<T> nodoActual) {
         NodoBinario<T> hijoIzquierdo=nodoActual.getHijoIzquierdo();
        hijoIzquierdo=rotacionSimpleIzquierda(hijoIzquierdo);
        nodoActual.setHijoIzquierdo(hijoIzquierdo);
        return rotacionSimpleDerecha(nodoActual);
    }
        @Override
    public boolean eliminar(T dato) {
        try {
            this.raiz = this.eliminar(this.raiz, dato);
            return true;
        } catch (ExcepcionDatoNoExiste ex) {
            return false;
        }
    }

    private NodoBinario<T> eliminar(NodoBinario<T> nodoActual, T datoAEliminar) throws ExcepcionDatoNoExiste {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return nodoActual;
        }
        T datoDelNodoActual = nodoActual.getDato();
        if (datoAEliminar.compareTo(datoDelNodoActual) < 0) {
            NodoBinario<T> supuestoHijoIzquierdo = eliminar(nodoActual.getHijoIzquierdo(), datoAEliminar);
            nodoActual.setHijoIzquierdo(supuestoHijoIzquierdo);
            return balancear(nodoActual);
        }
        if (datoAEliminar.compareTo(datoDelNodoActual) > 0) {
            NodoBinario<T> suspuestoHijoDerecho = eliminar(nodoActual.getHijoDerecho(), datoAEliminar);
            nodoActual.setHijoDerecho(suspuestoHijoDerecho);
            return balancear(nodoActual);
        }
        // Ya encontro el dato a eliminar
        if (nodoActual.esNodoHoja()) {
            return (NodoBinario<T>) NodoBinario.nodoVacio();
        }
        if (!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho()) {
            return nodoActual.getHijoIzquierdo();
        }
        if (!nodoActual.esVacioHijoDerecho() && nodoActual.esVacioHijoIzquierdo()) {
            return nodoActual.getHijoDerecho();
        }
        T sucesor = getSucesor(nodoActual.getHijoDerecho());
        nodoActual = eliminar(nodoActual, sucesor);
        nodoActual.setDato(sucesor);
        return nodoActual;
    }
}
