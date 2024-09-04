package entidades;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "domicilios")
public class Domicilio implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_domicilio;
	
	@Column(name = "calle")
	private String calle;
	
	@Column(name = "numero")
	private int numero;
	
	@Column(name = "cod_postal")
	private int cod_postal;
	
	@Column(name = "provincia")
	private String provincia;
	
	// Relaciones
	@OneToOne(mappedBy = "domicilio")
	private Cliente cliente;

	// Constructores

	// Constructor sin parametros
	public Domicilio(){

	}

	// Constructor con todos los atributos necesarios de un Domicilio
	public Domicilio(String calle, int numero, int cod_postal, String provincia){
		this.calle = calle;
		this.numero = numero;
		this.cod_postal = cod_postal;
		this.provincia = provincia;
	}

	// Getters y Setters

	public long getId_domicilio() {
		return id_domicilio;
	}
	public void setId_domicilio(long id_domicilio) {
		this.id_domicilio = id_domicilio;
	}
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public int getCod_postal() {
		return cod_postal;
	}
	public void setCod_postal(int cod_postal) {
		this.cod_postal = cod_postal;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	

	
}
