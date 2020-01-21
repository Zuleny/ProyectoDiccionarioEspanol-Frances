/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio.Excepcion;

/**
 *
 * @author HP
 */
public class ExcepcionArbolVacio extends Exception {

    /**
     * Creates a new instance of <code>ExcepcionArbolVacio</code> without detail
     * message.
     */
    public ExcepcionArbolVacio() {
    }

    /**
     * Constructs an instance of <code>ExcepcionArbolVacio</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ExcepcionArbolVacio(String msg) {
        super(msg);
    }
}
