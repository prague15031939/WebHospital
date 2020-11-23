		function submitPrescribeForm() {
			var form = document.getElementById("prescribe-form");
			var error = false;
			
			if (document.getElementById("diagnosis").value.trim() === "") {
				document.getElementById("diagnosis").parentElement.style.borderColor = "#e94560";
				error = true;
			}
			
			if (containsBadSymbols(document.getElementById("medicines").value.trim())) {
				document.getElementById("medicines").parentElement.style.borderColor = "#e94560";
				error = true;
			}
				
			if (!error)
				form.submit();
			else
				error = false;	
		}			
		
		function containsBadSymbols(text) {
			const badSymbols = ["@", "!", "?", "~", "`"];
			
			if (badSymbols.some(element => text.includes(element)))
				return true;
				
			return false;
		}