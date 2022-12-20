$("#create_tournament").on("submit", function (event) {
  event.preventDefault();
  let categories = ['100кг+', '90кг+','80кг+'];
  $.ajax({
    url: "/api/create_tournament",
    method: "post",
    dataType: "json",
    data: {'name': 'tes5', 'description': '666', 'user_id': localStorage.getItem("user_id"), 'places': 'ууу', 'categories': '10', 'type': '2'}, 
    success: function (data) {
      localStorage.setItem("tournament_id", data['id']);
      console.log(data);
     
    },
    error: function (xhr, status) {
      console.log(xhr.status);
    },
  });
});