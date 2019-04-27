package com.cqjtu.bysj.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cqjtu.bysj.entity.AdminUser;
import com.cqjtu.bysj.entity.Role;
import com.cqjtu.bysj.entity.Route;
import com.cqjtu.bysj.mapper.AdminUserMapper;
import com.cqjtu.bysj.security.GrantedAuthorityImpl;
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
import java.util.*;

public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

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
        // 这里并没有对用户名密码进行验证,而是
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
        response.addHeader("token", "Bearer " + token);

        String uName = user.getUsername();
        Collection<?extends GrantedAuthority> authorities = user.getAuthorities();
        Iterator iterator = authorities.iterator();
        String role = iterator.next().toString();

        response.setStatus(200);
        response.setContentType("application/json;charset=utf-8");
        Map<String, String> metaMap = new HashMap<>();
        metaMap.put("title","自述文件");
        Map<String, String> metaMap1 = new HashMap<>();
        metaMap1.put("title","系统首页");
        Map<String, String> metaMap2 = new HashMap<>();
        metaMap2.put("title","404");
        Map<String, String> metaMap3 = new HashMap<>();
        metaMap3.put("title","403");
        Map<String, String> metaMap4 = new HashMap<>();
        metaMap4.put("title","创建课程");
        Map<String, String> metaMap5 = new HashMap<>();
        metaMap5.put("title","管理课程");
        Map<String, String> metaMap6 = new HashMap<>();
        metaMap6.put("title","选课中心");
        Route dashboard = new Route("'/dashboard'","resolve => require(['../components/page/Dashboard.vue'], resolve)",metaMap1);
        Route route404 = new Route("/404","resolve => require(['../components/page/404.vue'], resolve)",metaMap2);
        Route route403 = new Route("/403", " component: resolve => require(['../components/page/403.vue'], resolve)", metaMap3);
        List<Route> childrenList1 = new ArrayList<>();
        childrenList1.add(dashboard);
        childrenList1.add(route404);
        childrenList1.add(route403);
        List<Route> childrenList2 = new ArrayList<>();
        Route createCourse = new Route("create", "resolve => require(['../components/page/CreateCourse.vue'], resolve)", metaMap4);
        Route manageCourse = new Route("manage", "resolve => require(['../components/page/ManageCourse.vue'], resolve)", metaMap5);
        Route chooseCourse = new Route("choose", "resolve => require(['../components/page/ChooseCourse.vue'], resolve)", metaMap6);
        childrenList2.add(createCourse);
        childrenList2.add(manageCourse);
        childrenList2.add(chooseCourse);
        Route route = new Route("/","resolve => require(['../components/common/Home.vue'], resolve)",metaMap,childrenList1);
        Route route2 = new Route();
        route2.setPath("/login");
        route2.setComponent("resolve => require(['../components/page/Login.vue'], resolve)");
        Route route3 = new Route("*", "/404");
        Route route4 = new Route("/", "/dashboard");
        Route routeCourse = new Route("/course", "resolve => require(['../components/common/Home.vue'], resolve)", metaMap, childrenList2);
        List<Route> lastRoute = new ArrayList<>();
        lastRoute.add(route);
        lastRoute.add(route2);
        lastRoute.add(route3);
        lastRoute.add(route4);
        lastRoute.add(routeCourse);
        //将token写入response
        String routeStr = JSON.toJSON(lastRoute).toString();

        PrintWriter out = response.getWriter();
        String jobNo = user.getJobNo();

        out.write("{\"data\":{\"router\": "+routeStr+"},\"username\":"+"\""+uName+"\","+"\"role\":"+"\""+role+"\",\"jobNo\":"+"\""+jobNo+"\"}");
        out.flush();
        out.close();

    }


}
