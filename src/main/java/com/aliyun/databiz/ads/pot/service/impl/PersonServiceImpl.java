package com.aliyun.databiz.ads.pot.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.springframework.stereotype.Service;

import com.aliyun.databiz.ads.pot.data.object.Person;
import com.aliyun.databiz.ads.pot.data.object.Person.PersonBuilder;
import com.aliyun.databiz.ads.pot.service.PersonService;

@Service("personService")
public class PersonServiceImpl implements PersonService {
	
	private static final String ip = "ass-4a0e2ea2.cn-hangzhou-1.ads.aliyuncs.com";
	private static final String port = "3001";
	private static final String dbName = "ass";
	private static final String user = "GmPgSNCy2XMXQYSI";
	private static final String password = "K3jiWIDyVGb5KV0tvMz5kkIYDvNz03";
	
	private static final String CONST_USER = "user";
	private static final String CONST_PASSWORD = "password";
	
	public Person getPersonById(String id) throws Exception {
		//JDBC方式连接ADS，支持mysql协议
    	Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=UTF-8",
            		ip, port, dbName);
            Properties connectionProps = new Properties();
            connectionProps.put(CONST_USER, user);
            connectionProps.put(CONST_PASSWORD, password);

            connection = DriverManager.getConnection(url, connectionProps);
            statement = connection.createStatement();
        	
            String query = String.format("select id,name,sex,born_date,age,origin,policy,education,marry,adress,phone,social_security,payment_base,crime,police_station,is_second_child,birth_hospital"
            		+ " from user_all_info where id = '%s' limit 1", id);
            rs = statement.executeQuery(query);
            
            PersonBuilder builder = Person.builder();
            
            if (rs.next()) {
            	builder.id(rs.getString(1)).name(rs.getString(2)).sex(rs.getString(3)).bornDate(rs.getString(4))
            	.age(rs.getString(5)).origin(rs.getString(6)).policy(rs.getString(7)).education(rs.getString(8))
            	.marry(rs.getString(9)).address(rs.getString(10)).phone(rs.getString(11)).socialSecurity(rs.getString(12))
            	.paymentBase(rs.getString(13)).crime(rs.getString(14)).policeStation(rs.getString(15))
            	.isSecondChild(rs.getString(16)).birthHospital(rs.getString(17));
            } else {
            	throw new Exception("No record for id: " + id);
            }
            
            return builder.build();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }
	}
	

}
