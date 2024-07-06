package com.royalpaws.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.royalpaws.model.Proveedor;
import com.royalpaws.service.ProveedorService;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@RestController
@RequestMapping("/api/proveedores")
@CrossOrigin(origins = "http://localhost:4200")
public class ProveedorController {
    
    @Autowired
    private ProveedorService proveedorService;
    
    @GetMapping("/lista")
    public ResponseEntity<List<Proveedor>> listarProveedores() {
        List<Proveedor> proveedores = proveedorService.listarProveedores();
        return new ResponseEntity<>(proveedores, HttpStatus.OK);
    }
    
    @PostMapping("/grabar")
    public ResponseEntity<String> grabarProveedor(@RequestBody Proveedor proveedor) {
        proveedorService.grabar(proveedor);
        return ResponseEntity.ok("Proveedor registrado exitosamente");
    }
    
    @GetMapping("/buscar/{id}")
    public ResponseEntity<Proveedor> buscarProveedor(@PathVariable("id") Integer id) {
        Proveedor proveedor = proveedorService.buscar(id);
        if (proveedor != null) {
            return new ResponseEntity<>(proveedor, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizarProveedor(@PathVariable("id") Integer id, @RequestBody Proveedor proveedor) {
        Proveedor proveedorExistente = proveedorService.buscar(id);
        if (proveedorExistente != null) {
            proveedor.setCodProv(id);
            proveedorService.grabar(proveedor);
            return ResponseEntity.ok("Proveedor actualizado correctamente");
        } else {
            return new ResponseEntity<>("Proveedor no encontrado", HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarProveedor(@PathVariable("id") Integer id) {
        try {
            proveedorService.eliminar(id);
            return ResponseEntity.ok("Proveedor eliminado correctamente");
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar proveedor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private ResourceLoader resourceLoader;
    
    @GetMapping("/reportes")
    public void generarReporte(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_proveedores.pdf");
        
        String reportePath = resourceLoader.getResource("classpath:reportes/ReporteProveedor.jasper").getURI().getPath();
        
        try (OutputStream outputStream = response.getOutputStream()) {
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportePath, null, dataSource.getConnection());
            
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            
            outputStream.flush();
        } catch (IOException | JRException | RuntimeException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
            
        }
    }
}