package it.solvingteam.gespim.bindingutil

import org.springframework.beans.propertyeditors.CustomDateEditor
import org.springframework.beans.PropertyEditorRegistry
import org.springframework.beans.PropertyEditorRegistrar


import it.interno.siceant.*
import it.interno.siceant.security.*
import it.solvingteam.gespim.pratica.Beneficiario;


public class MyPropertyEditorRegistrar implements PropertyEditorRegistrar {
    public void registerCustomEditors(PropertyEditorRegistry propertyEditorRegistry) {

        propertyEditorRegistry.registerCustomEditor(Set, 'beneficiari', new IdListEditor(domainClass: Beneficiario))

        //propertyEditorRegistry.registerCustomEditor(Set, 'ruoli', new IdListEditor(domainClass: Ruolo))
                    
    }
}
