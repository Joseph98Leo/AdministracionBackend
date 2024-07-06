package com.royalpaws.controller;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.royalpaws.model.Cliente;
import com.royalpaws.model.Compra;
import com.royalpaws.model.Detalle;
import com.royalpaws.model.Producto;
import com.royalpaws.model.Vendedor;
import com.royalpaws.service.ClienteService;
import com.royalpaws.service.CompraService;
import com.royalpaws.service.ProductoService;
import com.royalpaws.service.VendedorService;
import jakarta.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Controller
@RequestMapping("/api/compras")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private VendedorService vendedorService;

    @GetMapping("/listar")
    public ResponseEntity<List<Compra>> listarCompras() {
        List<Compra> compras = compraService.listarCompras();
        return ResponseEntity.ok().body(compras);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Compra> buscarCompra(@PathVariable("id") Integer id) {
        Compra compra = compraService.buscar(id);
        if (compra != null) {
            return ResponseEntity.ok().body(compra);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarCompra(@PathVariable("id") Integer id) {
        try {
            compraService.eliminar(id);
            return ResponseEntity.ok().body("Compra eliminada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar la compra: " + e.getMessage());
        }
    }

    @PostMapping("/crear")
    public ResponseEntity<String> crearCompra(@Valid @RequestBody Compra compraRequest) {
        try {
            // Validar que el código de vendedor no sea nulo
            if (compraRequest.getCompraVendedor() == null || compraRequest.getCompraVendedor().getCodVen() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El código de vendedor no puede ser nulo");
            }

            // Obtener el cliente y el vendedor desde la solicitud
            Cliente cliente = clienteService.buscarPorId(compraRequest.getCompraCliente().getCodCli());
            if (cliente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado");
            }

            Vendedor vendedor = vendedorService.listById(compraRequest.getCompraVendedor().getCodVen());
            if (vendedor == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vendedor no encontrado");
            }

            // Crear la compra y establecer relaciones
            Compra compra = new Compra();
            compra.setFecCom(compraRequest.getFecCom());
            compra.setMontoCom(compraRequest.getMontoCom());
            compra.setMpago(compraRequest.getMpago());
            compra.setCompraCliente(cliente);
            compra.setCompraVendedor(vendedor);

            // Guardar la compra
            compraService.registrarCompra(compra);

            return ResponseEntity.status(HttpStatus.CREATED).body("Compra registrada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar la compra: " + e.getMessage());
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizarCompra(@PathVariable("id") Integer id, @Valid @RequestBody Compra compra) {
        compra.setCodCom(id);
        try {
            compraService.actualizar(compra);
            return ResponseEntity.ok().body("Compra actualizada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar la compra: " + e.getMessage());
        }
    }

    @GetMapping("/productos")
    public ResponseEntity<List<Producto>> listarProductos() {
        List<Producto> productos = productoService.listarProductos();
        return ResponseEntity.ok().body(productos);
    }

    @GetMapping("/clientes")
    public ResponseEntity<List<Cliente>> listarClientes(@RequestParam("apellido") String apellido) {
        List<Cliente> clientes = clienteService.listarClienteXApellido(apellido + "%");
        return ResponseEntity.ok().body(clientes);
    }

    @GetMapping("/vendedores")
    public ResponseEntity<List<Vendedor>> listarVendedores(@RequestParam("apellido") String apellido) {
        List<Vendedor> vendedores = vendedorService.listarVendedorXApellido(apellido + "%");
        return ResponseEntity.ok().body(vendedores);
    }

    @PostMapping("/agregarDetalle")
    @ResponseBody
    public List<Detalle> agregarDetalle(@RequestParam("codigo") int codigo, @RequestParam("nombre") String nombre,
            @RequestParam("cantidad") int cantidad, @RequestParam("precio") BigDecimal precio,
            HttpSession session) {
        List<Detalle> carrito = obtenerCarrito(session);

        for (Detalle detalle : carrito) {
            if (detalle.getCodigo() == codigo) {
                detalle.setCantidad(detalle.getCantidad() + cantidad);
                return carrito;
            }
        }

        Detalle nuevoDetalle = new Detalle();
        nuevoDetalle.setCodigo(codigo);
        nuevoDetalle.setNombre(nombre);
        nuevoDetalle.setCantidad(cantidad);
        nuevoDetalle.setPrecio(precio);

        carrito.add(nuevoDetalle);
        actualizarCarrito(session, carrito);

        return carrito;
    }

    @DeleteMapping("/eliminarDetalle")
    @ResponseBody
    public List<Detalle> eliminarDetalle(@RequestParam("codigo") int codigo, HttpSession session) {
        List<Detalle> carrito = obtenerCarrito(session);

        for (Detalle detalle : carrito) {
            if (detalle.getCodigo() == codigo) {
                carrito.remove(detalle);
                actualizarCarrito(session, carrito);
                break;
            }
        }

        return carrito;
    }

    @SuppressWarnings("unchecked")
    private List<Detalle> obtenerCarrito(HttpSession session) {
        return session.getAttribute("carrito") == null ? new ArrayList<>() : (List<Detalle>) session.getAttribute("carrito");
    }

    private void actualizarCarrito(HttpSession session, List<Detalle> carrito) {
        session.setAttribute("carrito", carrito);
        session.setAttribute("cantidadRegistros", carrito.size());
    }

    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private ResourceLoader resourceLoader;
    
    @GetMapping("/generarReporte")
    public ResponseEntity<byte[]> generarReporte() {
        try {
            // Cargar la plantilla del reporte
            String rutaReporte = resourceLoader.getResource("classpath:reportes/Boleta.jasper").getURI().getPath();
            InputStream reportStream = getClass().getResourceAsStream("/reportes/Boleta.jasper");

            // Parámetros opcionales para el reporte (en este caso no se usan)
            Map<String, Object> parameters = new HashMap<>();

            // Crear el JasperPrint llenando el reporte con los datos
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parameters, dataSource.getConnection());

            // Exportar el JasperPrint a bytes en formato PDF
            byte[] pdfReport = JasperExportManager.exportReportToPdf(jasperPrint);

            // Configurar los headers de la respuesta para indicar que es un PDF
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("inline", "reporte.pdf");

            // Devolver la respuesta con el PDF generado
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfReport);
        } catch (Exception e) {
            e.printStackTrace();
            // Manejar cualquier error y devolver un mensaje de error al cliente
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

