		function changeFunc() {
		  var value = document.getElementById("status-select").value;
		  var p = document.getElementById("patient-fields");
		  var d = document.getElementById("doctor-fields");
		  
		  if (value === "PATIENT") {
		    p.style.display = "block";
		    d.style.display = "none";
		  } else if (value === "DOCTOR") {
		    p.style.display = "none";
		    d.style.display = "block";
		  }
		}