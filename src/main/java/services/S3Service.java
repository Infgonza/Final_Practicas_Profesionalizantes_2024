package services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
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
	   
	   try {
		   
		   // Obtenemos el nombre original del archivo
		   String fileName = file.getOriginalFilename();
		   
		   // Preparamos una solicitud para subir el archivo
		   PutObjectRequest putObjectRequest = PutObjectRequest.builder()
				   .bucket(bucketName)
				   .key(fileName)
				   .contentType(file.getContentType())
				   .build();
		   
		   // Subimos el archivo a S3
		   s3Client.putObject(putObjectRequest,
				   RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
		   
		   
		   // Retornarmos la URL del archivo SUBIDO
		   return s3Client.utilities().getUrl(r -> r.bucket(bucketName).key(fileName)).toString();
	   } catch (IOException e) {
		   e.printStackTrace();
           return null;
	   }
	   
   }
   
	
}
