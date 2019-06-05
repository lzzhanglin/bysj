package com.cqjtu.bysj.service.serviceImpl;

import com.cqjtu.bysj.entity.*;
import com.cqjtu.bysj.mapper.AdminUserMapper;
import com.cqjtu.bysj.service.AdminUserService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Component
@Service("adminUserService")
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private AdminUserMapper adminUserMapper;

    @Autowired
    private PasswordEncoder encoder;

    private static final String SUFFIX_2003 = ".xls";
    private static final String SUFFIX_2007 = ".xlsx";
    @Override
    public AdminUser getUserInfoByJobNo(String jobNo) {
        AdminUser adminUser = adminUserMapper.findUserByJobNo(jobNo);

        Collection<GrantedAuthority> authList = myUserDetailsService.getAuthorities(jobNo);
        adminUser.setAuthorities(authList);
        adminUser.setPassword(null);
        return adminUser;
    }

    @Override
    public String getPasswordByJobNo(String jobNo){
        return adminUserMapper.getPasswordByJobNo(jobNo);
    }

    @Override
    public void updatePwdByJobNo(String jobNo, String newPassword) {
        adminUserMapper.updatePwdByJobNo(jobNo,newPassword);
    }

    @Override
    public Resp batchImport(MultipartFile file){
        List<String> jobNoList = adminUserMapper.getAllJobNo();
        List<AdminUser> userList = new ArrayList<>();
        List<String> msgList = new ArrayList<>();
        List<UserRole> userRoleList = new ArrayList<>();
        if (file == null) {
            return new Resp(RespCode.FAILED,"文件不能为空");
        }
        String originalFilename = file.getOriginalFilename();
        Workbook workbook = null;
        try {
            if (originalFilename.endsWith(SUFFIX_2003)) {
                workbook = new HSSFWorkbook(file.getInputStream());
            } else if (originalFilename.endsWith(SUFFIX_2007)) {
                workbook = new XSSFWorkbook(file.getInputStream());
            }
        } catch (Exception e) {
            e.printStackTrace();
            msgList.add("文件格式错误");
            throw new IllegalArgumentException("文件格式错误");
        }
        if (workbook == null) {
            msgList.add("文件格式错误");
            throw new IllegalArgumentException("文件格式错误");
        } else {
            //获取所有的工作表的的数量
            int numOfSheet = workbook.getNumberOfSheets();
            //遍历这个这些表
            for (int i = 0; i < numOfSheet; i++) {
                //获取一个sheet也就是一个工作簿
                Sheet sheet = workbook.getSheetAt(i);
                int lastRowNum = sheet.getLastRowNum();
                //从第一行开始第一行一般是标题
                for (int j = 1; j <= lastRowNum; j++) {
                    Row row = sheet.getRow(j);
                    AdminUser user = new AdminUser();
                    //姓名
                    if (row.getCell(0) == null) {
                        msgList.add("第" + j + "行，第1列，用户姓名不能为空");
                    }else {
                        row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                        String username = row.getCell(0).getStringCellValue();
                        user.setUsername(username);
                    }
                    if (row.getCell(1) == null) {
                        msgList.add("第" + j + "行，第2列，账号不能为空");
                    }else {
                        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                        String jobNo = row.getCell(1).getStringCellValue();
                        for (String str : jobNoList) {
                            if (Objects.equals(str, jobNo)) {
                                msgList.add("第" + j + "行，第2列，账号已存在");
                            }
                        }
                        user.setJobNo(jobNo);
                    }
                    //密码
                    if (row.getCell(2) == null) {
                        msgList.add("第" + j + "行，第3列，密码不能为空");

                    }else {
                        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                        String password = row.getCell(2).getStringCellValue();
                        if (password.replace("", "").length() < 8) {//校验密码长度
                            msgList.add("第" + j + "行，第3列，密码长度至少为8位");
                        }
                        String encodePwd = encoder.encode(password);
                        user.setPassword(encodePwd);
                    }
                    if (row.getCell(3) == null) {
                        msgList.add("第" + j + "行，第4列，角色不能为空");

                    }else {
                        row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                        String role = row.getCell(3).getStringCellValue();
                        if (Objects.equals(role, "ROLE_ADMIN")) {
                            userRoleList.add(new UserRole(user.getJobNo(),1));
                        }else if (Objects.equals(role, "ROLE_STUDENT")) {
                            userRoleList.add(new UserRole(user.getJobNo(),2));
                        }
                        else if (Objects.equals(role, "ROLE_TEACHER")) {
                            userRoleList.add(new UserRole(user.getJobNo(),3));
                        }else {
                            msgList.add("第" + j + "行，第4列，角色填写错误");
                        }

                    }
                    userList.add(user);
                }
            }
        }
        if (msgList.size() > 0) {
            return new Resp(RespCode.FAILED, msgList);
        }else {
            adminUserMapper.batchImport(userList);
            adminUserMapper.batchImportUserRole(userRoleList);
            return new Resp(RespCode.SUCCESS);
        }

    }

    @Override
    public  List<AdminUser> getAllTeacherAndStudent(){

        return adminUserMapper.getAllTeacherAndStudent();
    }

    @Override
    public void deleteUser(String jobNo) {
        adminUserMapper.deleteUserRole(jobNo);
        adminUserMapper.deleteUser(jobNo);
    }

    @Override
    public void updateUserProfile(String jobNo, String email, String phone, String birthday){
        adminUserMapper.updateUserProfile(jobNo,email,phone,birthday);
    }
}
