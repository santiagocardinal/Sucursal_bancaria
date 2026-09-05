package com.example.Caja_de_Herramientas.Arboles;
import java.util.function.Consumer;
import com.example.Caja_de_Herramientas.Lista.*;

// FIX: sacado "implements Comparable<T>" de la clase. No hace falta que el
// NODO sea comparable: lo que se compara es this.dato contra los
// Comparable<T> que ya reciben insertar/buscar/eliminar/obtenerNivel. Ese
// implements obligaba a un compareTo(T) que solo tiraba excepción si
// alguna vez se llamaba, y encima chocaba con los stubs T que traía la
// interfaz vieja (insertar(T)/buscar(T)/obtenerNivel(T)), que ya no existen
// en TDAElemento porque esa interfaz volvió a ser solo para árbol binario.
public class ElementoABBImpl<T> implements TDAElemento<T> {
    private T dato;
    private TDAElemento<T> hijoIzq;
    private TDAElemento<T> hijoDer;

    public ElementoABBImpl(T dato) {
        this.dato = dato;
        this.hijoIzq = null;
        this.hijoDer = null;
    }

    // ─── Getters y Setters ───────────────────────────────────────────

    @Override
    public void setHijoIzquierdo(TDAElemento<T> hijoIzquierdo) {
        this.hijoIzq = hijoIzquierdo;
    }

    @Override
    public void setHijoDerecho(TDAElemento<T> hijoDerecho) {
        this.hijoDer = hijoDerecho;
    }

    @Override
    public TDAElemento<T> getHijoIzquierdo() {
        return this.hijoIzq;
    }

    @Override
    public TDAElemento<T> getHijoDerecho() {
        return this.hijoDer;
    }

    @Override
    public void setDato(T dato) {
        this.dato = dato;
    }

    @Override
    public T getDato() {
        return this.dato;
    }

    // ─── Buscar ──────────────────────────────────────────────────────

    @Override
    public TDAElemento<T> buscar(Comparable<T> criterioBusqueda) {
        TDAElemento<T> resultado = null;

        if (criterioBusqueda.compareTo(this.dato) == 0) {
            resultado = this;
        } else {
            if (criterioBusqueda.compareTo(this.dato) < 0) {
                if (this.hijoIzq != null) {
                    resultado = hijoIzq.buscar(criterioBusqueda);
                }
            } else {
                if (this.hijoDer != null) {
                    resultado = hijoDer.buscar(criterioBusqueda);
                }
            }
        }
        return resultado;
    }

    // ─── Insertar ────────────────────────────────────────────────────

    @Override
    @SuppressWarnings("unchecked")
    public boolean insertar(Comparable<T> nuevoDato) {
        if (nuevoDato.compareTo(this.dato) > 0) {
            if (hijoDer == null) {
                hijoDer = new ElementoABBImpl<>((T) nuevoDato);
                return true;
            } else {
                return hijoDer.insertar(nuevoDato);
            }
        } else if (nuevoDato.compareTo(this.dato) < 0) {
            if (hijoIzq == null) {
                hijoIzq = new ElementoABBImpl<>((T) nuevoDato);
                return true;
            } else {
                return hijoIzq.insertar(nuevoDato);
            }
        }
        return false; // ya existe
    }

    // ─── Eliminar ────────────────────────────────────────────────────

    @Override
    public TDAElemento<T> eliminar(Comparable<T> criterio) {
        if (criterio.compareTo(this.dato) < 0) {
            if (this.hijoIzq != null) {
                this.hijoIzq = this.hijoIzq.eliminar(criterio);
            }
            return this;
        } else if (criterio.compareTo(this.dato) > 0) {
            if (this.hijoDer != null) {
                this.hijoDer = this.hijoDer.eliminar(criterio);
            }
            return this;
        }
        return quitarNodo();
    }

