		function changeFunc() {
		  var value = document.getElementById("status-select").value;
		  var p = document.getElementById("patient-fields");
		  var d = document.getElementById("doctor-fields");
		  var ill = document.getElementById("patient-extra");
		  
		  if (value === "PATIENT") {
		    p.style.display = "block";
		    ill.style.display = "block";
		    d.style.display = "none";
		  } else if (value === "DOCTOR" || value === "NURSE") {
		    p.style.display = "none";
		    ill.style.display = "none";
		    d.style.display = "block";
		  }
		  
		  if (value === "NURSE")
		  	document.getElementById("specialization")[5].selected = true;
		  if (value === "DOCTOR" && document.getElementById("specialization").value === "NURSE")
		  	document.getElementById("specialization")[0].selected = true;
		}
		
		function changeSpec() {
			if (document.getElementById("status-select").value == "NURSE")
				document.getElementById("specialization")[5].selected = true;
			if (document.getElementById("specialization").value == "NURSE" && document.getElementById("status-select").value == "DOCTOR")
				document.getElementById("specialization")[0].selected = true;
		}
			
		var error = false;
		
		function checkFields() {
			var form = document.getElementById("register-form");
			var basicElements = ["username", "email", "password"];
			var patientElements = ["own-name", "birthdate", "passport", "living-place", "past-illnesses"];
			var doctorElements = ["own-name", "birthdate", "specialization", "regkey"];
			
			basicElements.forEach((el) => track(el));
			
			if (document.getElementById("status-select").value === "PATIENT") 
				patientElements.forEach((el) => track(el));
			else if (document.getElementById("status-select").value === "DOCTOR" || document.getElementById("status-select").value === "NURSE")
				doctorElements.forEach((el) => track(el));
				
			if (!error)
				form.submit();
			else
				error = false;	
		}
		
		function checkLoginFields() {
			var form = document.getElementById("login-form");
			var basicElements = ["username", "password"];
			
			basicElements.forEach((el) => track(el));
				
			if (!error)
				form.submit();
			else
				error = false;	
		}
		
		function track(element) {
			console.log(document.getElementById(element));
			if (document.getElementById(element).value.trim() === "") {
				document.getElementById(element).parentElement.style.borderColor = "#e94560";
				error = true;
			}
		}
		