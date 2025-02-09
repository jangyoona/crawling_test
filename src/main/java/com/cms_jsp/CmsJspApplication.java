package com.cms_jsp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages="com.cms_jsp.mapper")
@SpringBootApplication
public class CmsJspApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmsJspApplication.class, args);
	}

}
