package ro.unibuc.hello.dto;

import org.springframework.data.annotation.Id;

public class CarDTO {

	@Id
	public String id;

	public String numarInmatriculare;
	public String valabilitateParcare;
	public String marca;
	public String model;
	public Boolean parcarePlatita;

	public CarDTO() {
	}

	public CarDTO(String id, String numarInmatriculare, String valabilitateParcare, String marca, String model) {
		this.id = id;
		this.numarInmatriculare = numarInmatriculare;
		this.valabilitateParcare = valabilitateParcare;
		this.marca = marca;
		this.model = model;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNumarInmatriculare() {
		return numarInmatriculare;
	}

	public void setNumarInmatriculare(String numarInmatriculare) {
		this.numarInmatriculare = numarInmatriculare;
	}

	public String getValabilitateParcare() {
		return valabilitateParcare;
	}

	public void setValabilitateParcare(String valabilitateParcare) {
		this.valabilitateParcare = valabilitateParcare;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Boolean getParcarePlatita() {
		return parcarePlatita;
	}

	public void setParcarePlatita(Boolean parcarePlatita) {
		this.parcarePlatita = parcarePlatita;
	}

	@Override
	public String toString() {
		return "Car{" + "id='" + id + '\'' + ", numarInmatriculare='" + numarInmatriculare + '\'' + ", valabilitateParcare='" + valabilitateParcare + '\'' + ", marca='" + marca + '\'' + ", model='" + model + '\'' + ", parcarePlatita=" + parcarePlatita + '}';
	}
}
