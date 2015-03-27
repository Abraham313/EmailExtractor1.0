/*
 * Thils is the class that hold the server connection code 
 * 
 * 
 */
package com.emailextractor;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;

import javax.swing.JOptionPane;

public class ServerConnection {

    private Properties props;
    private Session session;
    private Store store;
    protected Folder inbox;
    private Message messages[];
    private String Email;
    private String Pass;
    boolean flag = false;

    public void connectToServer(String Email, String Pass) throws MessagingException {
        props = new Properties();

        // server setting
        props.put("mail.imap.host", "imap.gmail.com");
        props.put("mail.imap.port", "993");

        //SSL setting
        props.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.imap.socketFactory.fallback", "false");
        props.setProperty("mail.imap.socketFactory.port", String.valueOf("993"));
        session = Session.getDefaultInstance(props);
        store = session.getStore("imaps"); //Connecting to Store
        store.connect("imap.gmail.com", Email, Pass);
    }

    //Open the inbox folder and get Message.
    public void getMessage() {
        try {
            inbox = store.getFolder("Inbox");
            inbox.open(Folder.READ_ONLY);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void signOut() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (inbox != null && inbox.isOpen()) {
            try {
                inbox.close(true);
            } catch (MessagingException ex) {
                Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (store != null) {
            try {
                store.close();
            } catch (MessagingException ex) {
                Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void authenTication(final String email, final String pass) {
        session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        email, pass);
            }
        });
    }

}
