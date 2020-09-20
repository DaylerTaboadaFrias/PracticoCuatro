/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.edu.uagrm.ficct.edd.proyectoArboles;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author dayle
 */
public class AVL<K extends Comparable<K> ,V> extends ArbolBinarioBusqueda<K,V> implements IArbolBinarioBusqueda<K,V>{
    public void insertar(K clave,V valor) throws DatoYaExisteExcepcion{
        raiz = insertar(raiz ,clave ,valor);
    }

    private NodoBinario<K, V> insertar(NodoBinario<K, V> nodoActual, K clave, V valor) throws DatoYaExisteExcepcion {
        if(NodoBinario.esNodoVacio(nodoActual)){
            NodoBinario<K,V> nuevoNodo = new NodoBinario<K,V>(clave,valor);
            return nuevoNodo;
        }
        K claveActual = nodoActual.getClave();
        if(clave.compareTo(claveActual)>0){
            NodoBinario<K,V> supuestoNodoDerecho = insertar(nodoActual.getHijoDerecho(),clave,valor);
            nodoActual.setHijoDerecho(supuestoNodoDerecho);
            return balancear(nodoActual);
        }else if(clave.compareTo(claveActual)<0){
            NodoBinario<K,V> supuestoNodoIzquierdo = insertar(nodoActual.getHijoIzquierdo(),clave,valor);
            nodoActual.setHijoIzquierdo(supuestoNodoIzquierdo);
            return balancear(nodoActual);
        }
        
        throw new DatoYaExisteExcepcion("El dato ya exite");
    }

    private NodoBinario<K, V> balancear(NodoBinario<K, V> nodoActual) {
        int alturaRamaIzquierda = altura(nodoActual.getHijoIzquierdo());
        int alturaRamaDerecha = altura(nodoActual.getHijoDerecho());
        int diferenciaDeAlturas= alturaRamaIzquierda - alturaRamaDerecha ;
        if(diferenciaDeAlturas > 1){
            NodoBinario<K,V> nodoIzquierdo = nodoActual.getHijoIzquierdo();
            int alturaIzquierda = altura(nodoIzquierdo.getHijoIzquierdo());
            int alturaDerecha = altura(nodoIzquierdo.getHijoDerecho());
            if(alturaDerecha > alturaIzquierda){
                return rotacionDoblePorDerecha(nodoActual);
            }else{
                return rotacionSimplePorDerecha(nodoActual);
            }
        }else if(diferenciaDeAlturas < -1){
            NodoBinario<K,V> nodoDerecha = nodoActual.getHijoDerecho();
            int alturaAIzquierda = altura(nodoDerecha.getHijoIzquierdo());
            int alturaADerecha = altura(nodoDerecha.getHijoDerecho());
            if(alturaAIzquierda > alturaADerecha){
                return rotacionDoblePorIzquierda(nodoActual); 
            }else{
                return rotacionSimplePorIzquierda(nodoActual);
            }
        }
        return nodoActual;
    }

    private NodoBinario<K, V> rotacionSimplePorDerecha(NodoBinario<K, V> nodoActual) {
        NodoBinario<K,V> nodoIzquierdo = nodoActual.getHijoIzquierdo();
         nodoActual.setHijoIzquierdo(nodoIzquierdo.getHijoDerecho());
        nodoIzquierdo.setHijoDerecho(nodoActual);
        return nodoIzquierdo; 
    }

    private NodoBinario<K, V> rotacionSimplePorIzquierda(NodoBinario<K, V> nodoActual) {
        NodoBinario<K,V> nodoDerecho = nodoActual.getHijoDerecho();
        nodoActual.setHijoDerecho(nodoDerecho.getHijoIzquierdo());
        nodoDerecho.setHijoIzquierdo(nodoActual);
        return nodoDerecho;
    }

    private NodoBinario<K, V> rotacionDoblePorDerecha(NodoBinario<K, V> nodoActual) {
        NodoBinario<K,V> nodoDeRotacionPorIzquierda = rotacionSimplePorIzquierda(nodoActual.getHijoIzquierdo());
        nodoActual.setHijoIzquierdo(nodoDeRotacionPorIzquierda);
        return rotacionSimplePorDerecha(nodoActual);
    }

