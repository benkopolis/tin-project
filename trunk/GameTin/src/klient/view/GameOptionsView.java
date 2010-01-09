package klient.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import klient.controller.ViewsController;


/**
 * 
 * @author zby
 *
 */
public class GameOptionsView extends GameView {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4531012350180082633L;

	public enum FieldKind {
		IP_FIELD,
		HOST_FIELD,
		NICK_FIELD
	}
	
	protected JPanel panel = new JPanel();
	protected JTextField[] textFields = new JTextField [3];
	protected JLabel[] labels = new JLabel [3];
	
	protected JButton ok = new JButton("OK");
	protected JButton cancel = new JButton("Cancel");
	/**
	 * 
	 */
	public GameOptionsView(ViewsController p) {
		super(p);
		labels[FieldKind.IP_FIELD.ordinal()] = new JLabel("Adres ip serwera:");
		textFields[FieldKind.IP_FIELD.ordinal()] = new JTextField();
		labels[FieldKind.HOST_FIELD.ordinal()] = new JLabel("Nazwa hosta:");
		textFields[FieldKind.HOST_FIELD.ordinal()] = new JTextField();
		labels[FieldKind.NICK_FIELD.ordinal()] = new JLabel("Podaj swoj nick:");
		textFields[FieldKind.NICK_FIELD.ordinal()] = new JTextField();
	}
	
	@Override
	public void init() {
		cancel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
			    closeApplication();
			}
		});
		ok.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] str = new String [3];
				str[0] = textFields[FieldKind.IP_FIELD.ordinal()].getText();
				str[1] = textFields[FieldKind.HOST_FIELD.ordinal()].getText();
				str[2] = textFields[FieldKind.NICK_FIELD.ordinal()].getText();
				Inet4Address ia;
				if(checkIp(str[0]) == false && str[1].isEmpty()==false) {
					try {
						ia = (Inet4Address) InetAddress.getByName(str[1]);
					} catch (UnknownHostException e1) {
						// don't close window - bad address
						JOptionPane.showMessageDialog(null, "Zly adres ip/nazwa hosta");
						return;
					}
					if(ia==null) {
						JOptionPane.showMessageDialog(null, "Zly adres ip/nazwa hosta");
						return;
					} 
				} else if(checkIp(str[0]) == false && str[1].isEmpty()==true) {
					JOptionPane.showMessageDialog(null, "Zly adres ip/nazwa hosta");
					return;						
				}
				if(str[2].isEmpty()) {
					JOptionPane.showMessageDialog(null, "Nie podano nicka");
					return;
				}
			}
		});
		setSize(400, 150);
		setLocation(300, 200);
		add(panel);
		panel.setLayout(new GridLayout(4,2));
		setTitle("Okno opcji gry w zbieranie monet");
		setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		labels[FieldKind.IP_FIELD.ordinal()].setSize(100, 30);
		textFields[FieldKind.IP_FIELD.ordinal()].setSize(100, 30);
		labels[FieldKind.HOST_FIELD.ordinal()].setSize(100, 30);
		textFields[FieldKind.HOST_FIELD.ordinal()].setSize(100, 30);
		labels[FieldKind.NICK_FIELD.ordinal()].setSize(100, 30);
		textFields[FieldKind.NICK_FIELD.ordinal()].setSize(100, 30);
		ok.setSize(50, 30);
		cancel.setSize(50, 30);
		panel.add(labels[FieldKind.IP_FIELD.ordinal()]);           
		panel.add(textFields[FieldKind.IP_FIELD.ordinal()]);
		panel.add(labels[FieldKind.HOST_FIELD.ordinal()]);
		panel.add(textFields[FieldKind.HOST_FIELD.ordinal()]);
		panel.add(labels[FieldKind.NICK_FIELD.ordinal()]);
		panel.add(textFields[FieldKind.NICK_FIELD.ordinal()]);
		panel.add(ok); 
		panel.add(cancel);
		ok.setVisible(true);
		cancel.setVisible(true);
		for(JLabel l: labels)
			l.setVisible(true);
		for(JTextField t: textFields)
			t.setVisible(true);
		panel.setVisible(true);
	}

	protected void closeApplication() {
		dispose();
	}
	
	public static boolean checkIp (String sip)
    {
		if(sip == null || sip.isEmpty())
			return false;
        String [] parts = sip.split ("\\.");
        for (String s : parts) {
            int i = Integer.parseInt (s);
            if (i < 0 || i > 255)
            {
                return false;
            }
        }
        return true;
    } 
}
