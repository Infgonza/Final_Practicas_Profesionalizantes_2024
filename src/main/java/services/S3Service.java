package services;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class S3Service {

	private final S3Client  s3Client;
	private final String bucketName = "aws-groove-records"; 


	public S3Service() {
	        this.s3Client = S3Client.builder()
	                .region(Region.US_EAST_1) 
	                .credentialsProvider(DefaultCredentialsProvider.create())
	                .build();
   }

	public String uploadFile(MultipartFile file) {
		
		String fileName = file.getOriginalFilename();
		Path filePath = Paths.get("uploads", fileName); // Ruta local temporal para almacenar el archivo

		try {
			Files.createDirectories(filePath.getParent()); // Crear el directorio si no existe
			Files.write(filePath, file.getBytes()); // Guardar el archivo en la ruta temporal

			// Prepara 
			PutObjectRequest putObjectRequest = PutObjectRequest.builder()
        		
                .bucket(bucketName)
                .key(fileName)
                .build();

			// Sube el archivo a S3
			s3Client.putObject(putObjectRequest, filePath);

			return s3Client.utilities().getUrl(r -> r.bucket(bucketName).key(fileName)).toString(); // Retorna la URL del archivo subido
		} catch (IOException e) {
			e.printStackTrace();
			return null; 
		}
	}

	
}
