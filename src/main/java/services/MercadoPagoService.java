package services;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.resources.payment.Payment;

public class MercadoPagoService {

    public String getPaymentStatus(Long paymentId) {
        try {
            // Asegúrate de configurar tu acceso correctamente antes de hacer la solicitud
            MercadoPagoConfig.setAccessToken("APP_USR-4476113047040697-102220-d93b6716c55297306979480021de501b-2051070665");

            // Usamos el cliente de pagos para obtener el estado del pago
            PaymentClient paymentClient = new PaymentClient();
            Payment payment = paymentClient.get(paymentId);  // Obtenemos el pago usando el ID

            // Retornamos el estado del pago
            return payment.getStatus().toString();  // "approved", "pending", "rejected", etc.
        } catch (Exception e) {
            // En caso de error, logueamos el error
            System.err.println("Error al consultar el estado del pago: " + e.getMessage());
            return "error";
        }
    }
}
