package com.personal.diccionariobd.modelodatos.controller.basedatos;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;



import com.personal.diccionariobd.modelodatos.dao.dto.BaseDatosDatasource;


public class BaseDatosValidator implements Validator {

	@Override
	public boolean supports(Class<?> aclass) {
		// TODO Auto-generated method stub
		return BaseDatosDatasource.class.isAssignableFrom(aclass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		BaseDatosDatasource customValidationEntity = (BaseDatosDatasource) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombreBd", "BaseDatos.nombreBD.requiered");
		
		/*if (customValidationEntity.getId().getIdDatasource()==""){
			errors.rejectValue("id.idDatasource", "IDssss de Datasource no puede estar en blanco");
		}*/
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id.idDatasource", "BaseDatos.idDataSource.requiered");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id.idBd", "BaseDatos.idBd.requiered");
	}

}
