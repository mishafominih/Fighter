$.ajax({
  url: "http://51.250.97.3/api/get_tournaments",
  method: "post",
  dataType: "json",
  data: {'user_id': localStorage.getItem("user_id")}, 
  success: function (data) {
    console.log(data);
   
  },
  error: function (xhr, status) {
    console.log(xhr.status);
  },
});

