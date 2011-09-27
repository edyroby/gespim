package it.solvingteam.gespim.bindingutil


import java.beans.PropertyEditorSupport

class IdListEditor extends PropertyEditorSupport {

    Class domainClass

    void setAsText(String text) {
        def result = []
        if (text) {
            text.split(",").each {
                if (it && it.trim()) {
                    def id = Long.parseLong(it.trim())
                    result << domainClass.get(id)
                }
            }
        }
        
        value = result as Set
    }

    String getAsText() {
        value?.id?.join(",")
    }
}
