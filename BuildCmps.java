package com.eclipse.logging;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.eclipse.logging.parsing.Parser;

public class BuildCmps {

	private Shell shell;
	private Composite cmpLeft;
	private Label lbl;
	private Button button;
	private Composite cmpRight;
	private TableViewer tableViewer;

	private List<Log> list;
	private Composite cmpTop;
	private Composite cmpBottom;
	private ComboViewer comboViewer;
	private Label lblBot1;
	private Label lblBot2;
	private Text txtBottom;
	private Parser parser;
	private ProgressBar pb1;

	public BuildCmps(Shell shell) {
		this.shell = shell;
	}

	public void showComposites() {
		createLeftComposite();
		createRightComposite();
		createBottomComposite();
	}

	public void createLeftComposite() {

		cmpTop = new Composite(shell, SWT.BORDER);
		cmpTop.setLayout(new GridLayout(2, false));
		GridData cmpTop_gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		cmpTop.setLayoutData(cmpTop_gd);

		cmpLeft = new Composite(cmpTop, SWT.BORDER);
		cmpLeft.setLayout(new GridLayout(1, false));
		cmpLeft.setLayoutData(new GridData());

		lbl = new Label(cmpLeft, SWT.NONE);
		lbl.setText("Press and read file:");

		button = new Button(cmpLeft, SWT.PUSH);
		button.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		button.setText("Push");
		button.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(shell, SWT.NULL);
				dialog.setText("FileDialog Demo");
				String selectedFile = dialog.open();
				if (selectedFile != null) {
					System.out.println("User selected : " + selectedFile);
				
					pb1.setMinimum(0);
					pb1.setMaximum(1250);
					pb1.setSelection(0);
					pb1.setSelection(1250);
					
					readFromFile(selectedFile);
				} else {
					System.out.println("User didn't select file");
					pb1.setSelection(0);
				}

			}
		});
	}

	public void createRightComposite() {

		cmpRight = new Composite(cmpTop, SWT.BORDER);
		TableColumnLayout tableColumnLayout = new TableColumnLayout();
		cmpRight.setLayout(tableColumnLayout);
		GridData cmpRiht_gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2);
		cmpRiht_gd.heightHint = 350;
		cmpRight.setLayoutData(cmpRiht_gd);

		tableViewer = new TableViewer(cmpRight,
				SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		tableViewer.setLabelProvider(new LabelProvider());
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());

		TableViewerColumn column1 = new TableViewerColumn(tableViewer, SWT.CENTER | SWT.FILL);
		tableColumnLayout.setColumnData(column1.getColumn(), new ColumnWeightData(20, 100, true));
		column1.getColumn().setResizable(true);
		column1.getColumn().setMoveable(true);
		column1.getColumn().setText("DateTime");
		column1.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Log log = (Log) element;
