/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.edu.uagrm.ficct.edd.proyectoArboles;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author dayle
 */
public class pruebas {
    public static void main(String[] argumentos) throws Exception{
        int tipoArbol ;
        
        
        

        
        
        

        boolean b = true;

         BufferedReader reader = 
                   new BufferedReader(new InputStreamReader(System.in));
        ArbolBinarioBusqueda<Integer,String> arbol = null ;
        while( b ){
            System.out.println("Lista de preguntas:");
            System.out.println("(1) Implementar un método que retorne verdadero si un árbol binario es un montículo");
            System.out.println("(2) Implementar un método insertar iterativo para dicho árbol balanceado:");
            System.out.println("(3) Implementar un método para un árbol de m-vías que retorne cuantos nodos del árbol son padres fuera del nivel n:");
            int metodo = Integer.parseInt(reader.readLine());
            int clave;
		switch(metodo) {
			case 1:			
				ArbolBinarioBusqueda<Integer,String> arbolito = new ArbolBinarioBusqueda<>();
                            System.out.println("nodos insertados: 100 ,150 ,50 ,125,25,26 : ");

                            arbolito.insertar(100, "d");
                            arbolito.insertar(150, "d");
                            arbolito.insertar(50, "d");
                            arbolito.insertar(125, "d");
                            arbolito.insertar(25, "d");
                            arbolito.insertar(26, "d");
                            System.out.println("-----------------Arbol Binario de busqueda ------------ ");
                            System.out.println("\n");
                            arbolito.printBinaryTree();
                            System.out.println("\n");
                            System.out.println("------------------------------------------------------- ");
                            System.out.println("\n");
                            System.out.println("Resultado pregunta 1 es monticulo ? : " + arbolito.esMonticulo());
                            break;
			case 2:
				/*
				 * AVL<Integer,String> arbolAVL = new AVL<>(); arbolAVL.insertarIterativo(100,
				 * "d"); arbolAVL.insertarIterativo(150, "d"); arbolAVL.insertarIterativo(50,
				 * "d"); arbolAVL.insertarIterativo(125, "d"); arbolAVL.insertarIterativo(25,
				 * "d"); arbolAVL.insertarIterativo(26, "d");
				 * System.out.println("nodos insertados: 100 ,150 ,50 ,125,25,26 : ");
				 * System.out.println("-----------------Arbol Binario de busqueda ------------ "
				 * ); System.out.println("\n"); arbolAVL.printBinaryTree();
				 * System.out.println("\n");
				 * System.out.println("------------------------------------------------------- "
				 * ); System.out.println("\n");
				 */
                break;
			case 3:
					ArbolMViasBusqueda<Integer,String> arbolm = new ArbolMViasBusqueda<>(4);
					arbolm.insertar(100, "s");
					arbolm.insertar(200, "s");
					arbolm.insertar(300, "s");
					arbolm.insertar(400, "s");
					arbolm.insertar(50, "s");
					arbolm.insertar(55, "s");
					arbolm.insertar(60, "s");
					arbolm.insertar(30, "s");
					arbolm.insertar(125, "s");
					arbolm.insertar(150, "s");
					arbolm.insertar(170, "s");
					arbolm.insertar(110, "s");
					System.out.println("Arbol de orden 4 ,se insertaron los nodos con clave: 100 ,200 ,300 ,400, 50, 55, 60,30, 125, 150,170, 110 : ");
					System.out.println("Resultado pregunta 3 , contar nodos padres a excepcion del nivel 0 ? resultado  : " + arbolm.cantidadDePadres(0));       
                            break;
		}

        }
//arbolito.insertar(12,"mama");
//arbolito.insertar(11,"mama");
//arbolito.insertar(14,"mama");
//arbolito.insertar(15,"mama");
//arbolito.printBinaryTree();

            
            

        
    }


}
