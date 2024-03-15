package com.mycompany.server;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalTime;

public class LocalTimeAdapter extends XmlAdapter<String, LocalTime> {
    @Override
    public LocalTime unmarshal(String v) throws JAXBException {
        return LocalTime.parse(v);
    }

    @Override
    public String marshal(LocalTime v) throws JAXBException {
        return v.toString();
    }
}
