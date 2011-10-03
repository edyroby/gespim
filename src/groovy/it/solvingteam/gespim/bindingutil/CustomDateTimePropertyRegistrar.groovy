package it.solvingteam.gespim.bindingutil

import org.springframework.beans.propertyeditors.CustomDateEditor
import org.springframework.beans.PropertyEditorRegistrar
import org.springframework.beans.PropertyEditorRegistry
import org.joda.time.DateTime

class CustomDateTimePropertyRegistrar implements PropertyEditorRegistrar {

    public void registerCustomEditors(PropertyEditorRegistry registry) {
        registry.registerCustomEditor(DateTime.class, 'inizio', new CustomDateTimePropertyEditor())
    }
}
