$.ajax({
  url: "api/get_players",
  method: "post",
  dataType: "json",
  data: {'tournament_id': '27', 'user': localStorage.getItem('user_id')}, 
  success: function (data) {
    console.log(data);
  },
  error: function (xhr, status) {
    console.log(xhr.status);
  },
});