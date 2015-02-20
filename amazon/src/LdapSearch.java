
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class LdapSearch {
  public static void main(String[] args) throws Exception {
    Hashtable env = new Hashtable();

    String sp = "com.sun.jndi.ldap.LdapCtxFactory";
    env.put(Context.INITIAL_CONTEXT_FACTORY, sp);

    String ldapUrl = "ldap://slc03nta.us.oracle.com:3060";
    env.put(Context.PROVIDER_URL, ldapUrl);
    env.put(Context.SECURITY_PRINCIPAL, "cn=orcladmin");
    env.put(Context.SECURITY_CREDENTIALS, "welcome1");
    DirContext dctx = new InitialDirContext(env);

    String base = "cn=jpsTestNode";

    SearchControls sc = new SearchControls();
  /*  String[] attributeFilter = { "cn", "mail" };
    sc.setReturningAttributes(attributeFilter);*/
    sc.setSearchScope(SearchControls.SUBTREE_SCOPE);

    String filter = "(orcljazncodebase=xxx)";

    NamingEnumeration<?> results = dctx.search(base, filter, sc);
    while (results.hasMore()) {
      SearchResult sr = (SearchResult) results.next();
      Attributes attrs = sr.getAttributes();

      try {

		
		  System.out.println(sr.getNameInNamespace());
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }
    
    
    ModificationItem[] mods = new ModificationItem[1];
    Attribute mod0 = new BasicAttribute("uniquemember", "orclguid=5aa83f015d1b11dfbfbcedc940c39733, cn=grantees, cn=jaas policy,cn=systempolicy, cn=base_domain, cn=jpscontext, cn=jpstestnode");
    mods[0] = new ModificationItem(DirContext.ADD_ATTRIBUTE, mod0);
    try {
		dctx.modifyAttributes("orclguid=5FF278305D1B11DFBFBCEDC940C39733,cn=Permissions,cn=JAAS Policy,cn=systempolicy,cn=base_domain,cn=JPSContext,cn=jpsTestNode", mods);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    dctx.close();
  }
}
