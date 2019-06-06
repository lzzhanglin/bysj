package com.cqjtu.bysj.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cqjtu.bysj.config.FilterConfig;
import com.cqjtu.bysj.entity.AccessRecord;
import com.cqjtu.bysj.entity.AdminUser;
import com.cqjtu.bysj.entity.Role;
import com.cqjtu.bysj.entity.Route;
import com.cqjtu.bysj.mapper.AdminUserMapper;
import com.cqjtu.bysj.security.GrantedAuthorityImpl;
import com.cqjtu.bysj.service.AccessRecordService;
import com.cqjtu.bysj.service.AdminUserService;
import com.cqjtu.bysj.service.serviceImpl.MyUserDetailsService;
import com.cqjtu.bysj.util.JsonParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.*;

public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {


    @Autowired
    private AccessRecordService accessRecordService;

    private AuthenticationManager authenticationManager;

    private Logger logger = LoggerFactory.getLogger(this.getClass());




    public JwtLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * 接收并解析用户登陆信息  /login,
     *为已验证的用户返回一个已填充的身份验证令牌，表示成功的身份验证
     *返回null，表明身份验证过程仍在进行中。在返回之前，实现应该执行完成该过程所需的任何额外工作。
     *如果身份验证过程失败，就抛出一个AuthenticationException
     *
     *
     * @param request  从中提取参数并执行身份验证
     * @param response 如果实现必须作为多级身份验证过程的一部分(比如OpenID)进行重定向，则可能需要响应
     * @return 身份验证的用户令牌，如果身份验证不完整，则为null。
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {


        //得到用户登陆信息,并封装到 Authentication 中,供自定义用户组件使用.
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        logger.info("进入JwtLoginFilter");

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }

        username = username.trim();


        Collection<? extends GrantedAuthority> authorities = new ArrayList<>();


        UsernamePasswordAuthenticationToken  authenticationToken = new UsernamePasswordAuthenticationToken(username, password, authorities);

        //authenticate()接受一个token参数,返回一个完全经过身份验证的对象，包括证书.

        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        return authenticate;
    }






    /**
     * 登陆成功后,此方法会被调用,因此我们可以在次方法中生成token,并返回给客户端
     *
     * @param request
     * @param response
     * @param chain
     * @param authResult
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException {
        logger.info("登录成功");



        String token = Jwts.builder()
                .setSubject(authResult.getName())
                //有效期两小时
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 2 * 1000))
                //采用什么算法是可以自己选择的，不一定非要采用HS512
                .signWith(SignatureAlgorithm.HS512, "MyJwtSecret")
                .compact();

        logger.info("token is: Bearer {}", token);
        AdminUser user = (AdminUser) authResult.getDetails();
        //登录成功之后将访问记录写入数据库
        AccessRecord accessRecord = new AccessRecord();
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if(ip.equals("127.0.0.1")){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ip= inet.getHostAddress();
            }
        }
        // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ip != null && ip.length() > 15){
            if(ip.indexOf(",")>0){
                ip = ip.substring(0,ip.indexOf(","));
            }
        }
        accessRecord.setIP(ip);
        accessRecord.setJobNo(user.getJobNo());
        accessRecord.setUsername(user.getUsername());
        if (accessRecordService == null) {
            accessRecordService = (AccessRecordService) FilterConfig.getBean("accessRecordService");
        }
        accessRecordService.createAccessRecord(accessRecord);
        response.addHeader("token", "Bearer " + token);

        String uName = user.getUsername();
        Collection<?extends GrantedAuthority> authorities = user.getAuthorities();
        Iterator iterator = authorities.iterator();
        String role = iterator.next().toString();
        response.setStatus(200);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        String jobNo = user.getJobNo();
        out.write("{\"username\":"+"\""+uName+"\","+"\"role\":"+"\""+role+"\",\"jobNo\":"+"\""+jobNo+"\",\"IP\":"+"\""+ip+"\"}");
        out.flush();
        out.close();

    }


}
