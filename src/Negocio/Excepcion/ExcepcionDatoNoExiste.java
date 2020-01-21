/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio.Excepcion;

/**
 *
 * @author CRUZ
 */
public class ExcepcionDatoNoExiste extends Exception {

    /**
     * Creates a new instance of <code>ExcepcionDatoNoExiste</code> without
     * detail message.
     */
    public ExcepcionDatoNoExiste() {
    }

    /**
     * Constructs an instance of <code>ExcepcionDatoNoExiste</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ExcepcionDatoNoExiste(String msg) {
        super(msg);
    }
}
