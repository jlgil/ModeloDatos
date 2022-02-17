package com.personal.diccionariobd.modelodatos.excepciones;

import com.personal.diccionariobd.modelodatos.model.MensajeErrores;

/**
 * Thrown when an expected error happens
 */
public class ApplicationException extends RuntimeException {

	private MensajeErrores errores;

	public ApplicationException(MensajeErrores app, String message) {
		super(message);
		this.errores = app;
	}

	public MensajeErrores getApplication() {
		return this.errores;
	}
}