//				return log.getDate() +" " + log.getTime();
				return String.valueOf(log.getDateTime());

			}
		});
		TableViewerColumn column2 = new TableViewerColumn(tableViewer, SWT.CENTER | SWT.FILL);
		tableColumnLayout.setColumnData(column2.getColumn(), new ColumnWeightData(20, 100, true));
		column2.getColumn().setResizable(true);
		column2.getColumn().setMoveable(true);
		column2.getColumn().setText("Info");
		column2.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Log log = (Log) element;
				return log.getInfo();
			}
		});

		TableViewerColumn column3 = new TableViewerColumn(tableViewer, SWT.CENTER | SWT.FILL);
		tableColumnLayout.setColumnData(column3.getColumn(), new ColumnWeightData(20, 100, true));
		column3.getColumn().setResizable(true);
		column3.getColumn().setMoveable(true);
		column3.getColumn().setText("Class");
		column3.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Log log = (Log) element;
				return log.getKlash();
			}
		});

		TableViewerColumn column4 = new TableViewerColumn(tableViewer, SWT.CENTER | SWT.FILL);
		tableColumnLayout.setColumnData(column4.getColumn(), new ColumnWeightData(20, 100, true));
		column4.getColumn().setResizable(true);
		column4.getColumn().setMoveable(true);
		column4.getColumn().setText("Thread");
		column4.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Log log = (Log) element;
				String threadId = String.valueOf(log.getThreadId());
				return threadId;
			}
		});
		TableViewerColumn column5 = new TableViewerColumn(tableViewer, SWT.CENTER | SWT.FILL);
		tableColumnLayout.setColumnData(column5.getColumn(), new ColumnWeightData(20, 100, true));
		column5.getColumn().setResizable(true);
		column5.getColumn().setMoveable(true);
		column5.getColumn().setText("IP");
		column5.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Log log = (Log) element;
				return log.getIp();
			}
		});
		TableViewerColumn column6 = new TableViewerColumn(tableViewer, SWT.CENTER | SWT.FILL);
		tableColumnLayout.setColumnData(column6.getColumn(), new ColumnWeightData(20, 100, true));
		column6.getColumn().setResizable(true);
		column6.getColumn().setMoveable(true);
		column6.getColumn().setText("Port");
		column6.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Log log = (Log) element;
				String port = String.valueOf(log.getPort());
				return port;
			}
		});
		TableViewerColumn column7 = new TableViewerColumn(tableViewer, SWT.CENTER | SWT.FILL);
		tableColumnLayout.setColumnData(column7.getColumn(), new ColumnWeightData(20, 100, true));
		column7.getColumn().setResizable(true);
		column7.getColumn().setMoveable(true);
		column7.getColumn().setText("Message");
		column7.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Log log = (Log) element;
				return log.getMessage();
			}
		});

//		tableViewer.getControl().addControlListener(new ControlListener() {
//
//			@Override
//			public void controlResized(ControlEvent arg0) {
//				Rectangle rect = tableViewer.getTable().getClientArea();
//				if (rect.width > 0) {
//					int extraSpace = rect.width / 7;
//					column1.getColumn().setWidth(extraSpace);
//					column2.getColumn().setWidth(extraSpace);
//					column3.getColumn().setWidth(extraSpace);
//					column4.getColumn().setWidth(extraSpace);
//					column5.getColumn().setWidth(extraSpace);
//					column6.getColumn().setWidth(extraSpace);
//					column7.getColumn().setWidth(extraSpace);
//
//				}
//			}
//
//			@Override
//			public void controlMoved(ControlEvent arg0) {
//				// TODO Auto-generated method stub
//			}
//		});

		tableViewer.getTable().setHeaderVisible(true);
		tableViewer.getTable().setLinesVisible(true);
	}

	public void createBottomComposite() {

		cmpBottom = new Composite(shell, SWT.BORDER);
		cmpBottom.setLayout(new GridLayout(1, false));
		GridData cmpBottom_gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		cmpBottom.setLayoutData(cmpBottom_gd);

		pb1 = new ProgressBar(cmpBottom, SWT.HORIZONTAL | SWT.SMOOTH);
		pb1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		
		lblBot1 = new Label(cmpBottom, SWT.NONE);
		lblBot1.setText("Choose the class");

		comboViewer = new ComboViewer(cmpBottom, SWT.DROP_DOWN);
		comboViewer.setContentProvider(ArrayContentProvider.getInstance());
		GridData combo_gd = new GridData(SWT.FILL, SWT.FILL, false, false);
		combo_gd.widthHint = 400;
		comboViewer.getCombo().setLayoutData(combo_gd);
		comboViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {

				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				String selectedObject = (String) selection.getFirstElement();

				System.out.println("Counter: " + parser.getCounter(selectedObject));
				txtBottom.setText(String.valueOf(parser.getCounter(selectedObject)));
 

			}
		});

		lblBot2 = new Label(cmpBottom, SWT.NONE);
		lblBot2.setText("Counter: ");

		txtBottom = new Text(cmpBottom, SWT.NONE);
	

		

	}

	public void readFromFile(String selectedFile) {

		parser = new Parser(selectedFile);
		try {

//			List<Log> 
			list = parser.parse();
			comboViewer.setInput(parser.getAvailableClassesSet());
			tableViewer.setInput(list);

		} catch (FileNotFoundException e) {

			System.out.println("The file: " + selectedFile + " was not found...");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
