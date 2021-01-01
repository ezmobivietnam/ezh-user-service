/**
 * Service connectors are similar to the Repository abstraction, encapsulating
 * communications with other services. This layer functions as either a façade or an
 * “anti-corruption” layer to protect domain logic from changes to external resource APIs, or
 * to convert between API wire formats and internal domain model constructs.
 * <p>
 * Ref: https://www.redbooks.ibm.com/redbooks/pdfs/sg248357.pdf, Figure 2-3 The internal structure of a microservice
 * <p>
 * Created by ezmobivietnam on 2020-12-28.
 */
package vn.com.ezmobi.ezhealth.ezhuserservice.connectors;