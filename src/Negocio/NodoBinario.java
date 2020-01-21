
package Negocio;

/**
 * @param <T>
 */
public class NodoBinario<T> { //solo generico
    private T dato;
    private NodoBinario<T> hijoIzquierdo;
    private NodoBinario<T> hijoDerecho;
    
    public NodoBinario(){
        
    }
    public NodoBinario(T dato) {
        this.dato = dato;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public NodoBinario<T> getHijoIzquierdo() {
        return hijoIzquierdo;
    }

    public void setHijoIzquierdo(NodoBinario<T> hijoIzquierdo) {
        this.hijoIzquierdo = hijoIzquierdo;
    }

    public NodoBinario<T> getHijoDerecho() {
        return hijoDerecho;
    }

    public void setHijoDerecho(NodoBinario<T> hijoDerecho) {
        this.hijoDerecho = hijoDerecho;
    }
    
    public static Object nodoVacio(){   //metodo STATIC para poder compatir con las otras clases este metodo.
        return null;
    }
    
    public static boolean esNodoVacio(NodoBinario unNodo){
        return NodoBinario.nodoVacio()==unNodo;
    }
    public boolean esVacioHijoIzquierdo(){
        return NodoBinario.esNodoVacio(this.getHijoIzquierdo());
    }
    public boolean esVacioHijoDerecho(){
        return this.getHijoDerecho()==NodoBinario.nodoVacio();
    }
    public boolean esNodoHoja(){
        return esVacioHijoIzquierdo()&&esVacioHijoDerecho();
    }
    public boolean esNodoCompleto(){
         return !esVacioHijoIzquierdo()&&!esVacioHijoDerecho();
    }
    
}
