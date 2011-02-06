package NewGUI;

import javax.swing.*;

public class SettingsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Variables declaration - do not modify
	private JButton _bApply;
	private JButton _bCancel;
	private JComboBox _cAgents;
	private JComboBox _cAlgorithm;
	private JComboBox _cDirections;
	private JComboBox _cHeuristic;
	private JSeparator _separator;
	private JLabel _lAgents;
	private JLabel _lAlgorithm;
	private JLabel _lDirections;
	private JLabel _lGridSize;
	private JLabel _lHeuristic;
	private JLabel _lNumOfAgents;
	private JRadioButton _rBlock;
	private JRadioButton _rEnd;
	private JRadioButton _rStart;
	private ButtonGroup _actionGroup;
	private JTextField _tGridSize;
	private JTextField _tNumOfAgents;

	// End of variables declaration

	public SettingsPanel() {
		initComponents();

	}

	private void initComponents() {
		this.setBorder(BorderFactory.createTitledBorder("Settings"));
		_lAlgorithm = new JLabel("Algorithm:");
		_lHeuristic = new JLabel("Heuristic:");
		_cAlgorithm = new JComboBox();
		_cHeuristic = new JComboBox();
		_lDirections = new JLabel("Directions:");
		_cDirections = new JComboBox();
		_separator = new JSeparator();
		_lNumOfAgents = new JLabel("Number of Agents:");
		_tNumOfAgents = new JTextField("", 3);
		_cAgents = new JComboBox();
		_lAgents = new JLabel("Agents:");
		_rStart = new JRadioButton("Start");
		_rEnd = new JRadioButton("End");
		_rBlock = new JRadioButton("Block");
		_actionGroup = new ButtonGroup();
		_lGridSize = new JLabel("Grid Size :");
		_tGridSize = new JTextField("", 3);
		_bApply = new JButton("Apply");
		_bCancel = new JButton("Cancel");

		_cAlgorithm
				.setModel(new DefaultComboBoxModel(new String[] { "A-Star" }));
		_cHeuristic.setModel(new DefaultComboBoxModel(
				new String[] { "Manhattan " }));
		_cDirections.setModel(new DefaultComboBoxModel(new String[] {
				"4 Directions", "8 Directions" }));
		_cAgents.setModel(new DefaultComboBoxModel(new String[] { "Agent 1",
				"Agent 2", "Agent 3", "Agent 4" }));

		_actionGroup.add(_rBlock);
		_actionGroup.add(_rStart);
		_actionGroup.add(_rEnd);
		
		this.add(_lAlgorithm);
		this.add(_cAlgorithm);
		this.add(_lHeuristic);
		this.add(_cHeuristic);
		this.add(_lDirections);
		this.add(_cDirections);
		this.add(_lNumOfAgents);
		this.add(_tNumOfAgents);
		this.add(_rStart);
		this.add(_rEnd);
		this.add(_rBlock);
		this.add(_lGridSize);
		this.add(_tGridSize);
		this.add(_bApply);
		this.add(_bCancel);

		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		_lAlgorithm)
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(
																		_cAlgorithm,
																		GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE))
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		_lHeuristic)
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(
																		_cHeuristic,
																		GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE))
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		_lDirections)
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		_cDirections,
																		GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE))))
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(_lNumOfAgents)
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(_tNumOfAgents,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(_lAgents)
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(_cAgents,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		_lGridSize)
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		_tGridSize,
																		GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE))
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		_rStart)
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(
																		_rEnd)
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		_rBlock))))
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(_bApply)
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(_bCancel))
				.addComponent(_separator, GroupLayout.PREFERRED_SIZE, 188,
						GroupLayout.PREFERRED_SIZE));
		layout.setVerticalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(5,5,5)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
												.addComponent(_lAlgorithm)
												.addComponent(
														_cAlgorithm,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
												.addComponent(_lHeuristic)
												.addComponent(
														_cHeuristic,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
												.addComponent(_lDirections)
												.addComponent(
														_cDirections,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(_separator,
										GroupLayout.PREFERRED_SIZE, 10,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
												.addComponent(_lNumOfAgents)
												.addComponent(
														_tNumOfAgents,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
												.addComponent(_lAgents)
												.addComponent(
														_cAgents,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
												.addComponent(_rStart)
												.addComponent(_rEnd)
												.addComponent(_rBlock))
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
												.addComponent(_lGridSize)
												.addComponent(
														_tGridSize,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addGap(18, 18, 18)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
												.addComponent(_bApply)
												.addComponent(_bCancel))
								.addContainerGap(34, Short.MAX_VALUE)));
	}

}
