import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Robot;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class bard implements NativeKeyListener {

	public static void waiting(int n) {

		long t0, t1;

		t0 = System.currentTimeMillis();

		do {
			t1 = System.currentTimeMillis();
		} while ((t1 - t0) < (n * 10));

	}
	
	public void pressKeyAndRelease(int key, Robot r) {
		int tw1 = 5;
		int tw2 = 10;

		r.keyPress(key);
		waiting(tw1);
		r.keyRelease(key);
		waiting(tw2);
	}
	
	public void playSong(int keys[], Robot r) {
		for(int i=0; i < keys.length; i++) {
			pressKeyAndRelease(keys[i], r);
		}
	}
	
	public List<String> readSongs() {
		List<String> songs = new ArrayList<String>();
		try {
			File book = new File("songs.txt");
			Scanner bookReader = new Scanner(book);
 			while (bookReader.hasNextLine()) {
				songs.add(bookReader.nextLine());
			}
			bookReader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return songs;
	}

	public void setKey(int[] song, int i, String key) {
		if(key.equals("1")) {
			song[i] = KeyEvent.VK_1;
		} else if(key.equals("2")) {
			song[i] = KeyEvent.VK_2;
		} else if(key.equals("3")) {
			song[i] = KeyEvent.VK_3;
		} else if(key.equals("4")) {
			song[i] = KeyEvent.VK_4;
		} else if(key.equals("5")) {
			song[i] = KeyEvent.VK_5;
		} else if(key.equals("6")) {
			song[i] = KeyEvent.VK_6;
		} else if(key.equals("7")) {
			song[i] = KeyEvent.VK_7;
		} else if(key.equals("8")) {
			song[i] = KeyEvent.VK_8;
		} else if(key.equals("9")) {
			song[i] = KeyEvent.VK_9;
		}
	}
	
	public void nativeKeyPressed(NativeKeyEvent e) {
		if (NativeKeyEvent.getKeyText(e.getKeyCode()).equals("F1")) {
			try {
				List<String> songs = readSongs();
				int[] song = new int[songs.get(0).length()];
				for(int i = 0; i < songs.get(0).length(); i++) {
					setKey(song, i, Character.toString(songs.get(0).charAt(i)));
				}
				playSong(song, new Robot());
				
			} catch (Exception awtE) {

			}
		} else if (NativeKeyEvent.getKeyText(e.getKeyCode()).equals("F3")) {
			try {
				List<String> songs = readSongs();
				int[] song = new int[songs.get(1).length()];
				for(int i = 0; i < songs.get(1).length(); i++) {
					setKey(song, i, Character.toString(songs.get(1).charAt(i)));
				}
				playSong(song, new Robot());
			} catch (Exception awtE) {

			}
		} else if (NativeKeyEvent.getKeyText(e.getKeyCode()).equals("F4")) {
			try {
				List<String> songs = readSongs();
				int[] song = new int[songs.get(2).length()];
				for(int i = 0; i < songs.get(2).length(); i++) {
					setKey(song, i, Character.toString(songs.get(2).charAt(i)));
				}
				playSong(song, new Robot());
			} catch (Exception awtE) {

			}
		} else if (NativeKeyEvent.getKeyText(e.getKeyCode()).equals("F5")) {
			try {
				List<String> songs = readSongs();
				int[] song = new int[songs.get(3).length()];
				for(int i = 0; i < songs.get(3).length(); i++) {
					setKey(song, i, Character.toString(songs.get(3).charAt(i)));
				}
				playSong(song, new Robot());
			} catch (Exception awtE) {

			}
		} else if (NativeKeyEvent.getKeyText(e.getKeyCode()).equals("F8")) {
			try {

			} catch (Exception awtE) {

			}
		}
	}

	public void nativeKeyTyped(NativeKeyEvent e) {

	}

	public void nativeKeyReleased(NativeKeyEvent e) {

	}

	public static void main(String[] args) {
		Image image = Toolkit.getDefaultToolkit().getImage(bard.class.getResource("trayicon.png") ); 

		PopupMenu popup = new PopupMenu();
		MenuItem item = new MenuItem("Ende");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		popup.add(item);

		TrayIcon trayIcon = new TrayIcon(image, "Java-Tray ", popup);
		trayIcon.setImageAutoSize(true);

		SystemTray tray = SystemTray.getSystemTray();
		try {
			tray.add(trayIcon);
		} catch (AWTException e1) {
			e1.printStackTrace();
		}
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());

			System.exit(1);
		}
		GlobalScreen.getInstance().addNativeKeyListener(new bard());
	}
}
