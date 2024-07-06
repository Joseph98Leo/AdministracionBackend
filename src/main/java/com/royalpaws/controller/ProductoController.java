package com.royalpaws.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.royalpaws.model.Marca;
import com.royalpaws.model.Producto;
import com.royalpaws.model.Proveedor;
import com.royalpaws.service.MarcaService;
import com.royalpaws.service.ProductoService;
import com.royalpaws.service.ProveedorService;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private MarcaService marcaService;

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping("/lista")
    public ResponseEntity<List<Producto>> listarProductos() {
        List<Producto> productos = productoService.listarProductos();
        return ResponseEntity.ok().body(productos);
    }

    @PostMapping("/grabar")
    public ResponseEntity<String> grabarProducto(@RequestBody Producto request, RedirectAttributes redirect) {
        try {
            Producto producto = new Producto();
            producto.setCodProd(request.getCodProd());
            producto.setNomProd(request.getNomProd());
            producto.setDesProd(request.getDesProd());
            producto.setCatProd(request.getCatProd());
            producto.setStockProd(request.getStockProd());
            producto.setPrecioCompra(request.getPrecioCompra());

            // Crear el objeto Marca
            Marca marca = new Marca();
            marca.setCodMarca(request.getProductoMarca().getCodMarca());
            marca.setNomMarca(request.getProductoMarca().getNomMarca());
            marca.setPaiMarca(request.getProductoMarca().getPaiMarca());

            // Crear el objeto Proveedor
            Proveedor proveedor = new Proveedor();
            proveedor.setCodProv(request.getProductoProveedor().getCodProv());
            proveedor.setNomProv(request.getProductoProveedor().getNomProv());

            // Asignar marca y proveedor al producto
            producto.setProductoMarca(marca);
            producto.setProductoProveedor(proveedor);

            productoService.grabar(producto);

            if (request.getCodProd() > 0) {
                redirect.addFlashAttribute("mensaje",
                        "Producto " + producto.getNomProd().toUpperCase() + " se actualizó correctamente.");
            } else {
                redirect.addFlashAttribute("mensaje",
                        "Producto " + producto.getNomProd().toUpperCase() + " se registró correctamente.");
            }
            return ResponseEntity.ok().body("Producto registrado/actualizado correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al intentar guardar el producto.");
        }
    }
    

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Producto> buscarProducto(@PathVariable("id") int cod) {
        Producto producto = productoService.buscar(cod);
        if (producto != null) {
            return ResponseEntity.ok().body(producto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable("id") int cod, RedirectAttributes redirect) {
        try {
            productoService.eliminar(cod);
            redirect.addFlashAttribute("mensaje", "Producto con código: " + cod + " eliminado.");
            return ResponseEntity.ok().body("Producto eliminado correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al intentar eliminar el producto.");
        }
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<Producto> obtenerDetalleProducto(@PathVariable("id") int id) {
        Producto producto = productoService.listById(id);
        if (producto != null) {
            return ResponseEntity.ok().body(producto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/imagen-producto")
    public ResponseEntity<String> subirImagenProducto(@RequestParam("dataArchivo") MultipartFile archivo,
            @RequestParam("txtCodigo") Integer cod, RedirectAttributes redirect) throws IOException {

        try {
            byte[] bytes = archivo.getBytes();
            String nombreArchivo = archivo.getOriginalFilename();

            // Ruta donde se almacenará el archivo (ajusta según tus necesidades)
            String ruta = "C:\\Users\\PC\\Desktop\\Jose Luis\\04-Estudios\\Cibertec\\06 Ciclo\\Desarrollo de aplicaciones Web II\\PROYECTO\\Royal_Paws - Final3\\Royal_Paws - Ready\\src\\main\\resources\\static\\resources\\img";
            Files.write(Paths.get(ruta + nombreArchivo), bytes);

            // Actualizar imagen en el producto
            productoService.actualizarImg(bytes, nombreArchivo, cod);

            redirect.addFlashAttribute("mensaje", "Imagen " + nombreArchivo.toUpperCase() + " se actualizó correctamente.");
            return ResponseEntity.ok().body("Imagen actualizada correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al intentar subir la imagen.");
        }
    }

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping("/reportes")
    public void generarReporte(HttpServletResponse response) {
        response.setHeader("Content-Disposition", "inline;");
        response.setContentType("application/pdf");
        try {
            String rutaReporte = resourceLoader.getResource("classpath:reportes/ReporteProducto.jasper").getURI()
                    .getPath();
            JasperPrint jasperPrint = JasperFillManager.fillReport(rutaReporte, null, dataSource.getConnection());
            OutputStream outStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}