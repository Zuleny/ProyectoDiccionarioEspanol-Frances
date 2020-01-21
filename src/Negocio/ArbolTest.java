/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Negocio.Excepcion.ExcepcionOrdenArbolInvalido;
import java.util.Scanner;

/**
 *
 *
 */
public class ArbolTest {
    public static void main(String[] args) throws ExcepcionOrdenArbolInvalido {
       /*IArbolBusqueda<Integer> unArbol= new ArbolMViasBusqueda<>(4);*/
        Scanner entrada= new Scanner(System.in);
        System.out.print("Elija un tipo de arbol (ABB,AVL,ABMV,AB)");
        String tipoArbol= entrada.next();
        tipoArbol=tipoArbol.toUpperCase();
        IArbolBusqueda<Palabra> unArbol;
        switch(tipoArbol){
            case "ABB":
                unArbol=new ArbolBinarioBusqueda<Palabra>();
                break;
            case "AVL":
                unArbol= new ArbolAVL<Palabra>();
                break;
            case "ABMV":
                unArbol= new ArbolMViasBusqueda<Palabra>(3);
                break;
            case "AB":
                unArbol= new ArbolB<Palabra>();
                break;
            default:
                System.out.print("Tipo de arbol invalido,eligiendo ArbolBinario");
                unArbol= new ArbolBinarioBusqueda<>();
        
        }
        Palabra word=new Palabra("mom","mama");
        unArbol.insertar(word);
        Palabra word1=new Palabra("hello","hola");
        unArbol.insertar(word1);
        Palabra word2=new Palabra("aunt","tia");
        unArbol.insertar(word2);
        Palabra word3=new Palabra("sad", "triste");
        unArbol.insertar(word3);
        Palabra word4=new Palabra("duck", "pato");
        unArbol.insertar(word4);
        Palabra word5=new Palabra("cat", "gato mishi");
        unArbol.insertar(word5);
        Palabra word6=new Palabra("hi", "hola");
        unArbol.insertar(word6);
        
        System.out.println(unArbol.recorridoPorNiveles());
        System.out.println(unArbol.recorridoEnInOrden());
        Palabra P=unArbol.getDatoEnNodo(new Palabra("duck", "pato"));
        if (P==null) {
            System.out.println("No existe");
        }else{
            System.out.println(P.toString());
        }
   //     unArbol.eliminar(word1);
        System.out.println(unArbol.recorridoEnInOrden());
        System.out.println(unArbol.size());
        
        
        /*unArbol.insertar(120);
        unArbol.insertar(200);
        unArbol.insertar(50);
        unArbol.insertar(70);
        unArbol.insertar(75);
        unArbol.insertar(98);
        unArbol.insertar(110);
        unArbol.insertar(130);
        unArbol.insertar(140);
        unArbol.insertar(150);
        unArbol.insertar(400);
        unArbol.insertar(500);
        unArbol.insertar(560);
        unArbol.insertar(72);
        unArbol.insertar(121);
        unArbol.insertar(134);
        unArbol.insertar(145);
        unArbol.insertar(160);
        unArbol.insertar(170);
        unArbol.insertar(190);
        unArbol.insertar(158);
        
       // System.out.println(unArbol.nodosCompletosNivelN(1));
        unArbol.insertar(500);
        unArbol.insertar(12580);
        unArbol.insertar(280);
        unArbol.insertar(55);
        unArbol.insertar(75);
        unArbol.insertar(78);
        unArbol.insertar(98);
        unArbol.insertar(18);
        unArbol.insertar(189);
        unArbol.insertar(145);
        System.out.println(unArbol.recorridoEnInOrden());
        unArbol.insertar(50);
        unArbol.insertar(10);
        unArbol.insertar(20);
        unArbol.insertar(59);
        unArbol.insertar(2275);
        unArbol.insertar(718);
        unArbol.insertar(93);
        unArbol.insertar(90);
        unArbol.insertar(19);
        unArbol.insertar(195);
        System.out.println(unArbol.recorridoEnInOrden());
        unArbol.insertar(150);
        System.out.println(unArbol.recorridoEnInOrden());
        unArbol.insertar(400);
        System.out.println(unArbol.recorridoEnInOrden());
        unArbol.insertar(500);
        System.out.println(unArbol.recorridoEnInOrden());
        unArbol.insertar(560);
        System.out.println(unArbol.recorridoEnInOrden());
        unArbol.insertar(72);
        System.out.println(unArbol.recorridoEnInOrden());
        unArbol.insertar(134);
        System.out.println(unArbol.recorridoEnInOrden());
        unArbol.insertar(160);
        System.out.println(unArbol.recorridoEnInOrden());
        unArbol.insertar(170);
        System.out.println(unArbol.recorridoEnInOrden());
        unArbol.insertar(190);
        System.out.println(unArbol.recorridoEnInOrden());
        unArbol.insertar(18);
        //System.out.println(unArbol.recorridoEnInOrden());
        List<Integer> l=unArbol.recorridoEnInOrden();
        System.out.println(l.size());*/
              
      
       // System.out.println(unArbol.nivelDelDatoMayor());
       // unArbol.insertar(158);
        
        /*unArbol.insertar(122);
        unArbol.insertar(145);
        unArbol.insertar(165);
        unArbol.insertar(134);
        unArbol.insertar(180);
        unArbol.insertar(195);*/
        
       // unArbol.insertar("OA");
        //System.out.println(unArbol.recorridoEnPreOrden());
        //*
     //   System.out.println(unArbol.estaBalanceado());
        /*System.out.println(unArbol.recorridoEnPostOrden());
        System.out.println(unArbol.recorridoPorNiveles());
        System.out.println(unArbol.altura());
        System.out.println(unArbol.nivel());
        System.out.println(unArbol.cantidadDeDatosVacios());
        System.out.println(unArbol.cantidadDeNodosCompletos());
        System.out.println(unArbol.soloHojasAPartirDelNivelN(2));*/
      //unArbol.eliminar(50);
       // System.out.println(unArbol.recorridoPorNiveles());
        //System.out.println(unArbol.getPrimoHermano("HOLA"));
        /* System.out.println(unArbol.recorridoEnPreOrden());
        System.out.println(unArbol.recorridoEnPreOrdenI());
        System.out.println(unArbol.altura());
        System.out.println(unArbol.nivel());
        System.out.println(unArbol.recorridoEnPostOrden());
        System.out.println(unArbol.recorridoEnPostOrdenI());
        System.out.println(unArbol.soloNodosHojasOCompletos());
        System.out.println(unArbol.cantidadNodosHojasAPartirDelNivelN(1));
        ArbolBinarioBusqueda<String> otroArbol=new ArbolBinarioBusqueda<>(unArbol.recorridoEnPreOrden(),unArbol.recorridoEnInOrden());
        System.out.println(otroArbol.recorridoPorNiveles());
        System.out.println(unArbol.recorridoPorNiveles());*/
        
    } 
    
}