    private NodoBinario<K, V> rotacionDoblePorIzquierda(NodoBinario<K, V> nodoActual) {
        NodoBinario<K,V> nodoDeRotacionPorDerecha = rotacionSimplePorDerecha(nodoActual.getHijoDerecho());
        nodoActual.setHijoDerecho(nodoDeRotacionPorDerecha);
        return rotacionSimplePorIzquierda(nodoActual);
    }
    public V eliminar(K clave) throws DatoNoExisteExcepcion  {
        V valor = this.buscar(clave);
        if(valor == null){
             throw new DatoNoExisteExcepcion();
        }else{
            this.raiz = this.eliminar(this.raiz,clave);
            return valor;
        }
        
    }
    private NodoBinario<K,V> eliminar(NodoBinario<K, V> nodoActual, K clave)throws DatoNoExisteExcepcion {
        if(NodoBinario.esNodoVacio(raiz)){
           throw new DatoNoExisteExcepcion("Error el dato no existe");
        }
        K claveActual = nodoActual.getClave();
        
        if(clave.compareTo(claveActual)>0){
            NodoBinario<K,V> supuestoNodoDerecho = eliminar(nodoActual.getHijoDerecho(),clave);
            nodoActual.setHijoDerecho(supuestoNodoDerecho);
            return balancear(nodoActual);
        }else if(clave.compareTo(claveActual)<0){
            NodoBinario<K,V> supuestoNodoIzquierdo = eliminar(nodoActual.getHijoIzquierdo(),clave);
            nodoActual.setHijoIzquierdo(supuestoNodoIzquierdo);
            return balancear(nodoActual);
        }
        //caso 1
        if(nodoActual.esHoja()){
            return this.nodoVacioParaElArbol();
        }
        //caso 2
        if(nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho()){
            return nodoActual.getHijoDerecho();
        }
        if(!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho()){
            return nodoActual.getHijoIzquierdo();
        }
        
        //caso 3
            NodoBinario<K,V>  nodoReemplazo = buscarPredecedorSucesor(nodoActual.getHijoDerecho());
            NodoBinario<K,V>  nodoDerecho = eliminar(nodoActual.getHijoDerecho(),nodoReemplazo.getClave());
            nodoActual.setHijoDerecho(nodoDerecho);
            nodoReemplazo.setHijoDerecho(nodoActual.getHijoDerecho());
            nodoReemplazo.setHijoIzquierdo(nodoActual.getHijoIzquierdo());
            nodoActual.setHijoDerecho(this.nodoVacioParaElArbol());
            nodoActual.setHijoIzquierdo(this.nodoVacioParaElArbol());
            return nodoReemplazo;
    }

    private NodoBinario<K, V> buscarPredecedorSucesor(NodoBinario<K, V> nodoActual){
        if(NodoBinario.esNodoVacio(nodoActual.getHijoIzquierdo())){
            return nodoActual;
        }
        
        return nodoActual.getHijoIzquierdo();
    }
    
    //Pregunta 2
    public void insertarIterativo(K clave,V valor) throws DatoYaExisteExcepcion{
    if(this.esArbolVacio()){
            this.raiz=new NodoBinario<>(clave,valor);
            return;
        }
        NodoBinario<K,V> nodoActual = this.raiz;
        NodoBinario<K,V> nodoAnterior = this.nodoVacioParaElArbol();
        Stack<NodoBinario<K,V>> pilaDeCamino = new Stack<>();
        while(!NodoBinario.esNodoVacio(nodoActual)){
            nodoAnterior= nodoActual;
            pilaDeCamino.push(nodoAnterior);
            if(clave.compareTo(nodoActual.getClave())>0){
                nodoActual =nodoActual.getHijoDerecho();
            }else if(clave.compareTo(nodoActual.getClave())<0){
                nodoActual = nodoActual.getHijoIzquierdo();
            }else{
                return ;
            }
        }
        NodoBinario<K,V> nodoNuevo = new NodoBinario<K,V>(clave,valor);
        if(clave.compareTo(nodoAnterior.getClave())>0){
            nodoAnterior.setHijoDerecho(nodoNuevo);
        }else{
            nodoAnterior.setHijoIzquierdo(nodoNuevo);
        }
        
        //Para balancear
        NodoBinario<K,V> nodoUltimo;
    	nodoActual = pilaDeCamino.pop();
    	nodoUltimo = this.balancear(nodoActual);
        while(! pilaDeCamino.isEmpty() ){
        	
        }
        
    }
    
    
    
    
}