    private TDAElemento<T> quitarNodo() {
        if (this.hijoIzq == null) return this.hijoDer;
        else if (this.hijoDer == null) return this.hijoIzq;
        else {
            TDAElemento<T> elHijo = this.hijoIzq;
            TDAElemento<T> elPadre = this;

            while (elHijo.getHijoDerecho() != null) {
                elPadre = elHijo;
                elHijo = elHijo.getHijoDerecho();
            }

            if (elPadre != this) {
                elPadre.setHijoDerecho(elHijo.getHijoIzquierdo());
                elHijo.setHijoIzquierdo(this.hijoIzq);
            }

            elHijo.setHijoDerecho(this.hijoDer);
            return elHijo;
        }
    }

    // ─── Recorridos ──────────────────────────────────────────────────

    @Override
    public void inOrder(Consumer<TDAElemento<T>> consumidor) {
        if (this.hijoIzq != null) this.hijoIzq.inOrder(consumidor);
        consumidor.accept(this);
        if (this.hijoDer != null) this.hijoDer.inOrder(consumidor);
    }

    @Override
    public void preOrder(Consumer<TDAElemento<T>> consumidor) {
        consumidor.accept(this);
        if (this.hijoIzq != null) this.hijoIzq.preOrder(consumidor);
        if (this.hijoDer != null) this.hijoDer.preOrder(consumidor);
    }

    @Override
    public void postOrder(Consumer<TDAElemento<T>> consumidor) {
        if (this.hijoIzq != null) this.hijoIzq.postOrder(consumidor);
        if (this.hijoDer != null) this.hijoDer.postOrder(consumidor);
        consumidor.accept(this);
    }

    // ─── Utilidades ──────────────────────────────────────────────────

    @Override
    public boolean esHoja() {
        return this.hijoIzq == null && this.hijoDer == null;
    }

    @Override
    public int cantidadHojas() {
        if (esHoja()) return 1;
        int hojas = 0;
        if (this.hijoIzq != null) hojas += this.hijoIzq.cantidadHojas();
        if (this.hijoDer != null) hojas += this.hijoDer.cantidadHojas();
        return hojas;
    }

    @Override
    public int cantidadNodosInternos() {
        if (esHoja()) return 0;
        int internos = 1;
        if (this.hijoIzq != null) internos += this.hijoIzq.cantidadNodosInternos();
        if (this.hijoDer != null) internos += this.hijoDer.cantidadNodosInternos();
        return internos;
    }

    @Override
    public int cantidadNodos() {
        int nodos = 1;
        if (this.hijoIzq != null) nodos += this.hijoIzq.cantidadNodos();
        if (this.hijoDer != null) nodos += this.hijoDer.cantidadNodos();
        return nodos;
    }

    @Override
    public int altura() {
        int altIzq = (this.hijoIzq != null) ? this.hijoIzq.altura() : 0;
        int altDer = (this.hijoDer != null) ? this.hijoDer.altura() : 0;
        return 1 + Math.max(altIzq, altDer);
    }

    @Override
    public int obtenerNivel(Comparable<T> criterioBusqueda) {
        if (criterioBusqueda.compareTo(this.dato) == 0) return 0;

        if (criterioBusqueda.compareTo(this.dato) < 0) {
            if (this.hijoIzq == null) return -1;
            int nivel = this.hijoIzq.obtenerNivel(criterioBusqueda);
            return (nivel == -1) ? -1 : nivel + 1;
        } else {
            if (this.hijoDer == null) return -1;
            int nivel = this.hijoDer.obtenerNivel(criterioBusqueda);
            return (nivel == -1) ? -1 : nivel + 1;
        }
    }

    @Override
    public void completos(TDALista<TDAElemento<T>> lista) {
        // si tiene ambos hijos → es completo
        if (hijoIzq != null && hijoDer != null) {
            lista.agregar(this);
        }

        if (hijoIzq != null) {
            hijoIzq.completos(lista);
        }

        if (hijoDer != null) {
            hijoDer.completos(lista);
        }
    }

    @Override
    public void enNivel(int nivel, TDALista<TDAElemento<T>> lista) {
        if (nivel == 0) {
            lista.agregar(this);
            return;
        }

        if (hijoIzq != null) {
            hijoIzq.enNivel(nivel - 1, lista);
        }

        if (hijoDer != null) {
            hijoDer.enNivel(nivel - 1, lista);
        }
    }
}