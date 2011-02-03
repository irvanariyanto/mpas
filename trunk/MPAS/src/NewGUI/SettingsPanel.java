package NewGUI;

import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.border.TitledBorder;

public class SettingsPanel extends JPanel {

	// Variables declaration - do not modify
	private javax.swing.JButton bApply;
	private javax.swing.JButton bCancel;
	private javax.swing.ButtonGroup buttonGroup1;
	private javax.swing.JComboBox cAgents;
	private javax.swing.JComboBox cAlgorithm;
	private javax.swing.JComboBox cDirections;
	private javax.swing.JComboBox cHeuristic;
	private javax.swing.JSeparator separator;
	private javax.swing.JLabel lAgents;
	private javax.swing.JLabel lAlgorithm;
	private javax.swing.JLabel lDirections;
	private javax.swing.JLabel lGridSize;
	private javax.swing.JLabel lHeuristic;
	private javax.swing.JLabel lNumOfAgents;
	private javax.swing.JRadioButton rBlock;
	private javax.swing.JRadioButton rEnd;
	private javax.swing.JRadioButton rStart;
	private javax.swing.JTextField tGridSize;
	private javax.swing.JTextField tNumOfAgents;

	// End of variables declaration

	public SettingsPanel() {
		initComponents();

	}

	private void initComponents() {
		this.setBorder(BorderFactory.createTitledBorder("Settings"));
		buttonGroup1 = new javax.swing.ButtonGroup();
		lAlgorithm = new javax.swing.JLabel("Algorithm:");
		lHeuristic = new javax.swing.JLabel("Heuristic:");
		cAlgorithm = new javax.swing.JComboBox();
		cHeuristic = new javax.swing.JComboBox();
		lDirections = new javax.swing.JLabel("Directions:");
		cDirections = new javax.swing.JComboBox();
		separator = new javax.swing.JSeparator();
		lNumOfAgents = new javax.swing.JLabel("Number of Agents:");		
		tNumOfAgents = new javax.swing.JTextField();
		cAgents = new javax.swing.JComboBox();
		lAgents = new javax.swing.JLabel("Agents:");
		rStart = new javax.swing.JRadioButton("Start");
		rEnd = new javax.swing.JRadioButton("End");
		rBlock = new javax.swing.JRadioButton("Block");
		lGridSize = new javax.swing.JLabel("Grid Size :");
		tGridSize = new javax.swing.JTextField();
		bApply = new javax.swing.JButton("Apply");
		bCancel = new javax.swing.JButton("Cancel");


		cAlgorithm.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"A-Star" }));
		cHeuristic.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Manhattan "}));
		cDirections.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"4 Directions", "8 Directions" }));
		cAgents.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Agent 1", "Agent 2", "Agent 3", "Agent 4" }));
		this.add(lAlgorithm);
		this.add(cAlgorithm);
		this.add(lHeuristic);
		this.add(cHeuristic);
		this.add(lDirections);
		this.add(cDirections);
		this.add(lNumOfAgents);
		this.add(tNumOfAgents);
		this.add(rStart);
		this.add(rEnd);
		this.add(rBlock);
		this.add(lGridSize);
		this.add(tGridSize);
		this.add(bApply);
		this.add(bCancel);
		
		GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lAlgorithm)
                        .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cAlgorithm,  GroupLayout.PREFERRED_SIZE,  GroupLayout.DEFAULT_SIZE,  GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lHeuristic)
                        .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cHeuristic,  GroupLayout.PREFERRED_SIZE,  GroupLayout.DEFAULT_SIZE,  GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lDirections)
                        .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cDirections,  GroupLayout.PREFERRED_SIZE,  GroupLayout.DEFAULT_SIZE,  GroupLayout.PREFERRED_SIZE))))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lNumOfAgents)
                .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tNumOfAgents,  GroupLayout.PREFERRED_SIZE,  GroupLayout.DEFAULT_SIZE,  GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lAgents)
                .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cAgents,  GroupLayout.PREFERRED_SIZE,  GroupLayout.DEFAULT_SIZE,  GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup( GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lGridSize)
                        .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tGridSize,  GroupLayout.PREFERRED_SIZE,  GroupLayout.DEFAULT_SIZE,  GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(rStart)
                        .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rEnd)
                        .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rBlock))))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bApply)
                .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bCancel))
            .addComponent(separator,  GroupLayout.PREFERRED_SIZE, 188,  GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup( GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup( GroupLayout.Alignment.BASELINE)
                    .addComponent(lAlgorithm)
                    .addComponent(cAlgorithm,  GroupLayout.PREFERRED_SIZE,  GroupLayout.DEFAULT_SIZE,  GroupLayout.PREFERRED_SIZE))
                .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup( GroupLayout.Alignment.BASELINE)
                    .addComponent(lHeuristic)
                    .addComponent(cHeuristic,  GroupLayout.PREFERRED_SIZE,  GroupLayout.DEFAULT_SIZE,  GroupLayout.PREFERRED_SIZE))
                .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup( GroupLayout.Alignment.BASELINE)
                    .addComponent(lDirections)
                    .addComponent(cDirections,  GroupLayout.PREFERRED_SIZE,  GroupLayout.DEFAULT_SIZE,  GroupLayout.PREFERRED_SIZE))
                .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(separator,  GroupLayout.PREFERRED_SIZE, 10,  GroupLayout.PREFERRED_SIZE)
                .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup( GroupLayout.Alignment.BASELINE)
                    .addComponent(lNumOfAgents)
                    .addComponent(tNumOfAgents,  GroupLayout.PREFERRED_SIZE,  GroupLayout.DEFAULT_SIZE,  GroupLayout.PREFERRED_SIZE))
                .addPreferredGap( LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup( GroupLayout.Alignment.BASELINE)
                    .addComponent(lAgents)
                    .addComponent(cAgents,  GroupLayout.PREFERRED_SIZE,  GroupLayout.DEFAULT_SIZE,  GroupLayout.PREFERRED_SIZE))
                .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup( GroupLayout.Alignment.BASELINE)
                    .addComponent(rStart)
                    .addComponent(rEnd)
                    .addComponent(rBlock))
                .addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup( GroupLayout.Alignment.BASELINE)
                    .addComponent(lGridSize)
                    .addComponent(tGridSize,  GroupLayout.PREFERRED_SIZE,  GroupLayout.DEFAULT_SIZE,  GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup( GroupLayout.Alignment.BASELINE)
                    .addComponent(bApply)
                    .addComponent(bCancel))
                .addContainerGap(34, Short.MAX_VALUE))
        );
    }
	

}
