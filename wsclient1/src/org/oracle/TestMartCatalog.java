
package org.oracle;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import com.oracle.ObjectFactory;
import com.oracle.Product;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "TestMartCatalog", targetNamespace = "http://www.oracle.com")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface TestMartCatalog {


    /**
     * 
     * @param arg0
     * @return
     *     returns java.util.List<java.lang.String>
     */
    @WebMethod(operationName = "fetch_categories", action = "fetch_categories")
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "fetch_categories", targetNamespace = "http://www.oracle.com", className = "com.oracle.FetchCategories")
    @ResponseWrapper(localName = "fetch_categoriesResponse", targetNamespace = "http://www.oracle.com", className = "com.oracle.FetchCategoriesResponse")
    @Action(input = "fetch_categories", output = "http://www.oracle.com/TestMartCatalog/fetch_categoriesResponse")
    public List<String> fetchCategories(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "addProductCategories", targetNamespace = "http://www.oracle.com", className = "com.oracle.AddProductCategories")
    @ResponseWrapper(localName = "addProductCategoriesResponse", targetNamespace = "http://www.oracle.com", className = "com.oracle.AddProductCategoriesResponse")
    @Action(input = "http://www.oracle.com/TestMartCatalog/addProductCategoriesRequest", output = "http://www.oracle.com/TestMartCatalog/addProductCategoriesResponse")
    public boolean addProductCategories(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1);

    /**
     * 
     * @return
     *     returns java.util.List<com.oracle.Product>
     */
    @WebMethod
    @WebResult(name = "PP", targetNamespace = "")
    @RequestWrapper(localName = "getProductCategoriesV2", targetNamespace = "http://www.oracle.com", className = "com.oracle.GetProductCategoriesV2")
    @ResponseWrapper(localName = "getProductCategoriesV2Response", targetNamespace = "http://www.oracle.com", className = "com.oracle.GetProductCategoriesV2Response")
    @Action(input = "http://www.oracle.com/TestMartCatalog/getProductCategoriesV2Request", output = "http://www.oracle.com/TestMartCatalog/getProductCategoriesV2Response")
    public List<Product> getProductCategoriesV2();

}