/**
 * @Title:   [ExcelHelper.java]
 * @Package: [com.cc.michelle.platform.util]
 * @author:  Mk.Yuan
 * @CreateDate: 2013-6-24 
 * @Description:  提供了生成导出，和导入excel的功能
 */
package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ExcelHelper {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	//  设置cell编码解决中文高位字节截断
	//  定制日期格式
	private static String DATE_FORMAT = " m/d/yy "; //  "m/d/yy h:mm"
	//  定制浮点数格式
	private static String NUMBER_FORMAT = " #,##0.00 ";
	private String xlsFileName;
	private HSSFWorkbook workbook;
	private HSSFSheet sheet;
	private HSSFRow row;

	/**
	* 初始化Excel
	*
	*  @param  fileName
	*            导出文件名
	 */
	public ExcelHelper(String fileName) {
		this.xlsFileName = fileName;
		this.workbook = new HSSFWorkbook();
		this.sheet = workbook.createSheet();
	}

	public void exportXLS() throws Exception {
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(xlsFileName);
			workbook.write(fout);

			fout.close();
		} catch (Exception e) {
			throw new Exception("Export excel for [" + xlsFileName + "] error", e);
		} finally {
			try {
				fout.flush();
			} catch (Exception e) {
			}
			try {
				if (fout != null) {
					fout.close();
					fout = null;
				}
			} catch (Exception e) {
			}
		}

	}

	/**
	* 增加一行
	*
	*  @param  index
	*            行号
	*/
	public void createRow(int index) {
		this.row = this.sheet.createRow(index);
	}

	/**
	* 设置单元格
	*
	*  @param  index
	*            列号
	*  @param  value
	*            单元格填充值
	*/
	public void setCell(int index, String value) {

		HSSFCell cell = this.row.createCell(index);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(value);
	}

	/**
	* 设置单元格
	*
	*  @param  index
	*            列号
	*  @param  value
	*            单元格填充值
	*/
	public void setCell(int index, Date value) {
		HSSFCell cell = this.row.createCell(index);
		cell.setCellValue(value);
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		HSSFDataFormat format = workbook.createDataFormat();
		cellStyle.setDataFormat(format.getFormat("yyyy/m/d"));
		cell.setCellStyle(cellStyle);
	}

	/**
	* 设置单元格
	*
	*  @param  index
	*            列号
	*  @param  value
	*            单元格填充值
	*/
	public void setCell(int index, int value) {
		HSSFCell cell = this.row.createCell(index);
		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell.setCellValue(value);
	}

	/**
	* 设置单元格
	*
	*  @param  index
	*            列号
	*  @param  value
	*            单元格填充值
	*/
	public void setCell(int index, double value) {
		HSSFCell cell = this.row.createCell(index);
		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell.setCellValue(value);
		HSSFCellStyle cellStyle = workbook.createCellStyle(); //  建立新的cell样式
		HSSFDataFormat format = workbook.createDataFormat();
		cellStyle.setDataFormat(format.getFormat(NUMBER_FORMAT)); //  设置cell样式为定制的浮点数格式
		cell.setCellStyle(cellStyle); //  设置该cell浮点数的显示格式
	}

	protected void downloadExcel(HSSFWorkbook workbook, HttpServletResponse response) throws Exception {
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(xlsFileName, "UTF-8"));
			response.setContentType("application/msexcel;charset=UTF-8");
			workbook.write(out);
		} catch (Exception e) {
			logger.error("download excel error: " + e.getMessage(), e);
			throw new Exception("download excel error", e);
		} finally {
			try {
				if (out != null) {
					out.close();
					out = null;
				}
			} catch (Exception e) {
			}
		}
	}

	/** 
	* 导入Excel文件 
	*      内容以List<Map<String K,String V>>的方式存放 
	* @param excelFile :   Excel文件对象 
	* @param strKeys   :   Map的Key列表，Value为相应的sheet一行中各列的值 
	* @return 
	*/
	public List<Map<String, String>> importExcelToMap(String strKeys) {
		File excelFile = new File(xlsFileName);
		String[] strKey = strKeys.split(",");
		List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
		int i = 1;
		FileInputStream fin = null;
		Workbook book = null;
		try {
			if (excelFile.exists()) {
				fin = new FileInputStream(excelFile);
				try {
					book = new XSSFWorkbook(fin);
				} catch (Exception ex) {
					book = new HSSFWorkbook(new FileInputStream(excelFile));
				}
				Sheet  sheet = book.getSheetAt(0);
				while (true) {
					Row row = sheet.getRow(i);
					if (row == null)
						break;

					Map<String, String> map = new HashMap<String, String>();
					for (int keyIndex = 0; keyIndex < strKey.length; keyIndex++) {

						String value = "";
						Cell cell = row.getCell(keyIndex);
						switch (cell.getCellType()) {

						case Cell.CELL_TYPE_NUMERIC: // 数字

							if (HSSFDateUtil.isCellDateFormatted(cell)) { // 如果是日期类型

								// 把Date转换成本地格式的字符串  

								SimpleDateFormat sdf = null;
								if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {
									sdf = new SimpleDateFormat("HH:mm");
								} else {// 日期  
									sdf = new SimpleDateFormat("yyyy-MM-dd");
								}
								Date date = cell.getDateCellValue();
								value = sdf.format(date);
							} else
								value = String.valueOf(cell.getNumericCellValue());

							break;
						case Cell.CELL_TYPE_STRING: // 字符串、
							value = cell.getStringCellValue();
							break;

						case Cell.CELL_TYPE_BLANK: // 空白

							value = "";

							break;

						case Cell.CELL_TYPE_BOOLEAN: // Boolean

							value = String.valueOf(cell.getBooleanCellValue());

							break;

						case Cell.CELL_TYPE_ERROR: // Error，返回错误码

							value = String.valueOf(cell.getErrorCellValue());

							break;

						default:
							value = "";
							break;

						}

						//						map.put(strKey[keyIndex], row.getCell(keyIndex)
						//								.getStringCellValue());
						map.put(strKey[keyIndex], value);
					}
					listMap.add(map);

					i++;
				}
			}
		} catch (Exception e) {
			logger.error("import was interrupted, error happened in " + i + "  row", e);
		} finally {
			try {
				if (fin != null) {
					fin.close();
					fin = null;
				}
			} catch (Exception e) {
			}
		}
		return listMap;
	}
}
