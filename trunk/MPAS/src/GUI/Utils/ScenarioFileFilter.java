package GUI.Utils;


import java.io.File;
import javax.swing.filechooser.*;

public class ScenarioFileFilter extends FileFilter{

	@Override
	public boolean accept(File file) {
		if (file.isDirectory())
			return true;
		String extension = Utils.getExtension(file);
		if (extension !=null)
			return extension.equals("xml");
		else{
			return false;
		}
	}

	@Override
	public String getDescription() {
		return ".xml";
	}
}
