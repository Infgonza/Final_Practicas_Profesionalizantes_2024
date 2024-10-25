package controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import entities.Usuario;
import services.CarritoDeComprasServiceImpl;


@RestController
@RequestMapping("/api/v1/mp")
public class MercadoPagoController {

	@Value("${MP_ACCESS_TOKEN}")
	private String accessToken;
	
	@Autowired
    private CarritoDeComprasServiceImpl carritoService;

    private static final Logger logger = LoggerFactory.getLogger(MercadoPagoController.class);


	@GetMapping("/pago")
	public ResponseEntity<Map<String, String>> createAndRedirect(Authentication authentication) throws MPException, MPApiException{
		
		try {
			MercadoPagoConfig.setAccessToken(accessToken);
			
			// Obtenemos el usuario autenticado
	        Usuario usuario = (Usuario) authentication.getPrincipal();
	        // Obtenesmo los productos que tiene el usuario en el carrito
	        List<Map<String, Object>> productosCarrito = carritoService.obtenerProductosCarrito(usuario.getIdUsuario());
	        
	        List<PreferenceItemRequest> items = new ArrayList<>();
	        
	        for (Map<String, Object> producto : productosCarrito) {
	            PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
	                .id(producto.get("id").toString()) 
	                .title((String) producto.get("nombre")) 
	                .quantity(((Number) producto.get("cantidad")).intValue()) 
	                .currencyId("ARS") // Peso argentino 
	                .unitPrice(new BigDecimal(producto.get("precio").toString()))
	                .build();
	            
	            items.add(itemRequest);
	        }
	        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
	                .items(items)
	                .build();

	        PreferenceClient client = new PreferenceClient();
	        Preference preference = client.create(preferenceRequest);

	        // Redirigir al usuario al checkout de Mercado Pago
	        Map<String, String> response = new HashMap<>();
	        response.put("url", preference.getInitPoint());
	        return ResponseEntity.ok(response);
		} catch (Exception e) {
            logger.error("Error de Mercado Pago: " + e.getMessage()); 
		    throw e; 
		}
		
	}


}
