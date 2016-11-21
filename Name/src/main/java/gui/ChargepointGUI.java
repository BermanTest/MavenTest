package gui;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;

import main.Chargepoint;
import main.Generator;

import javax.swing.JButton;

public class ChargepointGUI
{

	private Chargepoint chargepoint = new Chargepoint(this);
	private Generator generator = new Generator();
	private JFrame frame;
	private int textAreaID;
	private int numberofTextAreas = 6;
	private JTextArea[] textAreaArray = new JTextArea[numberofTextAreas];
	private int textAreaPosX = 50;
	private int textAreaPosY = 100;
	private int textAreaLength = 300;
	private int textAreaWidth = 180;	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					ChargepointGUI window = new ChargepointGUI();
					window.frame.setVisible(true);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ChargepointGUI()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 739, 753);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblChargepoint = new JLabel("Chargepoint:");
		lblChargepoint.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblChargepoint.setBounds(26, 24, 159, 39);
		frame.getContentPane().add(lblChargepoint);
		
		JButton btnAuthorize = new JButton("Authorize");
		btnAuthorize.setBounds(36, 82, 128, 23);
		frame.getContentPane().add(btnAuthorize);
		
		btnAuthorize.addActionListener(new ActionListener() { 
		    public void actionPerformed(ActionEvent e) { 
		    	chargepoint.authorizeElectronicVehicle(generator.generateUser());
		    } 
		});
	}
	
	public int editTextArea(String message, Color color)
	{		
		if(textAreaID >= numberofTextAreas)
		{
			moveUpTextAreas();
			textAreaID--;
		}
		
		createTextArea(textAreaID);
		editTextArea(textAreaID, message, color);
		textAreaID++;
		
		return textAreaID - 1;
	}	
	
	public void editTextArea(int textAreaID, String message, Color color)
	{
		textAreaArray[textAreaID].append(message);
		textAreaArray[textAreaID].setBackground(color);
	}
	
	private void createTextArea(int number)
	{
		textAreaArray[number] = new JTextArea();
		textAreaArray[number].setBounds(textAreaWidth, textAreaPosX + (textAreaPosY + 10) * number, textAreaLength, textAreaPosY);
		frame.getContentPane().add(textAreaArray[number]);
	}
	
	private void moveUpTextAreas()
	{
		for(int i = 0; i < numberofTextAreas - 1; i++)
		{
			System.out.println("i = " + i);
			System.out.println("i = " + textAreaArray[i].getText());
			textAreaArray[i] = textAreaArray[i+1];
			System.out.println("i2 = " + textAreaArray[i].getText());
			textAreaArray[i].setBounds(textAreaWidth, textAreaPosX + (textAreaPosY + 10) * i, textAreaLength, textAreaPosY);
			frame.getContentPane().add(textAreaArray[i]);
		}
	}
}
