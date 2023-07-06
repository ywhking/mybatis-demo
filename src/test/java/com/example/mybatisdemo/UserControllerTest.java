package com.example.mybatisdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.Assert;

import com.jayway.jsonpath.JsonPath;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static Long id = 0L;
    private static String name = "Alice";
    private static String phoneNumber = "13600010009";
    private static String email = "alice@example.com";
    private static String password = "123.456";

    @Test
    public void testAddUser() throws Exception {
        
        String reqParams = "?name=" + this.name + "&email=" + this.email + "&phone_number=" + this.phoneNumber + "&password=" + this.password;

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user" + reqParams)).andReturn();
        String jsonResult = mvcResult.getResponse().getContentAsString();
        System.out.println("JSON Response: " + jsonResult);
        // 使用JsonPath解析JSON响应
        Long id = JsonPath.parse(jsonResult).read("$.id",Long.class);
        //Long id = JsonPath.read(jsonResult, "$.id",Long.class);
        String name = JsonPath.read(jsonResult, "$.name");
        String email = JsonPath.read(jsonResult, "$.email");
        String phoneNumber = JsonPath.read(jsonResult, "$.phoneNumber");
        String password = JsonPath.read(jsonResult, "$.password");
        Assert.isTrue(null != id , "id is null!");
        System.out.println("The user id is:" + id);
        Assert.isTrue(this.name.equals(name) , "name not equal!");
        Assert.isTrue(this.email.equals(email) , "email not equal!");
        Assert.isTrue(this.phoneNumber.equals(phoneNumber) , "phone number not equal!");
        Assert.isTrue(this.password.equals(password) , "password not equal!");
        this.id = id;
    }

    @Test
    public void testGetUser() throws Exception {
        String reqParams = "?id=" + Long.toString(this.id);
        System.out.println("get user by string:" + reqParams);
        mockMvc.perform(MockMvcRequestBuilders.get("/user" + reqParams))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(this.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(this.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value(this.email))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].phoneNumber").value(this.phoneNumber))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].password").value(this.password));
    }

}

