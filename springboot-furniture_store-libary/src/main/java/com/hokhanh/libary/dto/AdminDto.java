package com.hokhanh.libary.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDto {

	@Size(min = 2, max = 10, message = "Vui lòng nhập tên riêng 2 - 10 kí tự!")
	private String firstName;
	
	@Size(min = 2, max = 10, message = "Vui lòng nhập tên họ 2 - 10 kí tự!")
	private String lastName;

	@Size(min = 2, max = 20, message = "Vui lòng nhập tên tài khoản 2 - 20 kí tự!")
	private String username;
	
	@Size(min = 2, max = 15, message = "Vui lòng nhập tên mật khẩu 2 - 15 kí tự!")
	private String password;
	
	private String repeatPassword;
}
