package com.Entidades;
import java.util.function.Consumer;

import com.example.Caja_de_Herramientas.Lista.TDALista;
//src\main\java\com\example\Enums\EstadoProducto.java
import com.example.Enums.EstadoProducto;
import com.example.Enums.Moneda;


public interface IProducto //Dice el que se debe hacer no el como!
{

    // Necesario para poder BUSCAR un producto puntual dentro de una lista mezclada de productos.
    String getId();
 
    //getEstado() permite leer si el estado del producto es (ACTIVO, VENCIDO, CANCELADO)
    EstadoProducto getEstado();
 
    // modificar el estado puntual de un producto utilizando los ENUMS ya dados (ACTIVO, VENCIDO, CANCELADO)
    void modificarEstado(EstadoProducto nuevoEstado);
 
    // para ver si un producto de un determinado cliente puede estar vencido o no
    boolean estaVencido();

    // árbol general n-ario
    // A partir de acá, todo IProducto puede tener cero o más "componentes" (hijos), y esos componentes pueden a su vez tener los suyos, sin límite de profundidad

    // Agrega un componente que vendriaa ser el hijo a este producto. Si el producto no tenía componentes todavía, a partir de este llamado deja de ser una hoja.
    void agregarComponente(IProducto componente);

    // Devuelve los componentes directos (hijos nomas). Si el producto es una hoja, devuelve una lista vacía.
    TDALista<IProducto> obtenerComponentes();

    // Indica si este producto es una hoja del árbol de composición
    boolean esHoja();

    // Recorre este producto y, recursivamente, todos sus componentes (sin límite de profundidad).
    void recorrer(Consumer<IProducto> accion);

    Moneda getMoneda();

    float getPrecioAPagar();

    boolean quitarComponente(String id);
    
    // Busca, entre este producto y todos sus componentes la Cuenta con mayor saldo. 
    //el default es simplemente para poder agregar un metodo dentro de la interfaz en vez de dejar declarado el metodo
    default Cuenta obtenerComponenteDeMayorSaldo()
    {

        // Array de tamaño 1 usado como variable mutable dentro de la lambda.
        // La lambda no puede modificar directamente una variable local capturada,
        // pero sí puede modificar el contenido de un array, ya que la referencia
        // al array permanece efectivamente final.

        Cuenta[] mejorCuenta = new Cuenta[1];

        recorrer(producto -> {
            if (producto instanceof Cuenta cuenta
                    && (mejorCuenta[0] == null || cuenta.getSaldo() > mejorCuenta[0].getSaldo()))
            {
                mejorCuenta[0] = cuenta;
            }
        });

        return mejorCuenta[0];
    }
}