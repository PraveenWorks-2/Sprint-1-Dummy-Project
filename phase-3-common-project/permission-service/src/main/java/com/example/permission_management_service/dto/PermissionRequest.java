package com.example.permission_management_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PermissionRequest{
        @NotBlank(message = "Permission name is required")
        @Size(max = 100, message = "Permission name must not exceed 100 characters")
        private String name;

        public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		public String getModule() {
			return module;
		}

		public void setModule(String module) {
			this.module = module;
		}

		public PermissionRequest() {
			super();
			// TODO Auto-generated constructor stub
		}

		public PermissionRequest(
				@NotBlank(message = "Permission name is required") @Size(max = 100, message = "Permission name must not exceed 100 characters") String name,
				@NotBlank(message = "Permission code is required") @Size(max = 100, message = "Permission code must not exceed 100 characters") String code,
				@Size(max = 500, message = "Description must not exceed 500 characters") String description,
				@NotBlank(message = "Permission category is required") @Size(max = 50, message = "Category must not exceed 50 characters") String category,
				@NotBlank(message = "Module is required") @Size(max = 100, message = "Module must not exceed 100 characters") String module) {
			this.name = name;
			this.code = code;
			this.description = description;
			this.category = category;
			this.module = module;
		}

		@NotBlank(message = "Permission code is required")
        @Size(max = 100, message = "Permission code must not exceed 100 characters")
        private String code;

        @Size(max = 500, message = "Description must not exceed 500 characters")
        String description;

        @NotBlank(message = "Permission category is required")
        @Size(max = 50, message = "Category must not exceed 50 characters")
        private String category;

        @NotBlank(message = "Module is required")
        @Size(max = 100, message = "Module must not exceed 100 characters")
        private String module;
}