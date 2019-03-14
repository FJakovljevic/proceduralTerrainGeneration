package terrains;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Hashtable;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.lwjgl.opengl.GL11;

import net.miginfocom.swing.MigLayout;
import renderengine.TerrainRenderer;

public class SettingsFrame extends JFrame {

	public SettingsFrame(HeightGenerator generator, Terrain terrain) {
		
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		setUndecorated(true);
		setSize((int) 300, (int)screenSize.getHeight());
		setLocation((int)screenSize.getWidth() - this.getWidth(), 1);
		setResizable(false);
		setLayout(new MigLayout());
		
		JLabel funNum = new JLabel("Number of functions");
		funNum.setFont(new Font("Monospaced", Font.BOLD, 17));
		JSlider numberOfFunctions = new JSlider(JSlider.HORIZONTAL, 1, 7, 4);
		numberOfFunctions.setMajorTickSpacing(1);
		numberOfFunctions.setPaintTicks(true);
		numberOfFunctions.setPaintLabels(true);
		numberOfFunctions.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				HeightGenerator.setBrojFunkcija( (int) ((JSlider)e.getSource()).getValue() );
			}
		});
		add(funNum, "center, height 10mm, pushX, wrap");
		add(numberOfFunctions, "center, pushX, wrap");
		
		
		JLabel dummping = new JLabel("Dummping");
		dummping.setFont(new Font("Monospaced", Font.BOLD, 17));
		JSlider dummpingJS = new JSlider(JSlider.HORIZONTAL, 0, 100, 40);
		Hashtable<Integer, JLabel> labelTable1 = new Hashtable<Integer, JLabel>();
			labelTable1.put( new Integer( 0 ), new JLabel("0.0") );
			labelTable1.put( new Integer( 50 ), new JLabel("0.5") );
			labelTable1.put( new Integer( 100 ), new JLabel("1.0") );
			
		dummpingJS.setLabelTable( labelTable1 );
		dummpingJS.setMajorTickSpacing(50);
		dummpingJS.setPaintTicks(true);
		dummpingJS.setPaintLabels(true);
		dummpingJS.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
			HeightGenerator.setUpijac( (float) ((JSlider)e.getSource()).getValue()/100 );
			}
		});
		add(dummping, "center, height 10mm, pushX, wrap");
		add(dummpingJS, "center, pushX, wrap");
		
		
		JLabel frequency = new JLabel("Frequency");
		frequency.setFont(new Font("Monospaced", Font.BOLD, 17));
		JSlider frequencyJS = new JSlider(JSlider.HORIZONTAL, 10, 50, 20);
		Hashtable<Integer, JLabel> labelTable2 = new Hashtable<Integer, JLabel>();
			labelTable2.put( new Integer( 10 ), new JLabel("1") );
			labelTable2.put( new Integer( 20 ), new JLabel("2") );
			labelTable2.put( new Integer( 30 ), new JLabel("3") );
			labelTable2.put( new Integer( 40 ), new JLabel("4") );
			labelTable2.put( new Integer( 50 ), new JLabel("5") );
		
		frequencyJS.setLabelTable( labelTable2 );
		frequencyJS.setMajorTickSpacing(10);
		frequencyJS.setPaintTicks(true);
		frequencyJS.setPaintLabels(true);
		frequencyJS.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
			HeightGenerator.setFrekvencija( (float) ((JSlider)e.getSource()).getValue()/10 );
			}
		});
		add(frequency, "center, height 10mm, pushX, wrap");
		add(frequencyJS, "center, pushX, wrap");
		
		JLabel amplitude = new JLabel("Amplitude");
		amplitude.setFont(new Font("Monospaced", Font.BOLD, 17));
		JSlider amplitudeJS = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
		amplitudeJS.setMajorTickSpacing(20);
		amplitudeJS.setPaintTicks(true);
		amplitudeJS.setPaintLabels(true);
		amplitudeJS.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
			HeightGenerator.setAMPLITUDE((int) ((JSlider)e.getSource()).getValue() );
			}
		});
		add(amplitude, "center, height 10mm, pushX, wrap");
		add(amplitudeJS, "center, pushX, wrap");
		
		JLabel interp = new JLabel("Interpolation method");
		interp.setFont(new Font("Monospaced", Font.BOLD, 17));
		String[] interps = { "Smoothinterp", "Cosinterp", "Linterp", "Stepinterp"};
		JComboBox<String> interpsList = new JComboBox<String>(interps);
		interpsList.setSelectedIndex(0);
		interpsList.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String str = (String) ((JComboBox)e.getSource()).getSelectedItem();
				HeightGenerator.setIzborFunkcije(str);
			}
		});
		add(interp, "center, height 10mm, pushX, wrap");
		add(interpsList, "center, pushX, wrap");
		
		JLabel vertex = new JLabel("Vertex count");
		vertex.setFont(new Font("Monospaced", Font.BOLD, 17));
		JSlider vertexJS = new JSlider(JSlider.HORIZONTAL, 100, 500, 100);
		vertexJS.setMajorTickSpacing(100);
		vertexJS.setPaintTicks(true);
		vertexJS.setPaintLabels(true);
		vertexJS.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				terrain.setVERTEX_COUNT((int) ((JSlider)e.getSource()).getValue() );
			}
		});
		JButton vertexJB = new JButton("Change");
		vertexJB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				terrain.setFlag(1);
			}
		});
		add(vertex, "center, height 10mm, pushX, wrap");
		add(vertexJS, "center, pushX, split");
		add(vertexJB, "wrap");
		
		

		JLabel color = new JLabel("  Color");
		color.setFont(new Font("Monospaced", Font.BOLD, 17));
		JRadioButton colorJRB = new JRadioButton();
		colorJRB.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					terrain.setColor(1);
			    }
			    else if (e.getStateChange() == ItemEvent.DESELECTED) {
			    	terrain.setColor(0);
			    }
				terrain.setFlag(1);
			}
		});
		
		add(color, "height 10mm, pushX, split");
		add(colorJRB, "height 10mm, pushX, wrap");
		
		
		JLabel lines = new JLabel("  Lines");
		lines.setFont(new Font("Monospaced", Font.BOLD, 17));
		JRadioButton linesJRB = new JRadioButton();
		linesJRB.setSelected(true);
		linesJRB.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					TerrainRenderer.setMode(GL11.GL_LINES);
			    }
				terrain.setFlag(1);
			}
		});
		JLabel trianlges = new JLabel("  Triangles");
		trianlges.setFont(new Font("Monospaced", Font.BOLD, 17));
		JRadioButton trianlgesJRB = new JRadioButton();
		trianlgesJRB.setFont(new Font("Monospaced", Font.BOLD, 17));
		trianlgesJRB.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					TerrainRenderer.setMode(GL11.GL_TRIANGLES);
			    }
				terrain.setFlag(1);
			}
		});
		ButtonGroup group = new ButtonGroup();
		    group.add(linesJRB);
		    group.add(trianlgesJRB);
		
		add(lines, "height 10mm, pushX, split");
		add(linesJRB, "height 10mm, pushX");
		add(trianlges, "height 10mm, pushX, split");
		add(trianlgesJRB, "height 10mm, pushX, wrap");
		
		
		JLabel numericalFun = new JLabel("Numerical fun: ");
		numericalFun.setFont(new Font("Monospaced", Font.BOLD, 17));
		String[] funs = { "function1", "function2"};
		JComboBox<String> funsCB = new JComboBox<String>(funs);
		funsCB.setSelectedIndex(0);
		funsCB.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String str = (String) ((JComboBox)e.getSource()).getSelectedItem();
				if(str.equals("function1"))
					HeightGenerator.setNacinRacunanjea(1);
				else
					HeightGenerator.setNacinRacunanjea(0);
			}
		});
		add(numericalFun, "center, height 10mm, pushX, split");
		add(funsCB, "center, pushX, wrap");
		
		
		
		add(new JLabel(""));
		
		
		revalidate();
		repaint();
		
	}
	
	
	
}
