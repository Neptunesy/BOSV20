package com.itsun.bos.web.action.base;

import com.itsun.bos.domain.base.Area;
import com.itsun.bos.service.base.AreaService;
import com.itsun.bos.utils.PinYin4jUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.ServletOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SY on 2017-07-22.
 * on BOSV20
 * on 下午 01:54
 */
@ParentPackage("json-default")
@Namespace("/")
@Scope("prototype")
@Controller
public class AreaAction extends ActionSupport implements ModelDriven<Area> {
    @Autowired
    private AreaService areaService;


    private File file;

    public void setFile(File file) {
        this.file = file;
    }

    private Area area = new Area();

    @Override
    public Area getModel() {
        return area;
    }

    @Action(value = "area_batchImport")
    public String batchImport() throws IOException {

        List<Area> areaList = new ArrayList<>();

        //加载Excel文件对象
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream(file));
        //读取一个Sheet
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
        //读取Sheet中每一行
        for (Row row : hssfSheet) {
            //第一行跳过
            if (row.getRowNum() == 0) {
                continue;
            }
            //空行跳过
            if (row.getCell(0) == null || StringUtils.isBlank(row.getCell(0).getStringCellValue())) {
                continue;
            }
            Area area = new Area();
            area.setId(row.getCell(0).getStringCellValue());
            area.setProvince(row.getCell(1).getStringCellValue());
            area.setCity(row.getCell(2).getStringCellValue());
            area.setDistrict(row.getCell(3).getStringCellValue());
            area.setPostcode(row.getCell(4).getStringCellValue());
            // 基于pinyin4j生成城市编码和简码
            String province = area.getProvince();
            String city = area.getCity();
            String district = area.getDistrict();
            province = province.substring(0, province.length() - 1);
            city = city.substring(0, city.length() - 1);
            district = district.substring(0, district.length() - 1);
            // 简码
            String[] headArray = PinYin4jUtils.getHeadByString(province + city
                    + district);
            StringBuffer buffer = new StringBuffer();
            for (String headStr : headArray) {
                buffer.append(headStr);
            }
            String shortcake = buffer.toString();
            area.setShortcode(shortcake);
            // 城市编码
            String citywide = PinYin4jUtils.hanziToPinyin(city, "");
            area.setCitycode(citywide);

            areaList.add(area);
        }
        areaService.saveBatch(areaList);
        return NONE;
    }

    private int page;
    private int rows;

    public void setPage(int page) {
        this.page = page;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    @Action(value = "area_pageQuery", results = {@Result(type = "json")})
    public String area_pageQuery() {


        Specification<Area> areaSpecification = new Specification<Area>() {
            @Override
            public Predicate toPredicate(Root<Area> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (StringUtils.isNotBlank(area.getProvince())) {
                    Predicate province = cb.like(root.get("province").as(String.class), area.getProvince() + "%");
                    predicates.add(province);
                }
                if (StringUtils.isNotBlank(area.getCity())) {
                    Predicate city = cb.like(root.get("city").as(String.class), area.getCity() + "%");
                    predicates.add(city);
                }
                if (StringUtils.isNotBlank(area.getDistrict())) {
                    Predicate district = cb.like(root.get("district").as(String.class), area.getDistrict() + "%");
                    predicates.add(district);
                }

                return cb.and(predicates.toArray(new Predicate[0]));
            }
        };


        Pageable pageable = new PageRequest(page - 1, rows);
        Page<Area> areaPage = areaService.findAllSpecialPage(areaSpecification, pageable);
        Map<String, Object> map = new HashMap<>();
        map.put("total", areaPage.getTotalElements());
        map.put("rows", areaPage.getContent());
        ActionContext.getContext().getValueStack().push(map);
        return SUCCESS;
    }

    @Action(value = "save_areaAction", results = {@Result(type = "redirect", location = "./pages/base/area.html")})
    public String save_areaAction() {
        areaService.saveArea(area);
        return SUCCESS;
    }


    @Action(value = "export")
    public String export() throws Exception {
//		1
//		拿到要导出的数据
        List<Area> rstList = areaService.findAll();
//		2
//		创建一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
//		创建一个sheet
        HSSFSheet sheet = workbook.createSheet("区域信息");
//		创建一个行
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("编号");
        row.createCell(1).setCellValue("省");
        row.createCell(2).setCellValue("市");
        row.createCell(3).setCellValue("区");
        row.createCell(4).setCellValue("邮编");
        row.createCell(5).setCellValue("城市简码");
        //填充数据
        if (rstList != null && rstList.size() > 0) {
            for (int i = 1; i <= rstList.size(); i++) {
                Area area = rstList.get(i - 1);
                HSSFRow temp = sheet.createRow(i);
                temp.createCell(0).setCellValue(area.getId());
                temp.createCell(1).setCellValue(area.getProvince());
                temp.createCell(2).setCellValue(area.getCity());
                temp.createCell(3).setCellValue(area.getDistrict());
                temp.createCell(4).setCellValue(area.getPostcode());
                temp.createCell(5).setCellValue(area.getShortcode());
            }
        }
//		3
//		把工作簿传到前台页面进行下载
        ServletOutputStream out = ServletActionContext.getResponse().getOutputStream();
        String filename = "区域信息.xls";
        filename = new String(filename.getBytes(), "ISO-8859-1");
        //设置头
        ServletActionContext.getResponse().setContentType("multipart/form-data");
        ServletActionContext.getResponse().setHeader("content-disposition", "attachement;filename=" + filename);


        workbook.write(out);

        return NONE;


    }


}
