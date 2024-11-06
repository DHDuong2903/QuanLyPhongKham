package qlpk.report;

import qlpk.entity.Patient;
import qlpk.entity.PatientManager; 

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import java.util.*;

public class ReportGenerator {
    public static void generateReport(String jsonFilePath, String reportTemplate, String outputPdfPath) {
        try {
            // Lấy danh sách bệnh nhân từ PatientManager
            PatientManager patientManager = new PatientManager();
            List<Patient> patients = patientManager.getPatients();

            // Chuyển dữ liệu bệnh nhân thành JRBeanCollectionDataSource
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(patients);

            // Tải báo cáo đã biên dịch (.jasper)
            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile("src/main/resources/reports/patient_report.jasper");

            // Tạo tham số cho báo cáo (nếu cần)
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("ReportTitle", "Patient Report");

            // Tạo JasperPrint từ dữ liệu và tham số
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Xuất báo cáo ra file PDF
            JasperExportManager.exportReportToPdfFile(jasperPrint, outputPdfPath);

            System.out.println("Report generated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
