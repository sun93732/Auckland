
package com.oracle;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.oracle package. 
 * <p>An ObjectFactory allows you to programatically 
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

    private final static QName _Label_QNAME = new QName("http://www.oracle.com", "label");
    private final static QName _AddProductCategoriesResponse_QNAME = new QName("http://www.oracle.com", "addProductCategoriesResponse");
    private final static QName _GetProductCategoriesV2_QNAME = new QName("http://www.oracle.com", "getProductCategoriesV2");
    private final static QName _GetProductCategoriesV2Response_QNAME = new QName("http://www.oracle.com", "getProductCategoriesV2Response");
    private final static QName _FetchCategories_QNAME = new QName("http://www.oracle.com", "fetch_categories");
    private final static QName _Product_QNAME = new QName("http://www.oracle.com", "Product");
    private final static QName _FetchCategoriesResponse_QNAME = new QName("http://www.oracle.com", "fetch_categoriesResponse");
    private final static QName _AddProductCategories_QNAME = new QName("http://www.oracle.com", "addProductCategories");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.oracle
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetProductCategoriesV2Response }
     * 
     */
    public GetProductCategoriesV2Response createGetProductCategoriesV2Response() {
        return new GetProductCategoriesV2Response();
    }

    /**
     * Create an instance of {@link FetchCategories }
     * 
     */
    public FetchCategories createFetchCategories() {
        return new FetchCategories();
    }

    /**
     * Create an instance of {@link Product }
     * 
     */
    public Product createProduct() {
        return new Product();
    }

    /**
     * Create an instance of {@link FetchCategoriesResponse }
     * 
     */
    public FetchCategoriesResponse createFetchCategoriesResponse() {
        return new FetchCategoriesResponse();
    }

    /**
     * Create an instance of {@link AddProductCategories }
     * 
     */
    public AddProductCategories createAddProductCategories() {
        return new AddProductCategories();
    }

    /**
     * Create an instance of {@link Label }
     * 
     */
    public Label createLabel() {
        return new Label();
    }

    /**
     * Create an instance of {@link AddProductCategoriesResponse }
     * 
     */
    public AddProductCategoriesResponse createAddProductCategoriesResponse() {
        return new AddProductCategoriesResponse();
    }

    /**
     * Create an instance of {@link GetProductCategoriesV2 }
     * 
     */
    public GetProductCategoriesV2 createGetProductCategoriesV2() {
        return new GetProductCategoriesV2();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Label }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.oracle.com", name = "label")
    public JAXBElement<Label> createLabel(Label value) {
        return new JAXBElement<Label>(_Label_QNAME, Label.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddProductCategoriesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.oracle.com", name = "addProductCategoriesResponse")
    public JAXBElement<AddProductCategoriesResponse> createAddProductCategoriesResponse(AddProductCategoriesResponse value) {
        return new JAXBElement<AddProductCategoriesResponse>(_AddProductCategoriesResponse_QNAME, AddProductCategoriesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProductCategoriesV2 }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.oracle.com", name = "getProductCategoriesV2")
    public JAXBElement<GetProductCategoriesV2> createGetProductCategoriesV2(GetProductCategoriesV2 value) {
        return new JAXBElement<GetProductCategoriesV2>(_GetProductCategoriesV2_QNAME, GetProductCategoriesV2 .class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProductCategoriesV2Response }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.oracle.com", name = "getProductCategoriesV2Response")
    public JAXBElement<GetProductCategoriesV2Response> createGetProductCategoriesV2Response(GetProductCategoriesV2Response value) {
        return new JAXBElement<GetProductCategoriesV2Response>(_GetProductCategoriesV2Response_QNAME, GetProductCategoriesV2Response.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FetchCategories }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.oracle.com", name = "fetch_categories")
    public JAXBElement<FetchCategories> createFetchCategories(FetchCategories value) {
        return new JAXBElement<FetchCategories>(_FetchCategories_QNAME, FetchCategories.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Product }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.oracle.com", name = "Product")
    public JAXBElement<Product> createProduct(Product value) {
        return new JAXBElement<Product>(_Product_QNAME, Product.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FetchCategoriesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.oracle.com", name = "fetch_categoriesResponse")
    public JAXBElement<FetchCategoriesResponse> createFetchCategoriesResponse(FetchCategoriesResponse value) {
        return new JAXBElement<FetchCategoriesResponse>(_FetchCategoriesResponse_QNAME, FetchCategoriesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddProductCategories }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.oracle.com", name = "addProductCategories")
    public JAXBElement<AddProductCategories> createAddProductCategories(AddProductCategories value) {
        return new JAXBElement<AddProductCategories>(_AddProductCategories_QNAME, AddProductCategories.class, null, value);
    }

}
