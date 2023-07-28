let index = {
	init: function() {
		$("#btn-save").on("click", ()=>{
			this.save();
		});
		
//		$("#btn-login").on("click", ()=>{
//			this.login();
//		});

		$("#btn-update").on("click", ()=>{
			this.update();
		});
	},
	
	save: function() {
		// alert('user의 save함수 호출됨.');
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		
		// console.log(data);
		$.ajax({
			// 회원가입 수행 요청 -> 성공 / 실패
			type: "POST",
			url: "/auth/joinProc",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(_resp) {
			alert("회원가입 완료.");
			// alert(_resp);
			// console.log(_resp);
			location.href = "/";
		}).fail(function(error) {
			alert(JSON.stringify(error));
		}); // ajax를 이용해 3개의 데이터를 json으로 변경해 insert 요청
		
	},
	
/*	login: function() {
		// alert('user의 save함수 호출됨.');
		let data = {
			username: $("#username").val(),
			password: $("#password").val()
		};
		
		// console.log(data);
		$.ajax({
			// 회원가입 수행 요청 -> 성공 / 실패
			type: "POST",
			url: "/api/user/login",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(_resp) {
			alert("로그인 완료.");
			// alert(_resp);
			// console.log(_resp);
			location.href = "/";
		}).fail(function(error) {
			alert(JSON.stringify(error));
		}); // ajax를 이용해 3개의 데이터를 json으로 변경해 insert 요청
		
	}
*/

	update: function() {
		let data = {
			id: $("#id").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		
		$.ajax({
			type: "PUT",
			url: "/user",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(_resp) {
			alert("수정 완료.");
			location.href = "/";
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
		
	},
}

index.init();