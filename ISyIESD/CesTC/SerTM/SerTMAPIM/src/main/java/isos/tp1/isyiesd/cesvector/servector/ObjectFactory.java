
package isos.tp1.isyiesd.cesvector.servector;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the isos.tp1.isyiesd.cesvector.servector package. 
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

    private final static QName _XaCommit_QNAME = new QName("http://iesd21.isos.isyiesd.cesvector.servectorserver", "xa_commit");
    private final static QName _XaCommitResponse_QNAME = new QName("http://iesd21.isos.isyiesd.cesvector.servectorserver", "xa_commitResponse");
    private final static QName _XaPrepare_QNAME = new QName("http://iesd21.isos.isyiesd.cesvector.servectorserver", "xa_prepare");
    private final static QName _XaPrepareResponse_QNAME = new QName("http://iesd21.isos.isyiesd.cesvector.servectorserver", "xa_prepareResponse");
    private final static QName _XaRollback_QNAME = new QName("http://iesd21.isos.isyiesd.cesvector.servectorserver", "xa_rollback");
    private final static QName _XaRollbackResponse_QNAME = new QName("http://iesd21.isos.isyiesd.cesvector.servectorserver", "xa_rollbackResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: isos.tp1.isyiesd.cesvector.servector
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link XaCommit }
     * 
     */
    public XaCommit createXaCommit() {
        return new XaCommit();
    }

    /**
     * Create an instance of {@link XaCommitResponse }
     * 
     */
    public XaCommitResponse createXaCommitResponse() {
        return new XaCommitResponse();
    }

    /**
     * Create an instance of {@link XaPrepare }
     * 
     */
    public XaPrepare createXaPrepare() {
        return new XaPrepare();
    }

    /**
     * Create an instance of {@link XaPrepareResponse }
     * 
     */
    public XaPrepareResponse createXaPrepareResponse() {
        return new XaPrepareResponse();
    }

    /**
     * Create an instance of {@link XaRollback }
     * 
     */
    public XaRollback createXaRollback() {
        return new XaRollback();
    }

    /**
     * Create an instance of {@link XaRollbackResponse }
     * 
     */
    public XaRollbackResponse createXaRollbackResponse() {
        return new XaRollbackResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XaCommit }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link XaCommit }{@code >}
     */
    @XmlElementDecl(namespace = "http://iesd21.isos.isyiesd.cesvector.servectorserver", name = "xa_commit")
    public JAXBElement<XaCommit> createXaCommit(XaCommit value) {
        return new JAXBElement<XaCommit>(_XaCommit_QNAME, XaCommit.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XaCommitResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link XaCommitResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://iesd21.isos.isyiesd.cesvector.servectorserver", name = "xa_commitResponse")
    public JAXBElement<XaCommitResponse> createXaCommitResponse(XaCommitResponse value) {
        return new JAXBElement<XaCommitResponse>(_XaCommitResponse_QNAME, XaCommitResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XaPrepare }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link XaPrepare }{@code >}
     */
    @XmlElementDecl(namespace = "http://iesd21.isos.isyiesd.cesvector.servectorserver", name = "xa_prepare")
    public JAXBElement<XaPrepare> createXaPrepare(XaPrepare value) {
        return new JAXBElement<XaPrepare>(_XaPrepare_QNAME, XaPrepare.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XaPrepareResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link XaPrepareResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://iesd21.isos.isyiesd.cesvector.servectorserver", name = "xa_prepareResponse")
    public JAXBElement<XaPrepareResponse> createXaPrepareResponse(XaPrepareResponse value) {
        return new JAXBElement<XaPrepareResponse>(_XaPrepareResponse_QNAME, XaPrepareResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XaRollback }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link XaRollback }{@code >}
     */
    @XmlElementDecl(namespace = "http://iesd21.isos.isyiesd.cesvector.servectorserver", name = "xa_rollback")
    public JAXBElement<XaRollback> createXaRollback(XaRollback value) {
        return new JAXBElement<XaRollback>(_XaRollback_QNAME, XaRollback.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XaRollbackResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link XaRollbackResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://iesd21.isos.isyiesd.cesvector.servectorserver", name = "xa_rollbackResponse")
    public JAXBElement<XaRollbackResponse> createXaRollbackResponse(XaRollbackResponse value) {
        return new JAXBElement<XaRollbackResponse>(_XaRollbackResponse_QNAME, XaRollbackResponse.class, null, value);
    }

}
