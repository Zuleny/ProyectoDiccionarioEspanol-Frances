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
public class ExcepcionDatoYaExiste extends Exception {

    /**
     * Creates a new instance of <code>ExcepcionDatoYaExiste</code> without
     * detail message.
     */
    public ExcepcionDatoYaExiste() {
    }

    /**
     * Constructs an instance of <code>ExcepcionDatoYaExiste</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ExcepcionDatoYaExiste(String msg) {
        super(msg);
    }
}
