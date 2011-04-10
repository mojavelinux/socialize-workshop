package com.socialize.test.account;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.socialize.account.Registration;
import com.socialize.data.PersistenceContextProducers;
import com.socialize.data.qualifier.Primary;
import com.socialize.domain.Member;

@RunWith(Arquillian.class)
public class MemberRegistrationTest
{
   @Deployment
   public static WebArchive createTestArchive() {
      return ShrinkWrap.create(WebArchive.class, "registration.war")
         .addClasses(Member.class, Registration.class, Primary.class, PersistenceContextProducers.class)
         .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
         .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
   }

   @Inject Registration registration;

   @Test
   public void testRegister() throws Exception
   {
      Member newMember = registration.getNewMember();
      newMember.setName("Jane Doe");
      newMember.setUsername("jadoe");
      newMember.setPassword("jadoe");
      newMember.setEmail("jane@mailinator.com");
      registration.register();
      assertNotNull(newMember.getId());
   }
}
