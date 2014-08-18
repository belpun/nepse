package com.nepse.starter;

/*
 *
 * Copyright (c) 1998 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license to use,
 * modify and redistribute this software in source and binary code form,
 * provided that i) this copyright notice and license appear on all copies of
 * the software; and ii) Licensee does not utilize the software in a manner
 * which is disparaging to Sun.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE
 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS
 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 * OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 *
 * This software is not designed or intended for use in on-line control of
 * aircraft, air traffic, aircraft navigation or aircraft communications; or in
 * the design, construction, operation or maintenance of any nuclear
 * facility. Licensee represents and warrants that it will not use or
 * redistribute the Software for such purposes.
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/* ComboBoxDemo2.java requires no other files. */
public class ComboBoxDemo2 extends JPanel implements ActionListener {
	static JFrame frame;

	JLabel result;

	String currentPattern;

	public ComboBoxDemo2() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		String[] patternExamples = { "dd MMMMM yyyy", "dd.MM.yy", "MM/dd/yy",
				"yyyy.MM.dd G 'at' hh:mm:ss z", "EEE, MMM d, ''yy", "h:mm a",
				"H:mm:ss:SSS", "K:mm a,z", "yyyy.MMMMM.dd GGG hh:mm aaa" };

		currentPattern = patternExamples[0];

		// Set up the UI for selecting a pattern.
		JLabel patternLabel1 = new JLabel("Enter the pattern string or");
		JLabel patternLabel2 = new JLabel("select one from the list:");

		JComboBox patternList = new JComboBox(patternExamples);
		patternList.setEditable(true);
		patternList.addActionListener(this);

		// Create the UI for displaying result.
		JLabel resultLabel = new JLabel("Current Date/Time", JLabel.LEADING); // ==
																				// LEFT
		result = new JLabel(" ");
		result.setForeground(Color.black);
		result.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.black),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		// Lay out everything.
		JPanel patternPanel = new JPanel();
		patternPanel
				.setLayout(new BoxLayout(patternPanel, BoxLayout.PAGE_AXIS));
		patternPanel.add(patternLabel1);
		patternPanel.add(patternLabel2);
		patternList.setAlignmentX(Component.LEFT_ALIGNMENT);
		patternPanel.add(patternList);

		JPanel resultPanel = new JPanel(new GridLayout(0, 1));
		resultPanel.add(resultLabel);
		resultPanel.add(result);

		patternPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		resultPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

		add(patternPanel);
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(resultPanel);

		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		reformat();
	} // constructor

	public void actionPerformed(ActionEvent e) {
		JComboBox cb = (JComboBox) e.getSource();
		String newSelection = (String) cb.getSelectedItem();
		currentPattern = newSelection;
		reformat();
	}

	/** Formats and displays today's date. */
	public void reformat() {
		Date today = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(currentPattern);
		try {
			String dateString = formatter.format(today);
			result.setForeground(Color.black);
			result.setText(dateString);
		} catch (IllegalArgumentException iae) {
			result.setForeground(Color.red);
			result.setText("Error: " + iae.getMessage());
		}
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("ComboBoxDemo2");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		JComponent newContentPane = new ComboBoxDemo2();
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}