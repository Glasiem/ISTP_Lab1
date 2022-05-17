package com.glasiem.service;

import com.glasiem.entity.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImportService {

    private final int AGENCY_COLUMNS = 3;
    private final int GENERATION_COLUMNS = 4;
    private final int VTUBER_COLUMNS = 4;
    private final int MEDIA_COLUMNS = 3;
    private final int MANAGER_COLUMNS = 4;
    @Autowired
    private AgencyService agencyService;

    @Autowired
    private GenerationService generationService;

    @Autowired
    private VTuberService vtuberService;

    @Autowired
    private MediaService mediaService;

    @Autowired
    private ManagerService managerService;

    public AgencyService getAgencyService() {
        return agencyService;
    }

    public GenerationService getGenerationService() {
        return generationService;
    }

    public VTuberService getVtuberService() {
        return vtuberService;
    }

    public MediaService getMediaService() {
        return mediaService;
    }

    public ManagerService getManagerService() {
        return managerService;
    }

    private String checkEmpty(MultipartFile reapExcelDataFile){
        if (reapExcelDataFile.isEmpty()){
            return "Тут нічого нема!";
        }
        return null;
    }

    private String checkCells(int numberOfCells, int needed){
        if (numberOfCells != needed){
            return "Неправильна кількість колонок!";
        }
        return null;
    }

    public String importAgency(MultipartFile reapExcelDataFile) throws IOException {

        if (checkEmpty(reapExcelDataFile) != null){
            return checkEmpty(reapExcelDataFile);
        }

        List<AgencyEntity> tempAgencyList = new ArrayList<>();
        XSSFWorkbook excelWorkbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        XSSFSheet excelSheet = excelWorkbook.getSheetAt(0);

        int numberOfCells = excelSheet.getRow(0).getPhysicalNumberOfCells();
        if (checkCells(numberOfCells, AGENCY_COLUMNS) != null){
            return checkCells(numberOfCells, AGENCY_COLUMNS);
        }

        for(int i = 1; i < excelSheet.getPhysicalNumberOfRows(); i++) {
            AgencyEntity tempAgency = new AgencyEntity();

            XSSFRow row = excelSheet.getRow(i);

            row.getCell(1).setCellType(CellType.STRING);
            tempAgency.setName(row.getCell(1).getStringCellValue());
            row.getCell(2).setCellType(CellType.STRING);
            tempAgency.setInfo(row.getCell(2).getStringCellValue());

            if (agencyService.createAgencyCheck(tempAgency) == null){
                tempAgencyList.add(tempAgency);
            }
        }
        for (int i = 0; i < tempAgencyList.size(); i++) {
            agencyService.getAgencyRepo().save(tempAgencyList.get(i));
        }

        return null;
    }

    public String importGeneration(MultipartFile reapExcelDataFile) throws IOException {

        if (checkEmpty(reapExcelDataFile) != null){
            return checkEmpty(reapExcelDataFile);
        }

        List<GenerationEntity> tempGenerationList = new ArrayList<>();
        XSSFWorkbook excelWorkbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        XSSFSheet excelSheet = excelWorkbook.getSheetAt(0);

        int numberOfCells = excelSheet.getRow(0).getPhysicalNumberOfCells();
        if (checkCells(numberOfCells, GENERATION_COLUMNS) != null){
            return checkCells(numberOfCells, GENERATION_COLUMNS);
        }

        for(int i = 1; i < excelSheet.getPhysicalNumberOfRows(); i++) {
            GenerationEntity tempGeneration = new GenerationEntity();

            XSSFRow row = excelSheet.getRow(i);

            row.getCell(1).setCellType(CellType.STRING);
            tempGeneration.setName(row.getCell(1).getStringCellValue());
            row.getCell(2).setCellType(CellType.STRING);
            tempGeneration.setInfo(row.getCell(2).getStringCellValue());
            row.getCell(3).setCellType(CellType.STRING);
            tempGeneration.setAgency(agencyService.getAgencyRepo().findByName(row.getCell(3).getStringCellValue()));

            if (generationService.createGenerationCheck(tempGeneration) == null){
                tempGenerationList.add(tempGeneration);
            }
        }
        for (int i = 0; i < tempGenerationList.size(); i++) {
            generationService.getGenerationRepo().save(tempGenerationList.get(i));
        }

        return null;
    }

    public String importVTuber(MultipartFile reapExcelDataFile) throws IOException {

        if (checkEmpty(reapExcelDataFile) != null){
            return checkEmpty(reapExcelDataFile);
        }

        List<VTuberEntity> tempVTuberList = new ArrayList<>();
        XSSFWorkbook excelWorkbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        XSSFSheet excelSheet = excelWorkbook.getSheetAt(0);

        int numberOfCells = excelSheet.getRow(0).getPhysicalNumberOfCells();
        if (checkCells(numberOfCells, VTUBER_COLUMNS) != null){
            return checkCells(numberOfCells, VTUBER_COLUMNS);
        }

        for(int i = 1; i < excelSheet.getPhysicalNumberOfRows(); i++) {
            VTuberEntity tempVTuber = new VTuberEntity();

            XSSFRow row = excelSheet.getRow(i);

            row.getCell(1).setCellType(CellType.STRING);
            tempVTuber.setName(row.getCell(1).getStringCellValue());
            row.getCell(2).setCellType(CellType.STRING);
            tempVTuber.setInfo(row.getCell(2).getStringCellValue());
            row.getCell(3).setCellType(CellType.STRING);
            tempVTuber.setGeneration(generationService.getGenerationRepo().findByName(row.getCell(3).getStringCellValue()));

            if (vtuberService.createVTuberCheck(tempVTuber) == null){
                tempVTuberList.add(tempVTuber);
            }
        }
        for (int i = 0; i < tempVTuberList.size(); i++) {
            vtuberService.getVTuberRepo().save(tempVTuberList.get(i));
        }

        return null;
    }

    public String importMedia(MultipartFile reapExcelDataFile) throws IOException {

        if (checkEmpty(reapExcelDataFile) != null){
            return checkEmpty(reapExcelDataFile);
        }

        List<MediaEntity> tempMediaList = new ArrayList<>();
        XSSFWorkbook excelWorkbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        XSSFSheet excelSheet = excelWorkbook.getSheetAt(0);

        int numberOfCells = excelSheet.getRow(0).getPhysicalNumberOfCells();
        if (checkCells(numberOfCells, MEDIA_COLUMNS) != null){
            return checkCells(numberOfCells, MEDIA_COLUMNS);
        }

        for(int i = 1; i < excelSheet.getPhysicalNumberOfRows(); i++) {
            MediaEntity tempMedia = new MediaEntity();

            XSSFRow row = excelSheet.getRow(i);

            row.getCell(1).setCellType(CellType.STRING);
            tempMedia.setContents(row.getCell(1).getStringCellValue());
            row.getCell(2).setCellType(CellType.STRING);
            tempMedia.setVtuber(vtuberService.getVTuberRepo().findByName(row.getCell(2).getStringCellValue()));

            if (mediaService.createMediaCheck(tempMedia) == null){
                tempMediaList.add(tempMedia);
            }
        }
        for (int i = 0; i < tempMediaList.size(); i++) {
            mediaService.getMediaRepo().save(tempMediaList.get(i));
        }

        return null;
    }

    public String importManager(MultipartFile reapExcelDataFile) throws IOException {

        if (checkEmpty(reapExcelDataFile) != null){
            return checkEmpty(reapExcelDataFile);
        }

        List<ManagerEntity> tempManagerList = new ArrayList<>();
        XSSFWorkbook excelWorkbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        XSSFSheet excelSheet = excelWorkbook.getSheetAt(0);

        int numberOfCells = excelSheet.getRow(0).getPhysicalNumberOfCells();
        if (checkCells(numberOfCells, GENERATION_COLUMNS) != null){
            return checkCells(numberOfCells, GENERATION_COLUMNS);
        }

        for(int i = 1; i < excelSheet.getPhysicalNumberOfRows(); i++) {
            ManagerEntity tempManager = new ManagerEntity();

            XSSFRow row = excelSheet.getRow(i);

            row.getCell(1).setCellType(CellType.STRING);
            tempManager.setName(row.getCell(1).getStringCellValue());
            row.getCell(2).setCellType(CellType.STRING);
            tempManager.setInfo(row.getCell(2).getStringCellValue());
            row.getCell(3).setCellType(CellType.STRING);
            tempManager.setAgency(agencyService.getAgencyRepo().findByName(row.getCell(3).getStringCellValue()));

            if (managerService.createManagerCheck(tempManager) == null){
                tempManagerList.add(tempManager);
            }
        }
        for (int i = 0; i < tempManagerList.size(); i++) {
            managerService.getManagerRepo().save(tempManagerList.get(i));
        }

        return null;
    }
}
