package it.solvingteam.gespim.bindingutil

import java.beans.PropertyEditorSupport
import org.joda.time.format.DateTimeFormatter
import org.joda.time.format.DateTimeFormat
import org.joda.time.DateTime

class CustomDateTimePropertyEditor extends PropertyEditorSupport {

    void setAsText(String text) {
        value = new DateTime(Long.parseLong(text))

    }

    String getAsText() {
        if (value) {
        }
    }
}

