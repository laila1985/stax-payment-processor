package stax.payment.processor.validator;

import org.springframework.stereotype.Component;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.InputStream;

@Component
public class XmlValidator {

    public void validate(InputStream inputStream) throws Exception {

        SchemaFactory schemaFactory =
                SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        Schema schema = schemaFactory.newSchema(
                new StreamSource(
                        getClass().getResourceAsStream("/payment.xsd")
                )
        );

        schema.newValidator().validate(
                new StreamSource(inputStream)
        );
    }
}