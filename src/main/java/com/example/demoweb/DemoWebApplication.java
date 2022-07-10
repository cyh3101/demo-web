package com.example.demoweb;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.nio.charset.StandardCharsets;

@SpringBootApplication
@RestController
public class DemoWebApplication {
//	@Autowired
//	RestTemplate restTemplate;
//	public static final Logger logger = LoggerFactory.getLogger(DemoWebApplication.class);
	public static final Logger logger = Logger.getLogger("MyLogger");

	public static void main(String[] args) {
		SpringApplication.run(DemoWebApplication.class, args);
	}

	@GetMapping("/hello")
	public String Hello(@RequestParam(value = "name",defaultValue = "world")String name){
		String postUrl = "http://127.0.0.1:8080/test";

		logger.info("userName :"+name);
//		System.out.println(String.format("Hello %s",name));
		RestTemplate restTemplate = new RestTemplate();
//		restTemplate.set
		restTemplate.getMessageConverters().set(1,new StringHttpMessageConverter(StandardCharsets.UTF_8));

		HttpHeaders headers = new HttpHeaders();

		HttpMethod method = HttpMethod.POST;
		MultiValueMap<String,String> map = new LinkedMultiValueMap<>();
		map.add("name","tester");
		headers.add("Accept",MediaType.APPLICATION_JSON.toString());
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<MultiValueMap<String,String>> requestEntity = new HttpEntity<>(map,headers);

//		ResponseEntity<String> response = restTemplate.postForEntity(postUrl,requestEntity,String.class);
		ResponseEntity<String> response = restTemplate.getForEntity(postUrl,String.class);
		Class<String> type = String.class;
		return String.format("response text : %s",response.getBody());
	}

	@GetMapping("/test")
	public String test(@RequestParam(value = "name",defaultValue = "World1")String name){
		return String.format("hello %s",name);
	}

	@GetMapping(value = "/testJson")
	public String testJson(@RequestParam(value = "userName",defaultValue = "test")String userName){
		JSONObject jobj = new JSONObject();

		jobj.put("userName",userName);
		jobj.put("password","abc12345");
		return jobj.toJSONString();
	}

	@ResponseBody
	@PostMapping(value = "/testJsonPost",produces = "application/json;charset=UTF-8")
	public String testJsonPost(@RequestBody JSONObject jsonParam){
		logger.info(jsonParam.toJSONString());

		JSONObject result = new JSONObject();
		result.put("userName",jsonParam.get("userName"));
		result.put("password",jsonParam.get("password"));

		result.put("success",true);

		return result.toJSONString();

		//新增注释
	}


}
