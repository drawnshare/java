package com.esgi.vMail.view_controler;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Theme {
	public static class Description extends Properties{
		/**
		 *
		 */
		private static final long serialVersionUID = 2483939568997704491L;
		public Description(Path path) throws IOException {
			this.load(new FileInputStream(path.toString()));
		}
		public String getName() {
			return this.getProperty("name");
		}
		public String getLowerName() {
			return this.getName().toLowerCase();
		}
		public String getVersion() {
			return (this.getProperty("ver") != null)? this.getProperty("ver") : this.getProperty("version");
		}
		public String getDescription() {
			return (this.getProperty("desc") != null)? this.getProperty("desc") : this.getProperty("description");
		}
		public String getConnectionIcon() {
			return this.getProperty("connectionsIcon");
		}
		public String getDisplayIcon() {
			return this.getProperty("displayIcon");
		}
		public String getCSS() {
			return this.getProperty("css");
		}
	}
	public final static Path rootPath = Paths.get("themes");
	public static void checkIfRootExist() throws IOException {
		if (!Files.exists(rootPath)) {
			Files.createDirectory(rootPath, null);
		}
	}
	private static ArrayList<Theme> getThemes() throws IOException {
		ArrayList<Theme> themes = new ArrayList<>();
		for (Path path : Theme.getSubPath(rootPath)) {
			themes.add(new Theme(path));
		}
		return themes;
	}

	private static List<Path> getSubPath(Path path) throws IOException {
		ArrayList<Path> listPath = new ArrayList<>();
		Files.newDirectoryStream(path).forEach((subPath)->{
			listPath.add(subPath);
		});
		return listPath;
	}
	// End of static block
	public Theme(Path path) {
		this.mainPath = path;
		try {
			for (Path file : Theme.getSubPath(mainPath)) {
				if (file.endsWith("description.properties")) {
					this.description = new Description(file);
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean load() throws FileNotFoundException {
		this.connectionsIcon = new Image(new FileInputStream(new File(this.description.getConnectionIcon())));
		this.displayIcon = new Image(new FileInputStream(new File(this.description.getDisplayIcon())));
		this.pathCSS = Paths.get(this.description.getCSS());
		return (connectionsIcon != null) || (displayIcon != null);
	}

	private Path mainPath;
	private Description description;
	private Image connectionsIcon;
	private Image displayIcon;
	private Path pathCSS;
	/**
	 * @return the rootpath
	 */
	public static Path getRootpath() {
		return rootPath;
	}
	/**
	 * @return the mainPath
	 */
	public Path getMainPath() {
		return mainPath;
	}
	/**
	 * @return the description
	 */
	public Description getDescription() {
		return description;
	}
	/**
	 * @return the connectionsIcon
	 */
	public Image getConnectionsIcon() {
		return connectionsIcon;
	}
	/**
	 * @return the displayIcon
	 */
	public Image getDisplayIcon() {
		return displayIcon;
	}
	/**
	 * @return the pathCSS
	 */
	public Path getPathCSS() {
		return pathCSS;
	}
}
