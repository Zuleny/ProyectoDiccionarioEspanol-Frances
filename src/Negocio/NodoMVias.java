
package Negocio;

import java.util.ArrayList;
import java.util.List;

/**
 * @param <T>
 */
public class NodoMVias<T> {
    private List<T> listaDeDatos;
    private List<NodoMVias<T>> listaDeHijos;
    
    public NodoMVias(int orden){
        listaDeDatos=new ArrayList<>(orden-1);
        listaDeHijos=new ArrayList<>(orden);
        for(int i=0;i < orden-1;i++){           
            listaDeHijos.add(NodoMVias.nodoVacio());
            listaDeDatos.add( (T) NodoMVias.datoVacio());
        }
        this.listaDeHijos.add( NodoMVias.nodoVacio());  
    }
    
    public NodoMVias(int orden,T datoAInsertar){
        this(orden);
        this.listaDeDatos.set(0,datoAInsertar);
    }
    public static  NodoMVias nodoVacio(){
        return null;
    }
    
    public static Object  datoVacio(){
        return null;
    }
    
    public T getDato(int posicion){
        return this.listaDeDatos.get(posicion);
    }
    
    public NodoMVias getHijo(int posicion){
        return this.listaDeHijos.get(posicion);
    }
    
    public void setDato(int posicion,T dato){
        this.listaDeDatos.set(posicion, dato);
    }
    
    public void setHijo(int posicion,NodoMVias<T> nodoHijo){
        this.listaDeHijos.set(posicion,nodoHijo);
    }
    
    public static boolean esNodoVacio(NodoMVias unNodo){
        return unNodo==NodoBinario.nodoVacio();
    }
    
    public  boolean esDatoVacio(int posicion){
        return this.listaDeDatos.get(posicion) ==(T)NodoMVias.datoVacio();
    }
    
    public boolean  esNodoHoja(){
          int i=0;
          while(i<listaDeHijos.size()){
              if(!NodoMVias.esNodoVacio(getHijo(i))){
                  return false;
              }
              i=i+1;
          }return true;
          
    }
    
    public boolean esNodoCompleto(){
         for(int i=0; i<listaDeHijos.size(); i++){
             if(  NodoMVias.esNodoVacio(getHijo(i))){
                 return false;
             }
         }
         return true;
    }
    public void setDatoVacio(int posicion){
        this.listaDeDatos.set(posicion,(T) NodoMVias.datoVacio());
    }
    
    public void setHijoVacio(int posicion){
        this.listaDeHijos.set(posicion,NodoMVias.nodoVacio());
    }
    
    public int cantidadDeDatosVacios(){
        int cantidad=0;
        for(int i=0;i<this.listaDeDatos.size();i++){
            if(esDatoVacio(i)){
                cantidad++;
            }
        }
         return cantidad;
    }
    
    public int cantidadDeDatosNoVacios(){
        return this.listaDeDatos.size()-cantidadDeDatosVacios();
    }
    
    public boolean estanDatosLlenos(){
        return cantidadDeDatosVacios()==0;
    }
    
}
