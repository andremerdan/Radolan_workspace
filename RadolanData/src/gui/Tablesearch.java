package gui;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.sun.jersey.core.impl.provider.entity.XMLJAXBElementProvider.Text;

import threads.TextOutputCreationThread;

public class Tablesearch extends JPanel{
	
	public Tablesearch(){
		
		JTable table = new JTable(TextOutputCreationThread.model);
		// size of columns
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(180);
		columnModel.getColumn(1).setPreferredWidth(40);
		columnModel.getColumn(2).setPreferredWidth(40);
		columnModel.getColumn(3).setPreferredWidth(100);
		table.setAutoCreateRowSorter(true);
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.gridheight = 19;
		gbc_table.insets = new Insets(0, 0, 5, 5);
		gbc_table.gridx = 1;
		gbc_table.gridy = 3;
		gbc_table.insets = new Insets(10, 3, 3, 3);
		add(table, gbc_table);
	
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane);
	}
	
}
