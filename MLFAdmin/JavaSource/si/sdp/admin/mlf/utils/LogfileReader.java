package si.sdp.admin.mlf.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import si.sdp.admin.mlf.DAO.LogfileBean;

public class LogfileReader {

	public LogfileReader() {
	}

	public static void main(String[] args) {
		LogfileReader test = new LogfileReader();
		try {
			
			ArrayList<LogfileBean> tmplist = test.getLogfiles();
			for(int x=0;x<tmplist.size();x++){
				System.out.println(tmplist.get(x).getLogfilepath());
			}
			
			
			//System.out.println(test.getLogfile("/home/janmj/java/jboss-6.1.0.SDP/server/default/log/boot.log"));
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public ArrayList<LogfileBean> getLogfiles()throws Exception{
		ArrayList<LogfileBean> retval = new ArrayList<LogfileBean>();
		try{
			PropertiesServices prop = new PropertiesServices();
			File file = new File(prop.getPropertyValue("LOGFILE_PATH"));
			//File file = new File("/home/janmj/java/jboss-6.1.0.SDP/server/default/log/");			
			if(file.isDirectory()){
				File files[] = dirListAcendingbyDate(file);
				for(int x=0;x<file.listFiles().length;x++){
					LogfileBean tmplog = new LogfileBean();
					File tmpfile = files[x];
					tmplog.setLogfilename(tmpfile.getName());
					tmplog.setLogfilepath(tmpfile.getAbsolutePath());
					retval.add(tmplog);
				}
			}
		}catch (Exception e){
			throw new Exception(e);
		}
		return retval;
	}
	
	public String getLogfile(String path)throws Exception{
		String retval = null;
		File Tfile = new File(path);
		StringBuilder innhold = new StringBuilder();
		try {
			BufferedReader input = new BufferedReader(new FileReader(Tfile));
			try {
				String strline = null;
				while((strline = input.readLine())!=null){
					innhold.append(strline);
					innhold.append(System.getProperty("line.separator"));
				}
				retval = innhold.toString();
			}finally{
				input.close();
			}
		} catch (Exception e) {
			throw new Exception("Feilet i getLogfile " + e.getMessage());
		}
		return retval;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static File[] dirListAcendingbyDate(File folder){
		if(!folder.isDirectory()){
			return null;
		}
		File files[] = folder.listFiles();
		Arrays.sort(files, new Comparator() {
			public int compare(final Object o1, final Object o2){
				return new Long(((File)o2).lastModified()).compareTo(new Long(((File)o1).lastModified()));
			}
		});
		return files;
	}
}
