package com.royalpaws.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.royalpaws.model.Vendedor;
import com.royalpaws.service.VendedorService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;


@RestController
@RequestMapping("/api/vendedores")
public class VendedorController {
    
    @Autowired
    private VendedorService vendedorService;
    
    @GetMapping("/lista")
    public ResponseEntity<List<Vendedor>> listarVendedores() {
        List<Vendedor> vendedores = vendedorService.listarVendedores();
        return ResponseEntity.ok(vendedores);
    }
    
    @PostMapping("/guardar")
    public ResponseEntity<?> guardarVendedor(@Valid @RequestBody Vendedor vendedor, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error en los datos de entrada");
        }
        
        vendedorService.save(vendedor);
        return ResponseEntity.status(HttpStatus.CREATED).body("Vendedor guardado correctamente");
    }
    
    @GetMapping("/lista/{id}")
    public ResponseEntity<Vendedor> obtenerVendedorPorId(@PathVariable("id") int id) {
        Vendedor vendedor = vendedorService.listById(id);
        if (vendedor != null) {
            return ResponseEntity.ok(vendedor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarVendedor(@PathVariable("id") int id, @Valid @RequestBody Vendedor vendedor,
            BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error en los datos de entrada");
        }
        
        Vendedor vendedorExistente = vendedorService.listById(id);
        if (vendedorExistente != null) {
            vendedor.setCodVen(id); 
            vendedorService.save(vendedor);
            return ResponseEntity.ok("Vendedor actualizado correctamente");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarVendedor(@PathVariable("id") int id) {
        Vendedor vendedor = vendedorService.listById(id);
        if (vendedor != null) {
            vendedorService.eliminarByID(id);
            return ResponseEntity.ok("Vendedor eliminado correctamente");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/buscar")
    public ResponseEntity<List<Vendedor>> buscarVendedorPorApellido(@RequestParam("apellido") String apellido) {
        try {
            List<Vendedor> vendedores = vendedorService.listarVendedorXApellido(apellido);
            return ResponseEntity.ok(vendedores);
        } catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); 
        }
    }

    @Autowired
    private ResourceLoader resourceLoader;


    @GetMapping("/reporte")
    public void generarReporte(HttpServletResponse response) {
        try {
            // Configuración del response y carga del archivo Jasper
            response.setContentType(MediaType.APPLICATION_PDF_VALUE);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_vendedores.pdf");
            InputStream inputStream = this.getClass().getResourceAsStream("/reportes/ReporteVendedor.jasper");

            // Obtener los datos para el reporte
            List<Vendedor> vendedores = vendedorService.listarVendedores();

            // Llenar el reporte con datos y generar JasperPrint
            JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, null, new JRBeanCollectionDataSource(vendedores));

            // Exportar a PDF y escribir en el OutputStream de la respuesta
            OutputStream outputStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

            // Cerrar el flujo de salida
            outputStream.flush();
            outputStream.close();
        } catch (JRException | IOException e) {
            e.printStackTrace(); 
        }
    }
}
