package com.cqjtu.bysj.controller;

import com.cqjtu.bysj.entity.AdminUser;
import com.cqjtu.bysj.entity.Resp;
import com.cqjtu.bysj.entity.RespCode;
import com.cqjtu.bysj.mapper.AdminUserMapper;
import com.cqjtu.bysj.service.AdminUserService;
import com.cqjtu.bysj.service.AuthenticationService;
import com.cqjtu.bysj.service.serviceImpl.MyUserDetailsService;
import com.cqjtu.bysj.util.MediaTypeUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class AdminUserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AdminUserMapper adminUserMapper;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private ServletContext servletContext;

    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public Resp login(HttpServletRequest request) {
        Authentication authentication = authenticationService.getAuthentication();
        String jobNo = authentication.getName();
        UserDetails userDetails = adminUserService.getUserInfoByJobNo(jobNo);


            return new Resp(RespCode.SUCCESS,userDetails);
    }



    @RequestMapping(value = "changePwd", method = RequestMethod.POST)
    public Resp changePwd(HttpServletRequest request) {
        String oldPwd = request.getParameter("oldPwd");
        String newPwd = request.getParameter("newPwd");
        String newPwdAgain = request.getParameter("newPwdAgain");
        if (!Objects.equals(newPwd, newPwdAgain)) {
            return new Resp(RespCode.FAILED, "new password isn't same");
        }
        Authentication authentication = authenticationService.getAuthentication();
        String jobNo = authentication.getName();
        String pwdFromDb = adminUserService.getPasswordByJobNo(jobNo);
        boolean flag = encoder.matches(oldPwd, pwdFromDb);
        if (!flag) {
            return new Resp(RespCode.FAILED, "old password is wrong");
        }
        String newPassword = encoder.encode(newPwd);
        adminUserService.updatePwdByJobNo(jobNo,newPassword);
        return new Resp(RespCode.SUCCESS);
    }
   //修改密码时检查原密码是否正确
    @RequestMapping(value = "checkPwd", method = RequestMethod.GET)
    public Resp getOldPwd(HttpServletRequest request) {
        String oldPwd = request.getParameter("oldPwd");
        Authentication authentication = authenticationService.getAuthentication();
        String jobNo = authentication.getName();
        String pwdFromDb = adminUserService.getPasswordByJobNo(jobNo);
        boolean flag = encoder.matches(oldPwd, pwdFromDb);
        return new Resp(RespCode.SUCCESS, flag);
    }

    @RequestMapping("/hello")
    public Resp hello() {
        return new Resp(RespCode.SUCCESS, "hello");
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping("/batchImport")
    public Resp upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        if (!file.isEmpty()) {

            String fileName = file.getOriginalFilename();

            return adminUserService.batchImport(file);
            //向数据库写入一条导入文件记录


        } else {
            return new Resp(RespCode.FAILED, "文件不能为空");
        }
    }
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/download",method = RequestMethod.GET)
        public ResponseEntity<InputStreamResource> getDownload(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
        String fileName = "导入模板.xlsx";
            MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);
            //创建工作簿
            XSSFWorkbook wb = new XSSFWorkbook();
            //创建一个sheet
            XSSFSheet sheet = wb.createSheet();
            for (int i = 0; i < 2; i++) { //需要6行表格
                Row row =	sheet.createRow(i); //创建行
                for (int j = 0; j < 4; j++) {//需要6列
                    row.createCell(j);
                }
            }
            XSSFRow row = sheet.getRow(0);
            row.getCell(0).setCellValue("姓名");
            row.getCell(1).setCellValue("账号（工号）");
            row.getCell(2).setCellValue("密码");
            row.getCell(3).setCellValue("角色");
            XSSFRow row1 = sheet.getRow(1);
            row1.getCell(0).setCellValue("张三");
            row1.getCell(1).setCellValue("123456");
            row1.getCell(2).setCellValue("88888888（至少8位数字或字符）");
            row1.getCell(3).setCellValue("ROLE_ADMIN or ROLE_TEACHER or ROLE_STUDENT");
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(fileName);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                wb.write(out);
            } catch (IOException e) {
                e.printStackTrace();
            }
            File file = new File(fileName);
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            String returnFileName = "";
            try {
                returnFileName = new String(fileName.getBytes("GB2312"), "ISO-8859-1");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return ResponseEntity.ok()
                    // Content-Disposition
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + returnFileName)
                    // Content-Type
                    .contentType(mediaType)
                    // Contet-Length
                    .contentLength(file.length()) //
                    .body(resource);
        }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "getAllTeacherAndStudent", method = RequestMethod.GET)
    public Resp getAllTeacherAndStudent() {
        return new Resp(RespCode.SUCCESS, adminUserService.getAllTeacherAndStudent());
    }
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "resetPwd", method = RequestMethod.POST)
    public Resp resetPwd(HttpServletRequest request) {
        String jobNo = request.getParameter("jobNo");
        String newPwd = encoder.encode(jobNo);
        adminUserService.updatePwdByJobNo(jobNo,newPwd);
        return new Resp(RespCode.SUCCESS);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public Resp deleteUser(HttpServletRequest request) {
        String jobNo = request.getParameter("jobNo");
        adminUserService.deleteUser(jobNo);
        return new Resp(RespCode.SUCCESS);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Resp updateUserProfile(HttpServletRequest request) {
        String jobNo = request.getParameter("jobNo");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String birthday = request.getParameter("birthday");
        adminUserService.updateUserProfile(jobNo, email, phone, birthday);
        return new Resp(RespCode.SUCCESS);
    }



}
