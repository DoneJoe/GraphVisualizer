package ch.bfh.bti7301.hs2013.gravis.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public class MainWindow extends JFrame {

	private static final long serialVersionUID = 8699847848182615730L;
	
	private final static String TITLE = "Graph Visualizer";
	
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		super(TITLE);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 895, 371);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.contentPane.setLayout(new BorderLayout(0, 0));
		this.setContentPane(this.contentPane);
		
		this.setVisible(true);
	}

}
