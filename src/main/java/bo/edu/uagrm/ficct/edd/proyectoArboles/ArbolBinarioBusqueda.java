/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.edu.uagrm.ficct.edd.proyectoArboles;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author dayle
 */
public class ArbolBinarioBusqueda<K extends Comparable<K>,V> implements IArbolBinarioBusqueda<K,V>{
    protected NodoBinario<K,V> raiz;
    public ArbolBinarioBusqueda(){
        this.raiz=null;
    }
    protected NodoBinario<K,V> nodoVacioParaElArbol(){
        return (NodoBinario<K, V>) NodoBinario.nodoVacio();
    }

    @Override
    public String toString() {
       return GenerarCadenaArbol(raiz,"",true);
    }

    private String GenerarCadenaArbol(NodoBinario<K,V> nodo, String prefijo, boolean PonerCodo) {
    StringBuilder  cadena= new StringBuilder();
       cadena.append(prefijo);
       //es lo mismo :
       // cadena.append(ponerCodo ? "└- ":"├- ");
       if(PonerCodo){
       cadena.append("└-");
       }else{
       cadena.append("├-");
       }
       if(NodoBinario.esNodoVacio(nodo)){
       cadena.append("\n");
       return cadena.toString();
       }
       
       cadena.append(nodo.getValor().toString());
       cadena.append("\n");
       NodoBinario nodoIzq= nodo.getHijoIzquierdo();
       String prefijoAux= cadena.toString() + (PonerCodo ? "  ": "|  ");
       cadena.append(GenerarCadenaArbol(nodoIzq,prefijoAux, false));
       
       NodoBinario nodoDer= nodo.getHijoDerecho();
       prefijoAux= cadena.toString() + (PonerCodo ? "  " : "|   ");
       cadena.append(GenerarCadenaArbol(nodoDer, prefijoAux, true));
       
       return cadena.toString();
    }
    public void insertar(K clave,V valor) throws DatoYaExisteExcepcion{
    if(this.esArbolVacio()){
            this.raiz=new NodoBinario<>(clave,valor);
            return;
        }
        NodoBinario<K,V> nodoActual = this.raiz;
        NodoBinario<K,V> nodoAnterior = this.nodoVacioParaElArbol();
        while(!NodoBinario.esNodoVacio(nodoActual)){
            nodoAnterior= nodoActual;
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
    }
    public void vaciar(){
        this.raiz=(NodoBinario<K, V>) NodoBinario.nodoVacio();
    }
    public boolean esArbolVacio(){
        return (NodoBinario.esNodoVacio(this.raiz));
    }
    public V buscar(K clave){
        NodoBinario<K,V> nodoActual = this.raiz;
        while(!NodoBinario.esNodoVacio(nodoActual)){
            if(clave.compareTo(nodoActual.getClave())>0){
                nodoActual=nodoActual.getHijoDerecho();
            }else if(clave.compareTo(nodoActual.getClave())<0){
                nodoActual=nodoActual.getHijoIzquierdo();
            }else{
                return nodoActual.getValor();
            }
        }
        return null;
    }
    public List<K> recorridoPorNiveles(){
        List<K> listakeys= new LinkedList<>();
        if(this.esArbolVacio()){
            return listakeys;
        }
        Queue<NodoBinario<K,V>> colaNodos = new LinkedList<>();
        colaNodos.offer(this.raiz);
        while(!colaNodos.isEmpty()){
            NodoBinario<K,V> nodoActual = colaNodos.poll();
            listakeys.add(nodoActual.getClave());
            if(!nodoActual.esVacioHijoIzquierdo()){
                colaNodos.offer(nodoActual.getHijoIzquierdo());
            }
            if(!nodoActual.esVacioHijoDerecho()){
                colaNodos.offer(nodoActual.getHijoDerecho());
            }
        }
        return listakeys;
    }
    public int size(){
        List<K> listakeys= new LinkedList<>();
        if(this.esArbolVacio()){
            return 0;
        }
        int contador = 0;
        Queue<NodoBinario<K,V>> colaNodos = new LinkedList<>();
        colaNodos.offer(this.raiz);
        while(!colaNodos.isEmpty()){
            NodoBinario<K,V> nodoActual = colaNodos.poll();
            contador=contador+1;
            if(!nodoActual.esVacioHijoIzquierdo()){
                colaNodos.offer(nodoActual.getHijoIzquierdo());
            }
            if(!nodoActual.esVacioHijoDerecho()){
                colaNodos.offer(nodoActual.getHijoDerecho());
            }
        }
        return contador;
    }
    public List<K> recorridoEnPreorden(){
        List<K> listakeys = new LinkedList<>();
        if(this.esArbolVacio()){
            return listakeys;
        }
        Stack<NodoBinario<K,V>> pilaNodos = new Stack<>();
        pilaNodos.push(this.raiz);
        while(!pilaNodos.isEmpty()){
            NodoBinario<K,V> nodoActual= pilaNodos.pop();
            listakeys.add(nodoActual.getClave());
            if(!nodoActual.esVacioHijoDerecho()){
                pilaNodos.push(nodoActual.getHijoDerecho());
            }
            if(!nodoActual.esVacioHijoIzquierdo()){
                pilaNodos.push(nodoActual.getHijoIzquierdo());
            }
        }
        return listakeys;
    }

    @Override
    public List<K> recorridoInOrden() {
        List<K> listaClaves=new LinkedList<>();
        if(this.esArbolVacio()){
            return listaClaves;
        }
        NodoBinario<K,V> nodoActual = this.raiz;
        Stack<NodoBinario<K,V>> pilaDeNodos = new Stack<>();
        while(!NodoBinario.esNodoVacio(nodoActual)){
            pilaDeNodos.push(nodoActual);
            nodoActual = nodoActual.getHijoIzquierdo();
        }
        while(!pilaDeNodos.isEmpty()){
            NodoBinario<K,V> nodoActuals=pilaDeNodos.pop();
            listaClaves.add(nodoActuals.getClave());
            if(!nodoActuals.esVacioHijoDerecho()){
                NodoBinario<V,K> nodoAuxiliar= (NodoBinario<V,K>) nodoActuals.getHijoDerecho();
                while(!NodoBinario.esNodoVacio(nodoAuxiliar)){
                    pilaDeNodos.push((NodoBinario<K, V>) nodoAuxiliar);
                    nodoAuxiliar = nodoAuxiliar.getHijoIzquierdo();
                }
            }
        }
        return listaClaves;
    }

    @Override
    public List<K> recorridoPostOrden() {
        List<K> listaDeClaves = new LinkedList<>();
        Stack<NodoBinario<K,V>>pilaDeNodos = new Stack<>();
        NodoBinario<K,V> nodoActual = this.raiz;
        NodoBinario<K,V> nodoUltimo ;
        NodoBinario<K,V> nodoVisitado=null ;
        while(!NodoBinario.esNodoVacio(nodoActual) || !pilaDeNodos.isEmpty()){
            if(!NodoBinario.esNodoVacio(nodoActual)){
                pilaDeNodos.push(nodoActual);
                nodoActual = nodoActual.getHijoIzquierdo();
            }
            else{
                nodoUltimo = pilaDeNodos.peek();
                if(!NodoBinario.esNodoVacio(nodoUltimo.getHijoDerecho()) 
                        && nodoVisitado !=nodoUltimo.getHijoDerecho() ){
                    nodoActual = nodoUltimo.getHijoDerecho();
                }else{
                    listaDeClaves.add(nodoUltimo.getClave());
                    nodoVisitado = pilaDeNodos.pop();
                }
            }
        }
        return listaDeClaves;
        
    }
    
    public int examenPost(int nivel){
        Stack<NodoBinario<K,V>> pilaDeNodos = new Stack<>();
        Stack<Integer> pilaDeAlturas = new Stack<>();
        NodoBinario<K,V> nodoActual = this.raiz;
        int alturaActual =1;
        NodoBinario<K,V> nodoUltimo ;
        NodoBinario<K,V> nodoVisitado= null;
        int cantidad = 0 ;
        while(! NodoBinario.esNodoVacio(nodoActual ) || ! pilaDeNodos.isEmpty()){
            if(! NodoBinario.esNodoVacio(nodoActual)){
                pilaDeNodos.push(nodoActual);
                nodoActual = nodoActual.getHijoIzquierdo();
                pilaDeAlturas.push(alturaActual);
                alturaActual = alturaActual +1;
                
            }else{
                alturaActual = pilaDeAlturas.peek();
                nodoUltimo = pilaDeNodos.peek();
                if(! nodoUltimo.esVacioHijoDerecho() && nodoVisitado != nodoUltimo.getHijoDerecho()){
                    nodoActual = nodoUltimo.getHijoDerecho();
                    alturaActual = alturaActual +1;
                }else{
                    nodoVisitado = pilaDeNodos.pop();
                    alturaActual = pilaDeAlturas.pop();
                    if(nodoVisitado.esHoja() && alturaActual>= nivel){
                        cantidad = cantidad +1;
                    }
                }
            }
        }
        return cantidad;
    }
    
    
    
    public List<K> recorridoEnPostOrden(){
        List<K> lista = new LinkedList<>();
        recorridoEnPostOrdenRecursivo(this.raiz,lista);
        return lista;
    }

    private void recorridoEnPostOrdenRecursivo(NodoBinario<K, V> raiz, List<K> lista) {
        if(NodoBinario.esNodoVacio(raiz)){
           return; 
        }
        recorridoEnPostOrdenRecursivo(raiz.getHijoIzquierdo(),lista);
        recorridoEnPostOrdenRecursivo(raiz.getHijoDerecho(),lista);
        lista.add(raiz.getClave());
    }
    //Implemente un método recursivo que retorne la cantidad nodos hojas que existen en un arbol binario
    public int cantidadDeNodosHojas(){
        int contador=0;
        return this.cantidadDeNodosHojasRecursivo(raiz, contador);
    }
    private  int cantidadDeNodosHojasRecursivo(NodoBinario<K,V> nodoActual,int contador){
        if(NodoBinario.esNodoVacio(nodoActual)){
          return 0;
        }
        int cantidadPorIzquierda = this.cantidadDeNodosHojasRecursivo(nodoActual.getHijoIzquierdo(), contador);
        int cantidadPorDerecha = this.cantidadDeNodosHojasRecursivo(nodoActual.getHijoDerecho(), contador);
        if(nodoActual.esHoja()){
            return cantidadPorDerecha+cantidadPorIzquierda+1;
        }
        return cantidadPorDerecha+cantidadPorIzquierda;
    }
    
        //Implemente un método recursivo que retorne la cantidad nodos hojas en un solo nivel
    public int cantidadDeNodosHojasEnUnNivel(int nivel){
        int contador=0;
        return this.cantidadDeNodosHojasEnUnNivelRecursivo(raiz, contador,0,nivel);
    }
    private  int cantidadDeNodosHojasEnUnNivelRecursivo(NodoBinario<K,V> nodoActual,int contador,int nivelActual,int nivel){
        if(NodoBinario.esNodoVacio(nodoActual)){
          return 0;
        }
        int cantidadPorIzquierda = this.cantidadDeNodosHojasEnUnNivelRecursivo(nodoActual.getHijoIzquierdo(), contador,nivelActual+1,nivel);
        int cantidadPorDerecha = this.cantidadDeNodosHojasEnUnNivelRecursivo(nodoActual.getHijoDerecho(), contador,nivelActual+1,nivel);
        if(nodoActual.esHoja() && nivel==nivelActual){
            return cantidadPorDerecha+cantidadPorIzquierda+1;
        }
        return cantidadPorDerecha+cantidadPorIzquierda;
    }
    
    
    //Implemente un método iterativo que retorne la cantidad nodos hojas que existen en un árbol binario 
    public  int cantidadDeNodosHojasIterativo(){
        if(NodoBinario.esNodoVacio(raiz)){
           return 0;
        }
        int contador=0;
        Queue<NodoBinario<K,V>> colaNodos = new LinkedList<>();
        colaNodos.offer(raiz);
        while(!colaNodos.isEmpty()){
           NodoBinario<K,V> nodoActual = colaNodos.poll();
           if(nodoActual.esHoja()){
               contador = contador + 1 ;
           }
           if(!nodoActual.esVacioHijoIzquierdo()){
               colaNodos.offer(nodoActual.getHijoIzquierdo());
           }
           if(!nodoActual.esVacioHijoDerecho()){
               colaNodos.offer(nodoActual.getHijoDerecho());
           }
        }
        return contador;
    }
    //Implemente un método recursivo que retorne la cantidad nodos hojas que existen en un árbol binario,
    //pero solo en el nivel 
    public int cantidadNodosHojaAntesUnNivel(int nivel){
        return cantidadNodosHojaAntesUnNivelRecursivo(this.raiz,0,nivel);
    } 

    private int cantidadNodosHojaAntesUnNivelRecursivo(NodoBinario<K, V> nodoActual,int nivelActual,int nivel) {
        if(NodoBinario.esNodoVacio(nodoActual)){
            return 0;
        }
        int cantidadPorIzquierda = cantidadNodosHojaAntesUnNivelRecursivo(nodoActual.getHijoIzquierdo(),
                nivelActual+1,nivel);
        int cantidadPorDerecha = cantidadNodosHojaAntesUnNivelRecursivo(nodoActual.getHijoDerecho(),
                nivelActual+1,nivel);
        if(nodoActual.esHoja() && nivel >= nivelActual){
            return cantidadPorIzquierda+cantidadPorDerecha+1;
        }
        return cantidadPorIzquierda+cantidadPorDerecha;
    }
        //Implemente un método iterativo que retorne la cantidad nodos hojas que existen en un árbol binario,
    //pero solo en el nivel 
    public int cantidadNodosHojasAntesNivelIterativo(int nivel){
        if(NodoBinario.esNodoVacio(raiz)){
            return 0;
        }
        Queue<NodoBinario<K,V>> colaDeNodos= new LinkedList<>();
        NodoBinario<K,V> nodoActual ;
        colaDeNodos.offer(this.raiz);
        int contador=0;
        while(!colaDeNodos.isEmpty()){
            nodoActual = colaDeNodos.poll();
            int nivelActual = this.encontraNivel(nodoActual.getClave());
            if(nivelActual<=nivel && nodoActual.esHoja()){
                contador = contador + 1;
            }
            if(!nodoActual.esVacioHijoIzquierdo()){
                colaDeNodos.offer(nodoActual.getHijoIzquierdo()) ;
            }
            if(!nodoActual.esVacioHijoDerecho()){
                colaDeNodos.offer(nodoActual.getHijoDerecho()) ;
            }
        }
        return contador;
    }
    
    
    //Cantidad de nodos hoja un un solo nivel
    
    public int cantidadNodosHojasEnUnNivelIterativo(int nivel){
        if(NodoBinario.esNodoVacio(raiz)){
            return 0;
        }
        Queue<NodoBinario<K,V>> colaDeNodos= new LinkedList<>();
        Queue<Integer> colaDeNiveles= new LinkedList<>();
        NodoBinario<K,V> nodoActual ;
        colaDeNodos.offer(this.raiz);
        colaDeNiveles.offer(0);
        int contador=0;
        while(!colaDeNodos.isEmpty()){
            nodoActual = colaDeNodos.poll();
            int nivelActual = colaDeNiveles.poll();
            if(nivelActual==nivel && nodoActual.esHoja()){
                contador = contador + 1;
            }
            if(!nodoActual.esVacioHijoIzquierdo()){
                colaDeNodos.offer(nodoActual.getHijoIzquierdo()) ;
                colaDeNiveles.offer(nivelActual+1);
            }
            if(!nodoActual.esVacioHijoDerecho()){
                colaDeNodos.offer(nodoActual.getHijoDerecho()) ;
                colaDeNiveles.offer(nivelActual+1);
            }
        }
        return contador;
    }
    //Encontrar nivel de nodo
    public int encontraNivel(K clave){
        NodoBinario<K,V> nodoActual = this.raiz;
        K claveActual = nodoActual.getClave();
        Boolean b=false;
        int contador = 0;
        while(b==false){
            if(clave.compareTo(claveActual)>0){
                nodoActual= nodoActual.getHijoDerecho();
                claveActual=nodoActual.getClave();
                contador = contador+1;
            }
            else if(clave.compareTo(claveActual)<0){
                nodoActual= nodoActual.getHijoIzquierdo();
                claveActual=nodoActual.getClave();
                contador = contador+1;
            }else{
                b=true;
                return contador;
            }
        }
        return 0;
    }
    //Altura del algo
    public int altura(){
        return this.altura(this.raiz);
    }
    protected int altura(NodoBinario<K, V> nodoActual) {
        if(nodoActual == null){
            return 0;
        }
        int cantidadPorIzquierda = altura(nodoActual.getHijoIzquierdo());
        int cantidadPorDerecha = altura(nodoActual.getHijoDerecho());
        if(cantidadPorIzquierda>cantidadPorDerecha){
            return cantidadPorIzquierda+1;
        }
        return cantidadPorDerecha+1;
    }
    //Contar nodos desde un nivel
    public int contarNodosDesdeUnNivel(int nivel){
        return this.contarNodosDesdeUnNivelRecursivo(this.raiz,0,nivel);
    }
    protected int contarNodosDesdeUnNivelRecursivo(NodoBinario<K, V> nodoActual,int nivelActual,int nivel) {
        if( NodoBinario.esNodoVacio(nodoActual)){
            return 0;
        }
        int cantidadPorIzquierda = contarNodosDesdeUnNivelRecursivo(nodoActual.getHijoIzquierdo(),nivelActual+1,nivel);
        int cantidadPorDerecha = contarNodosDesdeUnNivelRecursivo(nodoActual.getHijoDerecho(),nivelActual+1,nivel);
         if(nivelActual >= nivel){
            return cantidadPorIzquierda + cantidadPorDerecha + 1;
        }
        return cantidadPorDerecha  + cantidadPorIzquierda ;
    }
    
    
    //Implemente un método recursivo que retorne verdadero, si un árbol binario esta balanceado según las reglas 
    //que establece un árbol AVL, falso en caso contrario.
    public Boolean verificarSiEsBalanceado(){
        return verificarSiEsBalanceadoRecursivo(this.raiz);
    }

    private Boolean verificarSiEsBalanceadoRecursivo(NodoBinario<K, V> nodoActual) {
        if(NodoBinario.esNodoVacio(nodoActual)){
            return true;
        }
        Boolean guardarPorIzquierda = verificarSiEsBalanceadoRecursivo(nodoActual.getHijoIzquierdo());
        Boolean guardarPorDerecha = verificarSiEsBalanceadoRecursivo(nodoActual.getHijoDerecho());
        int alturaPorIzquierda = altura(nodoActual.getHijoIzquierdo());
        int alturaPorDerecha = altura(nodoActual.getHijoDerecho());
        int diferencia = alturaPorIzquierda-alturaPorDerecha;
        if(guardarPorIzquierda==false || guardarPorDerecha==false){
            return false;
        }
        if(diferencia < -1 || diferencia >1){
            return false;
        }
        return true;
    }
    
    
    //RECURSIVO : Implemente un método privado que reciba un nodo binario de un árbol binario 
    //y que retorne cual sería su sucesor inorden de la clave de dicho nodo. 
    public String buscarSucesorPublico(K clave) throws DatoNoExisteExcepcion{
        NodoBinario<K,V> nodoActual = this.raiz;
        while(!NodoBinario.esNodoVacio(nodoActual)){
            if(clave.compareTo(nodoActual.getClave())>0){
                nodoActual=nodoActual.getHijoDerecho();
            }else if(clave.compareTo(nodoActual.getClave())<0){
                nodoActual=nodoActual.getHijoIzquierdo();
            }else{
                NodoBinario<K,V> nodoSucesor = buscarSucesorPrivado(nodoActual);
                if(NodoBinario.esNodoVacio(nodoSucesor)){
                    return "No tiene nodo Sucesor";
                }
                return nodoSucesor.getClave().toString();
            }
        }
        throw new DatoNoExisteExcepcion("Error la clave no existe");
    }
    //  Metodo privado para encontrar un nodo sucesor
    private NodoBinario<K,V> buscarSucesorPrivado(NodoBinario<K,V> nodoBuscar) throws DatoNoExisteExcepcion{
        K clave = nodoBuscar.getClave();
        NodoBinario<K,V> nodoActual = nodoBuscar.getHijoDerecho();
        if(NodoBinario.esNodoVacio(nodoActual)){
             return null;
        }
        while(!NodoBinario.esNodoVacio(nodoActual.getHijoIzquierdo())){
            nodoActual = nodoActual.getHijoIzquierdo();
        }
        return nodoActual;
    }
    
    
    
    //RECURSIVO : Implemente un método privado que reciba un nodo binario de un árbol binario 
    //y que retorne cual sería su predecesor inorden de la clave de dicho nodo. 
    public String buscarPredecesorPublico(K clave) throws DatoNoExisteExcepcion{
        NodoBinario<K,V> nodoActual = this.raiz;
        while(!NodoBinario.esNodoVacio(nodoActual)){
            if(clave.compareTo(nodoActual.getClave())>0){
                nodoActual=nodoActual.getHijoDerecho();
            }else if(clave.compareTo(nodoActual.getClave())<0){
                nodoActual=nodoActual.getHijoIzquierdo();
            }else{
                NodoBinario<K,V> nodoPredecesor = buscarSucesorPrivado(nodoActual);
                if(NodoBinario.esNodoVacio(nodoPredecesor)){
                    return "No tiene nodo Sucesor";
                }
                return nodoPredecesor.getClave().toString();
            }
        }
        throw new DatoNoExisteExcepcion("Error la clave no existe");
    }
    //  Metodo privado para encontrar un nodo predecesor
    private NodoBinario<K,V> buscarPredecesorPrivado(NodoBinario<K,V> nodoBuscar) throws DatoNoExisteExcepcion{
        K clave = nodoBuscar.getClave();
        NodoBinario<K,V> nodoActual = nodoBuscar.getHijoIzquierdo();
        if(NodoBinario.esNodoVacio(nodoActual)){
             return null;
        }
        while(!NodoBinario.esNodoVacio(nodoActual.getHijoDerecho())){
            nodoActual = nodoActual.getHijoDerecho();
        }
        return nodoActual;
    }
    
    
    
    
    // Implemente un método privado que reciba un nodo binario de un árbol binario y
    //que retorne cuál sería su predecesor inorden de la clave de dicho nodo. 
    public NodoBinario<K,V> buscarPredecesorIterativo(K clave) throws DatoNoExisteExcepcion{
        if(NodoBinario.esNodoVacio(this.raiz)){
            throw new DatoNoExisteExcepcion("Error arbol vacio");
        }
       NodoBinario<K,V> nodoActual = this.raiz;
        while(!NodoBinario.esNodoVacio(nodoActual)){
            if(clave.compareTo(nodoActual.getClave())>0){
                nodoActual=nodoActual.getHijoIzquierdo();
            }
            else if(clave.compareTo(nodoActual.getClave())<0){
                nodoActual=nodoActual.getHijoDerecho();
            }else{
                if(nodoActual.esVacioHijoDerecho()){
                    throw new DatoNoExisteExcepcion("No tiene sucesor");
                }else{
                    NodoBinario<K,V> nodoDerecho=nodoActual.getHijoDerecho();
                    while(!NodoBinario.esNodoVacio(nodoDerecho.getHijoIzquierdo())){
                        nodoDerecho= nodoDerecho.getHijoIzquierdo();
                    }
                    return nodoDerecho;
                }
            }
        }
        throw new DatoNoExisteExcepcion("No se encontro resultados");
    }
   //7. Implemente un método iterativo que la lógica de un recorrido en postorden que 
    //retorne verdadero, si un árbol binario esta balanceado según las reglas que establece un árbol AVL, falso en caso contrario.  
    public  boolean verificarSiElArbolEstaBalanceadoIterativo(){
        Stack<NodoBinario<K,V>>pilaDeNodos = new Stack<>();
        NodoBinario<K,V> nodoActual = this.raiz;
        NodoBinario<K,V> nodoUltimo ;
        NodoBinario<K,V> nodoVisitado=null ;
        while(!NodoBinario.esNodoVacio(nodoActual) || !pilaDeNodos.isEmpty()){
            if(!NodoBinario.esNodoVacio(nodoActual)){
                pilaDeNodos.push(nodoActual);
                nodoActual = nodoActual.getHijoIzquierdo();
            }
            else{
                nodoUltimo = pilaDeNodos.peek();
                if(!NodoBinario.esNodoVacio(nodoUltimo.getHijoDerecho()) && nodoVisitado !=nodoUltimo.getHijoDerecho() ){
                    nodoActual = nodoUltimo.getHijoDerecho();
                }else{
                    int diferenciaAltura = this.altura(nodoUltimo.getHijoIzquierdo())-this.altura(nodoUltimo.getHijoDerecho());
                    if( !(diferenciaAltura >=-1 && diferenciaAltura<=1)){
                        return false;
                    }
                    nodoVisitado = pilaDeNodos.pop();
                }
            }
        }
        return true;
    }
    //Implemente un método que reciba en listas de parámetros las llaves y valores de los recorridos en preorden e inorden respectivamente
    //y que reconstruya el árbol binario original. Su método no debe usar el método insertar.
    public NodoBinario<K,V> reconstruirArbolBinario(List<K> clavePreorden,List<V> valorPreorde, List<K> claveInorden,List<V> valorInorden){
          
        return this.raiz;
    }

    @Override
    public List<K> recorridoPreOrden() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V eliminar(K clave) throws DatoNoExisteExcepcion  {
        V valor = this.buscar(clave);
        if(valor == null){
             throw new DatoNoExisteExcepcion();
        }else{
            this.raiz = this.eliminar(this.raiz,clave);
            return valor;
        }
        
    }
    public void printBinaryTree(){
        printBinaryTree(raiz, 0);
    }
    private  void printBinaryTree(NodoBinario<K,V> nodo, int level){
        if(nodo==null) 
            return; 
        printBinaryTree(nodo.getHijoDerecho(), level+1); 
        if(level!=0){ 
            for(int i=0;i<level-1;i++)
                System.out.print("|\t");
        System.out.println("|--------"+"("+nodo.getClave()+","+nodo.getValor()+")"); 
        } else System.out.println("("+nodo.getClave()+","+nodo.getValor()+")"); 
        printBinaryTree(nodo.getHijoIzquierdo(), level+1); 
    }

    private NodoBinario<K,V> eliminar(NodoBinario<K, V> nodoActual, K clave)throws DatoNoExisteExcepcion {
        if(NodoBinario.esNodoVacio(raiz)){
           throw new DatoNoExisteExcepcion("Error el dato no existe");
        }
        K claveActual = nodoActual.getClave();
        
        if(clave.compareTo(claveActual)>0){
            NodoBinario<K,V> supuestoNodoDerecho = eliminar(nodoActual.getHijoDerecho(),clave);
            nodoActual.setHijoDerecho(supuestoNodoDerecho);
            return nodoActual;
        }else if(clave.compareTo(claveActual)<0){
            NodoBinario<K,V> supuestoNodoIzquierdo = eliminar(nodoActual.getHijoIzquierdo(),clave);
            nodoActual.setHijoIzquierdo(supuestoNodoIzquierdo);
            return nodoActual;
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

    private NodoBinario<K, V> buscarPredecedorSucesor(NodoBinario<K, V> nodoActual) {
        if(NodoBinario.esNodoVacio(nodoActual.getHijoIzquierdo())){
            return nodoActual;
        }
        
        return nodoActual.getHijoIzquierdo();
    }

    
    public void ReconstruirArbolBinario(List<K> listaClaveInorden,List<V> listaValorInorden,List<K> listaClavePreorden,List<V> listaValorPreorden){
        int cantidadDeNodos = listaClaveInorden.size();
        K claveInorden = listaClaveInorden.get(0); listaClaveInorden.remove(0);
        int indiceClaveDeLaRaiz = listaClavePreorden.indexOf(claveInorden);
        
        List<K> subListaIzquierda = new LinkedList<>();  
        subListaIzquierda.addAll( listaClavePreorden.subList(0,indiceClaveDeLaRaiz)); 
        boolean eliminar=listaClavePreorden.removeAll(subListaIzquierda);
        int cantidadIzquierda =subListaIzquierda.size();
        
        int cantidadDeNodosDerecha = listaClavePreorden.size()-1;
        List<K> subListaDerecha = new LinkedList<>();  
         subListaDerecha.addAll(listaClavePreorden.subList(1,cantidadDeNodosDerecha)); 
        boolean eliminaer=listaClavePreorden.removeAll(subListaDerecha);
        
        
        
        
    
    }
    
    public int cantidadDeHijos(){
        return cantidadDeHijosRecursivo(this.raiz);
    }
    private int cantidadDeHijosRecursivo(NodoBinario<K,V> nodoActual){
        if(NodoBinario.esNodoVacio(nodoActual)){
            return 1;
        }
        int cantidadIzquierda = cantidadDeHijosRecursivo(nodoActual.getHijoIzquierdo());
        int cantidadDerecha = cantidadDeHijosRecursivo(nodoActual.getHijoDerecho());
        
        
        return cantidadIzquierda + cantidadDerecha ;
    }
    
    public int examen(int altura1 , int altura2){
        return examenRecursivo(this.raiz, altura1,altura2,1,1);
    }

    private int examenRecursivo(NodoBinario<K, V> nodoActual, int altura1, int altura2,int altura1Actual,int altura2Actual) {
        if(NodoBinario.esNodoVacio(nodoActual)){
            return 0;
        }
        int cantidadIzq = examenRecursivo(nodoActual.getHijoIzquierdo(),altura1,altura2,altura1Actual+1,altura2Actual+1);
        int cantidadDer = examenRecursivo(nodoActual.getHijoDerecho(),altura1,altura2,altura1Actual+1,altura2Actual+1);
        if(!nodoActual.esVacioHijoDerecho() && !nodoActual.esVacioHijoIzquierdo() 
                && altura1Actual >=altura1 && altura2Actual<=altura2 ){
            return cantidadDer+ cantidadIzq+1;
        }
        return cantidadIzq + cantidadDer;
    }
    
    public int examenIterativo(int nivel1 , int nivel2){
        if(NodoBinario.esNodoVacio(this.raiz)){
            return 0;
        }
        Stack<NodoBinario<K,V>> pilaDeNodos = new Stack<>();
        Stack<Integer> pilaDeAlturas= new Stack<>();
        pilaDeNodos.push(this.raiz);
        pilaDeAlturas.push(1);
        NodoBinario<K,V> nodoActual = this.raiz;
        int alturaActual = 1;
        while(!nodoActual.esVacioHijoIzquierdo()){
            nodoActual = nodoActual.getHijoIzquierdo();
            alturaActual = alturaActual + 1;
            pilaDeAlturas.push(alturaActual);
            pilaDeNodos.push(nodoActual);
        }
        int cantidad = 0 ;
        while(!pilaDeNodos.isEmpty()){
            nodoActual = pilaDeNodos.pop();
            int nivelActual = pilaDeAlturas.pop();
            if(! nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho() && nivelActual>= nivel1 && nivelActual <=nivel2){
                cantidad = cantidad +1;
            }
            if(!nodoActual.esVacioHijoDerecho()){
                NodoBinario<K,V> nodoDerecho = nodoActual.getHijoDerecho();
                nivelActual = nivelActual + 1 ;
                pilaDeAlturas.push(nivelActual);
                pilaDeNodos.push(nodoDerecho);
                while(!NodoBinario.esNodoVacio(nodoDerecho.getHijoIzquierdo())){
                    nodoDerecho = nodoDerecho.getHijoIzquierdo();
                    pilaDeNodos.push(nodoDerecho);
                    nivelActual = nivelActual + 1 ;
                    pilaDeAlturas.push(nivelActual);
                }
            }
        }
        return cantidad;
    }
    
    public int examenNervioso(){
        return examenNerviosoRecursivo(this.raiz);
    }

    private int examenNerviosoRecursivo(NodoBinario<K, V> nodoActual) {
        if(NodoBinario.esNodoVacio(nodoActual)){
            return 0 ;
        }
        int cantidad = 0;
        if(nodoActual.esHoja()){
            cantidad = 2;
        }
        if((!nodoActual.esVacioHijoDerecho() && nodoActual.esVacioHijoIzquierdo()) || (nodoActual.esVacioHijoDerecho() && !nodoActual.esVacioHijoIzquierdo()) ){
            cantidad = 1;
        }
        cantidad = cantidad+ examenNerviosoRecursivo(nodoActual.getHijoIzquierdo());
        cantidad =cantidad+ examenNerviosoRecursivo(nodoActual.getHijoDerecho());
        

        return cantidad;
    }
    
    public int iterativo(){
        if(NodoBinario.esNodoVacio(this.raiz)){
            return 0;
        }
        int cantidad = 0 ;
        Stack<NodoBinario<K,V>> pilaDeNodos = new Stack<>();
        NodoBinario<K,V> nodoActual = this.raiz;
        pilaDeNodos.push(nodoActual);
        while(! pilaDeNodos.isEmpty()){
            nodoActual = pilaDeNodos.pop();
            if(nodoActual.esHoja()){
                cantidad = cantidad + 1 ;
            }
            if(!nodoActual.esVacioHijoDerecho()){
                pilaDeNodos.push(nodoActual.getHijoDerecho());
            }
            if(!nodoActual.esVacioHijoIzquierdo()){
                pilaDeNodos.push(nodoActual.getHijoIzquierdo());
            }
        }
        return cantidad;
    }
    
  //Pregunta 1 
    public boolean esMonticulo(){
        List<K> listakeys = new LinkedList<>();
        boolean esMonticulo = true;
        if(this.esArbolVacio()){
            return esMonticulo;
        }
        Stack<NodoBinario<K,V>> pilaNodos = new Stack<>();
        pilaNodos.push(this.raiz);
        
        while(!pilaNodos.isEmpty()){
            NodoBinario<K,V> nodoActual= pilaNodos.pop();
            K claveDerecha = nodoActual.getHijoDerecho().getClave();
            K claveIzquierda = nodoActual.getHijoIzquierdo().getClave();
            K clave = nodoActual.getClave();
            listakeys.add(nodoActual.getClave());
            if(!(clave.compareTo(claveDerecha) < 0 && clave.compareTo(claveIzquierda) < 0)){
                esMonticulo = false;
                return esMonticulo;
            }
            if(!nodoActual.esVacioHijoDerecho()){
                pilaNodos.push(nodoActual.getHijoDerecho());
            }
            if(!nodoActual.esVacioHijoIzquierdo()){
                pilaNodos.push(nodoActual.getHijoIzquierdo());
            }
        }
        return esMonticulo;
    }
}   



















