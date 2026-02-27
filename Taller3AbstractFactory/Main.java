interface ReciboPago {
    void imprimir(String nombreCliente, double total);
}

interface EtiquetaPedido {
    void imprimir(String nombreCliente, String[] items);
}

class ReciboEstudiante implements ReciboPago {
    @Override
    public void imprimir(String nombreCliente, double total) {
        double descuento = total * 0.10;
        double totalFinal = total - descuento;
        System.out.println("------ RECIBO ESTUDIANTE ------");
        System.out.println("Nombre  : " + nombreCliente);
        System.out.println("Pago via: Carnet universitario");
        System.out.println("Subtotal: $" + String.format("%.2f", total));
        System.out.println("Descuento 10%: -$" + String.format("%.2f", descuento));
        System.out.println("TOTAL   : $" + String.format("%.2f", totalFinal));
        System.out.println("-------------------------------\n");
    }
}

class EtiquetaEstudiante implements EtiquetaPedido {
    @Override
    public void imprimir(String nombreCliente, String[] items) {
        System.out.println("=== PEDIDO [ESTUDIANTE] ===");
        System.out.println("Para: " + nombreCliente);
        for (String item : items) {
            System.out.println("  - " + item);
        }
        System.out.println("Zona: Comedor estudiantil");
        System.out.println("==========================\n");
    }
}

class ReciboDocente implements ReciboPago {
    @Override
    public void imprimir(String nombreCliente, double total) {
        System.out.println("------ RECIBO DOCENTE ------");
        System.out.println("Nombre  : " + nombreCliente);
        System.out.println("TOTAL   : $" + String.format("%.2f", total));
        System.out.println("----------------------------\n");
    }
}

class EtiquetaDocente implements EtiquetaPedido {
    @Override
    public void imprimir(String nombreCliente, String[] items) {
        System.out.println("=== PEDIDO [DOCENTE] ===");
        System.out.println("Para: " + nombreCliente);
        for (String item : items) {
            System.out.println("  - " + item);
        }
        System.out.println("Zona: Sala de profesores");
        System.out.println("========================\n");
    }
}

interface FabricaCafeteria {
    ReciboPago crearRecibo();
    EtiquetaPedido crearEtiqueta();
}

class FabricaEstudiante implements FabricaCafeteria {
    @Override
    public ReciboPago crearRecibo() {
        return new ReciboEstudiante();
    }
    @Override
    public EtiquetaPedido crearEtiqueta() {
        return new EtiquetaEstudiante();
    }
}

class FabricaDocente implements FabricaCafeteria {
    @Override
    public ReciboPago crearRecibo() {
        return new ReciboDocente();
    }
    @Override
    public EtiquetaPedido crearEtiqueta() {
        return new EtiquetaDocente();
    }
}

class CajaRegistradora {
    private FabricaCafeteria fabrica;

    public CajaRegistradora(FabricaCafeteria fabrica) {
        this.fabrica = fabrica;
    }

    public void procesarVenta(String nombre, String[] items, double total) {
        EtiquetaPedido etiqueta = fabrica.crearEtiqueta();
        etiqueta.imprimir(nombre, items);

        ReciboPago recibo = fabrica.crearRecibo();
        recibo.imprimir(nombre, total);
    }
}

public class Main {
    public static void main(String[] args) {

        String[] pedidoEstudiante = {"Almuerzo del dia", "Jugo de naranja"};
        String[] pedidoDocente    = {"Bandeja ejecutiva", "Cafe americano", "Postre"};

        System.out.println("======================================");
        System.out.println("  CAFETERIA UNIVERSIDAD");
        System.out.println("======================================\n");

        CajaRegistradora caja1 = new CajaRegistradora(new FabricaEstudiante());
        caja1.procesarVenta("Laura Ospina", pedidoEstudiante, 12500);

        CajaRegistradora caja2 = new CajaRegistradora(new FabricaDocente());
        caja2.procesarVenta("Prof. Andres Mideros", pedidoDocente, 22000);

        System.out.println("======================================");
        System.out.println("     Ventas procesadas correctamente. ");
        System.out.println("======================================");
    }
}