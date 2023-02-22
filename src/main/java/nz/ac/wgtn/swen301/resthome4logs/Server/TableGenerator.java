package nz.ac.wgtn.swen301.resthome4logs.Server;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

public class TableGenerator {

    static final String SHEET_NAME = "stats";

    public static Workbook generateWorkbook() {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet(SHEET_NAME);
        List<List<String>> table = FormatList.generateListOfList();
        for (int i = 0; i < table.size(); i++) {
            var row = sheet.createRow(i);
            var colList = table.get(i);
            for (int j = 0; j < colList.size(); j++) {
                row.createCell(j).setCellValue(colList.get(j));
            }
        }
        return workbook;
    }

    public static String generateCSV() {
        Sheet sheet = generateWorkbook().getSheetAt(0);
        var sb = new StringBuilder();
        Row row;
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            for (int j = 0; j < row.getLastCellNum(); j++) {
                sb.append(row.getCell(j)).append("\t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
