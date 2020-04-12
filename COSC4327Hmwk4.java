import java.util.Random;
import java.util.Scanner;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

class CandyBowl {
	private int CandyCnt, MaxCandies, ID;

	CandyBowl(int MaxCandies, int ID) {
		this.MaxCandies = MaxCandies;
		CandyCnt = MaxCandies;
		this.ID = ID;
	}

	public synchronized boolean Get(){
		if(CandyCnt > 0) {
			CandyCnt--;
			System.out.println("Candy bowl " + ID + " has " + CandyCnt + " candies left.");
			notifyAll();
			return true;
		}
		else {
			notifyAll();
			return false;
		}
	}

	public synchronized void Fill() {
		CandyCnt = MaxCandies;
		System.out.println("The candy bowl is full");
		notifyAll();
	}
}

class Professor extends Thread {
	private CandyBowl[] CandyBowlList;
	private TA[] TAList;
	private String Name;
	private Random RNG = new Random();
	private int CandyKnt, MaxSleep, SleepTime, Target, CandyBowlCnt, TACnt;
	private boolean GetStatus;

	Professor(CandyBowl[] CandyBowlList, TA[] TAList, int MaxSleep, String Name) {
		this.CandyBowlList = CandyBowlList;
		CandyBowlCnt = CandyBowlList.length;
		this.TAList = TAList;
		TACnt = TAList.length;
		this.MaxSleep = MaxSleep;
		this.Name = Name;
		CandyKnt = 0;
	}

	public void run(){
		try {
			while(true) {
				Target = RNG.nextInt(CandyBowlCnt);
				GetStatus = CandyBowlList[Target].Get();
				if(GetStatus == false) {
					TAList[RNG.nextInt(TACnt)].FillCandyBowl(Target);
				}
				else {
					CandyKnt++;
					SleepTime = RNG.nextInt(MaxSleep);
					System.out.println(Name + " has consumed a candy. " + Name + " sleeps for " + SleepTime + "ms.");
					Thread.sleep(SleepTime);
				}
			}
		}
		catch (InterruptedException e) {

		}
		finally {
			System.out.println(Name + " ate " + CandyKnt + " pieces of candy.");
		}
	}
}

class TA {
	private CandyBowl[] CandyBowlList;
	private String Name;

	TA(CandyBowl[] CandyBowlList, String Name) {
		this.CandyBowlList = CandyBowlList;
		this.Name = Name;
	}

	public void FillCandyBowl(int Target) {
		CandyBowlList[Target].Fill();
		System.out.println(Name + " has filled bowl " + Target);
	}
}

class Console {
	public Console() {
		JFrame frame = new JFrame("COSC 4327 Hmwk 4 - Threaded Program");
		frame.setBounds(325, 100, 700, 500);
		JTextArea textArea = new JTextArea(50, 10);

		System.setOut(new PrintStream(new OutputStream() {
		@Override
		public void write(int b) throws IOException {
			textArea.append(String.valueOf((char) b));
			textArea.setCaretPosition(textArea.getDocument().getLength());
			textArea.update(textArea.getGraphics());
			}
		}));

		frame.add(textArea);
		frame.setVisible(true);
	}
}

class secondWindow {
	private String[] choices = new String[] {"Run Forever", "Enter Runtime"};
	public JComboBox<String> comboBox = new JComboBox<String>(choices);

