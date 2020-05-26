package com.example.demo;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
/*
 * @MapperSacn :: Mapper 인터페이스를 인식할 수 있도록 설정
 */
@MapperScan(value= {"com.example.demo.board.mapper"})
public class TestBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestBoardApplication.class, args);
	}
	
	/*
     * SqlSessionFactory() :: 데이터베이스와의 연결, SQL의 실행 가진 중요한 객체
     * 						  MyBatis의 SqlSessionFactory를 반환, 스프링부트가 실행할 때 DataSource객체를 이 메서드 실행 시 주입해서 결과를 만들고, 그 결과를 스프링내 빈으로 사용하게 된다.
     * @Bean :: Spring에 필요한 객체를 생성
     */
    @Bean 
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{
        
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        
        sessionFactory.setDataSource(dataSource);
        return sessionFactory.getObject();
    }
    
    @Bean
	public SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
