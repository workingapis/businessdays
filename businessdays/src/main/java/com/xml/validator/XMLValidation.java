package com.xml.validator;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.log4j.Logger;

public class XMLValidation {
	
	private static Logger logger =  Logger.getLogger(XMLValidation.class);
	
	public static boolean validate(String xmlFile, String schemaFile) {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            Schema schema = schemaFactory.newSchema(new File(getResource(schemaFile)));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(getResource(xmlFile))));
            return true;
        } catch (Exception e) {
        	logger.error("error while validating xml file with xsd "+ e.getMessage());
        	throw new RuntimeException(e.getMessage());
        }
    }

    private static String getResource(final String filename) throws FileNotFoundException, MalformedURLException {
        URL resource = new File(filename).toURL();
        Objects.requireNonNull(resource);
        return resource.getFile();
    }
}
