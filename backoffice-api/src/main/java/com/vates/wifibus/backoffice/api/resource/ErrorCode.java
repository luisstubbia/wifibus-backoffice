package com.vates.wifibus.backoffice.api.resource;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

/**
 * Error enum. Permite modelar y controlar los errores del servicio.
 * 
 * @author luis.stubbia
 *
 */
public enum ErrorCode {

	CONFIGURATION_NOT_FOUND("1001", "No se encontró el Hotspot especificado para devolver la Configuración.", HttpStatus.NOT_FOUND), 
	CAMPAIGN_NOT_FOUND("1002", "No se encontró la campaña especifica.", HttpStatus.NOT_FOUND), 
	SESSION_NOT_FOUND("1001", "No se encontró una sesion activa para esa direccion MAC.", HttpStatus.NOT_FOUND), 
	PROFILE_NOT_FOUND("1001","No se encontró ningun perfil asociado para esos datos.", HttpStatus.NOT_FOUND),

	PROFILE_MISSING_USERNAME("2001","El nombre de usuario es requerido.", HttpStatus.BAD_REQUEST),
	PROFILE_MISSING_MAC_ADRESS("2002","La direccion MAC del sipositivo es requerida.", HttpStatus.BAD_REQUEST),
	PROFILE_MISSING_VALUES("2003","Debe indicar al menos un valor de entrada.", HttpStatus.BAD_REQUEST),
	PROFILE_MISSING_LOGIN_SOURCE("2004","Debe indicar el origen del inicio de sesion.", HttpStatus.BAD_REQUEST),
	
	BAD_INPUT("3001", "Ingreso de datos incorrectos.", HttpStatus.BAD_REQUEST), 
	SERVICE_ERROR("3002", "Error interno del servicio.", HttpStatus.INTERNAL_SERVER_ERROR);

	private String code;
	private String description;
	private HttpStatus status;

	private ErrorCode(String code, String description, HttpStatus status) {
		this.code = code;
		this.description = description;
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public HttpStatus getStatus() {
		return status;
	}

	/**
	 * Obtain and return the ErrorCode enum matching the given code.
	 *
	 * @param code
	 * @return ErrorCode or null;
	 */
	public static ErrorCode lookupByCode(String code) {
		if (!StringUtils.isEmpty(code)) {
			for (ErrorCode typeEnum : values()) {
				if (typeEnum.getCode().equalsIgnoreCase(code)) {
					return typeEnum;
				}
			}
		}
		return null;
	}
}
