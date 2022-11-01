package com.eclipse.logging;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main {

	public static void main(String[] args) {

		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout(1, true));


		BuildCmps buildCmps = new BuildCmps(shell);
		buildCmps.showComposites();

		shell.open(); 
		shell.setText("Read logs from file");
//		shell.setSize(600, 400);
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
 
		}
		display.dispose();

	}
}
