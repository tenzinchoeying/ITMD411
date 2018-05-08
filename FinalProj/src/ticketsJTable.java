import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class ticketsJTable {
	private final DefaultTableModel tm = new DefaultTableModel();
	public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException{
		ResultSetMetaData md = rs.getMetaData();
		//columns
		Vector<String> cName = new Vector<String>();
		int cCount = md.getColumnCount();
		for(int c = 1; c <= cCount; c++) {
			cName.add(md.getColumnName(c));
		}
		Vector<Vector<Object>> d = new Vector<Vector<Object>>();
		while(rs.next()) {
			Vector<Object> v = new Vector<Object>();
			for(int ci = 1; ci <= cCount; ci++) {
				v.add(rs.getObject(ci));
			}
			d.add(v);
		}
		return new DefaultTableModel(d, cName);
	}

}
