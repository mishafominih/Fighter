$.ajax({
	url: "/api/add_player",
	method: "post",
	dataType: "json",
	data: {'tournament_id': '39', 'user': localStorage.getItem("user_id"),'name': '0', 'surname': '0', 'patronymic': '0', 'categories':'100+', 'link': '0', 'description': '0'}, 
	success: function (data) {
	  console.log(data);
	},
	error: function (xhr, status) {
	  console.log(xhr.status);
	},
  });


// $("#join").on("submit", function () {
//     let q = $.ajax({
//       url: "/api/join_to_tournament",
//       method: "post",
//       dataType: "html",
//       data: $(this).serialize(),
//       success: function (data) {
//         let result = q.responseJSON;
//         console.log(data);
//         console.log(result);
//         console.log('Я работаю')
//         if ((result = true)) {
//           window.location.href = "grid.html";
//         }
//         else{
//             console.log("нет");
//         }
//       },
//       error: console.log('не отправился я')
//     });
//   });
