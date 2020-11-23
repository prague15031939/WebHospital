    let dischargeElem;

    document.onmousedown = function (event) {
		if (dischargeElem && event.target == dischargeElem) {
        	dischargeElem.remove();
        	dischargeElem = null;
      	}

      	let target = event.target;
      	let dischargeHtml = target.dataset.discharge;
      	if (!dischargeHtml) return;

      	dischargeElem = document.createElement('div');
      	dischargeElem.className = 'discharge-wrapper';
		dischargeElem.append(createForm(dischargeHtml));
      	document.body.append(dischargeElem);
	};
	
	function createForm(info) {
		var txt = document.createElement("h3");
		txt.appendChild(document.createTextNode("<c:out value='${hdischarge}'/>" + " " + info.split(';')[0]));
		
		var f = document.createElement("form");
		f.setAttribute('method',"post");
		f.setAttribute('class', "discharge-form");
		f.setAttribute('id', "discharge-form");
		f.setAttribute('action', info.split(';')[1]);
		f.setAttribute('autocomplete', "off");
		
		var i = document.createElement("input");
		i.setAttribute('type',"text");
		i.setAttribute('class', "txtb");
		i.setAttribute('name',"final-diagnosis");
		i.setAttribute('id',"final-diagnosis");
		i.setAttribute('placeholder', "<c:out value='${hfinaldiagnosis}'/>");
		i.setAttribute("onkeydown", "this.parentElement.style.borderColor = '#DCDCDC';");
		
		var txtb = document.createElement("div");
		txtb.setAttribute("class", "txtb");
		txtb.appendChild(i);

		var b = document.createElement("button");
		b.setAttribute('type',"button");
		b.setAttribute('class', "disbtn");
		b.setAttribute('onclick', "submitDischargeForm();");
		b.appendChild(document.createTextNode("<c:out value='${hdischarge}'/>"));

		f.appendChild(txt);
		f.appendChild(txtb);
		f.appendChild(b);
				
		return f;
	}
	
	function submitDischargeForm() {
			var form = document.getElementById("discharge-form");
			var error = false;
			
			if (document.getElementById("final-diagnosis").value.trim() === "") {
				document.getElementById("final-diagnosis").parentElement.style.borderColor = "#e94560";
				error = true;
			}
				
			if (!error)
				form.submit();
			else
				error = false;	
	}
	