package screencast;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.border.Border;
import javax.swing.filechooser.FileSystemView;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GUI implements ActionListener{

	private JFrame frame;
	private JPanel panel;
	private JButton button1;
	private JButton button2;
	private JButton button3;
	private JLabel label;
	private String path = "";
	private Scheduler s;
	private boolean start = false;

	
	//constructor of the GUI
	public GUI() {
		frame = new JFrame();
		frame.setSize(700, 200);
		frame.setTitle("ScreenCast");
		
		//setting a frame icon
		Image icon = java.awt.Toolkit.getDefaultToolkit().getImage("F:/Projects/Screen Cast/src/img/screenshot.png");    
		frame.setIconImage(icon);  
		
		//aligining the title in the center
		frame.addComponentListener((ComponentListener) new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                titleAlign(frame);
            }

        });
		
		//creating a panel
		panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		panel.setLayout(new GridLayout(2, 2, 20, 30));
		
		
		//confirm before exiting
		frame.addWindowListener(new WindowAdapter() {
		      public void windowClosing(WindowEvent we) {
		        closeWindow();
		      }
		   });
		
		button1 = new JButton("Start");
//		button1.setPreferredSize(new Dimension(70, 30));
		button1.addActionListener(this);
		panel.add(button1);
		
		button2 = new JButton("Stop");
//		button2.setPreferredSize(new Dimension(70, 30));
		button2.addActionListener(this);
		panel.add(button2);
		
		label = new JLabel("  Choose a directory");
		Border border = BorderFactory.createLineBorder(Color.black);
		label.setBorder(border);
//		label.setPreferredSize(new Dimension(300, 50));
		panel.add(label);
		
		button3 = new JButton("Browse");
//		button3.setPreferredSize(new Dimension(70, 30));
		button3.addActionListener(this);
		panel.add(button3);
		
		frame.add(panel);
		frame.setVisible(true);
	}
	
	
	//aligning title of the frame in the center
	private void titleAlign(JFrame frame) {

        Font font = frame.getFont();

        String currentTitle = frame.getTitle().trim();
        FontMetrics fm = frame.getFontMetrics(font);
        int frameWidth = frame.getWidth();
        int titleWidth = fm.stringWidth(currentTitle);
        int spaceWidth = fm.stringWidth(" ");
        int centerPos = (frameWidth / 2) - (titleWidth / 2);
        int spaceCount = centerPos / spaceWidth;
        String pad = "";
        pad = String.format("%" + (spaceCount - 14) + "s", pad);
        frame.setTitle(pad + currentTitle);

    }
	
	//show confirm dialog on closing Window
	public void closeWindow() {
		int result = JOptionPane.showConfirmDialog(frame, "Do you want to Exit ?", "Exit Confirmation : ",
				JOptionPane.YES_NO_OPTION);
        
        if (result == JOptionPane.YES_OPTION)
          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        else if (result == JOptionPane.NO_OPTION)
          frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
	
	//Performing the action events on the buttons
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//START BUTTON --> If path is not empty', then only start taking the screenshots
		if(e.getSource() == button1) {
			if(path != "" && !start) {
				start = true;
				s = new Scheduler(path);
				button2.setText("Pause");
				JOptionPane.showMessageDialog(null, "The App has started.");
			}
			else if(start)
				JOptionPane.showMessageDialog(null, "The App is running!");
			else if(path == "")
				JOptionPane.showMessageDialog(null, "Choose a directory to save the screenshots!!");
		}
		
		//STOP BUTTON
		else if(e.getSource() == button2) {
			//if scheduler constructor hasnt been called, i.e. no screenshots have been taken yet, close the application on stop button.
			if (s == null) {
				closeWindow();
				System.exit(1);
				}
			//else if screenshots have started, use stop as a pause button.
			else {
				JOptionPane.showMessageDialog(null, "The Scheduler has been paused. Click on Start to restart the scheduler!");
				start = false;
				s.stop();
			}
		}
		
		//BROWSE BUTTON
		else if(e.getSource() == button3) {
			 JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			 
			 //Selecting only directories
			 j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			 int r = j.showSaveDialog(null);
			 
			 //if save option is chosen, then save the absolute path in the string path, where screenshots are going to be saved.
			 if (r == JFileChooser.APPROVE_OPTION) {
				 path = j.getSelectedFile().getAbsolutePath();
				 System.out.println(path);
				 label.setText(path);
			 }				 
		}
	}
	
	
	public static void main(String[] args) {
		new GUI(); //initialise GUI
	}
	
}
