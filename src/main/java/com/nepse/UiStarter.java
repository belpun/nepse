package com.nepse;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.nepse.data.CompanyData;
import com.nepse.data.NepseDataExtractor;
import com.nepse.exception.DataNotAvailable;
import com.nepse.exception.FileCreationException;
import com.nepse.writer.CsvWriter;

public class UiStarter extends JFrame {

	private static final long serialVersionUID = 7770714940876701913L;

	private NepseDataExtractor nepseDataExtractor;
	private CsvWriter writer;

	public UiStarter() {

		initUI();
		nepseDataExtractor = new NepseDataExtractor();
		writer = new CsvWriter();
	}

	private void initUI() {
		//
		// JPanel pane = (JPanel) getContentPane();
		// GroupLayout gl = new GroupLayout(pane);
		// pane.setLayout(gl);

		// pane.setToolTipText("Content pane");

		setBounds(30, 30, 300, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridLayout grid = new GridLayout(6, 2, 60, 20);
		Container content = getContentPane();
		content.setLayout(grid);

		JLabel directoryLabel = new JLabel("Location for downloaded file:");
		content.add(directoryLabel);

		final JTextField directoryTarget = new JTextField();
		directoryTarget.setText("C:\\tmp");
		content.add(directoryTarget);

		JLabel exportBtnDes = new JLabel(
				"Please press this button to exoprt live:");
		content.add(exportBtnDes);

		JButton livebtn = new JButton("Export Live Data");

		content.add(livebtn);

		JLabel dateLabel = new JLabel("Specific Date (yyyy-MM-dd):");
		content.add(dateLabel);

		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd");

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);

		Date yesterday = cal.getTime();

		String yesterdayFormat = simpleDateFormat.format(yesterday);

		final JTextField dateValue = new JTextField();
		dateValue.setText(yesterdayFormat);
		content.add(dateValue);

		JLabel archivedBtnDes = new JLabel(
				"Please press this button to exoprt archived data:");
		content.add(archivedBtnDes);

		JButton archivedbtn = new JButton("Archived Data");
		content.add(archivedbtn);

		final JLabel errorLabel = new JLabel("");
		content.add(errorLabel);
		errorLabel.setForeground(Color.RED);

		livebtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				errorLabel.setText("");
				String targetLocation = directoryTarget.getText();

				try {
					List<CompanyData> extractLiveData = nepseDataExtractor
							.extractLiveData();
					writer.writeLiveDataToCsvFile(extractLiveData,
							targetLocation, "nepse.csv");
				} catch (FileCreationException exc) {
					errorLabel
							.setText("Cannot create a file due to internal errors");
				} catch (Exception e2) {
					errorLabel.setText("something went wrong "
							+ e2.getMessage());
				}

			}
		});

		archivedbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				errorLabel.setText("");
				String requestedDate = dateValue.getText();
				String targetLocation = directoryTarget.getText();
				try {
					simpleDateFormat.parse(requestedDate);

					List<CompanyData> extractLiveData = nepseDataExtractor
							.extractArchivedData(requestedDate);
					writer.writeArchivedDataToCsvFile(extractLiveData,
							targetLocation, "nepse.csv", requestedDate);

				} catch (ParseException e1) {
					errorLabel
							.setText("not a valid date Please use yyyy-MM-dd . ");
				} catch (DataNotAvailable exc) {
					errorLabel.setText("Data Not Available for this date : "
							+ requestedDate);
				} catch (FileCreationException exc) {
					errorLabel
							.setText("Cannot create a file due to internal errors"
									+ requestedDate);
				} catch (Exception e2) {
					errorLabel.setText("something went wrong "
							+ e2.getMessage());
				}
			}
		});

		pack();

		setTitle("Nepse DataExtractor");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				UiStarter ex = new UiStarter();
				ex.setVisible(true);
			}
		});
	}
}
