function switchLang() {
	
	var lang = document.getElementById("langChoise").checked ? "ru" : "en";
	var xhr = new XMLHttpRequest();
	xhr.open('POST', "main?lang=" + lang , false);
	
	xhr.onload = function() {
		location.reload(true);
	};
	
	xhr.send();
}