package com.online_exchange.spring.service;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.online_exchange.spring.model.User;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import java.io.IOException;

@SuppressWarnings("deprecation")
@Service("mailService")
public class MailServiceImpl implements MailService{

	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	VelocityEngine velocityEngine;
	
	@Autowired
	Configuration freemarkerConfiguration;
	

	@Override
	public void sendEmail(Object object) {

		User user = (User) object;
		MimeMessagePreparator preparator = getMessagePreparator(user);
		
		try {
            mailSender.send(preparator);
            System.out.println("Poslao poruku!");
        }
        catch (MailException ex) {
            System.out.println("GRESKA PRILIKOM SLANJA !");
        }
	}

	private MimeMessagePreparator getMessagePreparator(final User user){
		
		MimeMessagePreparator preparator = new MimeMessagePreparator() {

                        @SuppressWarnings("override")
			public void prepare(MimeMessage mimeMessage) throws Exception {
            	MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
 
               	helper.setSubject("online - menjacnica");
               	helper.setFrom("password@onlinemenjacnica.com");
               	helper.setTo(user.getEmail());
     
               	Map<String, Object> model = new HashMap<>();
                model.put("user", user);
                
            	String text = geFreeMarkerTemplateContent(model);

                // use the true flag to indicate you need a multipart message
            	helper.setText(text, true);

            	//Additionally, let's add a resource as an attachment as well.
            	helper.addAttachment("nas logo.png", new ClassPathResource("linux-icon.png"));

            }
        };
        return preparator;
	}
	
	public String geFreeMarkerTemplateContent(Map<String, Object> model){
		StringBuilder content = new StringBuilder();
		try{
         content.append(FreeMarkerTemplateUtils.processTemplateIntoString( 
        		 freemarkerConfiguration.getTemplate("mailTemplate.txt"),model));
         return content.toString();
		}catch(IOException | TemplateException e){
                    System.out.println("GRESKA KOD TEMPLATE");
                }
	      return "";
	}



}
