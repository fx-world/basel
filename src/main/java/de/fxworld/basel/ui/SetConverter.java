package de.fxworld.basel.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import com.vaadin.data.util.converter.Converter;

public class SetConverter implements Converter<Object, Collection> {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Collection convertToModel(Object value, Class<? extends Collection> targetType, Locale locale) throws ConversionException {
		Collection result = new ArrayList();		
		result.addAll((Collection) value);
		
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object convertToPresentation(Collection value, Class<? extends Object> targetType, Locale locale) throws ConversionException {
		Set result = new HashSet();		
		result.addAll(value);
		
		return result;
	}

	@Override
	public Class<Collection> getModelType() {
		return Collection.class;
	}

	@Override
	public Class<Object> getPresentationType() {
		return Object.class;
	}


}
