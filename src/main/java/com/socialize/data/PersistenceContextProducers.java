package com.socialize.data;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import org.jboss.seam.solder.core.ExtensionManaged;

import com.socialize.data.qualifier.Primary;
import com.socialize.data.qualifier.Utility;


/**
 * This class uses CDI to produce EntityManager instances qualified that are
 * qualified as &#064;Primary.
 * 
 * <p>Example injection on a managed bean field:</p>
 * <pre>
 * &#064;Inject &#064;Primary
 * private EntityManager memberRepository;
 * </pre>
 */
public class PersistenceContextProducers
{
   @Produces
   @Primary
   @PersistenceContext
   private EntityManager em;
   
   @Produces
   @ExtensionManaged
   @PersistenceUnit
   @ConversationScoped
   private EntityManagerFactory seamPersistenceUnit;
   
   @Produces
   @ExtensionManaged
   @Utility
   @PersistenceUnit
   private EntityManagerFactory utilitySeamPersistenceUnit;
}
