package com.mycompany.server;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
    @Override
    public LocalDate unmarshal(String v) throws JAXBException {
        return LocalDate.parse(v);
    }

    @Override
    public String marshal(LocalDate v) throws JAXBException {
        return v.toString();
    }
}