	public secondWindow() {
		JFrame frame = new JFrame("COSC 4327 Hmwk 4 - Threaded Program");
		frame.setBounds(325, 100, 700, 500);

		JLabel textLabel1 = new JLabel("Enter Number Of Professors:");
		textLabel1.setBounds(50, 5, 200, 100);
		JLabel textLabel2 = new JLabel("Enter Number Of TAs:");
		textLabel2.setBounds(50, 55, 125, 100);
		JLabel textLabel3 = new JLabel("Enter Number Of Bowls:");
		textLabel3.setBounds(50, 105, 150, 100);
		JLabel textLabel4 = new JLabel("Enter Bowl Capacity:");
		textLabel4.setBounds(50, 155, 125, 100);
		JLabel textLabel5 = new JLabel("Enter Max Sleep Time:");
		textLabel5.setBounds(50, 205, 150, 100);
		JLabel textLabel6 = new JLabel("Runtime Options:");
		textLabel6.setBounds(400, 5, 100, 100);
		JLabel textLabel7 = new JLabel("1. Run Forever");
		textLabel7.setBounds(525, 5, 100, 100);
		JLabel textLabel8 = new JLabel("2. Enter Runtime");
		textLabel8.setBounds(525, 30, 100, 100);
		JLabel textLabel9 = new JLabel("Enter Runtime:");
		textLabel9.setBounds(400, 150, 100, 100);

		JTextField textField1 = new JTextField();
		textField1.setBounds(250, 45, 100, 25);
		JTextField textField2 = new JTextField();
		textField2.setBounds(250, 95, 100, 25);
		JTextField textField3 = new JTextField();
		textField3.setBounds(250, 145, 100, 25);
		JTextField textField4 = new JTextField();
		textField4.setBounds(250, 195, 100, 25);
		JTextField textField5 = new JTextField();
		textField5.setBounds(250, 245, 100, 25);
		JTextField textField6 = new JTextField();
		textField6.setBounds(530, 190, 100, 25);

		JButton button = new JButton("Submit");
		button.setBounds(275, 350, 140, 40);
		JButton button1 = new JButton("Confirm Option");
		button1.setBounds(455, 150, 140, 25);

		comboBox.setBounds(450, 100, 150, 25);
		frame.setLayout(new FlowLayout());

		frame.add(textLabel1);
		frame.add(textLabel2);
		frame.add(textLabel3);
		frame.add(textLabel4);
		frame.add(textLabel5);
		frame.add(textLabel6);
		frame.add(textLabel7);
		frame.add(textLabel8);
		frame.add(textField1);
		frame.add(textField2);
		frame.add(textField3);
		frame.add(textField4);
		frame.add(textField5);
		frame.add(button);
		frame.add(button1);
		frame.add(comboBox);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		button1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				if (comboBox.getSelectedItem().equals("Run Forever")) {
					;
				}
				else if (comboBox.getSelectedItem().equals("Enter Runtime")) {
					frame.add(textLabel9);
					frame.add(textField6);
					frame.setVisible(true);
					frame.pack();
				}

				frame.setBounds(325, 100, 700, 500);
			}
		});

		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				if (comboBox.getSelectedItem().equals("Run Forever") && textField1.getText().isEmpty() == false && textField2.getText().isEmpty() == false && textField3.getText().isEmpty() == false && textField4.getText().isEmpty() == false && textField5.getText().isEmpty() == false){
					String text1 = textField1.getText();
					int parameter1 = Integer.parseInt(text1);
					String text2 = textField2.getText();
					int parameter2 = Integer.parseInt(text2);
					String text3 = textField3.getText();
					int parameter3 = Integer.parseInt(text3);
					String text4 = textField4.getText();
					int parameter4 = Integer.parseInt(text4);
					String text5 = textField5.getText();
					int parameter5 = Integer.parseInt(text5);

					COSC4327Hmwk4 obj = new COSC4327Hmwk4();
					obj.EnumParameters(parameter1, parameter2, parameter3, parameter4, parameter5, 9999);
					new Console();
				}
				if(textField1.getText().isEmpty() || textField2.getText().isEmpty() || textField3.getText().isEmpty() || textField4.getText().isEmpty() || textField5.getText().isEmpty() || textField6.getText().isEmpty()){
					;
				}
				else {
					String text1 = textField1.getText();
					int parameter1 = Integer.parseInt(text1);
					String text2 = textField2.getText();
					int parameter2 = Integer.parseInt(text2);
					String text3 = textField3.getText();
					int parameter3 = Integer.parseInt(text3);
					String text4 = textField4.getText();
					int parameter4 = Integer.parseInt(text4);
					String text5 = textField5.getText();
					int parameter5 = Integer.parseInt(text5);
					String text6 = textField6.getText();
					int parameter6 = Integer.parseInt(text6);

					COSC4327Hmwk4 obj = new COSC4327Hmwk4();
					obj.EnumParameters(parameter1, parameter2, parameter3, parameter4, parameter5, parameter6);
					new Console();
				}
			}
		});
	}

	public String getSelectedItem() {
	        return (String) comboBox.getSelectedItem();
    }
}

