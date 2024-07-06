package com.royalpaws.controller;

import java.io.OutputStream;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.royalpaws.model.Marca;
import com.royalpaws.service.MarcaService;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@RestController
@RequestMapping("/api/marcas")
public class MarcaController {

    @Autowired
    private MarcaService marcaService;

    @GetMapping
    public ResponseEntity<List<Marca>> listarMarcas() {
        List<Marca> marcas = marcaService.listarMarcas();
        return new ResponseEntity<>(marcas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Marca> buscarMarca(@PathVariable("id") Integer id) {
        Marca marca = marcaService.buscar(id);
        if (marca != null) {
            return new ResponseEntity<>(marca, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<String> agregarMarca(@RequestBody Marca marca, RedirectAttributes redirect) {
        try {
            marcaService.grabar(marca);
            redirect.addFlashAttribute("mensaje", "Marca " + marca.getNomMarca().toUpperCase() + " se registró correctamente.");
            return new ResponseEntity<>("Marca registrada correctamente", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error al intentar registrar la marca", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarMarca(@PathVariable("id") Integer id, @RequestBody Marca marca,
            RedirectAttributes redirect) {
        try {
            marca.setCodMarca(id);
            marcaService.grabar(marca);
            redirect.addFlashAttribute("mensaje", "Marca " + marca.getNomMarca().toUpperCase() + " se actualizó correctamente.");
            return new ResponseEntity<>("Marca actualizada correctamente", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error al intentar actualizar la marca", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarMarca(@PathVariable("id") Integer id, RedirectAttributes redirect) {
        try {
            marcaService.eliminar(id);
            redirect.addFlashAttribute("mensaje", "Marca eliminada correctamente.");
            return new ResponseEntity<>("Marca eliminada correctamente", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error al intentar eliminar la marca", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Autowired
    private DataSource dataSource;
    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping("/reportes")
    public void reporteMarcas(HttpServletResponse response) {
        response.setHeader("Content-Disposition", "inline;");
        response.setContentType("application/pdf");
        try {
            String rutaReporte = resourceLoader.getResource("classpath:reportes/ReporteMarca.jasper").getURI().getPath();
            JasperPrint jasperPrint = JasperFillManager.fillReport(rutaReporte, null, dataSource.getConnection());
            OutputStream outStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}