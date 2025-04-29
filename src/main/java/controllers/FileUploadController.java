package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import services.CloudinaryService;

@RestController
@RequestMapping("/api/v1/imagenes")
public class FileUploadController {
	
	  @Autowired
	    private CloudinaryService cloudinaryService;

	    @PostMapping("/subir")
	    public String subirImagen(@RequestParam("file") MultipartFile file) {
	        try {
	            return cloudinaryService.uploadImage(file);
	        } catch (Exception e) {
	            return "Error al subir imagen: " + e.getMessage();
	        }
	    }
	

}