public class COSC4327Hmwk4 {

	public static void createWindow() {
		JFrame frame = new JFrame("COSC 4327 Hmwk 4 - Threaded Program");
		frame.setBounds(325, 100, 700, 500);

		JLabel textLabel1 = new JLabel("Runtime options:");
		textLabel1.setBounds(200, 50, 100, 100);
		JLabel textLabel2 = new JLabel("1. Use Default Parameters");
		textLabel2.setBounds(325, 50, 200, 100);
		JLabel textLabel3 = new JLabel("2. Enumerate N Objects");
		textLabel3.setBounds(325, 75, 200, 100);
		JLabel textLabel4 = new JLabel("Enter option:");
		textLabel4.setBounds(200, 175, 100, 100);

		JButton button = new JButton("Submit");
		button.setBounds(275, 350, 140, 40);

		String[] choices = new String[] {"Default Parameters", "Enumerated N Objects"};

		JComboBox<String> comboBox = new JComboBox<String>(choices);
		comboBox.setBounds(325, 215, 150, 25);
		frame.setLayout(new FlowLayout());

		frame.add(textLabel1);
		frame.add(textLabel2);
		frame.add(textLabel3);
		frame.add(textLabel4);
		frame.add(button);
		frame.add(comboBox);
		frame.setLayout(null);
		frame.setVisible(true);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				if (comboBox.getSelectedItem().equals("Default Parameters")) {
					DefaultParameters();
					new Console();

				}
				else if (comboBox.getSelectedItem().equals("Enumerated N Objects")) {
				    frame.dispose();
				    new secondWindow();
				}
			}
		});
	}

	public static void main(String[] args) {
		createWindow();
	}

	public static void DefaultParameters() {
		String[] Names = {"Dr. Cooper", "Dr. Burris", "Dr. Smith", "Dr. Cho", "Dr. Islam", "Dr. Lester", "Dr. Glisson"};
		int SleepTime;
		CandyBowl[] Bowl = new CandyBowl[1];
		Bowl[0] = new CandyBowl(28, 0);
		TA[] TAs = new TA[1];
		TAs[0] = new TA(Bowl, "Bing Bong the TA");
		Professor[] Professors = new Professor[7];
		SleepTime = 800;
		for (int i = 0; i < 7; i++) {
			Professors[i] = new Professor(Bowl, TAs, SleepTime, Names[i]);
		}
		for (int i = 0; i < 7; i++) {
			Professors[i].start();
		}
	}

	public static void EnumParameters(int ProfCnt, int TACnt, int BowlCnt, int CandiesCnt, int SleepTime, int RunTime) {
		secondWindow sw = new secondWindow();
		String value = sw.getSelectedItem();
		String Name;

		CandyBowl[] Bowl = new CandyBowl[BowlCnt];
		for (int i = 0; i < BowlCnt; i++) {
			Bowl[i] = new CandyBowl(CandiesCnt, i);
		}

		TA[] TAs = new TA[TACnt];
		for (int i = 0; i < TACnt; i++) {
			Name = "TA" + i;
			TAs[i] = new TA(Bowl, Name);
		}

		Professor[] Professors = new Professor[ProfCnt];
		for (int i = 0; i < ProfCnt; i++) {
			Name = "Professor" + i;
			Professors[i] = new Professor(Bowl, TAs, SleepTime, Name);
		}

		if(value.equals("Run Forever")){
			for (int i = 0; i < ProfCnt; i++) {
				Professors[i].start();
			}
		}
		else if(value.equals("Enter Runtime")){
			for (int i = 0; i < ProfCnt; i++) {
					Professors[i].start();
			}

			try {
				Thread.sleep(RunTime);
				System.out.println("Time!");
				for (int i = 0; i < ProfCnt; i++) {
					Professors[i].interrupt();
				}
			}
			catch (InterruptedException e) {

			}
		}
	}
}