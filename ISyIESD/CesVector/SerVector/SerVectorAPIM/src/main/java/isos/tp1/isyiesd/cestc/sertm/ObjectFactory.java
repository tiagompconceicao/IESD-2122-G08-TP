
package isos.tp1.isyiesd.cestc.sertm;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the isos.tp1.isyiesd.cestc.sertm package. 
 * &lt;p&gt;An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AxReg_QNAME = new QName("http://iesd21.isos.isyiesd.cestc.sertmserver", "ax_reg");
    private final static QName _AxRegResponse_QNAME = new QName("http://iesd21.isos.isyiesd.cestc.sertmserver", "ax_regResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: isos.tp1.isyiesd.cestc.sertm
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AxReg }
     * 
     */
    public AxReg createAxReg() {
        return new AxReg();
    }

    /**
     * Create an instance of {@link AxRegResponse }
     * 
     */
    public AxRegResponse createAxRegResponse() {
        return new AxRegResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AxReg }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AxReg }{@code >}
     */
    @XmlElementDecl(namespace = "http://iesd21.isos.isyiesd.cestc.sertmserver", name = "ax_reg")
    public JAXBElement<AxReg> createAxReg(AxReg value) {
        return new JAXBElement<AxReg>(_AxReg_QNAME, AxReg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AxRegResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AxRegResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://iesd21.isos.isyiesd.cestc.sertmserver", name = "ax_regResponse")
    public JAXBElement<AxRegResponse> createAxRegResponse(AxRegResponse value) {
        return new JAXBElement<AxRegResponse>(_AxRegResponse_QNAME, AxRegResponse.class, null, value);
    }

}
