$("document").ready(function () {
	if (getCookie("login") === undefined) {
		document.location.href = "login.html";
	}
	
	let client = new Client();

	$("#confirm").click(function () {
		let userIdTo = $("#userIdTo").val();
		let money = $("#money").val();
		
		if (filterFloat(money) === undefined) {
			alert("Money amount wrong");
			return;
		}
		client.openConnection();
		client.sendMessage("transfer", getCookie("id"), userIdTo, money);
		
		let answer = client.getArrayFromMessage();
		alert("Answer:" + answer);
	});
});