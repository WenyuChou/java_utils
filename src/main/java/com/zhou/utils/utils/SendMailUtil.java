package com.zhou.utils.utils;

import java.util.List;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * @author : Wenyu Zhou
 * @version : v1.0
 * @date : 2019/7/16
 * description : 发送邮件。收件人为list，可以填入多个收件人群发
 */
public class SendMailUtil {
    /**smtp服务器*/
    private String host = "smtp.163.com";
    /**发件人地址*/
    private static String FROM = "m13718591023@163.com";
    /**用户名*/
    private static String USER = "m13718591023@163.com";
    /**163的授权码*/
    private static String PWD = "zwy930627";

    /**
     * 发送邮件
     * @param context   正文
     * @param subject   标题
     * @param tos   收件人地址（list可填入多个）
     */
    public static void sendMail(String context, String subject, List<String> tos) {
        Properties props = new Properties();
        //设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
        props.put("mail.smtp.host", "smtp.163.com");
        //需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
        props.put("mail.smtp.auth", "true");
        //用props对象构建一个session
        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);
        //用session为参数定义消息对象
        MimeMessage message = new MimeMessage(session);
        try {
            // 加载发件人地址
            message.setFrom(new InternetAddress(FROM));
            // 加载收件人地址
            InternetAddress[] sendTo = new InternetAddress[tos.size()];
            for (int i = 0; i < tos.size(); i++) {
                sendTo[i] = new InternetAddress(tos.get(i));
            }
            message.addRecipients(Message.RecipientType.TO, sendTo);
            //设置在发送给收信人之前给自己（发送方）抄送一份，不然会被当成垃圾邮件，报554错
            message.addRecipients(MimeMessage.RecipientType.CC, InternetAddress.parse(FROM));
            //加载标题
            message.setSubject(subject);
            //向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
            Multipart multipart = new MimeMultipart();
            //设置邮件的文本内容
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setText(context);
            multipart.addBodyPart(contentPart);
            //将multipart对象放到message中
            message.setContent(multipart);
            //保存邮件
            message.saveChanges();
            //发送邮件
            Transport transport = session.getTransport("smtp");
            //连接服务器的邮箱
            transport.connect("smtp.163.com", USER, PWD);
            //把邮件发送出去
            transport.sendMessage(message, message.getAllRecipients());
            //关闭连接
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

/*    public static void main(String[] args) {
        List<String> tos = new ArrayList<>();
        tos.add("zhouwenyu@polycis.com");
        sendMail("邮件发送测试，这是正文","标题测试",tos);
    }*/

}
