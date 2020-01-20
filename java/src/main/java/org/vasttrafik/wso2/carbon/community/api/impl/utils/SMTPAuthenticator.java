package org.vasttrafik.wso2.carbon.community.api.impl.utils;

import java.util.Properties;

import javax.mail.PasswordAuthentication;

public class SMTPAuthenticator extends javax.mail.Authenticator {
	
	Properties properties;
	
	public SMTPAuthenticator(Properties properties) {
		this.properties = properties;
	}
	
    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(properties.getProperty("mail.smtp.user"), properties.getProperty("mail.smtp.password"));
     }
 }
