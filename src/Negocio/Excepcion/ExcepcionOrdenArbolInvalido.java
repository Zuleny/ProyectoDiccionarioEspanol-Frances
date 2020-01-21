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
public class ExcepcionOrdenArbolInvalido extends Exception {

    /**
     * Creates a new instance of <code>ExcepcionOrdenArbolInvalido</code>
     * without detail message.
     */
    public ExcepcionOrdenArbolInvalido() {
    }

    /**
     * Constructs an instance of <code>ExcepcionOrdenArbolInvalido</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public ExcepcionOrdenArbolInvalido(String msg) {
        super(msg);
    }
}
