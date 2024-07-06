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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.royalpaws.model.CompraDetalle;
import com.royalpaws.service.CompraDetalleService;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;


@RestController
@RequestMapping("/api/detalles")
public class CompraDetalleController {
	
	@Autowired
	private CompraDetalleService serviCompraDetalle;

	@GetMapping("/lista")
	public ResponseEntity<List<CompraDetalle>> listarDetalles() {
		List<CompraDetalle> detalles = serviCompraDetalle.listaDetalles();
		return ResponseEntity.ok(detalles);
	}
	
	@DeleteMapping("/eliminar/{codigo}")
	public ResponseEntity<String> eliminar(@PathVariable("codigo") int cod) {
		try {
			serviCompraDetalle.eliminar(cod);
			return ResponseEntity.ok("Compra Detalle ID: " + cod + " eliminado.");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Se produjo un error al eliminar este registro!");
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
			String rutaReporte = resourceLoader.getResource("classpath:reportes/ReporteVentas.jasper").getURI().getPath();
			JasperPrint jasperPrint = JasperFillManager.fillReport(rutaReporte, null, dataSource.getConnection());
			OutputStream outStream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
