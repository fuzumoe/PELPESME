package com.sfb805.application;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;

import java.net.MalformedURLException;
import java.net.URL;

import static javafx.concurrent.Worker.State.FAILED;

public class NGBrowser extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JFXPanel jfxPanel = new JFXPanel();
	private WebEngine engine;

	private final JPanel panel = new JPanel(new BorderLayout());
	private final JLabel lblStatus = new JLabel();

	private final JProgressBar progressBar = new JProgressBar();
	private String url;

	public NGBrowser() {
		initComponents();
	}
	public NGBrowser(String url) {
		this.setUrl(url);
		this.loadURL();
		initComponents();
	}

	public String getUrl() {
		
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setLookAndFeel() {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(NGBrowser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(NGBrowser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(NGBrowser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(NGBrowser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
	}

	private void initComponents() {
		setLookAndFeel();

		createScene();

		progressBar.setPreferredSize(new Dimension(150, 18));
		progressBar.setStringPainted(true);

		JPanel topBar = new JPanel(new BorderLayout(5, 0));
		topBar.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));

		JPanel statusBar = new JPanel(new BorderLayout(5, 0));
		statusBar.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
		statusBar.add(lblStatus, BorderLayout.CENTER);
		statusBar.add(progressBar, BorderLayout.EAST);

		panel.add(topBar, BorderLayout.NORTH);
		panel.add(jfxPanel, BorderLayout.CENTER);
		panel.add(statusBar, BorderLayout.SOUTH);

		getContentPane().add(panel);

		setPreferredSize(new Dimension(1024, 600));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();

	}

	private void createScene() {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {

				WebView view = new WebView();
				engine = view.getEngine();

				engine.titleProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue,
							final String newValue) {
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								NGBrowser.this.setTitle(newValue);

							}
						});
					}
				});

				engine.setOnStatusChanged(new EventHandler<WebEvent<String>>() {
					@Override
					public void handle(final WebEvent<String> event) {
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								lblStatus.setText(event.getData());
								 
							}
						});
					}
				});

				engine.getLoadWorker().workDoneProperty().addListener(new ChangeListener<Number>() {
					@Override
					public void changed(ObservableValue<? extends Number> observableValue, Number oldValue,
							final Number newValue) {
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
							
								progressBar.setValue(newValue.intValue());
								if (progressBar.getValue() == 100) 
									progressBar.setVisible(false);
								
							}
						});
					}
				});

				engine.getLoadWorker().exceptionProperty().addListener(new ChangeListener<Throwable>() {

					public void changed(ObservableValue<? extends Throwable> o, Throwable old, final Throwable value) {
						if (engine.getLoadWorker().getState() == FAILED) {
							SwingUtilities.invokeLater(new Runnable() {
								@Override
								public void run() {
									JOptionPane.showMessageDialog(panel,
											(value != null) ? engine.getLocation() + "\n" + value.getMessage()
													: engine.getLocation() + "\nUnexpected error.",
											"Loading error...", JOptionPane.ERROR_MESSAGE);
								}
							});
						}
					}
				});

				jfxPanel.setScene(new Scene(view));
			}
		});
	}
   public void loadURL(){
	   Platform.runLater(new Runnable() {
			@Override
			public void run() {
				engine.load(getUrl());
			}
		});
	   
   }
	public void loadURL(final String url) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				String tmp = toURL(url);

				if (tmp == null) {
					tmp = toURL("http://" + url);
				}

				engine.load(tmp);
			}
		});
	}

	private static String toURL(String str) {
		try {
			return new URL(str).toExternalForm();
		} catch (MalformedURLException exception) {
			return null;
		}
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {

				NGBrowser browser = new NGBrowser();
				browser.setVisible(true);
				browser.loadURL("file:///home/adam/Desktop/PELOPS/index.html#foaf");
			}
		});
	}
}
