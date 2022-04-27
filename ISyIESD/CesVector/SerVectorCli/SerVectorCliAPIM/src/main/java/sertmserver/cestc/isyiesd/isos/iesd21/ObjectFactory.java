
package sertmserver.cestc.isyiesd.isos.iesd21;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the sertmserver.cestc.isyiesd.isos.iesd21 package. 
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

    private final static QName _TxBegin_QNAME = new QName("http://iesd21.isos.isyiesd.cestc.sertmserver", "tx_begin");
    private final static QName _TxBeginResponse_QNAME = new QName("http://iesd21.isos.isyiesd.cestc.sertmserver", "tx_beginResponse");
    private final static QName _TxCommit_QNAME = new QName("http://iesd21.isos.isyiesd.cestc.sertmserver", "tx_commit");
    private final static QName _TxCommitResponse_QNAME = new QName("http://iesd21.isos.isyiesd.cestc.sertmserver", "tx_commitResponse");
    private final static QName _TxRollback_QNAME = new QName("http://iesd21.isos.isyiesd.cestc.sertmserver", "tx_rollback");
    private final static QName _TxRollbackResponse_QNAME = new QName("http://iesd21.isos.isyiesd.cestc.sertmserver", "tx_rollbackResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: sertmserver.cestc.isyiesd.isos.iesd21
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TxBegin }
     * 
     */
    public TxBegin createTxBegin() {
        return new TxBegin();
    }

    /**
     * Create an instance of {@link TxBeginResponse }
     * 
     */
    public TxBeginResponse createTxBeginResponse() {
        return new TxBeginResponse();
    }

    /**
     * Create an instance of {@link TxCommit }
     * 
     */
    public TxCommit createTxCommit() {
        return new TxCommit();
    }

    /**
     * Create an instance of {@link TxCommitResponse }
     * 
     */
    public TxCommitResponse createTxCommitResponse() {
        return new TxCommitResponse();
    }

    /**
     * Create an instance of {@link TxRollback }
     * 
     */
    public TxRollback createTxRollback() {
        return new TxRollback();
    }

    /**
     * Create an instance of {@link TxRollbackResponse }
     * 
     */
    public TxRollbackResponse createTxRollbackResponse() {
        return new TxRollbackResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TxBegin }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TxBegin }{@code >}
     */
    @XmlElementDecl(namespace = "http://iesd21.isos.isyiesd.cestc.sertmserver", name = "tx_begin")
    public JAXBElement<TxBegin> createTxBegin(TxBegin value) {
        return new JAXBElement<TxBegin>(_TxBegin_QNAME, TxBegin.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TxBeginResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TxBeginResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://iesd21.isos.isyiesd.cestc.sertmserver", name = "tx_beginResponse")
    public JAXBElement<TxBeginResponse> createTxBeginResponse(TxBeginResponse value) {
        return new JAXBElement<TxBeginResponse>(_TxBeginResponse_QNAME, TxBeginResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TxCommit }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TxCommit }{@code >}
     */
    @XmlElementDecl(namespace = "http://iesd21.isos.isyiesd.cestc.sertmserver", name = "tx_commit")
    public JAXBElement<TxCommit> createTxCommit(TxCommit value) {
        return new JAXBElement<TxCommit>(_TxCommit_QNAME, TxCommit.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TxCommitResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TxCommitResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://iesd21.isos.isyiesd.cestc.sertmserver", name = "tx_commitResponse")
    public JAXBElement<TxCommitResponse> createTxCommitResponse(TxCommitResponse value) {
        return new JAXBElement<TxCommitResponse>(_TxCommitResponse_QNAME, TxCommitResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TxRollback }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TxRollback }{@code >}
     */
    @XmlElementDecl(namespace = "http://iesd21.isos.isyiesd.cestc.sertmserver", name = "tx_rollback")
    public JAXBElement<TxRollback> createTxRollback(TxRollback value) {
        return new JAXBElement<TxRollback>(_TxRollback_QNAME, TxRollback.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TxRollbackResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TxRollbackResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://iesd21.isos.isyiesd.cestc.sertmserver", name = "tx_rollbackResponse")
    public JAXBElement<TxRollbackResponse> createTxRollbackResponse(TxRollbackResponse value) {
        return new JAXBElement<TxRollbackResponse>(_TxRollbackResponse_QNAME, TxRollbackResponse.class, null, value);
    }

}
