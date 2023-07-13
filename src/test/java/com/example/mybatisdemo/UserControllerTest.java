package com.example.mybatisdemo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.context.jdbc.SqlMergeMode.MergeMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.jayway.jsonpath.JsonPath;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
@SqlMergeMode(MergeMode.MERGE)
@Sql("/sql/db_init.sql")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static Long id = 0L;
    private static String name = "Alice";
    private static String phoneNumber = "13600010009";
    private static String email = "alice@example.com";
    private static String password = "123.456";

    @Test
    @Order(1)
    public void testAddUser() throws Exception {
        
        MvcResult mvcResult = mockMvc.perform(
            MockMvcRequestBuilders.post("/user")
                .param("name",this.name)
                .param("email",this.email)
                .param("phone_number",this.phoneNumber)
                .param("password",this.password)
            ).andReturn();

        String jsonResult = mvcResult.getResponse().getContentAsString();
        System.out.println("JSON Response: " + jsonResult);
        // 使用JsonPath解析JSON响应
        Long id = JsonPath.parse(jsonResult).read("$.id",Long.class);
        String name = JsonPath.read(jsonResult, "$.name");
        String password = JsonPath.read(jsonResult, "$.password");
        String email = JsonPath.read(jsonResult, "$.email");
        String phoneNumber = JsonPath.read(jsonResult, "$.phoneNumber");

        
        assertNotNull(id);
        assertEquals(this.name,name,"Name is not equal!");
        assertEquals(this.password,password, "Password is not equal!");
        assertEquals(this.email,email,"Email is not equal!");
        assertEquals(this.phoneNumber,phoneNumber,"Phone number is not equal!");
        
    }

    @Test
    @Sql(statements = {
        "INSERT INTO user (name,password,email,phone_number) VALUES ('Alice','123.456', 'alice@example.com','13600010009');"
    })
    @Order(2)
    public void testGetUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user").param("id","1"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(this.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].password").value(this.password))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value(this.email))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].phoneNumber").value(this.phoneNumber));
    }

    @Test
    @Sql(statements = {
        "INSERT INTO user (name,password,email,phone_number) VALUES ('Alice','123.456', 'alice@example.com','13600010009');",
        "INSERT INTO user (name,password,email,phone_number) VALUES ('Alice','123.456', 'alice@example.com','13600010009');"
    })
    @Order(3)
    public void testGetUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(this.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].password").value(this.password))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value(this.email))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].phoneNumber").value(this.phoneNumber))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(this.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].password").value(this.password))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].email").value(this.email))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].phoneNumber").value(this.phoneNumber));
    }

}

