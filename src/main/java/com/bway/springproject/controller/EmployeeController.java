package com.bway.springproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bway.springproject.model.Employee;
import com.bway.springproject.service.DepartmentService;
import com.bway.springproject.service.EmployeeService;

import jakarta.servlet.http.HttpSession;

@Controller
public class EmployeeController {
	
	
	@Autowired
	private EmployeeService empService;
	
	@Autowired
	private DepartmentService deptService;
	
	
	
	@GetMapping("/employee")
	public String getEmployee(Model model, HttpSession session) {
		
		if(session.getAttribute("validuser") == null) {
			
			return "LoginForm";
			
		}
		
		
		model.addAttribute("dList", deptService.getAllDept());
		return "EmployeeForm";
	}
	
	@PostMapping("/employee")
	public String postEmployee(@ModelAttribute Employee emp) {
		
		empService.addEmployee(emp);
		return "redirect:/employee";
	}
	
	
	@GetMapping("/employeeList")
	public String getEmployeeList(Model model, HttpSession session) {
		
		if(session.getAttribute("validuser") == null) {
			
			return "LoginForm";
			
		}
		
		model.addAttribute("eList",empService.getAllEmployee());
		
		return "EmployeeListForm";
	}
	
	
	
	@GetMapping("/employee/delete")
	public String delete(@RequestParam Long id) {
		empService.deleteEmployee(id);
		return "redirect:/employeeList";
	}
	
	
	@GetMapping("/employee/edit")
	public String edit(@RequestParam Long id, Model model) {
		
		model.addAttribute("eModel",empService.getEmployeeById(id));
		model.addAttribute("dList",deptService.getAllDept());
		return "EmployeeEditForm";
	}
	
	@PostMapping("/employee/update")
	public String update(@ModelAttribute Employee emp) {
		empService.updateEmployee(emp);
		return "redirect:/employeeList";
	}
	

}
