package com.example.permission_management_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PermissionUpdateRequest{
        @NotBlank(message = "Permission name is required")
        @Size(max = 100, message = "Permission name must not exceed 100 characters")
        private String name;

        public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
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

		public Boolean getActive() {
			return active;
		}

		public void setActive(Boolean active) {
			this.active = active;
		}

		public PermissionUpdateRequest() {
			super();
			// TODO Auto-generated constructor stub
		}

		public PermissionUpdateRequest(
				@NotBlank(message = "Permission name is required") @Size(max = 100, message = "Permission name must not exceed 100 characters") String name,
				@Size(max = 500, message = "Description must not exceed 500 characters") String description,
				@NotBlank(message = "Permission category is required") @Size(max = 50, message = "Category must not exceed 50 characters") String category,
				@NotBlank(message = "Module is required") @Size(max = 100, message = "Module must not exceed 100 characters") String module,
				Boolean active) {
			super();
			this.name = name;
			this.description = description;
			this.category = category;
			this.module = module;
			this.active = active;
		}

		@Size(max = 500, message = "Description must not exceed 500 characters")
        private String description;

        @NotBlank(message = "Permission category is required")
        @Size(max = 50, message = "Category must not exceed 50 characters")
        private String category;

        @NotBlank(message = "Module is required")
        @Size(max = 100, message = "Module must not exceed 100 characters")
        private String module;

        private Boolean active;

		
        }
