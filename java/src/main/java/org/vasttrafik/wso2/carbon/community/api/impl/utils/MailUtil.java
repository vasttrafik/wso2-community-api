package org.vasttrafik.wso2.carbon.community.api.impl.utils;

import javax.mail.*;
import javax.mail.internet.*;

import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.description.Parameter;
import org.apache.axis2.description.TransportOutDescription;
import org.apache.axis2.engine.AxisConfiguration;
import org.apache.commons.io.FileUtils;
import org.wso2.carbon.core.CarbonConfigurationContextFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Properties;

public class MailUtil {

	private static Properties transportProperties;
	private static Session session;
	private static String template;
	
	static {

		try {
			transportProperties = loadTransportProperties();
			
			// Get the default Session object.
		    session = Session.getDefaultInstance(transportProperties);
		    
		    ClassLoader classLoader = MailUtil.class.getClassLoader();
			File file = new File(classLoader.getResource("watch.template").getFile());
			template = FileUtils.readFileToString(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void sendWatchMailTopic(String to, Integer topicId, String topicName){
		try {
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(transportProperties.getProperty("mail.smtp.from")));

	         // Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	         // Set Subject: header field
	         message.setSubject("Bevakning: Nytt inlägg i frågan " + topicName);

	         // Send the actual HTML message, as big as you like
	         message.setContent(template.replace("#TEMPLATE#", "Det har skrivits ett nytt inlägg i frågan <a href=\"https://developer.vasttrafik.se/portal/#/community/topic/" + topicId + "\">" + topicName + "</a>"), "text/html");

	         // Send message
	         Transport.send(message);
	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
	}
	
	public static void sendWatchMailForum(String to, Integer forumId, String forumName, Integer topicId, String topicName){
		try {
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(transportProperties.getProperty("mail.smtp.from")));

	         // Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	         // Set Subject: header field
	         message.setSubject("Bevakning: Ny fråga i forum " + forumName);

	         // Send the actual HTML message, as big as you like
	         message.setContent(template.replace("#TEMPLATE#", "Det har ställts en ny fråga <a href=\"https://developer.vasttrafik.se/portal/#/community/topic/" + topicId + "\">" + topicName + "</a> i forumet <a href=\"https://developer.vasttrafik.se/portal/#/community/forum/" + forumId + "\">" + forumName + "</a>"), "text/html");

	         // Send message
	         Transport.send(message);
	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
	}
	
	private static Properties loadTransportProperties() throws Exception {
		
		transportProperties = new Properties();
	
		try {
			ConfigurationContext configContext = CarbonConfigurationContextFactory.getConfigurationContext();
			AxisConfiguration axisConfig = configContext.getAxisConfiguration();
			TransportOutDescription mailto = axisConfig.getTransportOut("mailto"); 
			ArrayList<Parameter> parameters = mailto.getParameters();
			
            for (Parameter parameter : parameters) {
				String prop = parameter.getName();
				String value = (String)parameter.getValue();
				transportProperties.setProperty(prop, value);
			}
		}
		catch (Exception e) {
			throw e;
		}
		
		return transportProperties;
	}
	
}
